# coding: utf-8
import time
import math
import json
from scipy import spatial
import numpy as np

EPS = 1E-5
R = 6371
DEFAULT_RADIUS = 1.0

def get_cartesian(lat, lng):
    x = R * math.cos(lat) * math.cos(lng)
    y = R * math.cos(lat) * math.sin(lng)
    z = R * math.sin(lat)
    return x, y, z

def get_distance_km(lat_1, lng_1, lat_2, lng_2):
    d_lat = math.radians(lat_2 - lat_1)
    d_lng = math.radians(lng_2 - lng_1)
    a = math.sin(d_lat / 2) ** 2 + math.cos(math.radians(lat_1)) * \
            math.cos(math.radians(lat_2)) * math.sin(d_lng / 2) ** 2
    c = 2.0 * math.atan2(math.sqrt(a), math.sqrt(1 - a))
    d = R * c
    return d

class location_info_t:
    def __init__(self, lat, lng, count=0, last_seen=time.time()):
        self.count = count
        self.last_seen = last_seen
        self.lat = lat
        self.lng = lng

    def add_info(self):
        self.last_seen = time.time()
        self.count = self.count + 1
#        self.seen_time.append(new_time)

class location_t:
    def __init__(self, init_str='[{"long": 0.0, "lat": 0.0, "count": 0, "time": 0.5}]'):
            raw_data = json.loads(init_str)
            self.data = {}
            for location in raw_data:
                x, y, z = get_cartesian(location['lat'], location['long'])
                self.data[(x, y, z)] = location_info_t(location['lat'],
                        location['long'], location['count'], location['time'])
            self.tree = spatial.KDTree(self.data.keys())

    def query(self, xyz_coor):
        return self.data.get(xyz_coor)

    def update_location(self, lat, lng):
        x, y, z = get_cartesian(lat, lng)
        location_info = self.data.get((x, y, z))
        if location_info is None:
            return
        location_info.add_info()

    def add_location(self, lat, lng):
        x, y, z = get_cartesian(lat, lng)
        self.data[(x, y, z)] = location_info_t(lat, lng, count = 1)
        self.tree = spatial.KDTree(self.data.keys())

    def find_k_nearest(self, lat, lng, k=1, radius=DEFAULT_RADIUS):
        x, y, z = get_cartesian(lat, lng)
        query_result = self.tree.query(x = np.array([x, y, z]), k = k + 10,
                distance_upper_bound = radius + 50.0)

        ret = []
        for p in np.asarray(query_result).transpose():
            if not np.isinf(p[0]):
                location = self.data[tuple(self.tree.data[int(p[1])])]
                exact_dist = get_distance_km(lat, lng, location.lat, location.lng)
                if exact_dist < radius + EPS:
                    ret.append([exact_dist] + list(self.tree.data[int(p[1])]))

        if len(ret) == 0:
            return None
        ret = np.array(ret)
        np.sort(ret)
        return ret[0:k]

    def dumps(self):
        ret = []
        for coor, location in self.data.iteritems():
            ret.append({'lat': location.lat, 'long': location.lng,
                'count': location.count, 'time': location.last_seen})

        return ret

database = location_t()

def add(lat, lng):
    coor = database.find_k_nearest(lat, lng, radius = 0.05)
    if coor is None:
        database.add_location(lat, lng)
        return "Add new location"
    else:
        database.update_location(lat, lng)
        return "Update existing location"

def query(lat, lng, radius = DEFAULT_RADIUS):
    coor = database.find_k_nearest(lat, lng, 10, radius)
    ret = []
    if not coor is None:
        for p in coor:
            location = database.query(tuple(p[1:]))
            ret.append({'lat': location.lat, 'long': location.lng,
                'count': location.count, 'time': location.last_seen})

    return ret

def get_database():
    return database.dumps()

def init_database(str):
    database = location_t(str)

import sys
import json
import geometry
from flask import Flask, jsonify, make_response, request;

app = Flask(__name__)

@app.route('/get', methods = ['GET'])
def get_location():
    if not request.json or not 'lat' in request.json \
            or not 'long' in request.json:
                abort(400)

    ret = []

    if 'radius' in request.json:
        ret = geometry.query(lat = request.json['lat'], lng = request.json['long'],
                radius = request.json['radius'])
        ret.append({'radius': request.json['radius']})
    else:
        ret = geometry.query(lat = request.json['lat'], lng = request.json['long'])
        ret.append({'radius': geometry.DEFAULT_RADIUS})

    return jsonify(ret), 200

@app.route('/crawl', methods = ['GET'])
def dump_database():
    data = geometry.get_database()
    return jsonify(data)

@app.route('/add', methods = ['POST'])
def add_camp():
    if not request.json or not 'lat' in request.json or not 'long' in request.json:
        abort(400)

    status = geometry.add(lat = request.json['lat'], lng = request.json['long'])
    return jsonify({'status': status}), 200

@app.errorhandler(404)
def not_found(error):
    return jsonify({'error': 'Nothing here!!!'}), 400

if __name__ == '__main__':
    sv_address = '0.0.0.0'
    sv_port = '13097'
    app.run(host = sv_address, port = sv_port, debug = False)



package quytu.miniproject.services;

import java.lang.reflect.Array;
import java.util.ArrayList;

import quytu.miniproject.model.PikachuLocation;

/**
 * Created by f0ur on 5/15/18.
 */

public class DataService {
    private static final DataService instance = new DataService();

    public static DataService getInstance() {
        return instance;
    }

    private DataService() {

    }

    public ArrayList<PikachuLocation> getPikachuLocationsWithin2kms(String address) {
        //Get data from server, model in model/PikachuLocation

        ArrayList<PikachuLocation> list = new ArrayList<>();
        list.add(new PikachuLocation(37.422408f,-122.085609f, "Google Main Sand Court", "Day la cai dia chi"));
        list.add(new PikachuLocation(35.302f,-120.658f,"On the Campus","1 Grand Ave, San Luis Obispo, CA 93401"));
        list.add(new PikachuLocation(35.267f,-120.652f,"East Side Tower","2494 Victoria Ave, San Luis Obispo, CA 93401"));
        return list;
    }
}

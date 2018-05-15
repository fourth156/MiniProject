package quytu.miniproject.model;

/**
 * Created by f0ur on 5/14/18.
 */

public class PikachuLocation {
    public float getLongitude() {
        return longitude;
    }

    public float getLatitude() {
        return latitude;
    }

    public String getLocationTitle() {
        return locationTitle;
    }

    public String getLocationAddress() {
        return locationAddress;
    }

    private float longitude;
    private float latitude;
    private String locationTitle;
    private String locationAddress;

    public PikachuLocation(float latitude, float longitude, String locationTitle, String locationAddress) {
        this.longitude = longitude;
        this.latitude = latitude;
        this.locationTitle = locationTitle;
        this.locationAddress = locationAddress;
    }


}


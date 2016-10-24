package edu.fiusac.coecys.model;

import com.google.android.gms.maps.model.LatLng;

/**
 * Created by Mario Alexander Gutierrez Hernandez
 * email: alex.dev502@gmail.com
 */
public class Place {
    private String name, address;
    private LatLng latLng;

    public Place(String name, String address, double latitude, double longitude){
        this.name = name;
        this.address = address;
        this.latLng = new LatLng(latitude, longitude);
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public LatLng getLatLng() {
        return latLng;
    }
}

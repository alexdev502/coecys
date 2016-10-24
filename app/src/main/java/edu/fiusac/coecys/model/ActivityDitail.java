package edu.fiusac.coecys.model;

import com.google.android.gms.maps.model.LatLng;

/**
 * Created by Mario Alexander Gutierrez Hernandez
 * email: alex.dev502@gmail.com
 */
public class ActivityDitail extends ActivityInfo {

    private String company;
    private String inCharge;
    private String inChargePhone;
    private String description;
    private Double latitude;
    private Double longitude;
    private String place;
    private String address;
    private String date;

    public ActivityDitail(String id,
                          String title,
                          int type,
                          String startTime,
                          String endTime,
                          String speaker,
                          String company,
                          String inCharge,
                          String inChargePhone,
                          String description,
                          Double latitude,
                          Double length,
                          String place,
                          String address,
                          int icon,
                          String date) {

        this.id = id;
        this.title = title;
        this.type = type;
        this.startTime = startTime;
        this.endTime = endTime;
        this.speaker = speaker;
        this.company = company;
        this.inCharge = inCharge;
        this.inChargePhone = inChargePhone;
        this.description = description;
        this.latitude = latitude;
        this.longitude = length;
        this.place = place;
        this.address = address;
        this.icon = icon;
        this.date = date;
    }

    public String getCompany() {
        return company;
    }

    public String getInCharge() {
        return inCharge;
    }

    public String getInChargePhone() {
        return inChargePhone;
    }

    public String getDescription() {
        return description;
    }

    public Double getLatitude() {
        return latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public String getPlace() {
        return place;
    }

    public String getAddress() {
        return address;
    }

    public LatLng getLatLang(){
        if(this.latitude!=null && this.longitude!=null) return new LatLng(latitude, longitude);
        return null;
    }

    public String getDate() {
        return date;
    }
}

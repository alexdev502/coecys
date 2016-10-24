package edu.fiusac.coecys.utils;

import org.json.JSONObject;

import edu.fiusac.coecys.model.ActivityDitail;
import edu.fiusac.coecys.model.ActivityInfo;
import edu.fiusac.coecys.model.DayInfo;
import edu.fiusac.coecys.model.Place;

/**
 * Created by Mario Alexander Gutierrez Hernandez
 * email: alex.dev502@gmail.com
 */
public class TransformerJSONToObject {

    public DayInfo convertJSONToDayInfo(JSONObject jsonObject) {
        try {
            String id = jsonObject.getString("Id");
            String title = jsonObject.getString("Activity");
            String startTime = jsonObject.getString("Day_Start_Time");
            String endTime = jsonObject.getString("Day_Ending_Time");
            String date = jsonObject.getString("Date");

            return new DayInfo(id, title, date, startTime, endTime);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public ActivityInfo convertJSONToActivityInfo(JSONObject jsonObject) {
        try {
            String id = jsonObject.getString("Id");
            String title = jsonObject.getString("Titulo");
            int type = jsonObject.getInt("Type");
            String speaker = jsonObject.getString("Speaker");
            String startTime = jsonObject.getString("Start_Time");
            String endTime = jsonObject.getString("Ending_Time");
            int icon = jsonObject.getInt("Icon");

            return new ActivityInfo(id, title, speaker, startTime, endTime, type, icon);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public ActivityDitail convertJSONToActivityDitail(JSONObject jsonObject) {
        try {
            String id = jsonObject.getString("Id");
            String title = jsonObject.getString("Title");
            int type = jsonObject.getInt("Type");
            String startTime = jsonObject.getString("Start_time");
            String endTime = jsonObject.getString("Ending_time");
            String speaker = jsonObject.getString("Speaker");
            String company = jsonObject.getString("Company");
            String inCharge = jsonObject.getString("InCharge");
            String inChargePhone = jsonObject.getString("InCharge_Phone");
            String description = jsonObject.getString("Description");
            Double latitud = jsonObject.getDouble("Latitud");
            Double lenght = jsonObject.getDouble("Longitud");
            String place = jsonObject.getString("Place");
            String address = jsonObject.getString("Address");
            int icon = jsonObject.getInt("Icon");
            String date = jsonObject.getString("Date");

            return new ActivityDitail(id, title, type, startTime, endTime, speaker, company,
                    inCharge, inChargePhone, description, latitud, lenght, place, address, icon, date);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public Place convertJSONToPlace(JSONObject jsonObject) {
        try {
            String name = jsonObject.getString("Nombre");
            String address = jsonObject.getString("Direccion");
            Double latitude = jsonObject.getDouble("Latitud");
            Double longitude = jsonObject.getDouble("Longitud");

            return new Place(name, address, latitude, longitude);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }
}

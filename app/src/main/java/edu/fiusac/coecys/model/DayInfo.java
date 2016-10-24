package edu.fiusac.coecys.model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Mario Alexander Gutierrez Hernandez
 * email: alex.dev502@gmail.com
 */
public class DayInfo {
    String id;
    String title;
    String date;
    String timeStart;
    String timeEnd;

    public DayInfo(String id, String title, String date,  String timeStart, String timeEnd) {
        this.id = id;
        this.title = title;
        this.date = date;
        this.timeStart = timeStart;
        this.timeEnd = timeEnd;
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getTimeStart() {
        return timeStart;
    }

    public String getTimeEnd() {
        return timeEnd;
    }

    public String getNameMonth(){
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Date convertedDate = new Date();
        boolean flag = false;
        try {
            convertedDate = dateFormat.parse(this.date);
            flag = true;
        } catch (ParseException e) {
            e.printStackTrace();
        }

        if(flag){
            return (String) android.text.format.DateFormat.format("MMM", convertedDate);
        }

        return "";
    }


    public String getNumDay(){
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Date convertedDate = new Date();
        boolean flag = false;
        try {
            convertedDate = dateFormat.parse(this.date);
            flag = true;
        } catch (ParseException e) {
            e.printStackTrace();
        }

        if(flag){
            return (String) android.text.format.DateFormat.format("dd", convertedDate);
        }

        return "";
    }

    public String getYear(){
        return "2016";
    }

    public String getDate(){
        return this.date;
    }
}

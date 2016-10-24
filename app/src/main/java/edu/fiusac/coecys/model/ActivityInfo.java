package edu.fiusac.coecys.model;

/**
 * Created by Mario Alexander Gutierrez Hernandez
 * email: alex.dev502@gmail.com
 */
public class ActivityInfo {
    protected String id;
    protected String title;
    protected String speaker;
    protected String startTime;
    protected String endTime;
    protected int type;
    protected int icon;

    public ActivityInfo(){}

    public ActivityInfo(String id, String title, String speaker, String startTime, String endTime, int type, int icon) {
        this.id = id;
        this.title = title;
        this.speaker = speaker;
        this.startTime = startTime;
        this.endTime = endTime;
        this.type = type;
        this.icon = icon;
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getSpeaker() {
        return speaker;
    }

    public String getStartTime() {
        return startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public int getType() { return type; }

    public int getIcon() {
        return icon;
    }
}

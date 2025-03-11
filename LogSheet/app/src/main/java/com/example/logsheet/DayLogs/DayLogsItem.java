package com.example.logsheet.DayLogs;

public class DayLogsItem {
    String title, intensity, duration;

    public DayLogsItem(String title, String intensity, String duration) {
        this.title = title;
        this.intensity = intensity;
        this.duration = duration;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getIntensity() {
        return intensity;
    }

    public void setIntensity(String intensity) {
        this.intensity = intensity;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }
}

package com.example.logsheet.Logs;

public class LogsItem {
    String title;
    String activeness;

    public LogsItem(String title, String activeness) {
        this.title = title;
        this.activeness = activeness;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getActiveness() {
        return activeness;
    }

    public void setActiveness(String activeness) {
        this.activeness = activeness;
    }
}

package com.example.logsheet.Logs;

import com.example.logsheet.Utilities.Utility;

public class LogsItem {
    String title, activeness;
    int year, month, week;

    public int getWeek() {
        return week;
    }

    public void setWeek(int week) {
        this.week = week;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public LogsItem(String activeness, int year, int month, int week) {
        this.activeness = activeness;
        this.year = year;
        this.month = month;
        this.week = week;
    }

    public String getTitle() {
        return String.format("%s %d - Weeek %d", Utility.getMonthName(this.month), this.year, this.week);
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

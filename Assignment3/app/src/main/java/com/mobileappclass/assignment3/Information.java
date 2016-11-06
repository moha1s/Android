package com.mobileappclass.assignment3;

/**
 * Created by Chelsea on 11/5/16.
 */

public class Information {
    private String date;
    private String netid;
    private String x;
    private String y;

    public Information(String date, String netid, String x, String y) {
        this.date = date;
        this.netid = netid;
        this.x = x;
        this.y = y;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getNetid() {
        return netid;
    }

    public void setNetid(String netid) {
        this.netid = netid;
    }

    public String getX() {
        return x;
    }

    public void setX(String x) {
        this.x = x;
    }

    public String getY() {
        return y;
    }

    public void setY(String y) {
        this.y = y;
    }
}

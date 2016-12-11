package com.mobileappclass.finalproject;

/**
 * Created by GA on 12/6/16.
 */

public class Record
{
    public double longitude;
    public double latitude;
    public String title;
    public String userName;
    public String time;
    public String detail;

    public Record(double longitude, double latitude, String time, String title, String userName, String detail)
    {
        this.longitude = longitude;
        this.latitude = latitude;
        this.title = title;
        this.userName = userName;
        this.time = time;
        this.detail = detail;
    }

    public Record()
    {
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public double getLongitude() {
        return longitude;
    }

    public String getDetail() {
        return detail;
    }

    public String getTime() {
        return time;
    }

    public double getLatitude() {
        return latitude;
    }

    public String getUserName() {
        return userName;
    }

    public String getTitle() {
        return title;
    }
}

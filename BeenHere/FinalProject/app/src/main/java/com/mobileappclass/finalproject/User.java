package com.mobileappclass.finalproject;

import android.widget.ListView;

/**
 * Created by Chelsea on 12/6/16.
 */

public class User {
    private String aName;
    private String aSpeak;
    private int aIcon;

    public User() {
    }

    public User(String aName, String aSpeak, int aIcon) {
        this.aName = aName;
        this.aSpeak = aSpeak;
        this.aIcon = aIcon;
    }

    public String getaName() {
        return aName;
    }

    public String getaSpeak() {
        return aSpeak;
    }

    public int getaIcon() {
        return aIcon;
    }

    public void setaName(String aName) {
        this.aName = aName;
    }

    public void setaSpeak(String aSpeak) {
        this.aSpeak = aSpeak;
    }

    public void setaIcon(int aIcon) {
        this.aIcon = aIcon;
    }
}

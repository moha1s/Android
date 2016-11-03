package com.example.chelsea.hw2;

/**
 * Created by Chelsea on 10/26/16.
 */

public class Contacts {
    private String name;
    private String number;
    private String relationship;
    private boolean selected = false;

    public Contacts(String name, String number, String relationship) {
        this.name = name;
        this.number = number;
        this.relationship = relationship;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNumber() {return number;}

    public void setNumber(String number) {this.number = number;}

    public String getRelationship() {return relationship;}

    public void setRelationship(String relationship) {this.relationship = relationship;}

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }
}

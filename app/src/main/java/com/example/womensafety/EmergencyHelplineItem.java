package com.example.womensafety;

public class EmergencyHelplineItem {
    private String name;
    private String number;

    public EmergencyHelplineItem(String name, String number) {
        this.name = name;
        this.number = number;
    }

    public String getName() {
        return name;
    }

    public String getNumber() {
        return number;
    }
}

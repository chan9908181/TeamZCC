package com.example.teamzcc;

public class Preset {
    private String activity;
    private String color;

    public Preset(String activity, String color) {
        this.activity = activity;
        this.color = color;
    }

    public String getActivity() {
        return activity;
    }

    public void setActivity(String activity) {
        this.activity = activity;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }
}

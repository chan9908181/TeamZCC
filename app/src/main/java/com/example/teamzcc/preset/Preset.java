package com.example.teamzcc.preset;

public class Preset {
    static int count = 0;
    private String activity;
    private String color;
    //total time spent on the preset's activity represented in number of time units
    private long totalTime;
    private int position;
    private boolean selected;

    public Preset(String activity, String color) {
        this.activity = activity;
        if (color.isEmpty()){
            this.color = "gray";
        }
        this.color = color;
        this.totalTime = 0;
        this.position = count++;
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

    public long getTotalTime() {
        return totalTime;
    }

    public void setTotalTime(long totalTime) {
        this.totalTime = totalTime;
    }

    public int getPosition() {
        return position;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public boolean equals(Preset other){
        if (this.getActivity().equals(other.getActivity()) && this.getColor().equals(other.getColor())){
            return true;
        } else return false;
    }
}

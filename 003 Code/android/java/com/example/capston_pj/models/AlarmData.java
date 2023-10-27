package com.example.capston_pj.models;

public class AlarmData {
    private long id;
    private int dayOfWeek;
    private int hourOfDay;
    private int minute;


    public AlarmData(long id, int dayOfWeek, int hourOfDay, int minute) {
        this.id = id;
        this.dayOfWeek = dayOfWeek;
        this.hourOfDay = hourOfDay;
        this.minute = minute;
    }

    public long getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getDayOfWeek() {
        return dayOfWeek;
    }

    public void setDayOfWeek(int dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }

    public int getHourOfDay() {
        return hourOfDay;
    }

    public void setHourOfDay(int hourOfDay) {
        this.hourOfDay = hourOfDay;
    }

    public int getMinute() {
        return minute;
    }

    public void setMinute(int minute) {
        this.minute = minute;
    }
}

package com.example.findholidaysapp.model;

public class HolidayInfo {
    int id;
    String holiday_name;
    String holiday_date;

    public HolidayInfo (String name, String date) {
        this.holiday_name=name;
        this.holiday_date=date;
    }

    public String getHoliday_name() {
        return holiday_name;
    }

    public String getHoliday_date() {
        return holiday_date;
    }
}

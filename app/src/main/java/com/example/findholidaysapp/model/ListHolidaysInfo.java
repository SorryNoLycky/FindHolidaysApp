package com.example.findholidaysapp.model;

public class ListHolidaysInfo {

    public HolidayInfo[] listHolidaysInfo;

    public ListHolidaysInfo(int size) {
        listHolidaysInfo = new HolidayInfo[size];
    }

    public void addHoliday(String holiday_name, String holiday_date, int id) {
        HolidayInfo holiday = new HolidayInfo(holiday_name, holiday_date);
        listHolidaysInfo[id] = holiday;
    }
}

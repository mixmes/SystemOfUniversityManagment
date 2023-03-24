package ru.sfedu.Model;

import ru.sfedu.Constants;

import java.sql.Time;
import java.util.Date;
import java.util.Objects;
public class Event {
    protected int ID;
    protected int scheduleID;
    protected String place;
    protected Time time;
    protected Constants.DayOfWeek dayOfWeek;
    protected Event(){
    }
    protected Event(int ID, int scheduleID, String place,int hours,int minutes , Constants.DayOfWeek day){
        this.scheduleID = scheduleID;
        this.ID = ID;
        this.place = place;
        this.time = new Time(hours,minutes,0);
        this.dayOfWeek = day;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public int getScheduleID() {
        return scheduleID;
    }

    public void setScheduleID(int scheduleID) {
        this.scheduleID = scheduleID;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public Time getTime() {
        return time;
    }

    public void setTime(Time time) {
        this.time = time;
    }

    public Constants.DayOfWeek getDayOfWeek() {
        return dayOfWeek;
    }

    public void setDayOfWeek(Constants.DayOfWeek dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Event event = (Event) o;
        return ID == event.ID && Objects.equals(place, event.place) && Objects.equals(time, event.time);
    }

    @Override
    public int hashCode() {
        return Objects.hash(ID, place, time);
    }
}

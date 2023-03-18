package ru.sfedu.Model;

import java.util.Date;
import java.util.Objects;
public class Event {
    protected int ID;
    protected int scheduleID;
    protected String place;
    protected String time = new String();
    protected Event(){
        this.place = "Null";
    }
    protected Event(int ID,int scheduleID, String place , String time ){
        this.scheduleID = scheduleID;
        this.ID = ID;
        this.place = place;
        this.time = time;
    }

    public int getScheduleID() {
        return scheduleID;
    }

    public void setScheduleID(int scheduleID) {
        this.scheduleID = scheduleID;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
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

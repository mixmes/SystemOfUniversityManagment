package ru.sfedu.Model;

import java.util.Date;
import java.util.Objects;

public class Event {
    private int ID;
    private String place;
    private Date time = new Date();
    Event(){
        this.place = "Null";
    }
    Event(int ID, String place , Date time ){
        this.ID = ID;
        this.place = place;
        this.time = time;
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

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
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

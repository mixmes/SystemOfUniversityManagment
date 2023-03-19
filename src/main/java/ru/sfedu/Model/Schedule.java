package ru.sfedu.Model;
import ru.sfedu.Model.TypeOfSchedule;

import java.util.ArrayList;
import java.util.Objects;

public class Schedule  {
    private int ID;
    private int semester;
    private  TypeOfSchedule type;
    private ArrayList<Event> events = new ArrayList<>();
    public Schedule(){}
    public Schedule(int ID, int semester, TypeOfSchedule type){
        this.ID = ID;
        this.semester = semester;
        this.type = type;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public int getSemester() {
        return semester;
    }

    public void setSemester(int semester) {
        this.semester = semester;
    }

    public TypeOfSchedule getType() {
        return type;
    }

    public void setType(TypeOfSchedule type) {
        this.type = type;
    }

    public ArrayList<Event> getEvents() {
        return events;
    }

    public void setEvents(ArrayList<Event> events) {
        this.events = events;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Schedule schedule = (Schedule) o;
        return ID == schedule.ID && semester == schedule.semester;
    }

    @Override
    public int hashCode() {
        return Objects.hash(ID, semester);
    }
}

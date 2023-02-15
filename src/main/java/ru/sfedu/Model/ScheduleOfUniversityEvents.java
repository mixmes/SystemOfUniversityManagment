package ru.sfedu.Model;

import java.util.ArrayList;
import java.util.Objects;

public class ScheduleOfUniversityEvents extends Schedule {
    private ArrayList<UniversityEvent> universityEvents ;
    ScheduleOfUniversityEvents(){}
    ScheduleOfUniversityEvents(ArrayList<UniversityEvent> universityEvents){
        this.universityEvents = universityEvents;
    }

    public ArrayList<UniversityEvent> getUniversityEvents() {
        return universityEvents;
    }

    public void setUniversityEvents(ArrayList<UniversityEvent> universityEvents) {
        this.universityEvents = universityEvents;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ScheduleOfUniversityEvents that = (ScheduleOfUniversityEvents) o;
        return Objects.equals(universityEvents, that.universityEvents);
    }

    @Override
    public int hashCode() {
        return Objects.hash(universityEvents);
    }
}

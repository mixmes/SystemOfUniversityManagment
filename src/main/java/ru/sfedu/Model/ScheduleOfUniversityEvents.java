package ru.sfedu.Model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import java.util.ArrayList;
import java.util.Objects;
@XmlAccessorType(XmlAccessType.FIELD)
public class ScheduleOfUniversityEvents extends Schedule {
    private ArrayList<UniversityEvent> universityEvents ;
    public ScheduleOfUniversityEvents(){}
    public ScheduleOfUniversityEvents(int ID,int semester,ArrayList<UniversityEvent> universityEvents){
        this.ID = ID;
        this.semester = semester;
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
        return Objects.equals(universityEvents, that.universityEvents) && ID == that.ID;
    }

    @Override
    public int hashCode() {
        return Objects.hash(universityEvents);
    }
}

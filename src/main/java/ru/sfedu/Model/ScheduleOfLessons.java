package ru.sfedu.Model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import java.util.ArrayList;
import java.util.Objects;
@XmlAccessorType(XmlAccessType.FIELD)
public class ScheduleOfLessons extends Schedule {
    private ArrayList<Lesson> lessons;
    public ScheduleOfLessons(){}
    public ScheduleOfLessons(int ID,int semester,ArrayList<Lesson> lessons){
        this.ID = ID;
        this.semester = semester;
        this.lessons =  lessons;
    }

    public ArrayList<Lesson> getLessons() {
        return lessons;
    }

    public void setLessons(ArrayList<Lesson> lessons) {
        this.lessons = lessons;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ScheduleOfLessons that = (ScheduleOfLessons) o;
        return Objects.equals(lessons, that.lessons) && ID == that.ID;
    }

    @Override
    public int hashCode() {
        return Objects.hash(lessons);
    }
}

package ru.sfedu.Model;

import java.util.ArrayList;
import java.util.Objects;

public class ScheduleOfLessons extends Schedule {
    private ArrayList<Lesson> lessons;
    ScheduleOfLessons(){}
    ScheduleOfLessons(ArrayList<Lesson> lessons){
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
        return Objects.equals(lessons, that.lessons);
    }

    @Override
    public int hashCode() {
        return Objects.hash(lessons);
    }
}

package ru.sfedu.Model;

import java.util.ArrayList;
import java.util.Objects;

public class ScheduleOfExams extends Schedule {
    private ArrayList<Exam> exams = new ArrayList<>();
    ScheduleOfExams(){}
    ScheduleOfExams(ArrayList<Exam> exams){
        this.exams = exams;
    }

    public ArrayList<Exam> getExams() {
        return exams;
    }

    public void setExams(ArrayList<Exam> exams) {
        this.exams = exams;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ScheduleOfExams that = (ScheduleOfExams) o;
        return Objects.equals(exams, that.exams);
    }

    @Override
    public int hashCode() {
        return Objects.hash(exams);
    }
}

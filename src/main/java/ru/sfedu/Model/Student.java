package ru.sfedu.Model;

import java.util.ArrayList;
import java.util.Objects;

public class Student {
    private int ID;
    private String name ;
    private MarkBook markBook = new MarkBook();
    private ScheduleOfLessons scheduleOfLessons = new ScheduleOfLessons();
    private ScheduleOfExams scheduleOfExams = new ScheduleOfExams();
    private ScheduleOfUniversityEvents scheduleOfUniversityEvents = new ScheduleOfUniversityEvents();
    Student(){this.name = "Null Null  Null";}
    Student(int ID, String name){
        this.ID = ID;
        this.name = name;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public MarkBook getMarkBook() {
        return markBook;
    }

    public void setMarkBook(MarkBook markBook) {
        this.markBook = markBook;
    }

    public ScheduleOfLessons getScheduleOfLessons() {
        return scheduleOfLessons;
    }

    public void setScheduleOfLessons(ScheduleOfLessons scheduleOfLessons) {
        this.scheduleOfLessons = scheduleOfLessons;
    }

    public ScheduleOfExams getScheduleOfExams() {
        return scheduleOfExams;
    }

    public void setScheduleOfExams(ScheduleOfExams scheduleOfExams) {
        this.scheduleOfExams = scheduleOfExams;
    }

    public ScheduleOfUniversityEvents getScheduleOfUniversityEvents() {
        return scheduleOfUniversityEvents;
    }

    public void setScheduleOfUniversityEvents(ScheduleOfUniversityEvents scheduleOfUniversityEvents) {
        this.scheduleOfUniversityEvents = scheduleOfUniversityEvents;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Student student = (Student) o;
        return ID == student.ID && Objects.equals(name, student.name) && Objects.equals(markBook, student.markBook);
    }

    @Override
    public int hashCode() {
        return Objects.hash(ID, name, markBook);
    }
}
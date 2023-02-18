package ru.sfedu.Model;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlTransient;
import java.io.File;
import java.util.ArrayList;
import java.util.Objects;
@XmlAccessorType(XmlAccessType.FIELD)
public class Student {
    @XmlTransient
    private static final Logger log = LogManager.getLogger(Discipline.class);
    private int ID;
    private String name ;
    private MarkBook markBook = new MarkBook();
    @XmlTransient
    private ScheduleOfLessons scheduleOfLessons = new ScheduleOfLessons();
    @XmlTransient

    private ScheduleOfExams scheduleOfExams = new ScheduleOfExams();
    @XmlTransient

    private ScheduleOfUniversityEvents scheduleOfUniversityEvents = new ScheduleOfUniversityEvents();
    public Student(){this.name = "Null Null  Null";}
    public Student(int ID, String name){
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
    public void publishHomeWork(Discipline discipline, File file, String information){
        discipline.getIssue().getTaskIssue().get(information).put(this,file);
        log.info("Home work "+"'"+information+"'"+" of"+this.getName()+"was published");
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

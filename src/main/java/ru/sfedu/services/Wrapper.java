package ru.sfedu.services;

import ru.sfedu.Model.*;

import javax.xml.bind.annotation.*;
import java.util.ArrayList;
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
@XmlSeeAlso({EducationalMaterial.class, Lection.class, PracticalTask.class, Exam.class, Lesson.class, MarkBook.class,PracticalTask.class,ScheduleOfExams.class,
ScheduleOfLessons.class,ScheduleOfUniversityEvents.class, Student.class, StudentGroup.class, Teacher.class, Issue.class, Discipline.class})
public class Wrapper <T>{
    @XmlElement
    @XmlElementWrapper
    private ArrayList<T> beans = new ArrayList<>();
    Wrapper(){}

    public ArrayList<T> getBeans() {
        return beans;
    }

    public void setBeans(ArrayList<T> beans) {
        this.beans = beans;
    }
}

package ru.sfedu.Model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import java.util.ArrayList;
import java.util.Objects;
@XmlAccessorType(XmlAccessType.FIELD)
public class ScheduleOfExams extends Schedule {
    private ArrayList<Exam> exams = new ArrayList<>();
    public ScheduleOfExams(){}
    public ScheduleOfExams(int ID,int semester,ArrayList<Exam> exams){
        this.ID = ID;
        this.semester = semester;
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
        return Objects.equals(exams, that.exams) && ID == that.ID;
    }

    @Override
    public int hashCode() {
        return Objects.hash(exams);
    }
}

package ru.sfedu.Model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import java.util.Date;
import java.util.Objects;
@XmlAccessorType(XmlAccessType.FIELD)
public class Exam extends Event{
    private String nameOfDiscipline ;
    private String type;
    private String nameOfTeacher;
    public Exam(){
        this.nameOfDiscipline = this.nameOfTeacher = this.type = this.place = "Null";
    }
    public Exam(int ID, String place, Date time, String nameOfDiscipline , String type , String nameOfTeacher){
        this.ID = ID;
        this.place = place;
        this.time = time;
        this.nameOfDiscipline = nameOfDiscipline;
        this.type = type;
        this.nameOfTeacher = nameOfTeacher;
    }

    public String getNameOfDiscipline() {
        return nameOfDiscipline;
    }

    public void setNameOfDiscipline(String nameOfDiscipline) {
        this.nameOfDiscipline = nameOfDiscipline;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getNameOfTeacher() {
        return nameOfTeacher;
    }

    public void setNameOfTeacher(String nameOfTeacher) {
        this.nameOfTeacher = nameOfTeacher;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Exam exam = (Exam) o;
        return Objects.equals(nameOfDiscipline, exam.nameOfDiscipline) && Objects.equals(type, exam.type) && Objects.equals(nameOfTeacher, exam.nameOfTeacher);
    }

    @Override
    public int hashCode() {
        return Objects.hash( nameOfDiscipline, type, nameOfTeacher);
    }
}

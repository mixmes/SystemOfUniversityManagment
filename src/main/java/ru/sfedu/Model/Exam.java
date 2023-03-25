package ru.sfedu.Model;

import ru.sfedu.Constants;

import java.sql.Time;
import java.util.Date;
import java.util.Objects;

public class Exam extends Event{
    private String nameOfDiscipline ;
    private String type;
    private String nameOfTeacher;
    public Exam() {
    }
    public Exam(int ID, int scheduleId, String place, int hours, int minutes, Constants.DayOfWeek day, String nameOfDiscipline , String type , String nameOfTeacher){
        super(ID,scheduleId,place,hours,minutes,day);
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

package ru.sfedu.Model;

import ru.sfedu.Constants;

import java.util.Date;
import java.util.Objects;

public class Lesson extends Event{
    private String nameOfDiscipline;
    private String type;
    private String nameOfTeacher;
    public Lesson(){
        this.nameOfDiscipline = this.nameOfTeacher = this.type = "Null";
    }
    public Lesson(int ID, int scheduleId, String place, int hours, int minutes, Constants.DayOfWeek day, String nameOfDiscipline, String type , String nameOfTeacher){
        super(ID,scheduleId,place,hours,minutes,day);
        this.nameOfDiscipline = nameOfDiscipline;
        this.nameOfTeacher = nameOfTeacher;
        this.type = type;
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
        Lesson lesson = (Lesson) o;
        return Objects.equals(nameOfDiscipline, lesson.nameOfDiscipline) && Objects.equals(type, lesson.type) && Objects.equals(nameOfTeacher, lesson.nameOfTeacher);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nameOfDiscipline, type, nameOfTeacher);
    }
}

package ru.sfedu.Model;

import java.util.Objects;

public class Exam extends Event{
    private String nameOfDiscipline ;
    private String type;
    private String nameOfTeacher;
    Exam(){
        this.nameOfDiscipline = this.nameOfTeacher = this.type ="Null";
    }
    Exam(String nameOfDiscipline , String type , String nameOfTeacher){
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

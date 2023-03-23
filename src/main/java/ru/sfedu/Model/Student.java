package ru.sfedu.Model;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.Objects;

public class Student {
    private static final Logger log = LogManager.getLogger(Discipline.class);
    private int ID;
    private String name ;
    private ArrayList<StudentWork> studentWorks = new ArrayList<>();
    private int studentGroupId ;
    public Student(){this.name = "Null Null  Null";}
    public Student(int ID, String name,int studentGroupId){
        this.ID = ID;
        this.name = name;
        this.studentGroupId = studentGroupId;
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


    public ArrayList<StudentWork> getStudentWorks() {
        return studentWorks;
    }

    public void setStudentWorks(ArrayList<StudentWork> studentWorks) {
        this.studentWorks = studentWorks;
    }

    public int getStudentGroupId() {
        return studentGroupId;
    }

    public void setStudentGroupId(int studentGroupId) {
        this.studentGroupId = studentGroupId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Student student = (Student) o;
        return ID == student.ID && Objects.equals(name, student.name) ;
    }

    @Override
    public int hashCode() {
        return Objects.hash(ID, name);
    }
}

package ru.sfedu.Model;

import java.util.ArrayList;
import java.util.Objects;

public class StudentGroup {
    private int ID;
    private int course;
    private String name;
    private String codeOfGroup;
    ArrayList<Student> groupComposition = new ArrayList<>();
    StudentGroup(){
        this.name = "Null";
        this.codeOfGroup = "XXXXXXXXX";
    }
    StudentGroup(int ID,int course, String name, String codeOfGroup){
        this.ID = ID;
        this.course = course;
        this.name = name;
        this.codeOfGroup = codeOfGroup;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public int getCourse() {
        return course;
    }

    public void setCourse(int course) {
        this.course = course;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCodeOfGroup() {
        return codeOfGroup;
    }

    public void setCodeOfGroup(String codeOfGroup) {
        this.codeOfGroup = codeOfGroup;
    }

    public ArrayList<Student> getGroupComposition() {
        return groupComposition;
    }

    public void setGroupComposition(ArrayList<Student> groupComposition) {
        this.groupComposition = groupComposition;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StudentGroup that = (StudentGroup) o;
        return ID == that.ID && course == that.course && Objects.equals(name, that.name) && Objects.equals(codeOfGroup, that.codeOfGroup);
    }

    @Override
    public int hashCode() {
        return Objects.hash(ID, course, name, codeOfGroup);
    }
}

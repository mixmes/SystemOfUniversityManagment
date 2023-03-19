package ru.sfedu.Model;

import java.util.Objects;
public class Discipline {
    private int ID;
    private int teacherID;
    private String name;
    private String typeOfMarking;

    private EducationalMaterial educationalMaterial = new EducationalMaterial();
    public Discipline(){
        this.name = "Null";
        this.typeOfMarking = "Null";
    }
    public Discipline(int id,int teacherID , String name , String typeOfMarking){
        this.ID = id;
        this.teacherID = teacherID;
        this.name =  name;
        this.typeOfMarking = typeOfMarking;
    }

    public int getID() {
        return ID;
    }

    public void setID(int id) {
        this.ID = id;
    }

    public int getTeacherID() {
        return teacherID;
    }

    public void setTeacherID(int teacherID) {
        this.teacherID = teacherID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTypeOfMarking() {
        return typeOfMarking;
    }

    public void setTypeOfMarking(String typeOfMarking) {
        this.typeOfMarking = typeOfMarking;
    }


    public EducationalMaterial getEducationalMaterial() {
        return educationalMaterial;
    }

    public void setEducationalMaterial(EducationalMaterial educationalMatreial) {
        this.educationalMaterial = educationalMatreial;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Discipline that = (Discipline) o;
        return ID == that.ID && Objects.equals(name, that.name) && Objects.equals(typeOfMarking, that.typeOfMarking);
    }

    @Override
    public int hashCode() {
        return Objects.hash(ID, name, typeOfMarking);
    }
}

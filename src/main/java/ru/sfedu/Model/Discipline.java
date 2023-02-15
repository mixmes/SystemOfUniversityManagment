package ru.sfedu.Model;

import java.util.Objects;

public class Discipline {
    private int ID;
    private String name;
    private String typeOfMarking;
    private Issue issue =  new Issue();
    private EducationalMaterial educationalMaterial = new EducationalMaterial();
    Discipline(){
        this.name = "Null";
        this.typeOfMarking = "Null";
    }
    Discipline(int id, String name , String typeOfMarking){
        this.ID = id;
        this.name =  name;
        this.typeOfMarking = typeOfMarking;
    }

    public int getID() {
        return ID;
    }

    public void setID(int id) {
        this.ID = id;
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

    public Issue getIssue() {
        return issue;
    }

    public void setIssue(Issue issue) {
        this.issue = issue;
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
        return ID == that.ID && Objects.equals(name, that.name) && Objects.equals(typeOfMarking, that.typeOfMarking) && Objects.equals(issue, that.issue) && Objects.equals(educationalMaterial, that.educationalMaterial);
    }

    @Override
    public int hashCode() {
        return Objects.hash(ID, name, typeOfMarking, issue, educationalMaterial);
    }
}

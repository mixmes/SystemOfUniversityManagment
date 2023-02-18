package ru.sfedu.Model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import java.util.ArrayList;
import java.util.Objects;
@XmlAccessorType(XmlAccessType.FIELD)
public class EducationalMaterial {
    @XmlElement(name = "ID")
    private int ID;
    @XmlElement(name = "disciplineID")
    private int disciplineID;
    @XmlElement(name = "lections")
    private ArrayList<Lection> lections = new ArrayList<>();
    @XmlElement(name = "tasks")
    private ArrayList<PracticalTask> tasks = new ArrayList<>();
    public EducationalMaterial(){}
    public EducationalMaterial(int ID,int disciplineID ){
        this.ID = ID;
        this.disciplineID = ID;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public int getDisciplineID() {
        return disciplineID;
    }

    public void setDisciplineID(int disciplineID) {
        this.disciplineID = disciplineID;
    }

    public ArrayList<Lection> getLections() {
        return lections;
    }

    public void setLections(ArrayList<Lection> lections) {
        this.lections = lections;
    }

    public ArrayList<PracticalTask> getTasks() {
        return tasks;
    }

    public void setTasks(ArrayList<PracticalTask> tasks) {
        this.tasks = tasks;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EducationalMaterial that = (EducationalMaterial) o;
        return ID == that.ID && disciplineID == that.disciplineID && Objects.equals(lections, that.lections) && Objects.equals(tasks, that.tasks);
    }

    @Override
    public int hashCode() {
        return Objects.hash(ID, disciplineID, lections, tasks);
    }
}

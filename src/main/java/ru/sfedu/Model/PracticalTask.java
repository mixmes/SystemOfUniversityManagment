package ru.sfedu.Model;


import java.io.File;
import java.util.Objects;
public class PracticalTask {
    private int ID;
    private int educationMaterialID;
    private String information;
    private File task;
    public PracticalTask(){this.information = "Null";}
    public PracticalTask(int ID,int educationMaterialID,String URI, String information){
        this.educationMaterialID = educationMaterialID;
        this.ID = ID;
        this.task = new File(URI);
        this.information = information;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public int getEducationMaterialID() {
        return educationMaterialID;
    }

    public void setEducationMaterialID(int educationMaterialID) {
        this.educationMaterialID = educationMaterialID;
    }

    public File getTask() {
        return task;
    }

    public void setTask(File task) {
        this.task = task;
    }

    public String getInformation() {
        return information;
    }

    public void setInformation(String information) {
        this.information = information;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PracticalTask that = (PracticalTask) o;
        return ID == that.ID && Objects.equals(task, that.task);
    }

    @Override
    public int hashCode() {
        return Objects.hash(ID, task);
    }
}

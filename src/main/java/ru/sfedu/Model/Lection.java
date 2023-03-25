package ru.sfedu.Model;

import java.io.File;
import java.util.Objects;
public class Lection {
    private int ID;
    private int educationMaterialID;
    private String information;
    private File lection ;
    public Lection(){}
    public Lection(int ID,int educationMaterialID,String URI,String information){
        this.ID = ID;
        this.educationMaterialID = educationMaterialID;
        this.lection = new File(URI);
        this.information = information;
    }

    public int getEducationMaterialID() {
        return educationMaterialID;
    }

    public void setEducationMaterialID(int educationMaterialID) {
        this.educationMaterialID = educationMaterialID;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public File getLection() {
        return lection;
    }

    public void setLection(File lection) {
        this.lection = lection;
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
        Lection lection1 = (Lection) o;
        return ID == lection1.ID && Objects.equals(lection, lection1.lection);
    }

    @Override
    public int hashCode() {
        return Objects.hash(ID, lection);
    }
}

package ru.sfedu.Model;

import java.io.File;
import java.util.Objects;

public class Lection {
    private int ID;
    private File lection ;
    Lection(){}
    Lection(int ID,String URI){
        this.ID = ID;
        this.lection = new File(URI);
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
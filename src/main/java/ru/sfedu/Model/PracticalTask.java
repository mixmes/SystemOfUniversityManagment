package ru.sfedu.Model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import java.io.File;
import java.util.Objects;
@XmlAccessorType(XmlAccessType.FIELD)
public class PracticalTask {
    private int ID;
    private String information;
    private File task;
    public PracticalTask(){this.information = "Null";}
    public PracticalTask(int ID,String URI, String information){
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

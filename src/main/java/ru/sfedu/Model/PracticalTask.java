package ru.sfedu.Model;

import java.io.File;
import java.util.Objects;

public class PracticalTask {
    private int ID;
    private File task;
    PracticalTask(){}
    PracticalTask(int ID,String URI){
        this.ID = ID;
        this.task = new File(URI);
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

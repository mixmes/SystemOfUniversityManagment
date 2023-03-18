package ru.sfedu.Model;

import java.util.Objects;

public class Schedule  {
    protected int ID;
    protected int semester;
    Schedule(){}
    Schedule(int ID, int semester){
        this.ID = ID;
        this.semester = semester;
    }



    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public int getSemester() {
        return semester;
    }

    public void setSemester(int semester) {
        this.semester = semester;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Schedule schedule = (Schedule) o;
        return ID == schedule.ID && semester == schedule.semester;
    }

    @Override
    public int hashCode() {
        return Objects.hash(ID, semester);
    }
}

package ru.sfedu.Model;

import java.util.Objects;
public class UniversityEvent extends Event {
    private String information;
    public UniversityEvent(){
        this.information = "Null";
    }
    public UniversityEvent(int ID, String place, String time, String information){
        this.ID = ID;
        this.place = place;
        this.time = time;
        this.information = information;
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
        UniversityEvent that = (UniversityEvent) o;
        return Objects.equals(information, that.information);
    }

    @Override
    public int hashCode() {
        return Objects.hash(information);
    }
}

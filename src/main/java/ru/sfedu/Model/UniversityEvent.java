package ru.sfedu.Model;

import java.util.Objects;

public class UniversityEvent extends Event {
    private String information;
    UniversityEvent(){
        this.information = "Null";
    }
    UniversityEvent(String information){
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

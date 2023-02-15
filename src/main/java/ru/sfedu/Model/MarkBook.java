package ru.sfedu.Model;

import java.util.HashMap;
import java.util.Objects;

public class MarkBook {
    private int ID;
    private int studentID;
    HashMap<String,Integer> totalMark = new HashMap<>();
    HashMap<String,HashMap<String,Integer>> points = new HashMap<>();
    MarkBook(){
    }
    MarkBook(int ID, int studentID){
        this.ID = ID;
        this.studentID = studentID;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public int getStudentID() {
        return studentID;
    }

    public void setStudentID(int studentID) {
        this.studentID = studentID;
    }

    public HashMap<String, Integer> getTotalMark() {
        return totalMark;
    }

    public void setTotalMark(HashMap<String, Integer> totalMark) {
        this.totalMark = totalMark;
    }

    public HashMap<String, HashMap<String, Integer>> getPoints() {
        return points;
    }

    public void setPoints(HashMap<String, HashMap<String, Integer>> points) {
        this.points = points;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MarkBook markBook = (MarkBook) o;
        return ID == markBook.ID && studentID == markBook.studentID;
    }

    @Override
    public int hashCode() {
        return Objects.hash(ID, studentID);
    }
}

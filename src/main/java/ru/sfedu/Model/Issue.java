package ru.sfedu.Model;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Objects;

public class Issue {
    private int ID;
    private int disciplineID;
    private HashMap<String, HashMap<Student,File>> taskIssue = new HashMap<>();
    private HashMap<Date, ArrayList<String>> attendance = new HashMap<>();
    Issue(){}
    Issue(int ID, int disciplineID){
        this.ID = ID;
        this.disciplineID = disciplineID;
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

    public HashMap<String, HashMap<Student, File>> getTaskIssue() {
        return taskIssue;
    }

    public void setTaskIssue(HashMap<String, HashMap<Student, File>> taskIssue) {
        this.taskIssue = taskIssue;
    }

    public HashMap<Date, ArrayList<String>> getAttendance() {
        return attendance;
    }

    public void setAttendance(HashMap<Date, ArrayList<String>> attendance) {
        this.attendance = attendance;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Issue issue = (Issue) o;
        return ID == issue.ID && disciplineID == issue.disciplineID && Objects.equals(taskIssue, issue.taskIssue) && Objects.equals(attendance, issue.attendance);
    }

    @Override
    public int hashCode() {
        return Objects.hash(ID, disciplineID, taskIssue, attendance);
    }
}

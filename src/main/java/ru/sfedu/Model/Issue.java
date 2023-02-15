package ru.sfedu.Model;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

public class Issue {
    private int ID;
    private int disciplineID;
    private HashMap<String, ArrayList<File>> taskIssue = new HashMap<>();
    private HashMap<String, ArrayList<String>> attendance = new HashMap<>();
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

    public HashMap<String, ArrayList<File>> getTaskIssue() {
        return taskIssue;
    }

    public void setTaskIssue(HashMap<String, ArrayList<File>> taskIssue) {
        this.taskIssue = taskIssue;
    }

    public HashMap<String, ArrayList<String>> getAttendance() {
        return attendance;
    }

    public void setAttendance(HashMap<String, ArrayList<String>> attendance) {
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

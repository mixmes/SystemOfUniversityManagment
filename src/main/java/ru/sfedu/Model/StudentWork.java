package ru.sfedu.Model;

import java.io.File;

public class StudentWork {
    private int ID ;
    private int studentID;
    private String nameOfWork = new String();
    private String discipline = new String();
    private int mark ;
    private boolean homework;
    private File fileOfWork ;

    public int getStudentID() {
        return studentID;
    }

    public void setStudentID(int studentID) {
        this.studentID = studentID;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getNameOfWork() {
        return nameOfWork;
    }

    public void setNameOfWork(String nameOfWork) {
        this.nameOfWork = nameOfWork;
    }

    public String getDiscipline() {
        return discipline;
    }

    public void setDiscipline(String discipline) {
        this.discipline = discipline;
    }

    public int getMark() {
        return mark;
    }

    public void setMark(int mark) {
        this.mark = mark;
    }

    public boolean isHomework() {
        return homework;
    }

    public void setHomework(boolean homework) {
        this.homework = homework;
    }

    public File getFileOfWork() {
        return fileOfWork;
    }

    public void setFileOfWork(File fileOfWork) {
        this.fileOfWork = fileOfWork;
    }
}

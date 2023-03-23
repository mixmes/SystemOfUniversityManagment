package ru.sfedu.Model;

import java.io.File;
import java.util.Objects;

public class StudentWork {
    private int ID ;
    private int studentID;
    private String nameOfWork = new String();
    private String discipline = new String();
    private int mark ;
    private boolean homework;
    private File fileOfWork ;

    public StudentWork() {
    }

    public StudentWork(int ID, int studentID) {
        this.ID = ID;
        this.studentID = studentID;
    }

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StudentWork that = (StudentWork) o;
        return ID == that.ID && studentID == that.studentID && mark == that.mark && homework == that.homework && Objects.equals(nameOfWork, that.nameOfWork) && Objects.equals(discipline, that.discipline);
    }

    @Override
    public int hashCode() {
        return Objects.hash(ID, studentID, nameOfWork, discipline, mark, homework);
    }
}

package ru.sfedu.Model;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Objects;

import static ru.sfedu.Constants.MAX_MARK;

public class Teacher {
    private static final Logger log = LogManager.getLogger(Discipline.class);
    private int ID;
    private String name;
    private ArrayList<Discipline> disciplines = new ArrayList<>();
    public Teacher(){
        this.name = "Null Null Null";
    }
    public Teacher(int ID, String name){
        this.ID = ID;
        this.name = name;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<Discipline> getDisciplines() {
        return disciplines;
    }

    public void setDisciplines(ArrayList<Discipline> disciplines) {
        this.disciplines = disciplines;
    }
    public void checkMaxMark(int mark) throws Exception {
        if(mark>MAX_MARK){
            log.error("Max mark exceeding");
            throw new Exception("Mark exceed max mark");
        }
    }
    public void estimateStudentWork(Student student,String nameOfWork,Discipline discipline,int mark) throws Exception {
        checkHavingDiscipline(discipline);
        student.checkHavingDiscipline(discipline);
        student.checkHavingStudentWork(nameOfWork);
        checkMaxMark(mark);
        student.getStudentWorks().stream().filter(s->s.getNameOfWork() == nameOfWork).findFirst().get().setMark(mark);
    }
    public StudentWork retrieveStudentWork(Student student, String nameOfWork,Discipline discipline) throws Exception {
        checkHavingDiscipline(discipline);
        student.checkHavingDiscipline(discipline);
        student.checkHavingStudentWork(nameOfWork);
        return student.getStudentWorks().stream().filter(s->s.getNameOfWork() == nameOfWork).findFirst().get();
    }
    public void createEducationMaterial(Discipline discipline) throws Exception {
        checkHavingDiscipline(discipline);
        this.getDisciplines().stream().filter(s->s.equals(discipline)).findFirst().get().setEducationalMaterial(new EducationalMaterial());
    }
    public void addPracticalTask(Discipline discipline,PracticalTask practicalTask) throws Exception {
        checkHavingDiscipline(discipline);
        if(discipline.getEducationalMaterial().getTasks().stream().noneMatch(s->s.equals(practicalTask))){
            discipline.getEducationalMaterial().getTasks().add(practicalTask);
            log.info("Practical task was added");
        }
        else {
            discipline.getEducationalMaterial().getTasks().stream().filter(s->s.equals(practicalTask)).forEach(s->s = practicalTask);
            log.info("Practical task was updated");
        }
    }
    public void deletePracticalTask(Discipline discipline , PracticalTask practicalTask) throws Exception {
        checkHavingDiscipline(discipline);
        if(discipline.getEducationalMaterial().getTasks().stream().noneMatch(s->s.equals(practicalTask))){
            log.error("No such task");
            throw new Exception("No such practical task");
        }
        else {
            discipline.getEducationalMaterial().getTasks().remove(practicalTask);
            log.info("Practical task was deleted");
        }
    }
    public void addLection(Discipline discipline,Lection lection) throws Exception {
        checkHavingDiscipline(discipline);
        if(discipline.getEducationalMaterial().getLections().stream().noneMatch(s->s.equals(lection))){
            discipline.getEducationalMaterial().getLections().add(lection);
            log.info("Lection was added");
        }
        else {
            discipline.getEducationalMaterial().getLections().stream().filter(s->s.equals(lection)).forEach(s->s =lection);
            log.info("Lection was updated");
        }
    }
    public void deleteLection(Discipline discipline, Lection lection) throws Exception {
        checkHavingDiscipline(discipline);
        if(discipline.getEducationalMaterial().getLections().stream().noneMatch(s->s.equals(lection))){
            log.error("No such lection");
            throw new Exception("No such lection");
        }
        else {
            discipline.getEducationalMaterial().getLections().remove(lection);
            log.info("Lection was deleted");
        }
    }
    public void checkHavingDiscipline(Discipline discipline) throws Exception {
        if(this.getDisciplines().stream().noneMatch(s->s.equals(discipline))){
            log.error("No such discipline");
            throw new Exception("Teacher doesn't have such discipline");
        }
    }



    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Teacher teacher = (Teacher) o;
        return ID == teacher.ID && Objects.equals(name, teacher.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(ID, name);
    }
}

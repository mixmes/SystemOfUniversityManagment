package ru.sfedu.Model;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.util.ArrayList;
import java.util.Objects;

public class Teacher {
    private static final Logger log = LogManager.getLogger(Discipline.class);
    private int ID;
    private String name;
    private ArrayList<Discipline> disciplines = new ArrayList<>();
    Teacher(){
        this.name = "Null Null Null";
    }
    Teacher(int ID, String name){
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
    public void addLectionFile(Discipline discipline, Lection lection){
        discipline.getEducationalMaterial().getLections().add(lection);
        log.info("Lection file was published");
    }
    public void addTaskFile(Discipline discipline, PracticalTask practicalTask){
        discipline.getEducationalMaterial().getTasks().add(practicalTask);
        log.info("Practical task file was published");
    }
   public File getHomeWork(Discipline discipline,Student student,String information) throws Exception {
        if(discipline.getIssue().getTaskIssue().containsKey(information)){
            log.info("Home work "+"'"+information+"'"+" of"+student.getName()+" was found");
            return discipline.getIssue().getTaskIssue().get(information).get(student);
        }
        log.info("Home work "+"'"+information+"'"+" of"+student.getName()+" wasn't found");
        throw new Exception("Home work "+"'"+information+"'"+" of"+student.getName()+" wasn't found");
   }
   public void estimateHomeWork(Discipline discipline,Student student,int points,String information){
        student.getMarkBook().getTotalMark().put(discipline.getName()
                ,student.getMarkBook().getTotalMark().get(information)+points);
        student.getMarkBook().getPoints().get(discipline.getName()).put(information,points);
        log.info("Home work "+"'"+information+"'"+" of"+student.getName()+" was estimated");
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

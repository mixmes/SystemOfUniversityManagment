package ru.sfedu.Model;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlTransient;
import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Objects;
@XmlAccessorType(XmlAccessType.FIELD)
public class Teacher {
    @XmlTransient
    private static final Logger log = LogManager.getLogger(Discipline.class);
    private int ID;
    private String name;
    @XmlElementWrapper
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
    public void addLectionFile(Discipline discipline, Lection lection){
        discipline.getEducationalMaterial().getLections().add(lection);
        log.info("Lection file was published");
    }
    public void addTaskFile(Discipline discipline, PracticalTask practicalTask){
        discipline.getEducationalMaterial().getTasks().add(practicalTask);
        log.info("Practical task file was published");
        discipline.getIssue().getTaskIssue().put(practicalTask.getInformation(), new HashMap<>()); //Чтобы студенты могли публиковать дз
    }
   public File getHomeWork(Discipline discipline,Student student,String information) throws Exception {
        if(discipline.getIssue().getTaskIssue().get(information).containsKey(student)){
            System.out.println("1");
            log.info("Home work "+"'"+information+"'"+" of "+student.getName()+" was found");
            return discipline.getIssue().getTaskIssue().get(information).get(student);
        }
        System.out.println("0");
        log.info("Home work "+"'"+information+"'"+" of "+student.getName()+" wasn't found");
        throw new Exception("Home work "+"'"+information+"'"+" of "+student.getName()+" wasn't found");
   }
   public void estimateHomeWork(Discipline discipline,Student student,int points,String information){
        int totalMark = student.getMarkBook().getTotalMark().get(discipline.getName())+points;
        student.getMarkBook().getTotalMark().put(discipline.getName(),totalMark);
        student.getMarkBook().getPoints().get(discipline.getName()).put(information,points);
        log.info("Home work "+"'"+information+"'"+" of "+student.getName()+" was estimated");
   }
   public void setDailyAttendance(Date date, Discipline discipline,ArrayList<String> students){
        discipline.getIssue().getAttendance().put(date,students);
        log.info("Attendance Issue was filled");
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

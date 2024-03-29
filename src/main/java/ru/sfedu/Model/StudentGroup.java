package ru.sfedu.Model;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Optional;

public class StudentGroup {
    private static final Logger log =LogManager.getLogger(Discipline.class);
    private int ID;
    private int course;
    private String name;
    private String codeOfGroup;
    ArrayList<Student> groupComposition = new ArrayList<>();

    public StudentGroup(){
    }
    public StudentGroup(int ID,int course, String name, String codeOfGroup){
        this.ID = ID;
        this.course = course;
        this.name = name;
        this.codeOfGroup = codeOfGroup;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public int getCourse() {
        return course;
    }

    public void setCourse(int course) {
        this.course = course;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCodeOfGroup() {
        return codeOfGroup;
    }

    public void setCodeOfGroup(String codeOfGroup) {
        this.codeOfGroup = codeOfGroup;
    }

    public ArrayList<Student> getGroupComposition() {
        return groupComposition;
    }

    public void setGroupComposition(ArrayList<Student> groupComposition) {
        this.groupComposition = groupComposition;
    }
    public void addStudentToGroup(Student student) throws Exception {
        if(groupComposition.stream().noneMatch(s->s.equals(student))){
            groupComposition.add(student);
            log.info("Student was added to group");
        }
        else {
            log.error("Student already in this group");
            throw new Exception("Student already in this group");
        }

    }
    public void deleteStudentFromGroup(Student student) throws Exception {
        if(groupComposition.stream().anyMatch(s->s.equals(student))){
            groupComposition.remove(student);
            log.info("Student was deleted from group");
        }
        else {
            log.error("Student doesn't exists");
            throw new Exception("Student doesn't exist in this group");
        }
    }
    public Student getStudentById(int id) throws Exception {
        Optional<Student> student = groupComposition.stream().filter(s->s.getID() == id).findFirst();
        if(!student.isPresent()){
            throw new Exception("Student doesn't exist");
        }
        log.info("Student was obtained");
        return student.get();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StudentGroup that = (StudentGroup) o;
        return ID == that.ID && course == that.course && Objects.equals(name, that.name) && Objects.equals(codeOfGroup, that.codeOfGroup);
    }

    @Override
    public int hashCode() {
        return Objects.hash(ID, course, name, codeOfGroup);
    }
}

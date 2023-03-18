package ru.sfedu.Model;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Objects;
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
    public void addLectionFile(Discipline discipline, Lection lection){
        discipline.getEducationalMaterial().getLections().add(lection);
        log.info("Lection file was published");
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

package ru.sfedu.Model;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

class TeacherTest {
    static private Teacher teacher = new Teacher();
    static private Discipline discipline = new Discipline();
    static private String nameOfTask = new String("Lab1");
    static private File homeWork = new File("/home/mihail/FilesForSystem/PracticalTask");
    static private Student student1 = new Student();
    static private Student student2 = new Student();

    @BeforeAll
    static void init() {
        discipline.setName("Math");

        HashMap<String, Integer> tempTotal = new HashMap<>();
        tempTotal.put(discipline.getName(), 0);
        HashMap<String, HashMap<String, Integer>> tempPoint = new HashMap<>();
        tempPoint.put(discipline.getName(), new HashMap<>());
        tempPoint.get(discipline.getName()).put(nameOfTask, 0);


    }




}
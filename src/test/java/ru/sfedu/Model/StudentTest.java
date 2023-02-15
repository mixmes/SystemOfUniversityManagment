package ru.sfedu.Model;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

class StudentTest {
    private Student student =  new Student();
    private Discipline discipline = new Discipline();
    private String nameOfTask = new String("Lab1");
    private File homeWork = new File("/home/mihail/FilesForSystem/PracticalTask");
    @Test
    void publishHomeWork() {
        discipline.getIssue().getTaskIssue().put(nameOfTask,new HashMap<>());
        student.publishHomeWork(discipline,homeWork,nameOfTask);
        assertEquals(homeWork,discipline.getIssue().getTaskIssue().get(nameOfTask).get(student));
    }
}
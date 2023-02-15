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
    static private MarkBook markBook = new MarkBook();
    @BeforeAll
    static void init(){
        Issue issue = new Issue();
        issue.getTaskIssue().put(nameOfTask,new HashMap<Student,File>());
        discipline.setName("Math");
        discipline.setIssue(issue);

        HashMap<String,Integer> tempTotal = new HashMap<>();
        tempTotal.put(discipline.getName(),0);
        HashMap<String,HashMap<String,Integer>> tempPoint = new HashMap<>();
        tempPoint.put(discipline.getName(),new HashMap<>());
        tempPoint.get(discipline.getName()).put(nameOfTask,0);
        markBook.setTotalMark(tempTotal);
        markBook.setPoints(tempPoint);
        student1.setMarkBook(markBook);

    }

    @Test
    void getHomeWork() throws Exception {
        discipline.getIssue().getTaskIssue().get(nameOfTask).put(student1,homeWork);
        assertEquals(homeWork,teacher.getHomeWork(discipline,student1,nameOfTask));

    }
    @Test
    void getNonExistingHomeWork()
    {
        Exception exception = assertThrows(Exception.class,()->{
            teacher.getHomeWork(discipline,new Student(),nameOfTask);});
        assertEquals("Home work "+"'"+nameOfTask+"'"+" of "+student1.getName()+" wasn't found",exception.getMessage());
    }

    @Test
    void estimateHomeWork() {
        teacher.estimateHomeWork(discipline,student1,5,nameOfTask);
        assertEquals(5,student1.getMarkBook().getTotalMark().get(discipline.getName()));
        assertEquals(5,student1.getMarkBook().getPoints().get(discipline.getName()).get(nameOfTask));
    }
}
package ru.sfedu.Model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StudentGroupTest {
    private static StudentGroup studentGroup = new StudentGroup(1,1,"Math","12.01.11");
    private static Student student  = new Student(1,"Smernikov Mihail",1);

    @Test
    void addStudentToGroup() throws Exception {
        studentGroup.addStudentToGroup(student);
        assertEquals(student,studentGroup.getStudentById(student.getID()));
    }
    @Test
    void addExistingStudentToGroup() throws Exception {
        studentGroup.addStudentToGroup(student);

        Exception exception = assertThrows(Exception.class,()->{
            studentGroup.addStudentToGroup(student);
        });
        assertEquals("Student already in this group",exception.getMessage());
    }

    @Test
    void deleteStudentFromGroup() throws Exception {
        studentGroup.addStudentToGroup(student);
        studentGroup.deleteStudentFromGroup(student);

        Exception exception = assertThrows(Exception.class,()->{
            studentGroup.getStudentById(student.getID());
        });
        assertEquals("Student doesn't exist",exception.getMessage());
    }
    @Test
    void deleteNonExistingStudentFromGroup(){
        Exception exception = assertThrows(Exception.class,()->{
            studentGroup.deleteStudentFromGroup(new Student());
        });
        assertEquals("Student doesn't exist in this group",exception.getMessage());
    }
}
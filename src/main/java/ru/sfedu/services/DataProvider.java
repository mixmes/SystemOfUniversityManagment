package ru.sfedu.services;

import ru.sfedu.Model.*;

import java.io.IOException;
import java.sql.SQLException;


public interface DataProvider {
    public void saveDisciplineRecord(Discipline discipline) throws Exception;
    public Discipline getDiscicplineRecordById(int id) throws IOException;
    public void deleteDisciplineRecord(Discipline discipline);
    public void updateDisciplineRecord(Discipline discipline) throws IOException;
    public void saveEducationalMaterialRecord(EducationalMaterial educationalMaterial) throws Exception;
    public EducationalMaterial getEducationalMaterialRecordByID(int id) throws Exception;
    public void deleteEducationalMaterialRecord(EducationalMaterial educationalMaterial) throws IOException;
    public void updateEducationalMaterialRecord(EducationalMaterial educationalMaterial);
    public void saveExamRecord(Exam exam) throws Exception;
    public void deleteExamRecord(Exam exam) throws IOException;
    public Exam getExamRecordByID(int id) throws IOException;
    public void updateExamRecord(Exam exam) throws IOException;
    public void saveLectionRecord(Lection lection) throws Exception;
    public void deleteLectionRecord(Lection lection) throws IOException;
    public Lection getLectionRecordById(int id) throws Exception;
    public void updateLectionRecord(Lection lection) throws Exception;
    public void saveLessonRecord(Lesson lesson) throws Exception;
    public void deleteLessonRecord(Lesson lesson) throws IOException;
    public void updateLessonRecord(Lesson lesson) throws IOException;
    public Lesson getLessonRecordById(int id) throws IOException;
    public void savePracticalTaskRecord(PracticalTask practicalTask) throws Exception;
    public void deletePracticalTaskRecord(PracticalTask practicalTask) throws IOException;
    public void updatePracticalTaskRecord(PracticalTask practicalTask) throws IOException;
    public PracticalTask getPracticalTaskRecordById(int id) throws Exception;
    public void saveScheduleRecord(Schedule schedule) throws Exception;
    public void deleteScheduleRecord(Schedule schedule) throws IOException;
    public void updateScheduleRecord(Schedule schedule) throws IOException, SQLException;
    public Schedule getScheduleRecordById(int id) throws Exception;
    public void saveStudentRecord(Student student);
    public void deleteStudentRecord(Student student);
    public void updateStudentRecord(Student student);
    public Student getStudentRecordById(int id);
    public void saveStudentGroupRecord(StudentGroup studentGroup);
    public void deleteStudentGroupRecord(StudentGroup studentGroup);
    public void updateStudentGroupRecord(StudentGroup studentGroup);
    public StudentGroup getStudentGroupRecordById(int id);
    public void saveStudentWorKRecord(StudentWork studentWork) throws Exception;
    public void deleteStudentWorkRecord(StudentWork studentWork) throws IOException;
    public void updateStudentWorkRecord(StudentWork studentWork) throws IOException;
    public StudentWork getStudentWorkRecordById(int id) throws Exception;
    public void saveTeacherRecord(Teacher teacher);
    public void deleteTeacherRecord(Teacher teacher);
    public void updateTeacherRecord(Teacher teacher);
    public Teacher getTeacherRecordById(int id);
    public void saveUnEventRecord(UniversityEvent universityEvent) throws Exception;
    public void deleteUnEventRecord(UniversityEvent universityEvent) throws IOException;
    public void updateUnEventRecord(UniversityEvent universityEvent) throws IOException;
    public UniversityEvent getUnEventRecordById(int id) throws IOException;
}

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
    public void saveLessonRecord(Lesson lesson);
    public void deleteLessonRecord(Lesson lesson);
    public void updateLessonRecord(Lesson lesson);
    public Lesson getLessonRecordById(int id);
    public void savePracticalTaskRecord(PracticalTask practicalTask) throws Exception;
    public void deletePracticalTaskRecord(PracticalTask practicalTask) throws IOException;
    public void updatePracticalTaskRecord(PracticalTask practicalTask) throws IOException;
    public PracticalTask getPracticalTaskRecordById(int id) throws Exception;
    public void saveScheduleRecord(Schedule schedule);
    public void deleteScheduleRecord(Schedule schedule);
    public void updateScheduleRecord(Schedule schedule);
    public Schedule getScheduleRecordById(int id);
    public void saveStudentRecord(Student student);
    public void deleteStudentRecord(Student student);
    public void updateStudentRecord(Student student);
    public Student getStudentRecordById(int id);
    public void saveStudentGroupRecord(StudentGroup studentGroup);
    public void deleteStudentGroupRecord(StudentGroup studentGroup);
    public void updateStudentGroupRecord(StudentGroup studentGroup);
    public StudentGroup getStudentGroupRecordById(int id);
    public void saveStudentWorKRecord(StudentWork studentWork);
    public void deleteStudentWorkRecord(StudentWork studentWork);
    public void updateStudentWorkRecord(StudentWork studentWork);
    public StudentWork getStudentWorkRecordById(StudentWork studentWork);
    public void saveTeacherRecord(Teacher teacher);
    public void deleteTeacherRecord(Teacher teacher);
    public void updateTeacherRecord(Teacher teacher);
    public Teacher getTeacherRecordById(int id);
    public void saveUniversityEventRecord(UniversityEvent universityEvent);
    public void deleteUniversityEventRecord(UniversityEvent universityEvent);
    public void updateUniversityEventRecord(UniversityEvent universityEvent);
    public UniversityEvent getUniversityEventRecordById(int id);
}

package ru.sfedu.services;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import ru.sfedu.Model.EducationalMaterial;
import ru.sfedu.Model.*;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class XMLDataProviderTest {
    XMLDataProvider xmlDataProvider = new XMLDataProvider();
    static EducationalMaterial mathMaterial = new EducationalMaterial(1, 1);
    static EducationalMaterial engMaterial = new EducationalMaterial(2, 2);
    static EducationalMaterial philosophyMaterial = new EducationalMaterial(3, 3);
    static Lection mathLection = new Lection(1, "/home/mihail/FilesForSystem/Lections", "Лекция от 21.1.11");
    static Lection engLection = new Lection(2, "/home/mihail/FilesForSystem/Lections", "Лекция от 21.1.12");
    static Lection philosophyLection = new Lection(3, "/home/mihail/FilesForSystem/Lections", "Лекция от 22.1.12");
    static PracticalTask mathTask = new PracticalTask(1, "/home/mihail/FilesForSystem/PracticalTask", "Дедлайн 21.11.2022");
    static PracticalTask engTask = new PracticalTask(2, "/home/mihail/FilesForSystem/PracticalTask", "Дедлайн 21.11.2022");
    static PracticalTask philosophyTask = new PracticalTask(3, "/home/mihail/FilesForSystem/PracticalTask", "Дедлайн 21.11.2022");
    static Exam mathExam = new Exam(1, "509k", new Date(), "Math", "Экзамен", "Бобров Виктор Михайлович");
    static Exam engExam = new Exam(2, "509k", new Date(), "Eng", "Пересдача", "Казачков Дмитрий Анатольевич");
    static Exam philosophyExam = new Exam(3, "511k", new Date(), "philosophy", "Экзамен", "Сократов Платон Иванович");
    static Lesson mathLesson = new Lesson(1, "600k", new Date(), "Math", "Лекция", "Бобров Виктор Михайлович");
    static Lesson engLesson = new Lesson(2, "401k", new Date(), "English", "Практика", "Казачков Дмитрий Анатольевич");
    static Lesson philosophyLesson = new Lesson(3, "400k", new Date(), "Philosophy", "Семинар", "Сократов Платон Иванович");
    static UniversityEvent event1 = new UniversityEvent(1,"510",new Date(),"Встреча выпускников 2020");
    static UniversityEvent event2 = new UniversityEvent(2,"510",new Date(),"Встреча выпускников 2021");
    static UniversityEvent event3 = new UniversityEvent(3,"510",new Date(),"Встреча выпускников 2022");
    static MarkBook markBook1 = new MarkBook(1, 1);
    static MarkBook markBook2 = new MarkBook(2, 2);
    static MarkBook markBook3 = new MarkBook(3, 3);
    static ScheduleOfExams scheduleOfExams1 = new ScheduleOfExams(1,2,new ArrayList<Exam>(List.of(mathExam,engExam,philosophyExam)));
    static ScheduleOfExams scheduleOfExams2 = new ScheduleOfExams(2,2,new ArrayList<Exam>(List.of(mathExam,engExam,philosophyExam)));
    static ScheduleOfExams scheduleOfExams3 = new ScheduleOfExams(3,2,new ArrayList<Exam>(List.of(mathExam,engExam,philosophyExam)));
    static ScheduleOfLessons scheduleOfLessons1 = new ScheduleOfLessons(1,2,new ArrayList<Lesson>(List.of(mathLesson,engLesson,philosophyLesson)));
    static ScheduleOfLessons scheduleOfLessons2 = new ScheduleOfLessons(2,2,new ArrayList<Lesson>(List.of(mathLesson,engLesson,philosophyLesson)));
    static ScheduleOfLessons scheduleOfLessons3 = new ScheduleOfLessons(3,2,new ArrayList<Lesson>(List.of(mathLesson,engLesson,philosophyLesson)));
    static ScheduleOfUniversityEvents scheduleOfUniversityEvents1 = new ScheduleOfUniversityEvents(1,2,new ArrayList<UniversityEvent>(List.of(event1,event2,event3)));
    static ScheduleOfUniversityEvents scheduleOfUniversityEvents2 = new ScheduleOfUniversityEvents(2,2,new ArrayList<UniversityEvent>(List.of(event1,event2,event3)));
    static ScheduleOfUniversityEvents scheduleOfUniversityEvents3 = new ScheduleOfUniversityEvents(3,2,new ArrayList<UniversityEvent>(List.of(event1,event2,event3)));
    static Student mihail = new Student(1,"Смерников Михаил Викторович");
    static Student anna = new Student(2,"Овчинникова Анна Владимировна");
    static Student vasilisa = new Student(3,"Смерникова Василиса Михайловна");
    static StudentGroup  group1 = new StudentGroup(1,1,"Приборостроение","12.03.01");
    static StudentGroup  group2 = new StudentGroup(2,1,"Прикладная Информатика","12.03.05");
    static StudentGroup  group3 = new StudentGroup(3,1,"Инноватика","12.03.06");
    static Teacher mathTeacher = new Teacher(1,"Бобров Виктор Михайлович");
    static Teacher engTeacher = new Teacher(2,"Казачков Дмитрий Анатольевич");
    static Teacher philosophyTeacher = new Teacher(3,"Сократов Платон Иванович");
    static Discipline math = new Discipline(1,"Math","Зачет");
    static Discipline eng = new Discipline(2,"Eng","Экзамен");
    static Discipline philosophy = new Discipline(3,"Philosophy","Дифф зачет");
    static Issue mathIssue = new Issue(1,1);
    static Issue engIssue =  new Issue(2,2);
    static Issue philosophyIssue = new Issue(3,3);




    @BeforeAll
    static void init() {
        mathMaterial.setLections(new ArrayList<>(List.of(mathLection)));
        mathMaterial.setTasks(new ArrayList<>(List.of(mathTask)));
        engMaterial.setLections(new ArrayList<>(List.of(engLection)));
        engMaterial.setTasks(new ArrayList<>(List.of(engTask)));
        philosophyMaterial.setLections(new ArrayList<>(List.of(philosophyLection)));
        philosophyMaterial.setTasks(new ArrayList<>(List.of(philosophyTask)));

        HashMap<String, Integer> totalMark = new HashMap<>();
        totalMark.put("Math", 15);
        HashMap<String, Integer> point = new HashMap<>();
        point.put("Логарифмы", 5);
        HashMap<String, HashMap<String, Integer>> points = new HashMap<>();
        points.put("Math", point);
        markBook1.setTotalMark(totalMark);
        markBook1.setPoints(points);
        markBook2.setTotalMark(totalMark);
        markBook2.setPoints(points);
        markBook3.setTotalMark(totalMark);
        markBook3.setPoints(points);

        mihail.setMarkBook(markBook1);
        anna.setMarkBook(markBook2);
        vasilisa.setMarkBook(markBook3);

        group1.setGroupComposition(new ArrayList<Student>(List.of(mihail)));
        group2.setGroupComposition(new ArrayList<Student>(List.of(anna)));
        group3.setGroupComposition(new ArrayList<Student>(List.of(vasilisa)));

        mathTeacher.setDisciplines(new ArrayList<Discipline>(List.of(math)));
        engTeacher.setDisciplines(new ArrayList<Discipline>(List.of(eng)));
        philosophyTeacher.setDisciplines(new ArrayList<Discipline>(List.of(philosophy)));

        HashMap<Student, File> homework = new HashMap<>();
        homework.put(new Student(1,"Smernikov"),new File("/home/mihail/FilesForSystem/PracticalTask"));
        HashMap<String,HashMap<Student , File>> taskIssue = new HashMap<>();
        taskIssue.put("21.11.2020",homework);
        HashMap<Date,ArrayList<String>> attendanceOfLesson = new HashMap<>();
        attendanceOfLesson.put(new Date(),new ArrayList<>(List.of("Smernikov")));
        mathIssue.setTaskIssue(taskIssue);
        mathIssue.setAttendance(attendanceOfLesson);
        engIssue.setTaskIssue(taskIssue);
        engIssue.setAttendance(attendanceOfLesson);
        philosophyIssue.setTaskIssue(taskIssue);
        philosophyIssue.setAttendance(attendanceOfLesson);

        math.setIssue(mathIssue);
        math.setEducationalMaterial(mathMaterial);
        eng.setIssue(engIssue);
        eng.setEducationalMaterial(engMaterial);
        philosophy.setIssue(philosophyIssue);
        philosophy.setEducationalMaterial(philosophyMaterial);

    }

    @Test
    void saveEducationalMaterialRecord() throws Exception {
        xmlDataProvider.saveEducationalMaterialRecord(mathMaterial);
        assertEquals(mathMaterial, xmlDataProvider.getEducationalMaterialRecordByID(mathMaterial.getID()));
    }

    @Test
    void saveExistingEducationalMaterialRecord() throws Exception {
        xmlDataProvider.saveEducationalMaterialRecord(engMaterial);
        Exception exception = assertThrows(Exception.class, () -> {
            xmlDataProvider.saveEducationalMaterialRecord(engMaterial);
        });
        assertEquals("Record already exists", exception.getMessage());
    }

    @Test
    void deleteEducationalMaterialRecord() throws Exception {
        xmlDataProvider.saveEducationalMaterialRecord(philosophyMaterial);
        xmlDataProvider.deleteEducationalMaterialRecord(philosophyMaterial);
        Exception exception = assertThrows(Exception.class, () -> {
            xmlDataProvider.getEducationalMaterialRecordByID(philosophyMaterial.getID());
        });
        assertEquals("Educational material record wasn't found", exception.getMessage());
    }

    @Test
    void deleteNonExistingEducationalMaterialRecord() throws Exception {
        xmlDataProvider.deleteEducationalMaterialRecord(mathMaterial);
        Exception exception = assertThrows(Exception.class, () -> {
            xmlDataProvider.deleteEducationalMaterialRecord(mathMaterial);
        });
        assertEquals("Educational material record wasn't found", exception.getMessage());
    }

    @Test
    void getEducationalMaterialRecordByID() throws Exception {
        assertEquals(engMaterial, xmlDataProvider.getEducationalMaterialRecordByID(engMaterial.getID()));
    }

    @Test
    void getNonExistingEducationalRecordByID() {
        Exception exception = assertThrows(Exception.class, () -> {
            xmlDataProvider.getEducationalMaterialRecordByID(0);
        });
        assertEquals("Educational material record wasn't found", exception.getMessage());
    }

    @Test
    void saveExamRecord() throws Exception {
        xmlDataProvider.saveExamRecord(mathExam);
        assertEquals(mathExam, xmlDataProvider.getExamRecordByID(mathExam.getID()));
    }

    @Test
    void saveExistingExamRecord() throws Exception {
        xmlDataProvider.saveExamRecord(engExam);
        Exception exception = assertThrows(Exception.class, () -> {
            xmlDataProvider.saveExamRecord(engExam);
        });
        assertEquals("Exam record already exists", exception.getMessage());
    }

    @Test
    void deleteExamRecord() throws Exception {
        xmlDataProvider.saveExamRecord(philosophyExam);
        xmlDataProvider.deleteExamRecord(philosophyExam);
        Exception exception = assertThrows(Exception.class, () -> {
            xmlDataProvider.getExamRecordByID(philosophyExam.getID());
        });
        assertEquals("Exam record wasn't found", exception.getMessage());
    }

    @Test
    void deleteNonExistingExamRecord() throws Exception {
        xmlDataProvider.deleteExamRecord(mathExam);
        Exception exception = assertThrows(Exception.class, () -> {
            xmlDataProvider.deleteExamRecord(mathExam);
        });
        assertEquals("Exam record wasn't found", exception.getMessage());
    }

    @Test
    void getExamRecordByID() throws Exception {
        assertEquals(engExam, xmlDataProvider.getExamRecordByID(engExam.getID()));
    }

    @Test
    void getNonExistingExamRecordByID() {
        Exception exception = assertThrows(Exception.class, () -> {
            xmlDataProvider.getExamRecordByID(0);
        });
        assertEquals("Exam record wasn't found", exception.getMessage());
    }

    @Test
    void saveLectionRecord() throws Exception {
        xmlDataProvider.saveLectionRecord(mathLection);
        assertEquals(mathLection, xmlDataProvider.getLectionRecordByID(mathMaterial.getID()));
    }

    @Test
    void saveExistingLectionRecord() throws Exception {
        xmlDataProvider.saveLectionRecord(engLection);
        Exception exception = assertThrows(Exception.class, () -> {
            xmlDataProvider.saveLectionRecord(engLection);
        });
        assertEquals("Lection record already exists", exception.getMessage());
    }

    @Test
    void deleteLectionRecord() throws Exception {
        xmlDataProvider.saveLectionRecord(philosophyLection);
        xmlDataProvider.deleteLectionRecord(philosophyLection);
        Exception exception = assertThrows(Exception.class, () ->
                xmlDataProvider.getLectionRecordByID(philosophyLection.getID()));
        assertEquals("Lection record wasn't found", exception.getMessage());
    }

    @Test
    void deleteNonExistingLectionRecord() throws Exception {
        xmlDataProvider.deleteLectionRecord(mathLection);
        Exception exception = assertThrows(Exception.class, () -> xmlDataProvider.deleteLectionRecord(mathLection));
        assertEquals("Lection record wasn't found", exception.getMessage());
    }

    @Test
    void getLectionRecordByID() throws Exception {
        assertEquals(engLection, xmlDataProvider.getLectionRecordByID(engLection.getID()));
    }

    @Test
    void getNonExistingLectionRecordByID() {
        Exception exception = assertThrows(Exception.class, () -> xmlDataProvider.getLectionRecordByID(0));
        assertEquals("Lection record wasn't found", exception.getMessage());
    }

    @Test
    void saveLessonRecord() throws Exception {
        xmlDataProvider.saveLessonRecord(mathLesson);
        assertEquals(mathLesson, xmlDataProvider.getLessonRecordByID(mathLesson.getID()));
    }

    @Test
    void saveExistingLessonRecord() throws Exception {
        xmlDataProvider.saveLessonRecord(engLesson);
        Exception exception = assertThrows(Exception.class, () -> {
            xmlDataProvider.saveLessonRecord(engLesson);
        });
        assertEquals("Lesson record already exists", exception.getMessage());
    }

    @Test
    void deleteLessonRecord() throws Exception {
        xmlDataProvider.saveLessonRecord(philosophyLesson);
        xmlDataProvider.deleteLessonRecord(philosophyLesson);
        Exception exception = assertThrows(Exception.class, () -> {
            xmlDataProvider.deleteLessonRecord(philosophyLesson);
        });
        assertEquals("Lesson record wasn't found", exception.getMessage());
    }

    @Test
    void deleteNonExistingLessonRecord() throws Exception {
        xmlDataProvider.deleteLessonRecord(mathLesson);
        Exception exception = assertThrows(Exception.class, () -> {
            xmlDataProvider.deleteLessonRecord(mathLesson);
        });
        assertEquals("Lesson record wasn't found", exception.getMessage());
    }

    @Test
    void getLessonRecordByID() throws Exception {
        assertEquals(engLesson, xmlDataProvider.getLessonRecordByID(engLesson.getID()));
    }

    @Test
    void getNonExistingLessonRecordByID() {
        Exception exception = assertThrows(Exception.class, () -> {
            xmlDataProvider.getLessonRecordByID(0);
        });
        assertEquals("Lesson record wasn't found", exception.getMessage());
    }

    @Test
    void saveMarkBookRecord() throws Exception {
        xmlDataProvider.saveMarbookRecord(markBook1);
        assertEquals(markBook1, xmlDataProvider.getMarkBookRecordByID(markBook1.getID()));
    }

    @Test
    void saveExistingMarkBookRecord() throws Exception {
        xmlDataProvider.saveMarbookRecord(markBook2);
        Exception exception = assertThrows(Exception.class, () -> {
            xmlDataProvider.saveMarbookRecord(markBook2);
        });
        assertEquals("Markbook record already exists", exception.getMessage());
    }

    @Test
    void deleteMarkBookRecord() throws Exception {
        xmlDataProvider.saveMarbookRecord(markBook3);
        xmlDataProvider.deleteMarkBookRecord(markBook3);
        Exception exception = assertThrows(Exception.class, () -> {
            xmlDataProvider.getMarkBookRecordByID(markBook3.getID());
        });
        assertEquals("Markbook record wasn't found", exception.getMessage());
    }

    @Test
    void deleteNonExistingMarkBookRecord() throws Exception {
        xmlDataProvider.deleteMarkBookRecord(markBook1);
        Exception exception = assertThrows(Exception.class, () -> {
            xmlDataProvider.deleteMarkBookRecord(markBook1);
        });
        assertEquals("Markbook record wasn't found", exception.getMessage());
    }

    @Test
    void getMarkBookRecordByID() throws Exception {
        assertEquals(markBook2, xmlDataProvider.getMarkBookRecordByID(markBook2.getID()));
    }

    @Test
    void getNonExistingMarkBookRecordByID() {
        Exception exception = assertThrows(Exception.class, () -> {
            xmlDataProvider.getMarkBookRecordByID(0);
        });
        assertEquals("Markbook record wasn't found", exception.getMessage());
    }

    @Test
    void savePracticalTaskRecord() throws Exception {
        xmlDataProvider.savePracticalTaskRecord(mathTask);
        assertEquals(mathTask, xmlDataProvider.getPracticalTaskRecordByID(mathTask.getID()));
    }

    @Test
    void saveExistingPracticalTaskRecord() throws Exception {
        xmlDataProvider.savePracticalTaskRecord(engTask);
        Exception exception = assertThrows(Exception.class,()->{xmlDataProvider.savePracticalTaskRecord(engTask);});
        assertEquals("Practical task record already exists",exception.getMessage());
    }
    @Test
    void deletePracticalTaskRecord() throws Exception {
        xmlDataProvider.savePracticalTaskRecord(philosophyTask);
        xmlDataProvider.deletePracticalTaskRecord(philosophyTask);
        Exception exception = assertThrows(Exception.class,()->{
            xmlDataProvider.getPracticalTaskRecordByID(philosophyTask.getID());});
        assertEquals("Practical task record wasn't found",exception.getMessage());
    }
    @Test
    void deleteNonExistingPracticalTaskRecord() throws Exception {
        xmlDataProvider.deletePracticalTaskRecord(mathTask);
        Exception exception = assertThrows(Exception.class,()->{
            xmlDataProvider.deletePracticalTaskRecord(mathTask);
        });
        assertEquals("Practical task record wasn't found",exception.getMessage());
    }
    @Test
    void getPracticalTaskRecordByID() throws Exception {
        assertEquals(engTask,xmlDataProvider.getPracticalTaskRecordByID(engTask.getID()));
    }
    @Test
    void getNonExistingPracticalTaskRecordByID(){
        Exception exception = assertThrows(Exception.class ,()->{
            xmlDataProvider.getPracticalTaskRecordByID(0);
        });
        assertEquals("Practical task record wasn't found",exception.getMessage());
    }
    @Test
    void saveExamScheduleRecord() throws Exception {
        xmlDataProvider.saveExamScheduleRecord(scheduleOfExams1);
        assertEquals(scheduleOfExams1,xmlDataProvider.getScheduleOfExamsRecordByID(scheduleOfExams1.getID()));
    }
    @Test
    void saveExistingExamScheduleRecord() throws Exception {
        xmlDataProvider.saveExamScheduleRecord(scheduleOfExams2);
        Exception exception = assertThrows(Exception.class ,()->{
            xmlDataProvider.saveExamScheduleRecord(scheduleOfExams2);
        });
        assertEquals("Exam schedule record already exists",exception.getMessage());
    }
    @Test
    void deleteExamScheduleRecord() throws Exception {
        xmlDataProvider.saveExamScheduleRecord(scheduleOfExams3);
        xmlDataProvider.deleteExamScheduleRecord(scheduleOfExams3);
        Exception exception = assertThrows(Exception.class,()->{
            xmlDataProvider.getScheduleOfExamsRecordByID(scheduleOfExams3.getID());
        });
        assertEquals("Exam schedule record wasn't found",exception.getMessage());
    }
    @Test
    void deleteNonExistingExamScheduleRecord() throws Exception {
        xmlDataProvider.deleteExamScheduleRecord(scheduleOfExams1);
        Exception exception = assertThrows(Exception.class,()->{
            xmlDataProvider.deleteExamScheduleRecord(scheduleOfExams1);
        });
        assertEquals("Exam schedule record wasn't found",exception.getMessage());
    }
    @Test
    void getExamScheduleRecordByID() throws Exception {
        assertEquals(scheduleOfExams2,xmlDataProvider.getScheduleOfExamsRecordByID(scheduleOfExams2.getID()));
    }
    @Test
    void getNonExistingScheduleRecordByID(){
        Exception exception = assertThrows(Exception.class,()->{
            xmlDataProvider.getScheduleOfExamsRecordByID(0);
        });
        assertEquals("Exam schedule record wasn't found",exception.getMessage());
    }
    @Test
    void saveScheduleOfLessonsRecord() throws Exception {
        xmlDataProvider.saveScheduleOfLessonsRecord(scheduleOfLessons1);
        assertEquals(scheduleOfLessons1,xmlDataProvider.getScheduleOfLessonsRecordByID(scheduleOfLessons1.getID()));
    }
    @Test
    void saveExistingScheduleOfLessonsRecord() throws Exception {
        xmlDataProvider.saveScheduleOfLessonsRecord(scheduleOfLessons2);
        Exception exception = assertThrows(Exception.class,()->{
            xmlDataProvider.saveScheduleOfLessonsRecord(scheduleOfLessons2);
        });
        assertEquals("Lesson schedule record already exists",exception.getMessage());
    }
    @Test
    void deleteScheduleOfLessonsRecord() throws Exception {
        xmlDataProvider.saveScheduleOfLessonsRecord(scheduleOfLessons3);
        xmlDataProvider.deleteScheduleOfLessonRecord(scheduleOfLessons3);
        Exception exception = assertThrows(Exception.class,()->{
            xmlDataProvider.getScheduleOfLessonsRecordByID(scheduleOfLessons3.getID());
        });
        assertEquals("Lesson schedule record wasn't found",exception.getMessage());
    }
    @Test
    void deleteNonExistingScheduleOfLessonsRecord() throws Exception {
        xmlDataProvider.deleteScheduleOfLessonRecord(scheduleOfLessons1);
        Exception exception = assertThrows(Exception.class,()->{
            xmlDataProvider.deleteScheduleOfLessonRecord(scheduleOfLessons1);
        });
        assertEquals("Lesson schedule record wasn't found",exception.getMessage());
    }
    @Test
    void getScheduleOfLessonsRecordByID() throws Exception {
        assertEquals(scheduleOfLessons2,xmlDataProvider.getScheduleOfLessonsRecordByID(scheduleOfLessons2.getID()));
    }
    @Test
    void getNonExistingScheduleOfLessonsRecordByID(){
        Exception exception = assertThrows(Exception.class,()->{
            xmlDataProvider.getScheduleOfLessonsRecordByID(0);
        });
        assertEquals("Lesson schedule record wasn't found",exception.getMessage());
    }
    @Test
    void saveScheduleOfUnEvRecord() throws Exception {
        xmlDataProvider.saveScheduleOfUnEvRecord(scheduleOfUniversityEvents1);
        assertEquals(scheduleOfUniversityEvents1,xmlDataProvider.getScheduleOfUnEvRecordByID(scheduleOfUniversityEvents1.getID()));
    }
    @Test
    void saveExistingScheduleOfUnEvRecord() throws Exception {
        xmlDataProvider.saveScheduleOfUnEvRecord(scheduleOfUniversityEvents2);
        Exception exception = assertThrows(Exception.class,()->{
            xmlDataProvider.saveScheduleOfUnEvRecord(scheduleOfUniversityEvents2);
        });
        assertEquals("University event schedule record already exists",exception.getMessage());
    }
    @Test
    void deleteScheduleOfUnEvRecord() throws Exception {
        xmlDataProvider.saveScheduleOfUnEvRecord(scheduleOfUniversityEvents3);
        xmlDataProvider.deleteScheduleOfUnEvRecord(scheduleOfUniversityEvents3);
        Exception exception = assertThrows(Exception.class,()->{
            xmlDataProvider.getScheduleOfUnEvRecordByID(scheduleOfUniversityEvents3.getID());
        });
        assertEquals("University event schedule record wasn't found",exception.getMessage());
    }
    @Test
    void deleteNonExistingScheduleOfUnEvRecord() throws Exception {
        xmlDataProvider.deleteScheduleOfUnEvRecord(scheduleOfUniversityEvents1);
        Exception exception = assertThrows(Exception.class,()->{
            xmlDataProvider.deleteScheduleOfUnEvRecord(scheduleOfUniversityEvents1);
        });
        assertEquals("University event schedule record wasn't found",exception.getMessage());
    }
    @Test
    void getScheduleOfUnEvRecordByID() throws Exception {
        assertEquals(scheduleOfUniversityEvents2,xmlDataProvider.getScheduleOfUnEvRecordByID(scheduleOfUniversityEvents2.getID()));
    }
    @Test
    void getNonExistingScheduleOfUnEvRecordByID(){
        Exception exception = assertThrows(Exception.class,()->{
            xmlDataProvider.getScheduleOfUnEvRecordByID(0);
        });
        assertEquals("University event schedule record wasn't found",exception.getMessage());
    }
    @Test
    void saveStudentRecord() throws Exception {
        xmlDataProvider.saveStudentRecord(mihail);
        assertEquals(mihail,xmlDataProvider.getStudentRecordByID(mihail.getID()));
    }
    @Test
    void saveExistingStudentRecord() throws Exception {
        xmlDataProvider.saveStudentRecord(anna);
        Exception exception = assertThrows(Exception.class,()->{
            xmlDataProvider.saveStudentRecord(anna);
        });
        assertEquals("Student record already exists",exception.getMessage());
    }
    @Test
    void deleteStudentRecord() throws Exception {
        xmlDataProvider.saveStudentRecord(vasilisa);
        xmlDataProvider.deleteStudentRecord(vasilisa);
        Exception exception = assertThrows(Exception.class,()->{
            xmlDataProvider.getStudentRecordByID(vasilisa.getID());
        });
        assertEquals("Student record wasn't found",exception.getMessage());
    }
    @Test
    void deleteNonExistingStudentRecord() throws Exception {
        xmlDataProvider.deleteStudentRecord(mihail);
        Exception exception = assertThrows(Exception.class,()->{
            xmlDataProvider.deleteStudentRecord(mihail);
        });
        assertEquals("Student record wasn't found",exception.getMessage());
    }
    @Test
    void getStudentRecordByID() throws Exception {
        assertEquals(anna,xmlDataProvider.getStudentRecordByID(anna.getID()));
    }
    @Test
    void getNonExistingStudentRecordByID(){
        Exception exception = assertThrows(Exception.class,()->{
            xmlDataProvider.getStudentRecordByID(0);
        });
        assertEquals("Student record wasn't found",exception.getMessage());
    }
    @Test
    void saveStudentGroupRecord() throws Exception {
        xmlDataProvider.saveStudentGroupRecord(group1);
        assertEquals(group1,xmlDataProvider.getStudentGroupRecordByID(group1.getID()));
    }
    @Test
    void saveExistingStudentGroupRecord() throws Exception {
        xmlDataProvider.saveStudentGroupRecord(group2);
        Exception exception = assertThrows(Exception.class,()->{
            xmlDataProvider.saveStudentGroupRecord(group2);
        });
        assertEquals("Student group record already exists",exception.getMessage());
    }
    @Test
    void deleteStudentGroupRecord() throws Exception {
        xmlDataProvider.saveStudentGroupRecord(group3);
        xmlDataProvider.deleteStudentGroupRecord(group3);
        Exception exception = assertThrows(Exception.class,()->{
            xmlDataProvider.getStudentGroupRecordByID(group3.getID());
        });
        assertEquals("Student group record wasn't found",exception.getMessage());
    }
    @Test
    void deleteNonExistingStudentGroupRecord() throws Exception {
        xmlDataProvider.deleteStudentGroupRecord(group1);
        Exception exception = assertThrows(Exception.class,()->{
            xmlDataProvider.deleteStudentGroupRecord(group1);
        });
        assertEquals("Student group record wasn't found",exception.getMessage());
    }
    @Test
    void getStudentGroupRecordByID() throws Exception {
        assertEquals(group2,xmlDataProvider.getStudentGroupRecordByID(group2.getID()));
    }
    @Test
    void getNonExistingStudentGroupRecordByID(){
        Exception exception = assertThrows(Exception.class,()->{
            xmlDataProvider.getStudentGroupRecordByID(0);
        });
        assertEquals("Student group record wasn't found",exception.getMessage());
    }
    @Test
    void saveTeacherRecord() throws Exception {
        xmlDataProvider.saveTeacherRecord(mathTeacher);
        assertEquals(mathTeacher,xmlDataProvider.getTeacherRecordByID(mathTeacher.getID()));
    }
    @Test
    void saveExistingTeacherRecord() throws Exception {
        xmlDataProvider.saveTeacherRecord(engTeacher);
        Exception exception = assertThrows(Exception.class,()->{
            xmlDataProvider.saveTeacherRecord(engTeacher);
        });
        assertEquals("Teacher record already exists",exception.getMessage());
    }
    @Test
    void deleteDeleteTeacherRecord() throws Exception {
        xmlDataProvider.saveTeacherRecord(philosophyTeacher);
        xmlDataProvider.deleteTeacherRecord(philosophyTeacher);
        Exception exception = assertThrows(Exception.class,()->{
            xmlDataProvider.getTeacherRecordByID(philosophyTeacher.getID());
        });
        assertEquals("Teacher record wasn't found",exception.getMessage());
    }
    @Test
    void deleteNonExistingRecord() throws Exception {
        xmlDataProvider.deleteTeacherRecord(mathTeacher);
        Exception exception = assertThrows(Exception.class,()->{
            xmlDataProvider.deleteTeacherRecord(mathTeacher);
        });
        assertEquals("Teacher record wasn't found",exception.getMessage());
    }
    @Test
    void getTeacherRecordByID() throws Exception {
        assertEquals(engTeacher,xmlDataProvider.getTeacherRecordByID(engTeacher.getID()));
    }
    @Test
    void getNonExistingTeacherRecordByID(){
        Exception exception = assertThrows(Exception.class,()->{
            xmlDataProvider.getTeacherRecordByID(0);
        });
        assertEquals("Teacher record wasn't found",exception.getMessage());
    }
    @Test
    void saveIssueRecord() throws Exception {
        xmlDataProvider.saveIssueRecord(mathIssue);
        assertEquals(mathIssue,xmlDataProvider.getIssueRecordByID(mathIssue.getID()));
    }
    @Test
    void saveExistingIssueRecord() throws Exception {
        xmlDataProvider.saveIssueRecord(engIssue);
        Exception exception = assertThrows(Exception.class,()->{
            xmlDataProvider.saveIssueRecord(engIssue);
        });
        assertEquals("Issue record already exists",exception.getMessage());
    }
    @Test
    void deleteIssueRecord() throws Exception {
        xmlDataProvider.saveIssueRecord(philosophyIssue);
        xmlDataProvider.deleteIssueRecord(philosophyIssue);
        Exception exception = assertThrows(Exception.class,()->{
            xmlDataProvider.getIssueRecordByID(philosophyIssue.getID());
        });
        assertEquals("Issue record wasn't found",exception.getMessage());
    }
    @Test
    void deleteNonExistingIssueRecord() throws Exception {
        xmlDataProvider.deleteIssueRecord(mathIssue);
        Exception exception = assertThrows(Exception.class,()->{
            xmlDataProvider.deleteIssueRecord(mathIssue);
        });
        assertEquals("Issue record wasn't found",exception.getMessage());
    }
    @Test
    void getIssueRecordByID() throws Exception {
        assertEquals(engIssue,xmlDataProvider.getIssueRecordByID(engIssue.getID()));
    }
    @Test
    void getNonExistingRecordByID(){
        Exception exception = assertThrows(Exception.class,()->{
            xmlDataProvider.getIssueRecordByID(0);
        });
        assertEquals("Issue record wasn't found",exception.getMessage());
    }
    @Test
    void saveDisciplineRecord() throws Exception {
        xmlDataProvider.saveDisciplineRecord(math);
        assertEquals(math,xmlDataProvider.getDisciplineRecordByID(math.getID()));
    }
    @Test
    void saveExistingDisciplineRecord() throws Exception {
        xmlDataProvider.saveDisciplineRecord(eng);
        Exception exception = assertThrows(Exception.class,()->{
            xmlDataProvider.saveDisciplineRecord(eng);
        });
        assertEquals("Discipline record already exist",exception.getMessage());
    }
    @Test
    void deleteDisciplineRecord() throws Exception {
        xmlDataProvider.saveDisciplineRecord(philosophy);
        xmlDataProvider.deleteDisciplineRecord(philosophy);
        Exception exception = assertThrows(Exception.class,()->{
            xmlDataProvider.getDisciplineRecordByID(philosophy.getID());});
        assertEquals("Discipline record wasn't found",exception.getMessage());
    }
    @Test
    void deleteNonExistingDisciplineRecord() throws Exception {
        xmlDataProvider.deleteDisciplineRecord(math);
        Exception exception = assertThrows(Exception.class,()->{
            xmlDataProvider.deleteDisciplineRecord(math);
        });
        assertEquals("Discipline record wasn't found",exception.getMessage());
    }
    @Test
    void getDisciplineRecordByID() throws Exception {
        assertEquals(eng,xmlDataProvider.getDisciplineRecordByID(eng.getID()));
    }
    @Test
    void getNonExistingDisciplineRecordByID(){
        Exception exception = assertThrows(Exception.class,()->{
            xmlDataProvider.getDisciplineRecordByID(0);
        });
        assertEquals("Discipline record wasn't found",exception.getMessage());
    }
}
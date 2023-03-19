package ru.sfedu.services;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.sfedu.Model.*;
import ru.sfedu.utils.ConfigurationUtil;
import static ru.sfedu.Constants.*;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class DataBaseProviderTest {

    public static final ConfigurationUtil config = new ConfigurationUtil();
    public static final Discipline math = new Discipline(1,1,"Math","Exam");
    public static final Lection lection = new Lection(1,1,"/home/../","Lection 21/02/2012");
    public static final PracticalTask practTask = new PracticalTask(1,1,"/home/...","Deadline 21.02.2012");
    public static final EducationalMaterial edMat = new EducationalMaterial(1,1);
    public static final Exam exam = new Exam(1,1,"702k","12.10","Math","Main exam","Bond James");
    public static final Lesson lesson = new Lesson(1,1,"702k","12.10","Math","Lection","Bond James");
    public static final UniversityEvent unEvent = new UniversityEvent(1,1,"500k","12.00","game");
    public static final Schedule schedule = new Schedule(1,1,TypeOfSchedule.LESSONS);
    public static final Connection connection;

    static {
        try {
            connection = DriverManager.getConnection(config.getConfigurationEntry(URL_DATA_BASE),config.getConfigurationEntry(USER_DATA_BASE),
                    config.getConfigurationEntry(PASS_DATA_BASE));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static DataBaseProvider db;
    static {
        try {
            db = new DataBaseProvider();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    @AfterEach
    void cleanTables() throws SQLException, IOException {
        String sql = "TRUNCATE "+config.getConfigurationEntry(ED_MAT_DATA_TABLE);
        PreparedStatement  statement= connection.prepareStatement(sql);
        statement.executeUpdate();

        sql = "TRUNCATE "+config.getConfigurationEntry(DISCIPLINE_DATA_TABLE);
        statement = connection.prepareStatement(sql);
        statement.executeUpdate();

        sql = "TRUNCATE "+config.getConfigurationEntry(LECTION_DATA_TABLE);
        statement = connection.prepareStatement(sql);
        statement.executeUpdate();

        sql = "TRUNCATE "+config.getConfigurationEntry(PRACT_TASK_DATA_TABLE);
        statement = connection.prepareStatement(sql);
        statement.executeUpdate();

        sql = "TRUNCATE "+config.getConfigurationEntry(EXAM_DATA_TABLE);
        statement = connection.prepareStatement(sql);
        statement.executeUpdate();

        sql = "TRUNCATE "+config.getConfigurationEntry(LESSON_DATA_TABLE);
        statement = connection.prepareStatement(sql);
        statement.executeUpdate();

        sql = "TRUNCATE "+config.getConfigurationEntry(SCHEDULE_DATA_TABLE);
        statement = connection.prepareStatement(sql);
        statement.executeUpdate();

        sql = "TRUNCATE "+config.getConfigurationEntry(STUDENT_DATA_TABLE);
        statement = connection.prepareStatement(sql);
        statement.executeUpdate();

        sql = "TRUNCATE "+config.getConfigurationEntry(STUDENT_GROUP_DATA_TABLE);
        statement = connection.prepareStatement(sql);
        statement.executeUpdate();

        sql = "TRUNCATE "+config.getConfigurationEntry(STUDENT_WORK_DATA_TABLE);
        statement = connection.prepareStatement(sql);
        statement.executeUpdate();

        sql = "TRUNCATE "+config.getConfigurationEntry(TEACHER_DATA_TABLE);
        statement = connection.prepareStatement(sql);
        statement.executeUpdate();

        sql = "TRUNCATE "+config.getConfigurationEntry(UN_EVENTS_DATA_TABLE);
        statement = connection.prepareStatement(sql);
        statement.executeUpdate();
    }
    @BeforeAll
    static void init(){
        edMat.setTasks(new ArrayList<>(List.of(practTask)));
        edMat.setLections(new ArrayList<>(List.of(lection)));

        schedule.setEvents(new ArrayList<>(List.of(lesson)));
    }
    @Test
    void saveDisciplineRecord() throws Exception {
        db.saveDisciplineRecord(math);

        assertEquals(math,db.getDiscicplineRecordById(math.getID()));
    }
    @Test
    void saveExistingDisciplineRecord() throws Exception {
        db.saveDisciplineRecord(math);

        Exception exception = assertThrows(Exception.class,()->{
            db.saveDisciplineRecord(math);
        });
        assertEquals("Discipline record already exists",exception.getMessage());
    }
    @Test
    void updateDisciplineRecord() throws Exception {
        db.saveDisciplineRecord(math);
        math.setName("Not math");
        db.updateDisciplineRecord(math);

        assertEquals("Not math",db.getDiscicplineRecordById(math.getID()));

        math.setName("Math");
    }
    @Test
    void testSaveLectionRecord() throws Exception {
        db.saveLectionRecord(lection);

        assertEquals(lection,db.getLectionRecordById(lection.getID()));
    }
    @Test
    void testSaveExistingLectionRecord() throws Exception {
        db.saveLectionRecord(lection);

        Exception exception = assertThrows(Exception.class,()->{db.saveLectionRecord(lection);});
        assertEquals("Record already exists",exception.getMessage());
    }
    @Test
    void testUpdateLectionRecord() throws Exception {
        db.saveLectionRecord(lection);
        lection.setInformation("_");
        db.updateLectionRecord(lection);

        assertEquals(lection,db.getLectionRecordById(lection.getID()));

        lection.setInformation("Lection 21/02/2012");
    }
    @Test
    void testSavePractTaskRecord() throws Exception {
        db.savePracticalTaskRecord(practTask);

        assertEquals(practTask,db.getPracticalTaskRecordById(practTask.getID()));
    }
    @Test
    void testSaveExistingPractTaskRecord() throws Exception {
        db.savePracticalTaskRecord(practTask);

        Exception exception = assertThrows(Exception.class,()->{
            db.savePracticalTaskRecord(practTask);
        });
        assertEquals("Pract task record already exists",exception.getMessage());

    }
    @Test
    void testUpdatePractTaskRecord() throws Exception {
        db.savePracticalTaskRecord(practTask);
        practTask.setInformation("_");
        db.updatePracticalTaskRecord(practTask);

        assertEquals("_",db.getPracticalTaskRecordById(practTask.getID()).getInformation());

        practTask.setInformation("Deadline 21.02.2012");
    }
    @Test
    void saveEducationalMaterialRecord() throws Exception {
        db.saveEducationalMaterialRecord(edMat);

        assertEquals(edMat,db.getEducationalMaterialRecordByID(edMat.getID()));
    }
    @Test
    void saveExistingEdMatRecord() throws Exception {
        db.saveEducationalMaterialRecord(edMat);

        Exception exception = assertThrows(Exception.class,()->{db.saveEducationalMaterialRecord(edMat);});
        assertEquals("Education material record already exists",exception.getMessage());
    }
    @Test
    void testSaveExamRecord() throws Exception {
        db.saveExamRecord(exam);

        assertEquals(exam,db.getExamRecordByID(exam.getID()));
    }
    @Test
    void testSaveExistingExamRecord() throws Exception {
        db.saveExamRecord(exam);

        Exception exception = assertThrows(Exception.class,()->{
            db.saveExamRecord(exam);
        });
        assertEquals("Exam record already exists",exception.getMessage());
    }
    @Test
    void testUpdateExamRecord() throws Exception {
        db.saveExamRecord(exam);
        exam.setPlace("700k");
        db.updateExamRecord(exam);

        assertEquals("700k",db.getExamRecordByID(exam.getID()).getPlace());

        exam.setPlace("702k");
    }
    @Test
    void testSaveLessonRecord() throws Exception {
        db.saveLessonRecord(lesson);

        assertEquals(lesson,db.getLessonRecordById(lesson.getID()));
    }
    @Test
    void testSaveExistingLessonRecord() throws Exception {
        db.saveLessonRecord(lesson);

        Exception exception = assertThrows(Exception.class,()->{
            db.saveLessonRecord(lesson);
        });
        assertEquals("Lesson record already exists",exception.getMessage());
    }
    @Test
    void testUpdateLessonRecord() throws Exception {
        db.saveLessonRecord(lesson);
        lesson.setPlace("700k");
        db.updateLessonRecord(lesson);

        assertEquals("700k",db.getLessonRecordById(lesson.getID()).getPlace());

        lesson.setPlace("702k");
    }
    @Test
    void testSaveUnEventRecord() throws Exception {
        db.saveUnEventRecord(unEvent);

        assertEquals(unEvent,db.getUnEventRecordById(unEvent.getID()));
    }
    @Test
    void testSaveExistingUnEventRecord() throws Exception {
        db.saveUnEventRecord(unEvent);

        Exception exception = assertThrows(Exception.class,()->{
            db.saveUnEventRecord(unEvent);
        });
        assertEquals("University event record already exists",exception.getMessage());
    }
    @Test
    void testUpdateUnEventRecord() throws Exception {
        db.saveUnEventRecord(unEvent);
        unEvent.setInformation("meeting");
        db.updateUnEventRecord(unEvent);

        assertEquals("meeting",db.getUnEventRecordById(unEvent.getID()).getInformation());

        unEvent.setInformation("game");
    }
    @Test
    public void testSaveScheduleRecord() throws Exception {
        db.saveScheduleRecord(schedule);

        assertEquals(schedule,db.getScheduleRecordById(schedule.getID()));
    }
    @Test
    public void testSaveExistingScheduleRecord() throws Exception {
        db.saveScheduleRecord(schedule);

        Exception exception = assertThrows(Exception.class,()->{
            db.saveScheduleRecord(schedule);
        });
        assertEquals("Schedule record already exists",exception.getMessage());
    }
    @Test
    public void testUpdatedScheduleRecord() throws Exception {
        db.saveScheduleRecord(schedule);
        schedule.setSemester(2);
        db.updateScheduleRecord(schedule);

        assertEquals(2,db.getScheduleRecordById(schedule.getID()).getSemester());

        schedule.setSemester(1);
    }


}
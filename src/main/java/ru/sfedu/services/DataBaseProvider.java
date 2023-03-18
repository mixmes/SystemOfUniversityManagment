package ru.sfedu.services;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.sfedu.Model.*;
import ru.sfedu.utils.ConfigurationUtil;
import static ru.sfedu.Constants.*;

import java.io.File;
import java.io.IOException;
import java.rmi.server.ExportException;
import java.sql.*;

public class DataBaseProvider implements DataProvider{
    private Connection connection ;
    private ConfigurationUtil config = new ConfigurationUtil();
    private static final Logger log = LogManager.getLogger();
    DataBaseProvider() throws IOException {
        try{
            connection = DriverManager.getConnection(config.getConfigurationEntry(URL_DATA_BASE),config.getConfigurationEntry(USER_DATA_BASE),config.getConfigurationEntry(PASS_DATA_BASE));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public void deleteRecord(String nameTable, int id){
        String sql = "DELETE FROM "+nameTable+" WHERE id="+id;
        try(Statement statement = connection.createStatement()){
            statement.executeUpdate(sql);
            log.info("Record with ID:"+id+" was deleted");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    @Override
    public void saveDisciplineRecord(Discipline discipline) throws IOException {

    }
    @Override
    public Discipline getDiscicplineRecordById(int id) throws IOException {
        return null;
    }

    @Override
    public void deleteDisciplineRecordById(Discipline discipline) {

    }

    @Override
    public void updateDisciplineRecordById(Discipline discipline) {

    }

    @Override
    public void saveEducationalMaterialRecord(EducationalMaterial educationalMaterial) {

    }

    @Override
    public EducationalMaterial getEducationalMaterialRecordByID(int id) {
        return null;
    }

    @Override
    public void deleteEducationalMaterialRecord(EducationalMaterial educationalMaterial) {

    }

    @Override
    public void updateEducationalMaterialRecord(EducationalMaterial educationalMaterial) {

    }

    @Override
    public void saveExamRecord(Exam exam) {

    }

    @Override
    public void deleteExamRecord(Exam exam) {

    }

    @Override
    public Exam getExamRecordByID(int id) {
        return null;
    }

    @Override
    public void updateExamRecord(Exam exam) {

    }

    @Override
    public void saveLectionRecord(Lection lection) throws Exception {
        String sql = "INSERT INTO "+config.getConfigurationEntry(LECTION_DATA_TABLE)+" (id,edMatId,information,url) VALUES(?,?,?,?)";
        try(PreparedStatement statement = connection.prepareStatement(sql)){
            statement.setInt(1,lection.getID());
            statement.setInt(2,lection.getEducationMaterialID());
            statement.setString(3,lection.getInformation());
            statement.setString(4,lection.getLection().getAbsolutePath());
            if(statement.executeUpdate() == 0){
                log.error("Lection record can't be saved");
                throw new Exception("Record already exists");
            }

        } catch (Exception e) {
            throw new Exception("Record already exists");
        }
        log.info("Lection record was saved");
    }

    @Override
    public void deleteLectionRecord(Lection lection) throws IOException {
        deleteRecord(config.getConfigurationEntry(LECTION_DATA_TABLE), lection.getID());
    }

    @Override
    public Lection getLectionRecordById(int id) throws Exception {
        Lection lection = new Lection();
        String sql = "SELECT * FROM "+config.getConfigurationEntry(LECTION_DATA_TABLE)+" WHERE id="+id;
        try(Statement statement = connection.createStatement()){
            ResultSet resultSet = statement.executeQuery(sql);
            if(resultSet.next()){
                lection.setID(resultSet.getInt("id"));
                lection.setEducationMaterialID(resultSet.getInt("edMatId"));
                lection.setInformation(resultSet.getString("information"));
                lection.setLection(new File(resultSet.getString("url")));
                log.info("Lection record was obtained");
            }
            else {
                log.error("Lection record wasn't found");
                throw new Exception("Lection record wasn't found");
            }
        }
        return  lection;
    }

    @Override
    public void updateLectionRecord(Lection lection) throws Exception {
        String sql = "UPDATE "+config.getConfigurationEntry(LECTION_DATA_TABLE)+" SET information = '"+lection.getInformation() +
                "', url = '"+lection.getLection().getAbsolutePath()+"' WHERE id = "+lection.getID();
        try(PreparedStatement statement = connection.prepareStatement(sql)){
            statement.executeUpdate();
            log.info("Lection record was updated");
        } catch (SQLException e) {
            throw new Exception("Lection can't be updated");
        }
    }

    @Override
    public void saveLessonRecord(Lesson lesson) {

    }

    @Override
    public void deleteLessonRecord(Lesson lesson) {

    }

    @Override
    public void updateLessonRecord(Lesson lesson) {

    }

    @Override
    public Lesson getLessonRecordById(int id) {
        return null;
    }

    @Override
    public void savePracticalTaskRecord(PracticalTask practicalTask) throws Exception {
        String sql = "INSERT INTO "+config.getConfigurationEntry(PRACT_TASK_DATA_TABLE)+" (id,edMatId,information,url) VALUES(?,?,?,?)";
        try(PreparedStatement statement = connection.prepareStatement(sql)){
            statement.setInt(1,practicalTask.getID());
            statement.setInt(2,practicalTask.getEducationMaterialID());
            statement.setString(3,practicalTask.getInformation());
            statement.setString(4,practicalTask.getTask().getAbsolutePath());
            statement.executeUpdate();


        } catch (SQLException e) {
            log.info("Pract task record can't be saved");
            throw new Exception("Pract task record already exists");
        }
    }

    @Override
    public void deletePracticalTaskRecord(PracticalTask practicalTask) throws IOException {
        deleteRecord(config.getConfigurationEntry(PRACT_TASK_DATA_TABLE),practicalTask.getID());
    }

    @Override
    public void updatePracticalTaskRecord(PracticalTask practicalTask) throws IOException {
        String sql = "UPDATE "+config.getConfigurationEntry(PRACT_TASK_DATA_TABLE)+" SET information = '"+practicalTask.getInformation()+"'," +
                "url = '"+practicalTask.getTask().getAbsolutePath()+"' WHERE id = "+practicalTask.getID();
        try(PreparedStatement statement = connection.prepareStatement(sql)){
            statement.executeUpdate();
            log.info("Pract task record was updated");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public PracticalTask getPracticalTaskRecordById(int id) throws Exception {
        PracticalTask practicalTask = new PracticalTask();
        String sql = "SELECT * FROM " + config.getConfigurationEntry(PRACT_TASK_DATA_TABLE)+" WHERE id ="+id;
        try(PreparedStatement statement = connection.prepareStatement(sql)){
            ResultSet resultSet = statement.executeQuery(sql);
            if(resultSet.next()){
                practicalTask.setID(resultSet.getInt("id"));
                practicalTask.setEducationMaterialID(resultSet.getInt("edMatId"));
                practicalTask.setInformation(resultSet.getString("information"));
                practicalTask.setTask(new File(resultSet.getString("url")));
                log.info("Pract task record was obtained");
            }
            else {
                log.error("Pract task record wasn't found");
                throw new Exception("Pract task record wasn't found");
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return practicalTask;
    }

    @Override
    public void saveScheduleRecord(Schedule schedule) {

    }

    @Override
    public void deleteScheduleRecord(Schedule schedule) {

    }

    @Override
    public void updateScheduleRecord(Schedule schedule) {

    }

    @Override
    public Schedule getScheduleRecordById(int id) {
        return null;
    }

    @Override
    public void saveStudentRecord(Student student) {

    }

    @Override
    public void deleteStudentRecord(Student student) {

    }

    @Override
    public void updateStudentRecord(Student student) {

    }

    @Override
    public Student getStudentRecordById(int id) {
        return null;
    }

    @Override
    public void saveStudentGroupRecord(StudentGroup studentGroup) {

    }

    @Override
    public void deleteStudentGroupRecord(StudentGroup studentGroup) {

    }

    @Override
    public void updateStudentGroupRecord(StudentGroup studentGroup) {

    }

    @Override
    public StudentGroup getStudentGroupRecordById(int id) {
        return null;
    }

    @Override
    public void saveStudentWorKRecord(StudentWork studentWork) {

    }

    @Override
    public void deleteStudentWorkRecord(StudentWork studentWork) {

    }

    @Override
    public void updateStudentWorkRecord(StudentWork studentWork) {

    }

    @Override
    public StudentWork getStudentWorkRecordById(StudentWork studentWork) {
        return null;
    }

    @Override
    public void saveTeacherRecord(Teacher teacher) {

    }

    @Override
    public void deleteTeacherRecord(Teacher teacher) {

    }

    @Override
    public void updateTeacherRecord(Teacher teacher) {

    }

    @Override
    public Teacher getTeacherRecordById(int id) {
        return null;
    }

    @Override
    public void saveUniversityEventRecord(UniversityEvent universityEvent) {

    }

    @Override
    public void deleteUniversityEventRecord(UniversityEvent universityEvent) {

    }

    @Override
    public void updateUniversityEventRecord(UniversityEvent universityEvent) {

    }

    @Override
    public UniversityEvent getUniversityEventRecordById(int id) {
        return null;
    }
}

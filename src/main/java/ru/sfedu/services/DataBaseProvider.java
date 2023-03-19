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
import java.util.ArrayList;
import java.util.List;

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
    public void saveDisciplineRecord(Discipline discipline) throws Exception {
        String sql = "INSERT INTO " + config.getConfigurationEntry(DISCIPLINE_DATA_TABLE)+" (id,teacherId,name,typeOfMarking) VALUES(?,?,?,?)";
        try(PreparedStatement statement = connection.prepareStatement(sql)){
            statement.setInt(1,discipline.getID());
            statement.setInt(2,discipline.getTeacherID());
            statement.setString(3,discipline.getName());
            statement.setString(4,discipline.getTypeOfMarking());
            statement.executeUpdate();
            saveEducationalMaterialRecord(discipline.getEducationalMaterial());
            log.info("Discipline record was saved");
        } catch (SQLException e) {
            log.error("Discipline record already exists");
            throw new Exception("Discipline record already exists");
        }
    }
    @Override
    public Discipline getDiscicplineRecordById(int id) throws IOException {
        Discipline discipline = new Discipline();
        String sql = "SELECT * FROM "+config.getConfigurationEntry(DISCIPLINE_DATA_TABLE)+" WHERE id="+id;
        try(Statement statement = connection.createStatement()){
            ResultSet resultSet = statement.executeQuery(sql);
            if(!resultSet.next()){
                throw new Exception("Discipline record wasn't found");
            }
            discipline.setID(resultSet.getInt("id"));
            discipline.setTeacherID(resultSet.getInt("teacherId"));
            discipline.setName(resultSet.getString("name"));
            discipline.setTypeOfMarking(resultSet.getString("typeOfMarking"));
            discipline.setEducationalMaterial(getEducationalMatRecordByDisciplineId(id));
            log.info("Discipline record was obtained");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return discipline;
    }

    @Override
    public void deleteDisciplineRecord(Discipline discipline) {
        deleteRecord(DISCIPLINE_DATA_TABLE,discipline.getID());
        deleteRecord(ED_MAT_DATA_TABLE,discipline.getEducationalMaterial().getID());
    }

    @Override
    public void updateDisciplineRecord(Discipline discipline) throws IOException {
        String sql = "UPDATE "+config.getConfigurationEntry(DISCIPLINE_DATA_TABLE)+" SET name= '"+discipline.getName()+"', typeOfMarking = '"+
                discipline.getTypeOfMarking()+"' WHERE id="+discipline.getID();
        try(PreparedStatement statement = connection.prepareStatement(sql)){
            statement.executeUpdate();
            updateEducationalMaterialRecord(discipline.getEducationalMaterial());
            log.info("Discipline record was updated");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public void saveEducationalMaterialRecord(EducationalMaterial educationalMaterial) throws Exception {
        String sql = "INSERT INTO " + config.getConfigurationEntry(ED_MAT_DATA_TABLE)+" (id,disciplineId) VALUES(?,?)";
        try( PreparedStatement preparedStatement = connection.prepareStatement(sql);){
        preparedStatement.setInt(1,educationalMaterial.getID());
        preparedStatement.setInt(2,educationalMaterial.getDisciplineID());
        if(preparedStatement.executeUpdate() == 0){
            log.error("Education material record can't be saved");
        }
        }
        catch (SQLException e) {
            throw new Exception("Education material record already exists");
        }

        educationalMaterial.getLections().stream().forEach(s-> {
            try {
                saveLectionRecord(s);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        educationalMaterial.getTasks().stream().forEach(s-> {
            try {
                savePracticalTaskRecord(s);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        log.info("Education material record was saved");
    }

    @Override
    public EducationalMaterial getEducationalMaterialRecordByID(int id) throws Exception {
        EducationalMaterial edMat = new EducationalMaterial();
        String sql = "SELECT * FROM "+ config.getConfigurationEntry(ED_MAT_DATA_TABLE)+ " WHERE id="+id;
        Statement statement = connection.createStatement();
        ResultSet resultSet=statement.executeQuery(sql);
        if(resultSet.next()){
            edMat.setID(resultSet.getInt("id"));
            edMat.setDisciplineID(resultSet.getInt("disciplineId"));
        }
        else {
            log.error("Education material record wasn't found");
            throw new Exception("Education material record wasn't found");
        }
        edMat.setTasks(getPractTaskRecordByEdMatId(id));
        edMat.setLections(getLectionRecordByEdMatId(id));
        log.info("Education material record was obtained");
        return  edMat;
    }
    public EducationalMaterial getEducationalMatRecordByDisciplineId(int id) throws IOException {
        EducationalMaterial edMat = new EducationalMaterial();
        String sql = "SELECT * FROM "+config.getConfigurationEntry(ED_MAT_DATA_TABLE)+" WHERE disciplineId="+id;
        try(Statement statement = connection.createStatement()){
            ResultSet resultSet = statement.executeQuery(sql);
            if(!resultSet.next()){
                throw new Exception();
            }
            edMat.setID(resultSet.getInt("id"));
            edMat.setDisciplineID(resultSet.getInt("disciplineId"));
            edMat.setLections(getLectionRecordByEdMatId(edMat.getID()));
            edMat.setTasks(getPractTaskRecordByEdMatId(edMat.getID()));
            log.info("Education material record was obtained");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (Exception e) {
            return null;
        }
        return edMat;
    }

    @Override
    public void deleteEducationalMaterialRecord(EducationalMaterial educationalMaterial) throws IOException {
        deleteRecord(config.getConfigurationEntry(ED_MAT_DATA_TABLE),educationalMaterial.getID());
        educationalMaterial.getLections().stream().forEach(s-> {
            try {
                deleteRecord(config.getConfigurationEntry(LECTION_DATA_TABLE),s.getID());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        educationalMaterial.getTasks().stream().forEach(s-> {
            try {
                deleteRecord(config.getConfigurationEntry(PRACT_TASK_DATA_TABLE),s.getID());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

    @Override
    public void updateEducationalMaterialRecord(EducationalMaterial educationalMaterial) {
        educationalMaterial.getTasks().stream().forEach(s-> {
            try {
                updatePracticalTaskRecord(s);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        educationalMaterial.getLections().stream().forEach(s-> {
            try {
                updateLectionRecord(s);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
    }

    @Override
    public void saveExamRecord(Exam exam) throws Exception {
        String sql = "INSERT INTO "+config.getConfigurationEntry(EXAM_DATA_TABLE) + " (id,scheduleId,place,time,nameOfDiscipline,type,nameOfTeacher)" +
                "VALUES(?,?,?,?,?,?,?)";
        try(PreparedStatement preparedStatement = connection.prepareStatement(sql)){
            preparedStatement.setInt(1,exam.getID());
            preparedStatement.setInt(2,exam.getScheduleID());
            preparedStatement.setString(3,exam.getPlace());
            preparedStatement.setString(4,exam.getTime());
            preparedStatement.setString(5,exam.getNameOfDiscipline());
            preparedStatement.setString(6,exam.getType());
            preparedStatement.setString(7,exam.getNameOfTeacher());
            preparedStatement.executeUpdate();
            log.info("Exam record was saved");
        } catch (SQLException e) {
            log.error("Exam record already exists");
            throw new Exception("Exam record already exists");
        }
    }

    @Override
    public void deleteExamRecord(Exam exam) throws IOException {
        deleteRecord(config.getConfigurationEntry(EXAM_DATA_TABLE),exam.getID());
    }

    @Override
    public Exam getExamRecordByID(int id) throws IOException {
        Exam exam = new Exam();
        String sql = "SELECT * FROM "+config.getConfigurationEntry(EXAM_DATA_TABLE)+" WHERE id="+id;
        try(PreparedStatement statement = connection.prepareStatement(sql)){
            ResultSet resultSet = statement.executeQuery();
            if(!resultSet.next()){
                log.error("Exam record wasn't found");
                throw new Exception("Exam record wasn't found");
            }
            exam.setID(resultSet.getInt("id"));
            exam.setScheduleID(resultSet.getInt("scheduleId"));
            exam.setPlace(resultSet.getString("place"));
            exam.setTime(resultSet.getString("time"));
            exam.setNameOfDiscipline(resultSet.getString("nameOfDiscipline"));
            exam.setType(resultSet.getString("type"));
            exam.setNameOfTeacher(resultSet.getString("nameOfTeacher"));
            log.info("Exam record was obtained");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return exam;
    }

    @Override
    public void updateExamRecord(Exam exam) throws IOException {
        String sql = "UPDATE " + config.getConfigurationEntry(EXAM_DATA_TABLE)+" SET place = '"+exam.getPlace()+"' , time = '"+exam.getTime()+
                "' , nameOfDiscipline = '"+exam.getNameOfDiscipline()+"', type = '"+exam.getType()+"' , nameOfTeacher = '"+exam.getNameOfTeacher()+
                "' WHERE id="+exam.getID();
        try(PreparedStatement statement = connection.prepareStatement(sql)){
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
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
    public ArrayList<Lection> getLectionRecordByEdMatId(int id) throws Exception {
        ArrayList<Lection> lections = new ArrayList<>();
        String sql = "SELECT * FROM "+config.getConfigurationEntry(LECTION_DATA_TABLE)+ " WHERE edMatId="+id;
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(sql);
        Lection lection = new Lection();
        while(resultSet.next()){
            lection.setID(resultSet.getInt("id"));
            lection.setEducationMaterialID(resultSet.getInt("edMatId"));
            lection.setInformation(resultSet.getString("information"));
            lection.setLection(new File(resultSet.getString("url")));
            lections.add(lection);
        }
        return lections;
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
        PreparedStatement statement = connection.prepareStatement(sql);
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

        return practicalTask;
    }
    public ArrayList<PracticalTask> getPractTaskRecordByEdMatId(int id) throws Exception {
        ArrayList<PracticalTask> practicalTasks = new ArrayList<>();
        String sql = "SELECT * FROM " + config.getConfigurationEntry(PRACT_TASK_DATA_TABLE)+" WHERE edMatId="+id;
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(sql);
        PracticalTask practicalTask = new PracticalTask();
        while(resultSet.next()){
            practicalTask.setID(resultSet.getInt("id"));
            practicalTask.setEducationMaterialID(resultSet.getInt("edMatId"));
            practicalTask.setInformation(resultSet.getString("information"));
            practicalTask.setTask(new File(resultSet.getString("url")));
            practicalTasks.add(practicalTask);
        }
        return practicalTasks;
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

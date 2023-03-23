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
    public ArrayList<Event> getExamsRecordByScheduleId(int id) throws IOException {
        ArrayList<Event> exams = new ArrayList<>();
        String sql = "SELECT * FROM "+config.getConfigurationEntry(EXAM_DATA_TABLE)+" WHERE scheduleId="+id;
        try(PreparedStatement statement = connection.prepareStatement(sql)){
            ResultSet resultSet = statement.executeQuery();
            if(!resultSet.next()){
                log.error("Exam record wasn't found");
            }
            Exam exam = new Exam();
            while (!resultSet.next()){
                exam.setID(resultSet.getInt("id"));
                exam.setScheduleID(resultSet.getInt("scheduleId"));
                exam.setPlace(resultSet.getString("place"));
                exam.setTime(resultSet.getString("time"));
                exam.setNameOfDiscipline(resultSet.getString("nameOfDiscipline"));
                exam.setType(resultSet.getString("type"));
                exam.setNameOfTeacher(resultSet.getString("nameOfTeacher"));
                exams.add(exam);
            }
            log.info("Exam records was obtained");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return exams;
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
    public void saveLessonRecord(Lesson lesson) throws Exception {
        String sql = "INSERT INTO "+config.getConfigurationEntry(LESSON_DATA_TABLE) + " (id,scheduleId,place,time,nameOfDiscipline,type,nameOfTeacher)" +
                "VALUES(?,?,?,?,?,?,?)";
        try(PreparedStatement preparedStatement = connection.prepareStatement(sql)){
            preparedStatement.setInt(1,lesson.getID());
            preparedStatement.setInt(2,lesson.getScheduleID());
            preparedStatement.setString(3,lesson.getPlace());
            preparedStatement.setString(4,lesson.getTime());
            preparedStatement.setString(5,lesson.getNameOfDiscipline());
            preparedStatement.setString(6,lesson.getType());
            preparedStatement.setString(7,lesson.getNameOfTeacher());
            preparedStatement.executeUpdate();
            log.info("Lesson record was saved");
        } catch (SQLException e) {
            log.error("Lesson record already exists");
            throw new Exception("Lesson record already exists");
        }
    }

    @Override
    public void deleteLessonRecord(Lesson lesson) throws IOException {
        deleteRecord(config.getConfigurationEntry(LESSON_DATA_TABLE),lesson.getID());
    }

    @Override
    public void updateLessonRecord(Lesson lesson) throws IOException {
        String sql = "UPDATE " + config.getConfigurationEntry(LESSON_DATA_TABLE)+" SET place = '"+lesson.getPlace()+"' , time = '"+lesson.getTime()+
                "' , nameOfDiscipline = '"+lesson.getNameOfDiscipline()+"', type = '"+lesson.getType()+"' , nameOfTeacher = '"+lesson.getNameOfTeacher()+
                "' WHERE id="+lesson.getID();
        try(PreparedStatement statement = connection.prepareStatement(sql)){
            statement.executeUpdate();
            log.info("Lesson record was updated");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Lesson getLessonRecordById(int id) throws IOException {
        Lesson lesson = new Lesson();
        String sql = "SELECT * FROM "+config.getConfigurationEntry(LESSON_DATA_TABLE)+" WHERE id="+id;
        try(PreparedStatement statement = connection.prepareStatement(sql)){
            ResultSet resultSet = statement.executeQuery();
            if(!resultSet.next()){
                log.error("Lesson record wasn't found");
                throw new Exception("Lesson record wasn't found");
            }
            lesson.setID(resultSet.getInt("id"));
            lesson.setScheduleID(resultSet.getInt("scheduleId"));
            lesson.setPlace(resultSet.getString("place"));
            lesson.setTime(resultSet.getString("time"));
            lesson.setNameOfDiscipline(resultSet.getString("nameOfDiscipline"));
            lesson.setType(resultSet.getString("type"));
            lesson.setNameOfTeacher(resultSet.getString("nameOfTeacher"));
            log.info("Lesson record was obtained");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return lesson;
    }
    public ArrayList<Event> getLessonsRecordByScheduleId(int id) throws IOException {
        ArrayList<Event> lessons = new ArrayList<>();
        String sql = "SELECT * FROM "+config.getConfigurationEntry(LESSON_DATA_TABLE)+" WHERE scheduleId="+id;
        try(PreparedStatement statement = connection.prepareStatement(sql)){
            ResultSet resultSet = statement.executeQuery();
            if(!resultSet.next()){
                log.error("Lesson record wasn't found");
            }
            Lesson lesson = new Lesson();
            while (resultSet.next()){
                lesson.setID(resultSet.getInt("id"));
                lesson.setScheduleID(resultSet.getInt("scheduleId"));
                lesson.setPlace(resultSet.getString("place"));
                lesson.setTime(resultSet.getString("time"));
                lesson.setNameOfDiscipline(resultSet.getString("nameOfDiscipline"));
                lesson.setType(resultSet.getString("type"));
                lesson.setNameOfTeacher(resultSet.getString("nameOfTeacher"));
                lessons.add(lesson);
            }
            log.info("Lesson records was obtained");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return lessons;
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
    public void saveScheduleRecord(Schedule schedule) throws Exception {
        String sql = "INSERT INTO "+config.getConfigurationEntry(SCHEDULE_DATA_TABLE)+" (id,semester,type) VALUES(?,?,?)";
        try(PreparedStatement statement = connection.prepareStatement(sql)){
            statement.setInt(1,schedule.getID());
            statement.setInt(2,schedule.getSemester());
            statement.setString(3,schedule.getType().name());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new Exception("Schedule record already exists");
        }
        saveEvents(schedule);
        log.info("Schedule record was saved");
    }

    @Override
    public void deleteScheduleRecord(Schedule schedule) throws IOException {
        deleteRecord(config.getConfigurationEntry(SCHEDULE_DATA_TABLE),schedule.getID());
        deleteEvents(schedule);
        log.info("Schedule record was deleted");
    }

    @Override
    public void updateScheduleRecord(Schedule schedule) throws IOException {
        String sql = "UPDATE "+config.getConfigurationEntry(SCHEDULE_DATA_TABLE)+" SET semester = '"+schedule.getSemester()+"' , type = '"+
                schedule.getType().name()+"'  WHERE id = "+schedule.getID();
        try(PreparedStatement statement = connection.prepareStatement(sql)){
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        updateEvents(schedule);
    }

    @Override
    public Schedule getScheduleRecordById(int id) throws Exception {
        Schedule schedule = new Schedule();
        String sql = "SELECT * FROM "+config.getConfigurationEntry(SCHEDULE_DATA_TABLE)+" WHERE id = "+id;
        try(PreparedStatement statement = connection.prepareStatement(sql)){
            ResultSet resultSet = statement.executeQuery(sql);
            if(!resultSet.next()){
                throw new Exception("Schedule record wasn't found");
            }
            schedule.setID(resultSet.getInt("id"));
            schedule.setSemester(resultSet.getInt("semester"));
            schedule.setType(TypeOfSchedule.valueOf(resultSet.getString("type")));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (Exception e) {
            log.info("schedule "+schedule.getSemester());
            throw new Exception("Schedule record wasn't found");
        }
        getEvents(schedule);
        return schedule;
    }
    public void saveEvents(Schedule schedule){
        switch (schedule.getType().name()){
            case("LESSONS"):
                schedule.getEvents().stream().forEach(s->
                {
                    try {
                        saveLessonRecord((Lesson) s);
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                });
                break;
            case("EXAMS"):
                schedule.getEvents().stream().forEach(s->
                {
                    try {
                        saveExamRecord((Exam) s);
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                });
                break;
            case("UNIVERSITY EVENTS"):
                schedule.getEvents().stream().forEach(s->
                {
                    try {
                        saveUnEventRecord((UniversityEvent) s);
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                });
        }
    }
    public void deleteEvents(Schedule schedule){
        switch (schedule.getType().name()){
            case("LESSONS"):
                schedule.getEvents().stream().forEach(s->
                {
                    try {
                        deleteRecord(config.getConfigurationEntry(LESSON_DATA_TABLE),s.getID());
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                });
                break;
            case("EXAMS"):
                schedule.getEvents().stream().forEach(s->
                {
                    try {
                        deleteRecord(config.getConfigurationEntry(EXAM_DATA_TABLE),s.getID());
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                });
                break;
            case("UNIVERSITY EVENTS"):
                schedule.getEvents().stream().forEach(s->
                {
                    try {
                        deleteRecord(config.getConfigurationEntry(UN_EVENTS_DATA_TABLE),s.getID());
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                });
        }
    }
    public void updateEvents(Schedule schedule){
        switch (schedule.getType().name()){
            case("LESSONS"):
                schedule.getEvents().stream().forEach(s->
                {
                    try {
                        updateLessonRecord((Lesson) s);
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                });
                break;
            case("EXAMS"):
                schedule.getEvents().stream().forEach(s->
                {
                    try {
                        updateExamRecord((Exam) s);
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                });
                break;
            case("UNIVERSITY EVENTS"):
                schedule.getEvents().stream().forEach(s->
                {
                    try {
                        updateUnEventRecord((UniversityEvent) s);
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                });
        }
    }
    public void getEvents(Schedule schedule) throws IOException {
        ArrayList<Event> events = new ArrayList<>();
        switch (schedule.getType().name()){
            case("LESSONS"):
                schedule.setEvents(getLessonsRecordByScheduleId(schedule.getID()));
                break;
            case("EXAMS"):
                schedule.setEvents(getExamsRecordByScheduleId(schedule.getID()));
                break;
            case("UNIVERSITY EVENTS"):
                schedule.setEvents(getUnEventsRecordByScheduleId(schedule.getID()));
        }
    }

    @Override
    public void saveStudentRecord(Student student) throws Exception {
        String sql = "INSERT INTO " +config.getConfigurationEntry(STUDENT_DATA_TABLE)+ " (id,name) VALUES(?,?)";
        try(PreparedStatement statement = connection.prepareStatement(sql)){
            statement.setInt(1,student.getID());
            statement.setString(2,student.getName());
            statement.setInt(3,student.getStudentGroupId());
            statement.executeUpdate();
            log.info("Student record was saved");
        } catch (SQLException e) {
            log.error("Student record already exists");
            throw new Exception("Student record already exists");
        }
    }

    @Override
    public void deleteStudentRecord(Student student) throws IOException {
        deleteRecord(config.getConfigurationEntry(STUDENT_DATA_TABLE),student.getID());
    }

    @Override
    public void updateStudentRecord(Student student) throws IOException {
        String sql = "UPDATE " + config.getConfigurationEntry(STUDENT_DATA_TABLE)+" SET id = '"+student.getID()+"' , name = '"+student.getName()+
                "' WHERE id = "+student.getID();
        try(Statement statement = connection.createStatement()){
            statement.executeUpdate(sql);
            log.info("Student record was saved");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Student getStudentRecordById(int id) throws Exception {
        Student student = new Student();
        String sql = "SELECT * FROM "+config.getConfigurationEntry(STUDENT_DATA_TABLE)+" WHERE id = "+id;
        try(PreparedStatement statement = connection.prepareStatement(sql)){
            ResultSet resultSet = statement.executeQuery();
            if(resultSet.next()){
                student.setID(resultSet.getInt("id"));
                student.setName(resultSet.getString("name"));
                student.setStudentGroupId(resultSet.getInt("studentGroupId"));
                log.info("Student record was obtained");
            }
            else {
                log.error("Student record wasn't found");
                throw new Exception("Student record wasn't found");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return student;
    }
    public ArrayList<Student> getStudentRecordsByGroupId(int id) throws IOException {
        ArrayList<Student> students = new ArrayList<>();
        String sql = "SELECT * FROM " + config.getConfigurationEntry(STUDENT_DATA_TABLE)+ " WHERE studentGroupId = "+id;
        try(PreparedStatement statement = connection.prepareStatement(sql)){
            ResultSet resultSet = statement.executeQuery();
            Student student = new Student();
            while (resultSet.next()){
                student.setID(resultSet.getInt("id"));
                student.setName(resultSet.getString("name"));
                student.setStudentGroupId(resultSet.getInt("studentGroupId"));
                students.add(student);
            }
            log.info("Student records was obtained");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return students;
    }

    @Override
    public void saveStudentGroupRecord(StudentGroup studentGroup) throws Exception {
        String sql = "INSERT INTO " + config.getConfigurationEntry(STUDENT_GROUP_DATA_TABLE) + " (id,course,name,codeOfGroup) VALUES(?,?,?,?)";
        try(PreparedStatement statement  = connection.prepareStatement(sql)){
            statement.setInt(1,studentGroup.getID());
            statement.setInt(2,studentGroup.getCourse());
            statement.setString(3,studentGroup.getName());
            statement.setString(4, studentGroup.getCodeOfGroup());
            statement.executeUpdate();
            studentGroup.getGroupComposition().stream().forEach(s-> {
                try {
                    saveStudentRecord(s);
                } catch (Exception e) {
                    log.info(e.getMessage());
                }
            });
            log.info("Student group record was saved");
        } catch (SQLException e) {
            log.error("Student group record already exists");
            throw new Exception("Student group record already exists");
        }
    }

    @Override
    public void deleteStudentGroupRecord(StudentGroup studentGroup) throws IOException {
        deleteRecord(config.getConfigurationEntry(STUDENT_GROUP_DATA_TABLE),studentGroup.getID());
        studentGroup.getGroupComposition().stream().forEach(s-> {
            try {
                deleteRecord(config.getConfigurationEntry(STUDENT_GROUP_DATA_TABLE),s.getID());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

    @Override
    public void updateStudentGroupRecord(StudentGroup studentGroup) throws IOException{
        String sql = "UPDATE "+ config.getConfigurationEntry(STUDENT_GROUP_DATA_TABLE)+ " SET course = '"+studentGroup.getCourse()+"', name = '"+
                studentGroup.getName()+"', codeOfGroup = '"+studentGroup.getCodeOfGroup()+"' WHERE id = "+studentGroup.getID();
        try(Statement statement = connection.createStatement()){
            statement.executeUpdate(sql);
            studentGroup.getGroupComposition().stream().forEach(s-> {
                try {
                    updateStudentRecord(s);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });
            log.info("Student group record was updated");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public StudentGroup getStudentGroupRecordById(int id) throws Exception {
        StudentGroup studentGroup = new StudentGroup();
       String sql = "SELECT * FROM "+ config.getConfigurationEntry(STUDENT_GROUP_DATA_TABLE)+ " WHERE id = "+id;
       try(PreparedStatement statement  = connection.prepareStatement(sql)){
           ResultSet resultSet = statement.executeQuery();
           if(resultSet.next()){
                studentGroup.setID(resultSet.getInt("id"));
                studentGroup.setCourse(resultSet.getInt("course"));
                studentGroup.setName(resultSet.getString("name"));
                studentGroup.setCodeOfGroup(resultSet.getString("codeOfGroup"));
                studentGroup.setGroupComposition(getStudentRecordsByGroupId(id));
                log.info("Student group record was obtained");
           }
           else {
               log.error("Student group record wasn't found");
               throw new Exception("Student group record wasn't found");
           }
       } catch (SQLException e) {
           throw new RuntimeException(e);
       } catch (Exception e) {
           throw e;
       }
       return studentGroup;
    }

    @Override
    public void saveStudentWorKRecord(StudentWork studentWork) throws Exception {
        String sql = "INSERT INTO " + config.getConfigurationEntry(STUDENT_WORK_DATA_TABLE)+" (id,studentId,nameOfWork,disciplineName,mark,homework ,url) VALUES(?,?,?,?,?,?,?)";
        try(PreparedStatement statement  = connection.prepareStatement(sql)){
            statement.setInt(1,studentWork.getID());
            statement.setInt(2,studentWork.getStudentID());
            statement.setString(3,studentWork.getNameOfWork());
            statement.setString(4,studentWork.getDiscipline());
            statement.setInt(5,studentWork.getMark());
            statement.setString(6, Boolean.toString(studentWork.isHomework()));
            statement.setString(7,studentWork.getFileOfWork().getPath());

            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteStudentWorkRecord(StudentWork studentWork) throws IOException {
        deleteRecord(config.getConfigurationEntry(STUDENT_WORK_DATA_TABLE),studentWork.getID());
    }

    @Override
    public void updateStudentWorkRecord(StudentWork studentWork) throws IOException {
        String sql = "UPDATE " + config.getConfigurationEntry(STUDENT_WORK_DATA_TABLE) + " SET nameOfWork = '"+studentWork.getNameOfWork()+"' , " +
                "disciplineName = '" + studentWork.getDiscipline()+"' , mark = '"+studentWork.getMark()+"' , homework = '"+studentWork.isHomework()+
                "' , url = '" + studentWork.getFileOfWork().getPath()+"' WHERE id = "+studentWork.getID();
        try(PreparedStatement statement = connection.prepareStatement(sql)){
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public StudentWork getStudentWorkRecordById(int id) throws Exception {
        StudentWork studentWork = new StudentWork();
        String sql = "SELECT * FROM " + config.getConfigurationEntry(STUDENT_WORK_DATA_TABLE)+" WHERE id = "+ id;
        try(PreparedStatement statement = connection.prepareStatement(sql)){
            ResultSet resultSet = statement.executeQuery(sql);
            if(!resultSet.next()){
                log.error("Student work record wasn't found");
                throw new Exception("Student work record wasn't found");
            }
            studentWork.setID(resultSet.getInt("id"));
            studentWork.setStudentID(resultSet.getInt("studentId"));
            studentWork.setNameOfWork(resultSet.getString("nameOfWork"));
            studentWork.setDiscipline(resultSet.getString("disciplineName"));
            studentWork.setMark(resultSet.getInt("mark"));
            studentWork.setHomework(Boolean.valueOf(resultSet.getString("homeWork")));
            studentWork.setFileOfWork(new File(resultSet.getString("url")));
            log.info("Student work record was obtained");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (Exception e) {
            throw new Exception(e);
        }
        return studentWork;
    }
    public ArrayList<StudentWork> getStudentWorksByStudentId(int id) throws IOException {
        ArrayList<StudentWork> studentWorks = new ArrayList<>();
        String sql = "SELECT * FROM " + config.getConfigurationEntry(STUDENT_WORK_DATA_TABLE)+ "WHERE studentId = "+id;
        try(PreparedStatement statement = connection.prepareStatement(sql)){
            ResultSet resultSet = statement.executeQuery(sql);
            StudentWork studentWork = new StudentWork();
            while(resultSet.next()){
                studentWork.setID(resultSet.getInt("id"));
                studentWork.setStudentID(resultSet.getInt("studentId"));
                studentWork.setNameOfWork(resultSet.getString("nameOfWork"));
                studentWork.setDiscipline(resultSet.getString("nameOfDiscipline"));
                studentWork.setMark(resultSet.getInt("mark"));
                studentWork.setHomework(resultSet.getBoolean("homeWork"));
                studentWork.setFileOfWork(new File(resultSet.getString("url")));

                studentWorks.add(studentWork);
            }
            log.info("Student work records was obtained");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return studentWorks;
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
    public void saveUnEventRecord(UniversityEvent unEvent) throws Exception {
        String sql = "INSERT INTO "+config.getConfigurationEntry(UN_EVENTS_DATA_TABLE) + " (id,scheduleId,place,time,information)" +
                " VALUES(?,?,?,?,?)";
        try(PreparedStatement preparedStatement = connection.prepareStatement(sql)){
            preparedStatement.setInt(1,unEvent.getID());
            preparedStatement.setInt(2,unEvent.getScheduleID());
            preparedStatement.setString(3,unEvent.getPlace());
            preparedStatement.setString(4,unEvent.getTime());
            preparedStatement.setString(5,unEvent.getInformation());
            preparedStatement.executeUpdate();
            log.info("University event record was saved");
        } catch (SQLException e) {
            log.error("University event record already exists");
            throw new Exception("University event record already exists");
        }
    }

    @Override
    public void deleteUnEventRecord(UniversityEvent unEvent) throws IOException {
        deleteRecord(config.getConfigurationEntry(UN_EVENTS_DATA_TABLE),unEvent.getID());
    }

    @Override
    public void updateUnEventRecord(UniversityEvent unEvent) throws IOException {
        String sql = "UPDATE " + config.getConfigurationEntry(UN_EVENTS_DATA_TABLE)+" SET place = '"+unEvent.getPlace()+"' , time = '"+unEvent.getTime()+
                "' , information = '"+unEvent.getInformation()+ "' WHERE id="+unEvent.getID();
        try(PreparedStatement statement = connection.prepareStatement(sql)){
            statement.executeUpdate();
            log.info("University event record was updated");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public UniversityEvent getUnEventRecordById(int id) throws IOException {
        UniversityEvent universityEvent = new UniversityEvent();
        String sql = "SELECT * FROM "+config.getConfigurationEntry(UN_EVENTS_DATA_TABLE)+" WHERE id="+id;
        try(PreparedStatement statement = connection.prepareStatement(sql)){
            ResultSet resultSet = statement.executeQuery();
            if(!resultSet.next()){
                log.error("University event record wasn't found");
                throw new Exception("University event record wasn't found");
            }
            universityEvent.setID(resultSet.getInt("id"));
            universityEvent.setScheduleID(resultSet.getInt("scheduleId"));
            universityEvent.setPlace(resultSet.getString("place"));
            universityEvent.setTime(resultSet.getString("time"));
            universityEvent.setInformation(resultSet.getString("information"));
            log.info("University event record was obtained");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return universityEvent;
    }
    public ArrayList<Event> getUnEventsRecordByScheduleId(int id) throws IOException {
        ArrayList<Event> unEvents = new ArrayList<>();
        String sql = "SELECT * FROM "+config.getConfigurationEntry(LESSON_DATA_TABLE)+" WHERE scheduleId="+id;
        try(PreparedStatement statement = connection.prepareStatement(sql)){
            ResultSet resultSet = statement.executeQuery();
            if(!resultSet.next()){
                log.error("University event record wasn't found");
            }
            UniversityEvent unEvent = new UniversityEvent();
            while (!resultSet.next()){
                unEvent.setID(resultSet.getInt("id"));
                unEvent.setScheduleID(resultSet.getInt("scheduleId"));
                unEvent.setPlace(resultSet.getString("place"));
                unEvent.setTime(resultSet.getString("time"));
                unEvent.setInformation(resultSet.getString("information"));
                unEvents.add(unEvent);
            }
            log.info("University event records was obtained");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return unEvents;
    }
}

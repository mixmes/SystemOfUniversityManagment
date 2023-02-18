package ru.sfedu.services;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.sfedu.Model.*;
import ru.sfedu.utils.ConfigurationUtil;
import javax.xml.bind.*;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.rmi.server.ExportException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static ru.sfedu.Constants.*;

public class XMLDataProvider {
    Logger logger = LogManager.getLogger();
    private Wrapper wrapper = new Wrapper<>();
    private JAXBContext context;
    private ConfigurationUtil config = new ConfigurationUtil();

    private <T>Wrapper<T> getAllRecords(String path) {
        try {
            logger.debug("Trying to deserialize objects ...");
            context = JAXBContext.newInstance(Wrapper.class);
            Unmarshaller unmarshaller = context.createUnmarshaller();
            Wrapper<T> wrap = (Wrapper<T>) unmarshaller.unmarshal(new InputStreamReader(new FileInputStream(path), StandardCharsets.UTF_8));
            logger.debug("Records obtained" + wrapper.getBeans());
            return wrap;
        } catch (JAXBException e) {
            logger.error(e);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        return new Wrapper<T>();
    }
    private<T> void writeAllRecords(Wrapper<T> wrapper , String path){
        try{
            FileOutputStream file = new FileOutputStream(new File(path));
            logger.debug("Updating data source "+path+" with new record");
            context = JAXBContext.newInstance(Wrapper.class);
            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
            marshaller.marshal(wrapper,file);
        } catch (PropertyException e) {
            throw new RuntimeException(e);
        } catch (JAXBException e) {
            throw new RuntimeException(e);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
    public void saveEducationalMaterialRecord(EducationalMaterial educationalMaterial) throws Exception {
        Wrapper<EducationalMaterial> beans = getAllRecords(config.getConfigurationEntry(ED_MATERIAL));
        if(beans.getBeans().parallelStream().noneMatch((s)->s.getID() == educationalMaterial.getID())){
            beans.getBeans().add(educationalMaterial);
            writeAllRecords(beans,config.getConfigurationEntry(ED_MATERIAL));
            logger.info("Educational material record was saved");
        }
        else{
            logger.error("Educational material record already exists");
            throw new Exception("Record already exists");
        }
    }
    public void deleteEducationalMaterialRecord(EducationalMaterial educationalMaterial) throws Exception {
        Wrapper<EducationalMaterial> beans = getAllRecords(config.getConfigurationEntry(ED_MATERIAL));
        if(beans.getBeans().stream().anyMatch(s-> s.getID() == educationalMaterial.getID() ) ){
            beans.getBeans().remove(educationalMaterial);
            writeAllRecords(beans, config.getConfigurationEntry(ED_MATERIAL));
            logger.info("Educational material record was deleted");
        }
        else{
            logger.error("Educational material record wasn't found ID:"+educationalMaterial.getID());
            throw new Exception("Educational material record wasn't found");
        }
    }
    public EducationalMaterial getEducationalMaterialRecordByID(int ID) throws Exception {
        Wrapper<EducationalMaterial> beans = getAllRecords(config.getConfigurationEntry(ED_MATERIAL));
        Optional<EducationalMaterial> educationalMaterial = beans.getBeans().stream().filter(
                s-> s.getID() == ID).findFirst();
        logger.info("Educational material record was found");
        if(!educationalMaterial.isPresent()){
            logger.error("Educational material wasn't found ID:"+ID);
            throw new Exception("Educational material record wasn't found");
        }
        return  educationalMaterial.get();
    }
    public void saveExamRecord(Exam exam) throws Exception {
        Wrapper<Exam> beans = getAllRecords(config.getConfigurationEntry(EXAM));
        if(beans.getBeans().stream().noneMatch(s-> s.getID() == exam.getID())){
            beans.getBeans().add(exam);
            writeAllRecords(beans,config.getConfigurationEntry(EXAM));
            logger.info("Exam record was saved");
        }
        else{
            logger.error("Exam record already exists");
            throw new Exception("Exam record already exists");
        }
    }
    public void deleteExamRecord(Exam exam) throws Exception {
        Wrapper<Exam> beans = getAllRecords(config.getConfigurationEntry(EXAM));
        if(beans.getBeans().stream().anyMatch(s-> s.getID() == exam.getID())){
            beans.getBeans().remove(exam);
            writeAllRecords(beans, config.getConfigurationEntry(EXAM));
            logger.info("Exam record was saved");
        }
        else {
            logger.error("Exam record wasn't found ID:",exam.getID());
            throw new Exception("Exam record wasn't found");
        }
    }
    public Exam getExamRecordByID(int ID) throws Exception {
        Wrapper<Exam> beans = getAllRecords(config.getConfigurationEntry(EXAM));
        Optional<Exam> exam = beans.getBeans().stream().filter(s-> s.getID() == ID).findFirst();
        if(!exam.isPresent()){
            logger.error("Exam record wasn't found ID:"+ID);
            throw new Exception("Exam record wasn't found");
        }
        logger.info("Exam record was found");
        return exam.get();
    }
    public void saveLectionRecord(Lection lection) throws Exception {
        Wrapper<Lection> beans = getAllRecords(config.getConfigurationEntry(LECTION));
        if(beans.getBeans().stream().noneMatch(s ->s.getID() == lection.getID())){
            beans.getBeans().add(lection);
            writeAllRecords(beans, config.getConfigurationEntry(LECTION));
            logger.info("Lection record was saved");
        }else {
            logger.error("Lection record already exists");
            throw new Exception("Lection record already exists");
        }
;    }
    public void deleteLectionRecord(Lection lection) throws Exception {
        Wrapper<Lection> beans = getAllRecords(config.getConfigurationEntry(LECTION));
        if(beans.getBeans().stream().anyMatch(s-> s.getID() == lection.getID())){
            beans.getBeans().remove(lection);
            writeAllRecords(beans, config.getConfigurationEntry(LECTION));
            logger.info("Lection record was deleted");
        }
        else {
            logger.error("Lection record wasn't found ID:"+lection.getID());
            throw new Exception("Lection record wasn't found");
        }
    }
    public Lection getLectionRecordByID(int ID) throws Exception {
        Wrapper<Lection> beans =  getAllRecords(config.getConfigurationEntry(LECTION));
        Optional<Lection> lection = beans.getBeans().stream().filter(s-> s.getID() == ID).findFirst();
        if(!lection.isPresent()){
            logger.error("Lection record wasn't found ID:"+ID);
            throw new Exception("Lection record wasn't found");
        }
        logger.info("Lection record was found");
        return lection.get();
    }
    public void saveLessonRecord(Lesson lesson) throws Exception {
        Wrapper<Lesson> beans = getAllRecords(config.getConfigurationEntry(LESSON));
        if(beans.getBeans().stream().noneMatch(s-> s.getID() == lesson.getID())){
            beans.getBeans().add(lesson);
            writeAllRecords(beans,config.getConfigurationEntry(LESSON));
            logger.info("Lesson record was saved");
        }
        else {
            logger.error("Lesson record already exists");
            throw new Exception("Lesson record already exists");
        }
    }
    public void deleteLessonRecord(Lesson lesson) throws Exception {
        Wrapper<Lesson> beans = getAllRecords(config.getConfigurationEntry(LESSON));
        if(beans.getBeans().stream().anyMatch(s-> s.getID() == lesson.getID())){
            beans.getBeans().remove(lesson);
            writeAllRecords(beans, config.getConfigurationEntry(LESSON));
            logger.info("Lesson record was deleted");
        }
        else {
            logger.error("Lesson record wasn't found ID:"+lesson.getID());
            throw new Exception("Lesson record wasn't found");
        }
    }
    public Lesson getLessonRecordByID(int ID) throws Exception {
        Wrapper<Lesson> beans = getAllRecords(config.getConfigurationEntry(LESSON));
        Optional<Lesson> lesson = beans.getBeans().stream().filter(s-> s.getID() == ID).findFirst();
        if(!lesson.isPresent()){
            logger.error("Lesson record wasn't found ID:"+ID);
            throw new Exception("Lesson record wasn't found");
        }
        logger.info("Lesson record was found");
        return lesson.get();
    }
    public void saveMarbookRecord(MarkBook markBook) throws Exception {
        Wrapper<MarkBook> beans = getAllRecords(config.getConfigurationEntry(MARKBOOK));
        if(beans.getBeans().stream().noneMatch(s-> s.getID() == markBook.getID())){
            beans.getBeans().add(markBook);
            writeAllRecords(beans, config.getConfigurationEntry(MARKBOOK));
            logger.info("Markbook record was saved");
        }
        else {
            logger.info("Markbook record already exists");
            throw new Exception("Markbook record already exists");
        }
    }
    public void deleteMarkBookRecord(MarkBook markBook) throws Exception {
        Wrapper<MarkBook> beans = getAllRecords(config.getConfigurationEntry(MARKBOOK));
        if(beans.getBeans().stream().anyMatch(s-> s.getID() == markBook.getID())){
            beans.getBeans().remove(markBook);
            writeAllRecords(beans,config.getConfigurationEntry(MARKBOOK));
            logger.info("Markbook record was deleted");
        }
        else {
            logger.error("Markbook record wasn't found ID:"+markBook.getID());
            throw new Exception("Markbook record wasn't found");
        }
    }
    public MarkBook getMarkBookRecordByID(int ID) throws Exception {
        Wrapper<MarkBook> beans = getAllRecords(config.getConfigurationEntry(MARKBOOK));
        Optional<MarkBook> markBook = beans.getBeans().stream().filter(s->s.getID() == ID).findFirst();
        if(!markBook.isPresent()){
            logger.error("Markbook record wasn't found ID:"+ID);
            throw new Exception("Markbook record wasn't found");
        }
        logger.info("Markbook record was found");
        return markBook.get();
    }
    public void savePracticalTaskRecord(PracticalTask practicalTask) throws Exception {
        Wrapper<PracticalTask> beans = getAllRecords(config.getConfigurationEntry(PRACT_TASK));
        if(beans.getBeans().stream().noneMatch(s-> s.getID() == practicalTask.getID())){
            beans.getBeans().add(practicalTask);
            writeAllRecords(beans,config.getConfigurationEntry(PRACT_TASK));
            logger.info("Practical task record was saved");
        }
        else{
            logger.error("Practical task record already exists");
            throw new Exception("Practical task record already exists");
        }
    }
    public void deletePracticalTaskRecord(PracticalTask practicalTask) throws Exception {
        Wrapper<PracticalTask> beans = getAllRecords(config.getConfigurationEntry(PRACT_TASK));
        if(beans.getBeans().stream().anyMatch(s-> s.getID() == practicalTask.getID())){
            beans.getBeans().remove(practicalTask);
            writeAllRecords(beans,config.getConfigurationEntry(PRACT_TASK));
            logger.info("Practical task record was deleted");
        }
        else {
            logger.error("Practical task record wasn't found ID:"+practicalTask.getID());
            throw new Exception("Practical task record wasn't found");
        }
    }
    public PracticalTask getPracticalTaskRecordByID(int ID) throws Exception {
        Wrapper<PracticalTask> beans = getAllRecords(config.getConfigurationEntry(PRACT_TASK));
        Optional<PracticalTask> practicalTask = beans.getBeans().stream().filter(s->s.getID() == ID).findFirst();
        if(!practicalTask.isPresent()){
            logger.error("Practical task record wasn't found ID:"+ID);
            throw new Exception("Practical task record wasn't found");
        }
        logger.info("Practical task record was found");
        return practicalTask.get();
    }
    public void saveExamScheduleRecord(ScheduleOfExams scheduleOfExams) throws Exception {
        Wrapper<ScheduleOfExams> beans = getAllRecords(config.getConfigurationEntry(EXAM_SCHEDULE));
        if(beans.getBeans().stream().noneMatch(s->s.getID() == scheduleOfExams.getID())){
            beans.getBeans().add(scheduleOfExams);
            writeAllRecords(beans,config.getConfigurationEntry(EXAM_SCHEDULE));
            logger.info("Exam schedule record was saved");
        }
        else {
            logger.error("Exam schedule record already exists");
            throw new Exception("Exam schedule record already exists");
        }
    }
    public void deleteExamScheduleRecord(ScheduleOfExams scheduleOfExams) throws Exception {
        Wrapper<ScheduleOfExams> beans = getAllRecords(config.getConfigurationEntry(EXAM_SCHEDULE));
        if(beans.getBeans().stream().anyMatch(s-> s.getID() == scheduleOfExams.getID())){
            beans.getBeans().remove(scheduleOfExams);
            writeAllRecords(beans,config.getConfigurationEntry(EXAM_SCHEDULE));
            logger.info("Exam schedule record was deleted");
        }
        else{
            logger.error("Exam schedule record wasn't found ID:"+scheduleOfExams.getID());
            throw new Exception("Exam schedule record wasn't found");
        }
    }
    public ScheduleOfExams getScheduleOfExamsRecordByID(int ID) throws Exception {
        Wrapper<ScheduleOfExams> beans = getAllRecords(config.getConfigurationEntry(EXAM_SCHEDULE));
        Optional<ScheduleOfExams> scheduleOfExams = beans.getBeans().stream().filter(s->s.getID() == ID).findFirst();
        if(!scheduleOfExams.isPresent()){
            logger.error("Exam schedule record wasn't found ID:"+ID);
            throw new Exception("Exam schedule record wasn't found");
        }
        logger.info("Exam record was found");
        return scheduleOfExams.get();
    }
    public void saveScheduleOfLessonsRecord(ScheduleOfLessons schedule) throws Exception {
        Wrapper<ScheduleOfLessons> beans = getAllRecords(config.getConfigurationEntry(LESSON_SCHEDULE));
        if(beans.getBeans().stream().noneMatch(s-> s.getID() == schedule.getID())){
            beans.getBeans().add(schedule);
            writeAllRecords(beans,config.getConfigurationEntry(LESSON_SCHEDULE));
            logger.info("Lesson schedule record was saved");
        }
        else {
            logger.error("Lesson schedule record already exists");
            throw new Exception("Lesson schedule record already exists");
        }
    }
    public void deleteScheduleOfLessonRecord(ScheduleOfLessons schedule) throws Exception {
        Wrapper<ScheduleOfLessons> beans = getAllRecords(config.getConfigurationEntry(LESSON_SCHEDULE));
        if(beans.getBeans().stream().anyMatch(s->s.getID() == schedule.getID())){
            beans.getBeans().remove(schedule);
            writeAllRecords(beans,config.getConfigurationEntry(LESSON_SCHEDULE));
            logger.info("Lesson schedule record was deleted");
        }
        else {
            logger.error("Lesson schedule record wasn't found ID:" + schedule.getID());
            throw new Exception("Lesson schedule record wasn't found");
        }
    }
    public ScheduleOfLessons getScheduleOfLessonsRecordByID(int ID) throws Exception {
        Wrapper<ScheduleOfLessons> beans = getAllRecords(config.getConfigurationEntry(LESSON_SCHEDULE));
        Optional<ScheduleOfLessons> schedule = beans.getBeans().stream().filter(s->s.getID() == ID).findFirst();
        if(!schedule.isPresent()){
            logger.error("Lesson schedule record wasn't found ID:"+ID);
            throw new Exception("Lesson schedule record wasn't found");
        }
        logger.info("Lesson schedule record was found");
        return  schedule.get();
    }
    public void saveScheduleOfUnEvRecord(ScheduleOfUniversityEvents schedule) throws Exception {
        Wrapper<ScheduleOfUniversityEvents> beans = getAllRecords(config.getConfigurationEntry(UNIVERSITY_EVENTS_SCHEDULE));
        if(beans.getBeans().stream().noneMatch(s->s.getID() == schedule.getID())){
            beans.getBeans().add(schedule);
            writeAllRecords(beans,config.getConfigurationEntry(UNIVERSITY_EVENTS_SCHEDULE));
            logger.info("University event schedule record was added");
        }
        else {
            logger.error("University event schedule record already exists");
            throw new Exception("University event schedule record already exists");
        }
    }
    public void deleteScheduleOfUnEvRecord(ScheduleOfUniversityEvents schedule) throws Exception {
        Wrapper<ScheduleOfUniversityEvents> beans =  getAllRecords(config.getConfigurationEntry(UNIVERSITY_EVENTS_SCHEDULE));
        if(beans.getBeans().stream().anyMatch(s->s.getID() ==schedule.getID())){
            beans.getBeans().remove(schedule);
            writeAllRecords(beans, config.getConfigurationEntry(UNIVERSITY_EVENTS_SCHEDULE));
            logger.info("University event schedule record was added");
        }
        else {
            logger.error("University event schedule record wasn't found ID:"+schedule.getID());
            throw new Exception("University event schedule record wasn't found");
        }
    }
    public ScheduleOfUniversityEvents getScheduleOfUnEvRecordByID(int ID) throws Exception {
        Wrapper<ScheduleOfUniversityEvents> beans = getAllRecords(config.getConfigurationEntry(UNIVERSITY_EVENTS_SCHEDULE));
        Optional<ScheduleOfUniversityEvents> schedule =  beans.getBeans().stream().filter(s->s.getID() == ID).findFirst();
        if(!schedule.isPresent()){
            logger.error("University event schedule record wasn't found ID:"+ID);
            throw new Exception("University event schedule record wasn't found");
        }
        logger.info("University event record was found");
        return schedule.get();
    }
    public void saveStudentRecord(Student student) throws Exception {
        Wrapper<Student> beans = getAllRecords(config.getConfigurationEntry(STUDENT));
        if(beans.getBeans().stream().noneMatch(s->s.getID() == student.getID())){
            beans.getBeans().add(student);
            writeAllRecords(beans,config.getConfigurationEntry(STUDENT));
            logger.info("Student record was saved");
        }
        else{
            logger.error("Student record already exists");
            throw new Exception("Student record already exists");
        }
    }
    public void deleteStudentRecord(Student student) throws Exception {
        Wrapper<Student> beans = getAllRecords(config.getConfigurationEntry(STUDENT));
        if(beans.getBeans().stream().anyMatch(s-> s.getID() == student.getID())){
            beans.getBeans().remove(student);
            writeAllRecords(beans,config.getConfigurationEntry(STUDENT));
            logger.info("Student record was deleted");
        }
        else {
            logger.error("Student record wasn't found ID:"+student.getID());
            throw new Exception("Student record wasn't found");
        }
    }
    public Student getStudentRecordByID(int ID) throws Exception {
        Wrapper<Student> beans = getAllRecords(config.getConfigurationEntry(STUDENT));
        Optional<Student> student = beans.getBeans().stream().filter(s-> s.getID() == ID).findFirst();
        if(!student.isPresent()){
            logger.error("Student record wasn't found ID:"+ID);
            throw new Exception("Student record wasn't found");
        }
        logger.info("Student record was found");
        return student.get();
    }
    public void saveStudentGroupRecord(StudentGroup studentGroup) throws Exception {
        Wrapper<StudentGroup> beans = getAllRecords(config.getConfigurationEntry(STUDENT_GROUP));
        if(beans.getBeans().stream().noneMatch(s->s.getID() == studentGroup.getID())){
            beans.getBeans().add(studentGroup);
            writeAllRecords(beans,config.getConfigurationEntry(STUDENT_GROUP));
            logger.info("Student group record was saved");
        }
        else{
            logger.error("Student record group already exists");
            throw new Exception("Student group record already exists");
        }
    }
    public void deleteStudentGroupRecord(StudentGroup studentGroup) throws Exception {
        Wrapper<StudentGroup> beans = getAllRecords(config.getConfigurationEntry(STUDENT_GROUP));
        if(beans.getBeans().stream().anyMatch(s->s.getID() == studentGroup.getID())){
            beans.getBeans().remove(studentGroup);
            writeAllRecords(beans,config.getConfigurationEntry(STUDENT_GROUP));
            logger.info("Student group record was deleted");
        }else {
            logger.error("Student group record wasn't found ID:"+studentGroup.getID());
            throw new Exception("Student group record wasn't found");
        }
    }
    public StudentGroup getStudentGroupRecordByID(int ID) throws Exception {
        Wrapper<StudentGroup> beans = getAllRecords(config.getConfigurationEntry(STUDENT_GROUP));
        Optional<StudentGroup> studentGroup = beans.getBeans().stream().filter(s->s.getID() == ID).findFirst();
        if(!studentGroup.isPresent()){
            logger.error("Student group record wasn't found ID:"+ID);
            throw new Exception("Student group record wasn't found");
        }
        logger.info("Student group record was found");
        return studentGroup.get();
    }
    public void saveTeacherRecord(Teacher teacher) throws Exception {
        Wrapper<Teacher> beans = getAllRecords(config.getConfigurationEntry(TEACHER));
        if(beans.getBeans().stream().noneMatch(s->s.getID() == teacher.getID())){
            beans.getBeans().add(teacher);
            writeAllRecords(beans,config.getConfigurationEntry(TEACHER));
            logger.info("Teacher record was saved");
        }
        else {
            logger.error("Teacher record already exists");
            throw new Exception("Teacher record already exists");
        }
    }
    public void deleteTeacherRecord(Teacher teacher) throws Exception {
        Wrapper<Teacher> beans =getAllRecords(config.getConfigurationEntry(TEACHER));
        if(beans.getBeans().stream().anyMatch(s->s.getID() == teacher.getID())){
            beans.getBeans().remove(teacher);
            writeAllRecords(beans,config.getConfigurationEntry(TEACHER));
            logger.info("Teacher record was deleted");
        }
        else {
            logger.error("Teacher record wasn't found ID:"+ teacher.getID());
            throw new Exception("Teacher record wasn't found");
        }
    }
    public Teacher getTeacherRecordByID(int ID) throws Exception {
        Wrapper<Teacher> beans = getAllRecords(config.getConfigurationEntry(TEACHER));
        Optional<Teacher> teacher = beans.getBeans().stream().filter(s->s.getID() == ID).findFirst();
        if(!teacher.isPresent()){
            logger.error("Teacher record wasn't found ID:"+ID);
            throw new Exception("Teacher record wasn't found");
        }
        logger.info("Teacher record was found");
        return teacher.get();
    }
    public void saveIssueRecord(Issue issue) throws Exception {
        Wrapper<Issue> beans = getAllRecords(config.getConfigurationEntry(ISSUE));
        if(beans.getBeans().stream().noneMatch(s->s.getID() == issue.getID())){
            beans.getBeans().add(issue);
            writeAllRecords(beans,config.getConfigurationEntry(ISSUE));
            logger.info("Issue record was saved");
        }
        else {
            logger.error("Issue record already exists");
            throw new Exception("Issue record already exists");
        }
    }
    public void deleteIssueRecord(Issue issue) throws Exception {
        Wrapper<Issue> beans =  getAllRecords(config.getConfigurationEntry(ISSUE));
        if(beans.getBeans().stream().anyMatch(s->s.getID() == issue.getID())){
            beans.getBeans().remove(issue);
            writeAllRecords(beans,config.getConfigurationEntry(ISSUE));
            logger.info("Issue record was deleted");
        }
        else {
            logger.error("Issue record wasn't found ID:"+issue.getID());
            throw new Exception("Issue record wasn't found");
        }
    }
    public Issue getIssueRecordByID(int ID) throws Exception {
        Wrapper<Issue> beans = getAllRecords(config.getConfigurationEntry(ISSUE));
        Optional<Issue> issue = beans.getBeans().stream().filter(s->s.getID() == ID).findFirst();
        if(!issue.isPresent()){
            logger.error("Issue record wasn't found ID:"+ID);
            throw new Exception("Issue record wasn't found");
        }
        logger.info("Issue record was found");
        return issue.get();
    }
    public void saveDisciplineRecord(Discipline discipline) throws Exception {
        Wrapper<Discipline> beans = getAllRecords(config.getConfigurationEntry(DISCIPLINE));
        if(beans.getBeans().stream().noneMatch(s->s.getID() == discipline.getID())){
            beans.getBeans().add(discipline);
            writeAllRecords(beans,config.getConfigurationEntry(DISCIPLINE));
            logger.info("Discipline record was saved");
            saveEducationalMaterialRecord(discipline.getEducationalMaterial());
            saveIssueRecord(discipline.getIssue());
        }
        else {
            logger.error("Discipline record already exists");
            throw new Exception("Discipline record already exist");
        }
    }
    public void deleteDisciplineRecord(Discipline discipline) throws Exception {
        Wrapper<Discipline> beans = getAllRecords(config.getConfigurationEntry(DISCIPLINE));
        if(beans.getBeans().stream().anyMatch(s->s.getID() == discipline.getID())){
            beans.getBeans().remove(discipline);
            writeAllRecords(beans,config.getConfigurationEntry(DISCIPLINE));
            logger.info("Discipline record was deleted");
            deleteIssueRecord(discipline.getIssue());
            deleteEducationalMaterialRecord(discipline.getEducationalMaterial());
        }
        else {
            logger.error("Discipline record wasn't found ID:"+discipline.getID());
            throw new Exception("Discipline record wasn't found");
        }
    }
    public Discipline getDisciplineRecordByID(int ID) throws Exception {
        Wrapper<Discipline> beans = getAllRecords(config.getConfigurationEntry(DISCIPLINE));
        Optional<Discipline> disc = beans.getBeans().stream().filter(s->s.getID() == ID).findFirst();
        if(!disc.isPresent()){
            logger.error("Discipline record wasn't found ID:"+ID);
            throw new Exception("Discipline record wasn't found");
        }
        logger.info("Discipline record wasn't found");
        Discipline discipline = disc.get();
        discipline.setEducationalMaterial(getEducationalMaterialRecordByID(discipline.getID()));
        discipline.setIssue(getIssueRecordByID(discipline.getID()));
        return  discipline;
    }

}
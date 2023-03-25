package ru.sfedu.services;

import org.junit.jupiter.api.Test;
import ru.sfedu.Constants;
import ru.sfedu.HistoryContent.HistoryContent;
import ru.sfedu.Model.Discipline;
import ru.sfedu.Model.Teacher;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

class HistoryClientTest {
    @Test
    public void testSaveChanges() throws Exception {
        HistoryService historyService = new HistoryService();

        Teacher teacher = new Teacher(1, "Иванов Иван Иванович");
        HistoryContent historyContentTeacher = new HistoryContent(teacher);
        historyContentTeacher.setCreatedDate(new Date());
        historyService.saveChanges(historyContentTeacher);

        Discipline discipline = new Discipline(1,1,"Math","Exam");
        teacher.setDisciplines(new ArrayList<>(List.of(discipline)));

        historyContentTeacher.setCreatedDate(new Date());
        historyContentTeacher.setMethodName("appendDiscipline()");
        historyContentTeacher.setStatus(Constants.Status.SUCCESS);

        historyService.saveChanges(historyContentTeacher);

        teacher.setName("Иванов Александ Иванович");
        historyContentTeacher.setCreatedDate(new Date());
        historyContentTeacher.setMethodName("setName()");
        historyContentTeacher.setStatus(Constants.Status.SUCCESS);

        historyService.saveChanges(historyContentTeacher);
    }
}
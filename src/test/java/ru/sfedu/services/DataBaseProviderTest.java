package ru.sfedu.services;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.sfedu.Model.*;
import ru.sfedu.utils.ConfigurationUtil;
import static ru.sfedu.Constants.*;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class DataBaseProviderTest {
    public static final ConfigurationUtil config = new ConfigurationUtil();
    public static final Discipline math = new Discipline(1,"Math","Exam");
    public static final Lection lection = new Lection(1,1,"/home/../","Lection 21/02/2012");
    public static final PracticalTask practTask = new PracticalTask(1,1,"/home/...","Deadline 21.02.2012");
    public static final EducationalMaterial edMat = new EducationalMaterial(1,1);
    public static DataBaseProvider db;
    static {
        try {
            db = new DataBaseProvider();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    @BeforeAll
    static void init(){
        edMat.setTasks(new ArrayList<>(List.of(practTask)));
        edMat.setLections(new ArrayList<>(List.of(lection)));
    }
    @Test
    void saveDisciplineRecord() throws IOException {
        math.setTeacherID(1);
        db.saveDisciplineRecord(math);
    }
    @Test
    void testSaveLectionRecord() throws Exception {
        db.saveLectionRecord(lection);

        assertEquals(lection,db.getLectionRecordById(lection.getID()));

        db.deleteRecord(config.getConfigurationEntry(LECTION_DATA_TABLE), lection.getID());
    }
    @Test
    void testSaveExistingLectionRecord() throws Exception {
        db.saveLectionRecord(lection);

        Exception exception = assertThrows(Exception.class,()->{db.saveLectionRecord(lection);});
        assertEquals("Record already exists",exception.getMessage());

        db.deleteRecord(config.getConfigurationEntry(LECTION_DATA_TABLE), lection.getID());
    }
    @Test
    void testUpdateLectionRecord() throws Exception {
        db.saveLectionRecord(lection);
        lection.setInformation("_");
        db.updateLectionRecord(lection);

        assertEquals(lection,db.getLectionRecordById(lection.getID()));

        db.deleteRecord(config.getConfigurationEntry(LECTION_DATA_TABLE), lection.getID());
        lection.setInformation("Lection 21/02/2012");
    }
    @Test
    void testSavePractTaskRecord() throws Exception {
        db.savePracticalTaskRecord(practTask);

        assertEquals(practTask,db.getPracticalTaskRecordById(practTask.getID()));

        db.deleteRecord(config.getConfigurationEntry(PRACT_TASK_DATA_TABLE), practTask.getID());
    }
    @Test
    void testSaveExistingPractTaskRecord() throws Exception {
        db.savePracticalTaskRecord(practTask);

        Exception exception = assertThrows(Exception.class,()->{
            db.savePracticalTaskRecord(practTask);
        });
        assertEquals("Pract task record already exists",exception.getMessage());

        db.deleteRecord(config.getConfigurationEntry(PRACT_TASK_DATA_TABLE), practTask.getID());
    }
    @Test
    void testUpdatePractTaskRecord() throws Exception {
        db.savePracticalTaskRecord(practTask);
        practTask.setInformation("_");
        db.updatePracticalTaskRecord(practTask);

        assertEquals("_",db.getPracticalTaskRecordById(practTask.getID()).getInformation());

        db.deleteRecord(config.getConfigurationEntry(PRACT_TASK_DATA_TABLE),practTask.getID());
        practTask.setInformation("Deadline 21.02.2012");
    }
    @Test
    void saveEducationalMaterialRecord() throws Exception {
        db.saveEducationalMaterialRecord(edMat);

        assertEquals(edMat,db.getEducationalMaterialRecordByID(edMat.getID()));

        db.deleteRecord(config.getConfigurationEntry(ED_MAT_DATA_TABLE), edMat.getID());
        edMat.getLections().stream().forEach(s-> {
            try {
                db.deleteRecord(config.getConfigurationEntry(LECTION_DATA_TABLE),s.getID());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        edMat.getTasks().stream().forEach(s->{
            try {
                db.deleteRecord(config.getConfigurationEntry(PRACT_TASK_DATA_TABLE),s.getID());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        
    }
    @Test
    void saveExistingEdMatRecord() throws Exception {
        db.saveEducationalMaterialRecord(edMat);

        Exception exception = assertThrows(Exception.class,()->{db.saveEducationalMaterialRecord(edMat);});
        assertEquals("Education material record already exists",exception.getMessage());

        db.deleteRecord(config.getConfigurationEntry(ED_MAT_DATA_TABLE), edMat.getID());
        edMat.getLections().stream().forEach(s-> {
            try {
                db.deleteRecord(config.getConfigurationEntry(LECTION_DATA_TABLE),s.getID());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        edMat.getTasks().stream().forEach(s->{
            try {
                db.deleteRecord(config.getConfigurationEntry(PRACT_TASK_DATA_TABLE),s.getID());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

}
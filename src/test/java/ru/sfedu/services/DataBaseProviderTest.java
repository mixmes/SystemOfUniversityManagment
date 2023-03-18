package ru.sfedu.services;

import org.junit.jupiter.api.Test;
import ru.sfedu.Model.*;
import ru.sfedu.utils.ConfigurationUtil;
import static ru.sfedu.Constants.*;
import java.io.IOException;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class DataBaseProviderTest {
    public static final ConfigurationUtil config = new ConfigurationUtil();
    public static final Discipline math = new Discipline(1,"Math","Exam");
    public static final Lection lection = new Lection(1,1,"/home/../","Lection 21/02/2012");
    public static DataBaseProvider db;
    static {
        try {
            db = new DataBaseProvider();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
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

}
package ru.sfedu.services;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.sfedu.Model.Discipline;
import ru.sfedu.Model.EducationalMaterial;
import ru.sfedu.Model.Lection;
import ru.sfedu.Model.PracticalTask;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MongoDBTest {
    private static final Discipline disc = new Discipline(1,1,"Math","Exam");
    private static final EducationalMaterial edMat = new EducationalMaterial(1,1);
    private static final Lection lection = new Lection(1,1,"/home/mihail/...","11.11.2023");
    private static final PracticalTask practTask =  new PracticalTask(1,1,"/home/mihail/..","Deadline 21.11.2023");
    private static final MongoDB mongo =new MongoDB("university");
    @BeforeAll
    static void init(){
        edMat.setTasks(new ArrayList<>(List.of(practTask)));
        edMat.setLections(new ArrayList<>(List.of(lection)));
        disc.setEducationalMaterial(edMat);
    }
    @Test
    void saveDiscipline() {

    }
}
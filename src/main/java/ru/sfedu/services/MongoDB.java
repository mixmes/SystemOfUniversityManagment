package ru.sfedu.services;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import ru.sfedu.Model.Discipline;

import java.util.List;
import java.util.Map;

public class MongoDB {
    private MongoClient client;
    private MongoDatabase database;


    public MongoDB(String dbName) {
        this.client = MongoClients.create();
        database = client.getDatabase(dbName);
    }


}

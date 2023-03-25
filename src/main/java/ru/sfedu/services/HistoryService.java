package ru.sfedu.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.mongodb.BasicDBObject;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.util.JSON;
import org.bson.Document;

import java.text.SimpleDateFormat;
import java.util.TimeZone;

import static ru.sfedu.Constants.*;

public class HistoryService {
    private MongoCollection collection;
    private final SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm");

    public HistoryService() {
        MongoClient client = MongoClients.create(MONGO_URL);
        MongoDatabase db = client.getDatabase(DB_NAME);
        collection = db.getCollection(NAME_COLLECTION);
    }
    public String objectToJSONArray(Object object) throws JsonProcessingException {
        dateFormat.setTimeZone(TimeZone.getTimeZone("Europe/Moscow"));
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setDateFormat(dateFormat);
        return objectMapper.writeValueAsString(object);
    }
    public void saveChanges(HistoryContent content) throws JsonProcessingException {
        BasicDBObject dbObject = (BasicDBObject) JSON.parse(objectToJSONArray(content));

        collection.insertOne(new Document(dbObject));
    }
}

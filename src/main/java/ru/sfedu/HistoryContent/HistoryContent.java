package ru.sfedu.HistoryContent;

import ru.sfedu.Constants;

import java.util.Date;

import static ru.sfedu.Constants.SYSTEM_ACTOR;

public class HistoryContent {
    private int id;
    private String className ;
    private Date createdDate;
    private String actor = SYSTEM_ACTOR;
    private String methodName ;
    private Object object;
    private Constants.Status status;

    public HistoryContent( Object object) {
        this.className = Object.class.getSimpleName();
        this.object = object;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public String getActor() {
        return actor;
    }

    public void setActor(String actor) {
        this.actor = actor;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public Object getObject() {
        return object;
    }

    public void setObject(Object object) {
        this.object = object;
    }

    public Constants.Status getStatus() {
        return status;
    }

    public void setStatus(Constants.Status status) {
        this.status = status;
    }
}

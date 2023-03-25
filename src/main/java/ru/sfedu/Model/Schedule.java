package ru.sfedu.Model;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.sfedu.Constants;

import java.util.ArrayList;
import java.util.Objects;

public class Schedule  {
    private static Logger log = LogManager.getLogger();
    private int ID;
    private int semester;
    private Constants.TypeOfSchedule type;
    private ArrayList<Event> events = new ArrayList<>();
    public Schedule(){}
    public Schedule(int ID, int semester, Constants.TypeOfSchedule type){
        this.ID = ID;
        this.semester = semester;
        this.type = type;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public int getSemester() {
        return semester;
    }

    public void setSemester(int semester) {
        this.semester = semester;
    }

    public Constants.TypeOfSchedule getType() {
        return type;
    }

    public void setType(Constants.TypeOfSchedule type) {
        this.type = type;
    }

    public ArrayList<Event> getEvents() {
        return events;
    }

    public void setEvents(ArrayList<Event> events) {
        this.events = events;
    }
    public void appendEventToSchedule(Event event) throws Exception {
        if(events.stream().noneMatch(s->s.equals(event))){
            events.add(event);
            log.info("Event was appended");
        }
        else {
            log.error("Event record already exists");
            throw new Exception("Event record already exists");
        }
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Schedule schedule = (Schedule) o;
        return ID == schedule.ID && semester == schedule.semester;
    }

    @Override
    public int hashCode() {
        return Objects.hash(ID, semester);
    }
}

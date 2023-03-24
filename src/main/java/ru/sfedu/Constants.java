package ru.sfedu;

public class Constants {
    public static final String USER_DATA_BASE = "userDataBase";
    public static final String URL_DATA_BASE = "urlDataBase";
    public static final String PASS_DATA_BASE = "passDataBase";
    public static final String DISCIPLINE_DATA_TABLE = "disciplineTable";
    public static final String ED_MAT_DATA_TABLE = "edMatTable";
    public static final String EXAM_DATA_TABLE= "examTable";
    public static final String LECTION_DATA_TABLE = "lectionTable";
    public static final String LESSON_DATA_TABLE = "lessonTable";
    public static final String PRACT_TASK_DATA_TABLE = "practTaskTable";
    public static final String SCHEDULE_DATA_TABLE = "schedulesTable";
    public static final String STUDENT_DATA_TABLE = "studentTable";
    public static final String STUDENT_GROUP_DATA_TABLE = "studentGroupTable";
    public static final String STUDENT_WORK_DATA_TABLE = "studentWorkTable";
    public static final String TEACHER_DATA_TABLE = "teacherTable";
    public static final String UN_EVENTS_DATA_TABLE = "unEventsTable";
    public static final int MAX_MARK = 10;

    public enum TypeOfSchedule {
        LESSONS,
        UNIVERSITY_EVENTS,
        EXAMS
    }
    public enum DayOfWeek{
        SUNDAY,
        MONDAY,
        TUESDAY,
        WEDNESDAY,
        THURSDAY,
        FRIDAY,
        SATURDAY

    }
}

package com.example.stiqr_java.recyclerview.model;

public class ScheduleModel {
    String subject, professor, time, room;

    public String getSubject() {
        return subject;
    }

    public String getProfessor() {
        return professor;
    }

    public String getTime() {
        return time;
    }

    public String getRoom() {
        return room;
    }

    public ScheduleModel(String subject, String professor, String time, String room) {
        this.subject = subject;
        this.professor = professor;
        this.time = time;
        this.room = room;
    }
}

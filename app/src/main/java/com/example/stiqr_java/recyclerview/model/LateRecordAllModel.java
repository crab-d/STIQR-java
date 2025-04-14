package com.example.stiqr_java.recyclerview.model;

public class LateRecordAllModel {
    String lateArrival, date, subject, schedule, name, section;

    public String getLateArrival() {
        return lateArrival;
    }

    public String getDate() {
        return date;
    }

    public String getSubject() {
        return subject;
    }

    public String getSchedule() {
        return schedule;
    }

    public String getName() {
        return name;
    }

    public String getSection() {
        return section;
    }

    public LateRecordAllModel(String lateArrival, String date, String subject, String schedule, String name, String section) {
        this.lateArrival = lateArrival;
        this.date = date;
        this.subject = subject;
        this.schedule = schedule;
        this.name = name;
        this.section = section;
    }
}

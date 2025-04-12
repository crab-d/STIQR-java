package com.example.stiqr_java.recyclerview.model;

public class LateRecordsModel {
    String date, subject, teacher, lateArrival, schedule;

    public String getDate() {
        return date;
    }

    public String getSubject() {
        return subject;
    }

    public String getTeacher() {
        return teacher;
    }

    public String getLateArrival() {
        return lateArrival;
    }

    public String getSchedule() {
        return schedule;
    }

    public LateRecordsModel(String date, String subject, String teacher, String lateArrival, String schedule) {
        this.date = date;
        this.subject = subject;
        this.teacher = teacher;
        this.lateArrival = lateArrival;
        this.schedule = schedule;
    }
}

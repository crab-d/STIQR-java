package com.example.stiqr_java.recyclerview.model;

public class StaffCSRecordModel {
    String reference, studentNumber, name, status, task, date;

    public String getReference() {
        return reference;
    }

    public String getStudentNumber() {
        return studentNumber;
    }

    public String getName() {
        return name;
    }

    public String getStatus() {
        return status;
    }

    public String getTask() {
        return task;
    }

    public String getDate() {
        return date;
    }

    public StaffCSRecordModel(String reference, String studentNumber, String name, String status, String task, String date) {
        this.reference = reference;
        this.studentNumber = studentNumber;
        this.name = name;
        this.status = status;
        this.task = task;
        this.date = date;
    }
}

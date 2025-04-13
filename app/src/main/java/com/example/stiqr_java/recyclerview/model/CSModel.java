package com.example.stiqr_java.recyclerview.model;

public class CSModel {
    String name, status, completeDate, task, reference, studentNumber;

    public String getName() {
        return name;
    }

    public String getStatus() {
        return status;
    }

    public String getCompleteDate() {
        return completeDate;
    }

    public String getTask() {
        return task;
    }

    public String getReference() {
        return reference;
    }

    public String getStudentNumber() {
        return studentNumber;
    }

    public CSModel(String name, String status, String completeDate, String task, String reference, String studentNumber) {
        this.name = name;
        this.status = status;
        this.completeDate = completeDate;
        this.task = task;
        this.reference = reference;
        this.studentNumber = studentNumber;
    }
}

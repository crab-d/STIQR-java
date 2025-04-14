package com.example.stiqr_java.recyclerview.model;

public class StudentModel {
    String name, studentNumber, section, gradeLevel;

    public String getName() {
        return name;
    }

    public String getStudentNumber() {
        return studentNumber;
    }

    public String getSection() {
        return section;
    }

    public String getGradeLevel() {
        return gradeLevel;
    }

    public StudentModel(String name, String studentNumber, String section, String gradeLevel) {
        this.name = name;
        this.studentNumber = studentNumber;
        this.section = section;
        this.gradeLevel = gradeLevel;
    }
}

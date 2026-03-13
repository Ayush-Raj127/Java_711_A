package com.course.model;

public class Student {
    int studentID;
    String studentName;

    public Student (int studentId, String studentName) {
        this.studentID = studentId;
        this.studentName = studentName;
    }

    public int getStudentId() {
        return studentID;
    }

    public String getStudentName() {
        return studentName;
    }
}

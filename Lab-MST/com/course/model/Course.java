package com.course.model;

import java.util.Scanner;

public class Course {
    int courseID;
    String courseName;
    int maxSeats;
    int enrolledStudents;

    public Course (int courseID, String courseName, int maxSeats) {
        this.courseID = courseID;
        this.courseName = courseName;
        this.maxSeats = maxSeats;
        this.enrolledStudents = 0;
    }

    public int getCourseID() {
        return courseID;
    }

    public String getCourseName() {
        return courseName;
    }

    public int getMaxSeats() {
        return maxSeats;
    }

    public int getEnrolledStudents() {
        return enrolledStudents;
    }

    public void setEnrolledStudents (int enrolledStudents) {
        this.enrolledStudents = enrolledStudents;
    }

    public void display() {
        System.out.println(courseID + " " + courseName +
                " Seats: " + enrolledStudents + "/" + maxSeats);
    }

}

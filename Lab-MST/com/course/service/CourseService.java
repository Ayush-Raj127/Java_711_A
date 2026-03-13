package com.course.service;

import com.course.model.Course;
import com.course.model.Student;
import com.course.exception.*;

import java.io.*;
import java.util.*;

public class CourseService {

    private List<Course> courses = new ArrayList<>();

    public void addCourse(Course c) {
        courses.add(c);
    }

    public void enrollStudent(int courseId, Student s)
            throws CourseFullException, CourseNotFoundException {

        Course found = null;

        for (Course c : courses) {
            if (c.getCourseID() == courseId) {
                found = c;
                break;
            }
        }

        if (found == null) {
            throw new CourseNotFoundException("Course not found");
        }

        if (found.getEnrolledStudents() >= found.getMaxSeats()) {
            throw new CourseFullException("Course is full");
        }

        found.setEnrolledStudents(found.getEnrolledStudents() + 1);

        try {
            BufferedWriter bw = new BufferedWriter(
                    new FileWriter("courses.txt", true));

            bw.write(s.getStudentId() + " " +
                    s.getStudentName() + " enrolled in " +
                    found.getCourseName());

            bw.newLine();
            bw.close();

        } catch (IOException e) {
            System.out.println("File writing error");
        }
    }

    public void viewCourses() {
        for (Course c : courses) {
            c.display();
        }

        try {
            BufferedReader br =
                    new BufferedReader(new FileReader("courses.txt"));

            String line;

            while ((line = br.readLine()) != null) {
                System.out.println(line);
            }

            br.close();

        } catch (IOException e) {
            System.out.println("File reading error");
        }
    }
}
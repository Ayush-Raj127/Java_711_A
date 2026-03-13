package com.course.main;

import com.course.model.*;
import com.course.service.CourseService;
import com.course.exception.*;

public class Main {

    public static void main(String[] args) {

        CourseService service = new CourseService();

        Course c1 = new Course(1, "Java", 2);
        Course c2 = new Course(2, "Python", 1);

        service.addCourse(c1);
        service.addCourse(c2);

        Student s1 = new Student(101, "Rahul");
        Student s2 = new Student(102, "Aman");

        try {

            service.enrollStudent(1, s1);
            service.enrollStudent(1, s2);

            service.viewCourses();

        } catch (CourseFullException e) {

            System.out.println(e.getMessage());

        } catch (CourseNotFoundException e) {

            System.out.println(e.getMessage());

        }
    }
}
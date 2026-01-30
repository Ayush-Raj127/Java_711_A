class Student {
    int id;
    String name;
    int age;

    Student () {
        id = 0;
        name = "Not Assigned";
        age = 0;
    }

    Student (int i, String n, int a) {
        id = i;
        name = n;
        age = a;
    }

    void display () {
        System.out.println("Student id: " + id);
        System.out.println("Student name: " + name);
        System.out.println("Student age: " + age);
    }
}

public class Exp1 {
    public static void main(String[] args) {
        Student s1 = new Student();
        Student s2 = new Student(1, "Ayush", 19);

        s1.display();
        System.out.println();
        s2.display();
    }
}
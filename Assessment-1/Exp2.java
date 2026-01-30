class calculateArea {
    void area (int l, int b) {
        System.out.println("Area of rectangle: " + (l * b));
    }

    void area (double radius) {
        System.out.println("Area of circle: " + (3.14 * radius * radius));
    }

    void area (double b, double h) {
        System.out.println("Area of triangle: " + (0.5 * b * h));
    }
}

public class Exp2 {
    public static void main(String[] args) {
        calculateArea shape1 = new calculateArea();
        calculateArea shape2 = new calculateArea();
        calculateArea shape3 = new calculateArea();

        shape1.area(5, 4);
        shape2.area(3.0);
        shape3.area(5.0, 7.0);
    }
}

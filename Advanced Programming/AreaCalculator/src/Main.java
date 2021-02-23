import java.util.NoSuchElementException;
import java.util.Scanner;

public class Main {
    private static double length1;
    private static double length2;
    private static Scanner input;
    private static Shape shape;

    public static void main(String args[]) throws InterruptedException {
        print(String.format("Calculate area for:\n%-18s> 1\n%-18s> 2\n%-18s> 3\n%-18s> 4\n", "Rectangle", "Square", "Triangle", "Circle"));
        input = new Scanner(System.in);
        try {
            String num = input.next();
            while (!num.equals("1") && !num.equals("2") && !num.equals("3") && !num.equals("4")) {
                print("Enter only between 1 to 4: ");
                num = input.next();
            }
            createObject(num);
        } catch (NoSuchElementException ignored) {
        }
        printWait();
        Thread.sleep(1200);
        print(String.format("\r%,.2f", shape.area()));
    }

    private static void createObject(String num) {
        switch (num) {
            case "1":
                rectangleArea();
                break;
            case "2":
                squareArea();
                break;
            case "3":
                triangleArea();
                break;
            case "4":
                circleArea();
                break;
        }
    }

    private static void rectangleArea() {
        input = new Scanner(System.in);
        print("Enter the Length: ");
        length1 = input.nextDouble();
        print("Enter the Width: ");
        length2 = input.nextDouble();
        shape = new Rectangle(length1, length2);
    }

    private static void squareArea() {
        input = new Scanner(System.in);
        print("Enter the Length: ");
        length1 = input.nextDouble();
        shape = new Square(length1);
    }

    private static void triangleArea() {
        input = new Scanner(System.in);
        print("Enter the Base: ");
        length1 = input.nextDouble();
        print("Enter the Height: ");
        length2 = input.nextDouble();
        shape = new Triangle(length1, length2);
    }

    private static void circleArea() {
        input = new Scanner(System.in);
        print("Enter the Radius: ");
        length1 = input.nextDouble();
        shape = new Circle(length1);
    }

    public static void printWait() throws InterruptedException {
        for (int i = 0; i < 5; i++) {
            print("Please wait ");
            Thread.sleep(300);
            print(".");
            Thread.sleep(300);
            print(".");
            Thread.sleep(300);
            print(".");
            Thread.sleep(300);
            print("\r");
        }
    }

    public static void print(String string) {
        System.out.print(string);
    }
}

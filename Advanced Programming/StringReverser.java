
import java.util.Scanner;

public class StringReverser {

    private static String statement;
    private static int middle;

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        System.out.printf("Enter your statement: ");
        statement = input.next();
        int length;
        length = statement.length();
        char characters[] = new char[length];
        middle = length / 2;
        if (length % 2 == 0) {
            evenReverser(characters, length);
        }
        if (length % 2 == 1) {
            oddReverser(characters, length);
        }
    }

    public static void evenReverser(char characters[], int length) {

        int counter = 0;
        for (int i = middle - 1; i >= 0; i--) {
            characters[counter] = statement.charAt(i);
            counter++;
        }
        for (int i = length - 1; i >= middle; i--) {
            characters[counter] = statement.charAt(i);
            counter++;
        }
        System.out.println(characters);
    }

    public static void oddReverser(char characters[], int length) {
        int counter = 0;
        for (int i = middle - 1; i >= 0; i--) {
            characters[counter] = statement.charAt(i);
            counter++;
        }
        characters[counter] = statement.charAt(middle);
        counter++;
        for (int i = length - 1; i > middle; i--) {
            characters[counter] = statement.charAt(i);
            counter++;
        }
        System.out.println(characters);
    }
}

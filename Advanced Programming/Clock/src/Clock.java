import java.util.Calendar;

public class Clock {

    public static void main(String args[]) throws InterruptedException {
        for (int count = 8; count > 0; count--) {
            System.out.printf("\r%s", getTime(count, 8));
            Thread.sleep(150);
        }
        for (int count = 0; count < 72; count++) {
            insertSpace(count);
            System.out.print(getTime(0, 8));
            Thread.sleep(150);
        }
        for (int count = 0; count < 8; count++) {
            insertSpace(72 + count);
            System.out.print(getTime(0, 7 - count));
            Thread.sleep(150);
        }
        main(null);
    }

    public static void insertSpace(int i) {
        System.out.print("\r");
        for (int count = 0; count < i; count++) {
            System.out.print(" ");
        }
    }

    public static String getTime(int i, int j) {
        return Calendar.getInstance().getTime().toString().substring(11, 19).substring(i, j);
    }
}
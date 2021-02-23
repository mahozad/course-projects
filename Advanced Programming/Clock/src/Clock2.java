import java.time.*;

public class Clock {

    private static final int DISPLAY_WIDTH = 72;
    private static final int SLEEP_TIME = 150;
    private static final int LENGTH = 8;

    public static void main(String args[]) throws InterruptedException {
        while (true) {
            for (int count = LENGTH; count > 0; count--) {
                System.out.printf("\r%s", getTime(count, LENGTH));
                Thread.sleep(SLEEP_TIME);
            }
            for (int count = 0; count < DISPLAY_WIDTH; count++) {
                insertSpace(count);
                System.out.print(getTime(0, LENGTH));
                Thread.sleep(SLEEP_TIME);
            }
            for (int count = 0; count < LENGTH; count++) {
                insertSpace(DISPLAY_WIDTH + count);
                System.out.print(getTime(0, 7 - count));
                Thread.sleep(SLEEP_TIME);
            }
        }
    }

    private static String getTime(final int HEAD, final int TAIL) {
        return LocalTime.now().toString().substring(0, 8).substring(HEAD, TAIL);
    }

    private static void insertSpace(int i) {
        System.out.print("\r");
        for (int count = 0; count < i; count++) {
            System.out.print(" ");
        }
    }
}
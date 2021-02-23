import javax.swing.*;
import java.text.*;
import java.time.*;
import java.util.*;

public class Application {

    private static final int SLEEP_INTERVAL = 40;
    private static final int SLEEP_TIME = 500;
    private static final int CLOCK_LENGTH = 8;
    private static final String PAD = "   ";
    private static MainFrame frame;
    private static SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss");

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            frame = new MainFrame();
            Beeper beeper = new Beeper(frame);
            Thread beeperThread = new Thread(beeper);
            beeperThread.setPriority(Thread.MAX_PRIORITY);
            beeperThread.start();
            showClock();
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error in the program");
        }
    }

    private static void showClock() throws Exception {
        String day = PAD;
        while (true) {
            enterTime(day);
            for (int i = 0; i < 10; i++) {
                frame.updateTimeLabel(getTime(8));
                Thread.sleep(SLEEP_TIME);
            }
            day = getDay();
            enterDay(day);
            frame.updateTimeLabel(PAD + day);
            Thread.sleep(SLEEP_TIME);
        }
    }

    private static void enterTime(String day) throws Exception {
        for (int count = 0; count < CLOCK_LENGTH; count++) {
            String spaces = "";
            if (count < 4) {
                for (int i = 0; i < 3 - count; i++) {
                    spaces += " ";
                }
                frame.updateTimeLabel(getTime(count) + spaces + day);
            } else if (count > 3 && count < 7) {
                frame.updateTimeLabel(getTime(count) + day.substring(count - 3, 3));
            } else {
                frame.updateTimeLabel(getTime(count));
            }
            Thread.sleep(SLEEP_INTERVAL);
        }
    }

    private static String getTime(final int tail) {
        return formatter.format(Calendar.getInstance().getTime()).substring(0, tail);
    }

    private static void enterDay(String day) throws Exception {
        for (int count = CLOCK_LENGTH; count >= 0; count--) {
            if (count < 6 && count > 2) {
                frame.updateTimeLabel(getTime(count) + day.substring(count - 3, 3));
            } else if (count < 3) {
                String spaces = "";
                for (int i = 3; i > count; i--) {
                    spaces += " ";
                }
                frame.updateTimeLabel(getTime(count) + spaces + day);
            } else {
                frame.updateTimeLabel(getTime(count));
            }
            Thread.sleep(SLEEP_INTERVAL);
        }
    }

    private static String getDay() {
        String day = LocalDate.now().getDayOfWeek().toString();
        return day.charAt(0) + day.substring(1, 3).toLowerCase();
    }
}

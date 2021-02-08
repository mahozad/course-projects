import javax.swing.*;

public class Application {

    private static boolean canReExecute = true;

    public static void main(String args[]) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            Data.mainFrame = new MainFrame();
            while (canReExecute) {
                synchronized (Application.class) {
                    while (!Data.canRun) {
                        Application.class.wait();
                    }
                    Data.canRun = false;
                }
                new BigData().run();
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "A critical exception occurred in the program",
                    "Error", JOptionPane.ERROR_MESSAGE);
            canReExecute = false;
        }
    }
}

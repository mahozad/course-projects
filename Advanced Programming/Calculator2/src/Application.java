import javax.swing.*;

class Application {

    private static boolean canReRun = true;

    public static void main(String args[]) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            MainFrame frame = new MainFrame();
            frame.setVisible(true);
            while (canReRun) {
                synchronized (Application.class) {
                    while (!frame.canStart()) {
                        Application.class.wait();
                    }
                    frame.setStartDisabled();
                }
                new Calculator().run();
            }
            System.exit(1);
        } catch (Exception e) {
            canReRun = false;
            JOptionPane.showMessageDialog(null, "An exception occurred in the" +
                    " program" + ".", "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }
}

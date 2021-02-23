import javax.swing.*;
import java.awt.*;

public class Application {

    public static void main(String args[]) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            new MainFrame().setVisible(true);
        } catch (AWTException | IllegalAccessException |
                UnsupportedLookAndFeelException | InstantiationException |
                ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}

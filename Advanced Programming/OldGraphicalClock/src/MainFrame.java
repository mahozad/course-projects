import java.awt.event.*;
import java.nio.file.*;
import javax.swing.*;
import java.time.*;
import java.awt.*;
import java.io.*;

class MainFrame extends JFrame {

    private static final JLabel TIME_LABEL = new JLabel();
    private static final int HOUR_1 = 1;
    private static final int HOUR_2 = 2;
    private static final int HOUR_4 = 4;
    private JMenuItem[] menuItems = new JMenuItem[7];
    private JCheckBoxMenuItem halfBeepCheckBox;
    private boolean showClockAtStart = true;
    private boolean halfBeepEnabled = true;
    private boolean isMuted = false;
    private LocalDateTime muteTime;
    private TrayIcon trayIcon;
    private JMenu muteMenu;

    MainFrame() throws Exception {
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);
        setAlwaysOnTop(true);
        setType(Type.UTILITY);
        readAppProperties();
        initComponents();
        if (isMuted) {
            startUnmuter();
        }
        makeLayout();
        setFocusableWindowState(false);
        setBackground(new Color(0, 0, 0, 125));
        setVisible(showClockAtStart);
    }

    private void readAppProperties() throws Exception {
        if (Files.exists(Paths.get("res\\start.prop"))) {
            RandomAccessFile file = new RandomAccessFile("res\\start.prop", "rw");
            showClockAtStart = file.readBoolean();
            halfBeepEnabled = file.readBoolean();
        }
        if (Files.exists(Paths.get("res\\time.dat"))) {
            FileInputStream inpStr = new FileInputStream("res\\time.dat");
            ObjectInputStream stream = new ObjectInputStream(inpStr);
            muteTime = (LocalDateTime) stream.readObject();
            isMuted = LocalDateTime.now().compareTo(muteTime) < 0;
        }
    }

    private void initComponents() throws Exception {
        TIME_LABEL.setFont(new Font("Monofonto", Font.PLAIN, 16));
        TIME_LABEL.setForeground(Color.WHITE);
        menuItems[0] = new JMenuItem("for 1 hour", new ImageIcon("res\\1h.png"));
        menuItems[1] = new JMenuItem("for 2 hours", new ImageIcon("res\\2h.png"));
        menuItems[2] = new JMenuItem("for 4 hours", new ImageIcon("res\\4h.png"));
        menuItems[3] = new JMenuItem("Unmute", new ImageIcon("res\\unmute.png"));
        menuItems[3].setVisible(isMuted);
        menuItems[4] = new JMenuItem((showClockAtStart ? "Don't show" : "Show") +
                " clock at start");
        menuItems[4].setIcon(new ImageIcon("res\\clock.png"));
        menuItems[5] = new JMenuItem("OK", new ImageIcon("res\\ok.png"));
        menuItems[6] = new JMenuItem("Exit", new ImageIcon("res\\exit.png"));
        muteMenu = new JMenu("Mute");
        muteMenu.add(menuItems[0]);
        muteMenu.add(menuItems[1]);
        muteMenu.add(menuItems[2]);
        halfBeepCheckBox = new JCheckBoxMenuItem("Half-beep (30s)");
        halfBeepCheckBox.setSelected(halfBeepEnabled);
        halfBeepCheckBox.addActionListener(e -> halfBeepEnabled = !halfBeepEnabled);
        JPopupMenu popupMenu = new JPopupMenu();
        popupMenu.add(muteMenu);
        popupMenu.add(menuItems[3]);
        popupMenu.add(halfBeepCheckBox);
        popupMenu.add(menuItems[4]);
        popupMenu.add(menuItems[5]);
        popupMenu.add(menuItems[6]);
        menuItems[0].addActionListener(e -> mute(HOUR_1));
        menuItems[1].addActionListener(e -> mute(HOUR_2));
        menuItems[2].addActionListener(e -> mute(HOUR_4));
        menuItems[3].addActionListener(e -> unmute());
        menuItems[4].addActionListener(e -> {
            showClockAtStart = !showClockAtStart;
            menuItems[4].setText((showClockAtStart ? "Don't show" : "Show") + " "
                    + "clock at start");
        });
        menuItems[6].addActionListener(e -> {
            try {
                RandomAccessFile file = new RandomAccessFile("res\\start.prop",
                        "rw");
                file.writeBoolean(showClockAtStart);
                file.writeBoolean(halfBeepEnabled);
                FileOutputStream outStr = new FileOutputStream("res\\time.dat");
                ObjectOutputStream stream = new ObjectOutputStream(outStr);
                stream.writeObject(muteTime);
                System.exit(0);
            } catch (IOException error) {
                JOptionPane.showMessageDialog(null, "Error saving the state of " +
                        "the" + " program. Your settings may not be restored in "
                        + "the next execution.");
            }
        });
        SystemTray tray = SystemTray.getSystemTray();
        Image trayImage;
        trayImage = Toolkit.getDefaultToolkit().getImage(isMuted ? "res\\muteIcon"
                + ".png" : "res\\trayIcon.gif");
        trayIcon = new TrayIcon(trayImage, "Double-click to " + (showClockAtStart
                ? "hide" : "show") + " the clock");
        trayIcon.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                if (e.isPopupTrigger()) {
                    popupMenu.setLocation(e.getX(), e.getY());
                    popupMenu.setInvoker(popupMenu);
                    popupMenu.setVisible(true);
                }
            }
        });
        trayIcon.addActionListener(e -> {
            setVisible(!isVisible());
            trayIcon.setToolTip("Double-click to " + (isVisible() ? "hide" :
                    "show") + " the clock");
        });
        trayIcon.setImageAutoSize(true);
        tray.add(trayIcon);
    }

    void updateTimeLabel(String time) {
        TIME_LABEL.setText(" " + time);
    }

    synchronized boolean isMuted() {
        return isMuted;
    }

    synchronized boolean mustHalfBeep() {
        return halfBeepEnabled;
    }

    private void mute(int duration) {
        synchronized (this) {
            isMuted = true;
            muteTime = LocalDateTime.now().plusHours(duration);
        }
        startUnmuter();
    }

    private void startUnmuter() {
        new Thread(() -> {
            while (LocalDateTime.now().compareTo(muteTime) < 0) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException ignored) {
                }
            }
            unmute();
        }).start();
        muteMenu.setVisible(false);
        halfBeepCheckBox.setEnabled(false);
        String muteText = "Muted till " + muteTime.toString().substring(11, 16);
        menuItems[3].setText("Unmute (" + muteText + ")");
        menuItems[3].setVisible(true);
        trayIcon.setImage(Toolkit.getDefaultToolkit().getImage("res\\muteIcon.png"));
    }

    private void unmute() {
        synchronized (this) {
            isMuted = false;
            muteTime = LocalDateTime.now();
        }
        menuItems[3].setVisible(false);
        muteMenu.setVisible(true);
        halfBeepCheckBox.setEnabled(true);
        trayIcon.setImage(Toolkit.getDefaultToolkit().getImage("res\\trayIcon.gif"));
        synchronized (Beeper.class) {
            Beeper.class.notifyAll();
        }
    }

    private void makeLayout() {
        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment
                .LEADING).addComponent(TIME_LABEL, GroupLayout.DEFAULT_SIZE, 85,
                Short.MAX_VALUE));
        layout.setVerticalGroup(layout.createParallelGroup(GroupLayout.Alignment
                .LEADING).addComponent(TIME_LABEL, GroupLayout.PREFERRED_SIZE, 22,
                GroupLayout.PREFERRED_SIZE));
        pack();
    }
}

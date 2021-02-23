import java.awt.event.*;
import javax.swing.*;
import java.util.*;
import java.awt.*;
import java.io.*;

class MainFrame extends JFrame {

    private ImageIcon usaIcon = new ImageIcon("res\\icons\\usa_flag.png");
    private ImageIcon usaIconHighlight = new ImageIcon
            ("res\\icons\\usa_flag_highlight.png");
    private ImageIcon irnIcon = new ImageIcon("res\\icons\\iran_flag.png");
    private ImageIcon irnIconHighlight = new ImageIcon
            ("res\\icons\\iran_flag_highlight.png");
    private JLabel flagLabel = new JLabel(usaIcon);
    private JLabel phraseLabel = new JLabel();
    private JLabel checkLabel = new JLabel();
    private JLabel crossLabel = new JLabel();
    private ImageIcon checkIcon = new ImageIcon("res\\icons\\check_mark.png");
    private ImageIcon checkIconHighlight = new ImageIcon
            ("res\\icons\\check_mark_highlight.png");
    private ImageIcon crossIcon = new ImageIcon("res\\icons\\cross_mark.png");
    private ImageIcon crossIconHighlight = new ImageIcon
            ("res\\icons\\cross_mark_highlight.png");
    private LinkedList<Phrase> list = new LinkedList<>();
    private ObjectInputStream inputStream;
    private ObjectOutputStream outputStream;

    MainFrame() throws AWTException {
        list.push(new Phrase("Hello", "سلام"));
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            try {
                outputStream = new ObjectOutputStream(new FileOutputStream("data"
                        + ".ser"));
                outputStream.writeObject(list);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }));
        initializeFile();
        showMessages();
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setType(Type.UTILITY);
        setUndecorated(true);
        setAlwaysOnTop(true);
        setBackground(new Color(0, 0, 0, 88));
        initComponents();
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        setLocation(dim.width / 2 - getSize().width / 2, 0);
        setVisible(true);
    }

    private void showMessages() {
        for (int i = 0; i < list.size(); i++) {
            phraseLabel.setText(list.get(i).getEnglish());
            i = (i + 1) % list.size();
        }
    }

    private void initComponents() throws AWTException {
        checkLabel.setIcon(checkIcon);
        checkLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                checkLabel.setIcon(checkIconHighlight);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                checkLabel.setIcon(checkIcon);
            }
        });
        crossLabel.setIcon(new ImageIcon("res\\icons\\cross_mark.png"));
        crossLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                crossLabel.setIcon(crossIconHighlight);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                crossLabel.setIcon(crossIcon);
            }
        });
        flagLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                if (flagLabel.getIcon() == usaIcon) {
                    flagLabel.setIcon(usaIconHighlight);
                } else {
                    flagLabel.setIcon(irnIconHighlight);
                }
            }

            @Override
            public void mouseExited(MouseEvent e) {
                if (flagLabel.getIcon() == usaIconHighlight || flagLabel.getIcon()
                        == usaIcon) {
                    flagLabel.setIcon(usaIcon);
                } else {
                    flagLabel.setIcon(irnIcon);
                }
            }

            @Override
            public void mouseClicked(MouseEvent e) {
                if (flagLabel.getIcon() == usaIconHighlight || flagLabel.getIcon()
                        == usaIcon) {
                    flagLabel.setIcon(irnIconHighlight);
                } else {
                    flagLabel.setIcon(usaIconHighlight);
                }
            }
        });
        phraseLabel.setFont(new Font("Tahoma", Font.BOLD, 13));
        phraseLabel.setForeground(new Color(255, 255, 255, 255));
        phraseLabel.setHorizontalAlignment(SwingConstants.CENTER);
        SystemTray tray = SystemTray.getSystemTray();
        Image image = Toolkit.getDefaultToolkit().getImage("res\\icon.png");
        TrayIcon trayIcon = new TrayIcon(image, "Open settings");
        trayIcon.setImageAutoSize(true);
        trayIcon.addActionListener(e -> new SettingsFrame(list).setVisible(true));
        tray.add(trayIcon);
        makeLayout();
    }

    private void initializeFile() {
        try {
            if (new File("data.ser").exists()) {
                inputStream = new ObjectInputStream(new FileInputStream("data.ser"));
                list = (LinkedList<Phrase>) inputStream.readObject();
                phraseLabel.setText(list.getFirst().getEnglish());
            } else {
                outputStream = new ObjectOutputStream(new FileOutputStream("data"
                        + ".ser", true));
                inputStream = new ObjectInputStream(new FileInputStream("data.ser"));
            }
        } catch (Exception ignored) {
            ignored.printStackTrace();
        }
    }

    private void makeLayout() {
        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment
                .LEADING).addGroup(layout.createSequentialGroup().addContainerGap
                (GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE).addComponent
                (flagLabel).addPreferredGap(LayoutStyle.ComponentPlacement
                .UNRELATED).addComponent(phraseLabel, GroupLayout.PREFERRED_SIZE,
                215, GroupLayout.PREFERRED_SIZE).addPreferredGap(LayoutStyle
                .ComponentPlacement.UNRELATED).addComponent(checkLabel).addGap(18,
                18, 18).addComponent(crossLabel).addContainerGap()));
        layout.setVerticalGroup(layout.createParallelGroup(GroupLayout.Alignment
                .LEADING).addGroup(layout.createSequentialGroup().addGap(0, 0,
                Short.MAX_VALUE).addGroup(layout.createParallelGroup(GroupLayout
                .Alignment.TRAILING, false).addComponent(checkLabel, GroupLayout
                .Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 23, Short.MAX_VALUE)
                .addComponent(crossLabel, GroupLayout.DEFAULT_SIZE, GroupLayout
                        .DEFAULT_SIZE, Short.MAX_VALUE).addComponent(flagLabel,
                        GroupLayout.PREFERRED_SIZE, 20, GroupLayout
                                .PREFERRED_SIZE).addComponent(phraseLabel,
                        GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short
                                .MAX_VALUE))));
        pack();
    }
}
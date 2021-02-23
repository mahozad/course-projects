import javax.swing.*;
import java.awt.*;

public class LoginFrame extends JFrame {

    public String string1 = "";
    public String string2 = "";
    public JLabel jLabel4;
    public JPasswordField jPasswordField1;
    public JTextField jTextField1;

    public LoginFrame() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        initComponents();
        setResizable(false);
        setIconImage(new ImageIcon("Logo.png").getImage());
        setAlwaysOnTop(true);
    }

    private void initComponents() {
        setTitle("Judiance");
        JLabel jLabel1 = new JLabel();
        jTextField1 = new JTextField();
        jTextField1.addActionListener(e -> {
            string1 = jTextField1.getText();
            jPasswordField1.requestFocus();
            getString1();
        });
        JLabel jLabel2 = new JLabel();
        jPasswordField1 = new JPasswordField();
        jPasswordField1.addActionListener(e -> {
            string1 = jTextField1.getText();
            string2 = String.valueOf(jPasswordField1.getPassword());
            getString1();
            getString2();
        });
        JButton jButton1 = new JButton();
        jButton1.addActionListener(e -> {
            string1 = jTextField1.getText();
            string2 = String.valueOf(jPasswordField1.getPassword());
            getString1();
            getString2();
        });
        JButton jButton2 = new JButton();
        jButton2.addActionListener(e -> System.exit(-1));
        JLabel jLabel3 = new JLabel();
        jLabel4 = new JLabel();
        setTitle("Judiance");
        jLabel1.setText("Username:");
        jLabel2.setText("Password:");
        jButton1.setText("OK");
        jButton2.setText("Exit");
        jLabel3.setHorizontalAlignment(SwingConstants.LEFT);
        jLabel3.setIcon(new ImageIcon("Logo.png"));
        jLabel4.setForeground(new Color(255, 0, 0));
        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup().addContainerGap().addGroup(layout
                        .createParallelGroup(GroupLayout.Alignment.LEADING, false).addGroup
                                (layout.createSequentialGroup().addComponent(jButton1,
                                        GroupLayout.PREFERRED_SIZE, 80, GroupLayout
                                                .PREFERRED_SIZE).addPreferredGap(LayoutStyle
                                        .ComponentPlacement.RELATED).addComponent(jButton2,
                                        GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short
                                                .MAX_VALUE)).addComponent(jPasswordField1)
                        .addGroup(GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(jLabel1).addGap(114, 114, 114)).addComponent
                                (jTextField1, GroupLayout.Alignment.TRAILING).addComponent
                                (jLabel2)).addGroup(layout.createParallelGroup(GroupLayout
                        .Alignment.LEADING).addGroup(layout.createSequentialGroup().addGap(27,
                        27, 27).addComponent(jLabel3)).addGroup(layout.createSequentialGroup()
                        .addGap(18, 18, 18).addComponent(jLabel4))).addContainerGap(27, Short
                        .MAX_VALUE)));
        layout.setVerticalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup().addContainerGap().addComponent(jLabel1)
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent
                                (jTextField1, GroupLayout.PREFERRED_SIZE, GroupLayout
                                        .DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent
                                (jLabel2).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPasswordField1, GroupLayout.PREFERRED_SIZE, GroupLayout
                                .DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE).addContainerGap(54,
                                Short.MAX_VALUE)).addGroup(GroupLayout.Alignment.TRAILING, layout
                        .createSequentialGroup().addContainerGap(GroupLayout.DEFAULT_SIZE, Short
                                .MAX_VALUE).addComponent(jLabel3).addGap(18, 18, 18).addGroup
                                (layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(jButton2).addComponent(jButton1)
                                        .addComponent(jLabel4)).addContainerGap()));
        pack();
    }

    public String getString2() {
        try {
            while (string2.equals("")) {
                synchronized (this) {
                    wait();
                }
            }
        } catch (InterruptedException ignored) {
        }
        synchronized (this) {
            notify();
        }
        return string2;
    }

    public String getString1() {
        try {
            while (string1.equals("")) {
                synchronized (this) {
                    wait();
                }
            }
        } catch (InterruptedException ignored) {
        }
        synchronized (this) {
            notify();
        }
        return string1;
    }
}
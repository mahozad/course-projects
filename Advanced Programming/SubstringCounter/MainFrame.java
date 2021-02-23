import javax.swing.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class MainFrame extends JFrame {

    public String string1 = "";
    public String string2 = "";
    public JRadioButton jRadioButton1;
    public JRadioButton jRadioButton2;
    public JTextField jTextField1;
    public JTextField jTextField2;
    public JTextField jTextField3;

    public MainFrame() {
        super("Substring counter");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        initComponents();
        setResizable(false);
        setIconImage(new ImageIcon("Diamond.png").getImage());
        setAlwaysOnTop(true);
    }

    private void initComponents() {
        JLabel jLabel1 = new JLabel();
        jTextField1 = new JTextField();
        jTextField1.addActionListener(e -> {
            string1 = jTextField1.getText();
            getString1();
        });
        JLabel jLabel2 = new JLabel();
        jTextField2 = new JTextField();
        jTextField2.addActionListener(e -> {
            string1 = jTextField1.getText();
            string2 = jTextField2.getText();
            getString2();
        });
        jRadioButton1 = new JRadioButton();
        jRadioButton2 = new JRadioButton();
        jTextField3 = new JTextField();
        jTextField3.setEditable(false);
        JLabel jLabel4 = new JLabel();
        jLabel1.setText("Enter your string:");
        jLabel2.setText("Enter a substring you want to be counted:");
        jRadioButton1.setText("Inclusive");
        jRadioButton2.setSelected(true);
        jRadioButton2.setText("Exclusive");
        ButtonGroup buttonGroup = new ButtonGroup();
        buttonGroup.add(jRadioButton1);
        buttonGroup.add(jRadioButton2);
        jLabel4.setIcon(new ImageIcon("Diamond.png"));
        jLabel4.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                string1 = jTextField1.getText();
                string2 = jTextField2.getText();
                getString2();
            }

            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {
                jLabel4.setIcon(new ImageIcon("Diamond2.png"));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                jLabel4.setIcon(new ImageIcon("Diamond.png"));
            }
        });

        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                        .addGroup(layout.createSequentialGroup()
                                                .addComponent(jLabel1)
                                                .addGap(0, 0, Short.MAX_VALUE))
                                        .addGroup(GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                                                        .addComponent(jLabel2, GroupLayout.Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                        .addComponent(jTextField1)
                                                        .addComponent(jTextField2, GroupLayout.Alignment.LEADING)
                                                        .addGroup(GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                                                .addComponent(jRadioButton2)
                                                                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                                                                .addComponent(jRadioButton1)
                                                                .addGap(0, 0, Short.MAX_VALUE))
                                                        .addComponent(jTextField3))
                                                .addGap(18, 18, 18)
                                                .addComponent(jLabel4)
                                                .addGap(79, 79, 79))))
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jLabel1)
                                .addGap(4, 4, 4)
                                .addComponent(jTextField1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                        .addGroup(layout.createSequentialGroup()
                                                .addComponent(jLabel2)
                                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(jTextField2, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                                        .addComponent(jRadioButton1)
                                                        .addComponent(jRadioButton2))
                                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(jTextField3, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                                        .addComponent(jLabel4))
                                .addContainerGap(22, Short.MAX_VALUE))
        );
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
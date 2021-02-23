import javax.swing.*;
import javax.swing.event.MouseInputAdapter;
import java.awt.event.MouseEvent;

public class MainFrame extends javax.swing.JFrame {

    private String string = "";
    public javax.swing.JTextField jTextField1;
    public javax.swing.JTextField jTextField2;

    public MainFrame() {
        super("Case changer");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        initComponents();
        setResizable(false);
        setIconImage(new ImageIcon("Exchange.png").getImage());
        setAlwaysOnTop(true);
    }

    private void initComponents() {
        JLabel jLabel1 = new JLabel();
        jTextField1 = new javax.swing.JTextField();
        JLabel jLabel2 = new JLabel();
        labelHandler labelHandler = new labelHandler();
        jLabel2.addMouseListener(labelHandler);
        jTextField2 = new javax.swing.JTextField();
        jTextField2.setEditable(false);
        jLabel1.setText("Enter your string:");
        jTextField1.addActionListener(evt -> {
            string = jTextField1.getText();
            getString();
        });
        jLabel2.setIcon(new javax.swing.ImageIcon("Exchange2.png"));
        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(layout.createSequentialGroup()
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addGroup(layout.createSequentialGroup()
                                                                .addContainerGap()
                                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                                        .addComponent(jLabel1)
                                                                        .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                                        .addGroup(layout.createSequentialGroup()
                                                                .addGap(87, 87, 87)
                                                                .addComponent(jLabel2)))
                                                .addGap(0, 0, Short.MAX_VALUE))
                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                                .addContainerGap()
                                                .addComponent(jTextField2)))
                                .addContainerGap())
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel2)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap(34, Short.MAX_VALUE))
        );
        pack();
    }

    public String getString() {
        try {
            while (string.equals("")) {
                synchronized (this) {
                    wait();
                }
            }
        } catch (InterruptedException ignored) {
        }
        synchronized (this) {
            notify();
        }
        return string;
    }

    public void setString() {
        string = "";
    }

    private class labelHandler extends MouseInputAdapter {

        @Override
        public void mouseClicked(MouseEvent e) {
            string = jTextField1.getText();
            getString();
        }

        @Override
        public void mouseEntered(MouseEvent e) {
        }

        @Override
        public void mouseExited(MouseEvent e) {
        }
    }
}
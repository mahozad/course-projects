import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.LinkedList;

class SettingsFrame extends JFrame {

    private JButton exitButton = new JButton();
    private JButton okButton = new JButton();
    private JLabel jLabel1 = new JLabel();
    private JLabel jLabel2 = new JLabel();
    private JLabel jLabel3 = new JLabel();
    private JTextField engTextField = new JTextField();
    private JTextField perTextField = new JTextField();
    private LinkedList<Phrase> list;

    SettingsFrame(LinkedList<Phrase> linkedList) {
        list = linkedList;
        setDefaultCloseOperation(HIDE_ON_CLOSE);
        setIconImage(new ImageIcon("res\\icon.png").getImage());
        setResizable(false);
        initComponents();
        setLocationRelativeTo(null);
    }

    private void initComponents() {
        jLabel1.setFont(new Font("Tahoma", 0, 12));
        jLabel1.setText("افزودن عبارت جدید");
        jLabel2.setFont(new Font("Tahoma", 0, 12));
        jLabel2.setText("عبارت انگلیسی:");
        jLabel3.setFont(new Font("Tahoma", 0, 12));
        jLabel3.setText("معنی عبارت:");
        perTextField.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
        perTextField.setHorizontalAlignment(JTextField.RIGHT);
        exitButton.setText("خروج");
        exitButton.addActionListener(e -> setVisible(false));
        okButton.setText("افزودن");
        okButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!engTextField.getText().equals("")) {
                    if (!perTextField.getText().equals("")) {
                        list.push(new Phrase(engTextField.getText(), perTextField
                                .getText()));
//                        setVisible(false);
                        dispose();
                    } else {
                        showWarningMessage("لطفا هردو فیلد را پر کنید     ");
                    }
                } else {
                    showWarningMessage("لطفا هردو فیلد را پر کنید     ");
                }
            }

            private void showWarningMessage(String message) {
                JOptionPane.showMessageDialog(SettingsFrame.this, message,
                        "اخطار", JOptionPane.WARNING_MESSAGE);
            }
        });
        makeLayout();
    }

    private void makeLayout() {
        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment
                .LEADING).addGroup(layout.createSequentialGroup().addGap(126, 126,
                126).addComponent(exitButton, GroupLayout.PREFERRED_SIZE, 70,
                GroupLayout.PREFERRED_SIZE).addGap(18, 18, 18).addComponent
                (okButton, GroupLayout.PREFERRED_SIZE, 70, GroupLayout
                        .PREFERRED_SIZE).addGap(126, 126, 126)).addGroup
                (GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addContainerGap().addGroup(layout.createParallelGroup
                                (GroupLayout.Alignment.LEADING).addComponent
                                (jLabel1, GroupLayout.Alignment.TRAILING).addGroup
                                (GroupLayout.Alignment.TRAILING, layout
                                        .createSequentialGroup().addGroup(layout
                                                .createParallelGroup(GroupLayout
                                                        .Alignment.TRAILING,
                                                        false).addComponent
                                                        (engTextField, GroupLayout
                                                                .PREFERRED_SIZE,
                                                                300, GroupLayout
                                                                        .PREFERRED_SIZE).
                addComponent(perTextField, GroupLayout.PREFERRED_SIZE, 300,
                        GroupLayout.PREFERRED_SIZE)).addPreferredGap(LayoutStyle
                                                .ComponentPlacement.UNRELATED)
                                        .addGroup(layout.createParallelGroup
                                                (GroupLayout.Alignment.LEADING)
                                                .addComponent(jLabel2, GroupLayout
                                                        .Alignment.TRAILING)
                                                .addComponent(jLabel3, GroupLayout
                                                        .Alignment.TRAILING))))
                        .addContainerGap()));
        layout.setVerticalGroup(layout.createParallelGroup(GroupLayout.Alignment
                .LEADING).addGroup(layout.createSequentialGroup().addContainerGap
                ().addComponent(jLabel1).addGap(10, 10, 10).addGroup(layout
                .createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent
                        (jLabel2).addComponent(engTextField, GroupLayout
                        .PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout
                        .PREFERRED_SIZE)).addPreferredGap(LayoutStyle
                .ComponentPlacement.UNRELATED).addGroup(layout.createParallelGroup
                (GroupLayout.Alignment.LEADING).addComponent(jLabel3).addComponent
                (perTextField, GroupLayout.PREFERRED_SIZE, GroupLayout
                        .DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment
                        .BASELINE).addComponent(exitButton).addComponent(okButton)
                ).addContainerGap()));
        pack();
    }
}

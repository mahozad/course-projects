import javax.swing.*;
import java.awt.*;

class ASTFrame extends JFrame {

    private JScrollPane scrollPane = new JScrollPane();
    private JTextArea textField = new JTextArea();

    void resetParseTree() {
        ASTNode.resetRootNode();
        setVisible(false);
        textField.setText("");
    }

    ASTFrame() {
        super("Parse Tree");
        setIconImage(new ImageIcon("res\\icon.png").getImage());
        textField.setEditable(false);
        textField.setSelectionColor(new Color(108, 15, 145));
        scrollPane.setViewportView(textField);
        makeLayout();
        setLocationRelativeTo(null);
    }

    void setText(String text) {
        textField.setText(textField.getText() + text);
    }

    private void makeLayout() {
        javax.swing.GroupLayout layout = new javax.swing.GroupLayout
                (getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(layout.createParallelGroup(javax.swing
                .GroupLayout.Alignment.LEADING).addComponent(scrollPane, javax
                .swing.GroupLayout.DEFAULT_SIZE, 600, Short.MAX_VALUE));
        layout.setVerticalGroup(layout.createParallelGroup(javax.swing.GroupLayout
                .Alignment.LEADING).addComponent(scrollPane, javax.swing
                .GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout
                .DEFAULT_SIZE, 300, Short.MAX_VALUE));
        pack();
    }
}

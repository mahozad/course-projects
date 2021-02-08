import javax.swing.filechooser.*;
import java.awt.event.*;
import javax.swing.*;
import java.awt.*;
import java.io.*;

class MainFrame extends JFrame {

    private JScrollPane lexemesScrollPane = new JScrollPane();
    private JScrollPane errorsScrollPane = new JScrollPane();
    private JTextArea lexemesTextfield = new JTextArea();
    private JTextArea errorsTextfield = new JTextArea();
    private JTextField filePathField = new JTextField();
    private JButton browseButton = new JButton();
    private JLabel lexemesLabel = new JLabel();
    private JLabel errorsLabel = new JLabel();
    private JButton okButton = new JButton();
    private JLabel fileLabel = new JLabel();
    private String fileAddress;

    void setLexemesText(String text) {
        lexemesTextfield.setText(lexemesTextfield.getText() + text);
    }

    void setErrorsText(String text) {
        errorsTextfield.setText(errorsTextfield.getText() + text);
    }

    void resetTextFields() {
        lexemesTextfield.setText("");
        errorsTextfield.setText("");
    }

    MainFrame() {
        super("Lexical Analyser");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setIconImage(new ImageIcon("res\\icon.png").getImage());
        setResizable(false);
        initComponents();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    String getFileAddress() {
        return fileAddress;
    }

    private void initComponents() {
        fileLabel.setText("Code file address:");
        filePathField.addFocusListener(selectAllText(filePathField));
        filePathField.addActionListener(e -> actionPerformed());
        browseButton.setText("Browse...");
        browseButton.addActionListener(e -> showFileChooser());
        okButton.setText("Run");
        okButton.addActionListener(e -> actionPerformed());
        lexemesLabel.setText("Lexemes:");
        lexemesTextfield.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        lexemesTextfield.setColumns(20);
        lexemesTextfield.setRows(5);
        lexemesTextfield.setEditable(false);
        lexemesScrollPane.setViewportView(lexemesTextfield);
        errorsLabel.setText("Errors:");
        errorsTextfield.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        errorsTextfield.setForeground(new Color(255, 0, 0));
        errorsTextfield.setColumns(20);
        errorsTextfield.setRows(5);
        errorsTextfield.setEditable(false);
        errorsScrollPane.setViewportView(errorsTextfield);
        makeLayout();
    }

    private void actionPerformed() {
        if (fileIsValid()) {
            fileAddress = filePathField.getText();
            synchronized (Application.class) {
                Application.class.notifyAll();
            }
        } else {
            lexemesTextfield.setText("");
            errorsTextfield.setText("The file does not exist");
        }
    }

    private boolean fileIsValid() {
        try {
            String fileAddress = filePathField.getText();
            File file = new File(fileAddress);
            if (!file.exists()) {
                return false;
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Exception while checking " +
                    "the" + " file", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        return true;
    }

    private FocusAdapter selectAllText(JTextField field) {
        return new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                field.selectAll();
            }
        };
    }

    private void showFileChooser() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setCurrentDirectory(new File(Application.class.getResource
                ("Application.class").getPath()));
        fileChooser.setFileFilter(new FileNameExtensionFilter("text files", "txt"));
        if (fileChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
            filePathField.setText(fileChooser.getSelectedFile().getAbsolutePath());
        }
    }

    private void makeLayout() {
        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment
                .LEADING).addGroup(layout.createSequentialGroup().addContainerGap
                ().addGroup(layout.createParallelGroup(GroupLayout.Alignment
                .LEADING).addGroup(layout.createSequentialGroup().addGroup(layout
                .createParallelGroup(GroupLayout.Alignment.LEADING).addComponent
                        (lexemesLabel).addComponent(lexemesScrollPane, GroupLayout
                        .PREFERRED_SIZE, 403, GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment
                        .LEADING).addGroup(layout.createSequentialGroup()
                        .addComponent(errorsLabel).addGap(0, 181, Short.MAX_VALUE)
                ).addComponent(errorsScrollPane))).addGroup(layout
                .createSequentialGroup().addComponent(fileLabel).addPreferredGap
                        (LayoutStyle.ComponentPlacement.RELATED).addComponent
                        (filePathField).addPreferredGap(LayoutStyle
                        .ComponentPlacement.UNRELATED).addComponent(browseButton)
                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(okButton, GroupLayout.PREFERRED_SIZE, 80,
                        GroupLayout.PREFERRED_SIZE))).addContainerGap()));
        layout.setVerticalGroup(layout.createParallelGroup(GroupLayout.Alignment
                .LEADING).addGroup(layout.createSequentialGroup().addContainerGap
                ().addGroup(layout.createParallelGroup(GroupLayout.Alignment
                .BASELINE).addComponent(fileLabel).addComponent(filePathField,
                GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout
                        .PREFERRED_SIZE).addComponent(okButton, GroupLayout
                .PREFERRED_SIZE, 23, GroupLayout.PREFERRED_SIZE).addComponent
                (browseButton)).addPreferredGap(LayoutStyle.ComponentPlacement
                .RELATED).addGroup(layout.createParallelGroup(GroupLayout
                .Alignment.BASELINE).addComponent(lexemesLabel).addComponent
                (errorsLabel)).addPreferredGap(LayoutStyle.ComponentPlacement
                .RELATED).addGroup(layout.createParallelGroup(GroupLayout
                .Alignment.LEADING).addComponent(lexemesScrollPane, GroupLayout
                .DEFAULT_SIZE, 176, Short.MAX_VALUE).addComponent
                (errorsScrollPane)).addContainerGap()));
        pack();
    }
}

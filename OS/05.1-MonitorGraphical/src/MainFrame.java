import javax.swing.filechooser.*;
import java.awt.event.*;
import javax.swing.*;
import java.awt.*;
import java.io.*;

class MainFrame extends JFrame {

    private JProgressBar progressBar = new JProgressBar();
    private JTextPane resultsTextfield = new JTextPane();
    private JTextField filePathField = new JTextField();
    private JScrollPane scrollPane = new JScrollPane();
    private JTextField numberField = new JTextField();
    private JLabel numberPromptLabel = new JLabel();
    private JLabel filePromptLabel = new JLabel();
    private JButton browseButton = new JButton();
    private JButton exitButton = new JButton();
    private JLabel resultsLabel = new JLabel();
    private JButton okButton = new JButton();
    private JLabel downIcon = new JLabel();
    private JLabel upIcon = new JLabel();
    private int numberOfThreads = 1;

    MainFrame() {
        super("Big Data");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setIconImage(new ImageIcon("res\\icon.png").getImage());
        setResizable(false);
        initComponents();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    void modifyProgressBar(double value, boolean isUpdate) {
        synchronized (Data.PROGRESSBAR_LOCK) {
            Data.progressBarValue = isUpdate ? Data.progressBarValue + value : value;
            SwingUtilities.invokeLater(() -> progressBar.setValue((int) Data
                    .progressBarValue));
        }
    }

    void modifyResultsTextfield(String string, boolean isUpdate) {
        string += " ";
        synchronized (Data.RESULTS_FIELD_LOCK) {
            resultsTextfield.setText(isUpdate ? resultsTextfield.getText() +
                    string : string);
        }
    }

    private void initComponents() {
        filePromptLabel.setText("The input file address:");
        numberPromptLabel.setText("Number of threads to create:");
        numberField.setText("1");
        numberField.addActionListener(e -> {
            if (Data.isCompleted) {
                actionPerformed();
            }
        });
        numberField.addFocusListener(selectAllText(numberField));
        filePathField.addFocusListener(selectAllText(filePathField));
        browseButton.setText("Browse...");
        browseButton.addActionListener(e -> showFileChooser());
        okButton.setText("OK");
        okButton.addActionListener(e -> {
            if (Data.isCompleted) {
                actionPerformed();
            }
        });
        exitButton.setText("Exit");
        exitButton.addActionListener(e -> System.exit(0));
        progressBar.setFocusable(false);
        upIcon.setIcon(new ImageIcon("res\\Untitled up.png"));
        upIcon.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                numberOfThreads = Integer.parseInt(numberField.getText()) + 1;
                numberField.setText(Integer.toString(numberOfThreads));
            }
        });
        downIcon.setIcon(new ImageIcon("res\\Untitled down.png"));
        downIcon.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (Integer.parseInt(numberField.getText()) > 1) {
                    numberOfThreads = Integer.parseInt(numberField.getText()) - 1;
                    numberField.setText(Integer.toString(numberOfThreads));
                }
            }
        });
        resultsLabel.setText("Results:");
        resultsTextfield.setEditable(false);
        resultsTextfield.setFocusable(false);
        resultsTextfield.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        scrollPane.setViewportView(resultsTextfield);
        makeLayout();
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
        fileChooser.setCurrentDirectory(new File("res"));
        fileChooser.setFileFilter(new FileNameExtensionFilter("Data files", "dat"));
        if (fileChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
            filePathField.setText(fileChooser.getSelectedFile().getAbsolutePath());
            numberField.requestFocus();
        }
    }

    private void actionPerformed() {
        try {
            if (validateFile()) {
                Data.numberOfThreads = Integer.parseInt(numberField.getText());
                if (Data.numberOfThreads < 1) {
                    modifyResultsTextfield("Number of threads cannot be less " +
                            "than 1", false);
                    return;
                } else {
                    modifyResultsTextfield("", false);
                }
                progressBar.setStringPainted(true);
            } else {
                modifyResultsTextfield("The file does not exist", false);
                return;
            }
        } catch (NumberFormatException e) {
            modifyResultsTextfield("NaN", false);
            return;
        }
        modifyProgressBar(0, false);
        synchronized (Application.class) {
            Data.canRun = true;
            Data.isCompleted = false;
            Application.class.notifyAll();
        }
    }

    private boolean validateFile() {
        try {
            Data.fileAddress = filePathField.getText();
            File file = new File(Data.fileAddress);
            if (!file.exists()) {
                return false;
            } else {
                RandomAccessFile mainFile = new RandomAccessFile(file, "r");
                Data.totalNumbers = mainFile.readInt();
                Data.maximum = mainFile.readInt();
                mainFile.close();
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Exception while checking " +
                    "the" + " file", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        return true;
    }

    private void makeLayout() {
        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment
                .LEADING).addGroup(layout.createSequentialGroup().addGap(25, 25,
                25).addGroup(layout.createParallelGroup(GroupLayout.Alignment
                .LEADING).addGroup(layout.createSequentialGroup().addComponent
                (resultsLabel).addGap(0, 0, Short.MAX_VALUE)).addGroup(layout
                .createSequentialGroup().addGroup(layout.createParallelGroup
                        (GroupLayout.Alignment.TRAILING).addComponent(progressBar,
                        GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short
                                .MAX_VALUE).addGroup(layout.createSequentialGroup
                        ().addGroup(layout.createParallelGroup(GroupLayout
                        .Alignment.LEADING).addComponent(numberPromptLabel)
                        .addComponent(filePromptLabel)).addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment
                                .LEADING).addGroup(layout.createSequentialGroup()
                                .addComponent(numberField, GroupLayout
                                        .PREFERRED_SIZE, 58, GroupLayout
                                        .PREFERRED_SIZE).addGap(18, 18, 18)
                                .addComponent(upIcon).addGap(15, 15, 15)
                                .addComponent(downIcon).addPreferredGap
                                        (LayoutStyle.ComponentPlacement.RELATED,
                                                113, Short.MAX_VALUE).addComponent
                                        (okButton, GroupLayout.PREFERRED_SIZE, 80,
                                                GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(LayoutStyle.ComponentPlacement
                                        .RELATED).addComponent(exitButton,
                                        GroupLayout.PREFERRED_SIZE, 80,
                                        GroupLayout.PREFERRED_SIZE)).addGroup
                                (layout.createSequentialGroup().addComponent
                                        (filePathField).addPreferredGap
                                        (LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(browseButton, GroupLayout
                                                .PREFERRED_SIZE, 80, GroupLayout
                                                .PREFERRED_SIZE)))).addComponent
                        (scrollPane, GroupLayout.Alignment.LEADING)).addGap(25,
                        25, 25)))));
        layout.setVerticalGroup(layout.createParallelGroup(GroupLayout.Alignment
                .LEADING).addGroup(layout.createSequentialGroup().addGap(25, 25,
                25).addGroup(layout.createParallelGroup(GroupLayout.Alignment
                .BASELINE).addComponent(filePromptLabel).addComponent
                (browseButton).addComponent(filePathField, GroupLayout
                .PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout
                .PREFERRED_SIZE)).addPreferredGap(LayoutStyle.ComponentPlacement
                .UNRELATED).addGroup(layout.createParallelGroup(GroupLayout
                .Alignment.BASELINE).addComponent(numberPromptLabel).addComponent
                (numberField, GroupLayout.PREFERRED_SIZE, GroupLayout
                        .DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE).addComponent
                (exitButton, GroupLayout.PREFERRED_SIZE, 23, GroupLayout
                        .PREFERRED_SIZE).addComponent(okButton, GroupLayout
                .PREFERRED_SIZE, 23, GroupLayout.PREFERRED_SIZE).addComponent
                (upIcon).addComponent(downIcon)).addGap(12, 12, 12).addComponent
                (progressBar, GroupLayout.PREFERRED_SIZE, 17, GroupLayout
                        .PREFERRED_SIZE).addGap(8, 8, 8).addComponent
                (resultsLabel).addPreferredGap(LayoutStyle.ComponentPlacement
                .RELATED).addComponent(scrollPane, GroupLayout.PREFERRED_SIZE,
                140, GroupLayout.PREFERRED_SIZE).addContainerGap(24, Short
                .MAX_VALUE)));
        pack();
    }
}

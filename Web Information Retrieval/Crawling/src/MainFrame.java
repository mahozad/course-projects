import javax.swing.border.*;
import java.awt.event.*;
import java.util.List;
import javax.swing.*;
import java.util.*;
import java.net.*;
import java.awt.*;
import java.io.*;

class MainFrame extends JFrame {

    private static JProgressBar progressBar = new JProgressBar();
    private static int numberOfPages;
    private int numberOfThreads;
    private int maxDepth;
    private boolean attributesAreReady = false;
    private Color defaultProgressBarColor = progressBar.getForeground();
    private JLabel numberOfThreadsLabel = new JLabel();
    private JLabel maxDepthLabel = new JLabel();
    private JLabel numberOfPagesLabel = new JLabel();
    private JLabel statusLabel = new JLabel();
    private JLabel[] seedLabels = new JLabel[5];
    private List<Link> seeds = new LinkedList<>();
    private JTextField numberOfThreadsTextField = new JTextField();
    private JTextField maxDepthTextField = new JTextField();
    private JTextField numberOfPagesTextField = new JTextField();
    private JTextField[] seedTextFields = new JTextField[5];
    private JButton[] plusButtons = new JButton[4];
    private JButton crawlButton = new JButton();
    private JButton resultsButton = new JButton();
    private Border defaultTextFieldBorder = numberOfThreadsTextField.getBorder();

    MainFrame() {
        initComponents();
        makeHandlers();
        makeLayout();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    static void setProgressBarStatus(double numberOfPagesVisited) {
        int value = (int) (numberOfPagesVisited / numberOfPages * 100);
        progressBar.setValue(value);
        if (value < 100) {
            progressBar.setString(value + "%");
        } else {
            progressBar.setString("Completed!");
            progressBar.setForeground(new Color(0x1CA009));
        }
    }

    void reset() {
        seeds = new LinkedList<>();
        attributesAreReady = false;
        crawlButton.setEnabled(true);
    }

    void setTextFiledBorder(int fieldNumber) {
        seedTextFields[fieldNumber].setBorder(BorderFactory.createLineBorder(Color.red));
    }

    boolean areAttributesReady() {
        return attributesAreReady;
    }

    int getNumberOfThreads() {
        return numberOfThreads;
    }

    int getNumberOfPages() {
        return numberOfPages;
    }

    int getMaxDepth() {
        return maxDepth;
    }

    List<Link> getSeeds() {
        return seeds;
    }

    private void initComponents() {
        setTitle("Crawler");
        setIconImage(new ImageIcon("res\\icon.png").getImage());
        setResizable(false);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        numberOfThreadsLabel.setText("Number of threads:");
        maxDepthLabel.setText("Max depth:");
        numberOfPagesLabel.setText("Number of pages:");
        for (int i = 0; i < 5; i++) {
            seedLabels[i] = new JLabel();
            seedTextFields[i] = new JTextField();
            seedLabels[i].setText("Seed " + (i + 1) + " (URL):");
        }
        for (int i = 1; i < 5; i++) {
            seedLabels[i].setVisible(false);
            seedTextFields[i].setVisible(false);
        }
        for (int i = 0; i < 4; i++) {
            plusButtons[i] = new JButton();
            plusButtons[i].setText("+");
            if (i > 0) {
                plusButtons[i].setVisible(false);
            }
        }
        statusLabel.setText("Status:");
        crawlButton.setText("Crawl");
        resultsButton.setText("Show result");
        progressBar.setString("Not started");
        progressBar.setStringPainted(true);
    }

    private void makeHandlers() {
        crawlButton.addActionListener(evt -> {
            boolean allSeedsAreCorrect = true;
            numberOfThreads = takeNumber(numberOfThreadsTextField);
            maxDepth = takeNumber(maxDepthTextField);
            numberOfPages = takeNumber(numberOfPagesTextField);
            if (numberOfThreads > 0 && maxDepth > 0 && numberOfPages > 0) {
                int seedCount = 0;
                for (JTextField seedTextField : seedTextFields) {
                    String url = seedTextField.getText();
                    if (!url.equals("")) {
                        url = (url.startsWith("http://") ? "" : "http://") + url;
                        try {
                            InetAddress address = InetAddress.getByName(new URL(url).getHost());
                            address.getHostAddress();
                            seedTextField.setBorder(defaultTextFieldBorder);
                            seeds.add(new Link(url, 0));
                            seedCount++;
                        } catch (UnknownHostException | MalformedURLException e) {
                            setTextFiledBorder(seedTextField, false);
                            allSeedsAreCorrect = false;
                        }
                    }
                }
                if (seedCount < 1) {
                    setTextFiledBorder(seedTextFields[0], false);
                } else if (allSeedsAreCorrect) {
                    crawlButton.setEnabled(false);
                    progressBar.setValue(0);
                    progressBar.setString("0%");
                    progressBar.setForeground(defaultProgressBarColor);
                    synchronized (MainFrame.class) {
                        attributesAreReady = true;
                        MainFrame.class.notifyAll();
                    }
                }
            } else {
                for (JTextField seedTextField : seedTextFields) {
                    seedTextField.setBorder(defaultTextFieldBorder);
                }
            }
        });
        resultsButton.addActionListener(evt -> {
            try {
                Desktop.getDesktop().open(new File(System.getProperty("user.dir")));
            } catch (IOException e) {
                JOptionPane.showMessageDialog(null, "Cannot Open the folder!", "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        });
        plusButtons[0].addActionListener(evt -> {
            plusButtons[0].setVisible(false);
            seedLabels[1].setVisible(true);
            seedTextFields[1].setVisible(true);
            plusButtons[1].setVisible(true);
            seedTextFields[1].requestFocus();
            setMinimumSize(new Dimension(528, 180));
        });
        plusButtons[1].addActionListener(evt -> {
            plusButtons[1].setVisible(false);
            seedLabels[2].setVisible(true);
            seedTextFields[2].setVisible(true);
            plusButtons[2].setVisible(true);
            seedTextFields[2].requestFocus();
            setMinimumSize(new Dimension(528, 205));
        });
        plusButtons[2].addActionListener(evt -> {
            plusButtons[2].setVisible(false);
            seedLabels[3].setVisible(true);
            seedTextFields[3].setVisible(true);
            plusButtons[3].setVisible(true);
            seedTextFields[3].requestFocus();
            setMinimumSize(new Dimension(528, 230));
        });
        plusButtons[3].addActionListener(evt -> {
            plusButtons[3].setVisible(false);
            seedLabels[4].setVisible(true);
            seedTextFields[4].setVisible(true);
            seedTextFields[4].requestFocus();
            setMinimumSize(new Dimension(528, 255));
        });
        numberOfThreadsTextField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                numberOfThreadsTextField.selectAll();
            }
        });
        numberOfPagesTextField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                numberOfPagesTextField.selectAll();
            }
        });
        maxDepthTextField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                maxDepthTextField.selectAll();
            }
        });
        for (JTextField seedTextField : seedTextFields) {
            seedTextField.addFocusListener(new FocusAdapter() {
                @Override
                public void focusGained(FocusEvent e) {
                    seedTextField.selectAll();
                }
            });
        }
    }

    private void setTextFiledBorder(JTextField field, boolean isDefaultColor) {
        if (isDefaultColor) {
            field.setBorder(defaultTextFieldBorder);
        } else {
            field.setBorder(BorderFactory.createLineBorder(Color.red));
        }
    }

    private int takeNumber(JTextField srcField) {
        int number = 0;
        try {
            number = Integer.parseInt(srcField.getText());
            if (number < 1) {
                setTextFiledBorder(srcField, false);
            } else {
                setTextFiledBorder(srcField, true);
            }
        } catch (NumberFormatException e) {
            setTextFiledBorder(srcField, false);
        }
        return number;
    }

    private void makeLayout() {
        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup().addContainerGap().addGroup(layout
                        .createParallelGroup(GroupLayout.Alignment.LEADING).addComponent
                                (numberOfThreadsLabel).addComponent(seedLabels[0]).addComponent
                                (seedLabels[1]).addComponent(seedLabels[2]).addComponent
                                (seedLabels[3]).addComponent(seedLabels[4]).addComponent
                                (statusLabel)).addPreferredGap(LayoutStyle.ComponentPlacement
                        .RELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment
                        .LEADING).addGroup(layout.createSequentialGroup().addComponent
                        (progressBar, GroupLayout.PREFERRED_SIZE, 203, GroupLayout
                                .PREFERRED_SIZE).addGap(18, 18, 18).addComponent(crawlButton,
                        GroupLayout.PREFERRED_SIZE, 93, GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent
                                (resultsButton)).addGroup(layout.createSequentialGroup()
                        .addComponent(numberOfThreadsTextField, GroupLayout.PREFERRED_SIZE, 80,
                                GroupLayout.PREFERRED_SIZE).addPreferredGap(LayoutStyle
                                .ComponentPlacement.UNRELATED).addComponent(maxDepthLabel)
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent
                                (maxDepthTextField, GroupLayout.PREFERRED_SIZE, 80, GroupLayout
                                        .PREFERRED_SIZE).addPreferredGap(LayoutStyle
                                .ComponentPlacement.UNRELATED).addComponent(numberOfPagesLabel)
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent
                                (numberOfPagesTextField, GroupLayout.PREFERRED_SIZE, 80,
                                        GroupLayout.PREFERRED_SIZE)).addGroup(layout
                        .createSequentialGroup().addGroup(layout.createParallelGroup(GroupLayout
                                .Alignment.TRAILING).addComponent(seedTextFields[0], GroupLayout
                                .PREFERRED_SIZE, 364, GroupLayout.PREFERRED_SIZE).addComponent
                                (seedTextFields[1], GroupLayout.PREFERRED_SIZE, 364, GroupLayout
                                        .PREFERRED_SIZE).addComponent(seedTextFields[2],
                                GroupLayout.PREFERRED_SIZE, 364, GroupLayout.PREFERRED_SIZE)
                                .addComponent(seedTextFields[3], GroupLayout.PREFERRED_SIZE, 364,
                                        GroupLayout.PREFERRED_SIZE).addComponent
                                        (seedTextFields[4], GroupLayout.PREFERRED_SIZE, 364,
                                                GroupLayout.PREFERRED_SIZE)).addPreferredGap
                                (LayoutStyle.ComponentPlacement.RELATED).addGroup(layout
                                .createParallelGroup(GroupLayout.Alignment.LEADING).addComponent
                                        (plusButtons[0]).addComponent(plusButtons[1])
                                .addComponent(plusButtons[2]).addComponent(plusButtons[3]))))
                        .addContainerGap()));
        layout.setVerticalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup().addContainerGap().addGroup(layout
                        .createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent
                                (numberOfThreadsLabel).addComponent(numberOfThreadsTextField,
                                GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout
                                        .PREFERRED_SIZE).addComponent(maxDepthTextField,
                                GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout
                                        .PREFERRED_SIZE).addComponent(maxDepthLabel).addComponent
                                (numberOfPagesLabel).addComponent(numberOfPagesTextField,
                                GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout
                                        .PREFERRED_SIZE)).addGap(18, 18, 18).addGroup(layout
                        .createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent
                                (seedLabels[0]).addComponent(seedTextFields[0], GroupLayout
                                .PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout
                                .PREFERRED_SIZE).addComponent(plusButtons[0])).addPreferredGap
                        (LayoutStyle.ComponentPlacement.RELATED).addGroup(layout
                        .createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent
                                (seedLabels[1]).addComponent(plusButtons[1]).addComponent
                                (seedTextFields[1], GroupLayout.PREFERRED_SIZE, GroupLayout
                                        .DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(layout
                                .createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent
                                        (plusButtons[2]).addComponent(seedLabels[2]).addComponent
                                        (seedTextFields[2], GroupLayout.PREFERRED_SIZE,
                                                GroupLayout.DEFAULT_SIZE, GroupLayout
                                                        .PREFERRED_SIZE)).addPreferredGap
                                (LayoutStyle.ComponentPlacement.RELATED).addGroup(layout
                                .createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent
                                        (plusButtons[3]).addComponent(seedLabels[3]).addComponent
                                        (seedTextFields[3], GroupLayout.PREFERRED_SIZE,
                                                GroupLayout.DEFAULT_SIZE, GroupLayout
                                                        .PREFERRED_SIZE)).addPreferredGap
                                (LayoutStyle.ComponentPlacement.RELATED).addGroup(layout
                                .createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent
                                        (seedLabels[4]).addComponent(seedTextFields[4],
                                        GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
                                        GroupLayout.PREFERRED_SIZE)).addGap(18, 18, 18).addGroup
                                (layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(statusLabel).addComponent(resultsButton)
                                        .addComponent(crawlButton).addComponent(progressBar,
                                                GroupLayout.DEFAULT_SIZE, 24, Short.MAX_VALUE))
                        .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));
        pack();
    }
}

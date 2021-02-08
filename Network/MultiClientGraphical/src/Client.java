import java.awt.event.*;
import javax.swing.*;
import java.util.*;
import java.net.*;
import java.awt.*;
import java.io.*;

public class Client extends JFrame {

    private JLabel clientsLabel;
    private JTextArea clientsTextArea;
    private JScrollPane jScrollPane1;
    private JScrollPane jScrollPane2;
    private JTextArea myTextArea;
    private JLabel youLabel;
    private String message;
    private BufferedReader reader;
    private BufferedWriter writer;

    private Client() throws IOException {
        super("Client");
        Socket socket = new Socket("www.google.com", 8284);
        writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    try {
                        String message = reader.readLine();
                        message += clientsTextArea.getText().endsWith("\n") ? "" : "\n";
                        clientsTextArea.setText(clientsTextArea.getText() + message);
                    } catch (IOException e) {
                        e.printStackTrace();

                    }
                }
            }
        }).start();
        initComponents();
        setResizable(false);
        makeLayout();
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            new Client();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initComponents() {
        jScrollPane1 = new JScrollPane();
        myTextArea = new JTextArea();
        youLabel = new JLabel();
        jScrollPane2 = new JScrollPane();
        clientsTextArea = new JTextArea();
        clientsLabel = new JLabel();
        myTextArea.setColumns(20);
        myTextArea.setRows(5);
        jScrollPane1.setViewportView(myTextArea);
        youLabel.setText("You");
        clientsTextArea.setColumns(20);
        clientsTextArea.setRows(5);
        jScrollPane2.setViewportView(clientsTextArea);
        clientsLabel.setText("Clients");
        clientsTextArea.setEditable(false);
        Random random = new Random();
        clientsTextArea.setForeground(new Color(random.nextInt(256), random.nextInt(256), random
                .nextInt(256)));
        myTextArea.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    try {
                        e.consume();
                        message = myTextArea.getText() + "\n";
                        myTextArea.setText("");
                        writer.write(message);
                        writer.flush();
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                }
            }
        });
    }

    private void makeLayout() {
        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup().addContainerGap().addGroup(layout
                        .createParallelGroup(GroupLayout.Alignment.LEADING).addComponent
                                (jScrollPane1, GroupLayout.PREFERRED_SIZE, GroupLayout
                                        .DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE).addComponent
                                (youLabel)).addPreferredGap(LayoutStyle.ComponentPlacement
                        .UNRELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment
                        .LEADING).addComponent(clientsLabel).addComponent(jScrollPane2,
                        GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout
                                .PREFERRED_SIZE)).addContainerGap(GroupLayout.DEFAULT_SIZE, Short
                        .MAX_VALUE)));
        layout.setVerticalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup().addContainerGap().addGroup(layout
                        .createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent
                                (youLabel).addComponent(clientsLabel)).addPreferredGap
                        (LayoutStyle.ComponentPlacement.RELATED).addGroup(layout
                        .createParallelGroup(GroupLayout.Alignment.LEADING, false).addComponent
                                (jScrollPane2, GroupLayout.DEFAULT_SIZE, 145, Short.MAX_VALUE)
                        .addComponent(jScrollPane1)).addContainerGap(GroupLayout.DEFAULT_SIZE,
                        Short.MAX_VALUE)));
        pack();
    }
}

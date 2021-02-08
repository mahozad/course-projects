import java.nio.charset.*;
import java.nio.file.*;
import javax.swing.*;
import java.util.*;
import java.net.*;
import java.io.*;

class Application {

    public static void main(String[] args) throws Exception {
        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        MainFrame frame = new MainFrame();
        new Application().run(frame);
    }

    private void run(MainFrame frame) {
        while (true) {
            try {
                synchronized (Application.class) {
                    Application.class.wait();
                }
                String fileData = readFile(frame.getFileAddress());
                Lexer lexer = new Lexer();
                List<Token> listOfTokens = lexer.tokenize(fileData);
                if (listOfTokens.size() < 1) {
                    frame.getErrorsTextfield().setText("No tokens to parse");
                } else {
                    frame.showLexemesAndErrors(listOfTokens, lexer.getERRORS());
                    new Parser().parse(listOfTokens, frame);
                    if (frame.getErrorsTextfield().getText().equals("")) {
                        frame.showNoError();
                    }
                }
            } catch (InterruptedException e) {
                System.out.println("Error reading file. Terminating...");
                System.exit(1);
            }
        }
    }

    private String readFile(final String filePath) {
        try {
            Path path = Paths.get(Application.class.getResource(filePath).toURI());
            return new String(Files.readAllBytes(path), Charset.defaultCharset());
        } catch (IOException | URISyntaxException e) {
            System.err.println("Error reading file: \"" + filePath + "\"");
            e.printStackTrace();
        }
        return null;
    }
}



















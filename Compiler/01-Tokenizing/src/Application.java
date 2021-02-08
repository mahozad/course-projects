import java.nio.charset.*;
import java.nio.file.*;
import javax.swing.*;
import java.util.*;
import java.net.*;
import java.io.*;

public class Application {

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
                frame.resetTextFields();
                String fileData = readFile(frame.getFileAddress());
                Lexer lexer = new Lexer();
                List<Token> listOfTokens = lexer.tokenize(fileData);
                for (Token nextToken : listOfTokens) {
                    frame.setLexemesText(nextToken.toString() + "\n");
                }
                for (String nextToken : lexer.getERRORS()) {
                    frame.setErrorsText(nextToken + "\n");
                }
            } catch (IllegalAccessException | InterruptedException e) {
                System.out.println("Error reading file. Terminating...");
                System.exit(1);
            }
        }
    }

    private String readFile(final String filePath) throws IllegalAccessException {
        try {
            Path path = Paths.get(Application.class.getResource(filePath).toURI());
            return new String(Files.readAllBytes(path), Charset.defaultCharset());
        } catch (IOException | URISyntaxException e) {
            System.err.println("Error reading file: \"" + filePath + "\"");
            e.printStackTrace();
        }
        throw new IllegalArgumentException();
    }
}

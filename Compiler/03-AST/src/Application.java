import java.nio.charset.*;
import java.nio.file.*;
import javax.swing.*;
import java.util.*;
import java.net.*;
import java.io.*;

class Application {

    static {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static ASTFrame ast = new ASTFrame();

    public static void main(String[] args) throws Exception {
        MainFrame frame = new MainFrame(ast);
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
                frame.showLexemesAndErrors(listOfTokens, lexer.getErrors());
                if (listOfTokens.size() < 1) {
                    frame.getErrorsTextfield().setText("No token to parse");
                } else if (lexer.getErrors().size() == 0) {
                    boolean success = new Parser().parse(listOfTokens, frame);
                    if (success) {
                        frame.showNoError();
                        ast.setVisible(true);
                        ASTNode.getRootNode().print(ast);
                    }
                }
            } catch (InterruptedException e) {
                System.out.println("Error in run method. Terminating...");
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

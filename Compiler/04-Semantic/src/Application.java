import javax.swing.*;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

class Application {

    static {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ignored) {
        }
    }

    private ASTFrame ast = new ASTFrame();
    private MainFrame frame = new MainFrame(ast);

    public static void main(String[] args) throws Exception {
        new Application().run();
    }

    private void run() {
        while (true) {
            try {
                synchronized (Application.class) {
                    while (!frame.canReRun) {
                        Application.class.wait();
                    }
                    frame.canReRun = false;
                }
                String source = readSource(frame.getFileAddress());
                Lexer lexer = new Lexer();
                List<Token> listOfTokens = lexer.tokenize(source);
                frame.showLexemesAndErrors(listOfTokens, lexer.getErrors());
                if (listOfTokens.size() < 1) {
                    frame.getErrorsTextfield().setText("No token to parse");
                } else if (lexer.getErrors().size() == 0) {
                    boolean parsed = new Parser().parse(listOfTokens, frame);
                    if (parsed) {
                        ast.setVisible(true);
                        ASTNode.getRootNode().print(ast);
                        new ScopeCheck().run(frame);
                    }
                }
            } catch (InterruptedException e) {
                System.err.println("Error in run method. Terminating...");
                System.exit(1);
            }
        }
    }

    private String readSource(final String filePath) {
        try {
            Path path = Paths.get(filePath);
            return new String(Files.readAllBytes(path), Charset.defaultCharset());
        } catch (IOException e) {
            System.err.println("Error reading file: \"" + filePath + "\"");
        }
        return null;
    }
}

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

/**
 * Created by mojtab23 on 9/25/16.
 */
public class Main {
    public static void main(String[] args) {
        if (args.length > 0) {
            run(args[0]);
        } else {
            showUsage();
            run("/test.txt");
        }
    }

    private static void run(String arg) {
        String fileData = readFile(arg);
        if (fileData != null) {
            Lexer lexer = new Lexer();
            List<Token> tokenList = lexer.tokenize(fileData);
            showTokens(tokenList);

            Parser parser = new Parser();
            boolean success = parser.parse(tokenList);
        }
    }

    private static String readFile(final String filePath) {
        try {
            Path path = Paths.get(Main.class.getResource(filePath).toURI());
            return new String(Files.readAllBytes(path), Charset.defaultCharset());
        } catch (IOException e) {
            System.err.println("Error reading file: \"" + filePath + "\"");
            e.printStackTrace();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static void showTokens(List<Token> tokenList) {
        tokenList.forEach(System.out::println);
    }

    private static void showUsage() {
        System.out.println("call this with file path as argument.");
        System.out.println("using the default test.txt");
    }
}

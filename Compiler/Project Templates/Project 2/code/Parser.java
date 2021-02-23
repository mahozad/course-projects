import java.util.List;


/**
 * Created by mojtab23 on 10/3/16.
 */
public class Parser {
    private int errorLine;
    private List<Token> tokenList;
    private int next;

    public boolean parse(List<Token> tokenList) {
        this.tokenList = tokenList;
        next = 0;
        boolean success = program();
        if (success)
            return true;
        System.out.println("Parsing error at line: " + errorLine);
        return false;
    }

    private boolean program() {
        return term("int", TokenType.KEYWORD) && term("main", TokenType.KEYWORD) && term("(", TokenType.SEPARATOR)
                && term(")", TokenType.SEPARATOR) && term("{", TokenType.SEPARATOR) && E() && term("}", TokenType.SEPARATOR);
    }

    private boolean E() {
        // continue from here.
        // return true or false;
    }

    /**
     * for constant tokens like (if)
     *
     * @param token
     * @param tokenType
     * @return
     */
    private boolean term(String token, TokenType tokenType) {
        Token nextToken = tokenList.get(next);
        if (nextToken.getData().equals(token) && nextToken.getTokenClass().equals(tokenType.name())) {
            next++;
            return true;
        } else {
            errorLine = next;
            next++;
            return false;
        }
    }

    /**
     * for variables like literals and identifiers
     *
     * @param tokenType
     * @return
     */
    private boolean term(TokenType tokenType) {
        Token nextToken = tokenList.get(next);
        if (nextToken.getTokenClass().equals(tokenType.name())) {
            next++;
            return true;
        } else {
            errorLine = next;
            next++;
            return false;
        }
    }
}
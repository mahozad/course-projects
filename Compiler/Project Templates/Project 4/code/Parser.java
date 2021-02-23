import java.util.List;


/**
 * Created by mojtab23 on 10/3/16.
 */
public class Parser {
    private final ASTNode rootNode = ASTNode.getRootNode();
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
        ASTNode thisNode = rootNode.addChild("program");
        boolean b = term("int", TokenType.KEYWORD, thisNode) && term("main", TokenType.KEYWORD, thisNode)
                && term("(", TokenType.SEPARATOR, thisNode) && term(")", TokenType.SEPARATOR, thisNode)
                && term("{", TokenType.SEPARATOR, thisNode) && E(thisNode) && term("}", TokenType.SEPARATOR, thisNode);
        boolean c = tokenList.size() == next;
        return b && c;
    }

    private boolean E(ASTNode currentNode) {
        // continue from here.
        // return true or false;
        return true;
    }

    /**
     * for constant tokens like (if)
     *
     * @param token
     * @param tokenType
     * @return
     */
    private boolean term(String token, TokenType tokenType, ASTNode currentNode) {

        Token nextToken = tokenList.get(next);
        currentNode.addChild(nextToken.getTokenClass(), nextToken);
        if (nextToken.getData().equalsIgnoreCase(token) && nextToken.getTokenClass().equalsIgnoreCase(tokenType.name())) {
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
    private boolean term(TokenType tokenType, ASTNode currentNode) {
        Token nextToken = tokenList.get(next);
        currentNode.addChild(nextToken.getTokenClass(), nextToken);
        if (nextToken.getTokenClass().equalsIgnoreCase(tokenType.name())) {
            next++;
            return true;
        } else {
            errorLine = next;
            next++;
            return false;
        }
    }
}
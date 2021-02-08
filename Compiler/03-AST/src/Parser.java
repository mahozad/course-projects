import java.util.*;

class Parser {

    private ASTNode rootNode = ASTNode.getRootNode();
    private List<Token> tokenList;
    private int errorLine;
    private int next;

    boolean parse(List<Token> listOfTokens, MainFrame frame) {
        tokenList = listOfTokens;
        errorLine = tokenList.get(0).getTokenLine();
        next = 0;
        boolean success = program();
        if (success) {
            return true;
        } else {
            frame.showParsingError("Parsing error at line " + errorLine);
            return false;
        }
    }

    private boolean program() {
        ASTNode thisNode = rootNode.addChild("program");
        boolean parsed = term(TokenType.KEYWORD, "int", thisNode) && term
                (TokenType.IDENTIFIER, "main", thisNode) && term(TokenType
                .SEPARATOR, "(", thisNode) && term(TokenType.SEPARATOR, ")",
                thisNode) && term(TokenType.SEPARATOR, "{", thisNode) && E
                (thisNode) && term(TokenType.SEPARATOR, "}", thisNode);
        boolean endOfFile = next == tokenList.size();
        return parsed && endOfFile;
    }

    private boolean E(ASTNode currentNode) {
        int caret = next;
        ASTNode thisNode = currentNode.addChild("E");
        if (DECLARATION(thisNode) && E(thisNode)) {
            return true;
        }
        thisNode.clear();
        next = caret;
        if (ASSIGNMENT(thisNode) && E(thisNode)) {
            return true;
        }
        thisNode.clear();
        next = caret;
        if (INCDEC(thisNode) && E(thisNode)) {
            return true;
        }
        thisNode.clear();
        next = caret;
        if (IFBLOCK(thisNode) && E(thisNode)) {
            return true;
        }
        thisNode.clear();
        next = caret;
        return true;    //  E  ➜  ε
    }

    private boolean DECLARATION(ASTNode currentNode) {
        int caret = next;
        ASTNode thisNode = currentNode.addChild("DECLARATION");
        if (term(TokenType.KEYWORD, "int", thisNode) && INIT(thisNode) && term
                (TokenType.SEPARATOR, ";", thisNode)) {
            return true;
        }
        thisNode.clear();
        next = caret;
        if (term(TokenType.KEYWORD, "boolean", thisNode) && INIT(thisNode) && term
                (TokenType.SEPARATOR, ";", thisNode)) {
            return true;
        }
        thisNode.clear();
        next = caret;
        return false;
    }

    private boolean INIT(ASTNode currentNode) {
        int caret = next;
        ASTNode thisNode = currentNode.addChild("INIT");
        if (term(TokenType.IDENTIFIER, thisNode) && term(TokenType.OPERATOR, "=",
                thisNode) && OP(thisNode) && term(TokenType.SEPARATOR, ",",
                thisNode) && INIT(thisNode)) {
            return true;
        }
        thisNode.clear();
        next = caret;
        if (term(TokenType.IDENTIFIER, thisNode) && term(TokenType.SEPARATOR, ",",
                thisNode) && INIT(thisNode)) {
            return true;
        }
        thisNode.clear();
        next = caret;
        if (term(TokenType.IDENTIFIER, thisNode) && term(TokenType.OPERATOR, "=",
                thisNode) && OP(thisNode)) {
            return true;
        }
        thisNode.clear();
        next = caret;
        if (term(TokenType.IDENTIFIER, thisNode)) {
            return true;
        }
        thisNode.clear();
        next = caret;
        return false;
    }

    private boolean ASSIGNMENT(ASTNode currentNode) {
        int caret = next;
        ASTNode thisNode = currentNode.addChild("ASSIGNMENT");
        if (term(TokenType.IDENTIFIER, thisNode) && term(TokenType.OPERATOR, "=",
                thisNode) && OP(thisNode) && term(TokenType.SEPARATOR, ";",
                thisNode)) {
            return true;
        }
        thisNode.clear();
        next = caret;
        return false;
    }

    private boolean OP(ASTNode currentNode) {
        int caret = next;
        ASTNode thisNode = currentNode.addChild("OP");
        if (OP1(thisNode) && OPPRIME(thisNode)) {
            return true;
        }
        thisNode.clear();
        next = caret;
        if (term(TokenType.RESERVED_WORD, thisNode)) {
            return true;
        }
        thisNode.clear();
        next = caret;
        return false;
    }

    private boolean OPPRIME(ASTNode currentNode) {
        int caret = next;
        ASTNode thisNode = currentNode.addChild("OPPRIME");
        if (term(TokenType.OPERATOR, "-", thisNode) && OP1(thisNode) && OPPRIME
                (thisNode)) {
            return true;
        }
        thisNode.clear();
        next = caret;
        return true;    //  ➜  ε
    }

    private boolean OP1(ASTNode currentNode) {
        int caret = next;
        ASTNode thisNode = currentNode.addChild("OP1");
        if (OP2(thisNode) && OP1PRIME(thisNode)) {
            return true;
        }
        thisNode.clear();
        next = caret;
        return false;
    }

    private boolean OP1PRIME(ASTNode currentNode) {
        int caret = next;
        ASTNode thisNode = currentNode.addChild("OP1PRIME");
        if (term(TokenType.OPERATOR, "+", thisNode) && OP2(thisNode) && OP1PRIME
                (thisNode)) {
            return true;
        }
        thisNode.clear();
        next = caret;
        return true;    //  ➜  ε
    }

    private boolean OP2(ASTNode currentNode) {
        int caret = next;
        ASTNode thisNode = currentNode.addChild("OP2");
        if (OP3(thisNode) && OP2PRIME(thisNode)) {
            return true;
        }
        thisNode.clear();
        next = caret;
        return false;
    }

    private boolean OP2PRIME(ASTNode currentNode) {
        int caret = next;
        ASTNode thisNode = currentNode.addChild("OP2PRIME");
        if (term(TokenType.OPERATOR, "%", thisNode) && OP3(thisNode) && OP2PRIME
                (thisNode)) {
            return true;
        }
        thisNode.clear();
        next = caret;
        return true;    //  ➜  ε
    }

    private boolean OP3(ASTNode currentNode) {
        int caret = next;
        ASTNode thisNode = currentNode.addChild("OP3");
        if (OP4(thisNode) && OP3PRIME(thisNode)) {
            return true;
        }
        thisNode.clear();
        next = caret;
        return false;
    }

    private boolean OP3PRIME(ASTNode currentNode) {
        int caret = next;
        ASTNode thisNode = currentNode.addChild("OP3PRIME");
        if (term(TokenType.OPERATOR, "/", thisNode) && OP4(thisNode) && OP3PRIME
                (thisNode)) {
            return true;
        }
        thisNode.clear();
        next = caret;
        return true;    //  ➜  ε
    }

    private boolean OP4(ASTNode currentNode) {
        int caret = next;
        ASTNode thisNode = currentNode.addChild("OP4");
        if (OP5(thisNode) && OP4PRIME(thisNode)) {
            return true;
        }
        thisNode.clear();
        next = caret;
        return false;
    }

    private boolean OP4PRIME(ASTNode currentNode) {
        int caret = next;
        ASTNode thisNode = currentNode.addChild("OP4PRIME");
        if (term(TokenType.OPERATOR, "*", thisNode) && OP5(thisNode) && OP4PRIME
                (thisNode)) {
            return true;
        }
        thisNode.clear();
        next = caret;
        return true;    //  ➜  ε
    }

    private boolean OP5(ASTNode currentNode) {
        int caret = next;
        ASTNode thisNode = currentNode.addChild("OP5");
        if (term(TokenType.SEPARATOR, "(", thisNode) && OP(thisNode) && term
                (TokenType.SEPARATOR, ")", thisNode)) {
            return true;
        }
        thisNode.clear();
        next = caret;
        if (term(TokenType.OPERATOR, "-", thisNode) && OP(thisNode)) {
            return true;
        }
        thisNode.clear();
        next = caret;
        if (term(TokenType.NUMBER, thisNode)) {
            return true;
        }
        thisNode.clear();
        next = caret;
        if (term(TokenType.IDENTIFIER, thisNode)) {
            return true;
        }
        thisNode.clear();
        next = caret;
        return false;
    }

    private boolean INCDEC(ASTNode currentNode) {
        int caret = next;
        ASTNode thisNode = currentNode.addChild("INCDEC");
        if (term(TokenType.OPERATOR, "++", thisNode) && term(TokenType.IDENTIFIER,
                thisNode) && term(TokenType.SEPARATOR, ";", thisNode)) {
            return true;
        }
        thisNode.clear();
        next = caret;
        if (term(TokenType.OPERATOR, "--", thisNode) && term(TokenType.IDENTIFIER,
                thisNode) && term(TokenType.SEPARATOR, ";", thisNode)) {
            return true;
        }
        thisNode.clear();
        next = caret;
        if (term(TokenType.IDENTIFIER, thisNode) && term(TokenType.OPERATOR, "++",
                thisNode) && term(TokenType.SEPARATOR, ";", thisNode)) {
            return true;
        }
        thisNode.clear();
        next = caret;
        if (term(TokenType.IDENTIFIER, thisNode) && term(TokenType.OPERATOR, "--",
                thisNode) && term(TokenType.SEPARATOR, ";", thisNode)) {
            return true;
        }
        thisNode.clear();
        next = caret;
        return false;
    }

    private boolean IFBLOCK(ASTNode currentNode) {
        int caret = next;
        ASTNode thisNode = currentNode.addChild("IFBLOCK");
        if (term(TokenType.KEYWORD, "if", thisNode) && term(TokenType.SEPARATOR,
                "" + "(", thisNode) && CONDITION(thisNode) && term(TokenType
                .SEPARATOR, ")", thisNode) && term(TokenType.SEPARATOR, "{",
                thisNode) && E(thisNode) && term(TokenType.SEPARATOR, "}",
                thisNode) && term(TokenType.KEYWORD, "else", thisNode) && term
                (TokenType.SEPARATOR, "{", thisNode) && E(thisNode) && term
                (TokenType.SEPARATOR, "}", thisNode)) {
            return true;
        }
        thisNode.clear();
        next = caret;
        if (term(TokenType.KEYWORD, "if", thisNode) && term(TokenType.SEPARATOR,
                "" + "(", thisNode) && CONDITION(thisNode) && term(TokenType
                .SEPARATOR, ")", thisNode) && term(TokenType.SEPARATOR, "{",
                thisNode) && E(thisNode) && term(TokenType.SEPARATOR, "}",
                thisNode)) {
            return true;
        }
        thisNode.clear();
        next = caret;
        return false;
    }

    private boolean CONDITION(ASTNode currentNode) {
        int caret = next;
        ASTNode thisNode = currentNode.addChild("CONDITION");
        if (CON(thisNode) && JOINER(thisNode) && CONDITION(thisNode)) {
            return true;
        }
        thisNode.clear();
        next = caret;
        if (CON(thisNode)) {
            return true;
        }
        thisNode.clear();
        next = caret;
        return false;
    }

    private boolean CON(ASTNode currentNode) {
        int caret = next;
        ASTNode thisNode = currentNode.addChild("CON");
        if (term(TokenType.SEPARATOR, "(", thisNode) && PS(thisNode) && term
                (TokenType.SEPARATOR, ")", thisNode)) {
            return true;
        }
        thisNode.clear();
        next = caret;
        if (PS(thisNode)) {
            return true;
        }
        thisNode.clear();
        next = caret;
        if (term(TokenType.OPERATOR, "!", thisNode) && CON(thisNode)) {
            return true;
        }
        thisNode.clear();
        next = caret;
        return false;
    }

    private boolean PS(ASTNode currentNode) {
        int caret = next;
        ASTNode thisNode = currentNode.addChild("PS");
        if (OP(thisNode) && COMPARATOR(thisNode) && OP(thisNode)) {
            return true;
        }
        thisNode.clear();
        next = caret;
        if (term(TokenType.IDENTIFIER, thisNode)) {
            return true;
        }
        thisNode.clear();
        next = caret;
        return false;
    }

    private boolean JOINER(ASTNode currentNode) {
        int caret = next;
        ASTNode thisNode = currentNode.addChild("JOINER");
        if (term(TokenType.OPERATOR, "&&", thisNode)) {
            return true;
        }
        thisNode.clear();
        next = caret;
        if (term(TokenType.OPERATOR, "||", thisNode)) {
            return true;
        }
        thisNode.clear();
        next = caret;
        if (term(TokenType.OPERATOR, "&", thisNode)) {
            return true;
        }
        thisNode.clear();
        next = caret;
        if (term(TokenType.OPERATOR, "|", thisNode)) {
            return true;
        }
        thisNode.clear();
        next = caret;
        return false;
    }

    private boolean COMPARATOR(ASTNode currentNode) {
        int caret = next;
        ASTNode thisNode = currentNode.addChild("COMPARATOR");
        if (term(TokenType.OPERATOR, "==", thisNode)) {
            return true;
        }
        thisNode.clear();
        next = caret;
        if (term(TokenType.OPERATOR, "!=", thisNode)) {
            return true;
        }
        thisNode.clear();
        next = caret;
        if (term(TokenType.OPERATOR, "<=", thisNode)) {
            return true;
        }
        thisNode.clear();
        next = caret;
        if (term(TokenType.OPERATOR, ">=", thisNode)) {
            return true;
        }
        thisNode.clear();
        next = caret;
        if (term(TokenType.OPERATOR, "<", thisNode)) {
            return true;
        }
        thisNode.clear();
        next = caret;
        if (term(TokenType.OPERATOR, ">", thisNode)) {
            return true;
        }
        thisNode.clear();
        next = caret;
        return false;
    }

    private boolean term(TokenType tokenType, String token, ASTNode currentNode) {
        if (next >= tokenList.size()) {
            return false;
        }
        Token nextToken = tokenList.get(next);
        currentNode.addChild(nextToken, nextToken.getTokenClass());
        if (nextToken.getTokenLexeme().equals(token) && nextToken.getTokenClass()
                .equals(tokenType.name())) {
            next++;
            return true;
        } else {
            if (nextToken.getTokenLine() > errorLine) {
                errorLine = nextToken.getTokenLine();
            }
            return false;
        }
    }

    private boolean term(TokenType tokenType, ASTNode currentNode) {
        if (next >= tokenList.size()) {
            return false;
        }
        Token nextToken = tokenList.get(next);
        currentNode.addChild(nextToken, nextToken.getTokenClass());
        if (nextToken.getTokenClass().equals(tokenType.name())) {
            next++;
            return true;
        } else {
            if (nextToken.getTokenLine() > errorLine) {
                errorLine = nextToken.getTokenLine();
            }
            return false;
        }
    }
}

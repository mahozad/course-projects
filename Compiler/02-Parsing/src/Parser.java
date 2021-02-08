import java.util.*;

class Parser {

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
        boolean parsed = term(TokenType.KEYWORD, "int") && term(TokenType
                .IDENTIFIER, "main") && term(TokenType.SEPARATOR, "(") && term
                (TokenType.SEPARATOR, ")") && term(TokenType.SEPARATOR, "{") && E
                () && term(TokenType.SEPARATOR, "}");
        boolean endOfFile = next == tokenList.size();
        return parsed && endOfFile;
    }

    private boolean E() {
        int caret = next;
        if (DECLARATION() && E()) {
            return true;
        }
        next = caret;
        if (ASSIGNMENT() && E()) {
            return true;
        }
        next = caret;
        if (INCDEC() && E()) {
            return true;
        }
        next = caret;
        if (IFBLOCK() && E()) {
            return true;
        }
        next = caret;
        return true;    //  E  ➜  ε
    }

    private boolean DECLARATION() {
        int caret = next;
        if (term(TokenType.KEYWORD, "int") && term(TokenType.IDENTIFIER) && term
                (TokenType.OPERATOR, "=") && OPERATION() && term(TokenType
                .SEPARATOR, ";")) {
            return true;
        }
        next = caret;
        if (term(TokenType.KEYWORD, "boolean") && term(TokenType.IDENTIFIER) &&
                term(TokenType.OPERATOR, "=") && OPERATION() && term(TokenType
                .SEPARATOR, ";")) {
            return true;
        }
        next = caret;
        return false;
    }

    private boolean ASSIGNMENT() {
        int caret = next;
        if (term(TokenType.IDENTIFIER) && term(TokenType.OPERATOR, "=") &&
                OPERATION() && term(TokenType.SEPARATOR, ";")) {
            return true;
        }
        next = caret;
        return false;
    }

    private boolean OPERATION() {
        int caret = next;
        if (OP2() && term(TokenType.OPERATOR, "+") && OPERATION()) {
            return true;
        }
        next = caret;
        if (OP2()) {
            return true;
        }
        next = caret;
        return false;
    }

    private boolean OP2() {
        int caret = next;
        if (OP3() && term(TokenType.OPERATOR, "-") && OP2()) {
            return true;
        }
        next = caret;
        if (OP3()) {
            return true;
        }
        next = caret;
        return false;
    }

    private boolean OP3() {
        int caret = next;
        if (OP4() && term(TokenType.OPERATOR, "*") && OP3()) {
            return true;
        }
        next = caret;
        if (OP4()) {
            return true;
        }
        next = caret;
        return false;
    }

    private boolean OP4() {
        int caret = next;
        if (OP5() && term(TokenType.OPERATOR, "/") && OP4()) {
            return true;
        }
        next = caret;
        if (OP5()) {
            return true;
        }
        next = caret;
        return false;
    }

    private boolean OP5() {
        int caret = next;
        if (term(TokenType.IDENTIFIER)) {
            return true;
        }
        next = caret;
        if (term(TokenType.NUMBER)) {
            return true;
        }
        next = caret;
        if (term(TokenType.SEPARATOR, "(") && OPERATION() && term(TokenType
                .SEPARATOR, ")" + "" + "" + "" + "" + "" + "" + "" + "" + "" + ""
                + "" + "")) {
            return true;
        }
        next = caret;
        if (term(TokenType.RESERVED_WORD)) {
            return true;
        }
        next = caret;
        return false;
    }

    private boolean INCDEC() {
        return (term(TokenType.OPERATOR, "++") && term(TokenType.IDENTIFIER) &&
                term(TokenType.SEPARATOR, ";")) || (term(TokenType.OPERATOR, "--")
                && term(TokenType.IDENTIFIER) && term(TokenType.SEPARATOR, ";"))
                || (term(TokenType.IDENTIFIER) && term(TokenType.OPERATOR, "++")
                && term(TokenType.SEPARATOR, ";")) || (term(TokenType.IDENTIFIER)
                && term(TokenType.OPERATOR, "--") && term(TokenType.SEPARATOR, ";"));
    }

    private boolean IFBLOCK() {
        int caret = next;
        if (term(TokenType.KEYWORD, "if") && term(TokenType.SEPARATOR, "(") &&
                CONDITION() && term(TokenType.SEPARATOR, ")") && term(TokenType
                .SEPARATOR, "{") && E() && term(TokenType.SEPARATOR, "}") && term
                (TokenType.KEYWORD, "else") && term(TokenType.SEPARATOR, "{") && E
                () && term(TokenType.SEPARATOR, "}")) {
            return true;
        }
        next = caret;
        if (term(TokenType.KEYWORD, "if") && term(TokenType.SEPARATOR, "(") &&
                CONDITION() && term(TokenType.SEPARATOR, ")") && term(TokenType
                .SEPARATOR, "{") && E() && term(TokenType.SEPARATOR, "}")) {
            return true;
        }
        next = caret;
        return false;
    }

    private boolean CONDITION() {
        int caret = next;
        if (term(TokenType.OPERATOR, "!") && POSCON() && JOINER() && CONDITION()) {
            return true;
        }
        next = caret;
        if (POSCON() && JOINER() && CONDITION()) {
            return true;
        }
        next = caret;
        if (term(TokenType.OPERATOR, "!") && POSCON()) {
            return true;
        }
        next = caret;
        if (POSCON()) {
            return true;
        }
        next = caret;
        return false;
    }

    private boolean JOINER() {
        return term(TokenType.OPERATOR, "&&") || term(TokenType.OPERATOR, "||");
    }

    private boolean POSCON() {
        int caret = next;
        if (term(TokenType.SEPARATOR, "(") && PS() && term(TokenType.SEPARATOR, ")" +
                "")) {
            return true;
        }
        next = caret;
        if (PS()) {
            return true;
        }
        next = caret;
        return false;
    }

    private boolean PS() {
        int caret = next;
        if (term(TokenType.IDENTIFIER) && EQUALITY() && term(TokenType
                .RESERVED_WORD)) {
            return true;
        }
        next = caret;
        if (term(TokenType.IDENTIFIER) && COMPARATOR() && OPERATION()) {
            return true;
        }
        next = caret;
        if (term(TokenType.IDENTIFIER) && COMPARATOR() && term(TokenType
                .IDENTIFIER)) {
            return true;
        }
        next = caret;
        return false;
    }

    private boolean COMPARATOR() {
        return term(TokenType.OPERATOR, "==") || term(TokenType.OPERATOR, "!=") ||
                term(TokenType.OPERATOR, "<=") || term(TokenType.OPERATOR, ">=")
                || term(TokenType.OPERATOR, "<") || term(TokenType.OPERATOR, ">");
    }

    private boolean EQUALITY() {
        return term(TokenType.OPERATOR, "==") || term(TokenType.OPERATOR, "!=");
    }

    private boolean term(TokenType tokenType, String token) {
        if (next >= tokenList.size()) {
            return false;
        }
        Token nextToken = tokenList.get(next);
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

    private boolean term(TokenType tokenType) {
        if (next >= tokenList.size()) {
            return false;
        }
        Token nextToken = tokenList.get(next);
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
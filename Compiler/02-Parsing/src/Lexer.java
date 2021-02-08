import java.util.*;

class Lexer {

    private final ArrayList<String> ERRORS = new ArrayList<>();
    private final List<Token> tokenList = new ArrayList<>();
    private int currentLine = 1;
    private String nextToken;

    ArrayList<String> getERRORS() {
        return ERRORS;
    }

    List<Token> tokenize(String code) {
        int pointer;
        while (code.length() > 0) {
            code = removeLeadingSpaces(code);
            if (code.length() == 0) {
                break;
            }
            pointer = addNextToken(code);
            code = code.substring(pointer);
        }
        return tokenList;
    }

    private int addNextToken(String phrase) {
        int caret = 0;
        if (Character.isDigit(phrase.charAt(caret))) {
            while (caret != phrase.length() && Character.isDigit(phrase.charAt
                    (caret))) {
                caret++;
            }
            tokenList.add(new Token(phrase.substring(0, caret), "NUMBER",
                    currentLine));
            return caret;
        }
        switch (phrase.charAt(0)) {
            case '(':
            case ')':
            case '{':
            case '}':
            case ';':
                return makeUnary(caret, "SEPARATOR", phrase.substring(0, 1));
            case '=':
            case '!':
            case '<':
            case '>':
                return makeComparator(phrase, caret);
            case '&':
            case '|':
            case '+':
            case '-':
                return makeUnaryBinary(phrase.charAt(0), phrase.charAt(1), caret);
            case '/':
            case '*':
                return makeUnary(caret, "OPERATOR", phrase.substring(0, 1));
            default:
                return runDefaultCase(phrase, caret);
        }
    }

    private int makeUnary(int caret, String lexemeClass, String lexemeName) {
        caret++;
        tokenList.add(new Token(lexemeName, lexemeClass, currentLine));
        return caret;
    }

    private int makeComparator(String phrase, int caret) {
        if (phrase.charAt(1) == '=') {
            caret += 2;
            tokenList.add(new Token(phrase.charAt(0) + "=", "OPERATOR",
                    currentLine));
        } else {
            caret++;
            tokenList.add(new Token(phrase.substring(0, 1), "OPERATOR",
                    currentLine));
        }
        return caret;
    }

    private int makeUnaryBinary(char firstChar, char secondChar, int caret) {
        if (firstChar == secondChar) {
            caret += 2;
            tokenList.add(new Token("" + firstChar + secondChar, "OPERATOR",
                    currentLine));
        } else {
            caret++;
            tokenList.add(new Token("" + firstChar, "OPERATOR", currentLine));
        }
        return caret;
    }

    private int runDefaultCase(String phrase, int caret) {
        if (phrase.charAt(0) == '\uFEFF') {
            return ++caret;
        }
        caret = setNextToken(phrase, caret);
        switch (nextToken) {
            case "if":
            case "else":
            case "int":
            case "boolean":
                tokenList.add(new Token(nextToken, "KEYWORD", currentLine));
                break;
            case "true":
            case "false":
                tokenList.add(new Token(nextToken, "RESERVED_WORD", currentLine));
                break;
            default:
                if (validateAsIdentifier(nextToken)) {
                    tokenList.add(new Token(nextToken, "IDENTIFIER", currentLine));
                } else {
                    ERRORS.add("Lexical error at line " + currentLine +
                            ":\nillegal character");
                }
                break;
        }
        return caret;
    }

    private boolean validateAsIdentifier(String nextToken) {
        for (int i = 0; i < nextToken.length(); i++) {
            if (!Character.isJavaIdentifierPart(nextToken.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    private String removeLeadingSpaces(String phrase) {
        int i = 0;
        while (phrase.charAt(i) == ' ' || phrase.charAt(i) == '\r' || phrase
                .charAt(i) == '\n' || phrase.charAt(i) == '\t') {
            if (phrase.charAt(i) == '\n') {
                currentLine++;
            }
            i++;
            if (i == phrase.length()) {
                break;
            }
        }
        return phrase.substring(i);
    }

    private int setNextToken(String phrase, int caret) {
        while (phrase.charAt(caret) != '\r' && phrase.charAt(caret) != '\n' &&
                phrase.charAt(caret) != ' ' && phrase.charAt(caret) != '\t' &&
                phrase.charAt(caret) != ';' && phrase.charAt(caret) != '(' &&
                phrase.charAt(caret) != ')' && phrase.charAt(caret) != '{' &&
                phrase.charAt(caret) != '}' && phrase.charAt(caret) != '=' &&
                phrase.charAt(caret) != '!' && phrase.charAt(caret) != '<' &&
                phrase.charAt(caret) != '>' && phrase.charAt(caret) != '+' &&
                phrase.charAt(caret) != '-' && phrase.charAt(caret) != '*' &&
                phrase.charAt(caret) != '/' && phrase.charAt(caret) != '&' &&
                phrase.charAt(caret) != '|') {
            caret++;
            if (caret == phrase.length()) {
                break;
            }
        }
        nextToken = phrase.substring(0, caret);
        return caret;
    }
}
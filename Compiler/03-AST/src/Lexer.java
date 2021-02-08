import java.util.*;

class Lexer {

    private final ArrayList<String> Errors = new ArrayList<>();
    private final List<Token> tokenList = new ArrayList<>();
    private int currentLine = 1;
    private String nextToken;

    ArrayList<String> getErrors() {
        return Errors;
    }

    List<Token> tokenize(String code) {
        int pointer;
        while (code.length() > 0) {
            pointer = addNextToken(code);
            code = code.substring(pointer);
        }
        return tokenList;
    }

    private int addNextToken(String phrase) {
        int caret = 0;
        if (Character.isLetter(phrase.charAt(0))) {
            return makeWord(phrase, caret);
        } else if (Character.isDigit(phrase.charAt(0))) {
            return makeNumber(phrase, caret);
        } else if (phrase.charAt(0) == '(' || phrase.charAt(0) == ')' || phrase
                .charAt(0) == '{' || phrase.charAt(0) == '}' || phrase.charAt(0)
                == ',' || phrase.charAt(0) == ';') {
            tokenList.add(new Token(phrase.substring(0, 1), "SEPARATOR",
                    currentLine));
            return ++caret;
        } else if (phrase.charAt(0) == '/' || phrase.charAt(0) == '*') {
            tokenList.add(new Token(phrase.substring(0, 1), "OPERATOR",
                    currentLine));
            return ++caret;
        } else if (phrase.charAt(0) == '=' || phrase.charAt(0) == '!' || phrase
                .charAt(0) == '<' || phrase.charAt(0) == '>') {
            return makeComparator(phrase);
        } else if (phrase.charAt(0) == '+' || phrase.charAt(0) == '-' || phrase
                .charAt(0) == '&' || phrase.charAt(0) == '|') {
            return makeUnaryBinary(phrase);
        } else if (phrase.charAt(0) == ' ' || phrase.charAt(0) == '\t' || phrase
                .charAt(0) == '\n' || phrase.charAt(0) == '\r') {//   or '\f'
            return makeSpace(phrase, caret);
        } else {
            Errors.add("Lexical error at line " + currentLine + ":\nIllegal " +
                    "character");
            return ++caret;
        }
    }

    private int makeSpace(String phrase, int caret) {
        while (caret < phrase.length()) {
            if (phrase.charAt(caret) == '\n') {
                currentLine++;
            }
            if ((phrase.charAt(caret) == ' ' || phrase.charAt(caret) == '\t' ||
                    phrase.charAt(caret) == '\n' || phrase.charAt(caret) == '\r')) {
                caret++;
            } else {
                break;
            }
        }
        return caret;
    }

    private int makeNumber(String phrase, int caret) {
        while (caret < phrase.length()) {
            if (Character.isDigit(phrase.charAt(caret))) {
                caret++;
            } else {
                break;
            }
        }
        nextToken = phrase.substring(0, caret);
        tokenList.add(new Token(nextToken, "NUMBER", currentLine));
        return caret;
    }

    private int makeWord(String phrase, int caret) {
        while (caret < phrase.length()) {
            if (Character.isLetterOrDigit(phrase.charAt(caret))) {
                caret++;
            } else {
                break;
            }
        }
        nextToken = phrase.substring(0, caret);
        switch (nextToken) {
            case "int":
            case "boolean":
            case "if":
            case "else":
                tokenList.add(new Token(nextToken, "KEYWORD", currentLine));
                break;
            case "true":
            case "false":
                tokenList.add(new Token(nextToken, "RESERVED_WORD", currentLine));
                break;
            default:
                tokenList.add(new Token(nextToken, "IDENTIFIER", currentLine));
        }
        return caret;
    }

    private int makeUnaryBinary(String phrase) {
        if (phrase.length() < 2) {
            tokenList.add(new Token(phrase.substring(0, 1), "OPERATOR",
                    currentLine));
            return 1;
        }
        int end;
        if (phrase.charAt(1) == phrase.charAt(0)) {
            end = 2;
        } else {
            end = 1;
        }
        tokenList.add(new Token(phrase.substring(0, end), "OPERATOR", currentLine));
        return end;
    }

    private int makeComparator(String phrase) {
        if (phrase.length() < 2) {
            tokenList.add(new Token(phrase.substring(0, 1), "OPERATOR",
                    currentLine));
            return 1;
        }
        int end;
        if (phrase.charAt(1) == '=') {
            end = 2;
        } else {
            end = 1;
        }
        tokenList.add(new Token(phrase.substring(0, end), "OPERATOR", currentLine));
        return end;
    }
}
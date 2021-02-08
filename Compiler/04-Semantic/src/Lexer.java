import java.util.ArrayList;
import java.util.List;

import static java.lang.Character.*;

class Lexer {

    private List<Token> tokens = new ArrayList<>();
    private List<String> errors = new ArrayList<>();
    private int currentLine = 1;
    private String sourceCode;

    List<String> getErrors() {
        return errors;
    }

    List<Token> tokenize(String code) {
        sourceCode = code;
        int pointer;
        while (sourceCode.length() > 0) {
            pointer = addNextToken();
            sourceCode = sourceCode.substring(pointer);
        }
        return tokens;
    }

    private int addNextToken() {
        int caret = 0;
        char firstChar = sourceCode.charAt(0);
        if (isJavaIdentifierStart(firstChar)) {
            return makeWord(caret);
        } else if (isDigit(firstChar)) {
            return makeNumber(caret);
        } else if (isSeparator(firstChar)) {
            tokens.add(new Token("" + firstChar, "SEPARATOR", currentLine));
        } else if (firstChar == '/' || firstChar == '*') {
            tokens.add(new Token("" + firstChar, "OPERATOR", currentLine));
        } else if (isWhitespace(firstChar)) {
            return skipSpaces(caret);
        } else if (firstChar == '=' || firstChar == '!' || firstChar == '<' || firstChar == '>') {
            return makeUnaryBinary('=');
        } else if (firstChar == '+' || firstChar == '-' || firstChar == '&' || firstChar == '|') {
            return makeUnaryBinary(firstChar);
        } else {
            errors.add(String.format("Lexical error at line %d:%nillegal character <%c>", currentLine, firstChar));
        }
        return ++caret;
    }

    private int makeWord(int caret) {
        while (caret < sourceCode.length() && isJavaIdentifierPart(sourceCode.charAt(caret))) {
            caret++;
        }
        String lexeme = sourceCode.substring(0, caret);
        switch (lexeme) {
            case "int":
            case "boolean":
            case "if":
            case "else":
                tokens.add(new Token(lexeme, "KEYWORD", currentLine));
                break;
            case "true":
            case "false":
                tokens.add(new Token(lexeme, "RESERVED_WORD", currentLine));
                break;
            default:
                tokens.add(new Token(lexeme, "IDENTIFIER", currentLine));
        }
        return caret;
    }

    private int makeNumber(int caret) {
        while (caret < sourceCode.length() && isDigit(sourceCode.charAt(caret))) {
            caret++;
        }
        tokens.add(new Token(sourceCode.substring(0, caret), "NUMBER", currentLine));
        return caret;
    }

    private int skipSpaces(int caret) {
        while (caret < sourceCode.length() && isWhitespace(sourceCode.charAt(caret))) {
            if (sourceCode.charAt(caret) == '\n') {
                currentLine++;
            }
            caret++;
        }
        return caret;
    }

    private int makeUnaryBinary(char c) {
        int end = (sourceCode.length() < 2 || sourceCode.charAt(1) != c) ? 1 : 2;
        tokens.add(new Token(sourceCode.substring(0, end), "OPERATOR", currentLine));
        return end;
    }

    private boolean isSeparator(char c) {
        return c == '(' || c == ')' || c == '{' || c == '}' || c == ',' || c == ';';
    }
}

class Token {

    private final String tokenLexeme;
    private final String tokenClass;
    private final int tokenLine;

    Token(String lexeme, String category, int line) {
        tokenLexeme = lexeme;
        tokenClass = category;
        tokenLine = line;
    }

    String getTokenLexeme() {
        return tokenLexeme;
    }

    String getTokenClass() {
        return tokenClass;
    }

    int getTokenLine() {
        return tokenLine;
    }

    @Override
    public String toString() {
        return "Token{" + "data='" + tokenLexeme + '\'' + ", tokenClass='" +
                tokenClass + '\'' + ", tokenLine='" + tokenLine + '\'' + '}';
    }
}

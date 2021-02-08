class Token {

    private String tokenLexeme;
    private String tokenClass;
    private int tokenLine;

    Token(String lexeme, String category, int line) {
        tokenLexeme = lexeme;
        tokenClass = category;
        tokenLine = line;
    }

    @Override
    public String toString() {
        return "Token{" + "tokenLexeme='" + tokenLexeme + '\'' + ", tokenClass='"
                + tokenClass + '\'' + ", tokenLine=" + tokenLine + '}';
    }
}

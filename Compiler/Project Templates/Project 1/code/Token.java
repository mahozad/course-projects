/**
 * Created by mojtab23 on 9/26/16.
 */
public class Token {
    private String tokenLexeme;
    private String tokenClass;
    private int Line;

    public Token(String tLexeme, String tClass) {
        tokenLexeme = tLexeme;
        tokenClass = tClass;
    }

    public Token(String tLexeme, String tClass, int line) {
        tokenLexeme = tLexeme;
        tokenClass = tClass;
        Line = line;
    }

    @Override
    public String toString() {
        return "Token{" +
                "tokenLexeme='" + tokenLexeme + '\'' +
                ", tokenClass='" + tokenClass + '\'' +
                ", Line=" + Line +
                '}';
    }

    public String getLexeme() {
        return tokenLexeme;
    }

    public void setLexeme(String tLexeme) {
        tokenLexeme = tLexeme;
    }

    public String getTokenClass() {
        return tokenClass;
    }

    public void setTokenClass(String tClass) {
        tokenClass = tClass;
    }

    public int getLine() {
        return Line;
    }

    public void setLine(int line) {
        Line = line;
    }
}

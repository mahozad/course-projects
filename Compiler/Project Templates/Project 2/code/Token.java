/**
 * Created by mojtab23 on 9/26/16.
 */
public class Token {

    private String data;
    private String tokenClass;
    private int line;
    private int start;

    public Token(String data, String tokenClass) {
        this.data = data;
        this.tokenClass = tokenClass;
    }

    public Token(String data, String tokenClass, int line, int start) {
        this.data = data;
        this.tokenClass = tokenClass;
        this.line = line;
        this.start = start;
    }

    @Override
    public String toString() {
        return "Token{" +
                "data='" + data + '\'' +
                ", tokenClass='" + tokenClass + '\'' +
//                ", line=" + line +
//                ", start=" + start +
                '}';
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getTokenClass() {
        return tokenClass;
    }

    public void setTokenClass(String tokenClass) {
        this.tokenClass = tokenClass;
    }

    public int getLine() {
        return line;
    }

    public void setLine(int line) {
        this.line = line;
    }

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }




}

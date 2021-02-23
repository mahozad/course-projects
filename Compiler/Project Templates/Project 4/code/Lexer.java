import java.util.ArrayList;
import java.util.List;

/**
 * Created by mojtab23 on 9/25/16.
 */
public class Lexer {
    public List<Token> tokenize(String inputData) {
        int pointer = 0;
        List<Token> tokenList = new ArrayList<>();
        while (inputData.length() > 0) {
            pointer = addNextToken(inputData, tokenList);
            if (pointer < 0)
                break;
            inputData = inputData.substring(pointer + 1);
        }
        return tokenList;
    }

    private int addNextToken(String inputData, List<Token> tokenList) {
//		Your code goes here!
//		Extract the next token from inputData and add it to tokenList
//		The return value refers to the end of next token in inputData
        return 1;
    }
}
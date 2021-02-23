import javax.swing.*;

import static javax.swing.JOptionPane.*;

public class Calculator {

    private String phrase;
    private MainFrame mainFrame;
    private int startOfParentheses;
    private int endOfParentheses;
    private boolean minus[] = new boolean[2];

    public void mainMethod() throws InterruptedException {
        mainFrame = new MainFrame();
        mainFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        mainFrame.setSize(265, 265);
        mainFrame.setLocationRelativeTo(null);
        mainFrame.setVisible(true);
        showFrame();
    }

    public void showFrame() {
        phrase = mainFrame.getStatement();
        phrase = phrase.replaceAll(" ", "");
        phrase = phrase.replaceAll("=", "");
        errorChecker();
        parenthesesLocator();
        while (parenthesesState()) {
            parenthesesCalculator();
        }
        statementCalculator();
        mainFrame.jTextField1.setText(phrase);
        mainFrame.setStatement();
        showFrame();
    }

    public void errorChecker() {
        while (!parenthesesMatch() || !characterErrorFinder() || !sinLogErrorFinder()) {
            if (!parenthesesMatch()) {
                phrase = showInputDialog(null, "The parentheses doesn't match." +
                        " Please enter your statement correct: ", "Error", PLAIN_MESSAGE);
            } else if (!characterErrorFinder()) {
                phrase = showInputDialog(null, "Your statement has invalid characters." +
                        " Please enter your statement correct: ", "Error", PLAIN_MESSAGE);
            } else if (!sinLogErrorFinder()) {
                phrase = showInputDialog(null, "Please enter your sin or log string correct: ", "Error", PLAIN_MESSAGE);
            }
            phrase = phrase.replaceAll(" ", "");
            phrase = phrase.replaceAll("=", "");
        }
    }

    public boolean characterErrorFinder() {
        for (int i = 0; i < phrase.length(); i++) {
            if (!isNumber(i) && phrase.charAt(i) != 's'
                    && phrase.charAt(i) != 'i' && phrase.charAt(i) != 'n' && phrase.charAt(i) != 'l'
                    && phrase.charAt(i) != 'o' && phrase.charAt(i) != 'g' && phrase.charAt(i) != '^'
                    && phrase.charAt(i) != '*' && phrase.charAt(i) != '/' && phrase.charAt(i) != '%'
                    && phrase.charAt(i) != '+' && phrase.charAt(i) != '-' && phrase.charAt(i) != '('
                    && phrase.charAt(i) != ')') {
                return false;
            }
        }
        return true;
    }

    public boolean sinLogErrorFinder() {
        for (int i = 0; i < phrase.length(); i++) {
            if (phrase.charAt(i) == 's') {
                if (!phrase.startsWith("sin", i)) {
                    return false;
                }
            }
            if (phrase.charAt(i) == 'l') {
                if (!phrase.startsWith("log", i)) {
                    return false;
                }
            }
            if (phrase.charAt(i) == 'n' && i + 1 >= phrase.length()) {
                return false;
            }
            if (phrase.charAt(i) == 'g' && i + 1 >= phrase.length()) {
                return false;
            }
        }
        return true;
    }

    public boolean parenthesesMatch() {
        int counterRightBrace = 0;
        int counterLeftBrace = 0;
        for (int i = 0; i < phrase.length(); i++) {
            if (phrase.charAt(i) == '(') {
                counterRightBrace++;
            }
            if (phrase.charAt(i) == ')') {
                if (counterRightBrace == 0) {
                    return false;
                }
                counterLeftBrace++;
            }
        }
        return counterRightBrace == counterLeftBrace;
    }

    public boolean parenthesesState() {
        for (int i = 0; i < phrase.length(); i++) {
            if (phrase.charAt(i) == '(') {
                return true;
            }
        }
        return false;
    }

    public void parenthesesLocator() {
        for (int i = 0; i < phrase.length(); i++) {
            if (phrase.charAt(i) == '(') {
                startOfParentheses = i;
                for (int j = i; j < phrase.length(); j++) {
                    if (phrase.charAt(j) == ')') {
                        endOfParentheses = j;
                        i = j - 1;
                        break;
                    }
                }
                parenthesesRemover();
            }
        }
    }

    public void parenthesesRemover() {
        int counter = 0;
        if (parenthesesState()) {
            for (int i = startOfParentheses; i < endOfParentheses; i++) {
                if (isOperand(i)) {
                    counter++;
                }
            }
            if (counter < 1) {
                String str = phrase.substring(startOfParentheses + 1, endOfParentheses);
                phrase = phrase.substring(0, startOfParentheses) + str + phrase.substring(endOfParentheses + 1);
            }
        }
    }

    public void sinLogFinder() {
        int startOfSinLog;
        int endOfSinLog = 0;
        String sum;
        String sinLog;
        for (int i = 0; i < phrase.length(); i++) {
            if (phrase.charAt(i) == 's' || phrase.charAt(i) == 'l') {
                if (phrase.charAt(i + 3) == '(' || phrase.charAt(i + 3) == 's' || phrase.charAt(i + 3) == 'l') {
                    continue;
                }
                startOfSinLog = i;
                if (phrase.charAt(i + 3) == '-') {
                    for (int j = i + 4; j < phrase.length(); j++) {
                        if (isNumber(j)) {
                            endOfSinLog = j + 1;
                        } else {
                            break;
                        }
                    }
                    sinLog = phrase.substring(startOfSinLog, endOfSinLog);
                    sum = sinLogCalculator(sinLog);
                    phrase = phrase.replace(sinLog, sum);
                    parenthesesLocator();
                    parenthesesRemover();
                    i = -1;
                } else {
                    for (int j = i + 3; j < phrase.length(); j++) {
                        if (isNumber(j)) {
                            endOfSinLog = j + 1;
                        } else {
                            break;
                        }
                    }
                    sinLog = phrase.substring(startOfSinLog, endOfSinLog);
                    sum = sinLogCalculator(sinLog);
                    phrase = phrase.replace(sinLog, sum);
                    parenthesesLocator();
                    parenthesesRemover();
                    i = -1;
                }
            }
        }
    }

    public void sinLogFinder(int i) {
        int startOfSinLog;
        int endOfSinLog = 0;
        String sum;
        String sinLog;
        for (int num = i; num < phrase.length(); num++) {
            if (phrase.charAt(num) == 's' || phrase.charAt(num) == 'l') {
                if (phrase.charAt(num + 3) == '(' || phrase.charAt(num + 3) == 's' || phrase.charAt(num + 3) == 'l') {
                    continue;
                }
                startOfSinLog = num;
                if (phrase.charAt(num + 3) == '-') {
                    for (int j = num + 4; j < phrase.length(); j++) {
                        if (isNumber(j)) {
                            endOfSinLog = j + 1;
                        } else {
                            break;
                        }
                    }
                    sinLog = phrase.substring(startOfSinLog, endOfSinLog);
                    sum = sinLogCalculator(sinLog);
                    phrase = phrase.replace(sinLog, sum);
                    parenthesesLocator();
                } else {
                    for (int j = num + 3; j < phrase.length(); j++) {
                        if (isNumber(j)) {
                            endOfSinLog = j + 1;
                        } else {
                            break;
                        }
                    }
                    sinLog = phrase.substring(startOfSinLog, endOfSinLog);
                    sum = sinLogCalculator(sinLog);
                    phrase = phrase.replace(sinLog, sum);
                    parenthesesLocator();
                }
            }
        }
    }

    public String sinLogCalculator(String statement) {
        double realNumber;
        double sum;
        String strSum;
        if (statement.charAt(0) == 's') {
            if (statement.charAt(3) == '-') {
                realNumber = numberValue(statement.substring(4));
                realNumber = realNumber * -1;
                realNumber = realNumber * 0.0174532925;
                sum = Math.sin(realNumber);
                strSum = String.valueOf(sum);
                return strSum;
            } else {
                realNumber = numberValue(statement.substring(3));
                realNumber = realNumber * 0.0174532925;
                sum = Math.sin(realNumber);
                strSum = String.valueOf(sum);
                return strSum;
            }
        } else if (statement.charAt(0) == 'l') {
            if (statement.charAt(3) == '-') {
                realNumber = numberValue(statement.substring(4));
                realNumber = realNumber * -1;
                sum = Math.log10(realNumber);
                strSum = String.valueOf(sum);
                return strSum;
            } else {
                realNumber = numberValue(statement.substring(3));
                sum = Math.log10(realNumber);
                strSum = String.valueOf(sum);
                return strSum;
            }
        }
        return null;
    }

    public void powerFinder() {
        String sum;
        int startPower;
        int endPower;
        String power;
        for (int i = 0; i < phrase.length(); i++) {
            if (phrase.charAt(i) == '^') {
                if (phrase.charAt(i - 1) == ')' || phrase.charAt(i + 1) == '('
                        || phrase.charAt(i + 1) == 's' || phrase.charAt(i + 1) == 'l') {
                    continue;
                }
                startPower = startOfOperandFinder(i);
                endPower = endOfOperandFinder(i);
                power = phrase.substring(startPower, endPower);
                sum = numberFinder(power);
                phrase = phrase.replace(power, sum);
                parenthesesLocator();
                parenthesesRemover();
                statementCalculator();
                i = -1;
            }
        }
    }

    public void powerFinder(int num) {
        String sum;
        int startPower;
        int endPower;
        String power;
        for (int i = num; i < phrase.length(); i++) {
            if (phrase.charAt(i) == '^') {
                if (phrase.charAt(i - 1) == ')' || phrase.charAt(i + 1) == '('
                        || phrase.charAt(i + 1) == 's' || phrase.charAt(i + 1) == 'l') {
                    continue;
                }
                startPower = startOfOperandFinder(i);
                endPower = endOfOperandFinder(i);
                power = phrase.substring(startPower, endPower);
                sum = numberFinder(power);
                phrase = phrase.replace(power, sum);
                parenthesesLocator();
                parenthesesCalculator();
            }
        }
    }

    public void mulDivRemFinder() {
        String sum;
        int startStatement;
        int endStatement;
        String statement;
        for (int i = 0; i < phrase.length(); i++) {
            if (phrase.charAt(i) == '*' || phrase.charAt(i) == '/' || phrase.charAt(i) == '%') {
                if (phrase.charAt(i - 1) == ')' || phrase.charAt(i + 1) == '('
                        || phrase.charAt(i + 1) == 's' || phrase.charAt(i + 1) == 'l') {
                    continue;
                }
                startStatement = startOfOperandFinder(i);
                endStatement = endOfOperandFinder(i);
                statement = phrase.substring(startStatement, endStatement);
                sum = numberFinder(statement);
                phrase = phrase.replace(statement, sum);
                parenthesesLocator();
                parenthesesRemover();
                statementCalculator();
                i = -1;
            }
        }
    }

    public void mulDivRemFinder(int num) {
        String sum;
        int startStatement;
        int endStatement;
        String statement;
        for (int i = num; i < phrase.length(); i++) {
            if (phrase.charAt(i) == '*' || phrase.charAt(i) == '/' || phrase.charAt(i) == '%') {
                if (phrase.charAt(i - 1) == ')' || phrase.charAt(i + 1) == '('
                        || phrase.charAt(i + 1) == 's' || phrase.charAt(i + 1) == 'l') {
                    continue;
                }
                startStatement = startOfOperandFinder(i);
                endStatement = endOfOperandFinder(i);
                statement = phrase.substring(startStatement, endStatement);
                sum = numberFinder(statement);
                phrase = phrase.replace(statement, sum);
                parenthesesLocator();
                parenthesesCalculator();
            }
        }
    }

    public void addSubFinder() {
        String sum;
        int startStatement;
        int endStatement;
        String statement;
        for (int i = 0; i < phrase.length(); i++) {
            if (phrase.charAt(i) == '+' || (phrase.charAt(i) == '-' && subtractionChecker(i))) {
                if (phrase.charAt(i - 1) == ')' || phrase.charAt(i + 1) == '('
                        || phrase.charAt(i + 1) == 's' || phrase.charAt(i + 1) == 'l') {
                    continue;
                }
                startStatement = startOfOperandFinder(i);
                endStatement = endOfOperandFinder(i);
                statement = phrase.substring(startStatement, endStatement);
                sum = numberFinder(statement);
                phrase = phrase.replace(statement, sum);
                parenthesesLocator();
                parenthesesRemover();
                statementCalculator();
                i = -1;
            }
        }
    }

    public void addSubFinder(int num) {
        String sum;
        int startStatement;
        int endStatement;
        String statement;
        for (int i = num; i < phrase.length(); i++) {
            if (phrase.charAt(i) == '+' || (phrase.charAt(i) == '-' && subtractionChecker(i))) {
                if (phrase.charAt(i - 1) == ')' || phrase.charAt(i + 1) == '('
                        || phrase.charAt(i + 1) == 's' || phrase.charAt(i + 1) == 'l') {
                    continue;
                }
                startStatement = startOfOperandFinder(i);
                endStatement = endOfOperandFinder(i);
                statement = phrase.substring(startStatement, endStatement);
                sum = numberFinder(statement);
                phrase = phrase.replace(statement, sum);
                parenthesesLocator();
                parenthesesCalculator();
            }
        }
    }

    public boolean subtractionChecker(int i) {
        if (i == 0) {
            return (phrase.charAt(i) >= '0' && phrase.charAt(i) <= '9');
        } else {
            for (int j = i - 1; j >= 0; j--) {
                return (phrase.charAt(j) >= '0' && phrase.charAt(j) <= '9');
            }
        }
        return false;
    }

    public int startOfOperandFinder(int i) {
        int start = 0;
        for (int j = i - 1; j >= 0; j--) {
            if (isNumber(j)) {
                start = j;
            }
            if (phrase.charAt(j) == '-' && !subtractionChecker(j)) {
                return j;
            }
            if (phrase.charAt(j) == '+' || (phrase.charAt(j) == '-' && subtractionChecker(i)) || phrase.charAt(j) == '*'
                    || phrase.charAt(j) == '/' || phrase.charAt(j) == '%' || phrase.charAt(j) == '^'
                    || phrase.charAt(j) == '(' || phrase.charAt(j) == ')') {
                return start;
            }
        }
        return start;
    }

    public int endOfOperandFinder(int i) {
        boolean checker = true;
        int end = 0;
        for (int j = i + 1; j < phrase.length(); j++) {
            if (phrase.charAt(i + 1) == '-' && checker) {
                j++;
                checker = false;
            }
            if (isNumber(j)) {
                end = j;
            } else {
                break;
            }
        }
        return end + 1;
    }

    public void setParentheses() {
        for (int i = 0; i < phrase.length(); i++) {
            if (phrase.charAt(i) == '(') {
                startOfParentheses = i;
                for (int j = i; j < phrase.length(); j++) {
                    if (phrase.charAt(j) == ')') {
                        endOfParentheses = j;
                        break;
                    }
                }
                break;
            }
        }
    }

    public void parenthesesCalculator() {
        setParentheses();
        sinLogFinder(startOfParentheses);
        setParentheses();
        powerFinder(startOfParentheses);
        setParentheses();
        mulDivRemFinder(startOfParentheses);
        setParentheses();
        addSubFinder(startOfParentheses);
    }

    public void statementCalculator() {
        parenthesesLocator();
        sinLogFinder();
        powerFinder();
        mulDivRemFinder();
        addSubFinder();
    }

    public String numberFinder(String statement) {
        int counter;
        char operand = 0;
        String number1 = null;
        int startNumber1;
        int endNumber1;
        String number2 = null;
        if (statement.charAt(0) == '-') {
            minus[0] = true;
            startNumber1 = 1;
            counter = 1;
            while (statement.charAt(counter) != '+' && statement.charAt(counter) != '-' &&
                    statement.charAt(counter) != '*' && statement.charAt(counter) != '/' &&
                    statement.charAt(counter) != '%' && statement.charAt(counter) != '^') {
                counter++;
            }
            operand = statement.charAt(counter);
            endNumber1 = counter;
            number1 = statement.substring(startNumber1, endNumber1);
            if (statement.charAt(counter + 1) == '-') {
                minus[1] = true;
                number2 = statement.substring(counter + 2);
            } else {
                number2 = statement.substring(counter + 1);
            }
        }
        if (statement.charAt(0) != '-') {
            startNumber1 = 0;
            counter = 0;
            while (statement.charAt(counter) != '+' && statement.charAt(counter) != '-' &&
                    statement.charAt(counter) != '*' && statement.charAt(counter) != '/' &&
                    statement.charAt(counter) != '%' && statement.charAt(counter) != '^') {
                counter++;
            }
            operand = statement.charAt(counter);
            endNumber1 = counter;
            number1 = statement.substring(startNumber1, endNumber1);
            if (statement.charAt(counter + 1) == '-') {
                minus[1] = true;
                number2 = statement.substring(counter + 2);
            } else {
                number2 = statement.substring(counter + 1);
            }
        }
        double realNumber1 = numberValue(number1);
        double realNumber2 = numberValue(number2);
        double sum = oneOperandCalculator(realNumber1, realNumber2, operand);
        String strSum = String.valueOf(sum);
        minus[0] = false;
        minus[1] = false;
        return strSum;
    }

    public double numberValue(String number) {
        boolean dot = false;
        double sum = 0;
        int sign = -1;
        for (int i = 0; i < number.length(); i++) {
            if (number.charAt(i) != '.' && !dot) {
                sum = (sum * 10) + (number.charAt(i) - '0');
            }
            if (number.charAt(i) == '.') {
                dot = true;
                continue;
            }
            if (dot) {
                sum = sum + (number.charAt(i) - '0') * Math.pow(10, sign);
                sign--;
            }
        }
        return sum;
    }

    public double oneOperandCalculator(double number1, double number2, char function) {
        switch (function) {
            case '*':
                return multiplicationCalculator(number1, number2);
            case '/':
                return divisionCalculator(number1, number2);
            case '%':
                return number1 % number2;
            case '+':
                return additionCalculator(number1, number2);
            case '-':
                return subtractionCalculator(number1, number2);
            case '^':
                return powerCalculator(number1, number2);
            default:
                return 0;
        }
    }

    public double multiplicationCalculator(double number1, double number2) {
        double sum = number1 * number2;
        if (minus[0] ^ minus[1]) {
            sum = sum * -1;
            return sum;
        }
        return sum;
    }

    public double divisionCalculator(double number1, double number2) {
        double sum = number1 / number2;
        if (minus[0] ^ minus[1]) {
            sum *= -1;
        }
        return sum;
    }

    public double additionCalculator(double number1, double number2) {
        double sum;
        if (minus[0] && !minus[1]) {
            sum = number2 - number1;
            return sum;
        } else if (!minus[0] && minus[1]) {
            sum = number1 - number2;
            return sum;
        } else if (minus[0] & minus[1]) {
            number1 *= -1;
            number2 *= -1;
            sum = number1 + number2;
            return sum;
        } else {
            sum = number1 + number2;
            return sum;
        }
    }

    public double subtractionCalculator(double number1, double number2) {
        double sum;
        if (minus[0] && !minus[1]) {
            sum = -number1 - number2;
            return sum;
        } else if (!minus[0] && minus[1]) {
            sum = number1 + number2;
            return sum;
        } else if (minus[0] & minus[1]) {
            sum = number1 - number2;
            return -sum;
        } else {
            sum = number1 - number2;
            return sum;
        }
    }

    public double powerCalculator(double number1, double number2) {
        double sum;
        if (minus[0] && !minus[1]) {
            sum = Math.pow(-number1, number2);
            return sum;
        } else if (!minus[0] && minus[1]) {
            sum = Math.pow(number1, -number2);
            return sum;
        } else if (minus[0] & minus[1]) {
            sum = Math.pow(-number1, -number2);
            return sum;
        } else {
            sum = Math.pow(number1, number2);
            return sum;
        }
    }

    public boolean isOperand(int i) {
        return (phrase.charAt(i) == '+' || (phrase.charAt(i) == '-' && subtractionChecker(i)) ||
                phrase.charAt(i) == '*' || phrase.charAt(i) == '/' || phrase.charAt(i) == '%' ||
                phrase.charAt(i) == '^' || phrase.charAt(i) == 'l' || phrase.charAt(i) == 's');
    }

    public boolean isNumber(int i) {
        return ((phrase.charAt(i) >= '0' && phrase.charAt(i) <= '9') || phrase.charAt(i) == '.');
    }
}
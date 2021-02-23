import java.util.*;

class Calculator {

    private static String expression;
    private Stack<Character> stack = new Stack<>();

    static void setExpression(String expression) {
        Calculator.expression = expression;
    }

    void run() {
        calculateSinCos();
    }

    private boolean isOperator(char c) {
        return c == '+' || c == '-' || c == '*' || c == '/' || c == '×' || c == '÷';
    }

    private void calculateSinCos() {
        double number;
        int startOfSinCos;
        int endOfSinCos = 0;
        int length = expression.length();
        for (int i = 0; i < length; i++) {
            if (expression.indexOf("cos", i) > -1) {
                startOfSinCos = expression.indexOf("cos", i);
                for (int j = expression.charAt(i + 3) == '-' ? i + 4 : i + 3; j <
                        length; j++) {
                    if (Character.isDigit(expression.charAt(j))) {
                        endOfSinCos = i = j + 1;
                    }
                }
                number = Integer.parseInt(expression.substring(startOfSinCos + 3,
                        endOfSinCos));
                expression = expression.replaceAll(expression.substring(startOfSinCos,
                        endOfSinCos), String.valueOf(Math.cos(Math.toRadians(number))));
            } else if (expression.indexOf("sin", i) > -1) {
                startOfSinCos = expression.indexOf("sin", i);
                for (int j = expression.charAt(i + 3) == '-' ? i + 4 : i + 3; j <
                        length; j++) {
                    if (Character.isDigit(expression.charAt(j))) {
                        endOfSinCos = i = j + 1;
                    }
                }
                number = Integer.parseInt(expression.substring(startOfSinCos + 3,
                        endOfSinCos));
                expression = expression.replaceAll(expression.substring(startOfSinCos,
                        endOfSinCos), String.valueOf(Math.sin(Math.toRadians(number))));
            }
        }
    }
}
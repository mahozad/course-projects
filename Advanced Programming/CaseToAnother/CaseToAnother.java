public class CaseToAnother {

    private static MainFrame mainFrame;
    private static String string;

    public static void main(String[] args) {
        mainFrame = new MainFrame();
        mainFrame.setSize(227, 212);
        mainFrame.setLocationRelativeTo(null);
        mainFrame.setVisible(true);
        showFrame();
    }

    public static void showFrame() {
        string = mainFrame.getString();
        for (int i = 0; i < string.length(); i++) {
            if (string.charAt(i) >= 'A' && string.charAt(i) <= 'Z') {
                toLowerCase(i);
            } else if (string.charAt(i) >= 'a' && string.charAt(i) <= 'z') {
                toUpperCase(i);
            }
            if (string.charAt(i) >= '0' && string.charAt(i) <= '9') {
                numberCalculator(i);
            }
        }
        mainFrame.jTextField2.setText(string);
        mainFrame.setString();
        showFrame();
    }

    public static void toLowerCase(int i) {
        int newChar = string.charAt(i) - 'A' + 'a';
        string = string.substring(0, i) + (char) newChar + string.substring(i + 1);
    }

    public static void toUpperCase(int i) {
        int newChar = string.charAt(i) - 'a' + 'A';
        string = string.substring(0, i) + (char) newChar + string.substring(i + 1);
    }

    public static int realNumber(int i) {
        return string.charAt(i) - '0';
    }

    public static void numberCalculator(int i) {
        int length = string.length();
        int sum = 0;
        sum = sum + realNumber(i);
        int counter = i + 1;
        while (counter < length && string.charAt(counter) >= '0' && string.charAt(counter) <= '9') {
            sum = sum + realNumber(counter);
            counter++;
        }
        String number = String.valueOf(sum);
        string = string.substring(0, i) + number + string.substring(counter);
    }
}
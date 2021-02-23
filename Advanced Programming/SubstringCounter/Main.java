public class Main {

    private static MainFrame mainFrame;

    public static void main(String[] args) {
        mainFrame = new MainFrame();
        mainFrame.setSize(380, 200);
        mainFrame.setLocationRelativeTo(null);
        mainFrame.setVisible(true);
        showFrame();
    }

    public static void showFrame() {
        int counter = 0;
        String statement = mainFrame.getString1();
        String subString = mainFrame.getString2();
        int length = subString.length();
        if (mainFrame.jRadioButton1.isSelected()) {
            for (int i = 0; i < statement.length(); i++) {
                if (length + i > statement.length()) {
                    break;
                }
                if (i == statement.indexOf(subString, i)) {
                    counter++;
                }
            }
        } else if (mainFrame.jRadioButton2.isSelected()) {
            for (int i = 0; i < statement.length(); i++) {
                if (length + i > statement.length()) {
                    break;
                }
                if (subString.equals(statement.substring(i, length + i))) {
                    counter++;
                    i += length - 1;
                }
            }
        }
        mainFrame.jTextField3.setText(String.valueOf(counter));
        mainFrame.string1 = "";
        mainFrame.string2 = "";
        showFrame();
    }
}
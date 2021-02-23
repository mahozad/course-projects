import java.awt.event.*;
import javax.swing.*;
import java.awt.*;

class MainFrame extends JFrame {

    private final JTextField textField = new JTextField();
    private final JButton clearButton = new JButton("C");
    private final JButton sinButton = new JButton("sin");
    private final JButton cosButton = new JButton("cos");
    private final JButton divisionButton = new JButton("÷");
    private final JButton multiplyButton = new JButton("×");
    private final JButton minusButton = new JButton("-");
    private final JButton plusButton = new JButton("+");
    private final JButton equalButton = new JButton("=");
    private final JButton dotButton = new JButton(".");
    private final JButton[] numbers = new JButton[10];
    private boolean canStart = false;
    private String expression;

    MainFrame() {
        super("Calculator");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setIconImage(new ImageIcon("icon.png").getImage());
        setResizable(false);
        initComponents();
        setLocationRelativeTo(null);
    }

    private void initComponents() {
        textField.setSelectionColor(new Color(210, 0, 110));
        textField.addActionListener(validateStatement());
        clearButton.addActionListener(e -> {
            textField.setText("");
            textField.requestFocus();
        });
        sinButton.addActionListener(buttonPressed());
        cosButton.addActionListener(buttonPressed());
        divisionButton.addActionListener(buttonPressed());
        multiplyButton.addActionListener(buttonPressed());
        minusButton.addActionListener(buttonPressed());
        plusButton.addActionListener(buttonPressed());
        equalButton.addActionListener(validateStatement());
        dotButton.addActionListener(buttonPressed());
        for (int i = 0; i < 10; i++) {
            numbers[i] = new JButton(String.valueOf(i));
            numbers[i].addActionListener(buttonPressed());
        }
        makeLayout();
    }

    private ActionListener buttonPressed() {
        return e -> {
            textField.setText(textField.getText() + e.getActionCommand());
            textField.requestFocus();
        };
    }

    private ActionListener validateStatement() {
        return e -> {
            expression = textField.getText().replaceAll("\\s", "");
            if (expression.matches("[\\dsinco\\-+*/×÷. ]+") && (parenthesesMatch()) &&
                    (validateSinCos())) {
                Calculator.setExpression(expression);
                synchronized (Application.class) {
                    canStart = true;
                    Application.class.notifyAll();
                }
            } else {
                textField.setText("Error in the expression");
            }
        };
    }

    private boolean parenthesesMatch() {
        int rightBraceCounter = 0;
        int leftBraceCounter = 0;
        for (int i = 0; i < expression.length(); i++) {
            if (expression.charAt(i) == '(') {
                rightBraceCounter++;
            } else if (expression.charAt(i) == ')') {
                if (rightBraceCounter == 0) {
                    return false;
                } else {
                    leftBraceCounter++;
                }
            }
        }
        return (rightBraceCounter == leftBraceCounter);
    }

    private boolean validateSinCos() {
        try {
            for (int i = 0; i < expression.length(); i++) {
                if ((expression.charAt(i) == 's') && (i != 0) && (expression.charAt(i -
                        1) != 'o') && (!expression.substring(i, i + 4).matches("" +
                        "(sin\\d+)||(sin-\\d+)"))) {
                    return false;
                } else if ((expression.charAt(i) == 'c') && (!expression.substring(i, i
                        + 4).matches("(cos\\d)||(cos-)"))) {
                    return false;
                }
            }
        } catch (Exception e) {
            textField.setText("Error in the expression");
        }
        return true;
    }

    boolean canStart() {
        return canStart;
    }

    void setStartDisabled() {
        canStart = false;
    }

    private void makeLayout() {
        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment
                .LEADING).addGroup(layout.createSequentialGroup().addContainerGap()
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING,
                        false).addGroup(layout.createSequentialGroup().addGroup(layout
                        .createParallelGroup(GroupLayout.Alignment.TRAILING, false)
                        .addComponent(numbers[7], GroupLayout.Alignment.LEADING,
                                GroupLayout.DEFAULT_SIZE, 48, Short.MAX_VALUE)
                        .addComponent(clearButton, GroupLayout.Alignment.LEADING,
                                GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE,
                                Short.MAX_VALUE)).addPreferredGap(LayoutStyle
                        .ComponentPlacement.UNRELATED).addGroup(layout
                        .createParallelGroup(GroupLayout.Alignment.LEADING, false)
                        .addGroup(layout.createSequentialGroup().addComponent
                                (sinButton, GroupLayout.PREFERRED_SIZE, 77, GroupLayout
                                        .PREFERRED_SIZE).addPreferredGap(LayoutStyle
                                .ComponentPlacement.UNRELATED).addComponent(cosButton,
                                GroupLayout.PREFERRED_SIZE, 77, GroupLayout
                                        .PREFERRED_SIZE)).addGroup(layout
                                .createSequentialGroup().addComponent(numbers[8],
                                        GroupLayout.PREFERRED_SIZE, 48, GroupLayout
                                                .PREFERRED_SIZE).addPreferredGap
                                        (LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(numbers[9], GroupLayout.PREFERRED_SIZE,
                                        48, GroupLayout.PREFERRED_SIZE).addPreferredGap
                                        (LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(divisionButton, GroupLayout
                                        .PREFERRED_SIZE, 48, GroupLayout
                                        .PREFERRED_SIZE)))).addGroup(layout
                        .createSequentialGroup().addComponent(numbers[4], GroupLayout
                                .PREFERRED_SIZE, 48, GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(numbers[5], GroupLayout.PREFERRED_SIZE, 48,
                                GroupLayout.PREFERRED_SIZE).addPreferredGap(LayoutStyle
                                .ComponentPlacement.UNRELATED).addComponent(numbers[6],
                                GroupLayout.PREFERRED_SIZE, 48, GroupLayout
                                        .PREFERRED_SIZE).addPreferredGap(LayoutStyle
                                .ComponentPlacement.UNRELATED).addComponent
                                (multiplyButton, GroupLayout.PREFERRED_SIZE, 48,
                                        GroupLayout.PREFERRED_SIZE)).addGroup(layout
                        .createSequentialGroup().addComponent(numbers[1], GroupLayout
                                .PREFERRED_SIZE, 48, GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(numbers[2], GroupLayout.PREFERRED_SIZE, 48,
                                GroupLayout.PREFERRED_SIZE).addPreferredGap(LayoutStyle
                                .ComponentPlacement.UNRELATED).addComponent(numbers[3],
                                GroupLayout.PREFERRED_SIZE, 48, GroupLayout
                                        .PREFERRED_SIZE).addPreferredGap(LayoutStyle
                                .ComponentPlacement.UNRELATED).addComponent
                                (minusButton, GroupLayout.PREFERRED_SIZE, 48,
                                        GroupLayout.PREFERRED_SIZE)).addGroup(layout
                        .createSequentialGroup().addComponent(numbers[0], GroupLayout
                                .PREFERRED_SIZE, 48, GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(dotButton, GroupLayout.PREFERRED_SIZE, 48,
                                GroupLayout.PREFERRED_SIZE).addPreferredGap(LayoutStyle
                                .ComponentPlacement.UNRELATED).addComponent
                                (equalButton, GroupLayout.PREFERRED_SIZE, 48,
                                        GroupLayout.PREFERRED_SIZE).addPreferredGap
                                (LayoutStyle.ComponentPlacement.UNRELATED).addComponent
                                (plusButton, GroupLayout.PREFERRED_SIZE, 48,
                                        GroupLayout.PREFERRED_SIZE)).addComponent
                        (textField)).addContainerGap(GroupLayout.DEFAULT_SIZE, Short
                        .MAX_VALUE)));
        layout.setVerticalGroup(layout.createParallelGroup(GroupLayout.Alignment
                .LEADING).addGroup(layout.createSequentialGroup().addContainerGap()
                .addComponent(textField, GroupLayout.PREFERRED_SIZE, 30, GroupLayout
                        .PREFERRED_SIZE).addPreferredGap(LayoutStyle.ComponentPlacement
                        .UNRELATED).addGroup(layout.createParallelGroup(GroupLayout
                        .Alignment.BASELINE).addComponent(clearButton, GroupLayout
                        .PREFERRED_SIZE, 40, GroupLayout.PREFERRED_SIZE).addComponent
                        (cosButton, GroupLayout.PREFERRED_SIZE, 40, GroupLayout
                                .PREFERRED_SIZE).addComponent(sinButton, GroupLayout
                        .PREFERRED_SIZE, 40, GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addGroup
                        (layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                .addComponent(numbers[7], GroupLayout.PREFERRED_SIZE,
                                        40, GroupLayout.PREFERRED_SIZE).addComponent
                                        (numbers[8], GroupLayout.PREFERRED_SIZE, 40,
                                                GroupLayout.PREFERRED_SIZE)
                                .addComponent(numbers[9], GroupLayout.PREFERRED_SIZE,
                                        40, GroupLayout.PREFERRED_SIZE).addComponent
                                        (divisionButton, GroupLayout.PREFERRED_SIZE,
                                                40, GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addGroup
                        (layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                .addComponent(numbers[4], GroupLayout.PREFERRED_SIZE,
                                        40, GroupLayout.PREFERRED_SIZE).addComponent
                                        (numbers[5], GroupLayout.PREFERRED_SIZE, 40,
                                                GroupLayout.PREFERRED_SIZE)
                                .addComponent(numbers[6], GroupLayout.PREFERRED_SIZE,
                                        40, GroupLayout.PREFERRED_SIZE).addComponent
                                        (multiplyButton, GroupLayout.PREFERRED_SIZE,
                                                40, GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addGroup
                        (layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                .addComponent(numbers[1], GroupLayout.PREFERRED_SIZE,
                                        40, GroupLayout.PREFERRED_SIZE).addComponent
                                        (numbers[2], GroupLayout.PREFERRED_SIZE, 40,
                                                GroupLayout.PREFERRED_SIZE)
                                .addComponent(numbers[3], GroupLayout.PREFERRED_SIZE,
                                        40, GroupLayout.PREFERRED_SIZE).addComponent
                                        (minusButton, GroupLayout.PREFERRED_SIZE, 40,
                                                GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addGroup
                        (layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                .addComponent(numbers[0], GroupLayout.PREFERRED_SIZE,
                                        40, GroupLayout.PREFERRED_SIZE).addComponent
                                        (dotButton, GroupLayout.PREFERRED_SIZE, 40,
                                                GroupLayout.PREFERRED_SIZE)
                                .addComponent(equalButton, GroupLayout.PREFERRED_SIZE,
                                        40, GroupLayout.PREFERRED_SIZE).addComponent
                                        (plusButton, GroupLayout.PREFERRED_SIZE, 40,
                                                GroupLayout.PREFERRED_SIZE))
                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));
        pack();
    }
}
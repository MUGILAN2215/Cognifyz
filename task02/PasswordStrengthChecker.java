package task02;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PasswordStrengthChecker {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(PasswordCheckerFrame::new);
    }
}

class PasswordCheckerFrame extends JFrame {
    private final JPasswordField passwordField;
    private final JLabel resultLabel;
    private final JCheckBox showPasswordCheckBox;

    public PasswordCheckerFrame() {
        setTitle("Password Strength Checker");
        setSize(400, 250);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel instructionLabel = new JLabel("Enter a password:");
        passwordField = new JPasswordField(20);
        showPasswordCheckBox = new JCheckBox("Show Password");
        JButton checkButton = new JButton("Check Strength");
        resultLabel = new JLabel("", SwingConstants.CENTER);

        showPasswordCheckBox.addActionListener(e -> 
            passwordField.setEchoChar(showPasswordCheckBox.isSelected() ? (char) 0 : '\u2022')
        );

        checkButton.addActionListener(e -> {
            String password = new String(passwordField.getPassword());
            String strength = checkPasswordStrength(password);
            resultLabel.setText("Password Strength: " + strength);
        });

        gbc.gridx = 0;
        gbc.gridy = 0;
        add(instructionLabel, gbc);

        gbc.gridx = 1;
        add(passwordField, gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        add(showPasswordCheckBox, gbc);

        gbc.gridx = 1;
        gbc.gridy = 2;
        add(checkButton, gbc);

        gbc.gridx = 1;
        gbc.gridy = 3;
        add(resultLabel, gbc);

        setVisible(true);
    }

    private String checkPasswordStrength(String password) {
        int strengthPoints = 0;

        if (password.length() >= 8) strengthPoints++;
        if (password.matches(".*[A-Z].*")) strengthPoints++;
        if (password.matches(".*[a-z].*")) strengthPoints++;
        if (password.matches(".*\\d.*")) strengthPoints++;
        if (password.matches(".*[!@#$%^&*(),.?\":{}|<>].*")) strengthPoints++;

        switch (strengthPoints) {
            case 5: return "Very Strong";
            case 4: return "Strong";
            case 3: return "Moderate";
            case 2: return "Weak";
            default: return "Very Weak";
        }
    }
}

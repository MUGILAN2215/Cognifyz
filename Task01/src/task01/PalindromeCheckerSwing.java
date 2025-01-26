package task01;


import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PalindromeCheckerSwing {
    public static boolean isPalindrome(String s) {
        // Remove non-alphanumeric characters and convert to lowercase
        String cleaned = s.replaceAll("[^a-zA-Z0-9]", "").toLowerCase();
        
        // Check if the cleaned string is equal to its reverse
        return cleaned.equals(new StringBuilder(cleaned).reverse().toString());
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Palindrome Checker");
        frame.setSize(400, 200);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        frame.add(panel);
        placeComponents(panel);
        frame.setResizable(false);

        frame.setVisible(true);
    }

    private static void placeComponents(JPanel panel) {
        panel.setLayout(null);

        JLabel label = new JLabel("Enter a word or phrase:");
        label.setBounds(10, 20, 200, 25);
        panel.add(label);

        JTextField textField = new JTextField(20);
        textField.setBounds(180, 20, 165, 25);
        panel.add(textField);

        JButton checkButton = new JButton("Check");
        checkButton.setBounds(150, 60, 100, 25);
        panel.add(checkButton);

        JLabel resultLabel = new JLabel("");
        resultLabel.setBounds(10, 100, 300, 25);
        panel.add(resultLabel);

        checkButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String text = textField.getText();
                if (isPalindrome(text)) {
                    resultLabel.setText("It is a palindrome!");
                } else {
                    resultLabel.setText("It is not a palindrome.");
                }
            }
        });
    }
}


package task01;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.security.SecureRandom;
import java.net.URL;

public class PasswordGenerator extends JFrame {
    private JTextField lengthField;
    private JCheckBox numbersCheckBox;
    private JCheckBox lowercaseCheckBox;
    private JCheckBox uppercaseCheckBox;
    private JCheckBox specialCheckBox;
    private JTextArea passwordArea;

    private Image backgroundImage;

    private static final String LOWERCASE = "abcdefghijklmnopqrstuvwxyz";
    private static final String UPPERCASE = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final String NUMBERS = "0123456789";
    private static final String SPECIAL = "!@#$%^&*()-_=+[]{}|;:'\",.<>?/";

    public PasswordGenerator() {
        // Load the background image
        URL imageUrl = getClass().getResource("lock.jpg"); // Assuming the image is in the project folder
        if (imageUrl != null) {
            backgroundImage = new ImageIcon(imageUrl).getImage();
        } else {
            backgroundImage = new ImageIcon("background.jpg").getImage(); // Local path if not in resources
        }

        setTitle("Random Password Generator");
        setLayout(new BorderLayout());  // Use BorderLayout for better control
        setSize(400, 300);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Create a panel to hold the components and override its paintComponent method
        JPanel panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                if (backgroundImage != null) {
                    g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
                }
            }
        };
        panel.setLayout(new FlowLayout());

        // Create components
        JLabel lengthLabel = new JLabel("Enter password length:");
        lengthLabel.setForeground(Color.WHITE);  // Set label text color to white

        lengthField = new JTextField(10);
        lengthField.setForeground(Color.BLACK);  // Set the text color to black
        lengthField.setBackground(Color.WHITE);
        
        // Set background color of text field

        numbersCheckBox = new JCheckBox("Include Numbers");
        lowercaseCheckBox = new JCheckBox("Include Lowercase Letters");
        uppercaseCheckBox = new JCheckBox("Include Uppercase Letters");
        specialCheckBox = new JCheckBox("Include Special Characters");

        JButton generateButton = new JButton("Generate Password");
        
        // Set properties for password area
        passwordArea = new JTextArea(1, 9);
        passwordArea.setEditable(false);
        passwordArea.setBackground(Color.WHITE); // Set the background color of the text area
        passwordArea.setForeground(Color.WHITE); // Set the text color to black

        // Add action listener for the button
        generateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int length = Integer.parseInt(lengthField.getText());
                boolean includeNumbers = numbersCheckBox.isSelected();
                boolean includeLowercase = lowercaseCheckBox.isSelected();
                boolean includeUppercase = uppercaseCheckBox.isSelected();
                boolean includeSpecial = specialCheckBox.isSelected();

                String password = generatePassword(length, includeNumbers, includeLowercase, includeUppercase, includeSpecial);
                passwordArea.setText(password);
            }
        });

        // Add components to the panel
        panel.add(lengthLabel);
        panel.add(lengthField);
        panel.add(numbersCheckBox);
        panel.add(lowercaseCheckBox);
        panel.add(uppercaseCheckBox);
        panel.add(specialCheckBox);
        panel.add(generateButton);
        panel.add(new JScrollPane(passwordArea));

        // Add panel to the frame
        add(panel, BorderLayout.CENTER);

        setVisible(true);
    }

    private String generatePassword(int length, boolean includeNumbers, boolean includeLowercase, boolean includeUppercase, boolean includeSpecial) {
        StringBuilder allowedChars = new StringBuilder();

        if (includeLowercase) allowedChars.append(LOWERCASE);
        if (includeUppercase) allowedChars.append(UPPERCASE);
        if (includeNumbers) allowedChars.append(NUMBERS);
        if (includeSpecial) allowedChars.append(SPECIAL);

        if (allowedChars.length() == 0) {
            return "Please select at least one character type.";
        }

        SecureRandom random = new SecureRandom();
        StringBuilder password = new StringBuilder();

        for (int i = 0; i < length; i++) {
            int index = random.nextInt(allowedChars.length());
            password.append(allowedChars.charAt(index));
        }

        return password.toString();
    }

    public static void main(String[] args) {
        new PasswordGenerator();
    }
}

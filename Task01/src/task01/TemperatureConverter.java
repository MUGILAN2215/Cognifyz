package task01;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TemperatureConverter extends JFrame {
    private JTextField tempInput;
    private JLabel resultLabel;
    private JRadioButton celsiusToFahrenheit;
    private JRadioButton fahrenheitToCelsius;
    private JLabel backgroundLabel;
    
    public TemperatureConverter() {
        setTitle("Temperature Converter");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);
        
        // Load background image
        ImageIcon background = new ImageIcon("convetor.jpg"); // Change to your image path
        backgroundLabel = new JLabel(background);
        backgroundLabel.setBounds(0, 0, getWidth(), getHeight());
        
        JLabel label = new JLabel("Enter Temperature:");
        label.setBounds(50, 30, 150, 20);
        tempInput = new JTextField(10);
        tempInput.setBounds(200, 30, 100, 20);
        
        celsiusToFahrenheit = new JRadioButton("Celsius to Fahrenheit");
        fahrenheitToCelsius = new JRadioButton("Fahrenheit to Celsius");
        celsiusToFahrenheit.setBounds(50, 60, 200, 20);
        fahrenheitToCelsius.setBounds(50, 90, 200, 20);
        
        ButtonGroup group = new ButtonGroup();
        group.add(celsiusToFahrenheit);
        group.add(fahrenheitToCelsius);
        
        JButton convertButton = new JButton("Convert");
        convertButton.setBounds(50, 120, 100, 30);
        resultLabel = new JLabel("Result: ");
        resultLabel.setBounds(50, 160, 300, 20);
        
        convertButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                convertTemperature();
            }
        });
        
        add(label);
        add(tempInput);
        add(celsiusToFahrenheit);
        add(fahrenheitToCelsius);
        add(convertButton);
        add(resultLabel);
        
        add(backgroundLabel);
        
        setVisible(true);
    }
    
    private void convertTemperature() {
        try {
            double temp = Double.parseDouble(tempInput.getText());
            double convertedTemp;
            if (celsiusToFahrenheit.isSelected()) {
                convertedTemp = (temp * 9/5) + 32;
                resultLabel.setText("Result: " + convertedTemp + " °F");
            } else if (fahrenheitToCelsius.isSelected()) {
                convertedTemp = (temp - 32) * 5/9;
                resultLabel.setText("Result: " + convertedTemp + " °C");
            } else {
                resultLabel.setText("Please select a conversion type.");
            }
        } catch (NumberFormatException e) {
            resultLabel.setText("Invalid input. Enter a valid number.");
        }
    }
    
    public static void main(String[] args) {
        new TemperatureConverter();
    }
}

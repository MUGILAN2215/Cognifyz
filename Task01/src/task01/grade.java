package task01;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.util.*;

public class grade extends JFrame {
    private Map<String, Student> studentDatabase = new HashMap<>();
    private JPanel mainPanel;
    private Image backgroundImage;

    static class Student {
        String name;
        String regNumber;
        ArrayList<Double> semesterGPAs;
        double cgpa;

        Student(String name, String regNumber) {
            this.name = name;
            this.regNumber = regNumber;
            this.semesterGPAs = new ArrayList<>();
            this.cgpa = 0.0;
        }

        public void updateCGPA() {
            if (!semesterGPAs.isEmpty()) {
                double total = 0;
                for (double gpa : semesterGPAs) {
                    total += gpa;
                }
                this.cgpa = total / semesterGPAs.size();
            }
        }
    }

    static class Subject {
        String name;
        int credits;
        String grade;
        double gradePoint;

        Subject(String name, int credits, String grade) {
            this.name = name;
            this.credits = credits;
            this.grade = grade;
            this.gradePoint = convertGradeToPoints(grade);
        }

        private double convertGradeToPoints(String grade) {
            switch (grade.toUpperCase()) {
                case "O":  return 10.0;
                case "A+": return 9.0;
                case "A":  return 8.0;
                case "B+": return 7.0;
                case "B":  return 6.0;
                case "U":  return 0.0;
                default:   return 0.0;
            }
        }

        public boolean isPassing() {
            String upperGrade = grade.toUpperCase();
            return upperGrade.equals("O") || upperGrade.equals("A+") || 
                   upperGrade.equals("A") || upperGrade.equals("B+") || 
                   upperGrade.equals("B");
        }
    }

    // Custom JPanel that supports background image
    class BackgroundPanel extends JPanel {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            if (backgroundImage != null) {
                g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
            }
        }
    }

    public grade() {
        setTitle("GPA Calculator");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);

        // Create main panel with background support
        mainPanel = new BackgroundPanel();
        mainPanel.setLayout(new BorderLayout());

        // Create menu bar
        JMenuBar menuBar = createMenuBar();
        setJMenuBar(menuBar);

        // Create button panel
        JPanel buttonPanel = createButtonPanel();
        mainPanel.add(buttonPanel, BorderLayout.CENTER);

        add(mainPanel);
    }

    private JMenuBar createMenuBar() {
        JMenuBar menuBar = new JMenuBar();
        JMenu fileMenu = new JMenu("File");
        JMenuItem changeBackground = new JMenuItem("Change Background");
        
        changeBackground.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter(
                "Image files", "jpg", "jpeg", "png", "gif"));
            
            int result = fileChooser.showOpenDialog(this);
            if (result == JFileChooser.APPROVE_OPTION) {
                try {
                    File selectedFile = fileChooser.getSelectedFile();
                    backgroundImage = new ImageIcon(selectedFile.getPath()).getImage();
                    mainPanel.repaint();
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this, 
                        "Error loading background image", 
                        "Error", 
                        JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        fileMenu.add(changeBackground);
        menuBar.add(fileMenu);
        return menuBar;
    }

    private JPanel createButtonPanel() {
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridBagLayout());
        buttonPanel.setOpaque(false);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 10, 10, 10);

        JButton[] buttons = {
            createStyledButton("Add New Student"),
            createStyledButton("Calculate Semester GPA"),
            createStyledButton("Quick CGPA Calculator"),
            createStyledButton("View Student Record"),
            createStyledButton("List All Students")
        };

        for (JButton button : buttons) {
            buttonPanel.add(button, gbc);
        }

        // Add action listeners
        buttons[0].addActionListener(e -> addNewStudent());
        buttons[1].addActionListener(e -> calculateSemesterGPA());
        buttons[2].addActionListener(e -> quickCGPACalculator());
        buttons[3].addActionListener(e -> viewStudentRecord());
        buttons[4].addActionListener(e -> listAllStudents());

        return buttonPanel;
    }

    private JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.setPreferredSize(new Dimension(200, 40));
        button.setFont(new Font("Arial", Font.BOLD, 14));
        button.setBackground(new Color(70, 130, 180));
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        
        button.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                button.setBackground(new Color(100, 149, 237));
            }
            public void mouseExited(MouseEvent e) {
                button.setBackground(new Color(70, 130, 180));
            }
        });
        
        return button;
    }

    private void addNewStudent() {
        JDialog dialog = new JDialog(this, "Add New Student", true);
        dialog.setLayout(new GridLayout(3, 2, 10, 10));
        dialog.setSize(300, 150);
        dialog.setLocationRelativeTo(this);

        JTextField nameField = new JTextField();
        JTextField regField = new JTextField();

        dialog.add(new JLabel("Name:"));
        dialog.add(nameField);
        dialog.add(new JLabel("Registration Number:"));
        dialog.add(regField);

        JButton submitButton = new JButton("Add Student");
        submitButton.addActionListener(e -> {
            String name = nameField.getText().trim();
            String regNumber = regField.getText().trim();
            
            if (name.isEmpty() || regNumber.isEmpty()) {
                JOptionPane.showMessageDialog(dialog, 
                    "Please fill all fields", 
                    "Error", 
                    JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (studentDatabase.containsKey(regNumber)) {
                JOptionPane.showMessageDialog(dialog, 
                    "Student with this registration number already exists!", 
                    "Error", 
                    JOptionPane.ERROR_MESSAGE);
                return;
            }

            studentDatabase.put(regNumber, new Student(name, regNumber));
            JOptionPane.showMessageDialog(dialog, 
                "Student added successfully!", 
                "Success", 
                JOptionPane.INFORMATION_MESSAGE);
            dialog.dispose();
        });

        dialog.add(submitButton);
        dialog.setVisible(true);
    }

    private void calculateSemesterGPA() {
        String regNumber = JOptionPane.showInputDialog(this, 
            "Enter student registration number:");
        
        if (regNumber == null || regNumber.trim().isEmpty()) return;

        Student student = studentDatabase.get(regNumber);
        if (student == null) {
            JOptionPane.showMessageDialog(this, 
                "Student not found!", 
                "Error", 
                JOptionPane.ERROR_MESSAGE);
            return;
        }

        String numSubjectsStr = JOptionPane.showInputDialog(this, 
            "Enter number of subjects:");
        if (numSubjectsStr == null) return;
        
        try {
            int numSubjects = Integer.parseInt(numSubjectsStr);
            ArrayList<Subject> subjects = new ArrayList<>();

            for (int i = 0; i < numSubjects; i++) {
                JPanel panel = new JPanel(new GridLayout(3, 2));
                panel.add(new JLabel("Subject Name:"));
                JTextField nameField = new JTextField();
                panel.add(nameField);
                
                panel.add(new JLabel("Credits:"));
                JTextField creditsField = new JTextField();
                panel.add(creditsField);
                
                panel.add(new JLabel("Grade (O, A+, A, B+, B, U):"));
                JTextField gradeField = new JTextField();
                panel.add(gradeField);

                int result = JOptionPane.showConfirmDialog(this, panel, 
                    "Subject " + (i + 1), 
                    JOptionPane.OK_CANCEL_OPTION);
                
                if (result != JOptionPane.OK_OPTION) return;

                try {
                    String name = nameField.getText().trim();
                    int credits = Integer.parseInt(creditsField.getText().trim());
                    String grade = gradeField.getText().trim();

                    Subject subject = new Subject(name, credits, grade);
                    subjects.add(subject);

                    if (!subject.isPassing()) {
                        JOptionPane.showMessageDialog(this, 
                            name + " has a failing grade (U)!", 
                            "Warning", 
                            JOptionPane.WARNING_MESSAGE);
                    }
                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(this, 
                        "Invalid credits value!", 
                        "Error", 
                        JOptionPane.ERROR_MESSAGE);
                    return;
                }
            }

            // Calculate GPA
            boolean hasFailingGrade = subjects.stream().anyMatch(s -> !s.isPassing());
            if (hasFailingGrade) {
                JOptionPane.showMessageDialog(this, 
                    "Semester GPA cannot be calculated due to failing grade(s).", 
                    "Error", 
                    JOptionPane.ERROR_MESSAGE);
                return;
            }

            double totalWeightedPoints = 0;
            int totalCredits = 0;

            for (Subject subject : subjects) {
                totalWeightedPoints += subject.gradePoint * subject.credits;
                totalCredits += subject.credits;
            }

            double semesterGPA = totalWeightedPoints / totalCredits;
            student.semesterGPAs.add(semesterGPA);
            student.updateCGPA();

            JOptionPane.showMessageDialog(this, 
                String.format("Semester GPA: %.2f\nUpdated CGPA: %.2f", 
                    semesterGPA, student.cgpa), 
                "Results", 
                JOptionPane.INFORMATION_MESSAGE);

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, 
                "Invalid number of subjects!", 
                "Error", 
                JOptionPane.ERROR_MESSAGE);
        }
    }

    private void quickCGPACalculator() {
        String regNumber = JOptionPane.showInputDialog(this, 
            "Enter student registration number:");
        
        if (regNumber == null || regNumber.trim().isEmpty()) return;

        Student student = studentDatabase.get(regNumber);
        if (student == null) {
            JOptionPane.showMessageDialog(this, 
                "Student not found!", 
                "Error", 
                JOptionPane.ERROR_MESSAGE);
            return;
        }

        String numSemestersStr = JOptionPane.showInputDialog(this, 
            "Enter number of semesters:");
        if (numSemestersStr == null) return;

        try {
            int numSemesters = Integer.parseInt(numSemestersStr);
            student.semesterGPAs.clear();

            for (int i = 0; i < numSemesters; i++) {
                String gpaStr = JOptionPane.showInputDialog(this, 
                    "Enter GPA for Semester " + (i + 1) + " (0-10):");
                if (gpaStr == null) return;

                try {
                    double gpa = Double.parseDouble(gpaStr);
                    if (gpa >= 0 && gpa <= 10) {
                        student.semesterGPAs.add(gpa);
                    } else {
                        JOptionPane.showMessageDialog(this, 
                            "Invalid GPA! Please enter a value between 0 and 10.", 
                            "Error", 
                            JOptionPane.ERROR_MESSAGE);
                        i--; // ask for the same semester again
                    }
                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(this, 
                        "Invalid input! Please enter a valid GPA.", 
                        "Error", 
                        JOptionPane.ERROR_MESSAGE);
                    i--; // ask for the same semester again
                }
            }

            student.updateCGPA();
            JOptionPane.showMessageDialog(this, 
                "Updated CGPA: " + student.cgpa, 
                "Results", 
                JOptionPane.INFORMATION_MESSAGE);

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, 
                "Invalid number of semesters!", 
                "Error", 
                JOptionPane.ERROR_MESSAGE);
        }
    }

    private void viewStudentRecord() {
        String regNumber = JOptionPane.showInputDialog(this, 
            "Enter student registration number:");

        if (regNumber == null || regNumber.trim().isEmpty()) return;

        Student student = studentDatabase.get(regNumber);
        if (student == null) {
            JOptionPane.showMessageDialog(this, 
                "Student not found!", 
                "Error", 
                JOptionPane.ERROR_MESSAGE);
            return;
        }

        StringBuilder studentRecord = new StringBuilder();
        studentRecord.append("Name: ").append(student.name).append("\n");
        studentRecord.append("Reg Number: ").append(student.regNumber).append("\n");
        studentRecord.append("CGPA: ").append(String.format("%.2f", student.cgpa)).append("\n");
        studentRecord.append("Semesters: ").append(student.semesterGPAs.size()).append("\n");
        studentRecord.append("GPA History: \n");

        for (int i = 0; i < student.semesterGPAs.size(); i++) {
            studentRecord.append("Semester ").append(i + 1).append(": ")
                          .append(String.format("%.2f", student.semesterGPAs.get(i))).append("\n");
        }

        JOptionPane.showMessageDialog(this, 
            studentRecord.toString(), 
            "Student Record", 
            JOptionPane.INFORMATION_MESSAGE);
    }

    private void listAllStudents() {
        String[] columnNames = {"Reg Number", "Name", "Semesters", "CGPA"};
        DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0);

        for (Student student : studentDatabase.values()) {
            tableModel.addRow(new Object[] {
                student.regNumber, 
                student.name, 
                student.semesterGPAs.size(),
                String.format("%.2f", student.cgpa)
            });
        }

        JTable studentTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(studentTable);
        JOptionPane.showMessageDialog(this, scrollPane, "All Students", JOptionPane.INFORMATION_MESSAGE);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            grade frame = new grade();
            frame.setVisible(true);
        });
    }
}

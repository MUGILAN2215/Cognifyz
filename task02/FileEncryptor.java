package task02;
import java.io.*;
import java.util.Scanner;

public class FileEncryptor {
    private static final int SHIFT = 3; // Simple shift for Caesar cipher

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Choose an option: \n1. Encrypt a file \n2. Decrypt a file");
        int choice = scanner.nextInt();
        scanner.nextLine(); // Consume the newline

        System.out.print("Enter the file path: ");
        String inputFilePath = scanner.nextLine();
        
        System.out.print("Enter the output file path: ");
        String outputFilePath = scanner.nextLine();

        if (choice == 1) {
            processFile(inputFilePath, outputFilePath, true);
            System.out.println("File encrypted successfully!");
        } else if (choice == 2) {
            processFile(inputFilePath, outputFilePath, false);
            System.out.println("File decrypted successfully!");
        } else {
            System.out.println("Invalid choice!");
        }
        scanner.close();
    }

    private static void processFile(String inputFilePath, String outputFilePath, boolean encrypt) {
        try (BufferedReader reader = new BufferedReader(new FileReader(inputFilePath));
             BufferedWriter writer = new BufferedWriter(new FileWriter(outputFilePath))) {

            String line;
            while ((line = reader.readLine()) != null) {
                writer.write(encrypt ? encryptText(line) : decryptText(line));
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error processing file: " + e.getMessage());
        }
    }

    private static String encryptText(String text) {
        StringBuilder encrypted = new StringBuilder();
        for (char ch : text.toCharArray()) {
            encrypted.append((char) (ch + SHIFT));
        }
        return encrypted.toString();
    }

    private static String decryptText(String text) {
        StringBuilder decrypted = new StringBuilder();
        for (char ch : text.toCharArray()) {
            decrypted.append((char) (ch - SHIFT));
        }
        return decrypted.toString();
    }
}

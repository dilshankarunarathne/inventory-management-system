package GUI;

import java.io.*;
import java.util.*;

public class UserManager {
    private final String usersFilePath = "users.txt";

    public boolean registerUser(String username, String password) {
        if (isUserExists(username)) {
            return false; // User already exists
        }
        try (FileWriter fw = new FileWriter(usersFilePath, true);
             BufferedWriter bw = new BufferedWriter(fw);
             PrintWriter out = new PrintWriter(bw)) {
            out.println(username + "," + password);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean loginUser(String username, String password) {
        try (Scanner scanner = new Scanner(new File(usersFilePath))) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                if (!line.trim().isEmpty()) { // Ensure the line is not empty
                    String[] userData = line.split(",");
                    if (userData.length > 1 && userData[0].equals(username) && userData[1].equals(password)) {
                        return true; // Login successful
                    }
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return false; // Login failed
    }

    private boolean isUserExists(String username) {
        try (Scanner scanner = new Scanner(new File(usersFilePath))) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                if (!line.trim().isEmpty()) { // Check if the line is not empty
                    String[] userData = line.split(",");
                    if (userData.length > 1 && userData[0].equals(username)) { // Check if userData array has elements before accessing
                        return true; // User exists
                    }
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return false;
    }
}

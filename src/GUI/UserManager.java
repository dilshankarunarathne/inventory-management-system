package GUI;

import core.User;

import java.io.*;
import java.util.*;

public class UserManager {
    private final String usersFilePath = "users.txt";

    public User registerUser(String username, String password) {
        if (isUserExists(username)) {
            return null; // User already exists
        }
        try (FileWriter fw = new FileWriter(usersFilePath, true);
             BufferedWriter bw = new BufferedWriter(fw);
             PrintWriter out = new PrintWriter(bw)) {
            out.println(username + "," + password);
            return new User(username, password, false); // Return new User object
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public User loginUser(String username, String password) {
        try (Scanner scanner = new Scanner(new File(usersFilePath))) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                if (!line.trim().isEmpty()) {
                    String[] userData = line.split(",");
                    if (userData[0].equals(username) && userData[1].equals(password)) {
                        boolean firstOrderDone = userData.length > 2 && Boolean.parseBoolean(userData[2]);
                        return new User(username, password, firstOrderDone); // Return User object with firstOrderDone status
                    }
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return null; // Login failed
    }

    private boolean isUserExists(String username) {
        try (Scanner scanner = new Scanner(new File(usersFilePath))) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                if (!line.trim().isEmpty()) {
                    String[] userData = line.split(",");
                    if (userData.length > 1 && userData[0].equals(username)) {
                        return true;
                    }
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return false;
    }

    public void updateUser(User user) {
        // Read all users into a list, update the relevant user, then write back to the file
        File tempFile = new File(usersFilePath + ".tmp");
        try (BufferedReader reader = new BufferedReader(new FileReader(usersFilePath));
             BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile))) {
            String currentLine;
            while ((currentLine = reader.readLine()) != null) {
                String[] userData = currentLine.split(",");
                if (userData[0].equals(user.getUsername())) {
                    // Write updated user data
                    writer.write(user.getUsername() + "," + user.getPassword() + "," + user.isFirstOrderDone());
                } else {
                    // Write unchanged user data
                    writer.write(currentLine);
                }
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        // Replace the old file with the updated file
        new File(usersFilePath).delete();
        tempFile.renameTo(new File(usersFilePath));
    }
}

package user;

import utils.FileHandler;

import javax.crypto.SecretKey;
import java.util.HashMap;
import java.util.Map;


public class AuthService {
    private SecretKey secretKey;

    public AuthService(SecretKey key) {
        this.secretKey = key;
    }

    public void registerUser(String username, String password) {
        // Check if the user already exists in the file
        if (FileHandler.loadUserFromFile(username) == null) {
            // Creating a new user and save directly to file
            User newUser = new User(username, password, secretKey);
            FileHandler.saveUserToFile(newUser); // Save user to persistent storage
            FileHandler.saveUserResourcesToFile(newUser); // Save empty resources
            System.out.println("Registration successful");
        } else {
            System.out.println("Username already exists.");
        }
    }

    public User loginUser(String username, String password) {
        // Loading user data from file each time
        User user = FileHandler.loadUserFromFile(username);

        if (user != null && user.verifyPassword(password, secretKey)) {
            // Loading the user's resources from file after successful login
            user.getResourceManager().setResources(FileHandler.loadUserResourcesFromFile(user));
            return user;
        } else {
            System.out.println("Invalid username or password.");
            return null;
        }
    }
}

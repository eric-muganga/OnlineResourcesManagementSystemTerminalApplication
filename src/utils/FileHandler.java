package utils;

import resource_management.Resource;
import user.User;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class FileHandler {

    // Ensure the directories exist before saving
    private static void ensureDirectoryExists(String directory) {
        Path path = Paths.get(directory);
        if (!Files.exists(path)) {
            try {
                Files.createDirectories(path);
            } catch (IOException e) {
                System.out.println("Error creating directory: " + directory);
            }
        }
    }

    // saving a single user to a single file
    public static void saveUserToFile(User user) {
        String directory = "data/users/";
        ensureDirectoryExists(directory);
        String filename = directory + user.getUsername() + ".ser";
        try(FileOutputStream fos = new FileOutputStream(filename)) {
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(user);
        }catch (IOException e) {
            System.out.println("Error saving user: " + e.getMessage());
        }
    }

    // Loading a single user from their file
    public static User loadUserFromFile(String filename) {
        String fileName ="data/users/" + filename + ".ser";
        try(FileInputStream fos = new FileInputStream(fileName)){
            ObjectInputStream ois = new ObjectInputStream(fos);
            return (User) ois.readObject();
        } catch (FileNotFoundException e) {
            // The file does not exist, which means the user has not been registered yet
            return null; // Return null to indicate that the user doesn't exist
        }catch (IOException | ClassNotFoundException e) {
            System.out.println("Error loading user: " + e.getMessage());
            return null;
        }
    }

    // Saving the resources of a specific user to a separate fil
    public static void saveUserResourcesToFile(User user) {
        String directory = "data/resources/";
        ensureDirectoryExists(directory);  // Ensure the 'data/resources/' directory exists
        String fileName = directory + "resources_" + user.getUsername() + ".ser";
        try(FileOutputStream fos  = new FileOutputStream(fileName)) {
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(user.getResourceManager().getResources());
        } catch (IOException e) {
            System.out.println("Error saving user resources: " + e.getMessage());
        }
    }

    // Loading the resources for a specific user from their resource file
    @SuppressWarnings("unchecked")
    public static List<Resource> loadUserResourcesFromFile(User user) {
        String filename = "data/resources/resources_" + user.getUsername() + ".ser";
        try(FileInputStream fis = new FileInputStream(filename)){
            ObjectInputStream ois = new ObjectInputStream(fis);
            return (List<Resource>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error loading user resources: " + e.getMessage());
            return null;
        }
    }


}

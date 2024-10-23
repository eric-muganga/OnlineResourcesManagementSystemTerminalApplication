import resource_management.GameAccount;
import resource_management.Resource;
import resource_management.ResourceManager;
import resource_management.Subscription;
import user.AuthService;
import user.User;
import utils.FileHandler;
import utils.KeyHandler;

import javax.crypto.SecretKey;
import java.io.File;
import java.util.Scanner;


public class Main {

    private static AuthService authService;
    private static SecretKey secretKey;
    public static void main(String[] args) {

        try {
            File keyFile = new File("data/secretKey.ser");
            if (keyFile.exists()) {
                secretKey = KeyHandler.loadSecretKey("data/secretKey.ser");
                System.out.println("Secret key loaded from file");
            }else {
                secretKey = KeyHandler.generateSecretKey();
                KeyHandler.saveSecretKey(secretKey, "data/secretKey.ser");
                System.out.println("Secret key saved");
            }

            // Initializing AuthService with the generated SecretKey
            authService = new AuthService(secretKey);

            // Displaying the main menu
            displayMenu();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }


    private static void displayMenu(){
        Scanner scanner = new Scanner(System.in);
        while (true){
            System.out.println("\n--- Main Menu ---");
            System.out.println("1. Login");
            System.out.println("2. Register");
            System.out.println("3. Exit");
            System.out.println("Select an option: ");

            int choice;
            try {
                choice = Integer.parseInt(scanner.nextLine());
                switch (choice){
                    case 1: loginMenu(scanner); break;
                    case 2: registerMenu(scanner); break;
                    case 3: System.out.println("Exiting the application...");
                        System.exit(0);
                    default:
                        System.out.println("Invalid option. Please try again.");
                }
                break;
            }catch (Exception e){
                System.out.println("Invalid option. Please enter a number between 1 and 3.");
            }
        }
    }


    // Login functionality
    private static void loginMenu(Scanner scanner) {
        System.out.println("\n--- Login ---");
        System.out.println("Enter Username: ");
        String username = scanner.nextLine();
        System.out.println("Enter Password: ");
        String password = scanner.nextLine();

        // Authenticating the user using AuthService
        User loggedInUser = authService.loginUser(username, password);

        if (loggedInUser != null) {
            System.out.println("Login successful! Welcome, " + username + ".");
            userDashboard(loggedInUser, scanner);
        } else {
            System.out.println("Invalid username or password.");
        }
    }

    // Registration functionality
    private static void registerMenu(Scanner scanner) {
        System.out.println("\n--- Register ---");
        System.out.println("Enter Username: ");
        String username = scanner.nextLine();
        System.out.println("Enter Password: ");
        String password = scanner.nextLine();

        // Registering the user using AuthService
        authService.registerUser(username, password);

        displayMenu();
    }

    private static void userDashboard(User user, Scanner scanner) {
        ResourceManager resourceManager = user.getResourceManager();
        while (true){
            System.out.println("\n--- User Dashboard ---");
            System.out.println("1. View Resources");
            System.out.println("2. Add Resource");
            System.out.println("3. Find Resource");
            System.out.println("4. Remove Resource");
            System.out.println("5. Logout");

            System.out.println("Select an option: ");
            int choice;
            try {
                choice = Integer.parseInt(scanner.nextLine());

                switch (choice){
                    case 1:
                        System.out.println("Viewing resources for " + user.getUsername());
                        resourceManager.getResources().forEach(Resource::displayInfo);
                        break;
                    case 2:
                        System.out.println("Adding a resource for " + user.getUsername());
                        addResourceMenu(scanner, resourceManager,user);
                        break;
                    case 3:
                        System.out.println("Finding a resource for " + user.getUsername());

                        break;
                    case 4:
                        System.out.println("Removing a resource for " + user.getUsername());
                        break;
                    case 5:
                        System.out.println("Logging out....");
                        return;
                    default:
                        System.out.println("Invalid option. Please try again.");
                }
            }catch (NumberFormatException e){
                System.out.println("Invalid option. Please enter a number.");
            }
        }
    }

    private static void addResourceMenu(Scanner scanner, ResourceManager resourceManager, User user) {
        System.out.println("\n--- Add Resource Menu ---");
        System.out.println("1. Add a Game account");
        System.out.println("2. Add a Subscription");
        System.out.println("\n");
        System.out.println("Select an option: ");
        int choice;
        try {
            choice = Integer.parseInt(scanner.nextLine());
            switch (choice) {
                case 1:
                    System.out.println("Adding a game account");
                    addGameAccount(scanner, resourceManager, user);  // Pass User object
                    break;
                case 2:
                    System.out.println("Adding a subscription");
                    addSubscription(scanner, resourceManager, user);  // Pass User object
                    break;
                default:
                    System.out.println("Invalid option. Please try again.");
                    break;
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid option. Please enter a number.");
        }
    }



    private static void addGameAccount(Scanner scanner, ResourceManager resourceManager, User user) {
        System.out.println("\n--- Add Game Account ---");

        // Prompt user for game account details
        System.out.println("Enter Game Name: ");
        String gameName = scanner.nextLine();

        System.out.println("Enter Username: ");
        String username = scanner.nextLine();

        System.out.println("Enter Password: ");
        String password = scanner.nextLine();

        System.out.println("Enter Platform (e.g., PC, Xbox, PlayStation): ");
        String platform = scanner.nextLine();

        // Creating a new GameAccount object
        GameAccount gameAccount = new GameAccount(gameName, username, password, platform);

        // Add the resource to the user's resource list
        resourceManager.addResource(gameAccount);

        // Save the user's resources to persistent storage
        FileHandler.saveUserResourcesToFile(user);
        System.out.println("Game account added successfully!");
    }


    private static void addSubscription(Scanner scanner, ResourceManager resourceManager, User user) {
        System.out.println("\n--- Add Subscription ---");

        // Prompt user for subscription details
        System.out.println("Enter Subscription Name (e.g., Netflix, Spotify): ");
        String subscriptionName = scanner.nextLine();

        System.out.println("Enter Username: ");
        String username = scanner.nextLine();

        System.out.println("Enter Password: ");
        String password = scanner.nextLine();

        System.out.println("Enter Subscription Type (e.g., Streaming, Magazine): ");
        String subscriptionType = scanner.nextLine();

        // Creating a new Subscription object
        Subscription subscription = new Subscription(subscriptionName, username, password, subscriptionType);

        // Add the resource to the user's resource list
        resourceManager.addResource(subscription);

        // Save the user's resources to persistent storage
        FileHandler.saveUserResourcesToFile(user);
        System.out.println("Subscription added successfully!");
    }


    private static void findResourceMenu(Scanner scanner, ResourceManager resourceManager) {
        System.out.println("\n--- Find Resource ---");

        // Prompt user for the name of the resource they want to find
        System.out.println("Enter the name of the resource to find: ");
        String resourceName = scanner.nextLine();

        // Use ResourceManager to find the resource by name
        Resource foundResource = resourceManager.getResource(resourceName);

        // Check if resource was found
        if (foundResource != null) {
            System.out.println("Resource found:");
            foundResource.displayInfo(); // Display the details of the found resource
        } else {
            System.out.println("No resource found with the name: " + resourceName);
        }
    }


    private static void removeResourceMenu(Scanner scanner, ResourceManager resourceManager) {
        System.out.println("\n--- Remove Resource ---");

        // Prompt user for the name of the resource to remove
        System.out.println("Enter the name of the resource to remove: ");
        String resourceName = scanner.nextLine();

        // Find the resource to remove
        Resource resourceToRemove = resourceManager.getResource(resourceName);

        if (resourceToRemove != null) {
            resourceManager.removeResource(resourceToRemove); // Remove the resource using ResourceManager
            System.out.println("Resource removed successfully.");
        } else {
            System.out.println("No resource found with the name: " + resourceName);
        }
    }


}

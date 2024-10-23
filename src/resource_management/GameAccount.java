package resource_management;

public class GameAccount extends Resource {
    private String gamePlatform;

    public GameAccount(String name, String username, String password,String gamePlatform) {
        super(name, username, password);
        this.gamePlatform = gamePlatform;
    }
    @Override
    public void displayInfo() {
        System.out.println("Game Account: " + name + " (Platform: " + gamePlatform + " username: " + username + " password: "+ password +")");
    }
}

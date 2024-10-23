package resource_management;

public class Subscription extends Resource {
    private String subscriptionType;

    public Subscription(String name, String username, String password, String subscriptionType) {
        super(name, username, password);
        this.subscriptionType = subscriptionType;
    }
    @Override
    public void displayInfo() {
        System.out.println("Subscription: " + name + " (Type: " + subscriptionType + " username: " + username + " password: "+ password +")");
    }
}

package resource_management;

import java.io.Serializable;

public abstract class Resource implements Serializable {
    protected String name;
    protected String username;
    protected String password;

    public Resource(String name, String username, String password) {
        this.name = name;
        this.username = username;
        this.password = password;
    }

    public abstract void displayInfo();
}

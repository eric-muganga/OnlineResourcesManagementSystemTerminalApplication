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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public abstract void displayInfo();
}

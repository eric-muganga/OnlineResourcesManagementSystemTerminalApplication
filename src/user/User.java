package user;

import resource_management.Resource;
import resource_management.ResourceManager;
import utils.EncryptionUtil;

import javax.crypto.SecretKey;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class User implements Serializable {
    private String username;
    private byte[] encryptedPassword;
    private ResourceManager resourceManager;

    public User(String username, String password, SecretKey key) {
        this.username = username;
        this.encryptedPassword = EncryptionUtil.encryptPassword(password,key);
        this.resourceManager = new ResourceManager();
    }

    public String getUsername() {
        return username;
    }

    public ResourceManager getResourceManager() {
        return resourceManager;
    }


    public boolean verifyPassword(String inputPassword, SecretKey key) {
        return EncryptionUtil.verifyPassword(encryptedPassword, inputPassword, key);
    }
}

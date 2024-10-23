package utils;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.io.*;

public class KeyHandler {
    // saving the secretKey to a file
    public static void saveSecretKey(SecretKey secretKey, String fileName) {
        try(ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(fileName))) {
            oos.writeObject(secretKey);
        }catch(Exception e) {
            System.out.println("Error when saving the secretKey" + e.getMessage());
        }
    }

    public static SecretKey loadSecretKey(String fileName) {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(fileName))) {
            return (SecretKey) ois.readObject();

        }catch (FileNotFoundException e) {
            return null;
        } catch(Exception e) {
            System.out.println("Error when loading the secretKey" + e.getMessage());
            return null;
        }
    }

    // Generates a SecretKey for AES encryption
    public static SecretKey generateSecretKey() throws Exception {
        KeyGenerator keyGen = KeyGenerator.getInstance("AES");
        keyGen.init(128); // could use 256 for stronger encryption
        return keyGen.generateKey();
    }
}

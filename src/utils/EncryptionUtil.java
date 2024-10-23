package utils;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;


public class EncryptionUtil {
    public static byte[] encryptPassword(String password, SecretKey key) {
        try{
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.ENCRYPT_MODE, key);
            return cipher.doFinal(password.getBytes(StandardCharsets.UTF_8));
        } catch (Exception e) {
            System.out.println("Error encrypting password: " + e.getMessage());
            return null;
        }
    }

    public static boolean verifyPassword(byte[] encrptedPassword, String inputPassword, SecretKey key) {
        try{
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.DECRYPT_MODE,key);
            byte[] decrptedPassword = cipher.doFinal(encrptedPassword);
            return Arrays.equals(decrptedPassword, inputPassword.getBytes(StandardCharsets.UTF_8));
        }catch (Exception e){
            System.out.println("Error decrypting password: " + e.getMessage());
            return false;
        }

    }
}

package co.edu.uniquindio.eventmanager.model;

import co.edu.uniquindio.eventmanager.model.Enums.PaymentType;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.crypto.Cipher;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Random;
import javax.crypto.*;

@ToString
@Getter
@Setter
public class User {
    private String id, fullName, email, phoneNumber, password;
    private ArrayList<Purchase> purchaseList;
    private PaymentType paymentType;

    //All these variables are used to encrypt the password, thx.
    public static final KeyGenerator keygenerator;
    public static final Cipher desCipher;
    static {
        try {
            keygenerator = KeyGenerator.getInstance("AES");
            desCipher = Cipher.getInstance("AES");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    public static final SecretKey myDesKey = keygenerator.generateKey();

    public User(String fullName, String email, String phoneNumber, String password) {
        this.id = getSaltString();
        this.fullName = fullName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.purchaseList = new ArrayList<>();
        this.password = encrypt(password);
    }

     /*
    managePayment();
     */

    // Source - https://stackoverflow.com/a/20536597
    // Posted by Suresh Atta, modified by community. See post 'Timeline' for change history
    // Retrieved 2026-05-07, License - CC BY-SA 3.0
    protected String getSaltString() {
        String SALTCHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
        StringBuilder salt = new StringBuilder();
        Random rnd = new Random();
        while (salt.length() < 9) { // length of the random string.
            int index = (int) (rnd.nextFloat() * SALTCHARS.length());
            salt.append(SALTCHARS.charAt(index));
        }
        return salt.toString();

    }

    /**
     * Method that encrypts a password
     * @param password to encrypt
     * @return the encrypted password
     */
    private static String encrypt(String password){
        try{
            byte[] passwordBytes = password.getBytes(StandardCharsets.UTF_8);
            desCipher.init(Cipher.ENCRYPT_MODE, myDesKey);
            byte[] textEncrypted = desCipher.doFinal(passwordBytes);
            password = new String(textEncrypted);

        } catch (Exception e) {
            System.out.println("Exception");
        }
        return password;
    }
    /**
     * Method that compares a password with the encrypted one
     * @param password to compare
     * @return the comparison
     */
    public boolean comparePasswords(String password){
        return encrypt(password).equals(this.password);
    }
}

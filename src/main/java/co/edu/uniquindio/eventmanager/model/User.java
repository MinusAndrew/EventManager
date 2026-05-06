package co.edu.uniquindio.eventmanager.model;

import co.edu.uniquindio.eventmanager.model.Enums.PaymentType;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.crypto.Cipher;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
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

    public User(String id, String fullName, String email, String phoneNumber, String password) {
        this.id = id;
        this.fullName = fullName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.purchaseList = new ArrayList<>();
        this.password = encrypt(password);
    }

    /*
    updateUser();
    managePayment();
     */

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

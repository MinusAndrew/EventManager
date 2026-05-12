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

@Getter
@Setter
public class User implements Observer{
    private String id, fullName, email, phoneNumber, password;
    private ArrayList<Purchase> purchaseList;
    private boolean rootAccess = false;

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
    public void addPurchase(Purchase purchase){purchaseList.add(purchase);}
    public static final SecretKey myDesKey = keygenerator.generateKey();

    public User(String fullName, String email, String phoneNumber, String password) {
        this.id = getSaltString();
        this.fullName = fullName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.purchaseList = new ArrayList<>();
        this.password = encrypt(password);
    }

    public User(String id, String fullName, String email, String phoneNumber, String password) {
        this.id = id;
        this.fullName = fullName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.purchaseList = new ArrayList<>();
        this.password = encrypt(password);
    }

     /*
    managePayment(); // strategy
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


    /**
     * Method that updates the password of the user
     * @param password the new password of the user
     */
    public void updatePasswd(String password){
        setPassword(password);
    }

    @Override
    public String toString() {
        return
                        "   ID: " + id + "\n" +
                        "   Name: " + fullName + "\n" +
                        "   Email: " + email;
    }

    @Override
    public String update(String message) {
        System.out.println(fullName + " recibio una nueva notificacion: " + message);
        return message;
    }


    public String generateReceipt(){
        //PurchaseList have a: ticketlist and have a:
        // String idTicket, double finalCost, Event theEvent, Zone theZone, Chair theChair, TicketStatus ticketStatus
        String receipt;
        receipt =  "____________________________\n" +
                    "     Reporte de compras \n"+
                    "____________________________\n";
        for(Purchase purchase : purchaseList){
            receipt += "Compra N°: "+ purchase.getIdPurchase() + "\n Creada el: "+ purchase.getDateCreated() +
                    "\n Se adquirieron los siguientes tiquetes: \n";
            for(Ticket t : purchase.getTicketList()){
                receipt += "Ticket N°: " + t.getIdTicket() + "\n Para el evento de " + t.getTheEvent().getName() +
                        " en la zona " + t.getTheEvent().getThePlace() + "con la silla N°: " + t.getTheChair().getIdChair()
                        + "" + "\n \n";

            }
            receipt += "______________________________";

        }









        ;



        return receipt;
    }
}

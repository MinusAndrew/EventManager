package co.edu.uniquindio.eventmanager.model;

import co.edu.uniquindio.eventmanager.model.Enums.PaymentType;
import co.edu.uniquindio.eventmanager.model.Interfaces.Payment;
import co.edu.uniquindio.eventmanager.model.UserPayments.ApplePayment;
import co.edu.uniquindio.eventmanager.model.UserPayments.CardPayment;
import co.edu.uniquindio.eventmanager.model.UserPayments.PayPalPayment;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.font.Standard14Fonts;

import javax.crypto.Cipher;
import java.nio.charset.StandardCharsets;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.ArrayList;
import java.util.Random;
import javax.crypto.*;

@Getter
@Setter
public class User implements Observer{
    private String id, fullName, email, phoneNumber, password;
    private ArrayList<Purchase> purchaseList;
    private ArrayList<Purchase> cartList;
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
        this.cartList = new ArrayList<>();
        this.password = encrypt(password);
    }

    public User(String id, String fullName, String email, String phoneNumber, String password) {
        this.id = id;
        this.fullName = fullName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.purchaseList = new ArrayList<>();
        this.cartList = new ArrayList<>();
        this.password = encrypt(password);
    }

    public boolean managePayment(PaymentType type) {
        Payment strategy;
        switch (type) {
            case APPLE:
                strategy = new ApplePayment();
                break;
            case CARD:
                strategy = new CardPayment();
                break;
            case PAYPAL:
                strategy = new PayPalPayment();
                break;
            default:
                return false;
        }
        return strategy.executePayment();
    }

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
                        "   Email: " + email + "\n" +
                                "   Phone Number: "  + phoneNumber;

    }

    @Override
    public String update(String message) {
        System.out.println(fullName + " recibio una nueva notificacion: " + message);
        return message;
    }

    public void generateReceipt(){
        try {
            PDDocument document = new PDDocument();
            PDPage page = new PDPage(PDRectangle.A4);
            document.addPage(page);
            PDPageContentStream content = new PDPageContentStream(document, page);

            content.beginText();
            content.setFont(new PDType1Font(Standard14Fonts.FontName.TIMES_ROMAN), 12);
            content.newLineAtOffset(20, page.getMediaBox().getHeight() - 12);
            content.showText("_______________________________");
            content.newLineAtOffset(0, -20);
            content.showText("               Reporte de compras");
            content.newLineAtOffset(0, -10);
            content.showText("_______________________________");
            content.newLineAtOffset(0, -14);
            for(Purchase purchase : purchaseList){
                content.showText("Compra N°: " + purchase.getIdPurchase());
                content.newLineAtOffset(0, -14);
                content.showText("Creada el: " + purchase.getDateCreated().format(DateTimeFormatter.ofLocalizedDate(FormatStyle.LONG)));
                content.newLineAtOffset(0, -14);
                content.showText("Se adquirieron los siguientes tiquetes:");
                content.newLineAtOffset(0, -20);
                for(Ticket t : purchase.getTicketList()){
                    content.showText("-Ticket N°: " + t.getIdTicket());
                    content.newLineAtOffset(0, -14);
                    content.showText("Para el evento de " + t.getTheEvent().getName());
                    content.newLineAtOffset(0, -14);
                    content.showText("En la lugar ");
                    content.showText(t.getTheEvent().getThePlace().getName());
                    if(t.getTheChair() != null){
                        content.showText(" con la silla N°: ");
                        content.showText(t.getTheChair().getIdChair());
                    }
                    content.newLineAtOffset(0, -28);
                }
                content.showText(purchase.getDescription());
                content.newLineAtOffset(0, -10);
                content.showText("_______________________________");
                content.newLineAtOffset(0, -28);
            }
            content.endText();
            content.close();
            document.save("src/main/resources/reporteUser.pdf");

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }
}

package co.edu.uniquindio.eventmanager.model;

import co.edu.uniquindio.eventmanager.model.Enums.PaymentType;

import java.util.ArrayList;

public class User {
    private String id, fullName, email, phoneNumber;
    private ArrayList<Purchase> purchaseList;
    private PaymentType paymentType;

    public User(String id, String fullName, String email, String phoneNumber) {
        this.id = id;
        this.fullName = fullName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.purchaseList = new ArrayList<>();
    }

    /*
    updateUser();
    managePayment();
     */
}

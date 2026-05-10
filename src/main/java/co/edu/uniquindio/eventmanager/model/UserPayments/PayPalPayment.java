package co.edu.uniquindio.eventmanager.model.UserPayments;

import co.edu.uniquindio.eventmanager.model.Interfaces.Payment;

public class PayPalPayment implements Payment {

    @Override
    public boolean executePayment() {
        return false;
    }
}

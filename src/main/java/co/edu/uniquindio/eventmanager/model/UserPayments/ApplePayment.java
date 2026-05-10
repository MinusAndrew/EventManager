package co.edu.uniquindio.eventmanager.model.UserPayments;

import co.edu.uniquindio.eventmanager.model.Interfaces.Payment;

public class ApplePayment implements Payment {
    @Override
    public boolean executePayment() {
        return true;
    }
}

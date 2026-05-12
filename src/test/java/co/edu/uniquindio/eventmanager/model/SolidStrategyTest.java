package co.edu.uniquindio.eventmanager.model;

import co.edu.uniquindio.eventmanager.model.Enums.PaymentType;
import co.edu.uniquindio.eventmanager.model.Interfaces.Payment;
import co.edu.uniquindio.eventmanager.model.UserPayments.ApplePayment;
import co.edu.uniquindio.eventmanager.model.UserPayments.CardPayment;
import co.edu.uniquindio.eventmanager.model.UserPayments.PayPalPayment;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SolidStrategyTest {

    /**
     * Test for Dependency Inversion Principle (DIP).
     * The User class (high-level) doesn't depend on concrete payment implementations 
     * but on the Payment interface. We verify that different strategies can be 
     * swapped easily.
     */
    @Test
    void testDependencyInversionPrinciple() {
        User user = new User("John", "john@example.com", "123", "pass");

        // The user can manage different payments through the same method signature
        assertTrue(user.managePayment(PaymentType.APPLE), "Apple Payment should be executed via strategy");
        assertTrue(user.managePayment(PaymentType.CARD), "Card Payment should be executed via strategy");
        // PayPal currently returns false in its implementation
        assertFalse(user.managePayment(PaymentType.PAYPAL), "PayPal Payment should return false as per current implementation");
    }

    /**
     * Test for Single Responsibility Principle (SRP).
     * Each Payment implementation is only responsible for its own payment logic.
     */
    @Test
    void testSingleResponsibilityPrinciple() {
        Payment apple = new ApplePayment();
        Payment card = new CardPayment();
        Payment paypal = new PayPalPayment();

        // Each component has one job: executing its specific payment logic
        assertNotNull(apple);
        assertNotNull(card);
        assertNotNull(paypal);
        
        assertTrue(apple.executePayment());
        assertTrue(card.executePayment());
        assertFalse(paypal.executePayment());
    }
}

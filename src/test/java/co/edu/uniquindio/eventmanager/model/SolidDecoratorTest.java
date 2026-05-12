package co.edu.uniquindio.eventmanager.model;

import co.edu.uniquindio.eventmanager.model.Enums.PaymentType;
import co.edu.uniquindio.eventmanager.model.Interfaces.PurchaseComponent;
import co.edu.uniquindio.eventmanager.model.PurchaseDecorators.CateringDecorator;
import co.edu.uniquindio.eventmanager.model.PurchaseDecorators.InsuranceDecorator;
import co.edu.uniquindio.eventmanager.model.PurchaseDecorators.MerchandisingDecorator;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class SolidDecoratorTest {

    /**
     * Test for Open/Closed Principle (OCP).
     * We demonstrate that we can add new features (Catering, Insurance) 
     * to a base Purchase without modifying its original source code.
     */
    @Test
    void testOpenClosedPrinciple() {
        User user = new User("John", "john@example.com", "123", "pass");
        Purchase basePurchase = new Purchase(user, 100000, "P1", new ArrayList<>(), PaymentType.CARD);
        
        assertEquals(100000, basePurchase.getTotal());
        assertEquals("Compra Base", basePurchase.getDescription());

        // Add Catering without changing Purchase class
        PurchaseComponent decorated = new CateringDecorator(basePurchase);
        assertEquals(150000, decorated.getTotal(), "Total should increase by 50000 (Catering)");
        assertTrue(decorated.getDescription().contains("Catering"));

        // Add Insurance on top of Catering
        decorated = new InsuranceDecorator(decorated);
        assertEquals(165000, decorated.getTotal(), "Total should increase by 15000 (Insurance)");
        assertTrue(decorated.getDescription().contains("Seguro"));
    }

    /**
     * Test for Liskov Substitution Principle (LSP).
     * We demonstrate that any PurchaseDecorator can be used in place of 
     * the base PurchaseComponent interface without breaking the system.
     */
    @Test
    void testLiskovSubstitutionPrinciple() {
        User user = new User("John", "john@example.com", "123", "pass");
        PurchaseComponent base = new Purchase(user, 50000, "P2", new ArrayList<>(), PaymentType.PAYPAL);
        
        // This method accepts any PurchaseComponent
        double total = calculateTotalWithTax(base);
        assertEquals(55000, total, 0.001, "Tax calculation on base purchase");

        // We can pass a decorated object to the same method (LSP)
        PurchaseComponent decorated = new MerchandisingDecorator(base);
        double decoratedTotal = calculateTotalWithTax(decorated);
        
        // (50000 + 25000) * 1.1 = 82500
        assertEquals(82500, decoratedTotal, 0.001, "Tax calculation on decorated purchase should work correctly");
    }

    /**
     * Helper method to demonstrate LSP.
     * It relies on the abstraction (PurchaseComponent) and works for any implementation.
     */
    private double calculateTotalWithTax(PurchaseComponent component) {
        return component.getTotal() * 1.1; // 10% tax example
    }
}

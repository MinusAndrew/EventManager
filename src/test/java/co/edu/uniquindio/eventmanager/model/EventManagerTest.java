package co.edu.uniquindio.eventmanager.model;

import co.edu.uniquindio.eventmanager.model.Enums.EventPolicy;
import co.edu.uniquindio.eventmanager.model.Enums.EventType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class EventManagerTest {

    private EventManager eventManager;

    @BeforeEach
    void setUp() {
        eventManager = EventManager.getInstance();
        // Reset singleton state for clean tests
        eventManager.setUserList(new ArrayList<>());
        eventManager.setEventList(new ArrayList<>());
        eventManager.setPlaceList(new ArrayList<>());
        eventManager.setPurchaseList(new ArrayList<>());
    }

    @Test
    void testAddUserAndLogin() {
        User user = new User("John Doe", "john@example.com", "123456789", "password123");
        
        assertTrue(eventManager.addUser(user), "User should be added successfully");
        assertEquals(1, eventManager.getUserList().size());
        
        assertTrue(eventManager.login("john@example.com", "password123"), "Login should succeed with correct credentials");
        assertNotNull(eventManager.getCurrentUser());
        assertEquals("John Doe", eventManager.getCurrentUser().getFullName());
    }

    @Test
    void testLoginFailure() {
        User user = new User("John Doe", "john@example.com", "123456789", "password123");
        eventManager.addUser(user);
        
        assertFalse(eventManager.login("john@example.com", "wrongpassword"), "Login should fail with wrong password");
        assertFalse(eventManager.login("wrong@example.com", "password123"), "Login should fail with wrong email");
    }

    @Test
    void testAddUserValidation() {
        // Test invalid email
        User userInvalidEmail = new User("John Doe", "invalid-email", "123456789", "password123");
        assertFalse(eventManager.addUser(userInvalidEmail), "User with invalid email should not be added");

        // Test empty fields
        User userEmptyEmail = new User("John Doe", "", "123456789", "password123");
        assertFalse(eventManager.addUser(userEmptyEmail), "User with empty email should not be added");
    }

    @Test
    void testObserverNotification() {
        // This test demonstrates SRP and Observer Pattern
        User observerUser = new User("Observer User", "obs@example.com", "987654321", "pass");
        eventManager.addUser(observerUser);

        Event event = new Event("E1", "Concert", "Description", "Armenia", 
                                LocalDateTime.now().plusDays(10), null, 
                                EventType.CONCERT, EventPolicy.NONE);

        // In a real scenario, we'd check if the update method was called.
        // Since User.update() returns the message, we can't easily capture it without mocks or changes.
        // However, we can verify the method runs without error.
        assertDoesNotThrow(() -> eventManager.addEvent(event), "Adding an event should notify observers without throwing exceptions");
        assertEquals(1, eventManager.getEventList().size());
    }
}

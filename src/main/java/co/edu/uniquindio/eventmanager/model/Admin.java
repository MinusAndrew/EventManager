package co.edu.uniquindio.eventmanager.model;

public class Admin extends User{
    private final boolean rootAccess = true;

    public Admin(String id, String fullName, String email, String phoneNumber) {
        super(id, fullName, email, phoneNumber);
    }


    /*
    manageUsers()
    manageEvent()
    managePlace()
    manageZone()
     */
}

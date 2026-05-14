package co.edu.uniquindio.eventmanager.model;

public class Admin extends User{
    public Admin(String fullName, String email, String phoneNumber, String password) {
        super(fullName, email, phoneNumber, password);
        this.setRootAccess(true);
    }
}

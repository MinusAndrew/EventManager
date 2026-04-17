package co.edu.uniquindio.eventmanager.model;

import java.util.ArrayList;
import java.util.List;

public class UserManager {

    private final List<User> listUsers = new ArrayList<>();

    public void addUser(User user){
        System.out.println("Successfully created");
        listUsers.add(user);
    }

    public User read(String id){
        for(User user : listUsers){
            if(user.getId().equals(id)){
                System.out.println("User: "+id+" founded");
                return user;
            }
            else {
                System.out.println("User: "+id+" not founded");
            }
        }
        return null;
    }

    /*
    Not sure about it, weird logic
     */
    public void updateUser(User userUpdate){
        for(User user : listUsers) {
            if (user.getId().equals(userUpdate.getId())) {
                if(userUpdate.getEmail() != null) {user.setEmail(userUpdate.getEmail());}
                if(userUpdate.getPhoneNumber() != null) {user.setPhoneNumber(userUpdate.getPhoneNumber());}
                if(userUpdate.getFullName() != null) {user.setFullName(userUpdate.getFullName());}
                if(userUpdate.getEmail() != null){user.setPaymentType(userUpdate.getPaymentType());}
            }
        }
    }

    public void removeUser(User user){
        if(listUsers.remove(user)){
            System.out.println("Successfully deleted");
        }
        else {
            System.out.println("Couldn't delete, user not found");
        }
    }
}

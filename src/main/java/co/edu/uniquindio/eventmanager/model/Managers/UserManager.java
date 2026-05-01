package co.edu.uniquindio.eventmanager.model.Managers;

import co.edu.uniquindio.eventmanager.model.EventManager;
import co.edu.uniquindio.eventmanager.model.User;

import java.util.ArrayList;
import java.util.List;

public class UserManager {

    public static void addUser(User user){
        System.out.println("Successfully created");
        EventManager.getInstance().addUser(user);
    }

    public static User searchUserById(String id){
        for(User user : EventManager.getInstance().getUserList()){
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
    public static void updateUser(User userUpdate){
        for(User user : EventManager.getInstance().getUserList()) {
            if (user.getId().equals(userUpdate.getId())) {
                if(userUpdate.getEmail() != null) {
                    user.setEmail(userUpdate.getEmail());
                }
                if(userUpdate.getPhoneNumber() != null) {
                    user.setPhoneNumber(userUpdate.getPhoneNumber());
                }
                if(userUpdate.getFullName() != null) {
                    user.setFullName(userUpdate.getFullName());
                }
                if(userUpdate.getEmail() != null){
                    user.setPaymentType(userUpdate.getPaymentType());
                }
                System.out.println("User updated");
                break;
            }
        }
    }

    public static void removeUser(User user){
        if(searchUserById(user.getId()) != null){
            System.out.println("Successfully deleted");
            EventManager.getInstance().removeUser(user);
        } else {
            System.out.println("Couldn't delete, user not found");
        }
    }
    public static ArrayList<User> listUsers(){
        return EventManager.getInstance().getUserList();
    }
}

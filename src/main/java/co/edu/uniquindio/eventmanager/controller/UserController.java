package co.edu.uniquindio.eventmanager.controller;

import co.edu.uniquindio.eventmanager.model.EventManager;
import co.edu.uniquindio.eventmanager.model.User;

import java.util.ArrayList;

public class UserController {

    public static boolean addUser(User user){
        boolean flag = EventManager.getInstance().addUser(user);
        if (flag){
            System.out.println("Successfully created");
        }
        System.out.println("Not Created.");
        return flag;
    }

    public static User searchUserById(String id){
        for(User user : EventManager.getInstance().getUserList()){
            if(user.getId().equals(id)){
                System.out.println("User: "+id+" found");
                return user;
            }
            else {
                System.out.println("User: "+id+" not found");
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

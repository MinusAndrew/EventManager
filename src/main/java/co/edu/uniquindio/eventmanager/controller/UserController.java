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
    public static boolean updateUser(User userUpdate){
        int check = 0;
        for(User user : EventManager.getInstance().getUserList()) {
            if (user.getId().equals(userUpdate.getId())) {
                if(!userUpdate.getEmail().isBlank() && userUpdate.getEmail().matches("^[^@]+@[^@]+\\.[^@]+$")) {
                    user.setEmail(userUpdate.getEmail());
                    check++;
                }
                if(!userUpdate.getPhoneNumber().isBlank()) {
                    user.setPhoneNumber(userUpdate.getPhoneNumber());
                    check++;
                }
                if(!userUpdate.getFullName().isBlank()) {
                    user.setFullName(userUpdate.getFullName());
                    check++;
                }
                if(!userUpdate.comparePasswords("")){
                    user.updatePasswd(userUpdate.getPassword());
                    check++;
                }
                break;
                }
            }
        return check > 0;
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

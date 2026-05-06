package co.edu.uniquindio.eventmanager.model;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

@Getter
@Setter
public class EventManager {
    @Getter
    private static final EventManager instance = new EventManager("TicketMaster", "EventManagerPlus");

    private final String id, name;
    private ArrayList<User> userList;
    private ArrayList<Event> eventList;
    private ArrayList<Place> placeList;
    private ArrayList<Purchase> purchaseList;

    private EventManager(String id, String name) {
        this.id = id;
        this.name = name;
        this.userList = new ArrayList<>();
        this.eventList = new ArrayList<>();
        this.placeList = new ArrayList<>();
        this.purchaseList = new ArrayList<>();
    }

    /**
     * Method that verifies if a user can log in.
     * @param email of the user
     * @param password of the user
     * @return if the user can log in
     */
    public boolean login(String email, String password){
        boolean flag = false;
        for(User user : userList){
            if(user.getEmail().equals(email) && user.comparePasswords(password)){
                flag = true;
                break;
            }
        }
        return flag;
    }


    public void addUser(User u){
        userList.add(u);
    }
    public void removeUser(User u){
        userList.remove(u);
    }

    public void addEvent(Event e){
        eventList.add(e);
    }
    public void removeEvent(Event e){
        eventList.remove(e);
    }

    public void addPlace(Place p){
        placeList.add(p);
    }
    public void removePlace(Place p){
        placeList.remove(p);
    }

    public void addPurchase(Purchase p){
        purchaseList.add(p);
    }
    public void removePurchase(Purchase p){
        purchaseList.remove(p);
    }



}

package co.edu.uniquindio.eventmanager.model;

import co.edu.uniquindio.eventmanager.model.Enums.EventStatus;
import co.edu.uniquindio.eventmanager.model.Enums.EventType;
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

    private User currentUser;

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
                currentUser = EventManager.getInstance().findCurrentUser(email, password);
                break;
            }
        }
        return flag;
    }

    public User findCurrentUser(String email, String password){
        for(User user : userList){
            if(user.getEmail().equals(email) && user.comparePasswords(password)){
                return user;
            }
        }
        return null;
    }

    public ArrayList<Event> getEventByFilter(String city, EventType type, EventStatus status){
        ArrayList<Event> eventArrayList = new ArrayList<>();
        for (Event event : EventManager.getInstance().getEventList()){
            if (event.getCity().equalsIgnoreCase(city) && event.getEventType().equals(type) && event.getEventStatus().equals(status)){
                eventArrayList.add(event);
            }
        }
        return eventArrayList;
    }

    public boolean addUser(User u){
        if (name.isBlank() || u.getEmail().isBlank() || u.getPhoneNumber().isBlank() || u.comparePasswords("") || !u.getEmail().matches("^[^@]+@[^@]+\\.[^@]+$")) {
            return false;
        }
        userList.add(u);
        return true;
    }
    public void removeUser(User u){
        userList.remove(u);
    }
    public void addEvent(Event e){
        eventList.add(e);
        notifyObservers(e.notification());
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

    public void notifyObservers(String message){
        for(Observer o : userList){
            o.update(message);
        }
    }
}

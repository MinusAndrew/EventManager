package co.edu.uniquindio.eventmanager.model;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

@Getter
@Setter
public class EventManager {
    private static EventManager instance;

    private final String id, name;
    private ArrayList<User> userList;
    private ArrayList<Event> eventList;
    private ArrayList<Place> placeList;

    private EventManager(String id, String name) {
        this.id = id;
        this.name = name;
        this.userList = new ArrayList<>();
        this.eventList = new ArrayList<>();
        this.placeList = new ArrayList<>();
    }

    public static EventManager getInstance(){
        if (instance == null){
            return new EventManager("727osu!Game", "EventManagerPlus");
        }
        return instance;
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


}

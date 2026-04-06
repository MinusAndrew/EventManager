package co.edu.uniquindio.eventmanager.model;

import java.util.ArrayList;

public class EventManager {
    private static EventManager instance;

    private final String id, name;
    private ArrayList<User> userList;
    private ArrayList<Event> eventList;

    private EventManager(String id, String name) {
        this.id = id;
        this.name = name;
        this.userList = new ArrayList<>();
        this.eventList = new ArrayList<>();
    }

    public static EventManager getInstance(){
        if (instance == null){
            return new EventManager("727osu!Game", "EventManagerPlus");
        }
        return instance;
    }
}

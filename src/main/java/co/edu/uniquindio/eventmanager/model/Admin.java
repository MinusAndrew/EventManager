package co.edu.uniquindio.eventmanager.model;

import co.edu.uniquindio.eventmanager.model.Enums.EventPolicy;
import co.edu.uniquindio.eventmanager.model.Enums.EventStatus;
import co.edu.uniquindio.eventmanager.model.Managers.PlaceManager;
import co.edu.uniquindio.eventmanager.model.Managers.UserManager;
import co.edu.uniquindio.eventmanager.model.Managers.ZoneManager;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

public class Admin extends User {
    private final boolean rootAccess = true;

    public Admin(String id, String fullName, String email, String phoneNumber) {
        super(id, fullName, email, phoneNumber);
    }

    public void addUser(User u) {
        UserManager.addUser(u);
    }
    public User searchUserById(String id) {
        return UserManager.searchUserById(id);
    }
    public void updateUser(User u) {
        UserManager.updateUser(u);
    }
    public void removeUser(User u) {
        UserManager.removeUser(u);
    }
    public ArrayList<User> listUsers() {
        return UserManager.listUsers();
    }

    public void addEvent(Event event){
        EventManager.getInstance().addEvent(event);
    }
    public void removeEvent(Event event){
        EventManager.getInstance().removeEvent(event);
    }
    public Event searchEventById(String id){
        for(Event event : EventManager.getInstance().getEventList()){
            if(event.getIdEvent().equals(id)){
                return event;
            }
        }
        System.out.println("Event not found");
        return null;
    }
    //WAR CRIME, DO NOT CHANGE
    public void updateEvent(Event e) {
        for(Event event: listEvents()){
            if(e.getIdEvent().equals(event.getIdEvent())){
                removeEvent(event);
                addEvent(e);
                System.out.println("Event update");
                return;
            }
        }
        System.out.println("Event not found");
    }

    public ArrayList<Event> listEvents(){
        return EventManager.getInstance().getEventList();
    }
    public void publishEvent(Event event){
        event.setEventStatus(EventStatus.PUBLISHED);
    }
    public void cancelEvent(Event event){
        event.setEventStatus(EventStatus.CANCELLED);
    }
    public void pauseEvent(Event event){
        event.setEventStatus(EventStatus.PAUSED);
    }

    /* Useless
    public ArrayList<Place> getAllPlaces(){
        ArrayList<Place> p = new ArrayList<>();
        for(Event event : EventManager.getInstance().getEventList()){
            if(!p.contains(event.getThePlace())){
                p.add(event.getThePlace());
            }
        }
        return p;
    }
    */
    public void addPlace(Place p){
        PlaceManager.addPlace(p);
    }
    public void removePlace(Place p){
        PlaceManager.removePlace(p);
    }
    public Place searchPlaceById(String id){
        return PlaceManager.searchPlaceById(id);
    }
    public ArrayList<Place> listPlaces(){
        return PlaceManager.listPlaces();
    }
    public void updatePlace(Place placeUpdate){
        PlaceManager.updatePlace(placeUpdate);
    }

    public void addZone(Zone zone, Place place){
        ZoneManager.addZone(zone,place);
    }
    public void removeZone(Zone zone, Place place){
        ZoneManager.removeZone(zone,place);
    }
    public Zone searchZoneById(String id, Place place){
        return ZoneManager.searchZoneById(id,place);
    }
    public ArrayList<Zone> listZones(Place place){
        return ZoneManager.listZones(place);
    }
    public void updateZone(Zone zone, Place place){
        ZoneManager.updateZone(zone,place);
    }








    /*
    manageZone()
     */
}

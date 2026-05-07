package co.edu.uniquindio.eventmanager.model;

import co.edu.uniquindio.eventmanager.controller.*;
import co.edu.uniquindio.eventmanager.model.Enums.EventStatus;

import java.util.ArrayList;

public class Admin extends User {
    // private final boolean rootAccess = true; lmao proxyneeded

    public Admin(String id, String fullName, String email, String phoneNumber, String password) {
        super(fullName, email, phoneNumber, password);
    }

    public void addUser(User u) {
        UserController.addUser(u);
    }
    public User searchUserById(String id) {
        return UserController.searchUserById(id);
    }
    public void updateUser(User u) {
        UserController.updateUser(u);
    }
    public void removeUser(User u) {
        UserController.removeUser(u);
    }
    public ArrayList<User> listUsers() {
        return UserController.listUsers();
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
        PlaceController.addPlace(p);
    }
    public void removePlace(Place p){
        PlaceController.removePlace(p);
    }
    public Place searchPlaceById(String id){
        return PlaceController.searchPlaceById(id);
    }
    public ArrayList<Place> listPlaces(){
        return PlaceController.listPlaces();
    }
    public void updatePlace(Place placeUpdate){
        PlaceController.updatePlace(placeUpdate);
    }

    public void addZone(Zone zone, Place place){
        ZoneController.addZone(zone,place);
    }
    public void removeZone(Zone zone, Place place){
        ZoneController.removeZone(zone,place);
    }
    public Zone searchZoneById(String id, Place place){
        return ZoneController.searchZoneById(id,place);
    }
    public ArrayList<Zone> listZones(Place place){
        return ZoneController.listZones(place);
    }
    public void updateZone(Zone zone, Place place){
        ZoneController.updateZone(zone,place);
    }
    public void enableChair(Chair chair){
        ChairController.enableChair(chair);
    }
    public void reserveChair(Chair chair){
        ChairController.reserveChair(chair);
    }
    public void blockChair(Chair chair){
        ChairController.blockChair(chair);
    }

    public void addPurchase(Purchase purchase){
        PurchaseController.addPurchase(purchase);
    }
    public void removePurchase(Purchase purchase){
        PurchaseController.removePurchase(purchase);
    }
    public Purchase searchPurchaseById(String id){
        return PurchaseController.searchPurchaseById(id);
    }
    public void updatePurchase(Purchase purchase){
        PurchaseController.updatePurchase(purchase);
    }


}

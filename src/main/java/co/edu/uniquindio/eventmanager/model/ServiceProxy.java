package co.edu.uniquindio.eventmanager.model;

import co.edu.uniquindio.eventmanager.controller.*;
import co.edu.uniquindio.eventmanager.model.Enums.EventStatus;

import java.util.ArrayList;

public interface ServiceProxy {
    void addUser(User u);
    User searchUserById(String id);
    void updateUser(User u);
    void removeUser(User u);
    ArrayList<User> listUsers();

    void addEvent(Event event);
    void removeEvent(Event event);
    Event searchEventById(String id);
    void updateEvent(Event e);
    ArrayList<Event> listEvents();
    void publishEvent(Event event);
    void cancelEvent(Event event);
    void pauseEvent(Event event);

    void addPlace(Place p);
    void removePlace(Place p);
    Place searchPlaceById(String id);
    ArrayList<Place> listPlaces();
    void updatePlace(Place placeUpdate);

    void addZone(Zone zone, Place place);
    void removeZone(Zone zone, Place place);
    Zone searchZoneById(String id, Place place);
    ArrayList<Zone> listZones(Place place);
    void updateZone(Zone zone, Place place);

    void enableChair(Chair chair);
    void reserveChair(Chair chair);
    void blockChair(Chair chair);

    void addPurchase(Purchase purchase);
    void removePurchase(Purchase purchase);
    Purchase searchPurchaseById(String id) ;
    void updatePurchase(Purchase purchase);
    ArrayList<Purchase> listPurchases();
    boolean checkLoginAccess();
}

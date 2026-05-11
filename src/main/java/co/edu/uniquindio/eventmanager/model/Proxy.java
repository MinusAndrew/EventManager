package co.edu.uniquindio.eventmanager.model;

import co.edu.uniquindio.eventmanager.controller.AdminController;

import java.util.ArrayList;


public class Proxy  implements ServiceProxy {
    private final AdminController realService;
    private final User rolUser;

    public Proxy(AdminController admin, User user){
        this.realService = admin;
        this.rolUser = user;
    }

    public boolean checkAccess(){
        try {
            System.out.print("Verificando accesso.");
            for (int i = 0; i <= 10; i++) {
                System.out.print(".");
                Thread.sleep(200);
            }

        if (rolUser.isRootAccess()){
            System.out.println("Acceso concedido");
            return true;
        } else {
            System.out.println("No tiene acceso a esta funcion");
            Thread.sleep(2000);
            System.exit(126);
            return false;
        }
        } catch (InterruptedException e){
            Thread.currentThread().interrupt();
        }
        return false;
    }


    @Override
    public void addUser(User u) {
        if(checkAccess()){
            realService.addUser(u);
        }
    }

    @Override
    public User searchUserById(String id) {
        if(checkAccess()){
            return realService.searchUserById(id);
        }
        return null;
    }

    @Override
    public void updateUser(User u) {
        if(checkAccess()){
            realService.updateUser(u);
        }
    }

    @Override
    public void removeUser(User u) {
        if(checkAccess()){
            realService.removeUser(u);
        }
    }

    @Override
    public ArrayList<User> listUsers() {
        if(checkAccess()){
            return realService.listUsers();
        }
        return null;
    }

    @Override
    public void addEvent(Event event) {
        if(checkAccess()){
            realService.addEvent(event);
        }
    }

    @Override
    public void removeEvent(Event event) {
        if(checkAccess()){
            realService.removeEvent(event);
        }
    }

    @Override
    public Event searchEventById(String id) {
        if(checkAccess()){
            return realService.searchEventById(id);
        }
        return null;
    }

    @Override
    public void updateEvent(Event e) {
        if(checkAccess()){
            realService.updateEvent(e);
        }
    }

    @Override
    public ArrayList<Event> listEvents() {
        if(checkAccess()){
            return realService.listEvents();
        }
        return null;
    }

    @Override
    public void publishEvent(Event event) {
        if(checkAccess()){
            realService.publishEvent(event);
        }
    }

    @Override
    public void cancelEvent(Event event) {
        if(checkAccess()){
            realService.cancelEvent(event);
        }
    }

    @Override
    public void pauseEvent(Event event) {
        if(checkAccess()){
            realService.pauseEvent(event);
        }
    }

    @Override
    public void addPlace(Place p) {
        if(checkAccess()){
            realService.addPlace(p);
        }
    }

    @Override
    public void removePlace(Place p) {
        if(checkAccess()){
            realService.removePlace(p);
        }
    }

    @Override
    public Place searchPlaceById(String id) {
        if(checkAccess()){
            return realService.searchPlaceById(id);
        }
        return null;
    }

    @Override
    public ArrayList<Place> listPlaces() {
        if(checkAccess()){
            return realService.listPlaces();
        }
        return null;
    }

    @Override
    public void updatePlace(Place placeUpdate) {
        if(checkAccess()){
            realService.updatePlace(placeUpdate);
        }
    }

    @Override
    public void addZone(Zone zone, Place place) {
        if(checkAccess()){
            realService.addZone(zone,place);
        }
    }

    @Override
    public void removeZone(Zone zone, Place place) {
        if(checkAccess()){
            realService.removeZone(zone,place);
        }
    }

    @Override
    public Zone searchZoneById(String id, Place place) {
        if(checkAccess()){
            return realService.searchZoneById(id,place);
        }
        return null;
    }

    @Override
    public ArrayList<Zone> listZones(Place place) {
        if(checkAccess()){
            return realService.listZones(place);
        }
        return null;
    }

    @Override
    public void updateZone(Zone zone, Place place) {
        if(checkAccess()){
            realService.updateZone(zone,place);
        }
    }

    @Override
    public void enableChair(Chair chair) {
        if(checkAccess()){
            realService.enableChair(chair);
        }
    }

    @Override
    public void reserveChair(Chair chair) {
        if(checkAccess()){
            realService.reserveChair(chair);
        }
    }

    @Override
    public void blockChair(Chair chair) {
        if(checkAccess()){
            realService.blockChair(chair);
        }
    }

    @Override
    public void addPurchase(Purchase purchase) {
        if(checkAccess()){
            realService.addPurchase(purchase);
        }
    }

    @Override
    public void removePurchase(Purchase purchase) {
        if(checkAccess()){
            realService.removePurchase(purchase);
        }
    }

    @Override
    public Purchase searchPurchaseById(String id) {
        if(checkAccess()){
            return realService.searchPurchaseById(id);
        }
        return null;
    }

    @Override
    public void updatePurchase(Purchase purchase) {
        if(checkAccess()){
            realService.updatePurchase(purchase);
        }
    }

    @Override
    public ArrayList<Purchase> listPurchases() {
        if(checkAccess()){
            return realService.listPurchases();
        }
        return null;
    }
}

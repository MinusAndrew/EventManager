package co.edu.uniquindio.eventmanager.model.Managers;

import co.edu.uniquindio.eventmanager.model.EventManager;
import co.edu.uniquindio.eventmanager.model.Place;

import java.util.ArrayList;
import java.util.List;

public class PlaceManager {

    public static void addPlace(Place place){
        System.out.println("Successfully created");
        EventManager.getInstance().addPlace(place);
    }

    public static Place searchPlaceById(String id){
        for(Place place : EventManager.getInstance().getPlaceList()){
            if(place.getIdPlace().equals(id)){
                System.out.println("Place: "+id+" founded");
                return place;
            }
            else {
                System.out.println("Place: "+id+" not founded");
            }
        }
        return null;
    }


    public static void updatePlace(Place placeUpdate){
        for(Place place : EventManager.getInstance().getPlaceList()) {
            if (place.getIdPlace().equals(placeUpdate.getIdPlace())) {
                if(placeUpdate.getName() != null){
                    place.setName(placeUpdate.getName());}
                if(placeUpdate.getAddress() != null){
                    place.setAddress(placeUpdate.getAddress());}
                if(placeUpdate.getZoneList() != null){
                    place.setZoneList(placeUpdate.getZoneList());}
                if(placeUpdate.getEventList() != null){
                    place.setEventList(placeUpdate.getEventList());}
            }
        }
    }


    public static void removePlace(Place place){
        if(searchPlaceById(place.getIdPlace()) != null){
            EventManager.getInstance().removePlace(place);
            System.out.println("Successfully deleted");
        } else {
            System.out.println("Couldn't delete, place not found");
        }
    }
    public static ArrayList<Place> listPlaces(){
        return EventManager.getInstance().getPlaceList();
    }

}

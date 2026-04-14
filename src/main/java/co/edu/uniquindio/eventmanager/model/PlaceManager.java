package co.edu.uniquindio.eventmanager.model;

import java.util.ArrayList;
import java.util.List;

public class PlaceManager {

    private final List<Place> listPlaces = new ArrayList<>();

    public void create(Place place){
        System.out.println("Successfully created");
        listPlaces.add(place);
    }

    public Place read(String id){
        for(Place place : listPlaces){
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

    /*
    Not sure about it, weird logic
     */
    public void update(Place placeUpdate){
        for(Place place : listPlaces) {
            if (place.getIdPlace().equals(placeUpdate.getIdPlace())) {
                if(placeUpdate.getName() != null){place.setName(placeUpdate.getName());}
                if(placeUpdate.getAddress() != null){place.setAddress(placeUpdate.getAddress());}
                if(placeUpdate.getTheZone() != null){place.setTheZone(placeUpdate.getTheZone());}
                if(placeUpdate.getEventList() != null){place.setEventList(placeUpdate.getEventList());}
            }
        }
    }


    public void delete(Place place){
        if(listPlaces.remove(place)){
            System.out.println("Successfully deleted");
        }
        else {
            System.out.println("Couldn't delete, place not found");
        }
    }
}

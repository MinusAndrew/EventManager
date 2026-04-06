package co.edu.uniquindio.eventmanager.model;

import java.util.ArrayList;

public class Place {
    private String idPlace, name, address;

    private ArrayList<Event> eventList;
    private Zone theZone;

    public Place(String idPlace, String name, String address, Zone theZone) {
        this.idPlace = idPlace;
        this.name = name;
        this.address = address;
        this.eventList = new ArrayList<>();
        this.theZone = theZone;
    }


}

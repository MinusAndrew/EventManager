package co.edu.uniquindio.eventmanager.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;

@ToString
@Getter
@Setter
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

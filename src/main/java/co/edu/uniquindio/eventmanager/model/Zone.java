package co.edu.uniquindio.eventmanager.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;

@ToString
@Getter
@Setter
public class Zone {
    private String idZone, name;
    private int capacity;
    private double startingPrice;

    private ArrayList<Chair> chairList;

    public Zone(String idZone, String name, int capacity, double startingPrice) {
        this.idZone = idZone;
        this.name = name;
        this.capacity = capacity;
        this.startingPrice = startingPrice;
        this.chairList = new ArrayList<>();
    }


    /*
    setProperties();
     */

}

package co.edu.uniquindio.eventmanager.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;

@ToString
@Getter
@Setter
public class Place implements Composite {
    private String idPlace, name, address;

    private ArrayList<Event> eventList;
    private ArrayList<Composite> zoneList;

    public Place(String idPlace, String name, String address) {
        this.idPlace = idPlace;
        this.name = name;
        this.address = address;
        this.eventList = new ArrayList<>();
        this.zoneList = new ArrayList<>();
    }

    public void addZone(Composite zone){
        zoneList.add(zone);
    }
    public void removeZone(Zone zone){
        if(zoneList.remove(zone)){
            System.out.println("Successfully deleted");
        }
        else {
            System.out.println("Couldn't delete, zone not found");
        }
    }

    @Override
    public ArrayList<Chair> getAvailableChairs() {
        ArrayList<Chair> c = new ArrayList<>();
        for(Composite zone : zoneList){
            c.addAll(zone.getAvailableChairs());
        }
        return c;
    }
}

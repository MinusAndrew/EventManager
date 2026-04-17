package co.edu.uniquindio.eventmanager.model;

import java.util.ArrayList;
import java.util.List;

public class ZoneManager {
    private final List<Zone> listZones = new ArrayList<>();

    public void addZone(Zone zone){
        System.out.println("Successfully created");
        listZones.add(zone);
    }

    public Zone getZoneById(String id){
        for(Zone zone : listZones){
            if(zone.getIdZone().equals(id)){
                System.out.println("Zone: "+id+" founded");
                return zone;
            }
            else {
                System.out.println("Zone: "+id+" not founded");
            }
        }
        return null;
    }

    /*
    Not sure about it, weird logic
     */
    public void updateZone(Zone zoneUpdate){
        for(Zone zone : listZones) {
            if(zone.getIdZone().equals(zoneUpdate.getIdZone())){
                if(zoneUpdate.getName() != null){zone.setName(zoneUpdate.getName());}
                if(zoneUpdate.getCapacity() != 0){zone.setCapacity(zoneUpdate.getCapacity());}
                if(zoneUpdate.getChairList() != null){zone.setChairList(zoneUpdate.getChairList());}
                if(zoneUpdate.getStartingPrice() != 0){zone.setStartingPrice(zoneUpdate.getStartingPrice());}
            }
        }
    }

    public void removeZone(Zone zone){
        if(listZones.remove(zone)){
            System.out.println("Successfully deleted");
        }
        else {
            System.out.println("Couldn't delete, zone not found");
        }
    }
}

package co.edu.uniquindio.eventmanager.model.Managers;

import co.edu.uniquindio.eventmanager.model.Composite;
import co.edu.uniquindio.eventmanager.model.Place;
import co.edu.uniquindio.eventmanager.model.Zone;

import java.util.ArrayList;
import java.util.List;

public class ZoneManager {
    public static void addZone(Zone zone, Place place){
        System.out.println("Successfully created");
        place.getZoneList().add(zone);
    }

    public static Zone searchZoneById(String id, Place place){
        for(Composite z : place.getZoneList()){
            Zone zone = (Zone) z;
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

    public static void updateZone(Zone zoneUpdate, Place place){
        for(Composite z : place.getZoneList()){
            Zone zone = (Zone) z;
            if(zone.getIdZone().equals(zoneUpdate.getIdZone())){
                /*if(zoneUpdate.getName() != null){zone.setName(zoneUpdate.getName());}
                if(zoneUpdate.getCapacity() != 0){zone.setCapacity(zoneUpdate.getCapacity());}
                if(zoneUpdate.getChairList() != null){zone.setChairList(zoneUpdate.getChairList());}
                if(zoneUpdate.getStartingPrice() != 0){zone.setStartingPrice(zoneUpdate.getStartingPrice());}
                */
                place.removeZone(zone);
                place.addZone(zoneUpdate);
                return;
            }
        }
    }

    public static void removeZone(Zone zone, Place place) {
        for (Composite z : place.getZoneList()) {
            Zone zo = (Zone) z;
            if(zo.getIdZone().equals(zone.getIdZone())){
                System.out.println("Successfully deleted");
                place.removeZone(zone);
                return;

            }
        }
        System.out.println("Couldn't delete, zone not found");
    }
    public static ArrayList<Zone> listZones(Place place){
        ArrayList<Zone> z = new ArrayList<>();
        for(Composite c : place.getZoneList()){
            Zone zo = (Zone) c;
            z.add(zo);
        }
        return z;
    }

}

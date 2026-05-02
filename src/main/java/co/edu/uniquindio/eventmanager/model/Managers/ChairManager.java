package co.edu.uniquindio.eventmanager.model.Managers;

import co.edu.uniquindio.eventmanager.model.Chair;
import co.edu.uniquindio.eventmanager.model.Enums.ChairStatus;
import co.edu.uniquindio.eventmanager.model.Zone;

import java.util.ArrayList;
import java.util.List;

public class ChairManager {

    /*
    I found a way to fix this without too much work adapt it accordingly to the diagram.

    Also make the other managers static so we don't have to instance the Manager classes.
     */

    public static void addChair(Chair chair, Zone zone){ // pass the zone into the args
        System.out.println("Successfully created");
        zone.getChairList().add(chair);
    }

    public static Chair findChairById(String id, Zone zone){
        for(Chair chair : zone.getChairList()){
            if(chair.getIdChair().equals(id)){
                System.out.println("Chair: "+id+" found");
                return chair;
            }
            else {
                System.out.println("Chair: "+id+" not founded");
            }
        }
        return null;
    }

    /*
    Not sure about it, weird logic
     */
    public static void updateChair(Chair chairUpdate, Zone zone){
        for(Chair chair : zone.getChairList()) {
            if(chair.getIdChair().equals(chairUpdate.getIdChair())) {
            if(chairUpdate.getChairStatus() != null){chair.setChairStatus(chairUpdate.getChairStatus());}
            if(chairUpdate.getRow() != 0){chair.setRow(chairUpdate.getRow());}
            if(chairUpdate.getNumber() != 0){chair.setNumber(chairUpdate.getNumber());}
            if(chairUpdate.getTheZone() != null){chair.setTheZone(chairUpdate.getTheZone());}
            return;
            }
        }
    }


    public static void removeChair(Chair chair, Zone zone){
        if(zone.getChairList().remove(chair)){
            System.out.println("Successfully deleted");
        }
        else {
            System.out.println("Couldn't delete, chair not found");
        }
    }
    public static void enableChair(Chair chair){
        chair.setChairStatus(ChairStatus.AVAILABLE);
    }
    public static void reserveChair(Chair chair){
        chair.setChairStatus(ChairStatus.RESERVED);
    }
    public static void blockChair(Chair chair){
        chair.setChairStatus(ChairStatus.BLOCKED);
    }


}

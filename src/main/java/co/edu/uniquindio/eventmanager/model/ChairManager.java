package co.edu.uniquindio.eventmanager.model;

import java.util.ArrayList;
import java.util.List;

public class ChairManager {
    private final List<Chair> listChairs = new ArrayList<>();

    public void addChair(Chair chair){
        System.out.println("Successfully created");
        listChairs.add(chair);
    }

    public Chair read(String id){
        for(Chair chair : listChairs){
            if(chair.getIdChair().equals(id)){
                System.out.println("Chair: "+id+" founded");
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
    public void update(Chair chairUpdate){
        for(Chair chair : listChairs) {
            if(chair.getIdChair().equals(chairUpdate.getIdChair())) {
            if(chairUpdate.getChairStatus() != null){chair.setChairStatus(chairUpdate.getChairStatus());}
            if(chairUpdate.getRow() != 0){chair.setRow(chairUpdate.getRow());}
            if(chairUpdate.getNumber() != 0){chair.setNumber(chairUpdate.getNumber());}
            if(chairUpdate.getTheZone() != null){chair.setTheZone(chairUpdate.getTheZone());}
            }
        }
    }


    public void delete(Chair chair){
        if(listChairs.remove(chair)){
            System.out.println("Successfully deleted");
        }
        else {
            System.out.println("Couldn't delete, chair not found");
        }
    }
}

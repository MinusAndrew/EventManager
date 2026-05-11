package co.edu.uniquindio.eventmanager.model;

import co.edu.uniquindio.eventmanager.model.Enums.ChairStatus;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;

@ToString
@Getter
@Setter
@Builder
public class Zone implements Composite {
    private String idZone, name;
    private int capacity;
    private double startingPrice;
    @ToString.Exclude @Builder.Default
    private ArrayList<Chair> chairList = new ArrayList<>();


    public void addChair(Chair chair) {
        chairList.add(chair);
    }
    public void removeChair(Chair chair) {
        chairList.remove(chair);
    }
    public ArrayList<Chair> getChildren() {
        return chairList;

    }
    public Chair getChairById(String id) {
        for (Chair c : chairList) {
            if (c.getIdChair().equals(id)) {
                return c;
            }
        }
        System.out.println("Chair not found");
        return null;
    }

    @Override
    public ArrayList<Chair> getAvailableChairs() {
        ArrayList<Chair> c = new ArrayList<>();
        for(Chair chair : chairList) {
            if (chair.getChairStatus() == ChairStatus.AVAILABLE) {
                c.add(chair);
            }
        }
        return c;
    }



    /*
    setProperties();
     */

}

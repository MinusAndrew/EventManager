package co.edu.uniquindio.eventmanager.model;

import co.edu.uniquindio.eventmanager.model.Enums.ChairStatus;
import lombok.Getter;

@Getter
public class Chair {
    private String idChair;
    private int row, number;

    private Zone theZone;
    private ChairStatus chairStatus;

    public Chair(String idChair, int row, int number, Zone theZone, ChairStatus chairStatus) {
        this.idChair = idChair;
        this.row = row;
        this.number = number;
        this.chairStatus = chairStatus;
    }

    /*
    changeStatus();
     */
}

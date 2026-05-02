package co.edu.uniquindio.eventmanager.model;

import co.edu.uniquindio.eventmanager.model.Enums.TicketStatus;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
public class Ticket {
    private String idTicket;
    private double finalCost;

    private Event theEvent;
    private Zone theZone;
    private Chair theChair;

    private TicketStatus ticketStatus;
    private Purchase thePurchase;

    public Ticket(String idTicket, double finalCost, Event theEvent, Zone theZone, Chair theChair, TicketStatus ticketStatus) {
        this.idTicket = idTicket;
        this.finalCost = finalCost;
        this.theEvent = theEvent;
        this.theZone = theZone;
        this.theChair = theChair;
        this.ticketStatus = ticketStatus;
    }

    /*


    generateTicket();
    checkByPurchase();
    checkByEvent();
    boolean validateTicket();


     */

}

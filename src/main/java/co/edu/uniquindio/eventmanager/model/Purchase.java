package co.edu.uniquindio.eventmanager.model;
import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;

@ToString
@Getter
@Setter
public class Purchase{
    String idPurchase;
    LocalDate dateCreated;
    double total;

    private ArrayList<Ticket> ticketList;
    private final User theUser;

    @Builder
    public Purchase(User theUser, double total, LocalDate dateCreated, String idPurchase, ArrayList<Ticket> ticketList) {
        this.theUser = theUser;
        this.ticketList = ticketList;
        this.total = total;
        this.dateCreated = dateCreated;
        this.idPurchase = idPurchase;
    }
}

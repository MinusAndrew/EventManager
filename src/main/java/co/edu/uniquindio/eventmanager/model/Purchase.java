package co.edu.uniquindio.eventmanager.model;
import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;

@ToString
@Getter
@Setter
public class Purchase {
    private String idPurchase;
    private LocalDate dateCreated;
    private double total;

    private ArrayList<Ticket> ticketList;
    private final User theUser;

    @Builder
    public Purchase(User theUser, double total, String idPurchase, ArrayList<Ticket> ticketList) {
        this.theUser = theUser;
        this.ticketList = ticketList;
        this.total = total;
        this.dateCreated = LocalDate.now();
        this.idPurchase = idPurchase;
    }
}

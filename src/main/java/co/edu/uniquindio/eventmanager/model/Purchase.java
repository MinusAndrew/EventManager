package co.edu.uniquindio.eventmanager.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;

@Getter
@Setter
@Builder
public class Purchase {
    String idPurchase;
    LocalDate dateCreated;
    double total;

    private ArrayList<Ticket> ticketList;
    @Builder.Default
    // Possibly buggy behavior
    private User theUser = this.getTheUser();
}

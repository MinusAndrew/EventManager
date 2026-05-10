package co.edu.uniquindio.eventmanager.model;
import co.edu.uniquindio.eventmanager.model.Enums.PaymentType;
import co.edu.uniquindio.eventmanager.model.Interfaces.Clone;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;

@ToString
@Getter
@Setter
public class Purchase implements Clone {
    private String idPurchase;
    private LocalDateTime dateCreated;
    private double total;

    private ArrayList<Ticket> ticketList;
    private final User theUser;
    private PaymentType paymentType;


    @Builder
    public Purchase(User theUser, double total, String idPurchase, ArrayList<Ticket> ticketList, PaymentType paymentType) {
        this.theUser = theUser;
        this.ticketList = ticketList;
        this.total = total;
        this.dateCreated = LocalDateTime.now();
        this.idPurchase = idPurchase;
        this.paymentType = paymentType;

    }

    @Override
    public Clone cloneObject() {
        return new Purchase(this.theUser, this.total, this.idPurchase, this.ticketList, this.paymentType);
    }
}

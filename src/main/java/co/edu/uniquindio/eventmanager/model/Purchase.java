package co.edu.uniquindio.eventmanager.model;
import co.edu.uniquindio.eventmanager.model.Enums.PaymentType;
import co.edu.uniquindio.eventmanager.model.Interfaces.PurchaseClone;
import co.edu.uniquindio.eventmanager.model.Interfaces.PurchaseComponent;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;

@Getter
@Setter
public class Purchase implements PurchaseClone, PurchaseComponent {
    private String idPurchase;
    private LocalDateTime dateCreated;
    private double total;

    private ArrayList<Ticket> ticketList;
    private final User theUser;
    private PaymentType paymentType;
    private ArrayList<String> additionalServices;

    public Purchase(User theUser, double total, String idPurchase, ArrayList<Ticket> ticketList, PaymentType paymentType) {
        this.theUser = theUser;
        this.ticketList = ticketList;
        this.total = total;
        this.dateCreated = LocalDateTime.now();
        this.idPurchase = idPurchase;
        this.paymentType = paymentType;
        this.additionalServices = new ArrayList<>();
    }

    @Override
    public Purchase cloneObject() {
        return new Purchase(this.theUser, this.total, this.idPurchase, this.ticketList, this.paymentType);
    }

    @Override
    public String getDescription() {
        return "Compra Base";
    }

    public PurchaseMemento saveToMemento() {
        return new PurchaseMemento(total, new ArrayList<>(ticketList), paymentType, new ArrayList<>(additionalServices));
    }

    public void restoreFromMemento(PurchaseMemento memento) {
        this.total = memento.total();
        this.ticketList = memento.ticketList();
        this.paymentType = memento.paymentType();
        this.additionalServices = memento.additionalServices();
    }
}

package co.edu.uniquindio.eventmanager.model.Interfaces;

import co.edu.uniquindio.eventmanager.model.Ticket;

import java.util.ArrayList;

public interface PurchaseComponent {
    double getTotal();
    String getDescription();
    ArrayList<Ticket> getTicketList();
}

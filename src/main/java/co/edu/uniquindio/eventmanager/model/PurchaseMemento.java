package co.edu.uniquindio.eventmanager.model;

import co.edu.uniquindio.eventmanager.model.Enums.PaymentType;

import java.util.ArrayList;

public record PurchaseMemento(double total, ArrayList<Ticket> ticketList, PaymentType paymentType,
                              ArrayList<String> additionalServices) {
}

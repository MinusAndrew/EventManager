package co.edu.uniquindio.eventmanager.model;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class PurchaseManager {

    private final List<Purchase> listPurchases = new ArrayList<>();

    public void create(Purchase purchase){
        if(purchase.getTheUser() != null) {
            System.out.println("Successfully created");
            listPurchases.add(purchase);
        }
        else{
            System.out.println("The user cannot be null!");
        }
    }

    public Purchase read(String id){
        for(Purchase p : listPurchases){
            if(p.getIdPurchase().equals(id)){
                System.out.println("Order: "+id+" founded");
                return p;
            }
            else {
                System.out.println("Order: "+id+" not founded");
            }
        }
        return null;
    }

    /*
    Not sure about it, weird logic
     */
    public void update(Purchase purchaseUpdate){
        for(Purchase purchase : listPurchases) {
            if (purchase.getIdPurchase().equals(purchaseUpdate.getIdPurchase())) {
                if(purchaseUpdate.getTicketList() != null){purchase.setTicketList(purchaseUpdate.getTicketList());}
                if(purchaseUpdate.getDateCreated() != null){purchase.setDateCreated(purchaseUpdate.getDateCreated());}
                if(purchaseUpdate.getTotal() != 0){purchase.setTotal(purchaseUpdate.getTotal());}
            }
        }
    }

    public void delete(Purchase purchase){
        if(listPurchases.remove(purchase)){
            System.out.println("Successfully deleted");
        }
        else {
            System.out.println("Couldn't delete, order not found");
        }
    }


}

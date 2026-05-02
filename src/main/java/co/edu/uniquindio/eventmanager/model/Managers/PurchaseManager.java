package co.edu.uniquindio.eventmanager.model.Managers;

import co.edu.uniquindio.eventmanager.model.EventManager;
import co.edu.uniquindio.eventmanager.model.Purchase;
import lombok.Getter;

@Getter
public class PurchaseManager {

    public static void addPurchase(Purchase purchase){
        if(purchase.getTheUser() != null) {
            System.out.println("Successfully created");
            EventManager.getInstance().addPurchase(purchase);
        }
        else{
            System.out.println("The purchase cannot be null!");
        }
    }
    public static Purchase searchPurchaseById(String id){
        for(Purchase p : EventManager.getInstance().getPurchaseList()){
            if(p.getIdPurchase().equals(id)){
                System.out.println("Purchase: "+id+" found");
                return p;
            }
            else {
                System.out.println("Purchase: "+id+" not found");
            }
        }
        return null;
    }
    /*
    Not sure about it, weird logic
     */
    public static void updatePurchase(Purchase purchaseUpdate){
        for(Purchase purchase : EventManager.getInstance().getPurchaseList()) {
            if (purchase.getIdPurchase().equals(purchaseUpdate.getIdPurchase())) {
                if(purchaseUpdate.getTicketList() != null){purchase.setTicketList(purchaseUpdate.getTicketList());}
                if(purchaseUpdate.getDateCreated() != null){purchase.setDateCreated(purchaseUpdate.getDateCreated());}
                if(purchaseUpdate.getTotal() != 0){purchase.setTotal(purchaseUpdate.getTotal());}
                return;
            }
        }
    }
    public static void removePurchase(Purchase purchase){
        if(searchPurchaseById(purchase.getIdPurchase()) != null){
            EventManager.getInstance().removePurchase(purchase);
            System.out.println("Successfully deleted");
        } else {
            System.out.println("Couldn't delete, purchase not found");
        }
    }

}

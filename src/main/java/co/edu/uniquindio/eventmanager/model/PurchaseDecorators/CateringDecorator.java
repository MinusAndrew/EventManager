package co.edu.uniquindio.eventmanager.model.PurchaseDecorators;

import co.edu.uniquindio.eventmanager.model.Interfaces.PurchaseComponent;

public class CateringDecorator extends PurchaseDecorator {

    public CateringDecorator(PurchaseComponent wrappedComponent) {
        super(wrappedComponent);
    }

    @Override
    public double getTotal() {
        return super.getTotal() + 50000;
    }

    @Override
    public String getDescription() {
        return super.getDescription() + " + Catering";
    }
}

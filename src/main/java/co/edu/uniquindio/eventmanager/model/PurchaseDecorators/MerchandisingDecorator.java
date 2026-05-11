package co.edu.uniquindio.eventmanager.model.PurchaseDecorators;

import co.edu.uniquindio.eventmanager.model.Interfaces.PurchaseComponent;

public class MerchandisingDecorator extends PurchaseDecorator {

    public MerchandisingDecorator(PurchaseComponent wrappedComponent) {
        super(wrappedComponent);
    }

    @Override
    public double getTotal() {
        return super.getTotal() + 25000;
    }

    @Override
    public String getDescription() {
        return super.getDescription() + " + Merchandising";
    }
}

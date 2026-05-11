package co.edu.uniquindio.eventmanager.model.PurchaseDecorators;

import co.edu.uniquindio.eventmanager.model.Interfaces.PurchaseComponent;

public class InsuranceDecorator extends PurchaseDecorator {

    public InsuranceDecorator(PurchaseComponent wrappedComponent) {
        super(wrappedComponent);
    }

    @Override
    public double getTotal() {
        return super.getTotal() + 15000;
    }

    @Override
    public String getDescription() {
        return super.getDescription() + " + Seguro";
    }
}

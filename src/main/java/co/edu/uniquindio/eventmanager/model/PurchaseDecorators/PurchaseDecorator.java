package co.edu.uniquindio.eventmanager.model.PurchaseDecorators;

import co.edu.uniquindio.eventmanager.model.Interfaces.PurchaseComponent;

public abstract class PurchaseDecorator implements PurchaseComponent {
    protected PurchaseComponent wrappedComponent;

    public PurchaseDecorator(PurchaseComponent wrappedComponent) {
        this.wrappedComponent = wrappedComponent;
    }

    @Override
    public double getTotal() {
        return wrappedComponent.getTotal();
    }

    @Override
    public String getDescription() {
        return wrappedComponent.getDescription();
    }
}

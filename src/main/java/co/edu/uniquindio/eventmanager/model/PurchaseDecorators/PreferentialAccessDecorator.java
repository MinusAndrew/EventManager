package co.edu.uniquindio.eventmanager.model.PurchaseDecorators;

import co.edu.uniquindio.eventmanager.model.Interfaces.PurchaseComponent;

public class PreferentialAccessDecorator extends PurchaseDecorator {

    public PreferentialAccessDecorator(PurchaseComponent wrappedComponent) {
        super(wrappedComponent);
    }

    @Override
    public double getTotal() {
        return super.getTotal() + 30000;
    }

    @Override
    public String getDescription() {
        return super.getDescription() + " + Acceso Preferencial";
    }
}

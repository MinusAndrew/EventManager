package co.edu.uniquindio.eventmanager.viewController;

import co.edu.uniquindio.eventmanager.model.*;
import co.edu.uniquindio.eventmanager.model.Interfaces.PurchaseComponent;
import co.edu.uniquindio.eventmanager.model.PurchaseDecorators.*;
import co.edu.uniquindio.eventmanager.model.Enums.ChairStatus;
import co.edu.uniquindio.eventmanager.model.Enums.PaymentType;
import co.edu.uniquindio.eventmanager.model.Enums.TicketStatus;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.UUID;

import static co.edu.uniquindio.eventmanager.viewController.MainView.switchMenu;

public class PurchaseView implements Initializable {

    @FXML
    private Text eventNameText;
    @FXML
    private ChoiceBox<Zone> zoneChoiceBox;
    @FXML
    private GridPane chairsGrid;
    @FXML
    private ScrollPane chairsScrollPane;
    @FXML
    private Label noChairsLabel;
    @FXML
    private ChoiceBox<PaymentType> paymentTypeChoiceBox;
    @FXML
    private Text totalCostText;
    @FXML
    private CheckBox cateringCheck;
    @FXML
    private CheckBox insuranceCheck;
    @FXML
    private CheckBox merchandisingCheck;
    @FXML
    private CheckBox parkingCheck;
    @FXML
    private CheckBox preferentialCheck;

    private Event currentEvent;
    private ArrayList<Chair> selectedChairs = new ArrayList<>();
    private static Zone selectedZone;
    private Purchase editPurchase;
    private PurchaseMemento snapshot;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        editPurchase = EventManager.getInstance().getCurrentEditPurchase();
        if (editPurchase != null) {
            snapshot = editPurchase.saveToMemento();
        }
        currentEvent = EventManager.getInstance().getCurrentSelectedEvent();
        paymentTypeChoiceBox.getItems().addAll(PaymentType.values());


        zoneChoiceBox.setOnAction(
                event -> {
                    selectedZone = zoneChoiceBox.getValue();
                    renderChairsMatrix(selectedZone);
                    updateTotalCost();
                }
        );

        if (currentEvent != null) {
            eventNameText.setText(currentEvent.getName());
            
            ArrayList<Composite> zones = currentEvent.getThePlace().getZoneList();
            for (Composite c : zones) {
                if (c instanceof Zone) {
                    zoneChoiceBox.getItems().add((Zone) c);
                }
            }
            zoneChoiceBox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
                if (newValue != null) {
                    selectedZone = newValue;
                    renderChairsMatrix(newValue);
                }
            });

            if (editPurchase != null && !editPurchase.getTicketList().isEmpty()) {
                Zone editZone = editPurchase.getTicketList().get(0).getTheZone();
                zoneChoiceBox.setValue(editZone);
                paymentTypeChoiceBox.setValue(editPurchase.getPaymentType());
            }
        }

        // Restore checkboxes when editing
        if (editPurchase != null && editPurchase.getAdditionalServices() != null) {
            ArrayList<String> services = editPurchase.getAdditionalServices();
            cateringCheck.setSelected(services.contains("Catering"));
            insuranceCheck.setSelected(services.contains("Seguro"));
            merchandisingCheck.setSelected(services.contains("Merchandising"));
            parkingCheck.setSelected(services.contains("Parqueadero"));
            preferentialCheck.setSelected(services.contains("Acceso Preferencial"));
        }
    }

    private void renderChairsMatrix(Zone zone) {
        chairsGrid.getChildren().clear();
        selectedChairs.clear();

        ArrayList<Chair> chairs = zone.getChairList();
        if (chairs == null || chairs.isEmpty()) {
            chairsScrollPane.setVisible(false);
            noChairsLabel.setVisible(true);
            updateTotalCost();
            return;
        }

        chairsScrollPane.setVisible(true);
        noChairsLabel.setVisible(false);

        for (Chair chair : chairs) {
            ToggleButton btn = new ToggleButton(chair.getIdChair());
            btn.setPrefSize(50, 50);

            boolean isEditChair = false;
            if (editPurchase != null) {
                for (Ticket t : editPurchase.getTicketList()) {
                    if (t.getTheChair() != null && t.getTheChair().getIdChair().equals(chair.getIdChair())) {
                        isEditChair = true;
                        break;
                    }
                }
            }

            if (chair.getChairStatus() != ChairStatus.AVAILABLE && !isEditChair) {
                btn.setDisable(true);
                btn.setStyle("-fx-background-color: #ffcccc;");
            } else {
                btn.setStyle("-fx-background-color: #ccffcc;");
                if (isEditChair) {
                    btn.setSelected(true);
                    selectedChairs.add(chair);
                    btn.setStyle("-fx-background-color: #ccccff;");
                }
            }

            btn.setOnAction(e -> {
                if (btn.isSelected()) {
                    selectedChairs.add(chair);
                    btn.setStyle("-fx-background-color: #ccccff;");
                } else {
                    selectedChairs.remove(chair);
                    btn.setStyle("-fx-background-color: #ccffcc;");
                }
                updateTotalCost();
            });

            chairsGrid.add(btn, chair.getNumber(), chair.getRow());
        }
        updateTotalCost();
    }

    @FXML
    private void updateTotalCost() {
        if (selectedZone == null) {
            totalCostText.setText("0.0");
            return;
        }
        double baseTotal;
        if (checkTheresChairList()){
            baseTotal = selectedChairs.size() * selectedZone.getStartingPrice();
        }
        else {
            baseTotal = selectedZone.getStartingPrice();
        }
        double decorated = applyDecorators(baseTotal);
        totalCostText.setText(String.valueOf(decorated));
    }

    private double applyDecorators(double baseTotal) {
        // Create a simple PurchaseComponent with the base total
        PurchaseComponent component = new PurchaseComponent() {
            @Override
            public double getTotal() { return baseTotal; }
            @Override
            public String getDescription() { return "Compra Base"; }
        };
        if (cateringCheck.isSelected()) component = new CateringDecorator(component);
        if (insuranceCheck.isSelected()) component = new InsuranceDecorator(component);
        if (merchandisingCheck.isSelected()) component = new MerchandisingDecorator(component);
        if (parkingCheck.isSelected()) component = new ParkingDecorator(component);
        if (preferentialCheck.isSelected()) component = new PreferentialAccessDecorator(component);
        return component.getTotal();
    }

    @FXML
    private void backAction(ActionEvent event) {
        if (editPurchase != null && snapshot != null) {
            // Restore chair statuses for chairs that were in the snapshot
            // and set chairs that were added during the session back to AVAILABLE
            if (checkTheresChairList()) {
                // First, free ALL chairs currently selected in the UI
                for (Chair chair : selectedChairs) {
                    chair.setChairStatus(ChairStatus.AVAILABLE);
                }
                // Then, restore RESERVED status to chairs that were in the original snapshot
                for (Ticket t : snapshot.ticketList()) {
                    if (t.getTheChair() != null) {
                        t.getTheChair().setChairStatus(ChairStatus.RESERVED);
                    }
                }
            }
            editPurchase.restoreFromMemento(snapshot);
        }
        EventManager.getInstance().setCurrentEditPurchase(null);
        switchMenu(event, "mainMenu.fxml");
    }

    public static boolean checkTheresChairList(){
        return selectedZone != null && selectedZone.getChairList() != null && !selectedZone.getChairList().isEmpty();
    }

    @FXML
    private void payAction(ActionEvent event) {
        //1 this is not
        if (checkTheresChairList()){
            if (selectedChairs.isEmpty()) {
                showAlert(Alert.AlertType.ERROR, "Error", "Debe seleccionar al menos una silla.");
                return;
            }
        }
        if (paymentTypeChoiceBox.getValue() == null) {
            showAlert(Alert.AlertType.ERROR, "Error", "Debe seleccionar un método de pago.");
            return;
        }

        User currentUser = EventManager.getInstance().getCurrentUser();

        double baseTotal;
        if (checkTheresChairList()){
            baseTotal = selectedChairs.size() * selectedZone.getStartingPrice();
        }
        else {
            baseTotal = selectedZone.getStartingPrice();
        }
        double total = applyDecorators(baseTotal);


        // If editing, free the old chairs first
        // 4 this is not
        if (editPurchase != null) {
            for (Ticket t : editPurchase.getTicketList()) {
                if (t.getTheChair() != null) {
                    t.getTheChair().setChairStatus(ChairStatus.AVAILABLE);
                }
            }
            editPurchase.getTicketList().clear();
        }


        ArrayList<Ticket> tickets = new ArrayList<>();
        Purchase purchase = editPurchase != null ? editPurchase : new Purchase(currentUser, total, UUID.randomUUID().toString(), tickets, paymentTypeChoiceBox.getValue());

        purchase.setTotal(total);
        purchase.setPaymentType(paymentTypeChoiceBox.getValue());
        purchase.setTicketList(tickets);

        //3 this is not
        if (checkTheresChairList()){
            for (Chair chair : selectedChairs) {
                Ticket ticket = Ticket.builder().idTicket(UUID.randomUUID().toString()).finalCost(selectedZone.getStartingPrice()).theEvent(currentEvent).theZone(selectedZone).theChair(chair).ticketStatus(TicketStatus.ACTIVE).thePurchase(purchase).build();

                ticket.setThePurchase(purchase);
                tickets.add(ticket);
                // Reserve chair so others can't take it while it's in the cart
                chair.setChairStatus(ChairStatus.RESERVED);
            }
        }
        else {
            Ticket ticket = Ticket.builder().idTicket(UUID.randomUUID().toString()).finalCost(selectedZone.getStartingPrice()).theEvent(currentEvent).theZone(selectedZone).ticketStatus(TicketStatus.ACTIVE).thePurchase(purchase).build();

            ticket.setThePurchase(purchase);
            tickets.add(ticket);
        }
        // Save selected additional services
        ArrayList<String> services = new ArrayList<>();
        if (cateringCheck.isSelected()) services.add("Catering");
        if (insuranceCheck.isSelected()) services.add("Seguro");
        if (merchandisingCheck.isSelected()) services.add("Merchandising");
        if (parkingCheck.isSelected()) services.add("Parqueadero");
        if (preferentialCheck.isSelected()) services.add("Acceso Preferencial");
        purchase.setAdditionalServices(services);

        if (editPurchase == null) {
            currentUser.getCartList().add(purchase);
        }

        EventManager.getInstance().setCurrentEditPurchase(null);
        showAlert(Alert.AlertType.INFORMATION, "Éxito", "Compra guardada en el carrito. Total: $" + total);
        switchMenu(event, "mainMenu.fxml");
    }


    //  new Purchase(currentUser, total, UUID.randomUUID().toString(), tickets, paymentTypeChoiceBox.getValue());
    //  Ticket ticket = Ticket.builder().idTicket(UUID.randomUUID().toString()).finalCost(selectedZone.getStartingPrice()).theEvent(currentEvent).theZone(selectedZone).theChair(chair).ticketStatus(TicketStatus.ACTIVE).thePurchase(purchase).build();
    private void showAlert(Alert.AlertType type, String title, String content) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();
    }
}

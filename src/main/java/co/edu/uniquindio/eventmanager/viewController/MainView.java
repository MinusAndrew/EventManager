package co.edu.uniquindio.eventmanager.viewController;

import co.edu.uniquindio.eventmanager.Application;
import co.edu.uniquindio.eventmanager.controller.UserController;
import co.edu.uniquindio.eventmanager.model.Enums.ChairStatus;
import co.edu.uniquindio.eventmanager.model.Enums.EventStatus;
import co.edu.uniquindio.eventmanager.model.Enums.EventType;
import co.edu.uniquindio.eventmanager.model.Enums.TicketStatus;
import co.edu.uniquindio.eventmanager.model.Event;
import co.edu.uniquindio.eventmanager.model.EventManager;
import co.edu.uniquindio.eventmanager.model.Place;
import co.edu.uniquindio.eventmanager.model.User;
import co.edu.uniquindio.eventmanager.model.Purchase;
import co.edu.uniquindio.eventmanager.model.Ticket;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Objects;
import java.util.ResourceBundle;

public class MainView implements Initializable {
    @FXML
    private TableView<Event> eventTable;
    @FXML
    private TableColumn<Event, String> nameColumn;
    @FXML
    private TableColumn <Event, String> cityColumn;
    @FXML
    private TableColumn <Event, LocalDateTime> dateColumn;
    @FXML
    private TableColumn <Event, Place> placeColumn;
    @FXML
    private TableColumn <Event, EventType> typeColumn;
    @FXML
    private TableColumn <Event, EventStatus> statusColumn;

    @FXML
    private TableView<Purchase> historyTable;
    @FXML
    private TableColumn<Purchase, String> historyIdPurchaseColumn;
    @FXML
    private TableColumn<Purchase, LocalDate> historyDateColumn;
    @FXML
    private TableColumn<Purchase, Double> historyTotalColumn;
    @FXML
    private TableColumn<Purchase, String> historyPaymentColumn;
    @FXML
    private TableColumn<Purchase, Integer> historyTicketsColumn;


    @FXML
    private TableView<Purchase> cartTable;
    @FXML
    private TableColumn<Purchase, String> cartEventColumn;
    @FXML
    private TableColumn<Purchase, Double> cartTotalColumn;
    @FXML
    private TableColumn<Purchase, String> cartPaymentColumn;
    @FXML
    private TableColumn<Purchase, Integer> cartTicketsColumn;

    @FXML
    private Text userDataText;
    @FXML
    private TextField nameField;
    @FXML
    private TextField emailField;
    @FXML
    private TextField phoneField;
    @FXML
    private TextField passwordField;
    @FXML
    private TextField cityField;

    @FXML
    private ChoiceBox<EventType> typeChoiceBox;
    @FXML
    private ChoiceBox<EventStatus> statusChoiceBox;

    @FXML
    private AnchorPane scenePane;

    Stage currentWindow;
    Alert errorAlert = new Alert(Alert.AlertType.ERROR);
    Alert infoAlert = new Alert(Alert.AlertType.INFORMATION);

    //example implementation
    @FXML
    private void crazyAction(ActionEvent event) {
        switchMenu(event, "registerMenu.fxml");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        if (eventTable != null){
            userDataText.setText(EventManager.getInstance().getCurrentUser().toString());
            nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
            cityColumn.setCellValueFactory(new PropertyValueFactory<>("city"));
            dateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));
            placeColumn.setCellValueFactory(new PropertyValueFactory<>("thePlace"));
            typeColumn.setCellValueFactory(new PropertyValueFactory<>("eventType"));
            statusColumn.setCellValueFactory(new PropertyValueFactory<>("eventStatus"));
            typeChoiceBox.getItems().addAll(EventType.values());
            statusChoiceBox.getItems().addAll(EventStatus.values());
            fillUpEventList();
        }

        if (historyTable != null) {
            historyIdPurchaseColumn.setCellValueFactory(cellData ->
                    new SimpleStringProperty(cellData.getValue().getTicketList().isEmpty() ? "" : cellData.getValue().getIdPurchase()));
            historyTotalColumn.setCellValueFactory(new PropertyValueFactory<>("total"));
            historyPaymentColumn.setCellValueFactory(cellData ->
                    new SimpleStringProperty(cellData.getValue().getPaymentType().toString()));
            historyDateColumn.setCellValueFactory(cellData ->
                    new SimpleObjectProperty<>(cellData.getValue().getDateCreated().toLocalDate()));
            historyTicketsColumn.setCellValueFactory(cellData ->
                    new SimpleIntegerProperty(cellData.getValue().getTicketList().size()).asObject());
        }

        if (cartTable != null) {
            cartEventColumn.setCellValueFactory(cellData -> 
                new SimpleStringProperty(cellData.getValue().getTicketList().isEmpty() ? "" : cellData.getValue().getTicketList().get(0).getTheEvent().getName()));
            cartTotalColumn.setCellValueFactory(new PropertyValueFactory<>("total"));
            cartPaymentColumn.setCellValueFactory(cellData -> 
                new SimpleStringProperty(cellData.getValue().getPaymentType().toString()));
            cartTicketsColumn.setCellValueFactory(cellData -> 
                new SimpleIntegerProperty(cellData.getValue().getTicketList().size()).asObject());
        }
    }


    public void fillUpEventList(){
        eventTable.getItems().addAll(EventManager.getInstance().getEventList());
        eventTable.refresh();
    }

    @FXML
    public void fillUpFilteredEventList(){
        eventTable.getItems().clear();
        String city = cityField.getText().toLowerCase();
        ArrayList<Event> check = EventManager.getInstance().getEventByFilter(city, typeChoiceBox.getValue(), statusChoiceBox.getValue());
        eventTable.getItems().addAll(check);
        if (check.isEmpty()){
            infoAlert.setContentText("No se han encontrado eventos con tu criterio!");
            infoAlert.showAndWait();
            return;
        }
        eventTable.refresh();
        System.out.println("searched");
    }

    @FXML
    public void checkEventDetailsAction(ActionEvent event){
        Event selectedEvent = eventTable.getSelectionModel().getSelectedItem();
        if (selectedEvent == null) {
            errorAlert.setContentText("Por favor, seleccione un evento de la lista para ver sus detalles.");
            errorAlert.showAndWait();
            return;
        }
        EventManager.getInstance().setCurrentSelectedEvent(selectedEvent);
        switchMenu(event, "eventDetailsMenu.fxml");
    }


    @FXML
    public void buyEventAction(ActionEvent event) {
        Event selectedEvent = eventTable.getSelectionModel().getSelectedItem();
        if (selectedEvent == null) {
            errorAlert.setContentText("Por favor, seleccione un evento de la lista para comprar entradas.");
            errorAlert.showAndWait();
            return;
        }
        EventManager.getInstance().setCurrentSelectedEvent(selectedEvent);
        switchMenu(event, "purchaseMenu.fxml");
    }

    @FXML
    public void updateUserAction(ActionEvent event){
        User oldUserdata = EventManager.getInstance().getCurrentUser();
        User newData = new User(oldUserdata.getId(), getNameFromField(), getEmailFromField(), getPhoneNumberFromField(), getPasswordFromField());
        boolean check = UserController.updateUser(newData);
        if (!check){
            errorAlert.setContentText("No se ha ingresado, \nverifique que los datos dados sean correctos");
            errorAlert.showAndWait();
            return;
        }
        infoAlert.setContentText("Se han actualizado los datos correctamente.");
        infoAlert.showAndWait();
        userDataText.setText(EventManager.getInstance().getCurrentUser().toString());
    }

    @FXML
    private void logoutAction(ActionEvent event) {
        currentWindow = (Stage) scenePane.getScene().getWindow();
        currentWindow.close();
        switchMenu(event, "loginMenu.fxml");
    }

    public static void switchMenu(ActionEvent event, String menuFileName) {
        try {
            //check this 4 issues regarding classes                      |
            Parent mainRoot = FXMLLoader.load(Objects.requireNonNull(Application.class.getResource(menuFileName)));
            //
            Stage mainStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(mainRoot);
            mainStage.setScene(scene);
            mainStage.show();
        } catch (NullPointerException e) {
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setContentText("No se ha ingresado nombre de archivo del menú");
            errorAlert.show();
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public String getNameFromField(){
        return nameField.getText();
    }

    public String getPhoneNumberFromField(){
        return phoneField.getText();
    }

    public String getEmailFromField(){
        return emailField.getText();
    }

    public String getPasswordFromField(){
        return passwordField.getText();
    }

    @FXML
    public void refreshCartAction() {
        if (cartTable != null) {
            cartTable.getItems().clear();
            User currentUser = EventManager.getInstance().getCurrentUser();
            if (currentUser != null && currentUser.getCartList() != null) {
                cartTable.getItems().addAll(currentUser.getCartList());
            }
        }
    }

    @FXML
    public void refreshUserTabAction(){
        userDataText.setText(EventManager.getInstance().getCurrentUser().toString());
        refreshHistoryAction();
    }

    @FXML
    public void refreshHistoryAction() {
        if (historyTable != null) {
            historyTable.getItems().clear();
            User currentUser = EventManager.getInstance().getCurrentUser();
            if (currentUser != null && currentUser.getCartList() != null) {
                historyTable.getItems().addAll(currentUser.getPurchaseList());
            }
        }
    }

    @FXML
    public void processCartAction(ActionEvent event) {
        Purchase selected = cartTable.getSelectionModel().getSelectedItem();
        if (selected == null) {
            showAlert("Seleccione una compra del carrito para procesar.");
            return;
        }
        User currentUser = EventManager.getInstance().getCurrentUser();
        boolean success = currentUser.managePayment(selected.getPaymentType());
        if (success) {
            if (PurchaseView.checkTheresChairList()){
                for (Ticket t : selected.getTicketList()) {
                    t.getTheChair().setChairStatus(ChairStatus.SOLD);
                    t.setTicketStatus(TicketStatus.ACTIVE);
                }
            }
            currentUser.getCartList().remove(selected);
            currentUser.getPurchaseList().add(selected);
            EventManager.getInstance().addPurchase(selected);
            infoAlert.setContentText("Compra procesada exitosamente!");
            infoAlert.showAndWait();
            refreshCartAction();
        } else {
            showAlert("El pago no pudo ser procesado.");
        }
    }

    @FXML
    public void editCartAction(ActionEvent event) {
        Purchase selected = cartTable.getSelectionModel().getSelectedItem();
        if (selected == null) {
            showAlert("Seleccione una compra del carrito para modificar.");
            return;
        }
        EventManager.getInstance().setCurrentEditPurchase(selected);
        if (!selected.getTicketList().isEmpty()) {
            EventManager.getInstance().setCurrentSelectedEvent(selected.getTicketList().get(0).getTheEvent());
        }
        switchMenu(event, "purchaseMenu.fxml");
    }

    @FXML
    public void deleteCartAction(ActionEvent event) {
        Purchase selected = cartTable.getSelectionModel().getSelectedItem();
        if (selected == null) {
            showAlert("Seleccione una compra del carrito para eliminar.");
            return;
        }
        for (Ticket t : selected.getTicketList()) {
            if (t.getTheChair() != null) {
                t.getTheChair().setChairStatus(ChairStatus.AVAILABLE);
            }
        }
        EventManager.getInstance().getCurrentUser().getCartList().remove(selected);
        refreshCartAction();
    }

    @FXML
    public void generateAction(){
        EventManager.getInstance().getCurrentUser().generateReceipt();
        infoAlert.setContentText("Se ha generado el recibo");
        infoAlert.showAndWait();
    }

    private void showAlert(String msg) {
        errorAlert.setContentText(msg);
        errorAlert.showAndWait();
    }
}

package co.edu.uniquindio.eventmanager.viewController;

import co.edu.uniquindio.eventmanager.Application;
import co.edu.uniquindio.eventmanager.controller.AdminController;
import co.edu.uniquindio.eventmanager.controller.ChairController;
import co.edu.uniquindio.eventmanager.controller.PurchaseController;
import co.edu.uniquindio.eventmanager.controller.ZoneController;
import co.edu.uniquindio.eventmanager.model.*;
import co.edu.uniquindio.eventmanager.model.Enums.*;
import co.edu.uniquindio.eventmanager.model.*;
import co.edu.uniquindio.eventmanager.viewController.modifyView.ChairModify;
import co.edu.uniquindio.eventmanager.viewController.modifyView.EventModify;
import co.edu.uniquindio.eventmanager.viewController.modifyView.PlaceModify;
import co.edu.uniquindio.eventmanager.viewController.modifyView.ZoneModify;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import javax.swing.*;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class AdminView {

    @FXML
    private TextField newEventID, newEventName, newEventDescription, newEventCity, newEventTime,
                      newPlaceID, newPlaceName, newPlaceAddress,
                      newZoneID, newZoneName, newZoneCapacity, newZonePrice,
                      newChairID, newChairRow, newChairNumber,
                      newPurchaseID;


    @FXML
    private ComboBox<Place> newEventPlace, zoneThePlace, chairThePlace;

    @FXML
    private ComboBox<Composite> chairTheZone;

    @FXML
    private ComboBox<EventType>  newEventType;

    @FXML
    private ComboBox<EventPolicy> newEventPolicy;

    @FXML
    private ComboBox<ChairStatus> newChairStatus;

    @FXML
    private ComboBox<User> purchaseTheUser;

    @FXML
    private DatePicker newEventDate;

    @FXML
    private TableView eventMenu, placeMenu, zoneMenu, chairMenu, purchaseMenu;

    @FXML
    private TableColumn<Event, String> eventID;
    @FXML
    private TableColumn<Event, String> eventName;
    @FXML
    private TableColumn<Event, LocalDateTime> eventDate;

    @FXML
    private TableColumn<Place, String> placeID;
    @FXML
    private TableColumn<Place, String> placeName;
    @FXML
    private TableColumn<Place, Integer> placeAddress;

    @FXML
    private TableColumn<Zone, String> zoneID;
    @FXML
    private TableColumn<Zone, String> zoneName;
    @FXML
    private TableColumn<Zone, Integer> zoneCapacity;

    @FXML
    private TableColumn<Chair, String> chairID;
    @FXML
    private TableColumn<Chair, Integer> chairRow;
    @FXML
    private TableColumn<Chair, Integer> chairNumber;

    @FXML
    private TableColumn<Purchase, String> purchaseID;
    @FXML
    private TableColumn<Purchase, LocalDateTime> purchaseDate;
    @FXML
    private TableColumn<Purchase, Double> purchaseTotal;
    @FXML
    private TableColumn<Purchase, PurchaseStatus> purchaseStatus;

    @FXML
    private Button addEventButton, searchEventButton, backButton,
                   placeZoneList, addPlaceButton, searchPlaceButton, backButton2,
                   addZoneButton, searchZoneButton, zoneChairList, backButton3,
                   addChairButton, searchChairButton, backButton4,
                   showUserButton, searchPurchaseButton, backButton5;


    @FXML
    private javafx.scene.chart.LineChart<String, Number> ventasPorPeriodoChart;
    @FXML
    private javafx.scene.chart.BarChart<String, Number> ocupacionPorZonaChart;
    @FXML
    private javafx.scene.chart.PieChart ingresosServiciosChart;
    @FXML
    private javafx.scene.chart.PieChart tasaCancelacionChart;
    @FXML
    private javafx.scene.chart.BarChart<String, Number> topEventosChart;

    private AdminController adminController = new AdminController();
    private EventManager eventManager = EventManager.getInstance();

    private Place currentPlace;
    private Zone currentZone;
    private User currentUser;

    ObservableList<User> observableUsers = FXCollections.observableArrayList(eventManager.getUserList());
    ObservableList<Event> observableEvents = FXCollections.observableArrayList(eventManager.getEventList());
    ObservableList<Place> observablePlaces = FXCollections.observableArrayList(eventManager.getPlaceList());
    ObservableList<Composite> observableZones = FXCollections.observableArrayList();
    ObservableList<Chair> observableChairs = FXCollections.observableArrayList();
    ObservableList<Purchase> observablePurchases = FXCollections.observableArrayList();

    @FXML
    private void initialize(){

        zoneThePlace.setOnAction(e -> {

            currentPlace = zoneThePlace.getValue();

            if (currentPlace != null) {
                observableZones.setAll(currentPlace.getZoneList());
                zoneMenu.setItems(observableZones);
            }
        });

        chairThePlace.setOnAction(e -> {

            currentPlace = chairThePlace.getValue();

            if (currentPlace != null) {

                observableZones.setAll(currentPlace.getZoneList()
                );
                chairTheZone.setItems(observableZones);
                observableChairs.clear();
            }
        });

        chairTheZone.setOnAction(e -> {
            Composite selected =
                    chairTheZone.getValue();

            if (selected instanceof Zone) {
                currentZone = (Zone) selected;
                observableChairs.setAll(currentZone.getChairList());
                chairMenu.setItems(observableChairs);
            }
        });

        purchaseTheUser.setOnAction(e->{
            currentUser = purchaseTheUser.getValue();
            if(currentUser!=null){
                observablePurchases.setAll(currentUser.getPurchaseList());
                purchaseMenu.setItems(observablePurchases);
            }
        });


        newEventPlace.setItems(observablePlaces);
        newEventType.getItems().addAll(EventType.values());
        newEventPolicy.getItems().addAll(EventPolicy.values());
        newChairStatus.getItems().addAll(ChairStatus.values());
        zoneThePlace.setItems(observablePlaces);
        chairThePlace.setItems(observablePlaces);
        purchaseTheUser.setItems(observableUsers);

        eventID.setCellValueFactory(new PropertyValueFactory<>("idEvent"));
        eventName.setCellValueFactory(new PropertyValueFactory<>("name"));
        eventDate.setCellValueFactory(new PropertyValueFactory<>("date"));

        placeID.setCellValueFactory(new PropertyValueFactory<>("idPlace"));
        placeName.setCellValueFactory(new PropertyValueFactory<>("name"));
        placeAddress.setCellValueFactory(new PropertyValueFactory<>("address"));

        zoneID.setCellValueFactory(new PropertyValueFactory<>("idZone"));
        zoneName.setCellValueFactory(new PropertyValueFactory<>("name"));
        zoneCapacity.setCellValueFactory(new PropertyValueFactory<>("capacity"));

        chairID.setCellValueFactory(new PropertyValueFactory<>("idChair"));
        chairRow.setCellValueFactory(new PropertyValueFactory<>("row"));
        chairNumber.setCellValueFactory(new PropertyValueFactory<>("number"));

        purchaseID.setCellValueFactory(new PropertyValueFactory<>("idPurchase"));
        purchaseDate.setCellValueFactory(new PropertyValueFactory<>("dateCreated"));
        purchaseTotal.setCellValueFactory(new PropertyValueFactory<>("total"));
        purchaseStatus.setCellValueFactory(new PropertyValueFactory<>("purchaseStatus"));

        //Format Preferences
        {eventID.setReorderable(false);placeID.setReorderable(false);zoneID.setReorderable(false);chairID.setReorderable(false);purchaseID.setReorderable(false);purchaseTotal.setReorderable(false);
        eventName.setReorderable(false);placeName.setReorderable(false);zoneName.setReorderable(false);chairRow.setReorderable(false);purchaseDate.setReorderable(false);
        eventDate.setReorderable(false);placeAddress.setReorderable(false);zoneCapacity.setReorderable(false);chairNumber.setReorderable(false);purchaseStatus.setReorderable(false);

        eventID.setStyle("-fx-alignment: CENTER;");placeID.setStyle("-fx-alignment: CENTER;");zoneID.setStyle("-fx-alignment: CENTER;");
        chairID.setStyle("-fx-alignment: CENTER;");
        eventName.setStyle("-fx-alignment: CENTER;");placeName.setStyle("-fx-alignment: CENTER;");zoneName.setStyle("-fx-alignment: CENTER;");
        chairRow.setStyle("-fx-alignment: CENTER;");
        eventDate.setStyle("-fx-alignment: CENTER;");placeAddress.setStyle("-fx-alignment: CENTER;");zoneCapacity.setStyle("-fx-alignment: CENTER;");
        chairRow.setStyle("-fx-alignment: CENTER;");
        purchaseID.setStyle("-fx-alignment: CENTER;");purchaseTotal.setStyle("-fx-alignment: CENTER;");purchaseDate.setStyle("-fx-alignment: CENTER;");purchaseStatus.setStyle("-fx-alignment: CENTER;");}

        eventMenu.setItems(observableEvents);
        eventMenu.setRowFactory(tv -> {

            TableRow<Event> row = new TableRow<>();

            ContextMenu menu = new ContextMenu();

            MenuItem delete = new MenuItem("Delete");
            MenuItem modify = new MenuItem("Modify");
            MenuItem read = new MenuItem("Read");

            delete.setOnAction(e -> {
                Event event = row.getItem();

                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Confirmation");
                alert.setHeaderText("Event Deletion");
                alert.setContentText("Are you sure you want to delete the event:" + "\n" +
                        "ID: " + event.getIdEvent() + "\n" +
                        "Name: " + event.getName()
                );

                Optional<ButtonType> button = alert.showAndWait();

                if(button.isPresent() && button.get() == ButtonType.OK){
                    Place place = event.getThePlace();

                    if (place != null) {
                        place.removeEvent(event.getIdEvent());
                    }

                    eventManager.removeEvent(event);
                    observableEvents.remove(event);
                    Alert information = new Alert(Alert.AlertType.INFORMATION);
                    information.setTitle("Information");
                    information.setHeaderText("Succesfull!");
                    information.setContentText("The following event has been removed:" + "\n" +
                            "ID: " + event.getIdEvent() + "\n" +
                            "Name: " + event.getName());
                    information.show();
                }
                System.out.println(eventManager.getEventList().size());
            });

            modify.setOnAction(e -> {
                Event event = row.getItem();

                try {

                    FXMLLoader fxmlLoader = new FXMLLoader(Application.class.getResource("eventModify.fxml"));

                    Parent root = fxmlLoader.load();

                    EventModify controller =
                            fxmlLoader.getController();

                    controller.setEvent(event);

                    Stage stage = new Stage();
                    stage.setResizable(false);
                    stage.setTitle("Event Modify");
                    stage.setScene(new Scene(root));
                    stage.showAndWait();

                    observableEvents.setAll(eventManager.getEventList());

                    eventMenu.refresh();


                } catch (Exception ex) {

                    ex.printStackTrace();
                }
            });

            read.setOnAction(e ->{
                Event event = row.getItem();

                Alert information = new Alert(Alert.AlertType.INFORMATION);
                information.setTitle("Information");
                information.setHeaderText("Reading Event: "+event.getName());
                information.setContentText("Displaying event information: " + "\n" +
                            "ID: " + event.getIdEvent() + "\n" +
                            "Name: " + event.getName() + "\n" +
                            "Description: " + event.getDescription() + "\n" +
                            "Date: " + event.getDate().format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.LONG, FormatStyle.SHORT)) + "\n" +
                            "City: " + event.getCity() + "\n" +
                            "Place: " + "\n" + event.getThePlace() + "\n" +
                            "Type: " + event.getEventType() + "\n" +
                            "Status: " + event.getEventStatus());
                information.show();
            });

            menu.getItems().addAll(delete, modify, read);

            row.contextMenuProperty().bind(
                    Bindings.when(row.emptyProperty())
                            .then((ContextMenu) null)
                            .otherwise(menu)
            );
            return row;
        });

        placeMenu.setItems(observablePlaces);
        placeMenu.setRowFactory(tv -> {

            TableRow<Place> row = new TableRow<>();

            ContextMenu menu = new ContextMenu();

            MenuItem delete = new MenuItem("Delete");
            MenuItem modify = new MenuItem("Modify");
            MenuItem read = new MenuItem("Read");

            delete.setOnAction(e -> {
                Place place = row.getItem();

                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Confirmation");
                alert.setHeaderText("Place Deletion");
                alert.setContentText("Are you sure you want to delete the place:" + "\n" +
                        "ID: " + place.getIdPlace() + "\n" +
                        "Name: " + place.getName()
                );

                Optional<ButtonType> button = alert.showAndWait();

                if(button.isPresent() && button.get() == ButtonType.OK){
                    eventManager.removePlace(place);
                    observablePlaces.remove(place);
                    Alert information = new Alert(Alert.AlertType.INFORMATION);
                    information.setTitle("Information");
                    information.setHeaderText("Succesfull!");
                    information.setContentText("The following place has been removed:" + "\n" +
                            "ID: " + place.getIdPlace() + "\n" +
                            "Name: " + place.getName());
                    information.show();
                    for (Event event2 : eventManager.getEventList()) {
                        if (place.equals(event2.getThePlace())) {
                            event2.setThePlace(null);
                        }
                    }
                }
            });

            modify.setOnAction(e -> {
                Place place = row.getItem();

                try {

                    FXMLLoader fxmlLoader = new FXMLLoader(Application.class.getResource("placeModify.fxml"));

                    Parent root = fxmlLoader.load();

                    PlaceModify controller =
                            fxmlLoader.getController();

                    controller.setPlace(place);

                    Stage stage = new Stage();
                    stage.setResizable(false);
                    stage.setTitle("Place Modify");
                    stage.setScene(new Scene(root));
                    stage.showAndWait();

                    observablePlaces.setAll(eventManager.getPlaceList());

                    eventMenu.refresh();


                } catch (Exception ex) {

                    ex.printStackTrace();
                }
            });

            read.setOnAction(e -> {
                Place place = row.getItem();

                Alert information = new Alert(Alert.AlertType.INFORMATION);
                information.setTitle("Information");
                information.setHeaderText("Reading Place: "+place.getName());
                information.setContentText("Displaying place information: " + "\n" +
                        "ID: " + place.getIdPlace() + "\n" +
                        "Name: " + place.getName() + "\n" +
                        "Adress: " + place.getAddress() + "\n" +
                        "Events: " + "\n" + place.getEventList() + "\n" +
                        "Zones: " + place.getZoneList());
                information.show();
            });

            menu.getItems().addAll(delete, modify, read);

            row.contextMenuProperty().bind(
                    Bindings.when(row.emptyProperty())
                            .then((ContextMenu) null)
                            .otherwise(menu)
            );
            return row;
        });

        zoneMenu.setRowFactory(tv ->{
            TableRow<Zone> row = new TableRow<>();

            ContextMenu menu = new ContextMenu();

            MenuItem delete = new MenuItem("Delete");
            MenuItem modify = new MenuItem("Modify");
            MenuItem read = new MenuItem("Read");

            delete.setOnAction(e -> {

                Zone zone = row.getItem();

                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Confirmation");
                alert.setHeaderText("Zone Deletion");
                alert.setContentText(
                        "Are you sure you want to delete the zone:" + "\n" +
                                "ID: " + zone.getIdZone() + "\n" +
                                "Name: " + zone.getName()
                );

                Optional<ButtonType> button = alert.showAndWait();

                if (button.isPresent() && button.get() == ButtonType.OK) {
                    Place currentPlace = zoneThePlace.getValue();
                    if (currentPlace != null) {
                        currentPlace.getZoneList().remove(zone);
                        observableZones.remove(zone);

                        Alert information = new Alert(Alert.AlertType.INFORMATION);
                        information.setTitle("Information");
                        information.setHeaderText("Successful!");
                        information.setContentText(
                                "The following zone has been removed:" + "\n" +
                                        "ID: " + zone.getIdZone() + "\n" +
                                        "Name: " + zone.getName()
                        );
                        information.show();
                    }
                }
            });
            modify.setOnAction(e -> {
                Zone zone = row.getItem();

                try {

                    FXMLLoader fxmlLoader = new FXMLLoader(Application.class.getResource("zoneModify.fxml"));

                    Parent root = fxmlLoader.load();

                    ZoneModify controller = fxmlLoader.getController();

                    controller.setZone(zone);

                    Stage stage = new Stage();
                    stage.setResizable(false);
                    stage.setTitle("Zone Modify");
                    stage.setScene(new Scene(root));
                    stage.showAndWait();

                    observablePlaces.setAll(eventManager.getPlaceList());
                    eventMenu.refresh();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            });
            read.setOnAction(e -> {

                Zone zone = row.getItem();

                Alert information = new Alert(Alert.AlertType.INFORMATION);

                information.setTitle("Information");
                information.setHeaderText("Reading Zone: "+zone.getName());

                information.setContentText(
                        "Displaying zone information: " + "\n" +
                                "ID: " + zone.getIdZone() + "\n" +
                                "Name: " + zone.getName() + "\n" +
                                "Capacity: " + zone.getCapacity() + "\n" +
                                "Price: " + zone.getStartingPrice() + "\n" +
                                "Chair list: " + zone.getChairList() + "\n"
                );

                information.show();
            });

            read.setOnAction(e -> {

                Zone zone = row.getItem();

                Alert information = new Alert(Alert.AlertType.INFORMATION);

                information.setTitle("Information");
                information.setHeaderText(zone.getName());

                information.setContentText(
                        "Displaying zone information: " + "\n" +
                                "ID: " + zone.getIdZone() + "\n" +
                                "Name: " + zone.getName() + "\n" +
                                "Capacity: " + zone.getCapacity() + "\n" +
                                "Price: " + zone.getStartingPrice() + "\n" +
                                "Chair list: " + zone.getChairList() + "\n"
                );

                information.show();
            });

            menu.getItems().addAll(delete, modify, read);
            row.contextMenuProperty().bind(
                    Bindings.when(row.emptyProperty())
                            .then((ContextMenu) null)
                            .otherwise(menu)
            );
            return row;
        });

        chairMenu.setRowFactory(tv ->{
            TableRow<Chair> row = new TableRow<>();

            ContextMenu menu = new ContextMenu();

            MenuItem delete = new MenuItem("Delete");
            MenuItem modify = new MenuItem("Modify");
            MenuItem read = new MenuItem("Read");

            delete.setOnAction(e -> {

                Chair chair = row.getItem();

                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Confirmation");
                alert.setHeaderText("Chair Deletion");
                alert.setContentText(
                        "Are you sure you want to delete the chair:" + "\n" +
                                "ID: " + chair.getIdChair() + "\n" +
                                "Row: " + chair.getRow() + "\n" +
                                "Number: " +chair.getNumber());

                Optional<ButtonType> button = alert.showAndWait();

                if (button.isPresent() && button.get() == ButtonType.OK) {

                    if (currentZone != null) {

                        ChairController.removeChair(chair, currentZone);
                        observableChairs.remove(chair);

                        Alert information = new Alert(Alert.AlertType.INFORMATION);
                        information.setTitle("Information");
                        information.setHeaderText("Successful!");
                        information.setContentText(
                                "The following chair has been removed:\n" +
                                        "ID: " + chair.getIdChair() + "\n" +
                                        "Row: " + chair.getRow() + "\n" +
                                        "Number: " +chair.getNumber());
                        information.show();
                    }
                }
            });

            modify.setOnAction(e -> {
                Chair chair = row.getItem();

                try {
                    FXMLLoader fxmlLoader = new FXMLLoader(Application.class.getResource("chairModify.fxml"));
                    Parent root = fxmlLoader.load();
                    ChairModify controller = fxmlLoader.getController();
                    controller.setChair(chair);

                    Stage stage = new Stage();
                    stage.setResizable(false);
                    stage.setTitle("Zone Modify");
                    stage.setScene(new Scene(root));
                    stage.showAndWait();

                    observableChairs.setAll(currentZone.getChairList());
                    eventMenu.refresh();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            });

            read.setOnAction(e -> {

                Chair chair = row.getItem();

                Alert information = new Alert(Alert.AlertType.INFORMATION);

                information.setTitle("Information");
                information.setHeaderText("Reading Chair: "+chair.getIdChair());
                information.setContentText(
                        "Displaying chair information: " + "\n" +
                                "ID: " + chair.getIdChair() + "\n" +
                                "Row: " + chair.getRow() + "\n" +
                                "Number: " + chair.getNumber() + "\n" +
                                "Zone: " + chair.getTheZone() + "\n" +
                                "Chair Status: " + chair.getChairStatus() + "\n");
                information.show();
            });

            menu.getItems().addAll(delete, modify, read);
            row.contextMenuProperty().bind(
                    Bindings.when(row.emptyProperty())
                            .then((ContextMenu) null)
                            .otherwise(menu)
            );
            return row;
        });

        purchaseMenu.setRowFactory(tv ->{
            TableRow<Purchase> row = new TableRow<>();

            ContextMenu menu = new ContextMenu();

            MenuItem cancel = new MenuItem("Cancel");
            MenuItem refund = new MenuItem("Refund");
            MenuItem read = new MenuItem("Read");

            cancel.setOnAction(e->{
                Purchase purchase = row.getItem();
                purchase.setPurchaseStatus(PurchaseStatus.CANCELED);
                purchaseMenu.setItems(observablePurchases);
                purchaseMenu.refresh();
            });

            refund.setOnAction(e->{
                Purchase purchase = row.getItem();
                purchase.setPurchaseStatus(PurchaseStatus.REFUNDED);
                purchaseMenu.setItems(observablePurchases);
                purchaseMenu.refresh();
            });

            read.setOnAction(e -> {

                Purchase purchase = row.getItem();

                Alert information = new Alert(Alert.AlertType.INFORMATION);

                information.setTitle("Information");
                information.setHeaderText("Reading Purchase: "+purchase.getIdPurchase());
                information.setContentText(
                        "Displaying chair information: " + "\n" +
                                "ID: " + purchase.getIdPurchase() + "\n" +
                                "Date: " + purchase.getDateCreated().format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.LONG,FormatStyle.SHORT)) + "\n" +
                                "Total: " + purchase.getTotal() + "\n" +
                                "Tickets: " + purchase.getTicketList() + "\n" +
                                "User: " + purchase.getTheUser() + "\n"+
                                "Payment Type: "+ purchase.getPaymentType() + "\n" +
                                "Purchase Status: "+ purchase.getPurchaseStatus());
                information.show();
            });

            menu.getItems().addAll(cancel, refund, read);
            row.contextMenuProperty().bind(
                    Bindings.when(row.emptyProperty())
                            .then((ContextMenu) null)
                            .otherwise(menu)
            );
            return row;
        });

        addEventButton.setOnAction(e -> createEvent());
        addPlaceButton.setOnAction(e -> createPlace());
        addZoneButton.setOnAction(e-> createZone());
        addChairButton.setOnAction(e-> createChair());

        searchEventButton.setOnAction(e -> searchEvent());
        searchPlaceButton.setOnAction(e -> searchPlace());
        searchZoneButton.setOnAction(e-> searchZone());
        searchChairButton.setOnAction(e-> searchChair());
        searchPurchaseButton.setOnAction(e-> searchPurchase());

        placeZoneList.setOnAction(e-> showZoneList());
        zoneChairList.setOnAction(e-> showChairList());
        showUserButton.setOnAction(e-> showUser());


        backButton.setOnAction(e-> {
            try {
                backMenu(e);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });
        backButton2.setOnAction(e-> {
            try {
                backMenu(e);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });
        backButton3.setOnAction(e-> {
            try {
                backMenu(e);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });
        backButton4.setOnAction(e-> {
            try {
                backMenu(e);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });
        backButton5.setOnAction(e-> {
            try {
                backMenu(e);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });

        loadReportData();
    }

    private void loadReportData() {
        // Ventas por periodo
        XYChart.Series<String, Number> ventasSeries = new XYChart.Series<>();
        ventasSeries.setName("Ventas");
        Map<String, Double> ventasMap = new HashMap<>();
        for (Purchase p : eventManager.getPurchaseList()) {
            String month = p.getDateCreated().getMonth().toString();
            ventasMap.put(month, ventasMap.getOrDefault(month, 0.0) + p.getTotal());
        }
        for (Map.Entry<String, Double> entry : ventasMap.entrySet()) {
            ventasSeries.getData().add(new XYChart.Data<>(entry.getKey(), entry.getValue()));
        }
        ventasPorPeriodoChart.getData().add(ventasSeries);

        // Ocupación por zona
        XYChart.Series<String, Number> ocupacionSeries = new XYChart.Series<>();
        ocupacionSeries.setName("Tickets Vendidos");
        Map<String, Integer> ocupacionMap = new HashMap<>();
        for (Purchase p : eventManager.getPurchaseList()) {
            if (p.getTicketList() != null) {
                for (Ticket t : p.getTicketList()) {
                    if (t.getTheZone() != null) {
                        String zoneName = t.getTheZone().getName();
                        ocupacionMap.put(zoneName, ocupacionMap.getOrDefault(zoneName, 0) + 1);
                    }
                }
            }
        }
        for (Map.Entry<String, Integer> entry : ocupacionMap.entrySet()) {
            ocupacionSeries.getData().add(new XYChart.Data<>(entry.getKey(), entry.getValue()));
        }
        ocupacionPorZonaChart.getData().add(ocupacionSeries);

        // Ingresos por servicios adicionales
        java.util.Map<String, Double> ingresosMap = new java.util.HashMap<>();
        for (Purchase p : eventManager.getPurchaseList()) {
            if (p.getAdditionalServices() != null) {
                for (String service : p.getAdditionalServices()) {
                    double price = 0;
                    if (service.contains("Preferencial")) price = 30000;
                    else if (service.contains("Catering")) price = 50000;
                    else if (service.contains("Merchandising")) price = 20000;
                    else if (service.contains("Insurance")) price = 10000;
                    else if (service.contains("Parking")) price = 15000;
                    else price = 5000;

                    ingresosMap.put(service, ingresosMap.getOrDefault(service, 0.0) + price);
                }
            }
        }
        ObservableList<PieChart.Data> ingresosData = FXCollections.observableArrayList();
        for (Map.Entry<String, Double> entry : ingresosMap.entrySet()) {
            ingresosData.add(new PieChart.Data(entry.getKey(), entry.getValue()));
        }
        ingresosServiciosChart.setData(ingresosData);

        // Tasa de cancelación
        int totalEvents = eventManager.getEventList().size();
        int cancelledEvents = (int) eventManager.getEventList().stream().filter(ev -> ev.getEventStatus() == EventStatus.CANCELLED).count();
        int activeEvents = totalEvents - cancelledEvents;

        ObservableList<PieChart.Data> cancelacionData = FXCollections.observableArrayList(
            new PieChart.Data("Activos", activeEvents),
            new PieChart.Data("Cancelados", cancelledEvents)
        );
        tasaCancelacionChart.setData(cancelacionData);

        // Top eventos
        XYChart.Series<String, Number> topEventosSeries = new XYChart.Series<>();
        topEventosSeries.setName("Tickets Vendidos");
        java.util.Map<String, Integer> eventosMap = new java.util.HashMap<>();
        for (Purchase p : eventManager.getPurchaseList()) {
            if (p.getTicketList() != null) {
                for (Ticket t : p.getTicketList()) {
                    if (t.getTheEvent() != null) {
                        String eventName = t.getTheEvent().getName();
                        eventosMap.put(eventName, eventosMap.getOrDefault(eventName, 0) + 1);
                    }
                }
            }
        }
        eventosMap.entrySet().stream()
            .sorted(java.util.Map.Entry.<String, Integer>comparingByValue().reversed())
            .limit(5)
            .forEach(entry -> topEventosSeries.getData().add(new XYChart.Data<>(entry.getKey(), entry.getValue())));

        topEventosChart.getData().add(topEventosSeries);
    }

    @FXML
    private void createEvent() {
        String id = newEventID.getText();
        String name = newEventName.getText();
        String description = newEventDescription.getText();
        String city = newEventCity.getText();
        Place place = newEventPlace.getValue();
        LocalDate date = newEventDate.getValue();
        LocalTime time = LocalTime.parse(newEventTime.getText());
        EventType et = newEventType.getValue();
        EventPolicy ep = newEventPolicy.getValue();

        Event e = new Event(id,name,description,city,LocalDateTime.of(date,time),place,et,ep);

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setHeaderText("Event Creation");
        alert.setContentText("Are you sure you want to create the event:" + "\n" +
                "ID: " + e.getIdEvent() + "\n" +
                "Name: " + e.getName() + "\n" +
                "Description: " + e.getDescription() + "\n" +
                "Date: " + e.getDate().format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.LONG, FormatStyle.SHORT)) + "\n" +
                "City: " + e.getCity() + "\n" +
                "Place: " + "\n" + e.getThePlace() + "\n" +
                "Type: " + e.getEventType() + "\n" +
                "Status: " + e.getEventStatus() + "\n" +
                "Policy: " + e.getEventPolicy());

        Optional<ButtonType> okButton = alert.showAndWait();

        if(okButton.isPresent() && okButton.get() == ButtonType.OK) {

                boolean duplicated = false;

                for(Event eventFor : EventManager.getInstance().getEventList()) {
                    if(eventFor.getIdEvent().equals(e.getIdEvent())){

                        duplicated = true;

                        Alert error = new Alert(Alert.AlertType.ERROR);
                        error.setTitle("Error");
                        error.setHeaderText("Event ID already exist!");
                        error.setContentText("The ID: " +e.getIdEvent()+ "Is already assigned to other event");
                        error.showAndWait();
                        break;
                    }
                }
                if(!duplicated){
                    adminController.addEvent(e);
                    place.addEvent(e);
                    Alert information = new Alert(Alert.AlertType.INFORMATION);
                    information.setTitle("Information");
                    information.setHeaderText("Succesfull!");
                    information.setContentText("Event has been created correctly");
                    information.showAndWait();
                }
        }
        observableEvents.setAll(eventManager.getEventList());
        eventMenu.refresh();
    }

    @FXML
    private void createPlace() {
        String id = newPlaceID.getText();
        String name = newPlaceName.getText();
        String address = newPlaceAddress.getText();

        Place p = new Place(id, name, address);

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setHeaderText("Place Creation");
        alert.setContentText("Are you sure you want to create the place:" + "\n" +
                "ID: " + p.getIdPlace() + "\n" +
                "Name: " + p.getName() + "\n" +
                "Address: " + p.getAddress() + "\n");

        Optional<ButtonType> okButton = alert.showAndWait();

        if(okButton.isPresent() && okButton.get() == ButtonType.OK) {

            boolean duplicated = false;

            for(Place placeFor : EventManager.getInstance().getPlaceList()) {
                if(placeFor.getIdPlace().equals(p.getIdPlace())){

                    duplicated = true;

                    Alert error = new Alert(Alert.AlertType.ERROR);
                    error.setTitle("Error");
                    error.setHeaderText("Place ID already exist!");
                    error.setContentText("The ID: " +p.getIdPlace()+ "Is already assigned to other place");
                    error.showAndWait();
                    break;
                }
            }
            if(!duplicated){
                adminController.addPlace(p);
                Alert information = new Alert(Alert.AlertType.INFORMATION);
                information.setTitle("Information");
                information.setHeaderText("Succesfull!");
                information.setContentText("Place has been created correctly");
                information.showAndWait();
            }
        }
        observablePlaces.setAll(eventManager.getPlaceList());
        placeMenu.refresh();
    }

    @FXML
    private void createZone(){
        String id = newZoneID.getText();
        String name = newZoneName.getText();
        int capacity = Integer.parseInt(newZoneCapacity.getText());
        double price = Double.parseDouble(newZonePrice.getText());

        Zone newZone = Zone.builder().idZone(id).name(name).capacity(capacity).startingPrice(price).build();

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setHeaderText("Zone Creation");
        alert.setContentText("Are you sure you want to create the zone: " + "\n" +
                "ID: " + newZone.getIdZone() + "\n" +
                "Name: " + newZone.getName() + "\n" +
                "Capacity: " + newZone.getCapacity() + "\n" +
                "Starting Price: " + newZone.getStartingPrice());

        Optional<ButtonType> okButton = alert.showAndWait();
        if(okButton.isPresent() && okButton.get() == ButtonType.OK) {
            boolean duplicated = false;

            for (Composite c : currentPlace.getZoneList()) {
                if (c instanceof Zone) {
                    Zone zone = (Zone) c;
                    if (zone.getIdZone().equals(newZone.getIdZone())) {
                        duplicated = true;

                        Alert error = new Alert(Alert.AlertType.ERROR);
                        error.setTitle("Error");
                        error.setHeaderText("Zone ID already exists!");
                        error.setContentText(
                                "The ID: " + newZone.getIdZone() +
                                        " is already assigned to another zone"
                        );
                        error.showAndWait();
                        break;
                    }
                }
            }

            if(!duplicated){
                ZoneController.addZone(newZone, currentPlace);
                Alert information = new Alert(Alert.AlertType.INFORMATION);
                information.setTitle("Information");
                information.setHeaderText("Succesfull!");
                information.setContentText("Zone has been created correctly in the Place: "+currentPlace.getIdPlace());
                information.showAndWait();
            }
        }
        observableZones.setAll(currentPlace.getZoneList());
        zoneMenu.refresh();
    }

    @FXML
    private void createChair(){
        String id = newChairID.getText();
        int row = Integer.parseInt(newChairRow.getText());
        int number = Integer.parseInt(newChairNumber.getText());
        ChairStatus status = newChairStatus.getValue();

        Chair newChair = new Chair(id, row, number, status, currentZone);
        newChair.setTheZone(currentZone);

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setHeaderText("Chair Creation");
        alert.setContentText("Are you sure you want to create the Chair:" + "\n" +
                "ID: " + newChair.getIdChair() + "\n" +
                "Row: " + newChair.getRow() + "\n" +
                "Number: " + newChair.getNumber() + "\n" +
                "Zone: " + newChair.getTheZone() + "\n" +
                "Chair Status: " + newChair.getChairStatus());

        Optional<ButtonType> button = alert.showAndWait();
        if(button.isPresent() && button.get() == ButtonType.OK) {
            boolean duplicated = false;

            for(Chair c : currentZone.getChairList()){
                if(c.getIdChair().equals(newChair.getIdChair())){
                    duplicated = true;
                    Alert error = new Alert(Alert.AlertType.ERROR);
                    error.setTitle("Error");
                    error.setHeaderText("Chair ID already exists!");
                    error.setContentText(
                            "The ID: " + newChair.getIdChair() +
                                    " is already assigned to another chair"
                    );
                    error.showAndWait();
                    break;
                }
            }
            if(!duplicated) {
                ChairController.addChair(newChair, newChair.getTheZone());
                Alert information = new Alert(Alert.AlertType.INFORMATION);
                information.setTitle("Information");
                information.setHeaderText("Succesfull!");
                information.setContentText("Chair has been created correctly");
                information.showAndWait();
            }
        }
        observableChairs.setAll(currentZone.getChairList());
        chairMenu.refresh();
    }

    @FXML
    private void searchEvent(){
        String id = newEventID.getText();
        Event eventSearched = adminController.searchEventById(id);

        if(eventSearched != null){
            Alert information = new Alert(Alert.AlertType.INFORMATION);
            information.setTitle("Information");
            information.setHeaderText("Event found!");
            information.setContentText("Displaying event information: " + "\n" +
                    "ID: " + eventSearched.getIdEvent() + "\n" +
                    "Name: " + eventSearched.getName() + "\n" +
                    "Description: " + eventSearched.getDescription() + "\n" +
                    "Date: " + eventSearched.getDate().format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.LONG, FormatStyle.SHORT)) + "\n" +
                    "City: " + eventSearched.getCity() + "\n" +
                    "Place: " + "\n" + eventSearched.getThePlace() + "\n" +
                    "Type: " + eventSearched.getEventType() + "\n" +
                    "Status: " + eventSearched.getEventStatus() + "\n" +
                    "Policty: " + eventSearched.getEventPolicy());
            information.show();
        }
        else{
            Alert error = new Alert(Alert.AlertType.ERROR);
            error.setTitle("Error");
            error.setHeaderText("Event not found!");
            error.setContentText("The ID: " +id+ " It does not match any event on the list");
            error.show();
        }
    }

    @FXML
    private void searchPlace(){
        String id = newPlaceID.getText();
        Place placeSearched = adminController.searchPlaceById(id);

        if(placeSearched != null){
            Alert information = new Alert(Alert.AlertType.INFORMATION);
            information.setTitle("Information");
            information.setHeaderText("Place found!");
            information.setContentText("Displaying place information: " + "\n" +
                    "ID: " + placeSearched.getIdPlace() + "\n" +
                    "Name: " + placeSearched.getName() + "\n" +
                    "Address: " + placeSearched.getAddress() + "\n" +
                    "Events: " + placeSearched.getEventList() +"\n" +
                    "Zones: " + placeSearched.getZoneList());
            information.show();
        }
        else{
            Alert error = new Alert(Alert.AlertType.ERROR);
            error.setTitle("Error");
            error.setHeaderText("Place not found!");
            error.setContentText("The ID: " +id+ " It does not match any place on the list");
            error.show();
        }
    }

    @FXML
    private void searchZone(){
        String id = newZoneID.getText();
        Zone searchedZone = adminController.searchZoneById(id, currentPlace);

        if(searchedZone != null){
            Alert information = new Alert(Alert.AlertType.INFORMATION);
            information.setTitle("Information");
            information.setHeaderText("Zone found!");
            information.setContentText("Displaying zone information: " + "\n" +
                    "ID: " + searchedZone.getIdZone() + "\n" +
                    "Name: " + searchedZone.getName() + "\n" +
                    "Capacity: " + searchedZone.getCapacity() + "\n" +
                    "Starting Price: " + searchedZone.getStartingPrice() +"\n" +
                    "Chair list: " + searchedZone.getChairList());
            information.show();
        }
        else{
            Alert error = new Alert(Alert.AlertType.ERROR);
            error.setTitle("Error");
            error.setHeaderText("Zone not found!");
            error.setContentText("The ID: " +id+ " It does not match any zone on the place list");
            error.show();
        }
    }

    @FXML
    private void searchChair(){
        String id = newChairID.getText();
        Chair searchedChair = ChairController.findChairById(id, currentZone);

        if(searchedChair != null){
            Alert information = new Alert(Alert.AlertType.INFORMATION);
            information.setTitle("Information");
            information.setHeaderText("Chair found!");
            information.setContentText("Displaying chair information: " + "\n" +
                    "ID: " + searchedChair.getIdChair() + "\n" +
                    "Row: " + searchedChair.getRow() + "\n" +
                    "Number: " + searchedChair.getNumber() + "\n" +
                    "Zone: " + searchedChair.getTheZone() + "\n" +
                    "Chair Status: " + searchedChair.getChairStatus());
            information.show();
        }
        else{
            Alert error = new Alert(Alert.AlertType.ERROR);
            error.setTitle("Error");
            error.setHeaderText("Chair not found!");
            error.setContentText("The ID: " +id+ " It does not match any chair on the zone list");
            error.show();
        }
    }

    @FXML
    private void searchPurchase(){
        String id = newPurchaseID.getText();
        Purchase searchedPurchase = PurchaseController.searchPurchaseById(id);

        if(searchedPurchase != null){
            Alert information = new Alert(Alert.AlertType.INFORMATION);

            information.setTitle("Information");
            information.setHeaderText("Purchase Found!");
            information.setContentText(
                    "Displaying purchase information: " + "\n" +
                            "ID: " + searchedPurchase.getIdPurchase() + "\n" +
                            "Date: " + searchedPurchase.getDateCreated().format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.LONG,FormatStyle.SHORT)) + "\n" +
                            "Total: " + searchedPurchase.getTotal() + "\n" +
                            "Tickets: " + searchedPurchase.getTicketList() + "\n" +
                            "User: " + searchedPurchase.getTheUser() + "\n"+
                            "Payment Type: "+ searchedPurchase.getPaymentType() + "\n" +
                            "Purchase Status: "+ searchedPurchase.getPurchaseStatus());
            information.show();
        }
        else{
            Alert error = new Alert(Alert.AlertType.ERROR);
            error.setTitle("Error");
            error.setHeaderText("Purchase not found!");
            error.setContentText("The ID: " +id+ " It does not match any purchase on the list");
            error.show();
        }
    }

    @FXML
    private void backMenu(ActionEvent actionEvent) throws IOException {
        Button button = (Button) actionEvent.getSource();

        Stage stage = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader(Application.class.getResource("mainMenu.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setResizable(false);
        stage.setTitle("Main Menu");
        stage.setScene(scene);
        stage.show();
        Stage currentStage = (Stage) button.getScene().getWindow();
        currentStage.close();
    }

    @FXML
    private void showZoneList() {
        String idPlace = newPlaceID.getText();

        if (idPlace == null || idPlace.trim().isEmpty()) {
            Alert error = new Alert(Alert.AlertType.ERROR);
            error.setTitle("Error");
            error.setHeaderText("Invalid ID!");
            error.setContentText("Please enter a valid Place ID.");
            error.showAndWait();
            return;
        }

        boolean found = false;
        for (Place place : eventManager.getPlaceList()) {
            if (place.getIdPlace().equals(idPlace)) {
                found = true;
                Alert info = new Alert(Alert.AlertType.INFORMATION);
                info.setTitle("Info");
                info.setHeaderText(
                        "The following Place: " +
                                place.getIdPlace() +
                                " has the next zones."
                );
                info.setContentText(
                        "Zone list: " + place.getZoneList()
                );
                info.showAndWait();
                break;
            }
        }
        if (!found) {
            Alert error = new Alert(Alert.AlertType.ERROR);
            error.setTitle("Error");
            error.setHeaderText("Place ID not found!");
            error.setContentText(
                    "Make sure to specify a valid or existent ID"
            );
            error.showAndWait();
        }
    }

    @FXML
    private void showUser(){
        if(currentUser!=null){
            Alert info = new Alert(Alert.AlertType.INFORMATION);
            info.setTitle("Info");
            info.setHeaderText("Showing User: "+currentUser.getId());
            info.setContentText(
                    "ID: " +currentUser.getId() +"\n" +
                            "Name: "+currentUser.getFullName() +"\n" +
                            "Number: "+currentUser.getPhoneNumber() +"\n" +
                            "Phone: "+currentUser.getPhoneNumber() +"\n" +
                            "Purchase list: "+currentUser.getPurchaseList());
        }
        Alert error = new Alert(Alert.AlertType.ERROR);
        error.setTitle("Error");
        error.setHeaderText("User not found!");
        error.setContentText(
                "Make sure to specify a valid or existent User"
        );
        error.showAndWait();
    }

    @FXML
    private void showChairList(){
        String idZone = newZoneID.getText();

        if (idZone == null || idZone.trim().isEmpty()) {
            Alert error = new Alert(Alert.AlertType.ERROR);
            error.setTitle("Error");
            error.setHeaderText("Invalid ID!");
            error.setContentText("Please enter a valid Zone ID.");
            error.showAndWait();
            return;
        }

        boolean found = false;
        for (Composite c : currentPlace.getZoneList()) {
                if(c instanceof Zone) {

                    Zone zone1 =  (Zone) c;

                    if(zone1.getIdZone().equals(idZone)) {
                        found = true;
                        Alert info = new Alert(Alert.AlertType.INFORMATION);
                        info.setTitle("Info");
                        info.setHeaderText(
                                "The following zone: " +
                                        ((Zone) c).getIdZone() +
                                        " has the next chairs."
                        );
                        info.setContentText(
                                "Chair list: " + ((Zone) c).getChairList()
                        );
                        info.showAndWait();
                        break;
                    }
                }
        }
        if (!found) {
            Alert error = new Alert(Alert.AlertType.ERROR);
            error.setTitle("Error");
            error.setHeaderText("Zone ID not found!");
            error.setContentText(
                    "Make sure to specify a valid or existent ID"
            );
            error.showAndWait();
        }
    }
}

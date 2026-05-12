package co.edu.uniquindio.eventmanager.viewController;

import co.edu.uniquindio.eventmanager.Application;
import co.edu.uniquindio.eventmanager.controller.AdminController;
import co.edu.uniquindio.eventmanager.model.Enums.EventPolicy;
import co.edu.uniquindio.eventmanager.model.Enums.EventType;
import co.edu.uniquindio.eventmanager.model.Event;
import co.edu.uniquindio.eventmanager.model.EventManager;
import co.edu.uniquindio.eventmanager.model.Place;
import co.edu.uniquindio.eventmanager.viewController.modifyView.EventModify;
import co.edu.uniquindio.eventmanager.viewController.modifyView.PlaceModify;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
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
import java.util.Optional;

public class AdminView {

    @FXML
    private TextField newEventID, newEventName, newEventDescription, newEventCity, newEventTime,
                      newPlaceID, newPlaceName, newPlaceAddress;

    @FXML
    private ComboBox<Place> newEventPlace;

    @FXML
    private ComboBox<EventType>  newEventType;

    @FXML
    private ComboBox<EventPolicy> newEventPolicy;

    @FXML
    private DatePicker newEventDate;

    @FXML
    private TableView eventMenu, placeMenu;

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
    private Button addEventButton, searchEventButton, backButton,
                   placeZoneList, addPlaceButton, searchPlaceButton, backButton2;

    private AdminController adminController = new AdminController();

    private EventManager eventManager = EventManager.getInstance();

    ObservableList<Event> observableEvents = FXCollections.observableArrayList(eventManager.getEventList());
    ObservableList<Place> observablePlaces = FXCollections.observableArrayList(eventManager.getPlaceList());

    @FXML
    private void initialize(){

        newEventPlace.setItems(observablePlaces);
        newEventType.getItems().addAll(EventType.values());
        newEventPolicy.getItems().addAll(EventPolicy.values());

        eventID.setCellValueFactory(new PropertyValueFactory<>("idEvent"));
        eventName.setCellValueFactory(new PropertyValueFactory<>("name"));
        eventDate.setCellValueFactory(new PropertyValueFactory<>("date"));

        placeID.setCellValueFactory(new PropertyValueFactory<>("idPlace"));
        placeName.setCellValueFactory(new PropertyValueFactory<>("name"));
        placeAddress.setCellValueFactory(new PropertyValueFactory<>("address"));

        eventID.setReorderable(false);placeID.setReorderable(false);
        eventName.setReorderable(false);placeName.setReorderable(false);
        eventDate.setReorderable(false);placeAddress.setReorderable(false);

        eventID.setStyle("-fx-alignment: CENTER;");placeID.setStyle("-fx-alignment: CENTER;");
        eventName.setStyle("-fx-alignment: CENTER;");placeName.setStyle("-fx-alignment: CENTER;");
        eventDate.setStyle("-fx-alignment: CENTER;");placeAddress.setStyle("-fx-alignment: CENTER;");

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
                    stage.setTitle("Modificar Evento");
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
                information.setHeaderText(event.getName());
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
                    stage.setTitle("Modificar Evento");
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
                information.setHeaderText(place.getName());
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

        addEventButton.setOnAction(e -> createEvent());
        addPlaceButton.setOnAction(e -> createPlace());

        searchEventButton.setOnAction(e -> searchEvent());
        searchPlaceButton.setOnAction(e -> searchPlace());
        placeZoneList.setOnAction(e-> showZoneList());

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
}

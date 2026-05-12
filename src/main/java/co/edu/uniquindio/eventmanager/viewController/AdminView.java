package co.edu.uniquindio.eventmanager.viewController;

import co.edu.uniquindio.eventmanager.Application;
import co.edu.uniquindio.eventmanager.controller.AdminController;
import co.edu.uniquindio.eventmanager.model.Enums.EventPolicy;
import co.edu.uniquindio.eventmanager.model.Enums.EventStatus;
import co.edu.uniquindio.eventmanager.model.Enums.EventType;
import co.edu.uniquindio.eventmanager.model.Event;
import co.edu.uniquindio.eventmanager.model.EventManager;
import co.edu.uniquindio.eventmanager.model.Place;
import co.edu.uniquindio.eventmanager.model.Zone;
import co.edu.uniquindio.eventmanager.viewController.modifyView.EventModify;
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
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Optional;
import java.util.ResourceBundle;

public class AdminView {

    @FXML
    private TextField newEventID, newEventName, newEventDescription, newEventCity, newEventTime,
                      newZoneID, newZoneName, newZonePrice, newZoneCapacity;

    @FXML
    private ComboBox<Place> newEventPlace;

    @FXML
    private ComboBox<EventType>  newEventType;

    @FXML
    private ComboBox<EventPolicy> newEventPolicy;

    @FXML
    private DatePicker newEventDate;

    @FXML
    private TableView eventMenu, zoneMenu;

    @FXML
    private TableColumn<Event, String> eventID;
    @FXML
    private TableColumn<Event, String> eventName;
    @FXML
    private TableColumn<Event, LocalDateTime> eventDate;

    @FXML
    private TableColumn<Zone, String> zoneID;
    @FXML
    private TableColumn<Zone, String> zoneName;
    @FXML
    private TableColumn<Zone, Integer> zoneCapacity;

    @FXML
    private Button addEventButton, searchEventButton, backButton,
                   addZoneButton, searchZoneButton, zoneChairList, zoneAddChair, backButton2;

    private AdminController adminController = new AdminController();

    private EventManager eventManager = EventManager.getInstance();

    ObservableList<Event> observableEvents = FXCollections.observableArrayList(eventManager.getEventList());

    @FXML
    private void initialize(){

        newEventPlace.getItems().addAll(EventManager.getInstance().getPlaceList());
        newEventType.getItems().addAll(EventType.values());
        newEventPolicy.getItems().addAll(EventPolicy.values());

        System.out.println("Initializing...");
        System.out.println(eventManager.getEventList().size());
        System.out.println(observableEvents.size());

        eventID.setCellValueFactory(new PropertyValueFactory<>("idEvent"));

        eventName.setCellValueFactory(new PropertyValueFactory<>("name"));

        eventDate.setCellValueFactory(new PropertyValueFactory<>("date"));

        eventID.setReorderable(false);
        eventName.setReorderable(false);
        eventDate.setReorderable(false);

        eventID.setStyle("-fx-alignment: CENTER;");
        eventName.setStyle("-fx-alignment: CENTER;");
        eventDate.setStyle("-fx-alignment: CENTER;");

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

        addEventButton.setOnAction(e -> create());
        searchEventButton.setOnAction(e -> searchEvent());
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
    private void create() {
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
                    Alert information = new Alert(Alert.AlertType.INFORMATION);
                    information.setTitle("Information");
                    information.setHeaderText("Succesfull!");
                    information.setContentText("Event has been modified correctly");
                    information.showAndWait();
                }
        }
        observableEvents.setAll(eventManager.getEventList());
        eventMenu.refresh();
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
}

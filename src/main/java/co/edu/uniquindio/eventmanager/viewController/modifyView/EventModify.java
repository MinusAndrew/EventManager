package co.edu.uniquindio.eventmanager.viewController.modifyView;

import co.edu.uniquindio.eventmanager.controller.AdminController;
import co.edu.uniquindio.eventmanager.model.Enums.EventPolicy;
import co.edu.uniquindio.eventmanager.model.Enums.EventStatus;
import co.edu.uniquindio.eventmanager.model.Enums.EventType;
import co.edu.uniquindio.eventmanager.model.Event;
import co.edu.uniquindio.eventmanager.model.EventManager;
import co.edu.uniquindio.eventmanager.model.Place;
import co.edu.uniquindio.eventmanager.model.Proxy;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Optional;

public class EventModify {

    @FXML
    private Text description, place, currentEvent, eventModified,
            eventName, eventCity, eventDate, eventType, eventStatus;

    @FXML
    private Label eventDescription, eventPlace;

    @FXML
    private TextField newName, newDescription, newCity, newTime;

    @FXML
    private ComboBox<Place> newPlace;

    @FXML
    private ComboBox<EventType>  newType;

    @FXML
    private ComboBox<EventStatus> newStatus;

    @FXML
    private DatePicker newDate;

    @FXML
    private Button modifyButton;

    @FXML
    private Line line1, line2;

    private AdminController adminController = new AdminController();

    private Event event;

    public void setEvent(Event event) {

        this.event = event;

        Tooltip tooltip1 = new Tooltip(event.getDescription());
        Tooltip tooltip2 = new Tooltip(String.valueOf(event.getThePlace()));

        currentEvent.setText("Current Event:  "+event.getIdEvent());
        eventName.setText("Name:  "+event.getName());
        eventDescription.setTooltip(tooltip1);
        eventCity.setText("City:  "+event.getName());
        eventPlace.setTooltip(tooltip2);
        eventDate.setText("Date:  "+event.getDate().format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.LONG,FormatStyle.SHORT)));
        eventType.setText("Type:  "+ event.getEventType());
        eventStatus.setText("Status:  "+ event.getEventStatus());

        newPlace.getItems().addAll(EventManager.getInstance().getPlaceList());
        newStatus.getItems().addAll(EventStatus.values());
        newType.getItems().addAll(EventType.values());

        modifyButton.setOnAction(e -> save());
    }

    @FXML
    private void save() {
        String id = event.getIdEvent();
        EventPolicy ep = event.getEventPolicy();

        String name = newName.getText();
        String description = newDescription.getText();
        String city = newCity.getText();
        Place place = newPlace.getValue();
        LocalDate date = newDate.getValue();
        LocalTime time = LocalTime.parse(newTime.getText());
        EventType et = newType.getValue();
        EventStatus es = newStatus.getValue();

        Event e = new Event(id,name,description,city,LocalDateTime.of(date,time),place,et,ep);
        e.setEventStatus(es);

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setHeaderText("Event Modification");
        alert.setContentText("Are you sure you want to apply this changes? :" + "\n" +
                "ID: " + e.getIdEvent() + "\n" +
                "Name: " + e.getName() + "\n" +
                "Description: " + e.getDescription() + "\n" +
                "Date: " + e.getDate().format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.LONG, FormatStyle.SHORT)) + "\n" +
                "City: " + e.getCity() + "\n" +
                "Place: " + "\n" + e.getThePlace() + "\n" +
                "Type: " + e.getEventType() + "\n" +
                "Status: " + e.getEventStatus());

        Optional<ButtonType> button = alert.showAndWait();

        if(button.isPresent() && button.get() == ButtonType.OK) {
            adminController.updateEvent(e);
            Alert information = new Alert(Alert.AlertType.INFORMATION);
            information.setTitle("Information");
            information.setHeaderText("Succesfull!");
            information.setContentText("Event has been modified correctly");
            information.showAndWait();
        }
        Stage stage = (Stage) modifyButton.getScene().getWindow();
        stage.close();
    }
}


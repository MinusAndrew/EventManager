package co.edu.uniquindio.eventmanager.viewController.modifyView;

import co.edu.uniquindio.eventmanager.model.Enums.EventPolicy;
import co.edu.uniquindio.eventmanager.model.Enums.EventStatus;
import co.edu.uniquindio.eventmanager.model.Enums.EventType;
import co.edu.uniquindio.eventmanager.model.Event;
import co.edu.uniquindio.eventmanager.model.EventManager;
import co.edu.uniquindio.eventmanager.model.Place;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;

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

        Event e = new Event(id,name,description,city, LocalDateTime.of(date,time),place,et,es,ep);


    }
}


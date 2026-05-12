package co.edu.uniquindio.eventmanager.viewController.modifyView;

import co.edu.uniquindio.eventmanager.controller.AdminController;
import co.edu.uniquindio.eventmanager.controller.PlaceController;
import co.edu.uniquindio.eventmanager.model.Composite;
import co.edu.uniquindio.eventmanager.model.Event;
import co.edu.uniquindio.eventmanager.model.Place;
import co.edu.uniquindio.eventmanager.model.Zone;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.Optional;

public class PlaceModify {

    @FXML
    private Text currentPlace, placeModified, placeName, placeAddress;

    @FXML
    private TextField newName, newAddress, theEvent, theZone;

    @FXML
    private Button placeEventList, placeZoneList, deleteEvent, deleteZone, modifyButton;

    @FXML
    private Line line1, line2;

    private AdminController ac = new AdminController();

    private Place place;

    public void setPlace(Place place){
        this.place = place;

        currentPlace.setText("Current place: "+place.getIdPlace());
        placeName.setText("Name: "+place.getName());
        placeAddress.setText("Address: "+place.getAddress());

        placeEventList.setOnAction(e-> getEventList(place));
        placeZoneList.setOnAction(e-> getZoneList(place));
        deleteEvent.setOnAction(e-> placeDeleteEvent(place));
        deleteZone.setOnAction(e-> placeDeleteZone(place));
        modifyButton.setOnAction(e-> save());

    }

    @FXML
    private void getEventList(Place place){
        Alert info = new Alert(Alert.AlertType.INFORMATION);
        info.setTitle("Info");
        info.setHeaderText("The following Place: "+place.getIdPlace()+ "Has the next events");
        info.setContentText("Events list:  "+place.getEventList());
        info.showAndWait();
    }

    @FXML
    private void getZoneList(Place place){
        Alert info = new Alert(Alert.AlertType.INFORMATION);
        info.setTitle("Info");
        info.setHeaderText("The following Place: "+place.getIdPlace() +"Has the next zones.");
        info.setContentText("Zone list:  "+place.getZoneList());
        info.showAndWait();

    }

    @FXML
    private void placeDeleteEvent(Place place){
        Event event2 = findEvent();

        if(event2!=null) {
            place.removeEvent(theEvent.getText());
            Alert info = new Alert(Alert.AlertType.INFORMATION);
            info.setTitle("Information");
            info.setHeaderText("Succesfull!");
            info.setContentText("The Event: " + theEvent.getText() + " has been deleted correctly");
            info.showAndWait();
        }
    }

    @FXML
    private void placeDeleteZone(Place place) {
            Zone zone = findZone();
            if(zone!=null){
            place.removeZone(zone);
            Alert info = new Alert(Alert.AlertType.INFORMATION);
            info.setTitle("Information");
            info.setHeaderText("Succesfull!");
            info.setContentText("The Zone: " + zone.getIdZone() + " has been deleted correctly");
            info.showAndWait();
        }
    }

    @FXML
    private void save(){
        String id = place.getIdPlace();

        String name = newName.getText();
        String address = newAddress.getText();

        Place newPlace = new Place(id, name, address);
        newPlace.setEventList(place.getEventList());
        newPlace.setZoneList(place.getZoneList());

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setHeaderText("Place Modification");
        alert.setContentText("Are you sure you want to apply this changes? :" + "\n" +
                "ID: " + newPlace.getIdPlace() + "\n" +
                "Name: " + newPlace.getName() + "\n" +
                "Description: " + newPlace.getAddress() + "\n" +
                "Events: " +newPlace.getEventList() + "\n"+
                "Zones:  " +newPlace.getZoneList());

        Optional<ButtonType> button = alert.showAndWait();

        if(button.isPresent() && button.get() == ButtonType.OK) {
            PlaceController.updatePlace(newPlace);
            Alert information = new Alert(Alert.AlertType.INFORMATION);
            information.setTitle("Information");
            information.setHeaderText("Succesfull!");
            information.setContentText("Place has been modified correctly");
        }
        Stage stage = (Stage) modifyButton.getScene().getWindow();
        stage.close();
    }

    private Zone findZone(){
        String zoneID = theZone.getText();

        for(Composite c : place.getZoneList()) {
            if (c instanceof Zone zone){
                if(zone.getIdZone().equals(zoneID)){
                    return zone;
                }
            }
        }
        return null;
    }

    private Event findEvent(){
        String eventID = theEvent.getText();

        for(Event e : place.getEventList()) {
            if(e.getIdEvent().equals(eventID)){
                return e;
            }
        }
        return null;
    }

}

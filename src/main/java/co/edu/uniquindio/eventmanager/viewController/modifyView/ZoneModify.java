package co.edu.uniquindio.eventmanager.viewController.modifyView;

import co.edu.uniquindio.eventmanager.controller.PlaceController;
import co.edu.uniquindio.eventmanager.controller.ZoneController;
import co.edu.uniquindio.eventmanager.model.*;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.Optional;

public class ZoneModify {

    @FXML
    private Text currentZone, zoneModified, zoneName, zoneCapacity, zonePrice;

    @FXML
    private TextField newName, newCapacity, newPrice, theChair;

    @FXML
    private Button zoneChairList, deleteChair, modifyButton;

    @FXML
    private Line line1, line2;

    private Zone zone;

    public void setZone(Zone zone){
        this.zone = zone;

        currentZone.setText("Current place: "+zone.getIdZone());
        zoneName.setText("Name: "+zone.getName());
        zoneCapacity.setText("Capacity: "+zone.getCapacity());
        zonePrice.setText("Starting price: "+zone.getStartingPrice());

        zoneChairList.setOnAction(e-> getZoneList(zone));
        deleteChair.setOnAction(e-> zoneDeleteChair(zone));
        modifyButton.setOnAction(e-> save());
    }

    @FXML
    private void getZoneList(Zone zone){
        Alert info = new Alert(Alert.AlertType.INFORMATION);
        info.setTitle("Info");
        info.setHeaderText("The following Zone: "+zone.getIdZone()+ "Has the next chairs");
        info.setContentText("Chairs list:  "+zone.getChairList());
        info.showAndWait();
    }

    @FXML
    private void zoneDeleteChair(Zone z) {
        Chair chair = findChair();
        if(chair!=null){
            zone.removeChair(chair);
            Alert info = new Alert(Alert.AlertType.INFORMATION);
            info.setTitle("Information");
            info.setHeaderText("Succesfull!");
            info.setContentText("The Chair: " + chair.getIdChair() + " has been deleted correctly");
            info.showAndWait();
        }
    }

    @FXML
    private void save(){
        String id = zone.getIdZone();

        String name = newName.getText();
        int capacity = Integer.parseInt(newCapacity.getText());
        int price = Integer.parseInt(newPrice.getText());

        Zone newZone = new Zone(id, name, capacity,price);
        newZone.setChairList(zone.getChairList());

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setHeaderText("Zone Modification");
        alert.setContentText("Are you sure you want to apply this changes? :" + "\n" +
                "ID: " + newZone.getIdZone() + "\n" +
                "Name: " + newZone.getName() + "\n" +
                "Capacity: " + newZone.getCapacity() + "\n" +
                "Starting price: " +newZone.getStartingPrice() + "\n"+
                "Chair list:  " +newZone.getChairList());

        Optional<ButtonType> button = alert.showAndWait();

        if(button.isPresent() && button.get() == ButtonType.OK) {
            Place place = findPlaceByZone(newZone);

            if (place != null) {
                ZoneController.updateZone(newZone, place);
                Alert information = new Alert(Alert.AlertType.INFORMATION);
                information.setTitle("Information");
                information.setHeaderText("Succesfull!");
                information.setContentText("Place has been modified correctly");
                information.showAndWait();
            }
        }
        Stage stage = (Stage) modifyButton.getScene().getWindow();
        stage.close();
    }

    private Chair findChair() {
        String chairID = theChair.getText();

        for (Chair chair1 : zone.getChairList()) {
                if (chair1.getIdChair().equals(chairID)) {
                    return chair1;
                    }
        }
        return null;
    }

    private Place findPlaceByZone(Zone targetZone) {
        for (Place place : EventManager.getInstance().getPlaceList()) {
            for (Composite c : place.getZoneList()) {
                if (c instanceof Zone zone) {
                    if (zone.getIdZone()
                            .equals(targetZone.getIdZone())) {
                        return place;
                    }
                }
            }
        }
        return null;
    }
}

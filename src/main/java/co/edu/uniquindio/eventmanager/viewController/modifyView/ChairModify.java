package co.edu.uniquindio.eventmanager.viewController.modifyView;

import co.edu.uniquindio.eventmanager.controller.ChairController;
import co.edu.uniquindio.eventmanager.controller.PlaceController;
import co.edu.uniquindio.eventmanager.model.Chair;
import co.edu.uniquindio.eventmanager.model.Enums.ChairStatus;
import co.edu.uniquindio.eventmanager.model.Zone;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.Optional;

public class ChairModify {

    @FXML
    private Text currentChair, chairModified, chairRow, chairNumber, chairPlace, chairStatus;

    @FXML
    private Label chairShowZone;

    @FXML
    private TextField newRow, newNumber;

    @FXML
    private ComboBox<ChairStatus> newChairStatus;

    @FXML
    private Button modifyButton;

    @FXML
    private Line line1, line2;

    private Chair chair;

    public void setChair(Chair chair){
        this.chair = chair;

        Tooltip tooltip1 = new Tooltip(String.valueOf(chair.getTheZone()));

        currentChair.setText("Current chair: "+chair.getIdChair());
        chairRow.setText("Row: "+chair.getRow());
        chairNumber.setText("Number: "+chair.getNumber());
        chairStatus.setText("Status: "+chair.getChairStatus());
        chairShowZone.setTooltip(tooltip1);
        newChairStatus.getItems().addAll(ChairStatus.values());

        modifyButton.setOnAction(e-> save());
    }

    @FXML
    private void save(){
        String id = chair.getIdChair();
        int row = Integer.parseInt(newRow.getText());
        int number = Integer.parseInt(newNumber.getText());
        ChairStatus status = newChairStatus.getValue();

        Chair newChair = new Chair(id, row, number, status);
        newChair.setTheZone(chair.getTheZone());

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setHeaderText("Chair Modification");
        alert.setContentText("Are you sure you want to apply this changes? :" + "\n" +
                "ID: " + newChair.getIdChair() + "\n" +
                "Row: " + newChair.getRow() + "\n" +
                "Number: " + newChair.getNumber() + "\n" +
                "Zone: " + newChair.getTheZone() + "\n" +
                "Chair Status: " + newChair.getChairStatus());

        Optional<ButtonType> button = alert.showAndWait();
        if(button.isPresent() && button.get() == ButtonType.OK) {

            ChairController.updateChair(newChair,newChair.getTheZone());
            Alert information = new Alert(Alert.AlertType.INFORMATION);
            information.setTitle("Information");
            information.setHeaderText("Succesfull!");
            information.setContentText("Chair has been modified correctly");
            information.showAndWait();
        }
        Stage stage = (Stage) modifyButton.getScene().getWindow();
        stage.close();
    }
}

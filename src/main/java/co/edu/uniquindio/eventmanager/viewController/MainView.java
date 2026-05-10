package co.edu.uniquindio.eventmanager.viewController;

import co.edu.uniquindio.eventmanager.Application;
import co.edu.uniquindio.eventmanager.controller.UserController;
import co.edu.uniquindio.eventmanager.model.Enums.EventStatus;
import co.edu.uniquindio.eventmanager.model.Enums.EventType;
import co.edu.uniquindio.eventmanager.model.Event;
import co.edu.uniquindio.eventmanager.model.EventManager;
import co.edu.uniquindio.eventmanager.model.Place;
import co.edu.uniquindio.eventmanager.model.User;
import com.sun.tools.javac.Main;
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
import lombok.Setter;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
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
        userDataText.setText(EventManager.getInstance().getCurrentUser().toString());
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        cityColumn.setCellValueFactory(new PropertyValueFactory<>("city"));
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));
        placeColumn.setCellValueFactory(new PropertyValueFactory<>("thePlace"));
        typeColumn.setCellValueFactory(new PropertyValueFactory<>("eventType"));
        statusColumn.setCellValueFactory(new PropertyValueFactory<>("eventStatus"));
        typeChoiceBox.getItems().addAll(EventType.values());
        statusChoiceBox.getItems().addAll(EventStatus.values());
    }

    @FXML
    public void fillUpEventList(){
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
}

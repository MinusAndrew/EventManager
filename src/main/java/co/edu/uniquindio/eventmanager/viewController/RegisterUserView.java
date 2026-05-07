package co.edu.uniquindio.eventmanager.viewController;

import co.edu.uniquindio.eventmanager.controller.UserController;
import co.edu.uniquindio.eventmanager.model.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import static co.edu.uniquindio.eventmanager.viewController.MainView.switchMenu;

public class RegisterUserView {

    @FXML
    private Button registerButton;
    @FXML
    private TextField nameField;
    @FXML
    private TextField emailField;
    @FXML
    private TextField personalIdField;
    @FXML
    private TextField phoneNumberField;
    @FXML
    private TextField ageField;
    @FXML
    private PasswordField trainerPasswdField;

    @FXML
    private AnchorPane scenePane;

    Stage currentWindow;
    //backbuttonplz

    @FXML
    private void backAction(ActionEvent event){
        switchMenu(event, "loginMenu.fxml");
    }

    @FXML
    private void registerAction(ActionEvent event){

        Alert errorAlert = new Alert(Alert.AlertType.ERROR);
        errorAlert.setContentText("No se ha registrado el usuario, \nverifique que los datos dados sean correctos");

        currentWindow = (Stage) scenePane.getScene().getWindow();
        try {
            String name = grabName();
            String email = grabEmail();
            String phoneNumber = grabPhoneNum();
            String passwd = grabPasswd();

            User newUser;
            newUser = new User(name, email, phoneNumber, passwd);
            boolean check = UserController.addUser(newUser);

            if (!check){
                errorAlert.showAndWait();
                return;
            }

            Alert succsessAlert = new Alert(Alert.AlertType.INFORMATION);
            succsessAlert.setContentText("El Usuario se registró correctamente.");
            succsessAlert.showAndWait();
            switchMenu(event, "loginMenu.fxml");
        }
        catch (Throwable e){
            errorAlert.show();
        }
    }

    @FXML
    private String grabName(){
        return nameField.getText();
    }
    @FXML
    private String grabEmail(){
        return emailField.getText();
    }
    @FXML
    private String grabPhoneNum(){
        return phoneNumberField.getText();
    }
    @FXML
    private String grabPasswd(){
        return trainerPasswdField.getText();
    }
}

package co.edu.uniquindio.eventmanager.viewController;

import co.edu.uniquindio.eventmanager.Application;
import co.edu.uniquindio.eventmanager.controller.AdminController;
import co.edu.uniquindio.eventmanager.model.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lombok.Getter;

import java.io.IOException;

import static co.edu.uniquindio.eventmanager.viewController.MainView.switchMenu;

public class LoginView {
    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwdField;

    @FXML
    private AnchorPane loginPane;

    Stage currentWindow;

    @FXML
    private void registerAction(ActionEvent event) {
        switchMenu(event, "registerUserMenu.fxml");
    }

    public void loginFX() throws IOException {

        currentWindow = (Stage) loginPane.getScene().getWindow();

        String email = grabUser();
        String passwd = grabPasswd();

        Alert errorAlert = new Alert(Alert.AlertType.ERROR);
        errorAlert.setContentText("No se ha ingresado, \nverifique que los datos dados sean correctos");
        if (EventManager.getInstance().login(email, passwd)){
            Stage stage = new Stage();
            //temp var make proxy later
            AdminController adminController = new AdminController();
            ServiceProxy proxy = new Proxy(adminController,EventManager.getInstance().getCurrentUser());
            //
            boolean b = proxy.checkLoginAccess();

            if (!b) {//Change to mainMenu
                FXMLLoader fxmlLoader = new FXMLLoader(Application.class.getResource("mainMenu.fxml"));
                Scene scene = new Scene(fxmlLoader.load());
                stage.setResizable(false);
                stage.setTitle("Main Menu");
                stage.setScene(scene);
                stage.show();
            } else if (b) {
                FXMLLoader fxmlLoader2 = new FXMLLoader(Application.class.getResource("adminMenu.fxml"));
                Scene scene2 = new Scene(fxmlLoader2.load());
                stage.setResizable(false);
                stage.setTitle("Admin Menu");
                stage.setScene(scene2);
                stage.show();
            }
            currentWindow.close();
        }
        else {
            errorAlert.show();
        }

    }
    public static void createLoginMenu() throws IOException {
        Stage stage = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader(Application.class.getResource("loginMenu.fxml"));
        Scene scene1 = new Scene(fxmlLoader.load());
        LoginView loginView = fxmlLoader.getController();
        stage.setResizable(false);
        stage.setTitle("Main Menu");
        stage.setScene(scene1);
        stage.show();
    }

    public String grabUser(){
        return usernameField.getText();
    }

    public String grabPasswd(){
        return passwdField.getText();
    }
}

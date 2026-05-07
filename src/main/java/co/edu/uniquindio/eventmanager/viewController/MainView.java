package co.edu.uniquindio.eventmanager.viewController;

import co.edu.uniquindio.eventmanager.Application;
import com.sun.tools.javac.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lombok.Setter;

import java.io.IOException;
import java.util.Objects;

public class MainView {
    @FXML
    private AnchorPane scenePane;

    Stage currentWindow;

    //example implementation
    @FXML
    private void crazyAction(ActionEvent event) {
        switchMenu(event, "registerMenu.fxml");
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
}

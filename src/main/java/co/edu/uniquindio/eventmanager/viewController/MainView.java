package co.edu.uniquindio.eventmanager.viewController;

import co.edu.uniquindio.eventmanager.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class MainView {
    private static void switchMenu(ActionEvent event, String menuFileName) throws IOException {
        if (menuFileName.isBlank()){
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setContentText("No se ha ingresado nombre de archivo del menú");
            return;
        }
        //check this 4 issues regarding classes                      |
        Parent mainRoot = FXMLLoader.load(Objects.requireNonNull(Application.class.getResource(menuFileName)));
        //
        Stage mainStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(mainRoot);
        mainStage.setScene(scene);
        mainStage.show();
    }

}

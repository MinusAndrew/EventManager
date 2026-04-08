package co.edu.uniquindio.eventmanager;

import co.edu.uniquindio.eventmanager.model.Purchase;
import co.edu.uniquindio.eventmanager.model.User;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDate;

public class Application extends javafx.application.Application {
    @Override
    public void start(Stage stage) throws IOException {
        /*
        Take this as Main
         */

        /*
        Move to unit test
        Purchase purchase = Purchase.builder().total(7.500).dateCreated(LocalDate.now()).idPurchase("727").build();
         */

        FXMLLoader fxmlLoader = new FXMLLoader(Application.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 320, 240);
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();






    }
}

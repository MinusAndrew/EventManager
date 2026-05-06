package co.edu.uniquindio.eventmanager;

import co.edu.uniquindio.eventmanager.model.*;
import co.edu.uniquindio.eventmanager.viewController.LoginView;
import com.almasb.fxgl.profile.SaveLoadHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDate;

public class Application extends javafx.application.Application {
    @Override
    public void start(Stage stage) throws IOException {
        /*
        Move to unit test
        Purchase purchase = Purchase.builder().total(7.500).dateCreated(LocalDate.now()).idPurchase("727").build();
         */
        LoginView.createLoginMenu();
        /*
        Take this as Main
         */
        System.out.println("lmao");
        User user = new User("1", "MinusAndrew", "andrew@aqua.computer", "727", "pazitaTT");
        EventManager.getInstance().addUser(user);
        Zone zone = new Zone("as","ma",5,200);
        Zone zone1 = new Zone("as","ew",3,4400);

        Place place = new Place("2132","asdsa","asdaw");
    }
}

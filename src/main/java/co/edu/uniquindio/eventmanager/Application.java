package co.edu.uniquindio.eventmanager;

import co.edu.uniquindio.eventmanager.model.*;
import co.edu.uniquindio.eventmanager.viewController.LoginView;
import javafx.stage.Stage;

import java.io.IOException;

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
        User user = new User("MinusAndrew", "andrew@aqua.me", "727", "pazitaTT");
        EventManager.getInstance().addUser(user);
        Zone zone = new Zone("as","ma",5,200);
        Zone zone1 = new Zone("as","ew",3,4400);

        Place place = new Place("2132","asdsa","asdaw");
    }
}

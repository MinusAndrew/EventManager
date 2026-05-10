package co.edu.uniquindio.eventmanager;

import co.edu.uniquindio.eventmanager.model.*;
import co.edu.uniquindio.eventmanager.model.Enums.EventPolicy;
import co.edu.uniquindio.eventmanager.model.Enums.EventStatus;
import co.edu.uniquindio.eventmanager.model.Enums.EventType;
import co.edu.uniquindio.eventmanager.viewController.LoginView;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;

public class Application extends javafx.application.Application {

    //Main view - delete
    @Override
    public void start(Stage stage) throws IOException {
        /*
        Move to unit test
        Purchase purchase = Purchase.builder().total(7.500).dateCreated(LocalDate.now()).idPurchase("727").build();
         */

        /*
        Take this as Main
         */
        System.out.println("lmao");
        // Admin admin = new Admin("Juan,)
        User user = new User("MinusAndrew", "andrew@aqua.me", "727", "pazitaTT");
        EventManager.getInstance().addUser(user);
        Zone zone = new Zone("as","ma",5,200);
        Zone zone1 = new Zone("as","ew",3,4400);

        Place place = new Place("2132","asdsa","asdaw");

        Event event = new Event("123123", "SI", "No sé", "Colombia",
                LocalDate.now(), LocalTime.now(), place, EventType.CONCERT,
                EventStatus.PUBLISHED, EventPolicy.CANCELLATION);

        EventManager.getInstance().addEvent(event);
        System.out.println(EventManager.getInstance().getEventList().size());

        LoginView.createLoginMenu();
    }
}

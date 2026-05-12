package co.edu.uniquindio.eventmanager;

import co.edu.uniquindio.eventmanager.controller.AdminController;
import co.edu.uniquindio.eventmanager.model.*;
import co.edu.uniquindio.eventmanager.model.Enums.*;
import co.edu.uniquindio.eventmanager.viewController.LoginView;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class Application extends javafx.application.Application {

    //Main view - delete
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
        // Admin admin = new Admin("Juan,)
        User user = new User("MinusAndrew", "andrew@aqua.me", "727", "pazitaTT");
        EventManager.getInstance().addUser(user);
        Zone zone = Zone.builder().idZone("Z-01").name("VIP / Front Stage").capacity(50).startingPrice(60000.0).build();
        for (int i = 1; i <= zone.getCapacity(); i++) {
            zone.addChair(new Chair("V" + i, (i - 1) / 10 + 1, (i - 1) % 10 + 1, ChairStatus.AVAILABLE));
        }
        Zone zone1 = Zone.builder().idZone("Z-02").name("General Admission").capacity(15).startingPrice(42000.0).build();

        Chair chair1 = new Chair("123",9,1, ChairStatus.AVAILABLE);
        chair1.setTheZone(zone1);
        zone1.addChair(chair1);

        Place place = new Place("2132","asdsa","asdaw");
        place.addZone(zone);
        place.addZone(zone1);
        Place place2 = new Place("21323","asdsa","asdaw");
        EventManager.getInstance().addPlace(place);
        EventManager.getInstance().addPlace(place2);

        Event event1 = new Event(
                "EVT-001",
                "SI",
                "Festival de música electrónica",
                "Colombia",
                LocalDateTime.now(),
                place,
                EventType.CONCERT,
                EventPolicy.CANCELLATION
        );

        Event event2 = new Event(
                "EVT-002",
                "NO",
                "Obra clásica de Shakespeare",
                "Colombia",
                LocalDateTime.now().plusDays(3),
                place,
                EventType.THEATER,
                EventPolicy.REFUND
        );
        place.addEvent(event1);
        place.addEvent(event2);

        Event event3 = new Event(
                "EVT-003",
                "SI",
                "Conferencia de tecnología e IA",
                "Colombia",
                LocalDateTime.now().plusWeeks(1),
                place,
                EventType.CONFERENCE,
                EventPolicy.CANCELLATION
        );

        Event event4 = new Event(
                "EVT-004",
                "NO",
                "Concierto de rock alternativo",
                "Colombia",
                LocalDateTime.now().plusDays(10),
                place,
                EventType.CONCERT,
                EventPolicy.REFUND
        );

        Event event5 = new Event(
                "EVT-005",
                "SI",
                "Festival internacional de teatro",
                "Colombia",
                LocalDateTime.now().plusMonths(1),
                place,
                EventType.THEATER,
                EventPolicy.CANCELLATION
        );

        EventManager.getInstance().addEvent(event1);
        EventManager.getInstance().addEvent(event2);
        EventManager.getInstance().addEvent(event3);
        EventManager.getInstance().addEvent(event4);
        EventManager.getInstance().addEvent(event5);

        AdminController adminController = new AdminController();
        Admin admin = new Admin("Jacobo","esau@gmail.com","6767","iwtkms");
        Proxy proxy = new Proxy(adminController,admin);

        Purchase purchase = new Purchase(user, 100, "132",null, PaymentType.APPLE);
        user.addPurchase(purchase);
        System.out.println(EventManager.getInstance().getEventList().size());
    }
}

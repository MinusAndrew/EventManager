package co.edu.uniquindio.eventmanager.viewController;

import co.edu.uniquindio.eventmanager.model.Event;
import co.edu.uniquindio.eventmanager.model.EventManager;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.time.LocalDate;
import java.util.Optional;
import java.util.ResourceBundle;

public class AdminView {

    @FXML
    private TableView eventMenu;

    @FXML
    private TableColumn<Event, String> eventID;

    @FXML
    private TableColumn<Event, String> eventName;


    @FXML
    private TableColumn<Event, LocalDate> eventDate;

    private EventManager eventManager = EventManager.getInstance();

    ObservableList<Event> observableEvents = FXCollections.observableArrayList(eventManager.getEventList());

    @FXML
    private void initialize(){

        System.out.println("Initializing...");
        System.out.println(eventManager.getEventList().size());
        System.out.println(observableEvents.size());

        eventID.setCellValueFactory(new PropertyValueFactory<>("idEvent"));

        eventName.setCellValueFactory(new PropertyValueFactory<>("name"));

        eventDate.setCellValueFactory(new PropertyValueFactory<>("date"));

        eventID.setReorderable(false);
        eventName.setReorderable(false);
        eventDate.setReorderable(false);

        eventID.setStyle("-fx-alignment: CENTER;");
        eventName.setStyle("-fx-alignment: CENTER;");
        eventDate.setStyle("-fx-alignment: CENTER;");

        eventMenu.setItems(observableEvents);

        eventMenu.setRowFactory(tv -> {

            TableRow<Event> row = new TableRow<>();

            ContextMenu menu = new ContextMenu();

            MenuItem delete = new MenuItem("Delete");
            MenuItem modify = new MenuItem("Modify");
            MenuItem read = new MenuItem("Read");

            delete.setOnAction(e -> {
                Event event = row.getItem();

                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Confirmation");
                alert.setHeaderText("Event Deletion");
                alert.setContentText("Are you sure you want to delete the event:" + "\n" +
                        "ID: " + event.getIdEvent() + "\n" +
                        "Name: " + event.getName()
                );

                Optional<ButtonType> button = alert.showAndWait();

                if(button.isPresent() && button.get() == ButtonType.OK){
                    eventManager.removeEvent(event);
                    observableEvents.remove(event);
                    Alert information = new Alert(Alert.AlertType.INFORMATION);
                    information.setTitle("Information");
                    information.setHeaderText("Succesfull!");
                    information.setContentText("The following event has been removed:" + "\n" +
                            "ID: " + event.getIdEvent() + "\n" +
                            "Name: " + event.getName());
                    information.show();
                }
                System.out.println(eventManager.getEventList().size());
            });

            modify.setOnAction(e -> {

            });

            modify.setOnAction(e ->{

            });

            menu.getItems().addAll(delete, modify, read);

            row.contextMenuProperty().bind(
                    Bindings.when(row.emptyProperty())
                            .then((ContextMenu) null)
                            .otherwise(menu)
            );

            return row;
        });

//
//        eventMenu.setContextMenu(menu);

    }
}

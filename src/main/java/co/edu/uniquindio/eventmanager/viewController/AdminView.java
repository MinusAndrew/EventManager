package co.edu.uniquindio.eventmanager.viewController;

import co.edu.uniquindio.eventmanager.Application;
import co.edu.uniquindio.eventmanager.model.Event;
import co.edu.uniquindio.eventmanager.model.EventManager;
import co.edu.uniquindio.eventmanager.viewController.modifyView.EventModify;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
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
    private TableColumn<Event, LocalDateTime> eventDate;





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
                Event event = row.getItem();

                try {

                    FXMLLoader fxmlLoader = new FXMLLoader(Application.class.getResource("eventModify.fxml"));

                    Parent root = fxmlLoader.load();

                    // Obtener controller
                    EventModify controller =
                            fxmlLoader.getController();

                    // Pasar objeto
                    controller.setEvent(event);

                    // Nueva ventana
                    Stage stage = new Stage();
                    stage.setResizable(false);
                    stage.setTitle("Modificar Evento");
                    stage.setScene(new Scene(root));
                    stage.show();

                } catch (Exception ex) {

                    ex.printStackTrace();
                }
            });

            read.setOnAction(e ->{
                Event event = row.getItem();

                Alert information = new Alert(Alert.AlertType.INFORMATION);
                information.setTitle("Information");
                information.setHeaderText(event.getName());
                information.setContentText("Displaying event information: " + "\n" +
                            "ID: " + event.getIdEvent() + "\n" +
                            "Name: " + event.getName() + "\n" +
                            "Description: " + event.getDescription() + "\n" +
                            "Date: " + event.getDate().format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.LONG, FormatStyle.SHORT)) + "\n" +
                            "City: " + event.getCity() + "\n" +
                            "Place: " + "\n" + event.getThePlace() + "\n" +
                            "Type: " + String.valueOf(event.getEventType()) + "\n" +
                            "Status: " + String.valueOf(event.getEventStatus()));
                information.show();
            });

            menu.getItems().addAll(delete, modify, read);

            row.contextMenuProperty().bind(
                    Bindings.when(row.emptyProperty())
                            .then((ContextMenu) null)
                            .otherwise(menu)
            );

            return row;
        });
    }
}

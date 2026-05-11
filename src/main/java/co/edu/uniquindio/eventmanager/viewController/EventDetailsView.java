package co.edu.uniquindio.eventmanager.viewController;

import co.edu.uniquindio.eventmanager.model.EventManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.ResourceBundle;

import static co.edu.uniquindio.eventmanager.viewController.MainView.switchMenu;

public class EventDetailsView implements Initializable {
    @FXML
    private Text eventNameText;
    @FXML
    private Text descEventText;
    @FXML
    private Text placeText;
    @FXML
    private Text eventPricesText;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        eventNameText.setText(EventManager.getInstance().getCurrentSelectedEvent().getName());
        descEventText.setText(EventManager.getInstance().getCurrentSelectedEvent().getDescription());
        placeText.setText(EventManager.getInstance().getCurrentSelectedEvent().getThePlace().getName());
        eventPricesText.setText(EventManager.getInstance().getCurrentSelectedEvent().getEventPrices());
    }



    @FXML
    public void backAction(ActionEvent event){
        switchMenu(event, "mainMenu.fxml");
    }
}

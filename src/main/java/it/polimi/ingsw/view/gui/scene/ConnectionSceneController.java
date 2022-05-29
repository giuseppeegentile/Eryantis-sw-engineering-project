package it.polimi.ingsw.view.gui.scene;

import it.polimi.ingsw.controller.ClientController;
import it.polimi.ingsw.observer.ViewObservable;
import it.polimi.ingsw.view.gui.SceneController;
import javafx.css.PseudoClass;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

import java.util.Map;
/**
 * This class implements the scene where users connect to the server.
 */

public class ConnectionSceneController extends ViewObservable implements GenericSceneController{

    private final PseudoClass ERROR_PSEUDO_CLASS = PseudoClass.getPseudoClass("error");

    @FXML
    private Parent rootPane;

    @FXML
    private TextField serverAddressField;

    @FXML
    private TextField serverPortField;

    @FXML
    private Button connectButton;
    @FXML
    private Button backToTitleButton;

    @FXML
    private Label serverIPLabel;

    @FXML
    private Label serverPortLabel;

    @FXML
    public void initialize() {
        connectButton.addEventHandler(MouseEvent.MOUSE_CLICKED, this::onConnectButtonClick);
        backToTitleButton.addEventHandler(MouseEvent.MOUSE_CLICKED, this::onBackToTitleButtonClick);
    }


    /**
     * Handle the click on the connect button.
     *
     * @param event the mouse click event.
     */
    private void onConnectButtonClick(Event event) {
        connectButton.setOpacity(0);
        backToTitleButton.setOpacity(0);
        serverIPLabel.setOpacity(1);
        serverPortLabel.setOpacity(1);
        serverAddressField.setOpacity(1);
        serverPortField.setOpacity(1);
        String address = serverAddressField.getText();
        String port = serverPortField.getText();

        boolean isValidIpAddress = ClientController.isValidIpAddress(address);
        boolean isValidPort = ClientController.isValidPort(port);

        serverAddressField.pseudoClassStateChanged(ERROR_PSEUDO_CLASS, !isValidIpAddress);
        serverPortField.pseudoClassStateChanged(ERROR_PSEUDO_CLASS, !isValidPort);

        if (isValidIpAddress && isValidPort) {
            backToTitleButton.setDisable(true);
            connectButton.setDisable(true);

            Map<String, String> serverInfo = Map.of("address", address, "port", port);
            new Thread(() -> notifyObserver(obs -> obs.onUpdateServerInfo(serverInfo))).start();
        }
    }

    /**
     * Handle the click on the back button.
     *
     * @param event the mouse click event.
     */
    private void onBackToTitleButtonClick(Event event) {
        backToTitleButton.setDisable(true);
        connectButton.setDisable(true);

        SceneController.changeRootPane(observers, event, "ScreenTitle.fxml");
    }
}


package it.polimi.ingsw.view.gui.scene;


import it.polimi.ingsw.model.enums.GameMode;
import it.polimi.ingsw.observer.ViewObservable;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Popup;
import javafx.stage.Stage;

import java.util.List;

public class GameModeSceneController extends ViewObservable implements GenericSceneController{
    @FXML
    private RadioButton beginner_button;

    @FXML
    private RadioButton advanced_button;

    Dialog<String> dialog;
    @FXML
    private void initialize() {
        dialog = new Dialog<>();
        dialog.setTitle("Waiting...");
        dialog.setContentText("Waiting for other players to connect...");
        beginner_button.addEventHandler(MouseEvent.MOUSE_CLICKED, this::onBeginnerClick);
        advanced_button.addEventHandler(MouseEvent.MOUSE_CLICKED, this::onAdvancedClick);
    }

    private void onAdvancedClick(Event e) {
        new Thread(() -> notifyObserver(obs -> obs.onUpdateGameMode(GameMode.ESPERTO))).start();
        dialog.showAndWait();
    }

    private void onBeginnerClick(Event e) {
        new Thread(() -> notifyObserver(obs -> obs.onUpdateGameMode(GameMode.PRINCIPIANTE))).start();
        dialog.showAndWait();
    }
}

package it.polimi.ingsw.view.gui.scene;


import it.polimi.ingsw.model.enums.GameMode;
import it.polimi.ingsw.observer.ViewObservable;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Popup;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

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

        /*dialog.setHeaderText("Waiting for other players to connect...");
        dialog.setContentText("We override the style classes of the dialog");*/
        dialog.initStyle(StageStyle.UNDECORATED);

        //dialog.setContentText("Waiting for other players to connect...");
        DialogPane dialogPane = dialog.getDialogPane();
        dialogPane.setHeaderText("Lobby creata con successo!");
        dialogPane.setContentText("Aspetta gli altri giocatori...");
        dialogPane.getStylesheets().add(getClass().getResource("/css/StartingGame.css").toExternalForm());
        dialogPane.getStyleClass().add("solidPanel");
        dialog.setDialogPane(dialogPane);
        //dialog.setDialogPane();
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

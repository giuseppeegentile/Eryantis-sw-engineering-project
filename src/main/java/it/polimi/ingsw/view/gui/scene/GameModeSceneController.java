package it.polimi.ingsw.view.gui.scene;


import it.polimi.ingsw.model.enums.GameMode;
import it.polimi.ingsw.observer.ViewObservable;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogPane;
import javafx.scene.control.RadioButton;
import javafx.scene.input.MouseEvent;
import javafx.stage.StageStyle;

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
        new Thread(() -> notifyObserver(obs -> obs.onUpdateGameMode(GameMode.ADVANCED))).start();
        dialog.showAndWait();
    }

    private void onBeginnerClick(Event e) {
        new Thread(() -> notifyObserver(obs -> obs.onUpdateGameMode(GameMode.BEGINNER))).start();
        dialog.showAndWait();
    }
}

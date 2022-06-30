package it.polimi.ingsw.view.gui.scene;


import it.polimi.ingsw.model.enums.GameMode;
import it.polimi.ingsw.observer.ViewObservable;
import it.polimi.ingsw.view.gui.Gui;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.input.MouseEvent;
import javafx.stage.StageStyle;

/**
 * Class that manages every interaction of the player in game mode selection scene.
 */

public class GameModeSceneController extends ViewObservable implements GenericSceneController{
    @FXML
    private Label insertGameMode;

    @FXML
    private Label labelMinor;

    @FXML
    private Label waitOtherPlayers;

    @FXML
    private RadioButton beginner_button;

    @FXML
    private RadioButton advanced_button;

    /**
     * Manages every interaction of the player with the graphical objects in the scene.
     */

    @FXML
    private void initialize() {
        waitOtherPlayers.setVisible(false);
        labelMinor.setVisible(false);
        beginner_button.addEventHandler(MouseEvent.MOUSE_CLICKED, this::onBeginnerClick);
        advanced_button.addEventHandler(MouseEvent.MOUSE_CLICKED, this::onAdvancedClick);
    }

    /**
     * Notifies the observer when advanced mode is selected.
     * @param e click event
     */

    private void onAdvancedClick(Event e) {
        new Thread(() -> notifyObserver(obs -> obs.onUpdateGameMode(GameMode.ADVANCED))).start();
        Gui.setMode(GameMode.ADVANCED);
        showWaitOtherPlayers();
    }

    /**
     * Notifies the observer when advanced mode is selected.
     * @param e click event
     */

    private void onBeginnerClick(Event e) {
        new Thread(() -> notifyObserver(obs -> obs.onUpdateGameMode(GameMode.BEGINNER))).start();
        Gui.setMode(GameMode.BEGINNER);
        showWaitOtherPlayers();
    }

    /**
     * Shows a pop when waiting for the other players.
     */

    private void showWaitOtherPlayers(){
        advanced_button.setVisible(false);
        beginner_button.setVisible(false);
        insertGameMode.setVisible(false);
        labelMinor.setVisible(true);
        waitOtherPlayers.setVisible(true);
    }
}

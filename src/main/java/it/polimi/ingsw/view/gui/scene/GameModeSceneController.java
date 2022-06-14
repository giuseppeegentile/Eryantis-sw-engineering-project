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

    @FXML
    private void initialize() {
        beginner_button.addEventHandler(MouseEvent.MOUSE_CLICKED, this::onBeginnerClick);
        advanced_button.addEventHandler(MouseEvent.MOUSE_CLICKED, this::onAdvancedClick);
    }

    private void onAdvancedClick(Event e) {
        new Thread(() -> notifyObserver(obs -> obs.onUpdateGameMode(GameMode.ADVANCED))).start();
    }

    private void onBeginnerClick(Event e) {
        new Thread(() -> notifyObserver(obs -> obs.onUpdateGameMode(GameMode.BEGINNER))).start();
    }
}

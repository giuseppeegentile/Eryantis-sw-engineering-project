package it.polimi.ingsw.view.gui.scene;

import it.polimi.ingsw.observer.ViewObservable;
import it.polimi.ingsw.view.gui.SceneController;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;

/**
 * This class implements the scene where the game host chooses the number of players who are going to play.
 */
public class PlayersNumberSceneController extends ViewObservable implements GenericSceneController {

    @FXML
    final Spinner<Integer> spinner = new Spinner<>();

    @FXML
    private Label playersNumberFieldLabel;

    @FXML
    private Button nextStageBtn;

    @FXML
    private Button backToTitleButton;

    @FXML
    public void initialize() {
        spinner.setEditable(true);

        SpinnerValueFactory<Integer> valueFactory =
                new SpinnerValueFactory<>() {

                    @Override
                    public void decrement(int steps) {
                        int newValue = this.getValue() - 1;
                        this.setValue(newValue);
                    }

                    @Override
                    public void increment(int steps) {
                        int newValue = this.getValue() + 1;
                        this.setValue(newValue);
                    }

                };

        // Default value for Spinner
        valueFactory.setValue(2);

        spinner.setValueFactory(valueFactory);


        spinner.setValueFactory(valueFactory);
        nextStageBtn.addEventHandler(MouseEvent.MOUSE_CLICKED, this::onNextStageClicked);
        backToTitleButton.addEventHandler(MouseEvent.MOUSE_CLICKED, this::onBackToMenu);
/*
        confirmBtn.addEventHandler(MouseEvent.MOUSE_CLICKED, this::onConfirmBtnClick);
        backToMenuBtn.addEventHandler(MouseEvent.MOUSE_CLICKED, this::onBackToMenuBtnClick);*/
    }

    private void onBackToMenu(Event event) {
        SceneController.changeRootPane(observers, event, "ScreenTitle.fxml");
    }

    private void onNextStageClicked(Event event){
        nextStageBtn.setDisable(true);
        int playersNumber = spinner.getValue();

        new Thread(() -> notifyObserver(obs -> obs.onUpdatePlayersNumber(playersNumber))).start();
    }
}
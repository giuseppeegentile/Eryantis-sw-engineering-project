package it.polimi.ingsw.view.gui.scene;

import it.polimi.ingsw.observer.ViewObservable;
import it.polimi.ingsw.view.gui.SceneController;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;

/**
 * This class implements the scene where the game host chooses the number of players who are going to play.
 */
public class PlayersNumberSceneController extends ViewObservable implements GenericSceneController {

    @FXML
    private Button btn_minus;

    @FXML
    private Button btn_plus;

    @FXML
    private Button nextStageBtn;

    @FXML
    private Button backToTitleButton;

    @FXML
    private Label number_players;

    /**
     * Manages every interaction of the player with the graphical objects in the scene
     */

    @FXML
    public void initialize() {
        number_players.setText("2");
        btn_plus.addEventHandler(MouseEvent.MOUSE_CLICKED, (e)->{
            if(number_players.getText().equals("4")) btn_plus.setDisable(true);
            else{
                btn_minus.setDisable(false);
                int curr = Integer.parseInt(number_players.getText());
                curr+=1;
                number_players.setText(String.valueOf(curr));
            }
        });
        btn_minus.addEventHandler(MouseEvent.MOUSE_CLICKED, (e)->{
            if(number_players.getText().equals("2")) btn_minus.setDisable(true);
            else{
                btn_plus.setDisable(false);
                int curr = Integer.parseInt(number_players.getText());
                curr-=1;
                number_players.setText(String.valueOf(curr));
            }
        });


        nextStageBtn.addEventHandler(MouseEvent.MOUSE_CLICKED, this::onNextStageClicked);
        backToTitleButton.addEventHandler(MouseEvent.MOUSE_CLICKED, this::onBackToMenu);
        nextStageBtn.setOnKeyPressed(e -> {
            if( e.getCode() == KeyCode.ENTER ) {
                onNextStageClicked(e);
            }
        });
    }

    /**
     * Changes the scene to ScreenTitle if the button is clicked.
     * @param event click event
     */

    private void onBackToMenu(Event event) {
        SceneController.changeRootPane(observers, event, "ScreenTitle.fxml");
    }

    /**
     * Notifies the observer to change the scene if the button is clicked.
     * @param event click event
     */

    private void onNextStageClicked(Event event){
        nextStageBtn.setDisable(true);

        new Thread(() -> notifyObserver(obs -> obs.onUpdatePlayersNumber(Integer.parseInt(number_players.getText())))).start();
    }
}
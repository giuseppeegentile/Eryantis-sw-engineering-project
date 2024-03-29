
package it.polimi.ingsw.view.gui.scene;

import it.polimi.ingsw.observer.ViewObservable;
import it.polimi.ingsw.observer.ViewObserver;
import it.polimi.ingsw.view.gui.SceneController;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

/**
 * This class implements the scene where users choose their nicknames.
 */
public class LoginSceneController extends ViewObservable implements GenericSceneController {

    @FXML
    private TextField nicknameField;

    @FXML
    private Button nextStageButton;
    @FXML
    private Button backToTitleButton;

    /**
     * Manages every interaction of the player with the graphical objects in the scene.
     */

    @FXML
    public void initialize() {
        nextStageButton.addEventHandler(MouseEvent.MOUSE_CLICKED, this::onNextStageButtonClick);
        backToTitleButton.addEventHandler(MouseEvent.MOUSE_CLICKED, this::onBackToTitleButtonClick);
        nicknameField.setOnKeyPressed(e -> {
            if( e.getCode() == KeyCode.ENTER ) {
                onNextStageButtonClick(e);
            }
        });

    }

    /**
     * Handle click on "Inizia partita" button.
     *
     * @param event the mouse click event.
     */
    private void onNextStageButtonClick(Event event) {
        String nickname = nicknameField.getText();
        new Thread(() -> notifyObserver(obs -> obs.onUpdateNickname(nickname))).start();
    }

    /**
     * Handle click on back to title button.
     *
     * @param event the mouse click event.
     */
    private void onBackToTitleButtonClick(Event event) {
        nextStageButton.setDisable(true);
        backToTitleButton.setDisable(true);

        new Thread(() -> notifyObserver(ViewObserver::onDisconnection)).start();
        SceneController.changeRootPane(observers, event, "ScreenTitle.fxml");
    }
}

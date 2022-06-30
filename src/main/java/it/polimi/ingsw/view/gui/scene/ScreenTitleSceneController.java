
package it.polimi.ingsw.view.gui.scene;

import it.polimi.ingsw.observer.ViewObservable;
import it.polimi.ingsw.view.gui.SceneController;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

/**
 * This class implements the main menu scene.
 */
public class ScreenTitleSceneController extends ViewObservable implements GenericSceneController {

    @FXML
    private AnchorPane rootPane;
    @FXML
    private Button newGameButton;
    @FXML
    private Button quitGameButton;

    /**
     * Manages every interaction of the player with the graphical objects in the scene.
     */

    @FXML
    public void initialize() {
        newGameButton.addEventHandler(MouseEvent.MOUSE_CLICKED, this::onNewGameButtonClick);
        quitGameButton.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> System.exit(0));
    }

    /**
     * Handles click on Play button.
     *
     * @param event the mouse click event.
     */
    private void onNewGameButtonClick(Event event) {
        SceneController.changeRootPane(observers, event, "ConnectionScene.fxml");
    }
}

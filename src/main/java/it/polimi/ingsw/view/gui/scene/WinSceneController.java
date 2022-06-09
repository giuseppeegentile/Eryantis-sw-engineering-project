
package it.polimi.ingsw.view.gui.scene;

import it.polimi.ingsw.observer.ViewObservable;
import javafx.fxml.FXML;
import javafx.scene.control.Label;


/**
 * This class implements the scene that shows what player won.
 */
public class WinSceneController extends ViewObservable implements GenericSceneController {
    @FXML
    public Label winner_label;
    @FXML
    public void initialize() { }

    /**
     * Sets the name (received from server) of the winner label.
     * @param winnerNickname the nickname of the winner.
     */
    public void setWinnerNickname(String winnerNickname) {
        winner_label.setText(winnerNickname);
    }
}
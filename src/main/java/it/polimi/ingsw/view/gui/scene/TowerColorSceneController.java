package it.polimi.ingsw.view.gui.scene;

import it.polimi.ingsw.model.colors.ColorTower;
import it.polimi.ingsw.observer.ViewObservable;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.input.MouseEvent;

import java.util.List;

public class TowerColorSceneController extends ViewObservable implements GenericSceneController{

    @FXML
    private Button button;

    @FXML
    private RadioButton white_button;

    @FXML
    private RadioButton grey_button;

    @FXML
    private RadioButton black_button;

    @FXML
    public void initialize() {
        button.addEventHandler(MouseEvent.MOUSE_CLICKED, this::onBtnClick);
    }

    private void onBtnClick(Event e) {
        ColorTower colorChosen;
        if(black_button.isSelected()){
            colorChosen =ColorTower.BLACK;
        }
        else if(white_button.isSelected()){
            colorChosen =ColorTower.WHITE;
        }
        else if(grey_button.isSelected()){
            colorChosen =ColorTower.GREY;
        }else{
            colorChosen = ColorTower.NULL;
        }

        new Thread(() -> notifyObserver(obs -> obs.onUpdateTower(colorChosen))).start();

    }

}

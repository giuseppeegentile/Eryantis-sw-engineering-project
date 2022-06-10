package it.polimi.ingsw.view.gui.scene;

import it.polimi.ingsw.model.colors.ColorTower;
import it.polimi.ingsw.observer.ViewObservable;
import javafx.application.Platform;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;

import java.util.List;

public class TowerColorSceneController extends ViewObservable implements GenericSceneController{

    private List<ColorTower> availableColorTowers;
    @FXML
    private Button button;

    @FXML
    private RadioButton white_button;

    @FXML
    private RadioButton grey_button;

    @FXML
    private RadioButton black_button;


    private void hide(RadioButton btnToHide){
        btnToHide.setVisible(false);
        btnToHide.managedProperty().bind(btnToHide.visibleProperty());
    }

    @FXML
    private void initialize() {
        button.addEventHandler(MouseEvent.MOUSE_CLICKED, this::onBtnClick);
        black_button.addEventHandler(MouseEvent.MOUSE_CLICKED, this::onBlackBtnClick);
        white_button.addEventHandler(MouseEvent.MOUSE_CLICKED, this::onWhiteBtnClick);
        grey_button.addEventHandler(MouseEvent.MOUSE_CLICKED, this::onGreyBtnClick);
        if(!availableColorTowers.contains(ColorTower.GREY)){
            hide(grey_button);
        }
        if(!availableColorTowers.contains(ColorTower.BLACK)){
            hide(black_button);
        }
        if(!availableColorTowers.contains(ColorTower.WHITE)){
            hide(white_button);
        }
        white_button.setOnKeyPressed(e -> {
            if( e.getCode() == KeyCode.ENTER ) {
                onBtnClick(e);
            }
        });
        grey_button.setOnKeyPressed(e -> {
            if( e.getCode() == KeyCode.ENTER ) {
                onBtnClick(e);
            }
        });
        black_button.setOnKeyPressed(e -> {
            if( e.getCode() == KeyCode.ENTER ) {
                onBtnClick(e);
            }
        });

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

    private void onBlackBtnClick(Event e) {
        if (!white_button.isDisabled() && !grey_button.isDisabled()){
            white_button.setDisable(true);
            grey_button.setDisable(true);
        }else{
            white_button.setDisable(false);
            grey_button.setDisable(false);
        }
    }

    private void onWhiteBtnClick(Event e) {
        if (!black_button.isDisabled() && !grey_button.isDisabled()){
            black_button.setDisable(true);
            grey_button.setDisable(true);
        }else{
            black_button.setDisable(false);
            grey_button.setDisable(false);
        }
    }

    private void onGreyBtnClick(Event e) {
        if (!white_button.isDisabled() && !black_button.isDisabled()){
            white_button.setDisable(true);
            black_button.setDisable(true);
        }else{
            white_button.setDisable(false);
            black_button.setDisable(false);
        }
    }

    public void setAvailableTowers(List<ColorTower> availableColorTowers) {
        this.availableColorTowers =availableColorTowers;
    }
}

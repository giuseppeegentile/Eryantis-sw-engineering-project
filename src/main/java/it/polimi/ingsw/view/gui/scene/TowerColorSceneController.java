package it.polimi.ingsw.view.gui.scene;

import it.polimi.ingsw.model.colors.ColorTower;
import it.polimi.ingsw.observer.ViewObservable;

import java.util.List;

public class TowerColorSceneController extends ViewObservable implements GenericSceneController{

    List<ColorTower> towerColor;

    public void setTowerColor(List<ColorTower> towerColor){
        this.towerColor = towerColor;
    }


}

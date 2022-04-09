package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.GameModel;


//DA SISTEMARE: non dovrebbe essere una sottoclasse, ma per ora la lasciamo cosi
//perchè deve ricavare in qualche modo il gameModel
public class GameControllerState extends GameController{

    public GameControllerState(GameModel gameModel) {
        super(gameModel);
    }
}

package it.polimi.ingsw.network.message;

import it.polimi.ingsw.model.game.GameModel;

public class GameModeRequest extends Message {
    GameModeRequest(){
        super(GameModel.SERVER_NICKNAME, MessageType.PLAYERS_REQUEST);
    }


    @Override
    public String toString() {
        return "GameModeRequest{" +
                "nickname=" + getNickname() +
                '}';
    }
}

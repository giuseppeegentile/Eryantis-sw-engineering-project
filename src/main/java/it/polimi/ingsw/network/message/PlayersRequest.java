package it.polimi.ingsw.network.message;


import it.polimi.ingsw.model.game.GameModel;

public class PlayersRequest extends Message {

    private static final long serialVersionUID = -2155556142315548857L;

    public PlayersRequest() {
        super(GameModel.SERVER_NICKNAME, MessageType.PLAYERS_REQUEST);
    }

    @Override
    public String toString() {
        return "PlayersRequest{" +
                "nickname=" + getNickname() +
                '}';
    }
}

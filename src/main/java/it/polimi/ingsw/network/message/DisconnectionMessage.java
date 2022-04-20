package it.polimi.ingsw.network.message;

import it.polimi.ingsw.model.game.GameModel;

public class DisconnectionMessage extends Message {
    private static final long serialVersionUID = -5422965079989607600L;

    public DisconnectionMessage() {
        super(GameModel.SERVER_NICKNAME, MessageType.DISCONNECTION);
    }


    @Override
    public String toString() {
        return "DisconnectionMessage{" +
                '}';
    }
}

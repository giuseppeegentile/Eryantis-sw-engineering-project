package it.polimi.ingsw.network.message;

import it.polimi.ingsw.model.colors.ColorPawns;
import it.polimi.ingsw.model.islands.IslandModel;
import it.polimi.ingsw.model.player.PlayerModel;

public class MoveMessage extends Message{

    private static final long serialVersionUID = -5330536550018687004L;

    IslandModel islandModel = null;
    PlayerModel playerModel;
    ColorPawns colorPawns;
    public MoveMessage(PlayerModel playerModel, ColorPawns colorPawns, IslandModel islandModel) {
        super(playerModel.getNickname(), MessageType.MOVE);
        this.islandModel = islandModel;
        this.playerModel = playerModel;
        this.colorPawns = colorPawns;
    }
    //to hall
    public MoveMessage(PlayerModel playerModel,ColorPawns colorPawns) {
        super(playerModel.getNickname(), MessageType.MOVE);
        this.playerModel = playerModel;
        this.colorPawns = colorPawns;
    }

    @Override
    public String toString() {
        String str = this.islandModel==null ? "hall":"island";
        return "MoveMessage{" +
                "nickname=" + playerModel.getNickname() +
                ", position=" + str +
                ", student=" + colorPawns+
                '}';
    }
}

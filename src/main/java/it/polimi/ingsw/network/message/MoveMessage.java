package it.polimi.ingsw.network.message;

import it.polimi.ingsw.model.colors.ColorPawns;
import it.polimi.ingsw.model.game.GameModel;
import it.polimi.ingsw.model.islands.IslandModel;
import it.polimi.ingsw.model.player.PlayerModel;

public class MoveMessage extends Message{

    private static final long serialVersionUID = -5330536550018687004L;

    IslandModel islandModel = null;
    PlayerModel playerModel;
    ColorPawns colorPawns;

    //to island
    public MoveMessage(String playerModel, ColorPawns colorPawns, IslandModel islandModel) {
        super(playerModel, MessageType.MOVE);
        this.islandModel = islandModel;
        this.playerModel = GameModel.getInstance().getPlayerByNickname(playerModel);
        this.colorPawns = colorPawns;
    }
    //to hall
    public MoveMessage(String player,ColorPawns colorPawns) {
        super(player, MessageType.MOVE);
        this.playerModel = GameModel.getInstance().getPlayerByNickname(player);
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

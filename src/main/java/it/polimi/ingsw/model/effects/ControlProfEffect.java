package it.polimi.ingsw.model.effects;

import it.polimi.ingsw.model.colors.ColorPawns;
import it.polimi.ingsw.model.game.GameModel;
import it.polimi.ingsw.model.player.PlayerModel;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
//2
public class ControlProfEffect implements Effect, Serializable {
    private static final long serialVersionUID = 8743730422346855395L;
    private int costForEffect =2;
    private PlayerModel playerWithProfs;
    private Map<ColorPawns, PlayerModel> oldAssociationProfs;



    @Override
    public void enable(PlayerModel playerModel) {
        playerWithProfs = playerModel;
        oldAssociationProfs = new HashMap<>();
        for(PlayerModel p : GameModel.getInstance().getPlayersModel()){
            for(ColorPawns profs : p.getProfs()){
                oldAssociationProfs.put(profs, p);
            }
        }
        for(ColorPawns prof: List.of(ColorPawns.GREEN, ColorPawns.BLUE, ColorPawns.RED, ColorPawns.PINK, ColorPawns.YELLOW)) {
            playerModel.addProf(prof);
        }
        costForEffect++;
    }

    @Override
    public int getCoinsForEffect() {
        return this.costForEffect;
    }

    @Override
    public String getDescription() {
        return null;
    }

    public Map<ColorPawns, PlayerModel> getOldAssociationProfs(){
        return this.oldAssociationProfs;
    }

    public PlayerModel getPlayerWithProfs() {
        return playerWithProfs;
    }
}
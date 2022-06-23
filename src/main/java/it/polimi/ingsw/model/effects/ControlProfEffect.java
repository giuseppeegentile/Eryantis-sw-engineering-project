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
    private int costForEffect;
    private PlayerModel playerWithProfs;
    private Map<ColorPawns, PlayerModel> oldAssociationProfs;

    /**
     * Constructor for the character card that has "control prof" as effect: costForEffect is set to 2
     */

    public ControlProfEffect(){
        this.costForEffect = 2;
    }

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
            if (!playerModel.getProfs().contains(prof))
                playerModel.addProf(prof);
        }
    }
    @Override
    public void incrementCost() {
        this.costForEffect++;
        System.out.println(this.costForEffect);
    }

    @Override
    public int getCoinsForEffect() {
        return this.costForEffect;
    }

    @Override
    public String getDescription() {
        return "EFFECT: during this turn, take control of the professors even if you have the same number of students in your room as the player currently controlling them.";
    }

    /**
     * @return the old association of a prof to a player
     */

    public Map<ColorPawns, PlayerModel> getOldAssociationProfs(){
        return this.oldAssociationProfs;
    }

    /**
     * @return the player with profs so far
     */

    public PlayerModel getPlayerWithProfs() {
        return playerWithProfs;
    }
}

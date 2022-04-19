package it.polimi.ingsw.controller.player;

import it.polimi.ingsw.model.cards.AssistantCardModel;
import it.polimi.ingsw.model.game.GameModel;
import it.polimi.ingsw.model.game.PhaseGame;
import it.polimi.ingsw.model.islands.IslandModel;
import it.polimi.ingsw.model.player.PlayerModel;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

//3

public class PlayCardAssistantState implements PlayerState {

    private final PlayerModel playerModel;
    private final GameModel gameModel;

    public PlayCardAssistantState(PlayerModel playerModel,GameModel gameModel){
        this.playerModel = playerModel;
        this.gameModel = gameModel;
        this.gameModel.setGameState(PhaseGame.PLAY_CARD_ASSISTANT);
    }

    @Override
    public void addCoins() {

    }

    @Override
    public PlayerModel getPlayerModel() {
        return this.playerModel;
    }


    //ritorna true se può giocare la carta, false se è già stata usata e ha un'altra carta da usare
    public boolean canPlayCard(AssistantCardModel assistantCardModel){
        if(assistantCardModel.getMotherNatureMovement() == 0 || assistantCardModel.getPriority() == 0 ) return false;
        if(!this.playerModel.getDeckAssistantCardModel().contains(assistantCardModel)) return false; //se non ha la carta nel suo deck non può giocarla
        if(this.gameModel.getCemetery().size() > 1 && this.gameModel.getCemetery().contains(assistantCardModel)) {
            //int indexSameCard =  this.gameModel.getCemetery().indexOf(assistantCardModel);
            //solo nel caso in cui tutte le carte del cimitero siano uguali a quelle del giocatore posso rigiocare
            List<AssistantCardModel> tempDeck = this.playerModel.getDeckAssistantCardModel();
            List<AssistantCardModel> cem =this.gameModel.getCemetery();
            for (AssistantCardModel c : tempDeck) {
                for (AssistantCardModel cemCard : cem) {
                    if (!c.equals(cemCard)) return false;
                }
            }
        }
        return true;
    }


    public void playCard(AssistantCardModel assistantCardModel){
        this.gameModel.addToCemetery(assistantCardModel);

        int index = this.playerModel.getDeckAssistantCardModel().indexOf(assistantCardModel);
        this.playerModel.removeCard(index);
    }




}

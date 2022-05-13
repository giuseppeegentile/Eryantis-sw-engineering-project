package it.polimi.ingsw.controller.player;

import it.polimi.ingsw.model.cards.AssistantCardModel;
import it.polimi.ingsw.model.enums.StatePlayer;
import it.polimi.ingsw.model.game.GameModel;
import it.polimi.ingsw.model.enums.PhaseGame;
import it.polimi.ingsw.model.player.PlayerModel;

import java.util.List;

//3

public class PlayCardAssistantState implements PlayerState {

    private final PlayerModel playerModel;
    private final GameModel gameModel;
    /**
     * Constructor for PlayCardAssistantState: sets the game phase to PLAY_CARD_ASSISTANT
     * @param playerModel The player who is using a card
     * @param gameModel
     */
    public PlayCardAssistantState(PlayerModel playerModel,GameModel gameModel){
        this.playerModel = playerModel;
        this.gameModel = gameModel;
        this.playerModel.setState(StatePlayer.PLAY_CARD_ASSISTANT);
        this.gameModel.setGameState(PhaseGame.PLAY_CARDS_ASSISTANT);
    }

    @Override
    public StatePlayer getState() {
        return this.playerModel.getState();
    }

    @Override
    public void addCoins() {

    }

    @Override
    public PlayerModel getPlayerModel() {
        return this.playerModel;
    }


    //ritorna true se può giocare la carta, false se è già stata usata e ha un'altra carta da usare

    /**
     * @param assistantCardModel Card that the player wants to play
     * @return Tells if the player can play the assistantCardModel
     */
    public boolean canPlayCard(AssistantCardModel assistantCardModel){
        if(assistantCardModel.getMotherNatureMovement() == 0 || assistantCardModel.getPriority() == 0 ) return false;
        if(!this.playerModel.getDeckAssistantCardModel().contains(assistantCardModel)) return false; //se non ha la carta nel suo deck non può giocarla
        if(this.gameModel.getCemetery().size() > 0 && this.gameModel.getCemetery().contains(assistantCardModel)) {
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

    /**
     * Check if a player can play or not a certain card, if yes puts it in the cemetery and removes it from the player's cards
     * @param assistantCardModel The card played by the player
     * @return True or false true or false depending on whether the player can play the card or not
     */
    public void playCard(AssistantCardModel assistantCardModel){
        this.gameModel.addToCemetery(assistantCardModel);

        int index = this.playerModel.getDeckAssistantCardModel().indexOf(assistantCardModel);

        this.playerModel.removeCard(index);
    }




}

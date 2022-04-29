package it.polimi.ingsw.controller.game;

//1
//è il primo per il secondo turno di gioco però, per il primo giro non si sceglie in base a questa classe, ma in base all ordine di gioco
//scelto dai giocatori (ordine della lista di PlayersModel)

import it.polimi.ingsw.model.cards.AssistantCardModel;
import it.polimi.ingsw.model.enums.PhaseGame;
import it.polimi.ingsw.model.game.GameModel;
import it.polimi.ingsw.model.player.PlayerModel;
import it.polimi.ingsw.network.message.Message;
import it.polimi.ingsw.view.VirtualView;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

//SINGLETON
public class DecideOrderPlayerState extends GameController implements GameState {
    private final GameModel gameModel;

    /**
     * Constructor for DecideFirstPlayerState
     * @param gameModel The current gameModel
     */
    public DecideOrderPlayerState(GameModel gameModel){
        this.gameModel = gameModel;
        this.gameModel.setGameState(PhaseGame.DECIDE_ORDER_PHASE);
    }
    /**
     *
     * @return The current gameModel
     */
    @Override
    public GameModel getGameModel() {
        return this.gameModel;
    }

    @Override
    public PhaseGame getState() {
        return this.gameModel.getGameState();
    }

    //chiamato quando tutti  i giocatori hanno usato la loro carta
    //prende in input: la lista delle carte giocate, nell ordine di gioco. Restituisce una lista modificata con l ordine per la prossima fase azione
    //NON MODIFICARE LA LISTA DEI PLAYER DEL GAME, L'ORDINE INIZIALE DEVE ESSERE PRESERVATO
    /**
     * Called when all the players have played their card. Sets the order phase list.
     * @param cemetery The list of cards played in the current turn in the order they were played
     */
    public void setPlayersOrderForActionPhase(List<AssistantCardModel> cemetery){
        List<PlayerModel> playersActionPhase = new ArrayList<>(cemetery.size());

        //ordina le carte in base alla priorità, a pari priorità si basa sull indice
        cemetery.sort(Comparator.comparing(AssistantCardModel::getPriority)
                .thenComparingInt(cemetery::indexOf));

        int i = 0;
        this.gameModel.clearPhaseOrder();

        for(AssistantCardModel c: cemetery){
            //aggiunge il giocatore alla lista nell ordine della priorità
            playersActionPhase.add(c.getOwner());
            //imposta il movimento di madre natura per la fase azione (lo ricavo dalla carta assistente)
            playersActionPhase.get(i).setMovementMotherNatureCurrentActionPhase(c.getMotherNatureMovement());
            i += 1;
        }
        this.gameModel.setPhaseOrder(playersActionPhase);
    }
}

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

public class DecideOrderPlayerState extends GameController implements GameState {
    private static final long serialVersionUID = -1570438922861887484L;
    private final GameModel gameModel;

    /**
     * Constructor for DecideFirstPlayerState
     */
    public DecideOrderPlayerState(){
        this.gameModel = GameModel.getInstance();
        this.gameModel.setGameState(PhaseGame.DECIDE_ORDER_PHASE);
    }


    @Override
    public PhaseGame getState() {
        return this.gameModel.getGameState();
    }

    //chiamato quando tutti i giocatori hanno usato la loro carta
    //prende in input: la lista delle carte giocate, nell ordine di gioco. Restituisce una lista modificata con l ordine per la prossima fase azione
    /**
     * Called when all the players have played their card. Sets the order phase list.
     * @param cemetery The list of cards played in the current turn in the order they were played
     */
    public void setPlayersOrderForActionPhase(List<AssistantCardModel> cemetery){
        List<PlayerModel> playersActionPhase = new ArrayList<>(cemetery.size());

        List<AssistantCardModel> copyOfCemetery = new ArrayList<>(cemetery);

        //ordina le carte in base alla priorità, a pari priorità si basa sull indice
        copyOfCemetery.sort(Comparator.comparing(AssistantCardModel::getPriority)
                .thenComparingInt(copyOfCemetery::indexOf));

        int i = 0;
        this.gameModel.clearPhaseOrder();
        for(AssistantCardModel c: copyOfCemetery){
            //aggiunge il giocatore alla lista nell ordine della priorità
            playersActionPhase.add(c.getOwner());
            //imposta il movimento di madre natura per la fase azione (lo ricavo dalla carta assistente)
            playersActionPhase.get(i).setMovementMotherNatureCurrentActionPhase(c.getMotherNatureMovement());
            i += 1;
        }
        this.gameModel.setPhaseOrder(playersActionPhase);
    }
}

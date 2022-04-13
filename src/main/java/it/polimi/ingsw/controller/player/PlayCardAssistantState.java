package it.polimi.ingsw.controller.player;

import it.polimi.ingsw.model.cards.AssistantCardModel;
import it.polimi.ingsw.model.game.GameModel;
import it.polimi.ingsw.model.islands.IslandModel;
import it.polimi.ingsw.model.player.PlayerModel;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

//3

public class PlayCardAssistantState implements PlayerState {

    private PlayerModel playerModel;
    private GameModel gameModel;

    public PlayCardAssistantState(PlayerModel playerModel,GameModel gameModel){
        this.playerModel = playerModel;
        this.gameModel = gameModel;
    }


    @Override
    public void addCoins() {

    }

    @Override
    public void setCoins() {

    }

    @Override
    public void decrementCoins(int coinsUsed) {

    }

    //ritorna true se può giocare la carta e la aggiunge al cimitero, false se è già stata usata e ha un'altra carta da usare
    public boolean playCard(AssistantCardModel assistantCardModel){
        if(this.gameModel.getCemetery().size() > 1 && this.gameModel.getCemetery().contains(assistantCardModel)){
            //int indexSameCard =  this.gameModel.getCemetery().indexOf(assistantCardModel);
            //solo nel caso in cui tutte le carte del cimitero siano uguali a quelle del giocatore posso rigiocare
            for (AssistantCardModel c : this.playerModel.getDeckAssistantCardModel()) {
                for (AssistantCardModel cemCard : this.gameModel.getCemetery()) {
                    if (!c.equals(cemCard)) return false;
                }
            }
            this.gameModel.addToCemetery(assistantCardModel);
            this.playerModel.removeCard(assistantCardModel);
            return true;
        }
        this.gameModel.addToCemetery(assistantCardModel);
        this.playerModel.removeCard(assistantCardModel);
        return true;
    }


    //chiamato quando tutti  i giocatori hanno usato la loro carta
    //prende in input: la lista delle carte giocate, nell'ordine di gioco. Restituisce una lista modificata con l'ordine per la prossima fase azione
    //NON MODIFICARE LA LISTA DEI PLAYER DEL GAME, L'ORDINE INIZIALE DEVE ESSERE PRESERVATO
    public List<PlayerModel> getPlayersOrderForActionPhase(List<AssistantCardModel> cemetery){
        List<PlayerModel> playersActionPhase = new ArrayList<>(cemetery.size());

        //ordina le carte in base alla priorità, a pari priorità si basa sull indice
        cemetery.sort(Comparator.comparing(AssistantCardModel::getPriority)
                .thenComparingInt(cemetery::indexOf));

        AtomicInteger i = new AtomicInteger();
        cemetery.forEach(c ->{
            //aggiunge il giocatore alla lista nell'ordine della priorità
            playersActionPhase.add(c.getOwner());
            //imposta il movimento di madre natura per la fase azione (lo ricavo dalla carta assistente)
            playersActionPhase.get(i.get()).setMovementMotherNatureCurrentActionPhase(c.getMotherNatureMovement());
            i.getAndIncrement();
        });

        return playersActionPhase;
    }
}

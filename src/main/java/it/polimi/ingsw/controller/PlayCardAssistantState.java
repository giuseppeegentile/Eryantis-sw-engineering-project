package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.AssistantCardModel;
import it.polimi.ingsw.model.IslandModel;
import it.polimi.ingsw.model.PlayerModel;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

//3

//implementare metodo moveMotherNature
public class PlayCardAssistantState extends GameControllerState{

    //restituisce la lista di Isole con madre natura aggiornata
    public List<IslandModel> moveMotherNature(List<IslandModel> islandsModels, byte movementMotherNature){
        int indexOldMotherNature = 0;
        while(!islandsModels.get(indexOldMotherNature).getMotherNature()) indexOldMotherNature++;
        int newIndex = indexOldMotherNature + movementMotherNature;
        IslandModel oldIslandWithMotherNature = new IslandModel(false, islandsModels.get(indexOldMotherNature).getStudents());
        IslandModel newIslandWithMotherNature = new IslandModel(true,  islandsModels.get(newIndex).getStudents());

        islandsModels.set(indexOldMotherNature, oldIslandWithMotherNature);
        islandsModels.set(newIndex, newIslandWithMotherNature);
        return islandsModels;
    }

    //prende in input: la lista delle carte giocate, nell'ordine di gioco. Restituisce una lista modificata con l'ordine per la prossima fase azione
    //NON MODIFICARE LA LISTA DEI PLAYER DEL GAME, L'ORDINE INIZIALE DEVE ESSERE PRESERVATO
    private List<PlayerModel> getPlayersOrderForActionPhase(List<AssistantCardModel> cemetery){
        List<PlayerModel> playersActionPhase = new ArrayList<>(cemetery.size());

        cemetery.sort(Comparator.comparing(AssistantCardModel::getPriority)); //ordina le carte in base alla priorità

        AtomicInteger i = new AtomicInteger();
        cemetery.forEach(c ->{
            //aggiunge il giocatore alla lista nell'ordine della priorità
            playersActionPhase.add(c.getOwner());
            //imposta il movimento di madre natura (lo ricavo dalla carta assistente)
            playersActionPhase.get(i.get()).setMovementMotherNatureCurrentActionPhase(c.getMotherNatureMovement());
            i.getAndIncrement();
        });

        return playersActionPhase;
    }
}

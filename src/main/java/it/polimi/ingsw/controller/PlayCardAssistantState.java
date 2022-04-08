package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.AssistantCardModel;
import it.polimi.ingsw.model.PlayerModel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

//3

//---------------------------------------------DA SISTEMARE

//implementare metodo moveMotherNature
public class PlayCardAssistantState extends GameControllerState{

    private PlayerModel checkPriority(List<AssistantCardModel> cemetery, PlayerModel temp){
        List<Integer> priorityList = new ArrayList<>(cemetery.size());
        cemetery.forEach(c ->{
            priorityList.add(c.getPriority());
        });
        int minPriority = Collections.min(priorityList);
        return temp;
    }
}

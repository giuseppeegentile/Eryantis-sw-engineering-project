package it.polimi.ingsw.view.gui.scene;

import it.polimi.ingsw.model.cards.AssistantCardModel;
import it.polimi.ingsw.observer.ViewObservable;

import java.util.List;

public class PlayCardsSceneController extends ViewObservable implements GenericSceneController{

    List<AssistantCardModel> playerDeck;
    String nickname;

    public void playCards(String nickname, List<AssistantCardModel> playerDeck){
        this.nickname = nickname;
        this.playerDeck = playerDeck;
    }

}

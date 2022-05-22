package it.polimi.ingsw.network.message;

import it.polimi.ingsw.model.cards.AssistantCardModel;
import it.polimi.ingsw.model.game.GameModel;
import it.polimi.ingsw.model.player.PlayerModel;

import java.util.List;

public class AssignPlayerDeckResponseMessage extends DisplayMessage {

    private static final long serialVersionUID = -861308626070048225L;
    private final PlayerModel playerModel;
    private final List<AssistantCardModel> assistantCardModels;
    private final ObjectDisplay objectDisplay;

    //è una response
    public AssignPlayerDeckResponseMessage(String nickname, List<AssistantCardModel> assistantCardModels) {
        super(nickname);
        this.playerModel = GameModel.getInstance().getPlayerByNickname(nickname);
        this.assistantCardModels = assistantCardModels;
        this.objectDisplay = ObjectDisplay.CHOSENCARD;
    }

    @Override
    public String toString() {
        return "AssignPlayerDeckMessage{" +
                "nickname=" + getNickname() +
                ", objectDisplay=" + getObjectDisplay() +
                ", playerModel=" + playerModel +
                '}';
    }

    public ObjectDisplay getObjectDisplay() {
        return objectDisplay;
    }

    public List<AssistantCardModel> getAssistantCardModels() {
        return assistantCardModels;
    }
}

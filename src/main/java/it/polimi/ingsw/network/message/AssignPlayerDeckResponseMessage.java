package it.polimi.ingsw.network.message;

import it.polimi.ingsw.model.cards.AssistantCardModel;
import it.polimi.ingsw.model.game.GameModel;
import it.polimi.ingsw.model.player.PlayerModel;

import java.util.List;

public class AssignPlayerDeckResponseMessage extends Message {

    private static final long serialVersionUID = -861308626070048225L;
    private final PlayerModel playerModel;
    private final List<AssistantCardModel> assistantCardModels;

    //è una response

    /**
     * Message shown as a response to the assignment of a deck to a player
     * @param nickname nickname of the player to which the deck is assogned
     * @param assistantCardModels model of the assistant card
     */

    public AssignPlayerDeckResponseMessage(String nickname, List<AssistantCardModel> assistantCardModels) {
        super(nickname, MessageType.DISPLAY);
        this.playerModel = GameModel.getInstance().getPlayerByNickname(nickname);
        this.assistantCardModels = assistantCardModels;
    }

    @Override
    public String toString() {
        return "AssignPlayerDeckMessage{" +
                "nickname=" + getNickname() +
                ", playerModel=" + playerModel +
                '}';
    }

    public List<AssistantCardModel> getAssistantCardModels() {
        return assistantCardModels;
    }
}

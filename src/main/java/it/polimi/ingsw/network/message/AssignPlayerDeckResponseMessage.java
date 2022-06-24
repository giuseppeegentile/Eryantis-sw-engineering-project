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
     * Message sent as a response to the assignment of a deck to a player
     * Parameters are set by the constructor
     * @param nickname nickname of the player to which the deck is assigned
     * @param assistantCardModels model of the assistant card
     */

    public AssignPlayerDeckResponseMessage(String nickname, List<AssistantCardModel> assistantCardModels) {
        super(nickname, MessageType.DISPLAY);
        this.playerModel = GameModel.getInstance().getPlayerByNickname(nickname);
        this.assistantCardModels = assistantCardModels;
    }

    @Override
    public String toString() {
        return "AssignPlayerDeckResponseMessage{" +
                "nickname=" + getNickname() +
                ", playerModel=" + playerModel +
                '}';
    }

    /**
     * @return the models of the assistant cards
     */

    public List<AssistantCardModel> getAssistantCardModels() {
        return assistantCardModels;
    }
}

package it.polimi.ingsw.controller.game;

import it.polimi.ingsw.controller.player.PlayCardAssistantState;
import it.polimi.ingsw.model.enums.PhaseGame;
import it.polimi.ingsw.model.game.GameModel;
import it.polimi.ingsw.model.player.PlayerModel;
import it.polimi.ingsw.network.message.Message;

public class GameController {
    private GameState state;
    private PhaseGame phase;



    public void onMessageReceived(Message receivedMessage){
        switch (this.phase) {
            case START:
                new StartGameState(GameModel.getInstance()).setInitialGameConfiguration(receivedMessage);
                break;
            case CHECK_WIN:
                state = new CheckWinState(GameModel.getInstance());
                break;
            case CHECK_ISLAND:
                //state = new CheckIslandState(GameModel.getInstance(), receivedMessage.getIsland());
                break;
            case CHECK_JOIN:
                //state = new CheckIfJoinableState(GameModel.getInstance(), receivedMessage.getIsland());
                break;
            case ADD_STUDENT_CLOUD:
                state = new AddStudentFromBagToCloudState(GameModel.getInstance());
                break;
            case PLAY_CARDS_ASSISTANT:
                PlayerModel player = GameModel.getInstance().getPlayerByNickname(receivedMessage.getNickname());
                PlayCardAssistantState play = new PlayCardAssistantState(player, GameModel.getInstance());
                if(play.canPlayCard(receivedMessage.getCard())){
                    play.playCard(receivedMessage.getCard());
                }
                if (GameModel.getInstance().getIndexOfPlayer(player) == GameModel.getInstance().getPlayersNumber() - 1) {//se è l'ultimo giocatore che ha giocato la carta
                    new DecideOrderPlayerState(GameModel.getInstance()).setPlayersOrderForActionPhase(GameModel.getInstance().getCemetery());
                }
                break;
        }
    }
}

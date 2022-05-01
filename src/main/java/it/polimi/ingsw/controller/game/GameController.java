package it.polimi.ingsw.controller.game;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import it.polimi.ingsw.controller.player.*;
import it.polimi.ingsw.model.cards.AssistantCardModel;
import it.polimi.ingsw.model.enums.PhaseGame;
import it.polimi.ingsw.model.game.GameModel;
import it.polimi.ingsw.model.islands.ColorDirectionAdjacentIsland;
import it.polimi.ingsw.model.islands.IslandModel;
import it.polimi.ingsw.model.player.PlayerModel;
import it.polimi.ingsw.network.message.Message;

import java.util.List;

public class GameController {
    private PhaseGame phase;

    public void setPhaseGame(PhaseGame phase){
        this.phase = phase;
    }

    public void onMessageReceived(Message receivedMessage){
        switch (this.phase) {
            case START:
                new StartGameState(GameModel.getInstance()).setInitialGameConfiguration(receivedMessage);
                new AddStudentFromBagToCloudState(GameModel.getInstance()).moveStudentFromBagToClouds();
                phase = PhaseGame.PLAY_CARDS_ASSISTANT;
                break;
            case CHECK_WIN:
                GameModel.getInstance().checkWin();
                break;
            case ADD_STUDENT_TO_ISLAND:
                new StudentToIslandState(receivedMessage);
                this.phase = PhaseGame.ADD_STUDENT_TO_HALL;
                break;
            case ADD_STUDENT_TO_HALL:
                new StudentToHallState(receivedMessage);
                this.phase = PhaseGame.MOVE_MOTHER;
                break;
            case MOVE_MOTHER:
                new MoveMotherNatureState(receivedMessage);
                GameModel gameInstance =  GameModel.getInstance();
                IslandModel islandWithMother = gameInstance.getIslandWithMother();
                PlayerModel playerWithInfluence = islandWithMother.getInfluence(gameInstance);
                if(!islandWithMother.hasTower()) {
                    islandWithMother.setTowerColor(playerWithInfluence.getColorTower());
                }else{
                    PlayerModel playerWithTower = gameInstance.getPlayerByColorTower(islandWithMother.getTowerColor());
                    islandWithMother.setTowerColor(playerWithInfluence.getColorTower());
                    playerWithTower.addTowerToBoard();
                }
                playerWithInfluence.removeTowerFromBoard();


                //controllo se posso unificare, se sì, le unisco
                ColorDirectionAdjacentIsland direction = GameModel.getInstance().getAdjacentSameColor(islandWithMother);
                int indexOfMother = gameInstance.getIslandsModel().indexOf(islandWithMother);
                if(direction == ColorDirectionAdjacentIsland.RIGHT){
                    if(indexOfMother == gameInstance.getIslandsModel().size()-1)
                        new CheckIfJoinableState(gameInstance, islandWithMother).joinIsland(gameInstance.getIslandsModel().get(0), indexOfMother);
                    else
                        new CheckIfJoinableState(gameInstance, islandWithMother).joinIsland(gameInstance.getIslandsModel().get(indexOfMother+1), indexOfMother);
                }
                if(direction == ColorDirectionAdjacentIsland.LEFT){
                    if(indexOfMother == 0)
                        new CheckIfJoinableState(gameInstance, islandWithMother).joinIsland(gameInstance.getIslandsModel().get(gameInstance.getIslandsModel().size()-1), indexOfMother);
                    else
                        new CheckIfJoinableState(gameInstance, islandWithMother).joinIsland(gameInstance.getIslandsModel().get(indexOfMother-1), indexOfMother);
                }
                if(direction == ColorDirectionAdjacentIsland.BOTH){
                    if(indexOfMother == 0) {
                        new CheckIfJoinableState(gameInstance, islandWithMother).joinIsland(gameInstance.getIslandsModel().get(gameInstance.getIslandsModel().size() - 1), 0);
                        new CheckIfJoinableState(gameInstance, islandWithMother).joinIsland(gameInstance.getIslandsModel().get(1), 0);
                    }
                    if(indexOfMother == gameInstance.getIslandsModel().size() - 1) {
                        new CheckIfJoinableState(gameInstance, islandWithMother).joinIsland(gameInstance.getIslandsModel().get(0), indexOfMother);
                        new CheckIfJoinableState(gameInstance, islandWithMother).joinIsland(gameInstance.getIslandsModel().get(indexOfMother+1), indexOfMother);
                    }
                }
                this.phase = PhaseGame.ADD_STUDENT_CLOUD;
                break;
            case PLAYER_MOVE_FROM_CLOUD_TO_ENTRANCE:
                new AddStudentFromCloudToWaitingState(receivedMessage).moveStudentFromCloudToWaiting(receivedMessage);
                this.phase = PhaseGame.ADD_STUDENT_TO_HALL;
                //picking new player from here?
                break;
            case PLAY_CARDS_ASSISTANT:
                PlayerModel player = GameModel.getInstance().getPlayerByNickname(receivedMessage.getNickname());
                PlayCardAssistantState play = new PlayCardAssistantState(player, GameModel.getInstance());

                JsonObject obj = (new Gson()).fromJson(receivedMessage.toString(), JsonObject.class);
                JsonObject priorityJson = obj.getAsJsonObject("priority");
                int priority = new Gson().fromJson(priorityJson, int.class);

                JsonObject movementJson = obj.getAsJsonObject("movement");
                byte movement = new Gson().fromJson(movementJson, byte.class);
                AssistantCardModel card = new AssistantCardModel(priority, movement);

                if(play.canPlayCard(card)){
                    play.playCard(card);
                }
                if (GameModel.getInstance().getIndexOfPlayer(player) == GameModel.getInstance().getPlayersNumber() - 1) {//se è l'ultimo giocatore che ha giocato la carta
                    new DecideOrderPlayerState(GameModel.getInstance()).setPlayersOrderForActionPhase(GameModel.getInstance().getCemetery());
                }
                this.phase = PhaseGame.ADD_STUDENT_TO_ISLAND;
                break;
        }
    }
}

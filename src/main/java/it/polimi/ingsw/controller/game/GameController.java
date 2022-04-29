package it.polimi.ingsw.controller.game;

import it.polimi.ingsw.controller.player.MoveMotherNatureState;
import it.polimi.ingsw.controller.player.PlayCardAssistantState;
import it.polimi.ingsw.controller.player.StudentToHallState;
import it.polimi.ingsw.controller.player.StudentToIslandState;
import it.polimi.ingsw.model.enums.PhaseGame;
import it.polimi.ingsw.model.game.GameModel;
import it.polimi.ingsw.model.islands.ColorDirectionAdjacentIsland;
import it.polimi.ingsw.model.islands.IslandModel;
import it.polimi.ingsw.model.player.PlayerModel;
import it.polimi.ingsw.network.message.Message;

public class GameController {
    private PhaseGame phase;



    public void onMessageReceived(Message receivedMessage){
        switch (this.phase) {
            case START:
                new StartGameState(GameModel.getInstance()).setInitialGameConfiguration(receivedMessage);
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

                }
                
                break;
            case ADD_STUDENT_CLOUD:
                new AddStudentFromBagToCloudState(GameModel.getInstance());
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

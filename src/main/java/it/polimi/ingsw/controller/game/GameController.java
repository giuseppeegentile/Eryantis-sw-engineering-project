package it.polimi.ingsw.controller.game;

import it.polimi.ingsw.controller.player.*;
import it.polimi.ingsw.model.cards.AssistantCardModel;
import it.polimi.ingsw.model.colors.ColorPawns;
import it.polimi.ingsw.model.colors.ColorTower;
import it.polimi.ingsw.model.enums.PhaseGame;
import it.polimi.ingsw.model.game.GameModel;
import it.polimi.ingsw.model.islands.ColorDirectionAdjacentIsland;
import it.polimi.ingsw.model.islands.IslandModel;
import it.polimi.ingsw.model.player.PlayerModel;
import it.polimi.ingsw.network.message.Message;
import it.polimi.ingsw.network.message.PlayAssistantCardMessage;
import it.polimi.ingsw.network.message.StudentToIslandMessage;
import it.polimi.ingsw.network.message.TextMessage;
import it.polimi.ingsw.view.VirtualView;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;


public class GameController {
    private PhaseGame phase;
    private transient Map<String, VirtualView> virtualViewMap;
    private final GameModel gameInstance;
    private PlayerModel playerActive;

    public GameController(){
        this.virtualViewMap = Collections.synchronizedMap(new HashMap<>());
        this.gameInstance =  GameModel.getInstance();
    }

    public PhaseGame getPhaseGame(){return this.phase;}

    public void setPhaseGame(PhaseGame phase){
        this.phase = phase;
    }

    public void onMessageReceived(Message receivedMessage){
        switch (this.phase) {
            case START:
                boolean insertSuccess =new StartGameState(gameInstance).receiveAndSetTowerAndPlayer(receivedMessage);
                if(!insertSuccess){
                    //manda messaggio: scegli un'altra torre

                }
                if(gameInstance.getPlayersNumber()==gameInstance.getPlayersModel().size()){
                    new StartGameState(gameInstance).setInitialGameConfiguration();
                    new AddStudentFromBagToCloudState(gameInstance).moveStudentFromBagToClouds();
                    for (String player : virtualViewMap.keySet()) {
                        PlayerModel p = gameInstance.getPlayerByNickname(player);
                        virtualViewMap.get(player).showInitialTowerMessage(player, p.getColorTower(), p.getTowerNumber());
                        virtualViewMap.get(player).showDeckMessage(player, p.getDeckAssistantCardModel());

                        virtualViewMap.get(player).showHallMessage(player, p.getStudentInHall());

                        virtualViewMap.get(player).showEntranceMessage(player, p.getStudentInEntrance());

                        virtualViewMap.get(player).showClouds();
                    }
                    playerActive = gameInstance.getPlayersModel().get(0);
                    gameInstance.setPhaseOrder(gameInstance.getPlayersModel());
                    setPhaseGame(PhaseGame.PLAY_CARDS_ASSISTANT);
                    break;
                }
                phase = PhaseGame.START;
                break;

            case MOVE_FROM_BAG_TO_CLOUD:
                if(gameInstance.havePlayersFinishedCards()) {
                    this.checkWin();
                    break;
                }else{
                    new AddStudentFromBagToCloudState(gameInstance).moveStudentFromBagToClouds();

                    for (String player : virtualViewMap.keySet()) {
                        virtualViewMap.get(player).showClouds();
                    }
                    if(gameInstance.getBag().size()==0){
                        checkWin();
                    }else{
                        phase = PhaseGame.PLAY_CARDS_ASSISTANT;
                    }
                }
                break;
            case CHECK_WIN:
                this.checkWin();
                break;
            case ADD_STUDENT_TO_ISLAND:
                new StudentToIslandState(receivedMessage, playerActive);
                int indexIsland = ((StudentToIslandMessage)receivedMessage).getIndexIsland();
                IslandModel islandModel = gameInstance.getIslandsModel().get(indexIsland);
                virtualViewMap.get(playerActive.getNickname()).showNewIsland(playerActive.getNickname(), islandModel, indexIsland);
                this.phase = PhaseGame.ADD_STUDENT_TO_HALL;
                break;
            case ADD_STUDENT_TO_HALL:
                new StudentToHallState(receivedMessage, playerActive);
                //PlayerModel playerModel = gameInstance.getPlayerByNickname(receivedMessage.getNickname());
                virtualViewMap.get(playerActive.getNickname()).showNewHall(playerActive.getNickname(), (HashMap<ColorPawns, Integer>) playerActive.getStudentInHall());
                this.phase = PhaseGame.MOVE_MOTHER;
                break;
            case MOVE_MOTHER:
                new MoveMotherNatureState(receivedMessage, playerActive);

                IslandModel islandWithMother = gameInstance.getIslandWithMother();
                PlayerModel playerWithInfluence = islandWithMother.getInfluence(gameInstance);
                if(playerWithInfluence.getColorTower()!=ColorTower.NULL) {
                    if (!islandWithMother.hasTower()) {
                        islandWithMother.setTowerColor(playerWithInfluence.getColorTower());
                    } else {
                        PlayerModel playerWithTower = gameInstance.getPlayerByColorTower(islandWithMother.getTowerColor());
                        islandWithMother.setTowerColor(playerWithInfluence.getColorTower());
                        playerWithTower.addTowerToBoard();
                    }

                    playerWithInfluence.removeTowerFromBoard();
                    checkWin();
                    for (String player : virtualViewMap.keySet()) {
                        virtualViewMap.get(player).updateTowerOnIsland(islandWithMother);
                    }

                    virtualViewMap.get(playerActive.getNickname()).updateTowerOnBoard(playerWithInfluence.getNickname(), playerWithInfluence.getTowerNumber());
                }

                //messaggio di attrazione delle isole
                for (String player : virtualViewMap.keySet()) {
                    PlayerModel p = gameInstance.getPlayerByNickname(player);
                    virtualViewMap.get(player).showMessageJoiningIsland(new TextMessage(p.getNickname(), "JOINING ISLANDS..."));
                }

                //controllo se posso unificare, se sì, le unisco
                ColorDirectionAdjacentIsland direction = gameInstance.getAdjacentSameColor(islandWithMother);
                int indexOfMother = gameInstance.getIslandsModel().indexOf(islandWithMother);

                if(direction == ColorDirectionAdjacentIsland.NONE){
                    this.phase = PhaseGame.PLAYER_MOVE_FROM_CLOUD_TO_ENTRANCE;
                    break;
                }

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
                for(PlayerModel p: gameInstance.getPlayersModel()) {
                    virtualViewMap.get(p.getNickname()).updateIslands(gameInstance.getIslandsModel());
                }
                checkWin();
                this.phase = PhaseGame.PLAYER_MOVE_FROM_CLOUD_TO_ENTRANCE;
                break;
            case PLAYER_MOVE_FROM_CLOUD_TO_ENTRANCE:
                new AddStudentFromCloudToWaitingState(receivedMessage, playerActive).moveStudentFromCloudToWaiting(receivedMessage);
                this.phase = PhaseGame.ADD_STUDENT_TO_HALL;
                //picking new player for the next turn
                String nickCurrent = playerActive.getNickname();
                PlayerModel playerCurrent = gameInstance.getPlayerByNickname(nickCurrent);
                int indexCurrent = gameInstance.getPhaseOrder().indexOf(playerActive);

                virtualViewMap.get(nickCurrent).showEndTurn(nickCurrent);
                String nextPlayerNick;
                if(indexCurrent != gameInstance.getPlayersNumber()-1) {
                    nextPlayerNick= gameInstance.getPhaseOrder().get(indexCurrent + 1).getNickname();

                }else{
                    nextPlayerNick = gameInstance.getPhaseOrder().get(0).getNickname();
                }
                virtualViewMap.get(nickCurrent).showStartTurn(nextPlayerNick);
                phase = PhaseGame.ADD_STUDENT_TO_ISLAND;
                break;
            case PLAY_CARDS_ASSISTANT:
                PlayerModel player = gameInstance.getPlayerByNickname(receivedMessage.getNickname());
                PlayCardAssistantState play = new PlayCardAssistantState(player, gameInstance);
                AssistantCardModel card = ((PlayAssistantCardMessage)receivedMessage).getCard();

                if(play.canPlayCard(card)){
                    play.playCard(card);
                    for (String gamer : virtualViewMap.keySet()) {
                        virtualViewMap.get(gamer).updateCemetery(card);
                    }

                    if (gameInstance.getIndexOfPlayer(player) == gameInstance.getPlayersNumber() - 1) {//se è l'ultimo giocatore che ha giocato la carta
                        new DecideOrderPlayerState(gameInstance).setPlayersOrderForActionPhase(gameInstance.getCemetery());
                        if(gameInstance.getPlayersModel().get(0).getDeckAssistantCardModel().size()==0)
                            gameInstance.setTrueHavePlayerFinishedCards();

                        //cambio l'ordine anche per la lista originale

                        gameInstance.setPlayers(gameInstance.getPhaseOrder());
                        this.phase = PhaseGame.ADD_STUDENT_TO_ISLAND;
                    }else {
                        this.phase = PhaseGame.PLAY_CARDS_ASSISTANT;
                        break;
                    }
                }else{
                    //send message error can't play card
                    for (String gamer : virtualViewMap.keySet()) {
                        virtualViewMap.get(gamer).errorCard(gamer, card);
                    }
                }

                break;
        }
    }

    public void broadcastDisconnectionMessage(String nicknameDisconnected, String text) {
        for (VirtualView vv : virtualViewMap.values()) {
            vv.showDisconnectionMessage(nicknameDisconnected, text);
        }
    }

    public void setVirtualViewMap(Map<String, VirtualView> virtualViewMap) {
        this.virtualViewMap = virtualViewMap;
    }

    public Map<String, VirtualView> getVirtualViewMap() {
        return this.virtualViewMap;
    }


    private void checkWin(){
        ColorTower winner = gameInstance.checkWin();
        if(winner != ColorTower.NULL){ //se c'è un vincitore
            String winnerNick = gameInstance.getPlayerByColorTower(winner).getNickname();
            virtualViewMap.get(winnerNick).showWinMessage(gameInstance.getPlayerByNickname(winnerNick));
        }else{
            //manda in un altro stato
        }
    }

    public PlayerModel getPlayerActive() {
        return playerActive;
    }

    public void setPlayerActive(PlayerModel playerModel) {
        this.playerActive = playerModel;
    }
}

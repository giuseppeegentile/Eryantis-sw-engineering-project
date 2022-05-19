package it.polimi.ingsw.controller.game;

import it.polimi.ingsw.controller.player.*;
import it.polimi.ingsw.model.cards.AssistantCardModel;
import it.polimi.ingsw.model.colors.ColorTower;
import it.polimi.ingsw.model.enums.PhaseGame;
import it.polimi.ingsw.model.game.CloudModel;
import it.polimi.ingsw.model.game.GameModel;
import it.polimi.ingsw.model.islands.ColorDirectionAdjacentIsland;
import it.polimi.ingsw.model.islands.IslandModel;
import it.polimi.ingsw.model.player.PlayerModel;
import it.polimi.ingsw.network.message.*;
import it.polimi.ingsw.view.View;
import it.polimi.ingsw.view.VirtualView;
import it.polimi.ingsw.observer.Observer;

import java.io.Serializable;
import java.util.*;

public class GameController implements Observer, Serializable {
    private static final long serialVersionUID = 5892236063958381739L;
    private PhaseGame phase;
    private boolean gameStarted = false;
    private transient Map<String, VirtualView> virtualViewMap;
    private final GameModel gameInstance;
    private PlayerModel playerActive;
    private int numberStudentsMovedToIsland=0;
    private boolean boolForTestPlayedCard = true;
    private List<PlayerModel> playersThatHavePlayedCard;

    public GameController(){
        this.virtualViewMap = Collections.synchronizedMap(new HashMap<>());
        this.gameInstance =  GameModel.getInstance();
        this.phase = PhaseGame.START;
    }

    public PhaseGame getPhaseGame(){return this.phase;}

    public void setPhaseGame(PhaseGame phase){
        this.phase = phase;
    }

    private void showBoard(String nickname){
        PlayerModel playerToDisplay = gameInstance.getPlayerByNickname(nickname);
        List<ColorTower> towers = new ArrayList<>();
        for(int i = 0; i < playerToDisplay.getTowerNumber(); i++){
            towers.add(playerToDisplay.getColorTower());
        }
        virtualViewMap.get(nickname).showPlayerBoardMessage(nickname,
                towers,
                playerToDisplay.getStudentInHall(),
                playerToDisplay.getStudentInEntrance(),
                playerToDisplay.getProfs()
        );
    }

    /**
     * Switch on Game State.
     *
     * @param receivedMessage Message from players to server.
     */
    public void onMessageReceived(Message receivedMessage){

        switch (receivedMessage.getMessageType()){
            case LOGIN_REPLY:
                String nickMessage = receivedMessage.getNickname();
                handleLogin(nickMessage, virtualViewMap.get(nickMessage));

                break;
            case PLAYERNUMBER_REPLY:
                gameInstance.setPlayerNumber(((PlayerNumberReply)receivedMessage).getPlayerNumber());
                virtualViewMap.get(receivedMessage.getNickname()).askTowerColor(receivedMessage.getNickname(), getAvailableTowers());
                break;
            case CHOSEN_TOWER:
                System.out.println("here");
                ColorTower chosenTower = ((ChosenTowerMessage)receivedMessage).getColorTowers();
                if(!getAvailableTowers().contains(chosenTower)){//in case the user chose a tower already taken by a player
                                                                //it can happen even if in the client side we show only the
                                                                //available towers, because is async for other players
                        //future feature?: add live update for client when available towers change
                    virtualViewMap.get(receivedMessage.getNickname()).askTowerColor(receivedMessage.getNickname(), getAvailableTowers());
                }else {
                    int numPlayers = gameInstance.getPlayersNumber();
                    setTowers(receivedMessage, chosenTower, numPlayers);
                    //at the first player I ask also the gameMode
                    if (receivedMessage.getNickname().equals(gameInstance.getPlayersModel().get(0).getNickname())) {
                        virtualViewMap.get(receivedMessage.getNickname()).askGameMode();
                        //prepareGame();
                        return;
                    }else if(receivedMessage.getNickname().equals(gameInstance.getPlayersModel().get(gameInstance.getPlayersNumber()-1).getNickname())){
                        prepareGame();
                    }
                }
                break;
            case GAMEMODE_RES:
                gameInstance.setGameMode(((GameModeRes)receivedMessage).getGameMode());
                String nicknameMessage = receivedMessage.getNickname();


                showBoard(nicknameMessage);
                break;
        }


        switch (this.phase) {
            case PLAY_CARDS_ASSISTANT:

                boolForTestPlayedCard = true;
                PlayerModel player = gameInstance.getPlayerByNickname(receivedMessage.getNickname());
                PlayCardAssistantState play = new PlayCardAssistantState(player, gameInstance);
                AssistantCardModel card = ((PlayAssistantCardMessage)receivedMessage).getCard();

                if(!play.canPlayCard(card)){
                    boolForTestPlayedCard = false;
                    //send message error can't play card
                    for (String gamer : virtualViewMap.keySet()) {
                        virtualViewMap.get(gamer).errorCard(gamer, card);
                    }
                    System.out.println(player.getNickname() + " can't play this card");
                    this.phase = PhaseGame.PLAY_CARDS_ASSISTANT;
                    break;
                }

                play.playCard(card);
                playersThatHavePlayedCard.add(player);
                for (String gamer : virtualViewMap.keySet()) {
                    virtualViewMap.get(gamer).showCemeteryMessage(playerActive.getNickname(), gameInstance.getCemetery());
                }

                if (playersThatHavePlayedCard.containsAll(gameInstance.getPlayersModel())) {//se è l'ultimo giocatore che ha giocato la carta
                    playersThatHavePlayedCard = new ArrayList<>(gameInstance.getPlayersNumber());
                    new DecideOrderPlayerState().setPlayersOrderForActionPhase(gameInstance.getCemetery());

                    for(String gamer : virtualViewMap.keySet()){
                        virtualViewMap.get(gamer).showOrderPhase( gamer, gameInstance.getPhaseOrder());
                    }

                    playerActive = gameInstance.getPhaseOrder().get(0);

                    if(gameInstance.getPlayersModel().get(0).getDeckAssistantCardModel().size()==0)
                        gameInstance.setTrueHavePlayerFinishedCards();


                    this.phase = PhaseGame.ADD_STUDENT_TO_ISLAND;
                }else {
                    this.phase = PhaseGame.PLAY_CARDS_ASSISTANT;
                    break;
                }


                break;

            case ADD_STUDENT_TO_ISLAND:
                int maxCanMove = 3; //versione a 2 o 4
                if(gameInstance.getPlayersNumber() == 3) maxCanMove = 4;

                numberStudentsMovedToIsland = ((StudentToIslandMessage)receivedMessage).getStudents().size();
                if(numberStudentsMovedToIsland == 0){//se decide di spostare tutti gli studenti nella hall
                    this.phase = PhaseGame.ADD_STUDENT_TO_HALL;
                    break;
                }else if(numberStudentsMovedToIsland <= maxCanMove) {//se decide di spostare degli studenti nella isola
                    new StudentToIslandState(playerActive).moveStudentToIsland(((StudentToIslandMessage)receivedMessage).getStudents(), ((StudentToIslandMessage)receivedMessage).getIslandModel());
                    int indexIsland = ((StudentToIslandMessage) receivedMessage).getIndexIsland();
                    IslandModel islandModel = gameInstance.getIslandsModel().get(indexIsland);

                    if(!virtualViewMap.isEmpty())
                        virtualViewMap.get(playerActive.getNickname()).showIslandMessage(playerActive.getNickname(), islandModel, indexIsland);

                    if(numberStudentsMovedToIsland == maxCanMove) //vado direttamente nello spostamento di madre natura
                        this.phase = PhaseGame.MOVE_MOTHER;
                    else
                        this.phase = PhaseGame.ADD_STUDENT_TO_HALL;
                    break;
                }
            case ADD_STUDENT_TO_HALL:
                int canMove = 3; //versione a 2 o 4
                if(gameInstance.getPlayersNumber() == 3) canMove = 4;
                if(((StudentToHallMessage)receivedMessage).getStudents().size() != (canMove-numberStudentsMovedToIsland) ){
                    if(!virtualViewMap.isEmpty())
                        virtualViewMap.get(playerActive.getNickname()).showInvalidNumberOfStudentMoved(playerActive.getNickname());
                    this.phase = PhaseGame.ADD_STUDENT_TO_HALL;
                    break;
                }
                new StudentToHallState(playerActive).moveStudentToHall(((StudentToHallMessage)receivedMessage).getStudents());
                if(!virtualViewMap.isEmpty()) {
                    //virtualViewMap.get(playerActive.getNickname()).showHallMessage(playerActive.getNickname(), playerActive.getStudentInHall());
                    showBoard(playerActive.getNickname());
                }
                this.phase = PhaseGame.MOVE_MOTHER;
                break;
            case MOVE_MOTHER:
                int indexOfPlayerActive = gameInstance.getPlayersModel().indexOf(playerActive);
                byte movement = ((MoveMotherNatureMessage)receivedMessage).getMovement();
                byte movementAllowed =gameInstance.getCemetery().get(indexOfPlayerActive).getMotherNatureMovement();
                if(movement > playerActive.getMovementMotherNatureCurrentActionPhase()){
                    if(!virtualViewMap.isEmpty())
                        virtualViewMap.get(playerActive.getNickname()).showInvalidMovementMessage(playerActive.getNickname(), movementAllowed, movement );
                    phase = PhaseGame.MOVE_MOTHER;
                    break;
                }
                for(PlayerModel p: gameInstance.getPlayersModel()) {
                    if(!virtualViewMap.isEmpty()) {
                        virtualViewMap.get(p.getNickname()).showMoveMotherNatureMessage(p.getNickname(), movement);
                    }
                }

                new MoveMotherNatureState(playerActive).moveMotherNature(movement);

                IslandModel islandWithMother = gameInstance.getIslandWithMother();
                int indexOfMother = gameInstance.getIslandsModel().indexOf(islandWithMother);
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
                    if(playerWithInfluence.getTowerNumber()==0) {
                        if(!virtualViewMap.isEmpty())
                            virtualViewMap.get(playerActive.getNickname()).showWinMessage(gameInstance.getPlayerByNickname(playerActive.getNickname()));
                    }
                    for (String p : virtualViewMap.keySet()) {
                        virtualViewMap.get(p).showIslandMessage(p,islandWithMother, indexOfMother);
                    }
                    if(!virtualViewMap.isEmpty())//shows the updated board (towers changed) to the player who has influence on this island
                        showBoard(playerWithInfluence.getNickname());
                }

                //messaggio di attrazione delle isole
                for (String pp : virtualViewMap.keySet()) {
                    PlayerModel p = gameInstance.getPlayerByNickname(pp);
                    virtualViewMap.get(pp).showMessageJoiningIsland(new TextMessage(p.getNickname(), "JOINING ISLANDS..."));
                }

                //controllo se posso unificare, se sì, le unisco
                ColorDirectionAdjacentIsland direction = gameInstance.getAdjacentSameColor(islandWithMother);

                if(direction == ColorDirectionAdjacentIsland.NONE){
                    this.phase = PhaseGame.PLAYER_MOVE_FROM_CLOUD_TO_ENTRANCE;
                    break;
                }

                if(direction == ColorDirectionAdjacentIsland.RIGHT){
                    if(indexOfMother == gameInstance.getIslandsModel().size()-1)
                        new CheckIfJoinableState(islandWithMother).joinIsland(gameInstance.getIslandsModel().get(0), indexOfMother);
                    else
                        new CheckIfJoinableState(islandWithMother).joinIsland(gameInstance.getIslandsModel().get(indexOfMother+1), indexOfMother);
                }
                if(direction == ColorDirectionAdjacentIsland.LEFT){
                    if(indexOfMother == 0)
                        new CheckIfJoinableState(islandWithMother).joinIsland(gameInstance.getIslandsModel().get(gameInstance.getIslandsModel().size()-1), indexOfMother);
                    else
                        new CheckIfJoinableState(islandWithMother).joinIsland(gameInstance.getIslandsModel().get(indexOfMother-1), indexOfMother);
                }
                if(direction == ColorDirectionAdjacentIsland.BOTH){
                    if(indexOfMother == 0) {
                        new CheckIfJoinableState(islandWithMother).joinIsland(gameInstance.getIslandsModel().get(gameInstance.getIslandsModel().size() - 1), 0);
                        new CheckIfJoinableState(islandWithMother).joinIsland(gameInstance.getIslandsModel().get(1), 0);
                    }
                    if(indexOfMother == gameInstance.getIslandsModel().size() - 1) {
                        new CheckIfJoinableState(islandWithMother).joinIsland(gameInstance.getIslandsModel().get(0), indexOfMother);
                        new CheckIfJoinableState(islandWithMother).joinIsland(gameInstance.getIslandsModel().get(indexOfMother+1), indexOfMother);
                    }
                }
                for(PlayerModel p: gameInstance.getPlayersModel()) {
                    String n = p.getNickname();
                    if(!virtualViewMap.isEmpty()) {
                        virtualViewMap.get(n).showIslands(n, gameInstance.getIslandsModel());
                        if(!p.getProfs().isEmpty())
                            showBoard(n);
                    }
                }
                if(gameInstance.getIslandsModel().size()==3){
                    checkWin();
                }
                this.phase = PhaseGame.PLAYER_MOVE_FROM_CLOUD_TO_ENTRANCE;
                break;

            case PLAYER_MOVE_FROM_CLOUD_TO_ENTRANCE:
                int cloudIndex = ((AddStudentFromCloudToEntranceMessage)receivedMessage).getCloudIndex();
                CloudModel chosenCloudByPlayer = GameModel.getInstance().getCloudsModel().get(cloudIndex);

                if(chosenCloudByPlayer.getStudents().size() == 0) {
                    //Mandare messaggio di scegliere una nuvola non scelta da un altro player
                    if (!virtualViewMap.isEmpty())
                        virtualViewMap.get(playerActive.getNickname()).showInvalidCloud(playerActive.getNickname());
                    phase = PhaseGame.PLAYER_MOVE_FROM_CLOUD_TO_ENTRANCE;
                    break;
                }
                //se la nuvola scelta è valida continua
                new AddStudentFromCloudToWaitingState(playerActive).moveStudentFromCloudToWaiting(receivedMessage);

                //picking new player for the next turn
                String nickCurrent = playerActive.getNickname();
                int indexCurrent = gameInstance.getPhaseOrder().indexOf(playerActive);

                if(!virtualViewMap.isEmpty())
                    virtualViewMap.get(nickCurrent).showEndTurn(nickCurrent);

                String nextPlayerNick;
                if (indexCurrent != gameInstance.getPlayersNumber() - 1) {
                    nextPlayerNick = gameInstance.getPhaseOrder().get(indexCurrent + 1).getNickname();
                } else {
                    fromBagToCloud();
                    break;
                }
                int lastIndex = gameInstance.getPlayersNumber()-1;
                if(playerActive.equals(gameInstance.getPhaseOrder().get(lastIndex))) { //se è l'ultimo giocatore ad aver giocato
                    phase = PhaseGame.PLAY_CARDS_ASSISTANT;                             //fai giocare le carte a tutti i giocatori
                }else{
                    playerActive = gameInstance.getPlayerByNickname(nextPlayerNick);
                    if(!virtualViewMap.isEmpty())
                        virtualViewMap.get(nextPlayerNick).showStartTurn(nextPlayerNick);
                    phase = PhaseGame.ADD_STUDENT_TO_ISLAND;
                }
                break;


            case CHECK_WIN:
                this.checkWin();
                break;

        }
    }

    private boolean finished() {
        return gameInstance.getPlayersModel().size() == gameInstance.getPlayersNumber();
    }

    private void setTowers(Message receivedMessage, ColorTower chosenTower, int numPlayers) {
        if(numPlayers != 4) {
            if (numPlayers == 3)
                gameInstance.getPlayerByNickname(receivedMessage.getNickname()).setTowers(chosenTower, 6);
            else
                gameInstance.getPlayerByNickname(receivedMessage.getNickname()).setTowers(chosenTower, 8);
        }
        else{
            //se invece sono a 4 giocatori, avrò il team mate
            if(getAvailableTowers().containsAll(List.of(chosenTower, chosenTower)))
                gameInstance.getPlayerByNickname(receivedMessage.getNickname()).setTowers(chosenTower, 8);
            else
                gameInstance.getPlayerByNickname(receivedMessage.getNickname()).setTowers(chosenTower, 0);
        }
    }

    public void handleLogin(String nickname, VirtualView vv){
        if(virtualViewMap.isEmpty()){ // at the first player I ask the number of players
            this.virtualViewMap.put(nickname, vv);
            gameInstance.addObserver(vv);
            vv.showLoginResult(true, true, "SERVER_NICKNAME");
            gameInstance.addPlayer(new PlayerModel(nickname));
            vv.askPlayersNumber();
            //vv.askTowerColor(nickname, getAvailableTowers());
        }else if(virtualViewMap.size() < gameInstance.getPlayersNumber()){
            if(checkLoginNickname(nickname, vv)) {
                this.virtualViewMap.put(nickname, vv);
                gameInstance.addObserver(vv);
                gameInstance.addPlayer(new PlayerModel(nickname));
                vv.showLoginResult(true, true, "SERVER_NICKNAME");
            }
        }
    }

    private List<ColorTower> getAvailableTowers(){
        List<ColorTower> alreadyChosen = new ArrayList<>();
        for (PlayerModel p : gameInstance.getPlayersModel()) {
            alreadyChosen.add(p.getColorTower());
        }
        if(gameInstance.getPlayersNumber()==2){
            List<ColorTower> result = new ArrayList<>(List.of(ColorTower.BLACK, ColorTower.WHITE));
            result.removeAll(alreadyChosen);
            return result;
        }
        else if(gameInstance.getPlayersNumber()==3){
            List<ColorTower> result = new ArrayList<>(List.of(ColorTower.BLACK, ColorTower.WHITE, ColorTower.GREY));
            result.removeAll(alreadyChosen);
            return result;
        }
        else{
            List<ColorTower> result = new ArrayList<>(List.of(ColorTower.BLACK, ColorTower.WHITE, ColorTower.BLACK, ColorTower.WHITE));
            result.removeAll(alreadyChosen);
            return result;
        }
    }


    public void broadcastDisconnectionMessage(String nicknameDisconnected, String text) {
        for (VirtualView vv : virtualViewMap.values()) {
            vv.showDisconnectionMessage(nicknameDisconnected, text);
        }
    }

    public void addVirtualViewMap(String nickname, VirtualView virtualView) {
        virtualViewMap.put(nickname, virtualView);
        gameInstance.addObserver(virtualView);

    }

    public Map<String, VirtualView> getVirtualViewMap() {
        return this.virtualViewMap;
    }


    private void checkWin(){
        ColorTower winner = gameInstance.checkWin();
        if(winner != ColorTower.NULL){ //se c'è un vincitore
            String winnerNick = gameInstance.getPlayerByColorTower(winner).getNickname();
            if(!virtualViewMap.isEmpty())
                virtualViewMap.get(winnerNick).showWinMessage(gameInstance.getPlayerByNickname(winnerNick));
        }else{
            //manda in un altro stato
        }
    }

    public PlayerModel getPlayerActive() {
        return playerActive;
    }

    public boolean checkLoginNickname(String nickname, View view) {
        if (nickname.isEmpty()) {
            view.showGenericMessage("Forbidden name.");
            view.showLoginResult(false, true, null);
            return false;
        } else if (gameInstance.isNicknameTaken(nickname)) {
            System.out.println(nickname);
            view.showGenericMessage("Nickname already taken.");
            view.showLoginResult(false, true, null);
            return false;
        }
        return true;
    }

    private void prepareGame(){
        StartGameState startState = new StartGameState();

        if(gameInstance.getPlayersNumber()==gameInstance.getPlayersModel().size()){
            startState.setInitialGameConfiguration();
            gameStarted = true;
            new AddStudentFromBagToCloudState().moveStudentFromBagToClouds();
            for (String player : virtualViewMap.keySet()) {
                PlayerModel p = gameInstance.getPlayerByNickname(player);
                virtualViewMap.get(player).showDeckMessage(player, p.getDeckAssistantCardModel());

                showBoard(player);

                virtualViewMap.get(player).showCloudsMessage(player, gameInstance.getCloudsModel());
                virtualViewMap.get(player).showIslands(player, gameInstance.getIslandsModel());
            }
            playerActive = gameInstance.getPlayersModel().get(0);
            gameInstance.setPhaseOrder(gameInstance.getPlayersModel());


            for (String player : virtualViewMap.keySet()) {
                List<AssistantCardModel> playerDeck = gameInstance.getPlayerByNickname(player).getDeckAssistantCardModel();
                List<AssistantCardModel> playableCards = new ArrayList<>(playerDeck);
                playableCards.removeAll(gameInstance.getCemetery());
                if(!playableCards.isEmpty()) //if player has some cards not in the cemetery he can play that cards
                    virtualViewMap.get(player).askPlayCards(player, playableCards);
                else                         //if the cemetery is equals to all cards he owns, he can play whatever card he want
                    virtualViewMap.get(player).askPlayCards(player, playerDeck);
            }
            playersThatHavePlayedCard = new ArrayList<>(gameInstance.getPlayersNumber());
            setPhaseGame(PhaseGame.PLAY_CARDS_ASSISTANT);
        }
    }

    private void fromBagToCloud(){
        if(gameInstance.havePlayersFinishedCards() || gameInstance.getBag().size()==0) {
            this.checkWin();
        }else{
            new AddStudentFromBagToCloudState().moveStudentFromBagToClouds();
            gameInstance.setPlayers(gameInstance.getPhaseOrder());

            for (String player : virtualViewMap.keySet()) {
                virtualViewMap.get(player).showCloudsMessage(player, gameInstance.getCloudsModel());
            }
            phase = PhaseGame.PLAY_CARDS_ASSISTANT;
        }
    }

    public void removeVirtualView(String nickname) {
        VirtualView vv = virtualViewMap.remove(nickname);

        gameInstance.removeObserver(vv);
        gameInstance.removePlayerByNickname(nickname);
    }


    public boolean isGameStarted() {
        return gameStarted;
    }

    public boolean isBoolForTestPlayedCard() {
        return boolForTestPlayedCard;
    }

    @Override
    public void update(Message message) {
        VirtualView virtualView = virtualViewMap.get(playerActive.getNickname());
        //...
    }
}

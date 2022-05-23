package it.polimi.ingsw.controller.game;

import it.polimi.ingsw.controller.player.*;
import it.polimi.ingsw.model.cards.AssistantCardModel;
import it.polimi.ingsw.model.colors.ColorPawns;
import it.polimi.ingsw.model.colors.ColorTower;
import it.polimi.ingsw.model.enums.GameMode;
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
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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

    private int numberPlayersPlayedCard;

    public GameController(){
        this.virtualViewMap = Collections.synchronizedMap(new HashMap<>());
        this.gameInstance =  GameModel.getInstance();
        this.phase = PhaseGame.START;
        this.numberPlayersPlayedCard = 0;
        this.playersThatHavePlayedCard = new ArrayList<>();
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
                ColorTower chosenTower = ((ChosenTowerMessage)receivedMessage).getColorTowers();
                if(!getAvailableTowers().contains(chosenTower)){//in case the user chose a tower already taken by a player
                                                                //it can happen even if in the client side we show only the
                                                                //available towers, because is async for other players
                        //future feature?: add live update for client when available towers change
                    virtualViewMap.get(receivedMessage.getNickname()).askTowerColor(receivedMessage.getNickname(), getAvailableTowers());
                }else {
                    int numPlayers = gameInstance.getPlayersNumber();
                    setTowers(receivedMessage, chosenTower, numPlayers);
                    setInitialStudentEntrance(gameInstance.getPlayerByNickname(receivedMessage.getNickname()));
                    //at the first player I ask also the gameMode
                    if (receivedMessage.getNickname().equals(gameInstance.getPlayersModel().get(0).getNickname())) {
                        virtualViewMap.get(receivedMessage.getNickname()).askGameMode();
                        //prepareGame();
                    }else if(receivedMessage.getNickname().equals(gameInstance.getPlayersModel().get(gameInstance.getPlayersNumber()-1).getNickname())){
                        gameStarted = true;
                        //prepareGame();
                        for(String nick: virtualViewMap.keySet()) {
                            virtualViewMap.get(nick).showCloudsMessage(nick, gameInstance.getCloudsModel());
                            virtualViewMap.get(nick).showIslands(nick, gameInstance.getIslandsModel());

                            showBoard(nick);
                            //virtualViewMap.get(nick).showDeckMessage(nick, gameInstance.getPlayerByNickname(nick).getDeckAssistantCardModel());

                        }
                        playerActive = gameInstance.getPlayersModel().get(0);
                        //virtualViewMap.get(playerActive.getNickname()).askPlayCards(playerActive.getNickname(), playerActive.getDeckAssistantCardModel());

                        gameInstance.setPhaseOrder(gameInstance.getPlayersModel());
                        askPlayCardsController(playerActive.getNickname());
                    }
                }
                break;
            case GAMEMODE_RES:
                gameInstance.setGameMode(((GameModeRes)receivedMessage).getGameMode());
                /*if(((GameModeRes)receivedMessage).getGameMode() == GameMode.PRINCIPIANTE){

                }*/
                setIslands();
                setClouds();
                playerActive = gameInstance.getPlayersModel().get(0);
                break;

            case PLAYED_ASSISTANT_CARD:
                playersThatHavePlayedCard.add(gameInstance.getPlayerByNickname(receivedMessage.getNickname()));
                numberPlayersPlayedCard++;
                for (String gamer : virtualViewMap.keySet()) {
                    virtualViewMap.get(gamer).showCemeteryMessage(playerActive.getNickname(), gameInstance.getCemetery());
                }

                AssistantCardModel playedCard = ((PlayAssistantCardMessage)receivedMessage).getCard();
                PlayerModel player = gameInstance.getPlayerByNickname(receivedMessage.getNickname());
                playCard(player, playedCard);

                if(numberPlayersPlayedCard != gameInstance.getPlayersNumber())
                    askPlayCardsController(gameInstance.getPhaseOrder().get(numberPlayersPlayedCard).getNickname());
                else {//è l'ultimo giocatore ad aver giocato la carta
                    playersThatHavePlayedCard = new ArrayList<>(gameInstance.getPlayersNumber());
                    numberPlayersPlayedCard = 0;
                    setPlayersOrderForActionPhase();
                    playerActive = gameInstance.getPlayersModel().get(0);

                    if(playerActive.getDeckAssistantCardModel().size()==0)//se ha finito tutte le carte
                        gameInstance.setTrueHavePlayerFinishedCards();
                    virtualViewMap.get(playerActive.getNickname()).askMoveEntranceToIsland(playerActive.getNickname(), playerActive.getStudentInEntrance());
                }
                break;
            case PLAYER_MOVED_STUDENTS_ON_ISLAND:
                List<ColorPawns> movedStudents = ((MovedStudentOnIslandMessage)receivedMessage).getStudents();
                IslandModel islandCurrent = gameInstance.getIslandsModel().get(((MovedStudentOnIslandMessage)receivedMessage).getIslandIndex());
                moveStudentToIsland(playerActive, movedStudents, islandCurrent);


                for(String gamer: virtualViewMap.keySet()) {
                    virtualViewMap.get(gamer).showIslands(playerActive.getNickname(), gameInstance.getIslandsModel());
                }

                if(gameInstance.getPlayersNumber()%2 == 0) {
                    virtualViewMap.get(playerActive.getNickname()).askMoveEntranceToHall(playerActive.getNickname(), playerActive.getStudentInEntrance(), 3-movedStudents.size());
                }else{
                    virtualViewMap.get(playerActive.getNickname()).askMoveEntranceToHall(playerActive.getNickname(), playerActive.getStudentInEntrance(), (4-movedStudents.size()));
                }

                break;
            case PLAYER_MOVED_STUDENTS_ON_HALL:
                if (((MovedStudentToHallMessage) receivedMessage).getStudents() != null) {
                    moveStudentToHall(playerActive, ((MovedStudentToHallMessage) receivedMessage).getStudents());
                }
                showBoard(playerActive.getNickname());
                virtualViewMap.get(receivedMessage.getNickname()).askMotherNatureMovements(playerActive.getNickname(), playerActive.getMovementMotherNatureCurrentActionPhase());
                break;

            case PLAYER_MOVED_MOTHER:
                byte movement = ((MovedMotherNatureMessage)receivedMessage).getMovement();
                byte movementAllowed = playerActive.getMovementMotherNatureCurrentActionPhase();

                if(movement > movementAllowed){
                    virtualViewMap.get(playerActive.getNickname()).showInvalidMovementMessage(playerActive.getNickname(), movementAllowed, movement);
                    virtualViewMap.get(playerActive.getNickname()).askMotherNatureMovements(playerActive.getNickname(), movementAllowed);
                    break;
                }

                moveMotherNature(movement);
                computeIslandsChanges();
                virtualViewMap.get(playerActive.getNickname()).askMoveCloudToEntrance(playerActive.getNickname(), getAvailableClouds());
                break;
            case MOVED_CLOUD_TO_ENTRANCE:
                
                break;
        }


        switch (this.phase) {
            case MOVE_MOTHER:
                int indexOfPlayerActive = gameInstance.getPlayersModel().indexOf(playerActive);
                byte movement = ((MovedMotherNatureMessage)receivedMessage).getMovement();
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

    /**
     * Set the number of towers for each player according to the number of players
     * @param chosenTower tower's color chosen by the player
     * @param numPlayers number of players
     */

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

    /**
     * Handles the login procedure for each player
     */

    public void handleLogin(String nickname, VirtualView vv){
        if(virtualViewMap.isEmpty()){ // at the first player I ask the number of players
            virtualViewMap.put(nickname, vv);
            gameInstance.addObserver(vv);
            assignBag();
            generateDeck();
            vv.showLoginResult(true, true, "SERVER_NICKNAME");
            gameInstance.addPlayer(new PlayerModel(nickname));
            SetOwnerDeck(nickname);
            assignCardsToPlayer(nickname);
            vv.askPlayersNumber();
            //vv.askTowerColor(nickname, getAvailableTowers());
        }else if(virtualViewMap.size() < gameInstance.getPlayersNumber()){
            if(checkLoginNickname(nickname, vv)) {
                System.out.println("here");
                this.virtualViewMap.put(nickname, vv);
                gameInstance.addObserver(vv);
                gameInstance.addPlayer(new PlayerModel(nickname));
                SetOwnerDeck(nickname);
                assignCardsToPlayer(nickname);
                vv.showLoginResult(true, true, "SERVER_NICKNAME");
                vv.askTowerColor(nickname, getAvailableTowers());
            }
        }
    }

    /**
     * Method that assigns 10 cards to each player after the deck has been created
     */

    private void assignCardsToPlayer(String nickname){
        List<AssistantCardModel> deckPlayer = new ArrayList<>(gameInstance.getDeck().subList((gameInstance.getPlayersModel().indexOf(gameInstance.getPlayerByNickname(nickname))+1)*10-10, (gameInstance.getPlayersModel().indexOf(gameInstance.getPlayerByNickname(nickname))+1)*10));
        gameInstance.getPlayerByNickname(nickname).setDeckAssistantCardModel(deckPlayer);

    }

    private void SetOwnerDeck(String nickname){
        List<AssistantCardModel> deck = gameInstance.getDeck();

        AtomicInteger i = new AtomicInteger();

        deck.forEach(c->{
            if(i.get() < gameInstance.getPlayersModel().indexOf(gameInstance.getPlayerByNickname(nickname)) && i.get() >=10)
                c.setOwner(gameInstance.getPlayersModel().get(1));
            i.getAndIncrement();
        });
    }
    /**
     * Assigns the tower's color to the players according to the number of players
     */

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

    /**
     * Shows a disconnection message when a player disconnects
     * @param text message shown
     */


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

    /**
     * Checks if the winning conditions are observed
     */

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

    /**
     * Checks if the nickname chosen by a player is valid
     * @param nickname nickname chosen by a player
     * @param view view assigned to a player
     * @return returns false if the nickname is incorrect or true if it's valid
     */

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

    /**
     * Checks if a player has cards that can be played
     */

    private void askPlayCardsController(String player){
        List<AssistantCardModel> playerDeck = gameInstance.getPlayerByNickname(player).getDeckAssistantCardModel();
        List<AssistantCardModel> playableCards = new ArrayList<>(playerDeck);
        playableCards.removeAll(gameInstance.getCemetery());
        if(!playableCards.isEmpty()) //if player has some cards not in the cemetery he can play that cards
            virtualViewMap.get(player).askPlayCard(player, playableCards);
        else                         //if the cemetery is equals to all cards he owns, he can play whatever card he wants
            virtualViewMap.get(player).askPlayCard(player, playerDeck);
    }

    private void prepareGame(){
        StartGameState startState = new StartGameState();

        if(gameInstance.getPlayersNumber()==gameInstance.getPlayersModel().size()){
            startState.setInitialGameConfiguration();
            gameStarted = true;
            new AddStudentFromBagToCloudState().moveStudentFromBagToClouds();
            for (String player : virtualViewMap.keySet()) {
                PlayerModel p = gameInstance.getPlayerByNickname(player);
                //virtualViewMap.get(player).showDeckMessage(player, p.getDeckAssistantCardModel());

                showBoard(player);

                virtualViewMap.get(player).showCloudsMessage(player, gameInstance.getCloudsModel());
                virtualViewMap.get(player).showIslands(player, gameInstance.getIslandsModel());
            }
            playerActive = gameInstance.getPlayersModel().get(0);
            gameInstance.setPhaseOrder(gameInstance.getPlayersModel());


            String player = gameInstance.getPhaseOrder().get(0).getNickname();
            askPlayCardsController(player);

            playersThatHavePlayedCard = new ArrayList<>(gameInstance.getPlayersNumber());
            setPhaseGame(PhaseGame.PLAY_CARDS_ASSISTANT);
        }
    }

    /**
     * Adds a student from the bag to a cloud
     */

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

    /**
     * Removes the virtual view of a player when a player's nickname is removed
     */

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
    //-------------------Metodi di startGameState per inizializzare il gioco
    /**
     * Set islands, with mother nature and initial students configuration.
     */
    private void setIslands(){
        int motherNatureIndex = (int)(Math.random() * 12); //numero casuale fra 0 e 11
        List<IslandModel> islands = new ArrayList<>(12);

        int sizeIslandWithStudents = 10;
        int equalNumber = 2;
        List<ColorPawns> colors = new ArrayList<>(sizeIslandWithStudents);
        colors = fillListWithColors(equalNumber);
        //colors è una lista con 10 colori, 2 per ogni colore, riempita casualmente: come se fosse il sacchetto

        int indexMirrorMotherNature = (motherNatureIndex + 6) % 12;

        //counterForColors mi scorre gli elementi dell'array colors, viene incrementato solo quando assegno a un'isola
        for (int i = 0, counterForColors = 0; i < 12; i++) {
            if(i != motherNatureIndex && i != indexMirrorMotherNature) {
                islands.add(new IslandModel(false, colors.get(counterForColors)));
                counterForColors++;
            }
            else if(i == indexMirrorMotherNature){//posizione specchio di madre natura dove non ci sono studenti
                islands.add(new IslandModel(false));
            }
            else if(i == motherNatureIndex) //posizione di madre natura
                islands.add(new IslandModel(true));
        }
        gameInstance.setIslands(islands);
    }

    //prende una generica lista di studenti e la riempie casualmente
    //usata per riempire la bag e le isole iniziali
    //size: dimensione da riempire (bag: 120, isole: 10)
    //equalNumber: quantità uguali per ogni colore (bag: 24(=120/5)  isole: 2)
    /**
     * Takes a generic list of ColorPawns and fills it randomly. Mainly used for the bag and initial islands.
     * @param equalNumber Number of same colors that will be in the List.
     * @return A list of ColorPawns filled with random values in the same quantity for every value.
     */
    private List<ColorPawns> fillListWithColors(int equalNumber){
        List<ColorPawns> listGreen = new ArrayList<>(Collections.nCopies(equalNumber, ColorPawns.GREEN));
        List<ColorPawns> listRed = new ArrayList<>(Collections.nCopies(equalNumber, ColorPawns.RED));
        List<ColorPawns> listYellow = new ArrayList<>(Collections.nCopies(equalNumber, ColorPawns.YELLOW));
        List<ColorPawns> listPink = new ArrayList<>(Collections.nCopies(equalNumber, ColorPawns.PINK));
        List<ColorPawns> listBlue = new ArrayList<>(Collections.nCopies(equalNumber, ColorPawns.BLUE));

        List<ColorPawns> listToRet = Stream.of(listGreen, listRed, listYellow, listPink, listBlue)
                .flatMap(Collection::stream)
                .collect(Collectors.toList());

        Collections.shuffle(listToRet);
        return listToRet;
    }

    /**
     * Creates the student's bag at every game's beginning
     */

    private void assignBag(){
        List<ColorPawns> bag;
        int equalNumber = 24;
        bag = fillListWithColors(equalNumber);
        gameInstance.setBag(bag);
    }

    /**
     * Sets the clouds at every game's beginning according to playerSize
     */

    private void setClouds(){
        int playerSize = gameInstance.getPlayersNumber();
        int cloudsNumber, sizeStudentsClouds;
        if(playerSize % 2 == 0) sizeStudentsClouds = 3;
        else sizeStudentsClouds = 4;

        cloudsNumber = playerSize;
        List<CloudModel> cloudModels = new ArrayList<>(cloudsNumber);
        for(int i = 0; i < cloudsNumber; i++){
            cloudModels.add(new CloudModel(sizeStudentsClouds));

        }
        gameInstance.setCloudsModel(cloudModels);
        moveStudentFromBagToClouds();
    }

    /**
     * Sets the initial number of students at the entrance of the gaming board according to the number of players,
     * 9 if there are 3 players, 7 if there are 2 or 4 players
     * @param playerToSet is the current player to receive the initial number of students
     */

    private void setInitialStudentEntrance(PlayerModel playerToSet){
        int playerNumber = gameInstance.getPlayersNumber();
        int numStudentEntrance = 7; //gioco a 4 o 2
        if (playerNumber == 3) numStudentEntrance = 9; //gioco a 3

        int bagSize = gameInstance.getBag().size();
        List<ColorPawns> studentInEntrance = new ArrayList<>(gameInstance.getBag().subList(bagSize - numStudentEntrance,bagSize));
        //List<ColorPawns> studentInEntrance = gameInstance.getBag().subList(bagSize - numStudentEntrance,bagSize);
        playerToSet.setStudentInEntrance(studentInEntrance);


        List<ColorPawns> updatedBag = new ArrayList<>(gameInstance.getBag().subList(0, bagSize - numStudentEntrance));
        gameInstance.setBag(updatedBag);

    }

    /**
     * Method that generates and assigns a deck of cards for each player
     */

    void generateDeck(){
        for(int k = 0; k < 4; k++) {
            byte j = 0;
            for (int i = 0; i < 10; i++) {
                if (i % 2 == 0) j++;
                gameInstance.addCardToDeck(new AssistantCardModel(i + 1, j));
            }
        }
    }


    /**
     * Moves a student from the bag to the clouds. The number of students moved is different according to the number of players.
     */
    private void moveStudentFromBagToClouds(){
        GameModel gameInstance = GameModel.getInstance();
        int numStudentToMove = 3; //caso base: gioco a 2 o 4 devo spostare 3 studenti dal sacchetto all'isola
        if(gameInstance.getPlayersNumber() == 3) //con 3 giocatori ne sposto 4
            numStudentToMove = 4;

        int bagSize = gameInstance.getBag().size();
        int i = 0;
        for (CloudModel c :gameInstance.getCloudsModel()) {

            List<ColorPawns> temp = new ArrayList<>(gameInstance.getBag().subList(bagSize - numStudentToMove*(i+1), bagSize-numStudentToMove*i));

            c.setStudents(temp);// prendo dalla bag gli ultimi 3 studenti
            //gameInstance.getBag().subList(bagSize - numStudentToMove - 1, bagSize - 1).clear(); //rimuove gli studenti appena spostati

            i++;
        }
        List<ColorPawns> temp = new ArrayList<>(gameInstance.getBag().subList(0, gameInstance.getBag().size() - gameInstance.getPlayersNumber()*numStudentToMove));
        //RIMUOVERE QUI GLI ELEMENTI DALLA BAG, ALTRIMENTI CREA CASINI
        gameInstance.setBag(temp);
    }


    /**
     * Check if a player can play or not a certain card, if yes puts it in the cemetery and removes it from the player's cards
     * @param assistantCardModel The card played by the player
     */
    private void playCard(PlayerModel player, AssistantCardModel assistantCardModel){
        gameInstance.addToCemetery(assistantCardModel);

        int index = player.getDeckAssistantCardModel().indexOf(assistantCardModel);

        player.removeCard(index);
    }

    //chiamato quando tutti i giocatori hanno usato la loro carta
    //prende in input: la lista delle carte giocate, nell ordine di gioco. Restituisce una lista modificata con l ordine per la prossima fase azione
    /**
     * Called when all the players have played their card. Sets the order phase list.
     */
    private void setPlayersOrderForActionPhase(){

        List<PlayerModel> playersActionPhase = new ArrayList<>(gameInstance.getCemetery().size());

        List<AssistantCardModel> copyOfCemetery = new ArrayList<>(gameInstance.getCemetery());

        //ordina le carte in base alla priorità, a pari priorità si basa sull indice
        copyOfCemetery.sort(Comparator.comparing(AssistantCardModel::getPriority)
                .thenComparingInt(copyOfCemetery::indexOf));

        int i = 0;
        gameInstance.clearPhaseOrder();
        for(AssistantCardModel c: copyOfCemetery){
            //aggiunge il giocatore alla lista nell ordine della priorità
            playersActionPhase.add(c.getOwner());
            //imposta il movimento di madre natura per la fase azione (lo ricavo dalla carta assistente)
            playersActionPhase.get(i).setMovementMotherNatureCurrentActionPhase(c.getMotherNatureMovement());
            i += 1;
        }
        gameInstance.setPhaseOrder(playersActionPhase);
    }

    /**
     * Moves the students from the entrance to the island
     * @param students The students to place on the island
     * @param islandModel The island where the students have to be placed
     */
    public void moveStudentToIsland(PlayerModel player, List<ColorPawns> students, IslandModel islandModel){
        islandModel.addStudent(students);
        player.removeStudentFromEntrance(students);
    }

    /**
     * Moves a student from the entrance to the hall of the player
     */
    public void moveStudentToHall(PlayerModel player, List<ColorPawns> students) {
        for(ColorPawns student: students) {
            //conta le occorrenze per ogni studente di un colore
            if (player.getStudentInHall().get(student) + 1 % 3 == 0 && GameModel.getInstance().getGameMode() == GameMode.ESPERTO) { //se lo studente che sto per aggiungere è 3° 6° o 9° prende una moneta
                //addCoins();
            }

            player.getStudentInHall().put(student, player.getStudentInHall().get(student) + 1);

            if(canProfBeAssignedToPlayer(player, student))
                assignProfToPlayer(player, student);

            player.removeStudentFromEntrance(student);
        }
    }

    /** da TESTARE
     * Assign a prof to the player. If another player has that prof, it removes the prof from him
     * @param prof prof to be assigned to player
     */
    private void assignProfToPlayer(PlayerModel player, ColorPawns prof){
        List<PlayerModel> playersModels = GameModel.getInstance().getPlayersModel();
        boolean alreadyHave = false;
        for (PlayerModel p : playersModels) {
            //se un altro giocatore ha già il prof
            if(p.getProfs() != null) {
                if (!Objects.equals(player.getNickname(), p.getNickname()) && p.getProfs().contains(prof)) {
                    //lo tolgo a chi lo aveva prima
                    p.removeProf(prof);
                    player.addProf(prof);
                    alreadyHave = true;
                }
            }
        }
        if(!alreadyHave)
            player.addProf(prof);
    }

    /**
     * Checks if a prof can be assigned to a player, if he has the highest number of students of the same color in the hall
     * @param prof prof to be assigned
     */

    private boolean canProfBeAssignedToPlayer(PlayerModel player, ColorPawns prof){
        for(PlayerModel p: GameModel.getInstance().getPlayersModel()){
            if(!player.getNickname().equals(p.getNickname())){
                if(p.getStudentInHall().get(prof) > player.getStudentInHall().get(prof)) return false; //se uno studente ha più pedine del colore del prof, non può essere assegnato
            }
        }
        return true;
    }



    //modifica la lista di Isole con madre natura aggiornata
    /**
     * Updates the list of island with the new position of mother nature
     * @param movementMotherNature The number of movements mother nature is allowed to do
     */
    private void moveMotherNature(byte movementMotherNature){
        int indexOldMotherNature = 0;
        List<IslandModel> islandsModels = gameInstance.getIslandsModel();
        while(!islandsModels.get(indexOldMotherNature).getMotherNature()) indexOldMotherNature++;

        int newIndex = (indexOldMotherNature + movementMotherNature) % 11;
        IslandModel oldIslandWithMotherNature = new IslandModel(false, islandsModels.get(indexOldMotherNature).getStudents());
        IslandModel newIslandWithMotherNature = new IslandModel(true,  islandsModels.get(newIndex).getStudents());

        islandsModels.set(indexOldMotherNature, oldIslandWithMotherNature);
        islandsModels.set(newIndex, newIslandWithMotherNature);
        gameInstance.setIslands(islandsModels);
    }

    private void computeIslandsChanges(){
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
                showBoard(playerWithTower.getNickname()); //shows the updated board (towers changed) to the player who has lost influence on this island
            }

            playerWithInfluence.removeTowerFromBoard();
            if(playerWithInfluence.getTowerNumber()==0) {
                virtualViewMap.get(playerActive.getNickname()).showWinMessage(gameInstance.getPlayerByNickname(playerActive.getNickname()));
                //virtualViewMap.get(playerActive.getNickname()).showEndGame(); //foreach player. the showEndGame should also exit the game
                return;
            }
            for (String p : virtualViewMap.keySet()) {
                virtualViewMap.get(p).showIslandMessage(p,islandWithMother, indexOfMother);
            }

            showBoard(playerWithInfluence.getNickname());//shows the updated board (towers changed) to the player who has influence on this island
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
            String activeNick = playerActive.getNickname();

            virtualViewMap.get(activeNick).askMoveCloudToEntrance(activeNick, getAvailableClouds());
            return;
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
            virtualViewMap.get(n).showIslands(n, gameInstance.getIslandsModel());
            virtualViewMap.get(n).showTextMessage(n, "Updated profs:\n");
            if(!p.getProfs().isEmpty())
                showBoard(n);

        }
        if(gameInstance.getIslandsModel().size()==3){
            checkWin();
        }
        String activeNick = playerActive.getNickname();

        virtualViewMap.get(activeNick).askMoveCloudToEntrance(activeNick, getAvailableClouds());
    }

    private List<CloudModel> getAvailableClouds(){
        List<CloudModel> availableClouds = new ArrayList<>();

        for(CloudModel c: gameInstance.getCloudsModel()){
            if(c.getStudents().size() != 0) availableClouds.add(c);
        }
        return availableClouds;
    }
}





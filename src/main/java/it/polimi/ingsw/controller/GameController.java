package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.cards.AssistantCardModel;
import it.polimi.ingsw.model.cards.CharacterCardModel;
import it.polimi.ingsw.model.colors.ColorPawns;
import it.polimi.ingsw.model.colors.ColorTower;
import it.polimi.ingsw.model.effects.*;
import it.polimi.ingsw.model.enums.GameMode;
import it.polimi.ingsw.model.game.CloudModel;
import it.polimi.ingsw.model.game.GameModel;
import it.polimi.ingsw.model.islands.ColorDirectionAdjacentIsland;
import it.polimi.ingsw.model.islands.IslandModel;
import it.polimi.ingsw.model.player.PlayerModel;
import it.polimi.ingsw.network.message.*;
import it.polimi.ingsw.observer.Observer;
import it.polimi.ingsw.view.View;
import it.polimi.ingsw.view.VirtualView;

import java.io.Serializable;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class GameController implements Observer, Serializable {
    private static final long serialVersionUID = 5892236063958381739L;
    private boolean gameStarted = false;
    private transient Map<String, VirtualView> virtualViewMap;
    private final GameModel gameInstance;
    private PlayerModel playerActive;
    private List<PlayerModel> playersThatHavePlayedCard;
    private boolean activatedEffect = false;
    private String effectPlayed;
    private boolean shuffle = false;
    private CharacterCardModel characterCardPlayed;
    private int numberPlayersPlayedCard;
    private PlayerModel playerWithEffectAdditionalInfluence;
    private ColorPawns colorToExclude = null;
    private boolean considerTower = true;
    private List<ColorPawns> movedStudents;
    private byte movement;
    private MessageType oldState;
    private Message oldMessage;
    private int test = 0;

    /**
     * Set the player with additional influence on this turn, caused by character card effect
     * @param player the player who's going to have additional influence
     */
    public void setPlayerWithEffectAdditionalInfluence(PlayerModel player){
        this.playerWithEffectAdditionalInfluence = player;
    }

    /**
     * Game controller. Handles the phase of the game. On the server side.
     */
    public GameController(){
        this.virtualViewMap = Collections.synchronizedMap(new HashMap<>());
        this.gameInstance =  GameModel.getInstance();
        this.numberPlayersPlayedCard = 0;
        this.playersThatHavePlayedCard = new ArrayList<>();
        playerWithEffectAdditionalInfluence = null;
    }

    /**
     * Send the board to client with a message
     * @param nickname player's going to receive the board
     */
    private void showBoard(String nickname){
        PlayerModel playerToDisplay = gameInstance.getPlayerByNickname(nickname);
        List<ColorTower> towers = new ArrayList<>();
        for(int i = 0; i < playerToDisplay.getTowerNumber(); i++){
            towers.add(playerToDisplay.getColorTower());
        }
        virtualViewMap.get(nickname).showPlayerBoardMessage(gameInstance.getPlayerByNickname(nickname),
                towers,
                playerToDisplay.getStudentInHall(),
                playerToDisplay.getStudentInEntrance(),
                playerToDisplay.getProfs()
        );
    }

    public Map<String, VirtualView> getVirtualViewMap(){
        return this.virtualViewMap;
    }

    /**
     * send a message showing who's playing on the current turn
     */
    private void showWhosPlaying(){
        for(PlayerModel p: gameInstance.getPlayersModel()){
            if(!p.getNickname().equals(playerActive.getNickname()))
                virtualViewMap.get(playerActive.getNickname()).showGenericMessage("Hey " + p.getNickname() + ", "+  playerActive.getNickname() + " is playing...");
        }
    }
    /**
     * Switch on Game State. Logic of the game.
     *
     * @param receivedMessage Message from client to server.
     */
    public void onMessageReceived(Message receivedMessage) {

        switch (receivedMessage.getMessageType()) {
            case REQ_PLAYER_BOARD:
                String nickname = ((ReqRealPlayerBoardMessage)receivedMessage).getNickToSend();
                PlayerModel playerToDisplay = gameInstance.getPlayerByNickname(nickname);
                List<ColorTower> towers = new ArrayList<>();
                for(int i = 0; i < playerToDisplay.getTowerNumber(); i++){
                    towers.add(playerToDisplay.getColorTower());
                }
                virtualViewMap.get(receivedMessage.getNickname()).showPlayerBoardMessage(
                        GameModel.getInstance().getPlayerByNickname(nickname),
                        towers,
                        playerToDisplay.getStudentInHall(),
                        playerToDisplay.getStudentInEntrance(),
                        playerToDisplay.getProfs()
                );
                break;
            case REQ_LOBBY:
                List<PlayerModel> players = new ArrayList<>();
                for(PlayerModel p: gameInstance.getPlayersModel()){
                    if(!p.getNickname().equals(receivedMessage.getNickname())) players.add(p);
                }
                virtualViewMap.get(receivedMessage.getNickname()).showLobbyMessage(players);
                break;
            case LOGIN_REPLY:
                String nickMessage = receivedMessage.getNickname();
                handleLogin(nickMessage, virtualViewMap.get(nickMessage));
                break;
            case PLAYERNUMBER_REPLY:
                gameInstance.setPlayerNumber(((PlayerNumberReply) receivedMessage).getPlayerNumber());
                virtualViewMap.get(receivedMessage.getNickname()).askTowerColor(receivedMessage.getNickname(), getAvailableTowers(), gameInstance.getGameMode());
                break;
            case CHOSEN_TOWER:
                ColorTower chosenTower = ((ChosenTowerMessage) receivedMessage).getColorTowers();
                if (!getAvailableTowers().contains(chosenTower)) {//in case the user chose a tower already taken by a player
                    //it can happen even if in the client side we show only the
                    //available towers, because is async for other players
                    //future feature?: add live update for client when available towers change
                    virtualViewMap.get(receivedMessage.getNickname()).askTowerColor(receivedMessage.getNickname(), getAvailableTowers(), gameInstance.getGameMode());
                } else {
                    int numPlayers = gameInstance.getPlayersNumber();
                    setTowers(receivedMessage, chosenTower, numPlayers);
                    setInitialStudentEntrance(gameInstance.getPlayerByNickname(receivedMessage.getNickname()));
                    //at the first player I ask also the gameMode
                    if (receivedMessage.getNickname().equals(gameInstance.getPlayersModel().get(0).getNickname())) {
                        virtualViewMap.get(receivedMessage.getNickname()).askGameMode();
                        //prepareGame();
                    } else if (receivedMessage.getNickname().equals(gameInstance.getPlayersModel().get(gameInstance.getPlayersNumber() - 1).getNickname())) {
                        gameStarted = true;
                        //prepareGame();
                        for (String nick : virtualViewMap.keySet()) {
                            virtualViewMap.get(nick).showCloudsMessage(nick, gameInstance.getCloudsModel());
                            virtualViewMap.get(nick).showIslands(nick, gameInstance.getIslandsModel());
                            showBoard(nick);
                            //virtualViewMap.get(nick).showDeckMessage(nick, gameInstance.getPlayerByNickname(nick).getDeckAssistantCardModel());

                        }
                        if(gameInstance.getGameMode() == GameMode.ADVANCED){
                            setCharacterCards();
                            for(PlayerModel pl : gameInstance.getPlayersModel())
                                pl.setCoins();
                        }
                        playerActive = gameInstance.getPlayersModel().get(0);
                        //virtualViewMap.get(playerActive.getNickname()).askPlayCards(playerActive.getNickname(), playerActive.getDeckAssistantCardModel());

                        gameInstance.setPhaseOrder(gameInstance.getPlayersModel());
                        askPlayCardsController(playerActive.getNickname());
                    }
                }
                break;
            case GAMEMODE_RES:
                gameInstance.setGameMode(((GameModeRes) receivedMessage).getGameMode());

                setIslands();
                setClouds();
                playerActive = gameInstance.getPlayersModel().get(0);
                break;
            case PLAYED_ASSISTANT_CARD:
                PlayerModel player = gameInstance.getPlayerByNickname(receivedMessage.getNickname());
                playersThatHavePlayedCard.add(player);
                numberPlayersPlayedCard++;


                int indexPlayedCard = ((PlayAssistantCardMessage) receivedMessage).getIndexCard();

                AssistantCardModel playedCard = player.getDeckAssistantCardModel().get(indexPlayedCard);

                playCard(player, playedCard);
                for (String gamer : virtualViewMap.keySet()) {
                    virtualViewMap.get(gamer).showCemeteryMessage(playerActive.getNickname(), gameInstance.getCemetery());
                }
                if (numberPlayersPlayedCard != gameInstance.getPlayersNumber())
                    askPlayCardsController(gameInstance.getPhaseOrder().get(numberPlayersPlayedCard).getNickname());
                else {//è l'ultimo giocatore ad aver giocato la carta
                    playersThatHavePlayedCard = new ArrayList<>(gameInstance.getPlayersNumber());
                    numberPlayersPlayedCard = 0;
                    setPlayersOrderForActionPhase();
                    gameInstance.setPlayers(gameInstance.getPhaseOrder()); //aggiorno la lista con l'ordine nuovo
                    playerActive = gameInstance.getPlayersModel().get(0);
                    virtualViewMap.get(playerActive.getNickname()).showStartTurn(playerActive.getNickname());
                    if (playerActive.getDeckAssistantCardModel().size() == 0)//se ha finito tutte le carte
                        gameInstance.setTrueHavePlayerFinishedCards();
                    showWhosPlaying();
                    boolean temp = askCharacter(MessageType.PLAYED_ASSISTANT_CARD);
                    if(!temp) {
                        virtualViewMap.get(playerActive.getNickname()).askMoveEntranceToIsland(playerActive.getNickname(), playerActive.getStudentInEntrance(), gameInstance.getIslandsModel());
                    }
                }
                break;
            case CHARACTER_CARD_PLAYED:
                characterCardPlayed = ((PlayedCharacterCardMessage)receivedMessage).getCharacterPlayed();
                if(characterCardPlayed != null) {
                    effectPlayed = characterCardPlayed.getEffect().getClass().getSimpleName();
                    InitialConfigEffect initialConfigEffect;
                    switch (this.effectPlayed) {
                        case "AddToHallEffect":
                            initialConfigEffect = (InitialConfigEffect) characterCardPlayed.getEffect();
                            virtualViewMap.get(playerActive.getNickname()).askStudentFromCardToHall(playerActive.getNickname(), initialConfigEffect.getStudents());
                            break;
                        case "AddToIslandEffect":
                            initialConfigEffect = (InitialConfigEffect) characterCardPlayed.getEffect();
                            virtualViewMap.get(playerActive.getNickname()).askMoveStudentFromCardToIsland(playerActive.getNickname(), gameInstance.getIslandsModel(), initialConfigEffect.getStudents());
                            break;
                        case "ExchangeConfigEntranceEffect":
                            initialConfigEffect = (InitialConfigEffect) characterCardPlayed.getEffect();
                            virtualViewMap.get(playerActive.getNickname()).askMoveFromCardToEntrance(playerActive.getNickname(), initialConfigEffect.getStudents(), playerActive.getStudentInEntrance());
                            break;
                        case "ExchangeHallEntranceEffect":
                            virtualViewMap.get(playerActive.getNickname()).askStudentsChangeEntranceHall(playerActive.getNickname(), playerActive.getStudentInEntrance(), playerActive.getStudentInHall());
                            break;
                        case "ExcludeColorInfluenceEffect":
                            virtualViewMap.get(playerActive.getNickname()).askColorStudentToIgnore(playerActive.getNickname());
                            break;
                        case "PickIslandInfluenceEffect":
                            virtualViewMap.get(playerActive.getNickname()).askExtraGetInfluence(playerActive.getNickname(), gameInstance.getIslandsModel());
                            break;
                        case "ProhibitionEffect":
                            virtualViewMap.get(playerActive.getNickname()).askMoveBanCard(playerActive.getNickname(), gameInstance.getIslandsModel());
                            break;
                        default:
                            performEffectAndReset(characterCardPlayed);
                            break;  //come lo mando avanti?
                    }
                }else{
                    continueFromOldState();
                }
                break;

            case EFFECT_CARD_PLAYED:
                switch (this.effectPlayed) {
                    case "AddToHallEffect":
                        AddToHallEffect addToHallEffect = (AddToHallEffect)characterCardPlayed.getEffect();
                        MovedStudentFromCardToHall movedStudentFromCardToHall = (MovedStudentFromCardToHall)receivedMessage;
                        addToHallEffect.choose(movedStudentFromCardToHall.getPickedStudent());
                        break;
                    case "AddToIslandEffect":
                        AddToIslandEffect addToIslandEffect = (AddToIslandEffect)characterCardPlayed.getEffect();
                        MovedFromCardToIsland movedFromCardToIsland = (MovedFromCardToIsland)receivedMessage;
                        addToIslandEffect.choose(movedFromCardToIsland.getStudentToMove(), movedFromCardToIsland.getIslandIndex());
                        break;
                    case "ExchangeConfigEntranceEffect":
                        ExchangeConfigEntranceEffect exchangeConfigEntranceEffect = (ExchangeConfigEntranceEffect)characterCardPlayed.getEffect();
                        MovedFromCardToEntrance movedFromCardToEntrance = (MovedFromCardToEntrance)receivedMessage;
                        exchangeConfigEntranceEffect.choose(movedFromCardToEntrance.getStudentsFromCard(), movedFromCardToEntrance.getStudentsFromEntrance());
                        break;
                    case "ExchangeHallEntranceEffect":
                        ExchangeHallEntranceEffect exchangeHallEntranceEffect = (ExchangeHallEntranceEffect)characterCardPlayed.getEffect();
                        ChosenChangeEntranceHall chosenChangeEntranceHall = (ChosenChangeEntranceHall)receivedMessage;
                        exchangeHallEntranceEffect.choose(chosenChangeEntranceHall.getStudentsFromEntrance(), chosenChangeEntranceHall.getStudentsFromHall());
                        break;
                    case "ExcludeColorInfluenceEffect":
                        //ExcludeColorInfluenceEffect excludeColorInfluenceEffect = (ExcludeColorInfluenceEffect)characterCardPlayed.getEffect();
                        ChosenColorToIgnore chosenColorToIgnore = (ChosenColorToIgnore)receivedMessage;
                        //excludeColorInfluenceEffect.choose(chosenColorToIgnore.getChosenColor());
                        this.colorToExclude =  chosenColorToIgnore.getChosenColor();
                        break;
                    case "PickIslandInfluenceEffect":
                        PickIslandInfluenceEffect pickIslandInfluenceEffect = (PickIslandInfluenceEffect)characterCardPlayed.getEffect();
                        ExtraGetInfluence extraGetInfluence = (ExtraGetInfluence)receivedMessage;
                        pickIslandInfluenceEffect.choose(extraGetInfluence.getIndexIsland());
                        break;
                    case "ProhibitionEffect":
                        ProhibitionEffect prohibitionEffect = (ProhibitionEffect)characterCardPlayed.getEffect();
                        MovedBanCardMessage movedBanCardMessage = (MovedBanCardMessage)receivedMessage;
                        prohibitionEffect.choose(movedBanCardMessage.getIndexIsland());
                        break;
                    //mancherebbe l'ultimo effetto
                }
                performEffectAndReset(characterCardPlayed);
                break;

            case PLAYER_MOVED_STUDENTS_ON_ISLAND:
                this.oldMessage = receivedMessage;
                studentsOnIslandActions(receivedMessage);
                askCharacter(MessageType.PLAYER_MOVED_STUDENTS_ON_ISLAND);
                break;
            case PLAYER_MOVED_STUDENTS_ON_HALL:
                this.oldMessage = receivedMessage;
                studentsOnHallActions(receivedMessage);
                askCharacter(MessageType.PLAYER_MOVED_STUDENTS_ON_HALL);
                virtualViewMap.get(playerActive.getNickname()).showEntranceChange(playerActive.getNickname(), playerActive.getStudentInEntrance());
                for(String s: virtualViewMap.keySet()) {
                    PlayerModel p = gameInstance.getPlayerByNickname(s);
                    List<ColorTower> updatedTowers = new ArrayList<>();
                    for(int j = 0; j < p.getTowerNumber(); j++) updatedTowers.add(p.getColorTower());
                    virtualViewMap.get(s).showPlayerBoardMessage(p,
                            updatedTowers,
                            p.getStudentInHall(),
                            p.getStudentInEntrance(),
                            p.getProfs()
                    );
                }
                break;
            case PLAYER_MOVED_MOTHER:
                this.oldMessage = receivedMessage;
                this.movement = ((MovedMotherNatureMessage) receivedMessage).getMovement();
                motherActions(this.movement);
                break;
            case MOVED_CLOUD_TO_ENTRANCE:
                int cloudIndex = ((AddStudentFromCloudToEntranceMessage) receivedMessage).getCloudIndex();
                CloudModel chosenCloud = gameInstance.getCloudsModel().get(cloudIndex);
                System.out.println(chosenCloud.getStudents().get(0));
                if (chosenCloud.getStudents().size() == 0) { //if cloud isn't valid
                    //Mandare messaggio di scegliere una nuvola non scelta da un altro player
                    virtualViewMap.get(playerActive.getNickname()).showInvalidCloud(playerActive.getNickname());
                    virtualViewMap.get(playerActive.getNickname()).askMoveCloudToEntrance(playerActive.getNickname(), getAvailableClouds());
                    break;
                }
                //ask another one
                boolean successfulMove =moveStudentFromCloudToWaiting(receivedMessage);
                if(!successfulMove){
                    virtualViewMap.get(receivedMessage.getNickname()).askMoveCloudToEntrance(receivedMessage.getNickname(), getAvailableClouds());
                    break;
                }

                virtualViewMap.get(receivedMessage.getNickname()).showEntranceChange(receivedMessage.getNickname(), playerActive.getStudentInEntrance());
                //picking new player for the next turn
                int indexCurrent = 0;
                for(PlayerModel p: gameInstance.getPlayersModel()){
                    if(!p.getNickname().equals(playerActive.getNickname())){
                        indexCurrent++;
                    }
                    else break;
                }
                String nickCurrent = playerActive.getNickname();

                virtualViewMap.get(nickCurrent).showEndTurn(nickCurrent);

                for(String pl: virtualViewMap.keySet()){
                    virtualViewMap.get(pl).showCloudsMessage(pl, gameInstance.getCloudsModel());
                    virtualViewMap.get(pl).showIslands(pl, gameInstance.getIslandsModel());
                }
                int lastIndex = gameInstance.getPlayersNumber() - 1;

                if(GameModel.getInstance().getGameMode() == GameMode.ADVANCED)
                    resetEffects();

                if (receivedMessage.getNickname().equals(gameInstance.getPlayersModel().get(lastIndex).getNickname())) { //se è l'ultimo giocatore ad aver giocato, fai giocare le carte a tutti i giocatori
                    fromBagToCloud();
                    PlayerModel nextPlayer = gameInstance.getPlayersModel().get(0);
                    askPlayCardsController(nextPlayer.getNickname());
                    //virtualViewMap.get(nextPlayer.getNickname()).askPlayCard(nextPlayer.getNickname(), nextPlayer.getDeckAssistantCardModel());
                } else {
                    String nextPlayerNick = gameInstance.getPlayersModel().get(indexCurrent + 1).getNickname();
                    playerActive = gameInstance.getPlayerByNickname(nextPlayerNick);

                    virtualViewMap.get(nextPlayerNick).showStartTurn(nextPlayerNick);
                    showWhosPlaying();
                    askCharacter(MessageType.PLAYED_ASSISTANT_CARD);
                    if(gameInstance.getGameMode() == GameMode.BEGINNER) {
                        virtualViewMap.get(nextPlayerNick).askMoveEntranceToIsland(nextPlayerNick, gameInstance.getPlayerByNickname(nextPlayerNick).getStudentInEntrance(), gameInstance.getIslandsModel());
                    }
                }
                break;

        }
    }

    /**
     * Updates the old state of the game, in particular the movement of students from entrance to islands, from entrance to hall and the play of an assistant card
     */
    private void continueFromOldState() {
        switch (this.oldState) {
            case PLAYER_MOVED_STUDENTS_ON_ISLAND:
                movedStudents = ((MovedStudentOnIslandMessage)oldMessage).getStudents();
                if (gameInstance.getPlayersNumber() % 2 == 0) {
                    if(movedStudents.size() != 3)
                        virtualViewMap.get(playerActive.getNickname()).askMoveEntranceToHall(playerActive.getNickname(), playerActive.getStudentInEntrance(), 3 - movedStudents.size());
                    else
                        virtualViewMap.get(playerActive.getNickname()).askMotherNatureMovements(playerActive, playerActive.getMovementMotherNatureCurrentActionPhase());
                } else {
                    if(movedStudents.size() != 4)
                        virtualViewMap.get(playerActive.getNickname()).askMoveEntranceToHall(playerActive.getNickname(), playerActive.getStudentInEntrance(), 4 - movedStudents.size());
                    else
                        virtualViewMap.get(playerActive.getNickname()).askMotherNatureMovements(playerActive, playerActive.getMovementMotherNatureCurrentActionPhase());
                }
                break;
            case PLAYER_MOVED_STUDENTS_ON_HALL:
                virtualViewMap.get(playerActive.getNickname()).askMotherNatureMovements(playerActive, playerActive.getMovementMotherNatureCurrentActionPhase());
                break;
            case PLAYED_ASSISTANT_CARD:
                virtualViewMap.get(playerActive.getNickname()).askMoveEntranceToIsland(playerActive.getNickname(), playerActive.getStudentInEntrance(), gameInstance.getIslandsModel());
                break;
        }
    }

    /**
     * Enable the effect and return to the phase of the game where the player was before applying the card's effect
     * @param characterCardPlayed the card played sent by the client
     */
    private void performEffectAndReset(CharacterCardModel characterCardPlayed){
        characterCardPlayed.getEffect().enable(playerActive);
        for (CharacterCardModel card : playerActive.getCharacterDeck()){
            if(card.getEffect().getDescription().equals(characterCardPlayed.getEffect().getDescription())){
                card.getEffect().incrementCost();

                break;
            }
        }


        if(characterCardPlayed.getEffect().getClass().getSimpleName().equals("AddToIslandEffect") ||
                characterCardPlayed.getEffect().getClass().getSimpleName().equals("ProhibitionEffect"))
            for(String p: virtualViewMap.keySet()) virtualViewMap.get(p).showIslands(p, gameInstance.getIslandsModel());
        else if(characterCardPlayed.getEffect().getClass().getSimpleName().equals("AddToHallEffect")){
            for(PlayerModel p: gameInstance.getPlayersModel()) {
                List<ColorTower> towers = new ArrayList<>();
                for (int i = 0; i < p.getTowerNumber(); i++) {
                    towers.add(p.getColorTower());
                }
                virtualViewMap.get(p.getNickname()).showPlayerBoardMessage(p,
                        towers, p.getStudentInHall(), p.getStudentInEntrance(), p.getProfs());
            }
        }

        activatedEffect = true;
        continueFromOldState();
    }


    /**
     * All actions occurring by the movement of mother nature: incorrect movement, influence evaluation, possible join of islands, switch to next phase
     * @param movement movement performed by the client
     */
    private void motherActions(byte movement) {
        this.movement = movement;
        byte movementAllowed = playerActive.getMovementMotherNatureCurrentActionPhase();

        if (movement > movementAllowed) {
            virtualViewMap.get(playerActive.getNickname()).showInvalidMovementMessage(playerActive.getNickname(), movementAllowed, movement);
            virtualViewMap.get(playerActive.getNickname()).askMotherNatureMovements(playerActive, movementAllowed);
            return;
        }
        String activeNick = playerActive.getNickname();
        //moveMotherNature(movement);

        if(movement!=0) {
            int indexOldMotherNature = 0;
            List<IslandModel> islandsModels = gameInstance.getIslandsModel();
            while (!islandsModels.get(indexOldMotherNature).getMotherNature()) indexOldMotherNature++;
            int newIndex = (indexOldMotherNature + movement) % (gameInstance.getIslandsModel().size());
            if(!islandsModels.get(newIndex).hasProhibition()) {
                gameInstance.getIslandsModel().get(indexOldMotherNature).setMotherNature(false);
                gameInstance.getIslandsModel().get(newIndex).setMotherNature(true);
                computeIslandsChanges(playerActive, gameInstance.getIslandWithMother());
            }else{
                virtualViewMap.get(activeNick).showSkippingMotherMovement(activeNick);
                gameInstance.getIslandsModel().get(newIndex).setHasProhibition(false);
                for(CharacterCardModel c: playerActive.getCharacterDeck()) {
                    if (c.getEffect().getClass().getSimpleName().equals("ProhibitionEffect"))
                        ((ProhibitionEffect)c.getEffect()).endEffect();
                }
            }
            for(String gamer: virtualViewMap.keySet())
                virtualViewMap.get(gamer).showIslands(gamer, gameInstance.getIslandsModel());
        }

        virtualViewMap.get(activeNick).askMoveCloudToEntrance(activeNick, getAvailableClouds());
    }

    /**
     * Calling the method to move student on hall, showing the board updated and switch to next phase
     * @param receivedMessage message received from the client
     */
    private void studentsOnHallActions(Message receivedMessage) {
        if (((MovedStudentToHallMessage) receivedMessage).getStudents() != null) {
            moveStudentToHall(playerActive, ((MovedStudentToHallMessage) receivedMessage).getStudents(), false);
        }
        showBoard(playerActive.getNickname());
        if(gameInstance.getGameMode()==GameMode.BEGINNER)
            virtualViewMap.get(receivedMessage.getNickname()).askMotherNatureMovements(playerActive, playerActive.getMovementMotherNatureCurrentActionPhase());
    }
    /**
     * Calling the method to move student on island, showing the islands updated and switch to next phase
     * @param receivedMessage message received from the client
     */
    private void studentsOnIslandActions(Message receivedMessage) {
        List<ColorPawns> movedStudents = ((MovedStudentOnIslandMessage) receivedMessage).getStudents();
        this.movedStudents = movedStudents;
        IslandModel islandCurrent = gameInstance.getIslandsModel().get(((MovedStudentOnIslandMessage) receivedMessage).getIslandIndex());
        moveStudentToIsland(playerActive, movedStudents, islandCurrent);
        for (String gamer : virtualViewMap.keySet()) {
            virtualViewMap.get(gamer).showIslands(playerActive.getNickname(), gameInstance.getIslandsModel());
        }
        if(GameMode.BEGINNER == gameInstance.getGameMode()) {
            if (gameInstance.getPlayersNumber() % 2 == 0) {
                if(movedStudents.size() != 3)
                    virtualViewMap.get(playerActive.getNickname()).askMoveEntranceToHall(playerActive.getNickname(), playerActive.getStudentInEntrance(), 3 - movedStudents.size());
                else
                    virtualViewMap.get(playerActive.getNickname()).askMotherNatureMovements(playerActive, playerActive.getMovementMotherNatureCurrentActionPhase());
            } else {
                if(movedStudents.size()  != 4)
                    virtualViewMap.get(playerActive.getNickname()).askMoveEntranceToHall(playerActive.getNickname(), playerActive.getStudentInEntrance(), (4 - movedStudents.size()));
                else
                    virtualViewMap.get(playerActive.getNickname()).askMotherNatureMovements(playerActive, playerActive.getMovementMotherNatureCurrentActionPhase());
            }
        }
    }

    /**
     * Reset eventual effect activated in this phase
     */
    private void resetEffects() {
        colorToExclude = null;
        considerTower = true;
        playerWithEffectAdditionalInfluence = null;
        activatedEffect = false;
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
     * Handles the login procedure for each player. Separate logic to first players from next
     */
    public void handleLogin(String nickname, VirtualView vv){
        if(virtualViewMap.isEmpty()){ // at the first player I ask the number of players
            virtualViewMap.put(nickname, vv);
            gameInstance.addObserver(vv);
            assignBag();
            generateDeck();
            vv.showLoginResult(true, true, "SERVER_NICKNAME");
            gameInstance.addPlayer(new PlayerModel(nickname));
            setOwnerDeck(nickname);
            assignCardsToPlayer(nickname);
            vv.askPlayersNumber();
            //vv.askTowerColor(nickname, getAvailableTowers());
        }else if(virtualViewMap.size() < gameInstance.getPlayersNumber()){
            if(checkLoginNickname(nickname, vv)) {
                this.virtualViewMap.put(nickname, vv);
                gameInstance.addObserver(vv);
                gameInstance.addPlayer(new PlayerModel(nickname));
                setOwnerDeck(nickname);
                assignCardsToPlayer(nickname);
                vv.showLoginResult(true, true, "SERVER_NICKNAME");
                vv.askTowerColor(nickname, getAvailableTowers(), gameInstance.getGameMode());
            }
        }
    }

    /**
     * Method that assigns 10 cards to each player after the deck has been created
     */
    public void assignCardsToPlayer(String nickname){
        List<AssistantCardModel> deckPlayer = new ArrayList<>(gameInstance.getDeck().subList((gameInstance.getPlayersModel().indexOf(gameInstance.getPlayerByNickname(nickname))+1)*10-10, (gameInstance.getPlayersModel().indexOf(gameInstance.getPlayerByNickname(nickname))+1)*10));
        gameInstance.getPlayerByNickname(nickname).setDeckAssistantCardModel(deckPlayer);
    }

    /**
     * Assign to every card of player the owner of that
     * @param nickname player to be given the card
     */
    public void setOwnerDeck(String nickname) {
        PlayerModel playerCorrespond = gameInstance.getPlayerByNickname(nickname);
        int startingIndex = (gameInstance.getPlayersModel().indexOf(playerCorrespond) + 1) * 10 - 10;

        for (int idx = startingIndex; idx < startingIndex + 10; idx++) {
            gameInstance.getDeck().get(idx).setOwner(playerCorrespond);
        }
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

    /**
     * Checks if the winning conditions are observed
     */
    private void checkWin(){
        ColorTower winner = gameInstance.checkWin();
        if(winner != ColorTower.NULL){ //se c'è un vincitore
            String winnerNick = gameInstance.getPlayerByColorTower(winner).getNickname();
            virtualViewMap.get(winnerNick).showWinMessage(gameInstance.getPlayerByNickname(winnerNick));
        }//else
        //manda in un altro stato
    }

    /**
     *
     * @return the player who's playing in this phase
     */
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
        List<AssistantCardModel> playerDeck = new ArrayList<>(gameInstance.getPlayerByNickname(player).getDeckAssistantCardModel());
        List<AssistantCardModel> copy = new ArrayList<>(playerDeck);
        //playerDeck.removeAll(gameInstance.getCemetery());
/*        playerDeck.removeIf(c-> {
            return gameInstance.getCemetery().stream().anyMatch(card ->
                card.getPriority() == c.getPriority() && card.getMotherNatureMovement() == c.getMotherNatureMovement()
            );
        });*/

        for(AssistantCardModel c: playerDeck){
            for(AssistantCardModel cem: gameInstance.getCemetery()){
                if(c.getPriority() == cem.getPriority() && (cem.getMotherNatureMovement() == c.getMotherNatureMovement())) {
                    copy.remove(c);
                }

            }
        }
        virtualViewMap.get(player).askPlayCard(player, copy);
    }

    /**
     * Adds a student from the bag to a cloud
     */
    private void fromBagToCloud(){
        if(gameInstance.havePlayersFinishedCards() || gameInstance.getBag().size()==0) {
            this.checkWin();
        }else{
            moveStudentFromBagToClouds();
            gameInstance.setPlayers(gameInstance.getPhaseOrder());

            for (String player : virtualViewMap.keySet()) {
                virtualViewMap.get(player).showCloudsMessage(player, gameInstance.getCloudsModel());
            }

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

    /**
     *
     * @return true if the game is already started
     */
    public boolean isGameStarted() {
        return gameStarted;
    }

    @Override
    public void update(Message message) {}
    //-------------------Metodi di startGameState per inizializzare il gioco
    /**
     * Set islands, with mother nature and initial students configuration.
     */
    private void setIslands(){
        int motherNatureIndex = (int)(Math.random() * 12); //numero casuale fra 0 e 11
        List<IslandModel> islands = new ArrayList<>(12);

        int equalNumber = 2;
        List<ColorPawns> colors;
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
            else islands.add(new IslandModel(true));
        }
        gameInstance.setIslands(islands);
    }


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
    public void assignBag(){
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
    public void generateDeck(){
        for(int k = 0; k < 4; k++) {
            byte j = 0;
            for (int i = 0; i < 10; i++) {
                if (i % 2 == 0) j++;
                gameInstance.addCardToDeck(new AssistantCardModel(i + 1, j));
            }
        }
        Collections.shuffle(gameInstance.getDeck());
    }


    /**
     * Moves a student from the bag to the clouds. The number of students moved is different according to the number of players.
     */
    private void moveStudentFromBagToClouds(){
        int numStudentToMove = 3; //caso base: gioco a 2 o 4 devo spostare 3 studenti dal sacchetto all'isola
        if(gameInstance.getPlayersNumber() == 3) //con 3 giocatori ne sposto 4
            numStudentToMove = 4;

        int bagSize = gameInstance.getBag().size();
        int i = 0;
        if(gameInstance.getCloudsModel().size()==0) {
            for (int j = 0; j < gameInstance.getPlayersNumber(); j++) {
                gameInstance.getCloudsModel().add(new CloudModel(numStudentToMove));
                List<ColorPawns> temp = new ArrayList<>(gameInstance.getBag().subList(bagSize - numStudentToMove * (j + 1), bagSize - numStudentToMove * j));
                gameInstance.getCloudsModel().get(j).setStudents(temp);// prendo dalla bag gli ultimi 3 studenti
            }
        }else {
            for (CloudModel c : gameInstance.getCloudsModel()) {

                List<ColorPawns> temp = new ArrayList<>(gameInstance.getBag().subList(bagSize - numStudentToMove * (i + 1), bagSize - numStudentToMove * i));

                c.setStudents(temp);// prendo dalla bag gli ultimi 3 studenti
                //gameInstance.getBag().subList(bagSize - numStudentToMove - 1, bagSize - 1).clear(); //rimuove gli studenti appena spostati

                i++;
            }
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
        //int index = player.getDeckAssistantCardModel().indexOf(assistantCardModel);
        gameInstance.addToCemetery(assistantCardModel);
        player.getDeckAssistantCardModel().remove(assistantCardModel);
        //player.removeCard(index);
    }

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
        virtualViewMap.get(player.getNickname()).showEntranceChange(player.getNickname(), player.getStudentInEntrance());
    }

    /**
     * Moves a student from the entrance to the hall of the player
     */
    public void moveStudentToHall(PlayerModel player, List<ColorPawns> students, boolean fromCard) {
        int oldValue = 0;
        for(ColorPawns student: students) {
            oldValue = player.getStudentInHall().get(student);
            player.getStudentInHall().put(student, player.getStudentInHall().get(student) + 1);
            //conta le occorrenze per ogni studente di un colore
            if ( gameInstance.getGameMode() == GameMode.ADVANCED && player.getStudentInHall().get(student) % 3 == 0 && oldValue != player.getStudentInHall().get(student)) { //se lo studente che sto per aggiungere è 3° 6° o 9° prende una moneta
                player.addCoins();
                System.out.println("incremento di 1");
            }

            if(canProfBeAssignedToPlayer(player, student))
                assignProfToPlayer(player, student);

            if(!fromCard)
                player.removeStudentFromEntrance(student);
        }
    }

    /** da TESTARE
     * Assign a prof to the player. If another player has that prof, it removes the prof from him
     * @param prof prof to be assigned to player
     */
    private void assignProfToPlayer(PlayerModel player, ColorPawns prof){
        boolean alreadyHave = false;
        for (PlayerModel p : gameInstance.getPlayersModel()) {
            //se un altro giocatore ha già il prof
            if(p.getProfs() != null) {
                if (!Objects.equals(player.getNickname(), p.getNickname()) && p.getProfs().contains(prof)) {
                    //lo tolgo a chi lo aveva prima
                    p.removeProf(prof);
                    player.addProf(prof);
                    for(PlayerModel pp: gameInstance.getPlayersModel()) {
                        virtualViewMap.get(pp.getNickname()).showTextMessage(pp.getNickname(), "Updated profs:");
                    }
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
        for(PlayerModel p: gameInstance.getPlayersModel()){
            if(!player.getNickname().equals(p.getNickname())){
                if(p.getStudentInHall().get(prof) >= player.getStudentInHall().get(prof)) return false; //se uno studente ha più pedine del colore del prof, non può essere assegnato
            }
        }
        return true;
    }

    /**
     * Updates the list of island with the new position of mother nature
     * @param movementMotherNature The number of movements mother nature is allowed to do
     */
    private void moveMotherNature(byte movementMotherNature){
        if(movementMotherNature==0)return;
        int indexOldMotherNature = 0;
        List<IslandModel> islandsModels = gameInstance.getIslandsModel();
        while(!islandsModels.get(indexOldMotherNature).getMotherNature()) indexOldMotherNature++;
        int newIndex = (indexOldMotherNature + movementMotherNature) % (gameInstance.getIslandsModel().size());
        gameInstance.getIslandsModel().get(indexOldMotherNature).setMotherNature(false);
        gameInstance.getIslandsModel().get(newIndex).setMotherNature(true);
    }

    /**
     * Perform action caused by mother nature movement. Set tower on island, eventually join islands and then check the winner (if any)
     * @param active player who's playing in this turn
     * @param islandWithMother island where mother nature is placed
     */
    public void computeIslandsChanges(PlayerModel active,IslandModel islandWithMother){
        int indexOfMother = gameInstance.getIslandsModel().indexOf(islandWithMother);
        //PlayerModel playerWithInfluence = islandWithMother.getInfluence(considerTower, playerWithEffectAdditionalInfluence,ignoreColorEffect );
        System.out.println(colorToExclude);
        PlayerModel playerWithInfluence = islandWithMother.getInfluence(playerWithEffectAdditionalInfluence, colorToExclude, considerTower);
        if(playerWithInfluence.getColorTower()!=ColorTower.NULL) { //if a player has influence
            if (!islandWithMother.hasTower() && playerWithInfluence.getColorTower() != ColorTower.NULL) {
                islandWithMother.setTowerColor(playerWithInfluence.getColorTower());
            } else {
                PlayerModel playerWithTower = gameInstance.getPlayerByColorTower(islandWithMother.getTowerColor());
                islandWithMother.setTowerColor(playerWithInfluence.getColorTower());
                playerWithTower.addTowerToBoard();
                //showBoard(playerWithTower.getNickname()); //shows the updated board (towers changed) to the player who has lost influence on this island
            }

            playerWithInfluence.removeTowerFromBoard();
            if(playerWithInfluence.getTowerNumber()==0) {
                virtualViewMap.get(active.getNickname()).showWinMessage(gameInstance.getPlayerByNickname(active.getNickname()));
                //virtualViewMap.get(playerActive.getNickname()).showEndGame(); //foreach player. the showEndGame should also exit the game
                return;
            }
            for (String p : virtualViewMap.keySet()) {
                virtualViewMap.get(p).showIslandMessage(p,islandWithMother, indexOfMother);
            }

            showBoard(playerWithInfluence.getNickname());//shows the updated board (towers changed) to the player who has influence on this island
        }

        //controllo se posso unificare, se sì, le unisco
        ColorDirectionAdjacentIsland direction = gameInstance.getAdjacentSameColor(islandWithMother);

        if(islandWithMother.getTowerColor() == ColorTower.NULL || direction == ColorDirectionAdjacentIsland.NONE){
            return;
        }

        //messaggio di attrazione delle isole
        for (String pp : virtualViewMap.keySet()) {
            PlayerModel p = gameInstance.getPlayerByNickname(pp);
            virtualViewMap.get(pp).showMessageJoiningIsland(new TextMessage(p.getNickname(), "JOINING ISLANDS..."));
        }

        if(direction == ColorDirectionAdjacentIsland.RIGHT){
            if(indexOfMother == gameInstance.getIslandsModel().size()-1)
                joinIsland(islandWithMother, gameInstance.getIslandsModel().get(0), indexOfMother);
            else
                joinIsland(islandWithMother, gameInstance.getIslandsModel().get(indexOfMother+1), indexOfMother);
        }
        if(direction == ColorDirectionAdjacentIsland.LEFT){
            if(indexOfMother == 0)
                joinIsland(islandWithMother, gameInstance.getIslandsModel().get(gameInstance.getIslandsModel().size()-1), indexOfMother);
            else
                joinIsland(islandWithMother, gameInstance.getIslandsModel().get(indexOfMother-1), indexOfMother);
        }
        if(direction == ColorDirectionAdjacentIsland.BOTH){
            if(indexOfMother == 0) {
                joinIsland(islandWithMother, gameInstance.getIslandsModel().get(gameInstance.getIslandsModel().size() - 1), 0);
                joinIsland(islandWithMother, gameInstance.getIslandsModel().get(1), 0);
            }
            if(indexOfMother == gameInstance.getIslandsModel().size() - 1) {
                joinIsland(islandWithMother, gameInstance.getIslandsModel().get(0), indexOfMother);
                joinIsland(islandWithMother, gameInstance.getIslandsModel().get((indexOfMother+1)%gameInstance.getIslandsModel().size()), indexOfMother);
            }
        }
        for(PlayerModel p: gameInstance.getPlayersModel()) {
            String n = p.getNickname();
            virtualViewMap.get(n).showTextMessage(n, "Updated islands:");
            virtualViewMap.get(n).showIslands(n, gameInstance.getIslandsModel());
            if(!p.getProfs().isEmpty())
                showBoard(n);

        }
        if(gameInstance.getIslandsModel().size()==3){
            checkWin();
        }
    }

    /**
     *
     * @return all the clouds that the client can choose to move its student on the entrance
     */
    public List<CloudModel> getAvailableClouds(){
        List<CloudModel> availableClouds = new ArrayList<>();

        for(CloudModel c: gameInstance.getCloudsModel()){
            if(c.getStudents().size() != 0) availableClouds.add(c);
        }
        return availableClouds;
    }

    /**
     * Checks if there are students left to move from a cloud to the player's board's entrance
     * @param receivedMessage Message received
     * @return false if there aren't any students left to move
     */
    private boolean moveStudentFromCloudToWaiting(Message receivedMessage){
        PlayerModel playerModel = gameInstance.getPlayerByNickname(receivedMessage.getNickname());
        int cloudIndex = ((AddStudentFromCloudToEntranceMessage)receivedMessage).getCloudIndex();
        CloudModel chosenCloudByPlayer = gameInstance.getCloudsModel().get(cloudIndex);
        if(chosenCloudByPlayer.getStudents().size()!=0) {
            playerModel.getStudentInEntrance().addAll(chosenCloudByPlayer.getStudents());
            //playerModel.setStudentInEntrance(chosenCloudByPlayer.getStudents());
            chosenCloudByPlayer.cleanStudent();
            gameInstance.getCloudsModel().remove(chosenCloudByPlayer);
            return true;
        }else
            return false;
    }

    /**
     * Assign the character card deck to the player (if game mode is expert)
     */
    private void setCharacterCards() {
        List<ColorPawns> subBag = new ArrayList<>(gameInstance.getBag().subList(0, 4));
        List<ColorPawns> subBag2 = new ArrayList<>(gameInstance.getBag().subList(4, 10));
        List<ColorPawns> subBag3 = new ArrayList<>(gameInstance.getBag().subList(10, 14));

        List<Effect> effects = List.of(
                new ExcludeColorInfluenceEffect(this),
                new AddToIslandEffect(subBag),
                new ControlProfEffect(),
                new PickIslandInfluenceEffect(this),
                new ExtraMovementMotherEffect(),
                new ProhibitionEffect(),
                new IgnoreTowerEffect(this),
                new ExchangeConfigEntranceEffect(subBag2),
                new AddInfluenceEffect(this),

                new ExchangeHallEntranceEffect(),
                new AddToHallEffect(subBag3, this)
//*+++++++++++++++++++++++++++++++++++++++++++++
        );

        List<CharacterCardModel> characterDeck = new ArrayList<>(11);
        for(int i = 0; i < 11; i++){
            characterDeck.add(new CharacterCardModel(0,effects.get(i), i));
        }

        if(shuffle)
            Collections.shuffle(characterDeck);
        List<CharacterCardModel> tempToAssign = new ArrayList<>();;
        for(int j = test; j<3 + test; j++) {
            tempToAssign.add(characterDeck.get(j));
            if(characterDeck.get(j).getCharacterId() == 0){
                gameInstance.getBag().subList(0,4).clear();
            }else if(characterDeck.get(j).getCharacterId() == 10 ){
                gameInstance.getBag().subList(10, 14).clear();
            }else if(characterDeck.get(j).getCharacterId() == 6 ){
                gameInstance.getBag().subList(4, 10).clear();
            }
        }
        for(int j = 0; j<gameInstance.getPlayersNumber(); j++)
            gameInstance.getPlayersModel().get(j).assignCharacterDeck(tempToAssign);
    }

    /**
     *
     * @param incr test is incremented by "incr"'s value
     */

    public void incrementTest(int incr){
        test += incr;
    }

    /**
     * Ask the client to play the character card (if game mode is expert)
     * @param oldState the state of the game the player was before calling the effect
     * @return true iff exist a card that can be played (so that has enough money)
     */
    private boolean askCharacter(MessageType oldState){
        this.oldState = oldState;
        boolean existsCardPlayable  =false;
        if(gameInstance.getGameMode() == GameMode.ADVANCED) {
            if (!activatedEffect) {
                existsCardPlayable = playerActive.getCharacterDeck().stream().anyMatch(CharacterCardModel::enoughCoins);
                String active = playerActive.getNickname();
                virtualViewMap.get(active).askPlayCharacterCard(playerActive, playerActive.getCharacterDeck(), existsCardPlayable);

            } else
                continueFromOldState();
        }
        return existsCardPlayable;
    }

    /**
     * Called when the effect of a character card occurs
     * @param colorToExclude color that is not going to be evaluated in the influence
     */
    public void setIgnoreColorEffect(ColorPawns colorToExclude) {
        this.colorToExclude = colorToExclude;

    }

    //prende due isole da unire, e l'indice della prima isola da unire in questione, restituisce la lista delle isole aggiornata con quella unita
    /**
     * Joins the islands with the same influence according to the motherNature's position
     * @param islandToJoin Island to join with
     * @param indexFirstIsland The index of the first island in the islandModels list
     * @return The list of islandModels with the two joined island
     */
    public List<IslandModel> joinIsland(IslandModel islandModelToCheck, IslandModel islandToJoin, int indexFirstIsland){
        int sizeStudentsJoined = islandModelToCheck.getStudents().size() + islandToJoin.getStudents().size(); //calcolo studenti di entrambe le isole
        List<ColorPawns> joinedStudents = new ArrayList<>(sizeStudentsJoined); //inizializzo array degli studenti
        //aggiungo gli studenti delle isole da unire alla lista degli studenti uniti
        joinedStudents.addAll(islandModelToCheck.getStudents());
        joinedStudents.addAll(islandToJoin.getStudents());

        IslandModel joined = new IslandModel(islandModelToCheck.getMotherNature() || islandToJoin.getMotherNature(), joinedStudents);
        joined.setTowerColor(islandToJoin.getTowerColor());
        gameInstance.getPlayerByColorTower(islandToJoin.getTowerColor()).addTowerToBoard();
        //posiziono gli studenti sulla nuova isola
        //islandController.addStudent(joinedStudents);
        joined.setJoined(); //setta a true il valore isJoined
        int indexToRemove = gameInstance.getIslandsModel().indexOf(islandToJoin);
        return compressIsland(gameInstance.getIslandsModel(), joined, indexFirstIsland, indexToRemove);
    }

    /**
     * Utility method. Sets in the position of the first joined island the joining result and remove the other one
     * @param islandModels The list of island in the current game
     * @param joined The joining result of the two islands
     * @param indexFirstIsland Index of the first joined island
     * @return The list of islandModels with the two joined island
     */
    private List<IslandModel> compressIsland(List<IslandModel> islandModels, IslandModel joined, int indexFirstIsland, int indexToRemove){
        islandModels.set(indexFirstIsland, joined);
        islandModels.remove(indexToRemove);
        return islandModels;
    }

    /**
     * Called when the effect of a character card occurs
     * @param considerTower true if the towers are going to be evaluated in the influence algorithm
     */
    public void setConsiderTower(boolean considerTower) {
        this.considerTower = considerTower;
    }

    /**
     *
     * @return value of the effect of consider tower. True if the towers are going to be evaluated in the influence algorithm
     */
    public boolean getConsiderTower(){
        return this.considerTower;
    }

    /**
     * sets shuffle to false
     */

    public void setShuffleFalse() {
        this.shuffle = false;
    }

    /**
     *
     * @return return the player that used the character's card with the additional influence effect
     */

    public PlayerModel getPlayerWithEffectAdditionalInfluence(){
        return playerWithEffectAdditionalInfluence;
    }

    /**
     * @return the color to exclude due to a usage of a character card
     */

    public ColorPawns getColorToExclude(){
        return colorToExclude;
    }
}
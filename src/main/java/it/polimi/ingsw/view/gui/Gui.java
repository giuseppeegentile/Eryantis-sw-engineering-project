package it.polimi.ingsw.view.gui;

import it.polimi.ingsw.model.cards.AssistantCardModel;
import it.polimi.ingsw.model.cards.CharacterCardModel;
import it.polimi.ingsw.model.colors.ColorPawns;
import it.polimi.ingsw.model.colors.ColorTower;
import it.polimi.ingsw.model.game.CloudModel;
import it.polimi.ingsw.model.islands.IslandModel;
import it.polimi.ingsw.model.player.PlayerModel;
import it.polimi.ingsw.network.message.Message;
import it.polimi.ingsw.observer.ViewObservable;
import it.polimi.ingsw.view.View;
import it.polimi.ingsw.view.gui.scene.*;
import javafx.application.Platform;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public class Gui extends ViewObservable implements View {

    private static final String STR_ERROR = "ERROR";
    private GameBoardSceneController boardSceneController;
    private String nickname=null;

    @Override
    public void showWinMessage(PlayerModel winner) {
        WinSceneController winSceneController = new WinSceneController();
        winSceneController.addAllObservers(observers);
        winSceneController.setWinnerNickname(winner.getNickname());
        Platform.runLater(() -> SceneController.changeRootPane(winSceneController, "WinScene.fxml"));
    }

    @Override
    public void showMessageJoiningIsland(Message message){}

    @Override
    public void askMoveCloudToEntrance(String nickname, List<CloudModel> clouds){
        boardSceneController.setClouds(clouds);
    }

    @Override
    public void askMoveEntranceToHall(String player, List<ColorPawns> students, int numberStudentsToMove){
        boardSceneController.setNumberToMove(numberStudentsToMove);
        Platform.runLater(() -> {
            boardSceneController.setTurnLabel("Sposta " + numberStudentsToMove + " studenti dall'ingresso alla sala");
            boardSceneController.entranceDisplay();
        });
    }

    @Override
    public void askMotherNatureMovements(PlayerModel player, byte maxMovement) {
        Platform.runLater(()-> {
            boardSceneController.setTurnLabel("Seleziona un'isola in cui vuoi spostare madre natura");
            boardSceneController.setSubtitleText("Massimo " + maxMovement + " posizioni");
            boardSceneController.islandHandlerMother(maxMovement);
        });

    }

    @Override
    public void askMoveEntranceToIsland(String player, List<ColorPawns> colorPawns, List<IslandModel> islands) {
        Platform.runLater(()-> {
            boardSceneController.setVisibleSkip();
            boardSceneController.setTurnLabel("Sposta fino a 3 studenti dall'ingresso in un'isola");
            //boardSceneController.enableOnlyEntrance();
        });
    }

    @Override
    public void showCemeteryMessage(String player, List<AssistantCardModel> cemetery){}
    @Override
    public void showTextMessage(String player, String text){}

    @Override
    public void showLobbyMessage(List<String> nicknameList){
        /*boardSceneController.setPlayersLobby(nicknameList);
        boardSceneController.addAllObservers(observers);
        Platform.runLater(()->SceneController.changeRootPane(boardSceneController, "GameBoardScene.fxml"));*/
    }

    @Override
    public void showIslandMessage(String nickname, IslandModel islandModel, int islandIndex){}

    @Override
    public void showCloudsMessage(String nickname, List<CloudModel> clouds){
        if(!checkpointBoard)
            boardSceneController = new GameBoardSceneController();
        boardSceneController.setClouds(clouds);
        if(checkpointBoard) Platform.runLater(()->boardSceneController.cloudsDisplay());
    }

    @Override
    public void showMoveMotherNatureMessage(PlayerModel player, byte movement) {

    }

    boolean checkpointBoard = false;
    @Override
    public void showIslands(String nickname, List<IslandModel> islands){
        boardSceneController.setIslands(islands);
        if(checkpointBoard) {
            Platform.runLater(()->boardSceneController.islandsDisplay());
        }
    }

    @Override
    public void showInvalidTower(String player, ColorTower colorTower) {}

    @Override
    public void showLoginResult(boolean nicknameAccepted, boolean connectionSuccessful, String nickname) {
        if (!nicknameAccepted || !connectionSuccessful) {
            if (!nicknameAccepted && connectionSuccessful) {
                Platform.runLater(() -> {
                    SceneController.showAlert(STR_ERROR, "Nickname già scelto.");
                    SceneController.changeRootPane(observers, "LoginScene.fxml");
                });
            } else {
                Platform.runLater(() -> {
                    SceneController.showAlert(STR_ERROR, "Impossibile contattare il server.");
                    SceneController.changeRootPane(observers, "ScreenTitle.fxml");
                });
            }

        }
    }

    @Override
    public void askPlayCard(String player, List<AssistantCardModel> playerDeck){
        DeckSceneController deckSceneController = new DeckSceneController();
        deckSceneController.setDeck(playerDeck);
        deckSceneController.setNickname(player);
        deckSceneController.addAllObservers(observers);
        Platform.runLater(()-> {
            boardSceneController.setTurnLabel("Gioca una carta");
            boardSceneController.setDeckSceneController(deckSceneController);

        });
    }

    @Override
    public void askPlayCharacterCard(PlayerModel active, List<CharacterCardModel> characterDeck) {
        CharacterSceneController characterSceneController = new CharacterSceneController();
        characterSceneController.setDeck(characterDeck);
        characterSceneController.setNickname(active.getNickname());
        characterSceneController.addAllObservers(observers);
        Platform.runLater(()-> {
            boardSceneController.setTurnLabel("Scegli un effetto");
            boardSceneController.setCharacterSceneController(characterSceneController);

        });
    }

    @Override
    public void showEndTurn(String nick){
        Platform.runLater(() -> boardSceneController.setTurnLabel("Aspetta il tuo turno"));

    }
    @Override
    public void showInvalidNickname(String nickname){}

    @Override
    public void showStartTurn(String nick){
        Platform.runLater(() -> boardSceneController.setTurnLabel("Sposta fino a 3 studenti nell'entrata"));
    }

    @Override
    public void showInvalidCloud(String nick){}
    @Override
    public void errorCard(String player, AssistantCardModel card){}
    @Override
    public void showDisconnectionMessage(String nickname, String message){
        Platform.runLater(() -> {
            SceneController.showAlert("DISCONNECTION",   nickname + " " + message);
            SceneController.changeRootPane(observers, "ScreenTitle.fxml");
        });
    }
    @Override
    public void showGenericMessage(String message){
        if(!message.contains("is playing..."))
            Platform.runLater(() -> SceneController.showAlert("Info Message", message));
    }
    @Override
    public void showInvalidMovementMessage(String nick, byte movementAllowed, byte movementInserted){}

    @Override
    public void showInvalidNumberOfStudentMoved(String nickname){}

    @Override
    public void askNickname(){
        Platform.runLater(() -> SceneController.changeRootPane(observers, "LoginScene.fxml"));
    }

    @Override
    public void showErrorAndExit(String error){}

    @Override
    public void showOrderPhase(String nickname, List<PlayerModel> order){}

    @Override
    public void askTowerColor(String nickMessage, List<ColorTower> availableColorTowers){
        TowerColorSceneController towerColorSceneController = new TowerColorSceneController();

        towerColorSceneController.addAllObservers(observers);

        towerColorSceneController.setAvailableTowers(availableColorTowers);
        Platform.runLater(() -> SceneController.changeRootPane(towerColorSceneController, "TowerColorScene.fxml"));
    }
    @Override
    public void askPlayersNumber(){
        PlayersNumberSceneController playersNumberSceneController = new PlayersNumberSceneController();
        playersNumberSceneController.addAllObservers(observers);
        Platform.runLater(() -> SceneController.changeRootPane(playersNumberSceneController, "PlayersNumberScene.fxml"));
    }

    @Override
    public void askGameMode(){
        GameModeSceneController gameModeSceneController = new GameModeSceneController();
        gameModeSceneController.addAllObservers(observers);
        Platform.runLater(() -> SceneController.changeRootPane(observers, "GameModeScene.fxml"));
    }

    @Override
    public void showSkippingMotherMovement(String activeNick) {

    }


    @Override
    public void askMoveStudentFromCardToIsland(String active, List<IslandModel> islands, List<ColorPawns> studentsOnCard) {

    }

    @Override
    public void askExtraGetInfluence(String active, List<IslandModel> islands) {

    }

    @Override
    public void askMoveBanCard(String active, List<IslandModel> islands) {

    }

    @Override
    public void askMoveFromCardToEntrance(String active, List<ColorPawns> studentsOnCard, List<ColorPawns> entrance) {

    }

    @Override
    public void askColorStudentToIgnore(String active) {

    }

    @Override
    public void askColorRemoveForAll(String active) {

    }

    @Override
    public void askStudentsChangeEntranceHall(String active, List<ColorPawns> entrance, Map<ColorPawns, Integer> hall) {

    }

    @Override
    public void askStudentFromCardToHall(String nickname, List<ColorPawns> studentsOnCard) {

    }

    @Override
    public void showEntranceChange(String nickname, List<ColorPawns> studentInEntrance) {
        boardSceneController.setEntrance(studentInEntrance);
        Platform.runLater(() -> boardSceneController.entranceDisplay());
    }

    @Override
    public void showPlayerBoardMessage(PlayerModel nickname, List<ColorTower> towers, Map<ColorPawns, Integer> hall, List<ColorPawns> entrance, List<ColorPawns> profs){
        if(!checkpointBoard)
            boardSceneController.setTurnLabel("Aspetta il tuo turno");
        if(this.nickname!=null && !nickname.getNickname().equals(this.nickname)){
            OtherGameBoardSceneController board = new OtherGameBoardSceneController();
            board.setTowers(towers);
            board.setHall(hall);
            board.setEntrance(entrance);
            board.setProfs(profs);
            board.setPlayer(nickname.getNickname());
            Platform.runLater(()-> {
                try {
                    SceneController.showWindow(board, nickname.getNickname(),  "GameBoardScene.fxml");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        }else{
            boardSceneController.setHall(hall);
            boardSceneController.setProfs(profs);
            if(!checkpointBoard){
                this.nickname=nickname.getNickname();
                boardSceneController.setTowers(towers);
                boardSceneController.setEntrance(entrance);
                boardSceneController.setPlayer(nickname.getNickname());
                //new Thread(() -> notifyObserver(obs -> obs.onRequestLobby(nickname.getNickname()))).start();
                boardSceneController.addAllObservers(observers);
                Platform.runLater(()->SceneController.changeRootPane(boardSceneController, "GameBoardScene.fxml"));
                checkpointBoard = true;
            }else{

                Platform.runLater(()-> {
                            boardSceneController.hallDisplay();
                            boardSceneController.displayProfs();
                        }
                );
            }

        }
    }


}

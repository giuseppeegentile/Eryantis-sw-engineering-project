package it.polimi.ingsw.view.gui;

import it.polimi.ingsw.model.cards.AssistantCardModel;
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

import java.util.List;
import java.util.Map;

public class Gui extends ViewObservable implements View {

    private static final String STR_ERROR = "ERROR";
    private static final String SCREEN_TITLE_FXML = "ScreenTitle.fxml";
    private GameBoardSceneController boardSceneController;
    private String nickname=null;
    @Override
    public void showWinMessage(PlayerModel winner) {
        WinSceneController winSceneController = new WinSceneController();
        winSceneController.addAllObservers(observers);
        winSceneController.setWinnerNickname(winner.getNickname());
        Platform.runLater(() -> SceneController.changeRootPane(winSceneController, "WinScene.fxml"));
    }

    public void showMessageJoiningIsland(Message message){}

    public void askMoveCloudToEntrance(String nickname, List<CloudModel> clouds){}

    public void askMoveEntranceToHall(String player, List<ColorPawns> students, int numberStudentsToMove){}

    public void askMotherNatureMovements(String player, byte maxMovement){}

    public void askMoveEntranceToIsland(String player, List<ColorPawns> colorPawns){}

    public void showCemeteryMessage(String player, List<AssistantCardModel> cemetery){}

    public void showTextMessage(String player, String text){}

    public void showLobbyMessage(List<String> nicknameList){

        boardSceneController.setPlayersLobby(nicknameList);
        boardSceneController.addAllObservers(observers);
        Platform.runLater(()->SceneController.changeRootPane(boardSceneController, "GameBoardScene.fxml"));
    }

    public void showIslandMessage(String nickname, IslandModel islandModel, int islandIndex){}

    public void showCloudsMessage(String nickname, List<CloudModel> clouds){}

    public void showMoveMotherNatureMessage(String player, byte movement){}

    public void showPlayAssistantCardMessage(String player, AssistantCardModel assistantCard){}

    public void showIslands(String nickname, List<IslandModel> islands){}

    public void showInvalidTower(String player, ColorTower colorTower) {}

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
                    SceneController.changeRootPane(observers, SCREEN_TITLE_FXML);
                });
            }

        }
    }
    public void showDeckMessage(String player, List<AssistantCardModel> playerDeck){}

    public void showEndTurn(String nick){}

    public void showInvalidNickname(String nickname){}

    public void showStartTurn(String nick){}

    public void showInvalidCloud(String nick){}

    public void errorCard(String player, AssistantCardModel card){}

    public void showDisconnectionMessage(String nickname, String message){
        Platform.runLater(() -> {
            SceneController.showAlert("DISCONNECTION",   nickname + " " + message);
            SceneController.changeRootPane(observers, SCREEN_TITLE_FXML);
        });
    }

    public void showGenericMessage(String message){
        Platform.runLater(() -> SceneController.showAlert("Info Message", message));
    }

    public void showInvalidMovementMessage(String nick, byte movementAllowed, byte movementInserted){}

    public void showInvalidNumberOfStudentMoved(String nickname){}

    public void askNickname(){
        Platform.runLater(() -> SceneController.changeRootPane(observers, "LoginScene.fxml"));
    }

    public void showErrorAndExit(String error){}

    public void askPlayCards(String nickname, List<AssistantCardModel> playerDeck){}

    public void showOrderPhase(String nickname, List<PlayerModel> order){}

    public void askTowerColor(String nickMessage, List<ColorTower> availableColorTowers){
        TowerColorSceneController towerColorSceneController = new TowerColorSceneController();

        towerColorSceneController.addAllObservers(observers);

        towerColorSceneController.setAvailableTowers(availableColorTowers);
        Platform.runLater(() -> SceneController.changeRootPane(towerColorSceneController, "TowerColorScene.fxml"));
    }

    public void askPlayersNumber(){
        PlayersNumberSceneController playersNumberSceneController = new PlayersNumberSceneController();
        playersNumberSceneController.addAllObservers(observers);
        Platform.runLater(() -> SceneController.changeRootPane(playersNumberSceneController, "PlayersNumberScene.fxml"));
    }


    public void askGameMode(){
        GameModeSceneController gameModeSceneController = new GameModeSceneController();
        gameModeSceneController.addAllObservers(observers);
        Platform.runLater(() -> SceneController.changeRootPane(observers, "GameModeScene.fxml"));
    }


    public void showPlayerBoardMessage(String nickname, List<ColorTower> towers, Map<ColorPawns, Integer> hall, List<ColorPawns> entrance, List<ColorPawns> profs, int numClouds){

        if(this.nickname!=null && !nickname.equals(this.nickname)){
            GameBoardSceneController board = new GameBoardSceneController();
            board.setTowers(towers);
            board.setHall(hall);
            board.setEntrance(entrance);
            board.setProfs(profs);
            board.setPlayer(nickname);
            board.setNumClouds(numClouds);
            board.readOnly(true);
            System.out.println("enter");
            Platform.runLater(()->SceneController.changeRootPane(board, "GameBoardScene.fxml"));
        }else{
            this.nickname=nickname;
            boardSceneController = new GameBoardSceneController();
            boardSceneController.setTowers(towers);
            boardSceneController.setHall(hall);
            boardSceneController.setEntrance(entrance);
            boardSceneController.setProfs(profs);
            boardSceneController.setPlayer(nickname);
            boardSceneController.setNumClouds(numClouds);
            boardSceneController.readOnly(false);
            new Thread(() -> notifyObserver(obs -> obs.onRequestLobby(nickname))).start();
        }
    }


}

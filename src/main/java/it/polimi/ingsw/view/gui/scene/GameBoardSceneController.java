package it.polimi.ingsw.view.gui.scene;

import it.polimi.ingsw.model.cards.AssistantCardModel;
import it.polimi.ingsw.model.colors.ColorPawns;
import it.polimi.ingsw.model.colors.ColorTower;
import it.polimi.ingsw.model.enums.GameMode;
import it.polimi.ingsw.observer.ViewObservable;
import it.polimi.ingsw.view.gui.SceneController;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.Parameter;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class GameBoardSceneController extends ViewObservable implements GenericSceneController{
    private List<ColorTower> towers;
    private Map<ColorPawns, Integer> hall;
    private List<ColorPawns> profs;
    private List<ColorPawns> entrance;
    private String nickname;
    private int numClouds;

    @FXML
    private ImageView cloud_3;

    @FXML
    private ImageView cloud_4;

    @FXML
    private GridPane entrancePane;

    @FXML
    private AnchorPane pane;

    @FXML
    private Button lobbyBtn;

    @FXML
    private TableView<String> lobbyTable;

    @FXML
    ImageView wizardView;

    @FXML
    TableColumn<String, String> gamersCol;

    private List<String> nicknameList;
    private List<AssistantCardModel> playerDeck;
    private DeckSceneController deckSceneController;

    @FXML
    private void initialize(){
        lobbyTable.setVisible(false);
        Button b = new Button("s");
        b.setStyle("-fx-background-color:" + entrance.get(0).name().toLowerCase());
        entrancePane.add(b, 1, 0);

        int idx = 1;
        for(int j = 1; j < 4; j++){
            Button bt = new Button("s");
            bt.setStyle("-fx-background-color:" + entrance.get(idx).name().toLowerCase());
            entrancePane.add(bt, 0, j);
            idx++;
            Button bt2 = new Button("s");
            bt2.setStyle("-fx-background-color:" + entrance.get(idx).name().toLowerCase());
            entrancePane.add(bt2, 1, j);
            idx++;
        }/*
        if(idx!= entrance.size()-1){
            Button bt = new Button("s");
            bt.setStyle("-fx-background-color:" + entrance.get(idx).name().toLowerCase());
            entrancePane.add(bt, 1, 4);
        }
        int k = 0;
        String colorTower = towers.get(k).name().toLowerCase();
        Button bb = new Button("t");
        bb.setStyle("-fx-background-color:" + colorTower);
        towersGrid.add(bb, 0, k);
        k++;
        for(ColorTower c: towers){
            Button bt = new Button("t");
            bt.setStyle("-fx-background-color:" + colorTower);
            if(k % 2 == 0) {
                towersGrid.add(bt, 0, k);
            }else{
                towersGrid.add(bt, 1, k);
                k++;
            }
        }*/

        b.setMaxWidth(Double.POSITIVE_INFINITY);
        b.setPrefHeight(10.0);
        GridPane.setFillWidth(b, true);

        showCorrectClouds();
        gamersCol.setVisible(true);

        ObservableList<String> data = FXCollections.observableArrayList(nicknameList);
        lobbyTable.getColumns().clear();
        lobbyTable.getColumns().add(gamersCol);
        gamersCol.setCellValueFactory(d->new SimpleStringProperty(d.getValue()));
        lobbyTable.setItems(data);
        lobbyBtn.addEventHandler(MouseEvent.MOUSE_CLICKED, (e)->{
            lobbyTable.setVisible(!lobbyTable.isVisible());//toggle
        });

        wizardView.addEventHandler(MouseEvent.MOUSE_CLICKED, (e)->{
            try {
                SceneController.showDeck(deckSceneController, "DeckScene.fxml");
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });

        lobbyTable.setRowFactory(tv -> {
            TableRow<String> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (! row.isEmpty() && event.getButton()== MouseButton.PRIMARY
                        && event.getClickCount() == 2) {

                    String nickChosen = row.getItem();
                    System.out.println(nickChosen);
                    new Thread(() -> notifyObserver(obs -> obs.onRequestBoard(this.nickname, nickChosen))).start();
                }
            });
            return row ;
        });

    }


    private void showCorrectClouds(){
        if(numClouds == 3){
            cloud_4.setVisible(false);
        }else if (numClouds == 2){
            cloud_3.setVisible(false);
            cloud_4.setVisible(false);
        }
    }

    public void setTowers(List<ColorTower> towers) {
        this.towers =towers;
    }

    public void setEntrance(List<ColorPawns> entrance) {
        this.entrance =entrance;
    }

    public void setProfs(List<ColorPawns> profs) {
        this.profs =profs;
    }

    public void setHall(Map<ColorPawns, Integer> hall) {
        this.hall =hall;
    }

    public void setPlayer(String nickname) {
        this.nickname = nickname;
    }

    public void setNumClouds(int numClouds) {
        this.numClouds = numClouds;
    }

    public void setPlayersLobby(List<String> nicknameList) {
        this.nicknameList = nicknameList;
    }

    public void setDeckSceneController(DeckSceneController deckSceneController) {
        this.deckSceneController = deckSceneController;
    }
}

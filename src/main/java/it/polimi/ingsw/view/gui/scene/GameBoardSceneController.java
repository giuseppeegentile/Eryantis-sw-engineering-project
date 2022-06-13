package it.polimi.ingsw.view.gui.scene;

import it.polimi.ingsw.model.cards.AssistantCardModel;
import it.polimi.ingsw.model.colors.ColorPawns;
import it.polimi.ingsw.model.colors.ColorTower;
import it.polimi.ingsw.model.game.CloudModel;
import it.polimi.ingsw.model.islands.IslandModel;
import it.polimi.ingsw.observer.ViewObservable;
import it.polimi.ingsw.view.gui.SceneController;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public class GameBoardSceneController extends ViewObservable implements GenericSceneController{
    private List<ColorTower> towers;
    private Map<ColorPawns, Integer> hall;
    private List<ColorPawns> profs;
    private List<ColorPawns> entrance;
    private String nickname;

    @FXML
    private ImageView cloud_3;

    @FXML
    private ImageView cloud_4;

    @FXML
    private GridPane entrancePane;

    @FXML
    private GridPane towersGrid;
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

    @FXML
    private VBox vboxCloud1;
    @FXML
    private VBox vboxCloud2;
    @FXML
    private VBox vboxCloud3;
    @FXML
    private VBox vboxCloud4;


    @FXML
    private VBox vboxIsland1;
    @FXML
    private VBox vboxIsland2;
    @FXML
    private VBox vboxIsland3;
    @FXML
    private VBox vboxIsland4;
    @FXML
    private VBox vboxIsland5;
    @FXML
    private VBox vboxIsland6;
    @FXML
    private VBox vboxIsland7;
    @FXML
    private VBox vboxIsland8;
    @FXML
    private VBox vboxIsland9;
    @FXML
    private VBox vboxIsland10;
    @FXML
    private VBox vboxIsland11;
    @FXML
    private VBox vboxIsland12;


    private List<CloudModel> cloudModels;
    private List<String> nicknameList;
    private List<AssistantCardModel> playerDeck;
    private DeckSceneController deckSceneController;
    private List<IslandModel> islands;

    @FXML
    private void initialize(){
        lobbyTable.setVisible(false);

        entranceDisplay();
        towersDisplay();
        showCorrectClouds();
        setLobbyTable();
        islandsDisplay();
        cloudsDisplay();

        lobbyBtn.addEventHandler(MouseEvent.MOUSE_CLICKED, (e)->{
            lobbyTable.setVisible(!lobbyTable.isVisible());//toggle
            gamersCol.setVisible(!gamersCol.isVisible());
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

    private void cloudsDisplay() {
        List<VBox> cloudsVboxes = List.of(vboxCloud1, vboxCloud2);
        if(cloudModels.size() > 2) cloudsVboxes.add(vboxCloud3);
        if(cloudModels.size() > 3) cloudsVboxes.add(vboxCloud4);
        int index = 0;
        for(CloudModel c: cloudModels){
            cloudsVboxes.get(index).setAlignment(Pos.CENTER);
            for (ColorPawns s: c.getStudents()){
                Button b = getStyledButton(s);
                cloudsVboxes.get(index).getChildren().add(b);
            }
            index++;
        }
    }

    private Button getStyledButton(ColorPawns s) {
        Button student = new Button("s");
        student.setStyle("-fx-background-color:" + s.name().toLowerCase());
        return student;
    }

    private void islandsDisplay() {
        int k = 0;
        List<VBox> vBoxes = List.of(vboxIsland1,vboxIsland2,vboxIsland3,vboxIsland4,vboxIsland5,vboxIsland6,vboxIsland7,vboxIsland8,vboxIsland9,vboxIsland10,vboxIsland11,vboxIsland12);

        for(IslandModel i: islands){
            vBoxes.get(k).setAlignment(Pos.CENTER);
            if(i.getMotherNature()) {
                Button student = new Button("M");
                student.setStyle("-fx-background-color:#360909"); //brown
                vBoxes.get(k).getChildren().add(student);
            }
            for (ColorPawns st : i.getStudents()) {
                if (!st.name().equals("NULL")) {
                    Button student = getStyledButton(st);
                    vBoxes.get(k).getChildren().add(student);
                }
            }
            k++;
        }
    }

    private void setLobbyTable() {
        ObservableList<String> data = FXCollections.observableArrayList(nicknameList);
        lobbyTable.getColumns().clear();
        lobbyTable.getColumns().add(gamersCol);
        gamersCol.setCellValueFactory(d->new SimpleStringProperty(d.getValue()));
        lobbyTable.setItems(data);
    }

    private void entranceDisplay() {
        Button b = new Button("s");
        b.setStyle("-fx-background-color:" + entrance.get(0).name().toLowerCase());
        entrancePane.add(b, 1, 0);

        int idx = 1;
        int rowGrid = 2;
        for(int j = 1; j < rowGrid; j++){
            Button bt = getStyledButton(entrance.get(idx));
            entrancePane.add(bt, 0, j);
            idx++;
            if(idx == entrance.size()) break;
            Button bt2 = getStyledButton(entrance.get(idx));
            entrancePane.add(bt2, 1, j);
            idx++;
            if(idx == entrance.size()) break;
            rowGrid++;
        }
    }

    private void towersDisplay() {
        String colorTower = towers.get(0).name().toLowerCase();

        int i;
        System.out.println(towers.size());
        for (i = 0; i < towers.size() / 2; i++) {
            Button bt = new Button("t");
            bt.setStyle("-fx-background-color:" + colorTower);
            towersGrid.add(bt, 0, i);
            Button bt2 = new Button("t");
            bt2.setStyle("-fx-background-color:" + colorTower);
            towersGrid.add(bt2, 1, i);
        }
        Button bt = new Button("t");
        bt.setStyle("-fx-background-color:" + colorTower);
        if (towers.size() % 2 != 0) towersGrid.add(bt, 0, i);
    }


    private void showCorrectClouds(){
        if(cloudModels.size() == 3){
            cloud_4.setVisible(false);
        }else if (cloudModels.size() == 2){
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

    public void setPlayersLobby(List<String> nicknameList) {
        this.nicknameList = nicknameList;
    }

    public void setDeckSceneController(DeckSceneController deckSceneController) {
        this.deckSceneController = deckSceneController;
    }

    public void setIslands(List<IslandModel> islands){
        this.islands = islands;
    }

    public void setClouds(List<CloudModel> cloudModels) {
        this.cloudModels = cloudModels;
    }
}

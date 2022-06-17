package it.polimi.ingsw.view.gui.scene;

import it.polimi.ingsw.model.colors.ColorPawns;
import it.polimi.ingsw.model.colors.ColorTower;
import it.polimi.ingsw.model.game.CloudModel;
import it.polimi.ingsw.model.islands.IslandModel;
import it.polimi.ingsw.model.player.PlayerModel;
import it.polimi.ingsw.observer.ViewObservable;
import it.polimi.ingsw.view.gui.SceneController;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class GameBoardSceneController extends ViewObservable implements GenericSceneController{
    private List<ColorTower> towers;
    private Map<ColorPawns, Integer> hall;
    private List<ColorPawns> profs;
    private List<ColorPawns> entrance;
    private String nickname;
    private int tempIndex;
    private List<ColorPawns> studentToIsland = new ArrayList<>();
    private boolean alreadyMovedStudent = false;

    @FXML
    private GridPane hallGrid;

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
    @FXML
    private Label turnLabel;
    @FXML
    private Label subtitle;


    private List<CloudModel> cloudModels;
    private List<String> nicknameList;
    private DeckSceneController deckSceneController;
    private List<IslandModel> islands;
    private String turnText;
    private List<ColorPawns> studentToHall = new ArrayList<>();
    @FXML
    private void initialize(){
        skipMove.setVisible(false);
        lobbyTable.setVisible(false);
        lobbyBtn.setVisible(false);
        this.turnLabel.setText(turnText);
        //setLobbyTable();
        entranceDisplay();
        towersDisplay();
        showCorrectClouds();
        islandsDisplay();
        cloudsDisplay();

        skipMove.addEventHandler(MouseEvent.MOUSE_CLICKED, (e)->{
            new Thread(()-> notifyObserver(obs->obs.onUpdateStudentToIsland(nickname, List.of(), 0))).start();
            this.skipMove.setVisible(false);
            this.alreadyMovedStudent = true;
        });

/*        lobbyBtn.addEventHandler(MouseEvent.MOUSE_CLICKED, (e)->{
            lobbyTable.setVisible(!lobbyTable.isVisible());//toggle
            gamersCol.setVisible(!gamersCol.isVisible());
        });*/

        wizardView.addEventHandler(MouseEvent.MOUSE_CLICKED, (e)->{
            try {
                SceneController.showDeck(deckSceneController, "DeckScene.fxml");
                turnLabel.setText("Aspetta il tuo turno...");
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });


/*        lobbyTable.setRowFactory(tv -> {
            TableRow<String> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (! row.isEmpty() && event.getButton()== MouseButton.PRIMARY  && event.getClickCount() == 2) {

                    String nickChosen = row.getItem();
                    System.out.println(nickChosen);
                    new Thread(() -> notifyObserver(obs -> obs.onRequestBoard(this.nickname, nickChosen))).start();
                }
            });
            return row ;
        });*/

    }

    public void setSubtitleText(String text){
        subtitle.setText(text);
    }

    public void hideSubtitle(){
        subtitle.setVisible(false);
    }

    public void setTurnLabel(String turnLabel) {
        this.turnText = turnLabel;
        if(this.turnLabel != null)
            this.turnLabel.setText(turnLabel);
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
            int chosenIndex = index;
            cloudsVboxes.get(index).addEventHandler(MouseEvent.MOUSE_CLICKED, (e)->{
                new Thread(()->notifyObserver(obs -> obs.onChosenCloud(nickname, chosenIndex))).start();
                cloudsVboxes.get(chosenIndex).getChildren().clear();
                turnLabel.setText("Aspetta il tuo turno");
                subtitle.setVisible(false);
            });
            index++;
        }
    }

    private Button getStyledButton(ColorPawns s) {
        Button b = new Button();
        b.setPrefHeight(30.0);
        b.setPrefWidth(35.0);
        String path = "/images_cranio/pawns/" + s.name() +  ".png";
        BackgroundImage backgroundImage = new BackgroundImage(new Image(getClass().getResource(path).toExternalForm()),
                BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);

        Background background = new Background(backgroundImage);
        b.setBackground(background);
        return b;
    }

    public void islandsDisplay() {
        int k = 0;
        List<VBox> vBoxes = List.of(vboxIsland1,vboxIsland2,vboxIsland3,vboxIsland4,vboxIsland5,vboxIsland6,vboxIsland7,vboxIsland8,vboxIsland9,vboxIsland10,vboxIsland11,vboxIsland12);

        for(IslandModel i: islands){
            vBoxes.get(k).getChildren().clear();
            setIslandEventListener(vBoxes.get(k), k);
            vBoxes.get(k).setAlignment(Pos.CENTER);
            if(i.getMotherNature()) {
                Button student = getStyledMotherButton();
                tempIndex = k;
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

    private void setIslandEventListener(VBox vBox, int islandIndex) {
        vBox.addEventHandler(MouseEvent.MOUSE_CLICKED, (e)-> {
            //System.out.println(alreadyMovedStudent);
            if(!alreadyMovedStudent) {
                //System.out.println("entra ancora qui");
                new Thread(() -> notifyObserver(obs -> obs.onUpdateStudentToIsland(nickname, studentToIsland, islandIndex))).start();
                this.alreadyMovedStudent = true;
            }
        });
    }

    private Button getStyledMotherButton() {
        Button b = new Button();
        b.setPrefHeight(30.0);
        b.setPrefWidth(23.0);
        String path = "/images_cranio/pawns/MOTHER.png";
        BackgroundImage backgroundImage = new BackgroundImage(new Image(getClass().getResource(path).toExternalForm()),
                BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);

        Background background = new Background(backgroundImage);
        b.setBackground(background);
        return b;
    }

    private Button getStyledTower(String colorTower) {
        Button b = new Button();
        b.setPrefHeight(30.0);
        b.setPrefWidth(23.0);
        String path = "/images_cranio/towers/" + colorTower + ".png";
        BackgroundImage backgroundImage = new BackgroundImage(new Image(getClass().getResource(path).toExternalForm()),
                BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);

        Background background = new Background(backgroundImage);
        b.setBackground(background);
        return b;
    }

    public void entranceDisplay() {
        entrancePane.getChildren().clear();
        Button b = getStyledButton(entrance.get(0));
        entrancePane.add(b, 1, 0);
        setEntranceEventListener(b, entrance.get(0));
        int idx = 1;
        int rowGrid = 2;
        for(int j = 1; j < rowGrid; j++){
            Button bt = getStyledButton(entrance.get(idx));
            bt.setMaxHeight(30.0);
            setEntranceEventListener(bt, entrance.get(idx));
            GridPane.setFillWidth(bt, true);
            entrancePane.add(bt, 0, j);
            idx++;
            if(idx == entrance.size()) break;
            Button bt2 = getStyledButton(entrance.get(idx));
            setEntranceEventListener(bt2, entrance.get(idx));
            bt2.setMaxHeight(30.0);
            GridPane.setFillWidth(bt2, true);
            entrancePane.add(bt2, 1, j);
            idx++;
            if(idx == entrance.size()) break;
            rowGrid++;
        }
        entrancePane.setEffect(new DropShadow(10, Color.YELLOW));
    }

    @FXML
    private Button skipMove;
    private void setEntranceEventListener(Button button, ColorPawns colorToMove) {
        if(this.numberStudentsToMove == 0) {
            button.addEventHandler(MouseEvent.MOUSE_CLICKED, (e) -> {
                skipMove.setVisible(false);
                if (studentToIsland.size() < 3)
                    studentToIsland.add(colorToMove);
                //if(studentToIsland.size() == 3) enableOnlyIsland();
            });
        }else{
            button.addEventHandler(MouseEvent.MOUSE_CLICKED, (e) -> {
                if (studentToHall.size() <= this.numberStudentsToMove) {
                    this.studentToHall.add(colorToMove);
                    this.numberStudentsToMove-=1;
                    if(this.numberStudentsToMove == 0){
                        new Thread(()->notifyObserver(obs -> obs.onUpdateStudentToHall(nickname, studentToHall))).start();
                        //enableOnlyIsland();
                    }
                }
            });
        }
    }

    private void towersDisplay() {
        String colorTower = towers.get(0).name().toLowerCase();
        int i;
        System.out.println(towers.size());
        for (i = 0; i < towers.size() / 2; i++) {
            Button bt = getStyledTower(colorTower);
            towersGrid.add(bt, 0, i);
            Button bt2 = getStyledTower(colorTower);
            towersGrid.add(bt2, 1, i);
        }
        Button bt = getStyledTower(colorTower);
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

    public void setDeckSceneController(DeckSceneController deckSceneController) {
        this.deckSceneController = deckSceneController;
        if(this.turnLabel != null){
            this.turnLabel.setText("Gioca una carta");
        }
    }

    public void setIslands(List<IslandModel> islands){
        this.islands = islands;
    }

    public void setClouds(List<CloudModel> cloudModels) {
        this.cloudModels = cloudModels;
    }

    private int numberStudentsToMove = 0;

    public void setNumberToMove(int numberStudentsToMove) {
        this.numberStudentsToMove = numberStudentsToMove;
    }

    public void hallDisplay() {
        for(int i=0; i < hall.get(ColorPawns.GREEN); i++){
            hallGrid.add(getStyledButton(ColorPawns.GREEN) , i, 0);
        }
        for(int i=0; i < hall.get(ColorPawns.RED); i++){
            hallGrid.add(getStyledButton(ColorPawns.RED) , i, 1);
        }
        for(int i=0; i < hall.get(ColorPawns.YELLOW); i++){
            hallGrid.add(getStyledButton(ColorPawns.YELLOW) , i, 2);
        }
        for(int i=0; i < hall.get(ColorPawns.PINK); i++){
            hallGrid.add(getStyledButton(ColorPawns.PINK) , i, 3);
        }
        for(int i=0; i < hall.get(ColorPawns.BLUE); i++){
            hallGrid.add(getStyledButton(ColorPawns.BLUE) , i, 4);
        }
    }

    public void islandHandlerMother(byte maxMovement) {
        List<VBox> vBoxes = List.of(vboxIsland1,vboxIsland2,vboxIsland3,vboxIsland4,vboxIsland5,vboxIsland6,vboxIsland7,vboxIsland8,vboxIsland9,vboxIsland10,vboxIsland11,vboxIsland12);
        System.out.println("tmp " + tempIndex);
        System.out.println("max " + maxMovement);
        //enableOnlyIsland();
        for(int i = 0; i <= islands.size(); i++){
            int idx = i;
            System.out.println("idx");
            vBoxes.get(idx).addEventHandler(MouseEvent.MOUSE_CLICKED, (e)->{
                new Thread(() -> notifyObserver(obs -> obs.onUpdateMotherNature(nickname, (byte)(idx-tempIndex)))).start();
                vBoxes.get(tempIndex).getChildren().remove(getStyledMotherButton()); //non funziona

                tempIndex = idx;
                vBoxes.get(idx).getChildren().add(getStyledMotherButton());
                turnLabel.setText("Seleziona una nuvola");
                subtitle.setText("Sposterai gli studenti nell'ingresso");
                //enableOnlyClouds();
            });

        }
    }

    public void setVisibleSkip() {
        this.skipMove.setVisible(true);
    }

//    public void enableOnlyIsland(){
//        entrancePane.setDisable(true);
//        vboxCloud1.setDisable(true);
//        vboxCloud2.setDisable(true);
//        vboxCloud3.setDisable(true);
//        vboxCloud4.setDisable(true);
//        wizardView.setDisable(true);
//    }
//
//    public void enableOnlyClouds(){
//        entrancePane.setDisable(true);
//        vboxCloud1.setDisable(false);
//        vboxCloud2.setDisable(false);
//        vboxCloud3.setDisable(false);
//        vboxCloud4.setDisable(false);
//        wizardView.setDisable(true);
//        vboxIsland1.setDisable(true);
//        vboxIsland2.setDisable(true);
//        vboxIsland3.setDisable(true);
//        vboxIsland4.setDisable(true);
//        vboxIsland5.setDisable(true);
//        vboxIsland6.setDisable(true);
//        vboxIsland7.setDisable(true);
//        vboxIsland8.setDisable(true);
//        vboxIsland9.setDisable(true);
//        vboxIsland10.setDisable(true);
//        vboxIsland11.setDisable(true);
//        vboxIsland12.setDisable(true);
//    }
//
//    public void enableOnlyEntrance(){
//        entrancePane.setDisable(false);
//        vboxCloud1.setDisable(true);
//        vboxCloud2.setDisable(true);
//        vboxCloud3.setDisable(true);
//        vboxCloud4.setDisable(true);
//        wizardView.setDisable(true);
//        vboxIsland1.setDisable(true);
//        vboxIsland2.setDisable(true);
//        vboxIsland3.setDisable(true);
//        vboxIsland4.setDisable(true);
//        vboxIsland5.setDisable(true);
//        vboxIsland6.setDisable(true);
//        vboxIsland7.setDisable(true);
//        vboxIsland8.setDisable(true);
//        vboxIsland9.setDisable(true);
//        vboxIsland10.setDisable(true);
//        vboxIsland11.setDisable(true);
//        vboxIsland12.setDisable(true);
//
//    }
/*
    private void setLobbyTable() {
        ObservableList<String> data = FXCollections.observableArrayList(nicknameList);
        lobbyTable.getColumns().clear();
        lobbyTable.getColumns().add(gamersCol);
        gamersCol.setCellValueFactory(d->new SimpleStringProperty(d.getValue()));
        lobbyTable.setItems(data);
    }*/
}
package it.polimi.ingsw.view.gui.scene;

import it.polimi.ingsw.model.colors.ColorPawns;
import it.polimi.ingsw.model.colors.ColorTower;
import it.polimi.ingsw.model.enums.GameMode;
import it.polimi.ingsw.model.game.CloudModel;
import it.polimi.ingsw.model.islands.IslandModel;
import it.polimi.ingsw.model.player.PlayerModel;
import it.polimi.ingsw.observer.ViewObservable;
import it.polimi.ingsw.view.gui.SceneController;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * Class that manages every interaction of the player with the game board scene.
 */

public class GameBoardSceneController extends ViewObservable implements GenericSceneController{
    private List<ColorTower> towers;
    private Map<ColorPawns, Integer> hall;
    private List<ColorPawns> profs;
    private List<ColorPawns> entrance;
    private String nickname;
    private int tempIndex;
    private List<ColorPawns> studentToIsland = new ArrayList<>();
    private boolean alreadyMovedStudent = false;
    private boolean cloudsHasHandler = false;
    private CharacterSceneController characterSceneController;

    @FXML
    private ImageView island1;

    @FXML
    private ImageView island2;
    @FXML
    private ImageView island3;

    @FXML
    private ImageView island4;

    @FXML
    private ImageView island5;
    @FXML
    private ImageView island6;

    @FXML
    private ImageView island7;
    @FXML
    private ImageView island8;

    @FXML
    private ImageView island9;

    @FXML
    private ImageView island10;
    @FXML
    private ImageView island11;
    @FXML
    private ImageView island12;
    @FXML
    private Button skipMove;

    @FXML
    private GridPane profsGrid;

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
    private GridPane lobbyTable;

    @FXML
    private ImageView wizardView;

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
    @FXML
    private ImageView character;
    @FXML
    private Button skipCardGame;
    private int sizeEntrance;
    private List<CloudModel> cloudModels;
    private List<PlayerModel> nicknameList;
    private DeckSceneController deckSceneController;
    private List<IslandModel> islands;
    private String turnText;
    private List<ColorPawns> studentToHall = new ArrayList<>();
    private boolean alreadyMovedMother = false;
    private GameMode gameMode;
    private int islandProhib;
    /**
     * Manages every interaction of the player with graphical objects in the scene.
     */
    @FXML
    private void initialize(){
        skipMove.setVisible(false);
        lobbyTable.setVisible(false);
        this.turnLabel.setText(turnText);

        entranceDisplay();
        towersDisplay();
        showCorrectClouds();
        islandsDisplay();
        cloudsDisplay();

        if(gameMode == GameMode.BEGINNER) {
            character.setVisible(false);
            skipCardGame.setVisible(false);
        }
        skipCardGame.addEventHandler(MouseEvent.MOUSE_CLICKED, (e)->{
            new Thread(()->notifyObserver(obs->obs.onUpdateCharacterCardPlayed(nickname, null))).start();
        });

        skipMove.addEventHandler(MouseEvent.MOUSE_CLICKED, (e)->{
            new Thread(()-> notifyObserver(obs->obs.onUpdateStudentToIsland(nickname, List.of(), 0))).start();
            this.skipMove.setVisible(false);
            this.alreadyMovedStudent = true;
        });

        lobbyBtn.addEventHandler(MouseEvent.MOUSE_CLICKED, (e)->{
            lobbyTable.setVisible(!lobbyTable.isVisible());//toggle
            new Thread(()->notifyObserver(obs->obs.onRequestLobby(nickname))).start();
        });

        wizardView.addEventHandler(MouseEvent.MOUSE_CLICKED, (e)->{
            try {
                SceneController.showDeck(deckSceneController, "DeckScene.fxml");
                turnLabel.setText("Aspetta il tuo turno...");
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });

        character.addEventHandler(MouseEvent.MOUSE_CLICKED, (e)->{
            try {
                SceneController.showCharacter(characterSceneController, "CharacterScene.fxml");
            } catch (IOException ex) {
                ex.printStackTrace();

            }
        });
    }

    /**
     * Sets the subtitle text visible
     * @param text text to show
     */
    public void setSubtitleText(String text){
        subtitle.setVisible(true);
        subtitle.setText(text);
    }

    /**
     * Set the string describing the phase.
     * @param turnLabel Turn's description.
     */

    public void setTurnLabel(String turnLabel) {
        this.turnText = turnLabel;
        if(this.turnLabel != null)
            this.turnLabel.setText(turnLabel);
    }

    /**
     * Displays the clouds in the game board. Utility method.
     */
    public void cloudsDisplay() {
        List<VBox> cloudsVboxes = new ArrayList<>(List.of(vboxCloud1, vboxCloud2));
        if(cloudModels.size() > 2) cloudsVboxes.add(vboxCloud3);
        if(cloudModels.size() > 3) cloudsVboxes.add(vboxCloud4);
        int index = 0;
        for (VBox clBox: cloudsVboxes) clBox.getChildren().clear();
        vboxCloud3.getChildren().clear();
        vboxCloud4.getChildren().clear();
        for(CloudModel c: cloudModels){
            cloudsVboxes.get(index).setAlignment(Pos.CENTER);
            for (ColorPawns s: c.getStudents()){
                Button b = getPawnByColor(s);
                cloudsVboxes.get(index).getChildren().add(b);
            }
            if(!cloudsHasHandler){
                cloudsHasHandler = true;
                setHandlerClouds();
            }
            index++;
        }
    }

    /**
     * Add handler on the cloud image to fire the movement from cloud to entrance.
     */
    public void setHandlerClouds(){
        List<VBox> cloudsVboxes = new ArrayList<>(List.of(vboxCloud1, vboxCloud2));
        if(cloudModels.size() > 2) cloudsVboxes.add(vboxCloud3);
        if(cloudModels.size() > 3) cloudsVboxes.add(vboxCloud4);
        for(int index = 0; index < cloudModels.size(); index++){
            int chosenIndex = index;
            cloudsVboxes.get(index).addEventHandler(MouseEvent.MOUSE_CLICKED, (e)->{
                new Thread(()->notifyObserver(obs -> obs.onChosenCloud(nickname, chosenIndex))).start();
                cloudsVboxes.get(chosenIndex).getChildren().clear();
                turnLabel.setText("Aspetta il tuo turno");
                subtitle.setVisible(false);
            });
        }
    }

    /**
     * Gets a styled button of the pawn. Utility method.
     * @param s Color of the student.
     * @return The button with the image of the student based on the color.
     */
    private Button getPawnByColor(ColorPawns s) {
        Button b = new Button();
        b.setPrefHeight(30.0);
        b.setPrefWidth(35.0);
        String path = "/images_cranio/pawns/" + s.name() +  ".png";
        BackgroundImage backgroundImage = new BackgroundImage(new Image(Objects.requireNonNull(GameBoardSceneController.class.getResourceAsStream(path))),
                BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);

        Background background = new Background(backgroundImage);
        b.setBackground(background);
        return b;
    }

    /**
     * Displays the islands in the game board.
     */
    public void islandsDisplay() {
        int k = 0;
        List<VBox> vBoxes = List.of(vboxIsland1,vboxIsland2,vboxIsland3,vboxIsland4,vboxIsland5,vboxIsland6,vboxIsland7,vboxIsland8,vboxIsland9,vboxIsland10,vboxIsland11,vboxIsland12);
        List<ImageView> images = List.of(island1, island2, island3, island4, island5, island6, island7,island8, island9,island10, island11, island12);

        for(IslandModel i: islands){
            vBoxes.get(k).getChildren().clear();
            setIslandEventListener(vBoxes.get(k), k);
            vBoxes.get(k).setAlignment(Pos.CENTER);
            if(i.getMotherNature()) {
                Button student = getStyledMotherButton();
                tempIndex = k;
                vBoxes.get(k).getChildren().add(student);
            }
            if(i.hasProhibition()){
                Button prohib = getProhib();
                vBoxes.get(k).getChildren().add(prohib);
                islandProhib = k;
            }
            if(i.getTowerColor() != ColorTower.NULL) {
                Button towerBtn = getStyledTower(i.getTowerColor().name());
                vBoxes.get(k).getChildren().add(towerBtn);
            }
            for (ColorPawns st : i.getStudents()) {
                if (!st.name().equals("NULL")) {
                    Button student = getPawnByColor(st);
                    vBoxes.get(k).getChildren().add(student);
                }
            }
            k++;
        }
        for(; k< 12; k++){
            vBoxes.get(k).getChildren().clear();
            vBoxes.get(k).setDisable(true);

            images.get(k).setVisible(false);
        }
    }

    /**
     * Gets the image of the prohibition.
     * @return The button with the prohibition.
     */
    private Button getProhib() {
        Button b = new Button();
        b.setPrefHeight(62.0);
        b.setPrefWidth(53.0);
        String path = "/images_cranio/cards/prohibition.png";
        BackgroundImage backgroundImage = new BackgroundImage(new Image(Objects.requireNonNull(GameBoardSceneController.class.getResourceAsStream(path))),
                BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);

        Background background = new Background(backgroundImage);
        b.setBackground(background);
        return b;
    }


    /**
     * Notifies the observer whenever the player interacts with an island.
     * @param vBox The vbox object where the listener will be added.
     * @param islandIndex The index of the corresponding vbox-island correspondence.
     */
    private void setIslandEventListener(VBox vBox, int islandIndex) {
        vBox.addEventHandler(MouseEvent.MOUSE_CLICKED, (e)-> {
            if(!alreadyMovedStudent) {
                new Thread(() -> notifyObserver(obs -> obs.onUpdateStudentToIsland(nickname, studentToIsland, islandIndex))).start();
                this.alreadyMovedStudent = true;
            }
        });
    }

    /**
     * Gets the image of mother nature. Utility method.
     * @return the button with the image of mother nature
     */
    private Button getStyledMotherButton() {
        Button b = new Button();
        b.setPrefHeight(30.0);
        b.setPrefWidth(23.0);
        String path = "/images_cranio/pawns/MOTHER.png";
        BackgroundImage backgroundImage = new BackgroundImage(new Image(Objects.requireNonNull(GameBoardSceneController.class.getResourceAsStream(path))),
                BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);

        Background background = new Background(backgroundImage);
        b.setBackground(background);
        return b;
    }

    /**
     * Gets the image of the towers
     * @param colorTower color of the towers
     * @return the button with the image of the towers
     */
    private Button getStyledTower(String colorTower) {
        Button b = new Button();
        b.setPrefHeight(30.0);
        b.setPrefWidth(23.0);
        String path = "/images_cranio/towers/" + colorTower + ".png";
        BackgroundImage backgroundImage = new BackgroundImage(new Image(Objects.requireNonNull(GameBoardSceneController.class.getResourceAsStream(path))),
                BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);

        Background background = new Background(backgroundImage);
        b.setBackground(background);
        return b;
    }

    /**
     * Displays the player game board's entrance
     */
    public void entranceDisplay() {
        entrancePane.getChildren().clear();
        Button b = getPawnByColor(entrance.get(0));
        entrancePane.add(b, 1, 0);
        setEntranceEventListener(b, entrance.get(0));
        int idx = 1;
        int rowGrid = 2;
        for(int j = 1; j < rowGrid; j++){
            Button bt = getPawnByColor(entrance.get(idx));
            bt.setMaxHeight(30.0);
            setEntranceEventListener(bt, entrance.get(idx));
            GridPane.setFillWidth(bt, true);
            entrancePane.add(bt, 0, j);
            idx++;
            if(idx == entrance.size()) break;
            Button bt2 = getPawnByColor(entrance.get(idx));
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

    /**
     * Notifies the observer whenever the player interacts with the entrance.
     * The entrance size changes based on the players number.
     * @param button button that represents the students.
     * @param colorToMove color of the student to move
     */
    private void setEntranceEventListener(Button button, ColorPawns colorToMove) {
        if(sizeEntrance == 9) sizeEntrance = 4;
        else if (sizeEntrance == 7) sizeEntrance = 3;
        if(this.numberStudentsToMove == 0) {
            button.addEventHandler(MouseEvent.MOUSE_CLICKED, (e) -> {
                System.out.println("entered to island");
                skipMove.setVisible(false);
                if(gameMode == GameMode.ADVANCED && studentToIsland.size() == 0)
                    new Thread(()->notifyObserver(obs -> obs.onUpdateCharacterCardPlayed(nickname, null))).start();
                if (studentToIsland.size() < sizeEntrance && e.getClickCount() == 1)
                    studentToIsland.add(colorToMove);
                //if(studentToIsland.size() == 3) this.alreadyMovedMother = false;
            });
        }else{
            button.addEventHandler(MouseEvent.MOUSE_CLICKED, (e) -> {
                System.out.println("entered to hall");
                if(e.getClickCount() == 1) {
                    studentToHall.add(colorToMove);
                    if (studentToHall.size() == this.numberStudentsToMove) {
                        this.alreadyMovedMother = false;
                        new Thread(() -> notifyObserver(obs -> obs.onUpdateStudentToHall(nickname, studentToHall))).start();
                        this.numberStudentsToMove = 0;
                        //enableOnlyIsland();
                    }
                }
            });
        }
    }

    /**
     * Displays the towers in the game board
     */
    public void towersDisplay() {
        String colorTower = towers.get(0).name();
        int i;
        towersGrid.getChildren().clear();
        if(!colorTower.equals("NULL")) {
            for (i = 0; i < towers.size() / 2; i++) {
                Button bt = getStyledTower(colorTower);
                towersGrid.add(bt, 0, i);
                Button bt2 = getStyledTower(colorTower);
                towersGrid.add(bt2, 1, i);
            }
            i += 1;
            Button bt = getStyledTower(colorTower);
            if (towers.size() % 2 != 0) towersGrid.add(bt, 0, i);
        }
    }

    /**
     * Shows the correct number of clouds.
     */
    private void showCorrectClouds(){
        if(cloudModels.size() == 3){
            cloud_4.setVisible(false);
        }else if (cloudModels.size() == 2){
            cloud_3.setVisible(false);
            cloud_4.setVisible(false);
        }
    }

    /**
     * Default constructor. The parameter is set by the constructor
     * @param towers list of towers' color
     */
    public void setTowers(List<ColorTower> towers) {
        this.towers =towers;
    }



    /**
     * Default constructor. Parameter is set by the constructor.
     * @param entrance list of students in the entrance
     */
    public void setEntrance(List<ColorPawns> entrance) {
        this.entrance =entrance;
        sizeEntrance = entrance.size();
        if(turnLabel != null) {
            hideSubtitle();
            if (sizeEntrance == 9) turnLabel.setText("Sposta fino a 4 studenti dall'ingresso in un'isola");
            else if (sizeEntrance == 7) turnLabel.setText("Sposta fino a 3 studenti dall'ingresso in un'isola");
        }
    }

    /**
     * Default constructor. Parameter is set by the constructor.
     * @param profs list of the profs.
     */

    public void setProfs(List<ColorPawns> profs) {
        this.profs =profs;
    }

    /**
     * Default constructor. Parameter is set by the constructor.
     * @param hall map of the students in the hall
     */

    public void setHall(Map<ColorPawns, Integer> hall) {
        this.hall = hall;
    }

    /**
     * Default constructor. Parameter is set by the constructor.
     * @param nickname current player
     */

    public void setPlayer(String nickname) {
        this.nickname = nickname;
    }

    /**
     * Default constructor. Parameter is set by the constructor.
     * @param deckSceneController controller of the deck scene
     */

    public void setDeckSceneController(DeckSceneController deckSceneController) {
        this.deckSceneController = deckSceneController;
        if(this.turnLabel != null){
            this.turnLabel.setText("Gioca una carta assistente");
        }
    }

    /**
     * Default constructor. Parameter is set by the constructor.
     * @param islands list of the islands
     */

    public void setIslands(List<IslandModel> islands){
        this.islands = islands;
    }

    /**
     * Default constructor. Parameter is set by the constructor.
     * @param cloudModels list of the clouds
     */

    public void setClouds(List<CloudModel> cloudModels) {
        this.cloudModels = cloudModels;
    }

    private int numberStudentsToMove = 0;

    /**
     * Default constructor. Parameter is set by the constructor.
     * @param numberStudentsToMove number of students to move
     */

    public void setNumberToMove(int numberStudentsToMove) {
        this.numberStudentsToMove = numberStudentsToMove;
    }

    /**
     * Displays the player game board's hall.
     */

    public void hallDisplay() {
        for(int i=0; i < hall.get(ColorPawns.GREEN); i++){
            hallGrid.add(getPawnByColor(ColorPawns.GREEN) , i, 0);
        }
        for(int i=0; i < hall.get(ColorPawns.RED); i++){
            hallGrid.add(getPawnByColor(ColorPawns.RED) , i, 1);
        }
        for(int i=0; i < hall.get(ColorPawns.YELLOW); i++){
            hallGrid.add(getPawnByColor(ColorPawns.YELLOW) , i, 2);
        }
        for(int i=0; i < hall.get(ColorPawns.PINK); i++){
            hallGrid.add(getPawnByColor(ColorPawns.PINK) , i, 3);
        }
        for(int i=0; i < hall.get(ColorPawns.BLUE); i++){
            hallGrid.add(getPawnByColor(ColorPawns.BLUE) , i, 4);
        }
    }

    /**
     * Notifies the observer whenever the player interact with mother nature
     * @param maxMovement max number islands that mother nature can reach
     */

    public void islandHandlerMother(byte maxMovement) {
        List<VBox> vBoxes = List.of(vboxIsland1,vboxIsland2,vboxIsland3,vboxIsland4,vboxIsland5,vboxIsland6,vboxIsland7,vboxIsland8,vboxIsland9,vboxIsland10,vboxIsland11,vboxIsland12);
        //enableOnlyIsland();
        for(int i = tempIndex; i <= (tempIndex + maxMovement); i++){
            int idx = i % islands.size();
            int finalIdx = i;
            vBoxes.get(idx).addEventHandler(MouseEvent.MOUSE_CLICKED, (e)->{
                if(!alreadyMovedMother) {
                    /*if(gameMode == GameMode.ADVANCED)
                        new Thread(()->notifyObserver(obs -> obs.onUpdateCharacterCardPlayed(nickname, null))).start();*/

                    new Thread(() -> notifyObserver(obs -> obs.onUpdateMotherNature(nickname, (byte) (finalIdx - tempIndex)))).start();
                    towersDisplay();
                    tempIndex = idx;
                    //vBoxes.get(idx).getChildren().add(getStyledMotherButton());

                    //enableOnlyClouds();

                    turnLabel.setText("Seleziona una nuvola");
                    subtitle.setText("Sposterai gli studenti nell'ingresso");

                    alreadyMovedMother = true;

                }
            });

        }
    }

    /**
     * Sets the skip button to visible
     */

    public void setVisibleSkip() {
        this.skipMove.setVisible(true);
    }

    /**
     * Displays the profs on the player game board
     */

    public void displayProfs(){
        int row = 0;
        profsGrid.getChildren().clear();
        for (ColorPawns pr: List.of(ColorPawns.GREEN, ColorPawns.RED, ColorPawns.YELLOW,ColorPawns.PINK,ColorPawns.BLUE)){
            if(profs.contains(pr)){
                profsGrid.add(getPawnByColor(pr), 0, row);
            }
            row+=1;
        }
    }

    /**
     * Reset local variables to restart from original state in the next turn.
     */

    public void setEndTurn() {
        this.alreadyMovedStudent = false;
        this.numberStudentsToMove = 0;
        this.alreadyMovedMother = true;
    }

    /**
     * Refresh the objects of the students.
     */
    public void setNewTurn(){
        displayProfs();
        islandsDisplay();
        entranceDisplay();
        this.studentToIsland.clear();
        this.studentToHall.clear();
    }

    /**
     * Set the controller for the scene of the character cards.
     * @param characterSceneController controller of the character cards scene
     */
    public void setCharacterSceneController(CharacterSceneController characterSceneController){
        this.characterSceneController = characterSceneController;
        characterSceneController.setEntrance(entrance);
    }

    /**
     * Set the game mode in this controller.
     * @param gameMode chosen game mode
     */
    public void setGameMode(GameMode gameMode) {
        this.gameMode = gameMode;
    }

    /**
     * Sets the subtitle to visible.
     */
    public void setVisibleSubtitle() {
        subtitle.setVisible(true);
    }

    /**
     * Set the nickname list in this controller.
     * @param nicknameList list of the players' nicknames
     */
    public void setNicknameList(List<PlayerModel> nicknameList) {
        this.nicknameList = nicknameList;
        setLobbyTable();
    }

    /**
     * Sets the lobby table.
     */
    private void setLobbyTable() {
        lobbyTable.getChildren().clear();
        for(int i = 0; i < nicknameList.size(); i++){
            Label lb = new Label(nicknameList.get(i).getNickname());
            int id = i;
            lb.setFont(new Font(22.0));
            lb.setStyle("-fx-background-color: lightgray;");
            lb.addEventHandler(MouseEvent.MOUSE_ENTERED, (e)-> {
                lb.setStyle("-fx-background-color: lightblue;");
            });
            lb.addEventHandler(MouseEvent.MOUSE_EXITED, (e)-> {
                lb.setStyle("-fx-background-color: transparent;");
            });
            lb.addEventHandler(MouseEvent.MOUSE_CLICKED, (e)->{

                if (e.getClickCount() == 2) {
                    OtherGameBoardSceneController g = new OtherGameBoardSceneController();
                    g.setPlayer(nicknameList.get(id));
                    try {
                        SceneController.showWindow(g, nicknameList.get(id).getNickname(), "OtherBoardScene.fxml");
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
            });
            lobbyTable.add(lb,0 ,i);

        }
    }

    /**
     * Hides the subtitle of the turn.
     */
    public void hideSubtitle() {
        if(this.subtitle != null)
            this.subtitle.setVisible(false);
    }
}
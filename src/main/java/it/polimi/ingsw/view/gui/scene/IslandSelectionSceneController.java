package it.polimi.ingsw.view.gui.scene;

import it.polimi.ingsw.model.colors.ColorPawns;
import it.polimi.ingsw.model.colors.ColorTower;
import it.polimi.ingsw.model.islands.IslandModel;
import it.polimi.ingsw.observer.ViewObservable;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;

import java.util.List;
import java.util.Objects;

public class IslandSelectionSceneController extends ViewObservable implements GenericSceneController{
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

    private List<IslandModel> islands;
    private String nickname;

    public void setIslands(List<IslandModel> islands) {
        this.islands = islands;
    }

    @FXML
    private void initialize(){


    }



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
                vBoxes.get(k).getChildren().add(student);
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

    private ColorPawns colorToMove;

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public void setColorToMove(ColorPawns colorToMove) {
        this.colorToMove = colorToMove;
    }

    private void setIslandEventListener(VBox vBox, int islandIndex) {
        vBox.addEventHandler(MouseEvent.MOUSE_CLICKED, (e)-> {
            new Thread(() -> notifyObserver(obs -> obs.onUpdateMovedStudentFromCardToIsland(nickname, islandIndex, colorToMove))).start();
        });
    }

    private Button getPawnByColor(ColorPawns s) {
        Button b = new Button();
        b.setPrefHeight(30.0);
        b.setPrefWidth(35.0);
        String path = "/images_cranio/pawns/" + s.name() +  ".png";
        BackgroundImage backgroundImage = new BackgroundImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream(path))),
                BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);

        Background background = new Background(backgroundImage);
        b.setBackground(background);
        return b;
    }

    private Button getStyledMotherButton() {
        Button b = new Button();
        b.setPrefHeight(30.0);
        b.setPrefWidth(23.0);
        String path = "/images_cranio/pawns/MOTHER.png";
        BackgroundImage backgroundImage = new BackgroundImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream(path))),
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
        BackgroundImage backgroundImage = new BackgroundImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream(path))),
                BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);

        Background background = new Background(backgroundImage);
        b.setBackground(background);
        return b;
    }

}

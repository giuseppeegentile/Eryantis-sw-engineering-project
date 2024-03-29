package it.polimi.ingsw.view.gui.scene;

import it.polimi.ingsw.model.colors.ColorPawns;
import it.polimi.ingsw.model.colors.ColorTower;
import it.polimi.ingsw.model.player.PlayerModel;
import it.polimi.ingsw.observer.ViewObservable;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * Class the implements the scene with the other players game board
 */

public class OtherGameBoardSceneController extends ViewObservable implements GenericSceneController{
    private PlayerModel player;
    private Map<ColorPawns, Integer> hall;
    private List<ColorPawns> profs;
    private List<ColorPawns> entrance;

    @FXML
    private Label titleLabel;

    @FXML
    private GridPane profsGrid;

    @FXML
    private GridPane hallGrid;

    @FXML
    private GridPane entrancePane;


    private boolean readOnly;

    /**
     * Manages every interaction of the player with the graphical objects in the scene
     */

    @FXML
    private void initialize() {
        entranceDisplay();
        hallDisplay();
        titleLabel.setText("Plancia di gioco di: " + player.getNickname());
    }

    /**
     * Displays the player board's entrance
     */

    public void entranceDisplay() {
        entrancePane.getChildren().clear();
        Button b = getPawnByColor(entrance.get(0));
        entrancePane.add(b, 1, 0);
        int idx = 1;
        int rowGrid = 2;
        for(int j = 1; j < rowGrid; j++){
            Button bt = getPawnByColor(entrance.get(idx));
            bt.setMaxHeight(30.0);
            GridPane.setFillWidth(bt, true);
            entrancePane.add(bt, 0, j);
            idx++;
            if(idx == entrance.size()) break;
            Button bt2 = getPawnByColor(entrance.get(idx));
            bt2.setMaxHeight(30.0);
            GridPane.setFillWidth(bt2, true);
            entrancePane.add(bt2, 1, j);
            idx++;
            if(idx == entrance.size()) break;
            rowGrid++;
        }
    }

    /**
     * Display the player board's hall
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
     * Gets the image of the students from the resources.
     * @param s student associated with the color
     * @return the button with the students image
     */

    private Button getPawnByColor(ColorPawns s) {
        Button b = new Button();
        b.setPrefHeight(30.0);
        b.setPrefWidth(35.0);
        String path = "/images_cranio/pawns/" + s.name() +  ".png";
        BackgroundImage backgroundImage = new BackgroundImage(new Image(Objects.requireNonNull(OtherGameBoardSceneController.class
                .getResourceAsStream(path))),
                BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);

        Background background = new Background(backgroundImage);
        b.setBackground(background);
        return b;
    }

    /**
     * Default constructor. Parameter is set by the constructor. Also sets the students in the hall and in the entrance.
     * @param player current player
     */

    public void setPlayer(PlayerModel player) {
        this.player = player;
        hall = player.getStudentInHall();
        entrance = player.getStudentInEntrance();
    }
}
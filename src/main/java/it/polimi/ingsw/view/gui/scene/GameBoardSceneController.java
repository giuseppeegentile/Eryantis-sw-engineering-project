package it.polimi.ingsw.view.gui.scene;

import it.polimi.ingsw.model.colors.ColorPawns;
import it.polimi.ingsw.model.colors.ColorTower;
import it.polimi.ingsw.model.enums.GameMode;
import it.polimi.ingsw.observer.ViewObservable;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.Map;

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
    private Button student_1;

    @FXML
    private Button student_2;

    @FXML
    private AnchorPane pane;

    @FXML
    private Button lobbyBtn;

    @FXML
    private Button lobbyTable;

    @FXML
    private void initialize() {
        lobbyTable.setVisible(false);
        int gapX = 41;
        int gapY = 30;
        for(int i = 2, j = 1, k=1; i < entrance.size();i++){
            ColorPawns s = entrance.get(i);
            if(i%2 == 0){
                Button btn = new Button();//da mettere poi le image view al posto dei bottoni, con l'immagine dello studente corrispondente a colorPawns
                btn.setText("S");
                btn.setLayoutX(student_1.getLayoutX() + (j*gapX));
                btn.setLayoutY(student_2.getLayoutY() - (k*gapY));
                j++;
            }else{
                Button btn = new Button();//da mettere poi le image view al posto dei bottoni, con l'immagine dello studente corrispondente a colorPawns
                btn.setText("S");
                btn.setLayoutY(student_2.getLayoutY() + (k*gapY));
                btn.setLayoutX(student_1.getLayoutX() - (j*gapX));
                k++;
            }
        }

        if(numClouds == 3){
            cloud_4.setVisible(false);
        }else if (numClouds == 2){
            cloud_3.setVisible(false);
            cloud_4.setVisible(false);
        }
        lobbyBtn.addEventHandler(MouseEvent.MOUSE_CLICKED, (e)->{
            lobbyTable.setVisible(!lobbyTable.isVisible()); //toggle
            //new Thread(() -> notifyObserver(obs -> obs.onLobbyInfoRequest())).start(); ....get the lobby message from server and display rows with nickname
        });

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
}

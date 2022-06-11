package it.polimi.ingsw.view.gui.scene;

import it.polimi.ingsw.model.colors.ColorPawns;
import it.polimi.ingsw.model.colors.ColorTower;
import it.polimi.ingsw.model.enums.GameMode;
import it.polimi.ingsw.observer.ViewObservable;
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

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.lang.reflect.Parameter;
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
    private TableView<String> lobbyTable;

    private boolean readOnly;


    @FXML
    TableColumn<String, String> gamersCol;

    private List<String> nicknameList;

    @FXML
    private void initialize() throws InterruptedException {
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

    public void readOnly(boolean readOnly) {
        this.readOnly = readOnly;
        //to do: disable all buttons and actions, is showing the board of another player to a player: he can't perform actions!
    }

    public void setPlayersLobby(List<String> nicknameList) {
        this.nicknameList = nicknameList;
    }
}

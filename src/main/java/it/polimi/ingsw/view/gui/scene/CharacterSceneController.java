package it.polimi.ingsw.view.gui.scene;

import it.polimi.ingsw.model.cards.CharacterCardModel;
import it.polimi.ingsw.model.colors.ColorPawns;
import it.polimi.ingsw.model.effects.InitialConfigEffect;
import it.polimi.ingsw.model.islands.IslandModel;
import it.polimi.ingsw.model.player.PlayerModel;
import it.polimi.ingsw.observer.ViewObservable;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class CharacterSceneController extends ViewObservable implements GenericSceneController {

    private List<CharacterCardModel> cards;
    private List<ColorPawns> studentsFromCard = new ArrayList<>();
    private List<ColorPawns> entrance;
    private List<ColorPawns> studentsFromEntrance;

    @FXML
    private HBox boxCost_1;
    @FXML
    private HBox boxCost_2;
    @FXML
    private HBox boxCost_3;
    @FXML
    private GridPane gridEntrance;
    @FXML
    private GridPane gridStudent_1;
    @FXML
    private GridPane gridStudent_2;
    @FXML
    private GridPane gridStudent_3;
    @FXML
    private TextField label_1;
    @FXML
    private TextField label_2;
    @FXML
    private TextField label_3;
    @FXML
    private Label text_1;
    @FXML
    private Label text_2;
    @FXML
    private Label text_3;
    @FXML
    private ImageView card1;
    @FXML
    private ImageView card2;
    @FXML
    private ImageView card3;
    @FXML
    private Label moneyPlayerLbl;

    private PlayerModel nickname;

    private List<TextField> texts;
    private List<Label> labels;
    private int playerMoney;
    private List<IslandModel> islands;

    public void setIslands(List<IslandModel> islands) {
        this.islands = islands;
    }

    private void initialHide(){
        gridEntrance.setVisible(false);
        for (Label l : labels)
            l.setVisible(false);
        for (TextField t : texts)
            t.setVisible(false);
    }

    @FXML
    private void initialize(){
        texts = List.of(label_1, label_2, label_3);
        labels = List.of(text_1, text_2, text_3);
        initialHide();
        moneyPlayerLbl.setText(String.valueOf(playerMoney));

        List<ImageView> imagesList = List.of(card1,card2,card3);
        List<HBox> hboxList = List.of(boxCost_1, boxCost_2, boxCost_3);
        List<GridPane> gridPaneList = List.of(gridStudent_1, gridStudent_2, gridStudent_3);
        int i = 0;
        for(CharacterCardModel card: cards){
            int id = card.getCharacterId()+1;
            String path = "/characters/CarteTOT_front" + id + ".jpg";
            imagesList.get(i).setImage(new Image(Objects.requireNonNull(CharacterSceneController.class.getResourceAsStream(path))));

            int finalIndex = i;

            int maxStudentToMove = 1;
            int finalIdx = i;
            String effectName = card.getEffect().getClass().getSimpleName();
            switch (effectName) {
                case "AddToHallEffect":
                case "AddToIslandEffect":
                    placeStudentsOnCard(gridPaneList, i, card, maxStudentToMove, effectName);
                    break;
                case "ExchangeConfigEntranceEffect":
                    maxStudentToMove = 3;
                    gridEntrance.setVisible(true);
                    placeStudentsOnEntrance(gridEntrance, entrance, 3);
                    placeStudentsOnCard(gridPaneList, i, card, maxStudentToMove, effectName);
                    break;
                case "ProhibitionEffect":
                    imagesList.get(i).addEventHandler(MouseEvent.MOUSE_CLICKED, (e) -> {
                        if(card.enoughCoins(nickname.getCoins())) {
                            texts.get(finalIdx).setVisible(true);
                            labels.get(finalIndex).setVisible(true);
                            texts.get(finalIndex).setOnKeyPressed(ev -> {
                                if (ev.getCode() == KeyCode.ENTER) {
                                    int indexIsland = Integer.parseInt(texts.get(finalIdx).getText());
                                    new Thread(() -> notifyObserver(obs -> obs.onUpdateBanCard(nickname.getNickname(), indexIsland - 1))).start();
                                    ((Stage) card1.getScene().getWindow()).close();
                                }
                            });
                        }
                    });
                    break;
                case "ExcludeColorInfluenceEffect":
                    imagesList.get(i).addEventHandler(MouseEvent.MOUSE_CLICKED, (e) -> {
                            if(card.enoughCoins(nickname.getCoins())) {
                                labels.get(finalIndex).setVisible(true);
                                labels.get(finalIndex).setText("Seleziona il colore");
                                List<ColorPawns> colors = List.of(ColorPawns.BLUE, ColorPawns.RED, ColorPawns.PINK, ColorPawns.GREEN);
                                int k = 0;
                                int j = 0;
                                for (; k < 2; k++) {
                                    Button b = getPawnByColor(colors.get(j));
                                    int finalJ1 = j;
                                    b.addEventHandler(MouseEvent.MOUSE_CLICKED, (ev) -> {
                                        new Thread(() -> notifyObserver(obs -> obs.onUpdateColorToIgnore(nickname.getNickname(), colors.get(finalJ1)))).start();
                                        ((Stage) card1.getScene().getWindow()).close();
                                    });
                                    gridPaneList.get(finalIndex).add(b, 0, k);
                                    j += 1;
                                    Button b2 = getPawnByColor(colors.get(j));
                                    int finalJ = j;
                                    b2.addEventHandler(MouseEvent.MOUSE_CLICKED, (ev) -> {
                                        new Thread(() -> notifyObserver(obs -> obs.onUpdateColorToIgnore(nickname.getNickname(), colors.get(finalJ)))).start();
                                        ((Stage) card1.getScene().getWindow()).close();
                                    });
                                    gridPaneList.get(finalIndex).add(b2, 1, k);
                                    j += 1;
                                }
                                Button b = getPawnByColor(ColorPawns.YELLOW);
                                b.addEventHandler(MouseEvent.MOUSE_CLICKED, (ev) -> {
                                    new Thread(() -> notifyObserver(obs -> obs.onUpdateColorToIgnore(nickname.getNickname(), ColorPawns.YELLOW))).start();
                                    ((Stage) card1.getScene().getWindow()).close();
                                });
                                gridPaneList.get(finalIndex).add(b, 0, 2);
                            }
                    });
                    break;
            }
            imagesList.get(i).addEventHandler(MouseEvent.MOUSE_CLICKED, (e) -> {
                if (cards.get(finalIndex).enoughCoins(this.nickname.getCoins())){
                    new Thread(() -> notifyObserver(obs -> obs.onUpdateCharacterCardPlayed(this.nickname.getNickname(), cards.get(finalIndex)))).start();
                    List<String> effectsNotConfig = List.of("AddInfluenceEffect", "ControlProfEffect", "ExtraMovementMotherEffect", "IgnoreTowerEffect");
                    if (effectsNotConfig.contains(card.getEffect().getClass().getSimpleName()) && card.enoughCoins(this.nickname.getCoins()))
                        ((Stage) card1.getScene().getWindow()).close();
                }
            });

            Tooltip tooltip = new Tooltip(card.getEffect().getDescription());
            Tooltip.install(imagesList.get(finalIndex), tooltip);
            Tooltip.install(hboxList.get(finalIndex), tooltip);
            Tooltip.install(gridPaneList.get(finalIndex), tooltip);
            imagesList.get(i).addEventHandler(MouseEvent.MOUSE_ENTERED, (e)->{ //hover effect
                imagesList.get(finalIndex).setOpacity(0.7);
            });

            imagesList.get(i).addEventHandler(MouseEvent.MOUSE_EXITED, (e)->{
                imagesList.get(finalIndex).setOpacity(1);
            });
            i++;
        }
    }

    private void placeStudentsOnCard(List<GridPane> gridPaneList, int i, CharacterCardModel card, int maxStudents, String effect) {
        List<ColorPawns> students = ((InitialConfigEffect) card.getEffect()).getStudents();
        gridPaneList.get(i).getChildren().clear();
        int idx = 0;
        int rowGrid = 1;
        for (int j = 0; j < rowGrid; j++) {
            Button bt = getPawnByColor(students.get(idx));
            bt.setMaxHeight(30.0);
            setStudentsEventListener(bt, students.get(idx), maxStudents, effect, i, card);
            GridPane.setFillWidth(bt, true);
            gridPaneList.get(i).add(bt, 0, j);
            idx++;
            if (idx == students.size()) break;
            Button bt2 = getPawnByColor(students.get(idx));
            setStudentsEventListener(bt2, students.get(idx), maxStudents, effect, i, card);
            bt2.setMaxHeight(30.0);
            GridPane.setFillWidth(bt2, true);
            gridPaneList.get(i).add(bt2, 1, j);
            idx++;
            if (idx == students.size()) break;
            rowGrid++;
        }
        gridPaneList.get(i).setEffect(new DropShadow(10, Color.YELLOW));
    }

    public void setDeck(List<CharacterCardModel> cards) {
        this.cards = cards;
        if(boxCost_1 != null) {
            List<HBox> hboxes = List.of(boxCost_1, boxCost_2, boxCost_3);
            for (int k = 0; k < hboxes.size(); k++) {
                hboxes.get(k).getChildren().clear();
                for (int j = 0; j < cards.get(k).getEffect().getMoneyOnCard(); j++)
                    hboxes.get(k).getChildren().add(getStyledCoins());
            }
        }
    }

    private Button getStyledCoins() {
        Button b = new Button();
        b.setPrefHeight(30.0);
        b.setPrefWidth(30.0);
        String path = "/images_cranio/coin.png";
        BackgroundImage backgroundImage = new BackgroundImage(new Image(getClass().getResource(path).toExternalForm()),
                BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);

        Background background = new Background(backgroundImage);
        b.setBackground(background);
        return b;
    }

    private Button getPawnByColor(ColorPawns s) {
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

    private void setStudentsEventListener(Button button, ColorPawns colorToMove, int maxStudents, String effect, int numCard, CharacterCardModel card) {
        button.addEventHandler(MouseEvent.MOUSE_CLICKED, (e) -> {
            if (studentsFromCard.size() < 3)
                studentsFromCard.add(colorToMove);
            if (studentsFromCard.size() == maxStudents){
                switch (effect) {
                    case "AddToHallEffect":
                        if(card.enoughCoins(nickname.getCoins())) {
                                new Thread(() -> notifyObserver(obs -> obs.onMovedStudentsFromCardToHall(nickname.getNickname(), colorToMove))).start();
                                ((Stage) card1.getScene().getWindow()).close();
                        }
                        break;
                    case "AddToIslandEffect":
                        if(card.enoughCoins(nickname.getCoins())) {
                            labels.get(numCard).setVisible(true);
                            texts.get(numCard).setVisible(true);
                            texts.get(numCard).setOnKeyPressed(ev -> {
                                if (ev.getCode() == KeyCode.ENTER) {
                                    int indexIsland = Integer.parseInt(texts.get(numCard).getText());
                                    new Thread(() -> notifyObserver(obs -> obs.onUpdateMovedStudentFromCardToIsland(nickname.getNickname(), indexIsland - 1, colorToMove))).start();
                                    ((Stage) card1.getScene().getWindow()).close();
                                }
                            });
                        }
                        break;
                    case "ExchangeConfigEntranceEffect":
                        if(card.enoughCoins(nickname.getCoins())) {
                            new Thread(() -> notifyObserver(obs -> obs.onUpdateMovedStudentsFromCardToEntrance(nickname.getNickname(), studentsFromCard, studentsFromEntrance))).start();
                        }
                        break;
                }
            }

        });
    }

    public void setNickname(PlayerModel nickname) {
        this.nickname = nickname;
    }

    public void setEntrance(List<ColorPawns> studentInEntrance) {
        this.entrance = studentInEntrance;
    }

    private void placeStudentsOnEntrance(GridPane gridPane, List<ColorPawns> entrance, int maxStudents) {
        gridPane.getChildren().clear();
        int idx = 0;
        int rowGrid = 1;
        for (int j = 0; j < rowGrid; j++) {
            Button bt = getPawnByColor(entrance.get(idx));
            bt.setMaxHeight(30.0);
            setStudentsEventListenerEntrance(bt, entrance.get(idx), maxStudents);
            GridPane.setFillWidth(bt, true);
            gridPane.add(bt, 0, j);
            idx++;
            if (idx == entrance.size()) break;
            Button bt2 = getPawnByColor(entrance.get(idx));
            setStudentsEventListenerEntrance(bt2, entrance.get(idx), maxStudents);
            bt2.setMaxHeight(30.0);
            GridPane.setFillWidth(bt2, true);
            gridPane.add(bt2, 1, j);
            idx++;
            if (idx == entrance.size()) break;
            rowGrid++;
        }
        gridPane.setEffect(new DropShadow(10, Color.YELLOW));
    }

    private void setStudentsEventListenerEntrance(Button button, ColorPawns colorToMove, int maxStudents) {
        button.addEventHandler(MouseEvent.MOUSE_CLICKED, (e) -> {
            if (studentsFromEntrance.size() < maxStudents)
                studentsFromEntrance.add(colorToMove);
        });
    }

    public void setPlayerMoney(int playerMoney) {
        this.playerMoney = playerMoney;
        if(moneyPlayerLbl != null) {
            System.out.println("sono dentro");
            moneyPlayerLbl.setText(String.valueOf(playerMoney));
        }
        if(boxCost_1!= null) {
            List<HBox> hboxList = List.of(boxCost_1, boxCost_2, boxCost_3);
            for (int i = 0; i < cards.size(); i++) {
                hboxList.get(i).getChildren().clear();
                for (int j = 0; j < cards.get(i).getEffect().getMoneyOnCard(); j++) {
                    hboxList.get(i).getChildren().add(getStyledCoins());
                }
            }
        }

    }
}

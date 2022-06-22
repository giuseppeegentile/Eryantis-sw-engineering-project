package it.polimi.ingsw.view.gui.scene;

import it.polimi.ingsw.model.cards.CharacterCardModel;
import it.polimi.ingsw.observer.ViewObservable;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.util.List;
import java.util.Objects;

public class CharacterSceneController extends ViewObservable implements GenericSceneController {

    private List<CharacterCardModel> cards;

    @FXML
    private HBox boxCost_1;
    @FXML
    private HBox boxCost_2;
    @FXML
    private HBox boxCost_3;
    @FXML
    private ImageView card1;
    @FXML
    private ImageView card2;
    @FXML
    private ImageView card3;

    private String nickname;

    @FXML
    private void initialize()  {
        List<ImageView> imagesList = List.of(card1,card2,card3);
        List<HBox> hboxList = List.of(boxCost_1, boxCost_2, boxCost_3);
        int i = 0;
        for(CharacterCardModel card: cards){
            String path = "/images_cranio/characters/CarteTOT_front" + card.getCharacterId()+1 + ".jpg";
            imagesList.get(i).setImage(new Image(Objects.requireNonNull(DeckSceneController.class.getResourceAsStream(path))));

            int finalIndex = i;
            for(int j=0; j<card.getMoneyOnCard();j++) {
                hboxList.get(i).getChildren().add(getStyledCoins());
            }
            imagesList.get(i).addEventHandler(MouseEvent.MOUSE_CLICKED, (e)->{
                new Thread(()->notifyObserver(obs -> obs.onUpdateCharacterCardPlayed(this.nickname, cards.get(finalIndex)))).start();
                ((Stage)card1.getScene().getWindow()).close();
            });
            Tooltip tooltip = new Tooltip(card.getEffect().getDescription());
            Tooltip.install(imagesList.get(finalIndex), tooltip);
            imagesList.get(i).addEventHandler(MouseEvent.MOUSE_ENTERED, (e)->{ //hover effect
                imagesList.get(finalIndex).setOpacity(0.7);
            });
            imagesList.get(i).addEventHandler(MouseEvent.MOUSE_EXITED, (e)->{
                imagesList.get(finalIndex).setOpacity(1);
            });
            i++;
        }
    }

    public void setDeck(List<CharacterCardModel> cards) {
        this.cards = cards;
    }

    private Button getStyledCoins() {
        Button b = new Button();
        b.setPrefHeight(30.0);
        b.setPrefWidth(23.0);
        String path = "/images_cranio/coin.png";
        BackgroundImage backgroundImage = new BackgroundImage(new Image(getClass().getResource(path).toExternalForm()),
                BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);

        Background background = new Background(backgroundImage);
        b.setBackground(background);
        return b;
    }


    public void setNickname(String nickname) {
        this.nickname = nickname;
    }
}
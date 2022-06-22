package it.polimi.ingsw.view.gui.scene;

import it.polimi.ingsw.model.cards.CharacterCardModel;
import it.polimi.ingsw.observer.ViewObservable;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.util.List;
import java.util.Objects;

public class CharacterSceneController extends ViewObservable implements GenericSceneController {

    private List<CharacterCardModel> cards;

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
        int i = 0;
        for(CharacterCardModel card: cards){
            String path = "/images_cranio/characters/CarteTOT_front" + card.getCharacterId() + ".jpg";
            imagesList.get(i).setImage(new Image(Objects.requireNonNull(DeckSceneController.class.getResourceAsStream(path))));

            int finalIndex = i;
            imagesList.get(i).addEventHandler(MouseEvent.MOUSE_CLICKED, (e)->{
                new Thread(()->notifyObserver(obs -> obs.onUpdateCharacterCardPlayed(this.nickname, cards.get(finalIndex)))).start();
                ((Stage)card1.getScene().getWindow()).close();
            });
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

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }
}
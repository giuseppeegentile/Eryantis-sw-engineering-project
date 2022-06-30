package it.polimi.ingsw.view.gui.scene;

import it.polimi.ingsw.model.cards.AssistantCardModel;
import it.polimi.ingsw.model.colors.ColorPawns;
import it.polimi.ingsw.observer.ViewObservable;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.util.List;
import java.util.Objects;

/**
 * Class the manages every interaction in the player's deck scene.
 */

public class DeckSceneController extends ViewObservable implements GenericSceneController {
    private List<AssistantCardModel> cards;

    @FXML
    private ImageView card_1;
    @FXML
    private ImageView card_2;
    @FXML
    private ImageView card_3;
    @FXML
    private ImageView card_4;
    @FXML
    private ImageView card_5;
    @FXML
    private ImageView card_6;
    @FXML
    private ImageView card_7;
    @FXML
    private ImageView card_8;
    @FXML
    private ImageView card_9;
    @FXML
    private ImageView card_10;

    private String nickname;

    /**
     * Manages every interaction of the player with the graphical objects in the scene
     */

    @FXML
    private void initialize()  {
        List<ImageView> imagesList = List.of(card_1,card_2,card_3,card_4,card_5,card_6,card_7,card_8,card_9,card_10);
        int i = 0;
        for(AssistantCardModel card: cards){
            if(card.getPriority() != 0) {
                String path = "/images_cranio/cards/Assistente_" + card.getPriority() + ".png";
                imagesList.get(i).setImage(new Image(Objects.requireNonNull(DeckSceneController.class.getResourceAsStream(path))));

                int finalIndex = i;
                imagesList.get(i).addEventHandler(MouseEvent.MOUSE_CLICKED, (e)->{
                    new Thread(()->notifyObserver(obs -> obs.onUpdateCardPlayed(this.nickname, finalIndex))).start();
                    ((Stage)card_1.getScene().getWindow()).close();
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
    }

    /**
     * Default constructor. Parameter is set by the constructor
     * @param cards list of assistant cards
     */

    public void setDeck(List<AssistantCardModel> cards) {
        this.cards =cards;
    }

    /**
     * Default constructor. Parameter is set by the constructor
     * @param nickname current player
     */

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }
}
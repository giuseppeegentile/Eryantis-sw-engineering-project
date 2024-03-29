package it.polimi.ingsw.view.gui;

import it.polimi.ingsw.network.client.Client;
import it.polimi.ingsw.observer.ViewObservable;
import it.polimi.ingsw.observer.ViewObserver;
import it.polimi.ingsw.view.gui.scene.*;
import javafx.event.Event;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;
import java.util.logging.Logger;

import static java.util.logging.Level.SEVERE;

/**
 * This class implements the controller of a generic scene.
 */
public class SceneController extends ViewObservable {
    private static Scene activeScene;
    private static GenericSceneController activeController;

    /**
     * Returns the active scene.
     *
     * @return active scene.
     */
    public static Scene getActiveScene() {
        return activeScene;
    }

    /**
     * Changes the root panel of the scene argument.
     *
     * @param observerList a list of observers to be set into the scene controller.
     * @param scene        the scene whose change the root panel. This will become the active scene.
     * @param fxml         the new scene fxml name. It must include the extension ".fxml" (i.e. next_scene.fxml).
     * @param <T>          this is the type parameter.
     * @return the controller of the new scene loaded by the FXMLLoader.
     */
    public static <T> T changeRootPane(List<ViewObserver> observerList, Scene scene, String fxml) {
        T controller = null;

        try {
            FXMLLoader loader = new FXMLLoader(SceneController.class.getResource("/fxml/" + fxml));
            Parent root = loader.load();
            controller = loader.getController();
            ((ViewObservable) controller).addAllObservers(observerList);

            activeController = (GenericSceneController) controller;
            activeScene = scene;
            activeScene.setRoot(root);
        } catch (IOException e) {
            Client.LOGGER.severe(e.getMessage());
            e.printStackTrace();
        }
        return controller;
    }

    /**
     * Changes the root panel of the scene argument.
     *
     * @param <T>          this is the type parameter.
     * @param observerList a list of observers to be set into the scene controller.
     * @param event        the event which is happened into the scene.
     * @param fxml         the new scene fxml name. It must include the extension ".fxml" (i.e. next_scene.fxml).
     */
    public static <T> void changeRootPane(List<ViewObserver> observerList, Event event, String fxml) {
        Scene scene = ((Node) event.getSource()).getScene();
        changeRootPane(observerList, scene, fxml);
    }

    /**
     * Changes the root panel of the active scene.
     *
     * @param observerList a list of observers to be set into the scene controller.
     * @param fxml         the new scene fxml name. It must include the extension ".fxml" (i.e. next_scene.fxml).
     * @param <T>          this is the type parameter.
     * @return the controller of the new scene loaded by the FXMLLoader.
     */
    public static <T> T changeRootPane(List<ViewObserver> observerList, String fxml) {
        return changeRootPane(observerList, activeScene, fxml);
    }

    /**
     * Changes the root panel of the scene argument.
     * Offers the possibility to set a custom controller to the FXMLLoader.
     *
     * @param controller the custom controller that will be set into the FXMLLoader.
     * @param scene      the scene whose change the root panel. This will become the active scene.
     * @param fxml       the new scene fxml name. It must include the extension ".fxml" (i.e. next_scene.fxml).
     */
    public static void changeRootPane(GenericSceneController controller, Scene scene, String fxml) {
        try {
            FXMLLoader loader = new FXMLLoader(SceneController.class.getResource("/fxml/" + fxml));

            loader.setController(controller);
            activeController = controller;
            Parent root = loader.load();

            activeScene = scene;
            activeScene.setRoot(root);
        } catch (IOException e) {
            Client.LOGGER.severe(e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Changes the root panel of the active scene.
     * Offers the possibility to set a custom controller to the FXMLLoader.
     *
     * @param controller the custom controller that will be set into the FXMLLoader.
     * @param fxml       the new scene fxml name. It must include the extension ".fxml" (i.e. next_scene.fxml).
     */
    public static void changeRootPane(GenericSceneController controller, String fxml) {
        changeRootPane(controller, activeScene, fxml);
    }

    /**
     * Shows a custom message in a popup.
     *
     * @param title the popup's title.
     * @param message the popup's message.
     */
    public static void showAlert(String title, String message) {
        FXMLLoader loader = new FXMLLoader(SceneController.class.getResource("/fxml/AlertScene.fxml"));

        Parent parent;
        try {
            parent = loader.load();
        } catch (IOException e) {
            Client.LOGGER.severe(e.getMessage());
            e.printStackTrace();
            return;
        }
        ErrorSceneController alertSceneController = loader.getController();
        Scene alertScene = new Scene(parent);
        alertSceneController.setScene(alertScene);
        alertSceneController.setAlertTitle(title);
        alertSceneController.setAlertMessage(message);
        alertSceneController.displayAlert();
    }

    /**
     * Shows the board of another player in a new window.
     * @param board the controller of that board window.
     * @param nickname the nickname owning that board.
     * @param nameFxml the name of the fxml file.
     */
    public static void showWindow(OtherGameBoardSceneController board, String nickname, String nameFxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(SceneController.class.getResource("/fxml/" + nameFxml));
        fxmlLoader.setController(board);

        String title = "Plancia di gioco di: " + nickname;
        buildWindow(title, fxmlLoader);
    }

    /**
     * Shows a scene which contains the player's list of assistant cards
     * @param deckSceneController controller of the deck scene
     * @param nameFxml name of the fxml file
     * @throws IOException
     */
    public static void showDeck(DeckSceneController deckSceneController, String nameFxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(SceneController.class.getResource("/fxml/" + nameFxml));
        fxmlLoader.setController(deckSceneController);
        String title = "Carte disponibili: ";
        buildWindow(title, fxmlLoader);
    }

    /**
     * Builds the main game's window
     * @param title scene's title
     * @param fxmlLoader the loader of every game's scene
     */

    private static void buildWindow(String title, FXMLLoader fxmlLoader) {
        try {
            Scene scene = new Scene(fxmlLoader.load(), 1280, 720);
            Stage stage = new Stage();
            stage.setResizable(false);
            stage.setTitle(title);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            Logger logger = Logger.getLogger(SceneController.class.getName());
            logger.log(SEVERE, "Failed to create new Window.", e);
        }
    }

    /**
     * Shows a scene which contains a list of three character cards in the expert mode.
     * @param characterSceneController controller of the cards scene.
     * @param nameFxml name of the fxml file.
     * @throws IOException If the file can't be found
     */
    public static void showCharacter(CharacterSceneController characterSceneController, String nameFxml)  throws IOException{
        FXMLLoader fxmlLoader = new FXMLLoader(SceneController.class.getResource("/fxml/" + nameFxml));
        fxmlLoader.setController(characterSceneController);
        String title = "Carte disponibili: ";
        buildWindow(title, fxmlLoader);
    }
}
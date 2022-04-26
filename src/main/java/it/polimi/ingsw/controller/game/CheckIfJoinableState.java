package it.polimi.ingsw.controller.game;

import it.polimi.ingsw.model.islands.ColorDirectionAdjacentIsland;
import it.polimi.ingsw.model.colors.ColorPawns;
import it.polimi.ingsw.model.game.GameModel;
import it.polimi.ingsw.model.islands.IslandModel;
import it.polimi.ingsw.network.message.Message;
import it.polimi.ingsw.view.VirtualView;

import java.util.ArrayList;
import java.util.List;

//7
public class CheckIfJoinableState implements GameState {

    private final List<IslandModel> islandModels;
    private final GameModel gameModel;
    private final IslandModel islandModelToCheck;

    @Override
    public GameModel getGameModel() {
        return this.gameModel;
    }

    @Override
    public void onMessageReceived(Message receivedMessage) {
        VirtualView virtualView = gameModel.getVirtualViewMap().get(receivedMessage.getNickname());
    }


    public CheckIfJoinableState(GameModel gameModel, IslandModel islandModel) {
        this.gameModel = gameModel;
        this.islandModelToCheck = islandModel;
        this.islandModels = this.getGameModel().getIslandsModel();
    }

    //prende due isole da unire, e l'indice della prima isola da unire in questione, restituisce la lista delle isole aggiornata con quella unita
    public List<IslandModel> joinIsland(IslandModel islandToJoin, int indexFirstIsland){
        int sizeStudentsJoined = this.islandModelToCheck.getStudents().size() + islandToJoin.getStudents().size(); //calcolo studenti di entrambe le isole
        List<ColorPawns> joinedStudents = new ArrayList<>(sizeStudentsJoined); //inizializzo array degli studenti
        //aggiungo gli studenti delle isole da unire alla lista degli studenti uniti
        joinedStudents.addAll(this.islandModelToCheck.getStudents());
        joinedStudents.addAll(islandToJoin.getStudents());


        IslandModel joined = new IslandModel(this.islandModelToCheck.getMotherNature() || islandToJoin.getMotherNature(), joinedStudents);

        //posiziono gli studenti sulla nuova isola
        //islandController.addStudent(joinedStudents);
        joined.setJoined(); //setta a true il valore isJoined
        int indexToRemove = this.islandModels.indexOf(islandToJoin);
        return compressIsland(this.getGameModel().getIslandsModel(), joined, indexFirstIsland, indexToRemove);
    }

    private List<IslandModel> compressIsland(List<IslandModel> islandModels, IslandModel joined, int indexFirstIsland, int indexToRemove){
        islandModels.set(indexFirstIsland, joined);
        islandModels.remove(indexToRemove);
        return islandModels;
    }
}

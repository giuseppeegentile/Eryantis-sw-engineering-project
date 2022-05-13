package it.polimi.ingsw.controller.game;

import it.polimi.ingsw.model.enums.PhaseGame;
import it.polimi.ingsw.model.islands.ColorDirectionAdjacentIsland;
import it.polimi.ingsw.model.colors.ColorPawns;
import it.polimi.ingsw.model.game.GameModel;
import it.polimi.ingsw.model.islands.IslandModel;
import it.polimi.ingsw.network.message.Message;
import it.polimi.ingsw.view.VirtualView;

import java.util.ArrayList;
import java.util.List;

//7
public class CheckIfJoinableState extends GameController implements GameState {

    private static final long serialVersionUID = -4065719069108763021L;
    private final List<IslandModel> islandModels;
    private final GameModel gameModel;
    private final IslandModel islandModelToCheck;

    /**
     * Constructor for CheckIfJoinableState: initializes the island to check and the list of all the islands
     * @param islandModel The island that has to be checked
     */
    public CheckIfJoinableState(IslandModel islandModel) {
        this.gameModel = GameModel.getInstance();
        this.islandModelToCheck = islandModel;
        this.islandModels = GameModel.getInstance().getIslandsModel();
        this.gameModel.setGameState(PhaseGame.CHECK_JOIN);
    }




    @Override
    public PhaseGame getState() {
        return  this.gameModel.getGameState();
    }


    //prende due isole da unire, e l'indice della prima isola da unire in questione, restituisce la lista delle isole aggiornata con quella unita
    /**
     * Joins the islands with the same influence according to the motherNature's position
     * @param islandToJoin Island to join with
     * @param indexFirstIsland The index of the first island in the islandModels list
     * @return The list of islandModels with the two joined island
     */
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
        return compressIsland(gameModel.getIslandsModel(), joined, indexFirstIsland, indexToRemove);
    }

    /**
     * Utility method. Sets in the position of the first joined island the joining result and remove the other one
     * @param islandModels The list of island in the current game
     * @param joined The joining result of the two islands
     * @param indexFirstIsland Index of the first joined island
     * @return The list of islandModels with the two joined island
     */
    private List<IslandModel> compressIsland(List<IslandModel> islandModels, IslandModel joined, int indexFirstIsland, int indexToRemove){
        islandModels.set(indexFirstIsland, joined);
        islandModels.remove(indexToRemove);
        return islandModels;
    }
}

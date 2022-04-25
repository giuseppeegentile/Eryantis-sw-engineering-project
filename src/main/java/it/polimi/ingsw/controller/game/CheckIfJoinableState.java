package it.polimi.ingsw.controller.game;

import it.polimi.ingsw.model.islands.ColorDirectionAdjacentIsland;
import it.polimi.ingsw.model.ColorPawns;
import it.polimi.ingsw.model.game.GameModel;
import it.polimi.ingsw.model.islands.IslandModel;

import java.util.ArrayList;
import java.util.List;

//7
public class CheckIfJoinableState implements GameState {

    private List<IslandModel> islandModels;
    private GameModel gameModel;
    private IslandModel islandModelToCheck;

    /**
     *
     * @return The current gameModel
     */
    @Override
    public GameModel getGameModel() {
        return this.gameModel;
    }

    /**
     * Constructor for CheckIfJoinableState: initializes the island to check and the list of all the islands
     * @param gameModel The current gameModel
     * @param islandModel The island that has to be checked
     */
    public CheckIfJoinableState(GameModel gameModel, IslandModel islandModel) {
        this.gameModel = gameModel;
        this.islandModelToCheck = islandModel;
        this.islandModels = this.getGameModel().getIslandsModel();
    }

    /**
     * Explores the adjacent islands and checks if there are others with the same influence
     * @return The direction where we can find an island with the same influence
     */
    //convenzione: senso orario
    public ColorDirectionAdjacentIsland getAdjacentSameColor(){
        //indice dell'isola nell'array delle isole
        int right, left;
        int indexIslandToChekAdjacent = this.islandModels.indexOf(this.islandModelToCheck);
        if (indexIslandToChekAdjacent != 11)
            right = indexIslandToChekAdjacent + 1;
        else
            right = 0;
        if (indexIslandToChekAdjacent != 0)
            left = indexIslandToChekAdjacent - 1;
        else
            left = 11;
        if(islandModelToCheck.getTowerColor() == islandModels.get(left).getTowerColor() && islandModelToCheck.getTowerColor() == islandModels.get(right).getTowerColor())
            return ColorDirectionAdjacentIsland.BOTH;
        else if(islandModelToCheck.getTowerColor() == islandModels.get(right).getTowerColor())
            return ColorDirectionAdjacentIsland.RIGHT;
        else if(islandModelToCheck.getTowerColor() == islandModels.get(left).getTowerColor())
            return ColorDirectionAdjacentIsland.LEFT;
        else
            return ColorDirectionAdjacentIsland.NONE;
    }

    /**
     * Joins the islands with the same influence according to the motherNature's position
     * @param islandToJoin Island to join with
     * @param indexFirstIsland The index of the first island in the islandModels list
     * @return The list of islandModels with the two joined island
     */
    //MANCA IL TEST
    //prende due isole da unire, e l'indice della prima isola da unire in questione, restituisce la lista delle isole aggiornata con quella unita
    public List<IslandModel> joinIsland(IslandModel islandToJoin, int indexFirstIsland){
        int sizeStudentsJoined = this.islandModelToCheck.getStudents().size() + islandToJoin.getStudents().size(); //calcolo studenti di entrambe le isole
        List<ColorPawns> joinedStudents = new ArrayList<>(sizeStudentsJoined); //inizializzo array degli studenti
        //aggiungo gli studenti delle isole da unire alla lista degli studenti uniti
        joinedStudents.addAll(this.islandModelToCheck.getStudents());
        joinedStudents.addAll(islandToJoin.getStudents());
        IslandModel joined = new IslandModel(this.islandModelToCheck.getMotherNature() || islandToJoin.getMotherNature(), joinedStudents);
        IslandController islandController = new IslandController(joined);

        //posiziono gli studenti sulla nuova isola
        islandController.addStudent(joinedStudents);
        joined.setJoined(); //setta a true il valore isJoined
        return compressIsland(this.getGameModel().getIslandsModel(), joined, indexFirstIsland);
    }

    /**
     * Sets in the position of the first joined island the joining result and remove
     * @param islandModels The list of island in the current game
     * @param joined The joining result of the two islands
     * @param indexFirstIsland Index of the first joined island
     * @return The list of islandModels with the two joined island
     */
    private List<IslandModel> compressIsland(List<IslandModel> islandModels, IslandModel joined, int indexFirstIsland){
        islandModels.set(indexFirstIsland, joined);
        islandModels.remove(indexFirstIsland + 1);
        return islandModels;
    }
}

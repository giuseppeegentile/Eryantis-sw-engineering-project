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

    @Override
    public GameModel getGameModel() {
        return this.gameModel;
    }

    public CheckIfJoinableState(GameModel gameModel, IslandModel islandModel) {
        this.gameModel = gameModel;
        this.islandModelToCheck = islandModel;
        this.islandModels = this.getGameModel().getIslandsModel();
    }

    //convenzione: senso orario
    public ColorDirectionAdjacentIsland getAdjacentSameColor(){
        //indice dell'isola nell'array delle isole
        int indexIslandToChekAdjacent = this.islandModels.indexOf(this.islandModelToCheck);

        int right = indexIslandToChekAdjacent + 1;
        int left = indexIslandToChekAdjacent - 1;

        if(indexIslandToChekAdjacent == 0 && islandModelToCheck.getTowerColor() == islandModels.get(11).getTowerColor()){
            if(islandModelToCheck.getTowerColor() == islandModels.get(right).getTowerColor()){
                return ColorDirectionAdjacentIsland.BOTH;
            }
            return ColorDirectionAdjacentIsland.LEFT;
        }
        if(indexIslandToChekAdjacent == 11 && islandModelToCheck.getTowerColor() == islandModels.get(0).getTowerColor()){
            if(islandModelToCheck.getTowerColor() == islandModels.get(left).getTowerColor()){
                return ColorDirectionAdjacentIsland.BOTH;
            }
            return ColorDirectionAdjacentIsland.RIGHT;
        }
        if(islandModelToCheck.getTowerColor() == islandModels.get(right).getTowerColor()){
            return ColorDirectionAdjacentIsland.RIGHT;
        }
        if(islandModelToCheck.getTowerColor() == islandModels.get(left).getTowerColor()){
            return ColorDirectionAdjacentIsland.LEFT;
        }
        return ColorDirectionAdjacentIsland.NONE;
    }


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

    private List<IslandModel> compressIsland(List<IslandModel> islandModels, IslandModel joined, int indexFirstIsland){
        islandModels.set(indexFirstIsland, joined);
        islandModels.remove(indexFirstIsland + 1);
        return islandModels;
    }



}

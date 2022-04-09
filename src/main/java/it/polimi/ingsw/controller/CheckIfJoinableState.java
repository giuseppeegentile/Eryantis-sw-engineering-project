package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.ColorDirectionAdjacentIsland;
import it.polimi.ingsw.model.ColorPawns;
import it.polimi.ingsw.model.GameModel;
import it.polimi.ingsw.model.IslandModel;

import java.util.ArrayList;
import java.util.List;

//7
public class CheckIfJoinableState extends GameControllerState{

    List<IslandModel> islandModels;

    public CheckIfJoinableState(GameModel gameModel) {
        super(gameModel);
        islandModels = this.getGameModel().getIslandsModel();
    }

    public ColorDirectionAdjacentIsland getAdjacentSameColor(int indexIslandToChekAdjacent){
        if(indexIslandToChekAdjacent == 0 && islandModels.get(indexIslandToChekAdjacent).getTowerColor() == islandModels.get(11).getTowerColor()){
            if(islandModels.get(indexIslandToChekAdjacent).getTowerColor() == islandModels.get(indexIslandToChekAdjacent + 1).getTowerColor()){
                return ColorDirectionAdjacentIsland.BOTH;
            }
            return ColorDirectionAdjacentIsland.LEFT;
        }
        if(indexIslandToChekAdjacent == 11 && islandModels.get(indexIslandToChekAdjacent).getTowerColor() == islandModels.get(0).getTowerColor()){
            if(islandModels.get(indexIslandToChekAdjacent).getTowerColor() == islandModels.get(indexIslandToChekAdjacent - 1).getTowerColor()){
                return ColorDirectionAdjacentIsland.BOTH;
            }
            return ColorDirectionAdjacentIsland.RIGHT;
        }
        if(islandModels.get(indexIslandToChekAdjacent).getTowerColor() == islandModels.get(indexIslandToChekAdjacent + 1).getTowerColor()){
            return ColorDirectionAdjacentIsland.RIGHT;
        }
        if(islandModels.get(indexIslandToChekAdjacent).getTowerColor() == islandModels.get(indexIslandToChekAdjacent - 1).getTowerColor()){
            return ColorDirectionAdjacentIsland.LEFT;
        }
        return ColorDirectionAdjacentIsland.NONE;
    }


    //prende due isole da unire, e l'indice della prima isola da unire in questione, restituisce la lista delle isole aggiornata con quella unita
    public List<IslandModel> joinIsland(IslandModel firstIslandModel, IslandModel secondIslandModel, int indexFirstIsland){
        int sizeStudentsJoined = firstIslandModel.getStudents().size() + secondIslandModel.getStudents().size(); //calcolo studenti di entrambe le isole
        List<ColorPawns> joinedStudents = new ArrayList<>(sizeStudentsJoined); //inizializzo array degli studenti

        //per lo studente iniziale del costruttore ne prendo uno a caso (il primo della prima isola), che non dovò riaggiungere ovviamente. Quindi creo l'isola joinata
        IslandModel joined = new IslandModel(firstIslandModel.getMotherNature() || secondIslandModel.getMotherNature(), firstIslandModel.getStudents().get(0));

        //aggiungo gli studenti delle isole da unire alla lista degli studenti uniti
        joinedStudents.addAll(firstIslandModel.getStudents());
        joinedStudents.addAll(secondIslandModel.getStudents());
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

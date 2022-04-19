package it.polimi.ingsw.model.islands;

import it.polimi.ingsw.model.ColorPawns;
import it.polimi.ingsw.model.ColorTower;
import it.polimi.ingsw.model.game.GameModel;
import it.polimi.ingsw.model.player.PlayerModel;

import java.util.*;

import static it.polimi.ingsw.model.ColorPawns.*;
import static it.polimi.ingsw.model.islands.ColorDirectionAdjacentIsland.NONE;

public class IslandModel {
    private final boolean motherNature;
    private List<ColorPawns> students;
    private ColorTower colorTower;
    private boolean isJoined;
    private PlayerModel influence;


    public IslandModel(boolean motherNature){
        this.students = new ArrayList<>();
        this.motherNature = motherNature;
        this.isJoined = false;
        this.colorTower = ColorTower.NULL;
    }

    //quando creo l'isola so già dove va madre natura (ricorda vincolo solo su un'isola, questo vincolo non va in questa classe ma dove creo l'array di isole, cioè Game)
    //e so anche il colore dello studente che ci piazzo su
    public IslandModel(boolean motherNature, ColorPawns initialStudent){
        this.students = new ArrayList<>();
        this.students.add(initialStudent);
        this.motherNature = motherNature;
        this.isJoined = false;
        this.colorTower = ColorTower.NULL;
    }
    //usato principalmente nell'unione delle isole, quando devo traslocare tutti gli studenti di un'isola in un'altra
    public IslandModel(boolean motherNature, List<ColorPawns> students){
        this.students = new ArrayList<>();
        this.students.addAll(students);
        this.motherNature = motherNature;
        this.isJoined = false;
        this.colorTower = ColorTower.NULL;
    }

    public void addStudent(ColorPawns student){
        if(this.students == null) this.students = new ArrayList<>();
        this.students.add(student);
    }

    public void setJoined(){
        this.isJoined = true;
    }

    public boolean getMotherNature(){
        return motherNature;
    }

    public List<ColorPawns> getStudents(){
        return students;
    }

    public ColorTower getTowerColor(){
        return colorTower;
    }

    public void setTowerColor(ColorTower colorTower){
        this.colorTower = colorTower;
    }

    public PlayerModel getInfluence(GameModel gameModel){

        ColorTower towerOnIsland = this.getTowerColor();
        PlayerModel playerToAssignAdditionalInfluence = null;
        if(towerOnIsland != ColorTower.NULL){
            playerToAssignAdditionalInfluence = gameModel.getPlayerByColorTower(towerOnIsland);
        }

        HashMap<ColorPawns, Integer> studentInIsland = new HashMap<>(Map.of(
                GREEN, 0,
                RED, 0,
                YELLOW, 0,
                PINK, 0,
                BLUE, 0
        ));


        List<ColorPawns> allColors = new ArrayList<>(Arrays.asList(ColorPawns.BLUE, ColorPawns.GREEN, ColorPawns.PINK, ColorPawns.RED, ColorPawns.YELLOW));
        PlayerModel playerWithTower = playerToAssignAdditionalInfluence;
        for(ColorPawns s : allColors){
            if(playerWithTower != null && playerWithTower.hasProf(s)) { //aggiungo 1 per ogni prof che ha se il giocatore ha già una torre
                studentInIsland.put(s,
                        Collections.frequency(this.getStudents(), s)+1); //for each color of student count the frequency plus the tower on the island and put in the map
            }else {
                studentInIsland.put(s,
                        Collections.frequency(this.getStudents(), s)); //for each color of student count the frequency on the island and put in the map
            }
        }


        //studentInIsland:
        //  key     value
        //  GREEN:  # of green students in the island
        //  RED:    # of red students in the island
        //  YELLOW: # of yellow students in the island
        //  PINK:   # of pink students in the island
        //  BLUE:   # of blue students in the island

        List<PlayerModel> playersWithAtLeastAProf = new ArrayList<>();

        //populate the list with only players with at least a prof
        gameModel.getPlayersModel().forEach(playerModel -> {
            if(playerModel.getNumProfs() != 0) playersWithAtLeastAProf.add(playerModel);
        });


        while (studentInIsland.size() != 0){
            List<ColorPawns> colorsWithSameMax = new ArrayList<>();
            //per ogni riga della mappa, guardo se il valore è il massimo, se sì, lo aggiungo alla lista colorsWithSameMax
            studentInIsland.forEach((student, frequency)->{
                if(frequency.equals(Collections.max(studentInIsland.entrySet(), Comparator.comparingInt(Map.Entry::getValue)).getValue())){
                    colorsWithSameMax.add(student);
                }
            });

            if(colorsWithSameMax.size() > 1) { //in questo caso ho almeno due studenti con entrambi stesso numero massimo sull'isola
                int i = 0;                     //devo controllare se due giocatori hanno dei prof dello stesso colore dei due massimi
                for(ColorPawns s: colorsWithSameMax) {
                    for(PlayerModel p :playersWithAtLeastAProf) {
                        if (p.getProfs().contains(s)) {
                            i++;
                            if (i != 1) return new PlayerModel(); //ho due giocatori con entrambi stessa influenza
                        }
                    };
                }

            }

            //get the student with max presence in island
            ColorPawns maxStudentOnIsland = Collections.max(studentInIsland.entrySet(), Comparator.comparingInt(Map.Entry::getValue)).getKey();


            for(PlayerModel playerModel: playersWithAtLeastAProf){
                if (playerModel.hasProf(maxStudentOnIsland)) { //se un giocatore ha il prof dello stesso colore del massimo numero degli studenti sull'isola
                    return playerModel;
                }
            }
            studentInIsland.remove(maxStudentOnIsland); //remove max element, because no player has prof of that color
        }
        return new PlayerModel(); //default value, meaning no player has influence
    }
/*
    public PlayerModel getInfluence(GameModel gameModel){

        ColorTower towerOnIsland = getTowerColor();
        PlayerModel playerToAssignAdditionalInfluence = null;
        if(towerOnIsland != ColorTower.NULL){
            playerToAssignAdditionalInfluence = gameModel.getPlayerByColorTower(towerOnIsland);
        }


        Map<ColorPawns, Integer> studentInIslandNumber = new HashMap<>();
        List<ColorPawns> allColors = new ArrayList<>(Arrays.asList(ColorPawns.BLUE, ColorPawns.GREEN, ColorPawns.PINK, ColorPawns.RED, ColorPawns.YELLOW));
        PlayerModel finalPlayerToAssignAdditionalInfluence = playerToAssignAdditionalInfluence;
        allColors.forEach(s ->{
            if(finalPlayerToAssignAdditionalInfluence != null && finalPlayerToAssignAdditionalInfluence.hasProf(s)) { //aggiungo 1 per ogni prof che ha se il giocatore ha già una torre
                studentInIslandNumber.put(s,
                        Collections.frequency(this.getStudents(), s)+1); //for each color of student count the frequency on the island and put in the map
            }else {
                studentInIslandNumber.put(s,
                        Collections.frequency(this.getStudents(), s)); //for each color of student count the frequency on the island and put in the map
            }
        });


        //studentInIslandNumber:
          //  key     value
          //  BLUE:   # of blue students in the island
          //  GREEN:  # of green students in the island
          //  PINK:   # of pink students in the island
          //  RED:    # of red students in the island
          //  YELLOW: # of yellow students in the island


        List<PlayerModel> playersWithAtLeastAProf = new ArrayList<>();

        //populate the list with only players with at least a prof
        gameModel.getPlayersModel().forEach(playerModel -> {
            if(playerModel.getNumProfs() != 0) playersWithAtLeastAProf.add(playerModel);
        });


        while (studentInIslandNumber.size() == 0){
            List<ColorPawns> colorsWithSameMax = new ArrayList<>();
            //per ogni riga della mappa, guardo se il valore è il massimo, se sì, lo aggiungo alla lista colorsWithSameMax
            studentInIslandNumber.forEach((student, frequency)->{
                if(frequency.equals(Collections.max(studentInIslandNumber.entrySet(), Comparator.comparingInt(Map.Entry::getValue)).getValue())){
                    colorsWithSameMax.add(student);
                }
            });

            if(colorsWithSameMax.size() > 1) { //in questo caso ho almeno due studenti con entrambi stesso numero massimo sull'isola
                int i = 0;                     //devo controllare se due giocatori hanno dei prof dello stesso colore dei due massimi
                for(ColorPawns s: colorsWithSameMax) {
                    for(PlayerModel p :playersWithAtLeastAProf) {
                        if (p.getProfs().contains(s)) {
                            i++;
                            if (i != 1) return new PlayerModel(); //ho due giocatori con entrambi stessa influenza
                        }
                    };
                }

            }

            //get the student with max presence in island
            ColorPawns maxStudentOnIsland = Collections.max(studentInIslandNumber.entrySet(), Comparator.comparingInt(Map.Entry::getValue)).getKey();


            for(PlayerModel playerModel: playersWithAtLeastAProf){
                if (playerModel.hasProf(maxStudentOnIsland)) { //se un giocatore ha il prof dello stesso colore del massimo numero degli studenti sull'isola
                    return playerModel;
                }
            }
            studentInIslandNumber.remove(maxStudentOnIsland); //remove max element, because no player has prof of that color
        }
        return new PlayerModel(); //default value, meaning no player has influence
    }

 */

}

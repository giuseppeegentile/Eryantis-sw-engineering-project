package it.polimi.ingsw.model.islands;

import it.polimi.ingsw.model.colors.ColorPawns;
import it.polimi.ingsw.model.colors.ColorTower;
import it.polimi.ingsw.model.game.GameModel;
import it.polimi.ingsw.model.player.PlayerModel;
import java.io.Serializable;
import java.util.*;

import static it.polimi.ingsw.model.colors.ColorPawns.*;

public class IslandModel implements Serializable {
    private static final long serialVersionUID = 2173471499023058671L;
    private final boolean motherNature;
    private List<ColorPawns> students;
    private ColorTower colorTower;
    private boolean isJoined;
    private PlayerModel influence;

    /**
     * Constructor for island in the model: initializes isJoined tho the false value and motherNature
     * @param motherNature The value of motherNature. It's true only if the island is chosen as the starting position for mother nature
     */
    public IslandModel(boolean motherNature){
        this.students = new ArrayList<>();
        this.motherNature = motherNature;
        this.isJoined = false;
        this.colorTower = ColorTower.NULL;
    }

    //quando creo l'isola so già dove va madre natura (ricorda vincolo solo su un'isola, questo vincolo non va in questa classe ma dove creo l'array di isole, cioè Game)
    //e so anche il colore dello studente che ci piazzo su
    /**
     * Constructor for island in the model: initializes isJoined tho the false value and motherNature and set the initial student on the island
     * @param motherNature The value of motherNature. It's true only if the island is chosen as the starting position for mother nature
     * @param initialStudent The color of the initial student placed on the island
     */
    public IslandModel(boolean motherNature, ColorPawns initialStudent){
        this.students = new ArrayList<>();
        this.students.add(initialStudent);
        this.motherNature = motherNature;
        this.isJoined = false;
        this.colorTower = ColorTower.NULL;
    }
    //usato principalmente nell'unione delle isole, quando devo traslocare tutti gli studenti di un'isola in un'altra
    /**
     * Constructor for island in the model: initializes isJoined tho the false value and motherNature and set the list of the students on the island. It's used when joining islands
     * @param motherNature
     * @param students
     */
    public IslandModel(boolean motherNature, List<ColorPawns> students){
        this.students = new ArrayList<>();
        this.students.addAll(students);
        this.motherNature = motherNature;
        this.isJoined = false;
        this.colorTower = ColorTower.NULL;
    }

    /**
     * Adds student chosen by the player to the island
     * @param student Student moved by the player from a cloud or his entrance
     */
    public void addStudent(ColorPawns student){
        if(this.students == null) this.students = new ArrayList<>();
        this.students.add(student);
    }
    /**
     * Sets isJoined to true value
     */
    public void setJoined(){
        this.isJoined = true;
    }

    /**
     *
     * @return The value of motherNature. It depends on whether mother nature is on the island or not
     */
    public boolean getMotherNature(){
        return motherNature;
    }

    /**
     *
     * @return The list of students on the island
     */
    public List<ColorPawns> getStudents(){
        return students;
    }

    /**
     *
     * @return The tower color of the player who conquered the island
     */
    public ColorTower getTowerColor(){
        return colorTower;
    }

    /**
     *
     * @param colorTower The tower color of the player who conquered the island
     */
    public void setTowerColor(ColorTower colorTower){
        this.colorTower = colorTower;
    }

    /**
     * Calculates the influence of the island and returns the player who conquered the island
     * @param gameModel The istance of the current match
     * @return The player who has the major influence on the island. It depends on how many students of the same color of the prof owned by player are on the island
     */
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

    /**********************************************************/
    //DA TESTARE
    public void addStudent(List<ColorPawns> studentsToAdd){
        this.students.addAll(studentsToAdd);
    }

    public boolean hasTower(){
        return ColorTower.NULL != colorTower;
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

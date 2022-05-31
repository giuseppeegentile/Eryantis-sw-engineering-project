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
    private boolean motherNature;
    private List<ColorPawns> students;
    private ColorTower colorTower;
    private boolean isJoined;
    private boolean prohibition = false;

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
     */

    public ColorPawns getStudentWithInfluence(){
        Map<ColorPawns, Integer> frequency = new HashMap<>();
        frequency.put(ColorPawns.RED, Collections.frequency(students, RED));
        frequency.put(ColorPawns.BLUE, Collections.frequency(students, BLUE));
        frequency.put(ColorPawns.GREEN, Collections.frequency(students, GREEN));
        frequency.put(ColorPawns.PINK, Collections.frequency(students, PINK));
        frequency.put(ColorPawns.YELLOW, Collections.frequency(students, YELLOW));
        return Collections.max(frequency.entrySet(), Map.Entry.comparingByValue()).getKey();
    }

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

    public void setMotherNature(boolean newValue){
        this.motherNature = newValue;
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

    /**********************************************************/
    //DA TESTARE
    public void addStudent(List<ColorPawns> studentsToAdd){
        this.students.addAll(studentsToAdd);
    }

    public boolean hasTower(){
        return ColorTower.NULL != colorTower;
    }

    public boolean hasProhibition() {
        return prohibition;
    }

    public void setHasProhibition(boolean prohibition) {
        this.prohibition = prohibition;
    }


    public PlayerModel getInfluence(PlayerModel playerWithEffectAdditionalInfluence,ColorPawns ignoreColorEffect, boolean considerTower){
        GameModel gameModel = GameModel.getInstance();
        List<ColorPawns> profsOwned = new ArrayList<>();
        List<ColorPawns> copyStudents = new ArrayList<>(this.getStudents());
        if(copyStudents.size()!=0) {
            if (this.getTowerColor() != ColorTower.NULL && considerTower) { //add an occourency for every prof the player with tower owns
                copyStudents.addAll(gameModel.getPlayerByColorTower(this.getTowerColor()).getProfs());
                if (playerWithEffectAdditionalInfluence != null && playerWithEffectAdditionalInfluence.getNickname().equals(gameModel.getPlayerByColorTower(this.getTowerColor()).getNickname())) {
                    copyStudents.addAll(gameModel.getPlayerByColorTower(this.getTowerColor()).getProfs());
                }
            }

            if (playerWithEffectAdditionalInfluence != null) {
                copyStudents.addAll(playerWithEffectAdditionalInfluence.getProfs());
            }
            if (ignoreColorEffect != null) {
                copyStudents.removeIf(c -> c.equals(ignoreColorEffect));
            }

            for (PlayerModel p : gameModel.getPlayersModel()) {
                profsOwned.addAll(p.getProfs());
            }

            List<ColorPawns> mostFrequent = getMode(copyStudents);
            if (mostFrequent.size() == 2) {//there are 2 players with same students on island
                return new PlayerModel();
            } else {
                if (profsOwned.contains(mostFrequent.get(0))) {
                    for (PlayerModel p : gameModel.getPlayersModel()) {
                        if (p.hasProf(mostFrequent.get(0))) {
                            return p;
                        }
                    }
                }
            }
        }
        return new PlayerModel();
    }

    /**
     * Calculate the most frequent values in a list
     * @param arr List to retrieve the mode from.
     * @return A list of the most frequent values from another list
     */
    private List<ColorPawns> getMode(List<ColorPawns> arr){
        int n = arr.size();
        // Sort the list
        Collections.sort(arr);

        // find the max frequency using linear traversal
        int max_count = 1;
        ColorPawns res = arr.get(0);
        int curr_count = 1;

        for (int i = 1; i < n; i++) {
            if (arr.get(i) == arr.get(i - 1))
                curr_count++;
            else
                curr_count = 1;

            if (curr_count > max_count) {
                max_count = curr_count;
                res = arr.get(i - 1);
            }
        }
        int maxValue = -1;
        ColorPawns oldColor = NULL;
        for(ColorPawns c : List.of(RED, BLUE, YELLOW, GREEN, PINK)){
            if(maxValue < Collections.frequency(arr, c)){
                maxValue = Collections.frequency(arr, c);
                oldColor = c;
            }else if (maxValue == Collections.frequency(arr, c) && c.equals(res)){
                return List.of(oldColor, c);
            }

        }

        return List.of(res);

    }
}

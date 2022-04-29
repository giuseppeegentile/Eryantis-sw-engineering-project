package it.polimi.ingsw.controller.player;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import it.polimi.ingsw.model.colors.ColorPawns;
import it.polimi.ingsw.model.enums.GameMode;
import it.polimi.ingsw.model.game.GameModel;
import it.polimi.ingsw.model.player.PlayerModel;
import it.polimi.ingsw.model.enums.StatePlayer;
import it.polimi.ingsw.network.message.Message;

import java.util.List;
import java.util.Objects;

//4.0
public class StudentToHallState implements PlayerState {
    private final PlayerModel playerModel;
    /**
     * Constructor for StudentToHallState: sets the player state to MOVE_STUDENT
     * @param playerModel The player to be initialized
     */
    public StudentToHallState(PlayerModel playerModel){
        this.playerModel = playerModel;
        this.playerModel.setState(StatePlayer.MOVE_STUDENT_TO_HALL);
    }

    public StudentToHallState(Message receivedMessage){
        JsonObject obj = (new Gson()).fromJson(receivedMessage.toString(), JsonObject.class);
        JsonObject playersJson = obj.getAsJsonObject("player");
        this.playerModel = new Gson().fromJson(playersJson, PlayerModel.class);
        JsonObject studentsJson = obj.getAsJsonObject("students");
        moveStudentToHall(new Gson().fromJson(studentsJson, List.class));
        this.playerModel.setState(StatePlayer.MOVE_STUDENT_TO_HALL);
    }

    @Override
    public StatePlayer getState() {
        return this.playerModel.getState();
    }

    //sposta il singolo studente nella hall, da rimuovere
    /**
     * Moves a student from the entrance to the hall of the player
     * @param student The student that the player moves
     * @param gameMode The current game mode
     */
    public void moveStudentToHall(ColorPawns student, GameMode gameMode) {
        //conta le occorrenze per ogni studente di un colore
        if(this.playerModel.getStudentInHall().get(student) + 1 % 3 == 0 && GameModel.getInstance().getGameMode() == GameMode.ESPERTO) //se lo studente che sto per aggiungere è 3° 6° o 9° prende una moneta
            this.addCoins();

        this.playerModel.getStudentInHall().put(student, this.playerModel.getStudentInHall().get(student)+1);
        this.playerModel.removeStudentFromEntrance(student);
    }
    //sposta gli studenti nella hall, da testare al posto di quello sopra
    /**
     * Moves a student from the entrance to the hall of the player
     */
    private void moveStudentToHall(List<ColorPawns> students) {
        for(ColorPawns student: students) {
            //conta le occorrenze per ogni studente di un colore
            if (this.playerModel.getStudentInHall().get(student) + 1 % 3 == 0 && GameModel.getInstance().getGameMode() == GameMode.ESPERTO) //se lo studente che sto per aggiungere è 3° 6° o 9° prende una moneta
                this.addCoins();

            this.playerModel.getStudentInHall().put(student, this.playerModel.getStudentInHall().get(student) + 1);

            if(canProfBeAssignedToPlayer(student))
                assignProfToPlayer(student);


            this.playerModel.removeStudentFromEntrance(student);
        }
    }

    /** da rimuovere, sostituire con quello di sotto
     * Assign a prof to the player. If another player has that prof, it removes the prof from him
     * @param playersModels
     * @param prof
     */
    public void assignProfToPlayer(List<PlayerModel> playersModels, ColorPawns prof){
        boolean alreadyHave = false;
        for (PlayerModel p : playersModels) {
            //se un altro giocatore ha già il prof
            if(p.getProfs() != null) {
                if (!Objects.equals(this.playerModel.getNickname(), p.getNickname()) && p.getProfs().contains(prof)) {
                    //lo tolgo a chi lo aveva prima
                    p.removeProf(prof);
                    playerModel.addProf(prof);
                    alreadyHave = true;
                }
            }
        }
        if(!alreadyHave)
            playerModel.addProf(prof);
    }

    /** da TESTARE
     * Assign a prof to the player. If another player has that prof, it removes the prof from him
     * @param prof
     */
    private void assignProfToPlayer(ColorPawns prof){
        List<PlayerModel> playersModels = GameModel.getInstance().getPlayersModel();
        boolean alreadyHave = false;
        for (PlayerModel p : playersModels) {
            //se un altro giocatore ha già il prof
            if(p.getProfs() != null) {
                if (!Objects.equals(this.playerModel.getNickname(), p.getNickname()) && p.getProfs().contains(prof)) {
                    //lo tolgo a chi lo aveva prima
                    p.removeProf(prof);
                    playerModel.addProf(prof);
                    alreadyHave = true;
                }
            }
        }
        if(!alreadyHave)
            playerModel.addProf(prof);
    }

    private boolean canProfBeAssignedToPlayer(ColorPawns prof){
        for(PlayerModel player: GameModel.getInstance().getPlayersModel()){
            if(!playerModel.getNickname().equals(player.getNickname())){
                if(player.getStudentInHall().get(prof) > playerModel.getStudentInHall().get(prof)) return false; //se uno studente ha più pedine del colore del prof, non può essere assegnato
            }
        }
        return true;
    }


    @Override
    public void addCoins() {

    }

    @Override
    public PlayerModel getPlayerModel() {
        return this.playerModel;
    }

}

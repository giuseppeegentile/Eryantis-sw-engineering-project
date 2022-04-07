package it.polimi.ingsw.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class PlayerModel {
    private String nickname;
    private List<ColorPawns> profs;
    private int towerNumber; //da settare con il controller, strategy
    private ColorTower colorTower;
    private List<AssistantCardModel> deckAssistantCardModel;
    private Map<ColorPawns, Integer> studentInHall;
    private List<ColorPawns> studentAvailable;
    private StatePlayer state;
    private int coins;

    PlayerModel(String nickname, ColorTower colorTower, int coins){
        this.nickname = nickname;
        this.colorTower = colorTower;
        this.coins = coins;
    }

    protected String getNickname(){
        return this.nickname;
    }

    public void setNickname(String nickname){
        this.nickname = nickname;
    }

    private StatePlayer getState(){
        return this.state;
    }

    public void setProfs(ColorPawns profToAdd){
        if(profs == null)
            profs = new ArrayList<>();
        profs.add(profToAdd);
    }

    private static void playCard(){ }

    private int getCoins(){
        return this.coins;
    }

    public void removeCoins(int coinsUsed){
        this.coins -=coinsUsed;
    }

    public void addCoins(){
        this.coins +=1 ;
    }

    public void setCoins(){
        this.coins = 1;
    }


    public void setStudentHall(Map<ColorPawns, Integer> studentInHall){
        this.studentInHall = studentInHall;
    }

    public void setColorTower(ColorTower colorTower, int towerNumber){
        this.colorTower = colorTower;
        this.towerNumber = towerNumber;
    }


}

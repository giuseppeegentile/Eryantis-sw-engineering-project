package it.polimi.ingsw.controller.game;

import it.polimi.ingsw.controller.player.PlayerState;
import it.polimi.ingsw.controller.player.PlayerInitialState;
import it.polimi.ingsw.model.ColorPawns;
import it.polimi.ingsw.model.ColorTower;
import it.polimi.ingsw.model.game.CloudModel;
import it.polimi.ingsw.model.game.GameMode;
import it.polimi.ingsw.model.game.GameModel;
import it.polimi.ingsw.model.islands.IslandModel;
import it.polimi.ingsw.model.player.PlayerModel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class StartGameState implements GameState {
    private GameModel gameModel;

    @Override
    public GameModel getGameModel() {
        return this.gameModel;
    }

    public StartGameState(GameModel gameModel){
        this.gameModel = gameModel;
    }

    private void assignBag(){
        int bagSize = 120;
        List<ColorPawns> bag = new ArrayList<>(bagSize);
        int equalNumber = bagSize/5;

        bag = fillListWithColors( bag,  bagSize, equalNumber);
        this.gameModel.setBag(bag);
    }

    //utility
    //prende una generica lista di studenti e la riempie casualmente
    //usata per riempire la bag e le isole iniziali
    //size: dimensione da riempire (bag: 120, isole: 10)
    //equalNumber: quantità uguali per ogni colore (bag: 24(=120/5)  isole: 2)
    private List<ColorPawns> fillListWithColors(List<ColorPawns> list, int size, int equalNumber){
        for(int i = 0; i < size; i++){
            if(i < equalNumber)
                list.set(i, ColorPawns.GREEN);
            if(i < 2*equalNumber  && i > equalNumber )
                list.set(i, ColorPawns.RED);
            if(i < 3*equalNumber && i > 2*equalNumber)
                list.set(i, ColorPawns.YELLOW);
            if(i < 4*equalNumber  && i > 3*equalNumber)
                list.set(i, ColorPawns.PINK);
            if(i > 4*equalNumber )
                list.set(i, ColorPawns.BLUE);
        }
        Collections.shuffle(list);
        return list;
    }

    //se per errore ho dei colori delle torri assegnati due volte a utenti diversi li sistemo con valori di default
    private List<ColorTower> checkAndFixTower(List<ColorTower> colorTowers){
        List<ColorTower> fixed = new ArrayList<>(colorTowers.size());

        if(colorTowers.size() == 3) {
            if (colorTowers.get(0) == colorTowers.get(1) || colorTowers.get(0) == colorTowers.get(2) || colorTowers.get(1) == colorTowers.get(2)){
                fixed.set(0, ColorTower.WHITE);
                fixed.set(1, ColorTower.BLACK);
                fixed.set(2, ColorTower.GREY);

                return fixed;
            }
        }
        else if (colorTowers.size() == 2) {
            if (colorTowers.get(0) == colorTowers.get(1)){
                fixed.set(0, ColorTower.WHITE);
                fixed.set(1, ColorTower.BLACK);
                return fixed;
            }
        }
        else { //gioco a 4
            if (colorTowers.get(0) == colorTowers.get(1) || colorTowers.get(2) == colorTowers.get(3)
                    || colorTowers.get(0) != colorTowers.get(2)|| colorTowers.get(1) != colorTowers.get(3)){
                fixed.set(0, ColorTower.WHITE);
                fixed.set(1, ColorTower.BLACK);
                fixed.set(2, ColorTower.WHITE);
                fixed.set(3, ColorTower.BLACK);
                return fixed;
            }
        }
        //se andava bene come era all'inizio restituisco quella iniziale non modificata
        return colorTowers;
    }

    private void assignTowerColorToStudent(List<PlayerModel> players, List<ColorTower> colorTowers){
        List<ColorTower> fixedColorTowers = checkAndFixTower(colorTowers); //controllo che non ci siano errori nella lista delle torri
        AtomicInteger i = new AtomicInteger();
        int numTower = 0;
        if(players.size() == 2)
            numTower = 8;
        if(players.size() == 3)
            numTower = 6;

        int finalNumTower = numTower; //è 0 se ho 4 giocatori

        players.forEach(p ->{
            PlayerInitialState playerState = new PlayerInitialState(p);
            if(finalNumTower == 0) {//4
                if(i.get() == 0 || i.get() == 1)//membri del team con tutte e 8 le torri
                    playerState.setTower(fixedColorTowers.get(i.get()), 8);
                else
                    playerState.setTower(fixedColorTowers.get(i.get()), finalNumTower);
            }else //caso 2-3 giocatori
                playerState.setTower(fixedColorTowers.get(i.get()), finalNumTower);
            i.incrementAndGet();
        });
    }

    public void setInitialGameConfiguration(List<PlayerModel> players, List<ColorTower> colorTowers, List<CloudModel> cloudModels, List<ColorPawns> bag, GameMode mode){
        setIslandController();
        assignTowerColorToStudent(players, colorTowers);
        if(mode == GameMode.ESPERTO){
            players.forEach(p ->{
                PlayerState playerState = new PlayerInitialState(p);
                playerState.addCoins();
            });
            //imposta carte personaggio
        }
        assignBag();
        this.gameModel.init(players, cloudModels, bag, mode);
    }

    //imposta le isole, con madre natura e studenti INIZIALI
    private void setIslandController(){
        int motherNatureIndex = (int)(Math.random() * 12); //numero casuale fra 0 e 11
        List<IslandModel> islands = new ArrayList<>(12);

        int sizeIslandWithStudents = 10;
        int equalNumber = 10/2;
        List<ColorPawns> colors = new ArrayList<>(sizeIslandWithStudents);
        colors = fillListWithColors(colors, sizeIslandWithStudents, equalNumber);
        //colors è una lista con 10 colori, 2 per ogni colore, riempita casualmente: come se fosse il sacchetto

        //counterForColors mi scorre gli elementi dell'array colors, viene incrementato solo quando assegno a un isola
        for (int i = 0, counterForColors = 0; i < 12; i++) {
            if(i != motherNatureIndex) {
                islands.add(new IslandModel(false, colors.get(counterForColors)));
                counterForColors++;
            }
            else if((i + 6) % 12 == 0){//posizione specchio di madre natura dove non ci sono studenti
                islands.add(new IslandModel(false, ColorPawns.NULL));
            }
            else if(i == motherNatureIndex) //posizione di madre natura
                islands.add(new IslandModel(true,  ColorPawns.NULL));
        }
        this.gameModel.setIslands(islands);
    }





}

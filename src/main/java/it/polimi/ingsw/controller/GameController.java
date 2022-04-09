package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.*;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class GameController {
    StateGame currentState;

    private GameModel gameModel;

    public GameController(GameModel gameModel){
        this.gameModel = gameModel;
    }

    public GameModel getGameModel(){
        return this.gameModel;
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

    private void assignBag(){
        int bagSize = 120;
        List<ColorPawns> bag = new ArrayList<>(bagSize);
        int equalNumber = bagSize/5;

        bag = fillListWithColors( bag,  bagSize, equalNumber);
        this.gameModel.setBag(bag);
    }

    private void assignTowerStudent(List<PlayerModel> players, List<ColorTower> colorTowers){
        List<ColorTower> fixedColorTowers = checkAndFixTower(colorTowers); //controllo che non ci siano errori nella lista delle torri
        AtomicInteger i = new AtomicInteger();
        int numTower = 0;
        if(players.size() == 2)
            numTower = 8;
        if(players.size() == 3)
            numTower = 6;

        int finalNumTower = numTower; //è 0 se ho 4 giocatori

        players.forEach(p ->{
            PlayerController playerController = new PlayerController(p);
            if(finalNumTower == 0) {//4
                if(i.get() == 0 || i.get() == 1)//membri del team con tutte e 8 le torri
                    playerController.setTower(fixedColorTowers.get(i.get()), 8);
                else
                    playerController.setTower(fixedColorTowers.get(i.get()), finalNumTower);
            }else //caso 2-3 giocatori
                playerController.setTower(fixedColorTowers.get(i.get()), finalNumTower);
            i.incrementAndGet();
        });
    }

    public void setInitialGameConfiguration(List<PlayerModel> players, List<ColorTower> colorTowers, List<CloudModel> cloudModels, List<ColorPawns> bag, GameMode mode){
        setIslandController();
        assignTowerStudent(players, colorTowers);
        if(mode == GameMode.ESPERTO){
            players.forEach(p ->{
                PlayerController playerController = new PlayerController(p);
                playerController.addCoins();
            });
            //imposta carte personaggio
        }
        assignBag();
        this.gameModel.init(players, cloudModels, bag, mode);
    }

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

    //imposta le isole, con madre natura e studenti iniziali
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

    //***********************************************************////
    //DA SISTEMARE
    //controlla se un giocatore già possiede il prof, nel caso lo toglie e lo assegna al giocatore conquistatore
    private void assignProfToPlayer(PlayerModel playerConquerer, ColorPawns color){
        List<PlayerModel> playersModels = gameModel.getPlayersModel();
        playersModels.get(playersModels.indexOf(playerConquerer)).incrementNumProfs();
        /*
        for(int i=0; i<playersModels.size(); i++){
            if(playersModels.get(i).constains(color))
                playersModels.get(i).removeProf(playersModels.getProfs());
            playersModels.get(i).decrementNumProfs();
            gameModel.playersModels(gameModel.playersModels.indexOf(playerConquerer)).profs.add(color);
        }*/
    }
}

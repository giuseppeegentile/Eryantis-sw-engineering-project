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

        return compressIsland(this.gameModel.getIslandsModel(), joined, indexFirstIsland);
    }

    private List<IslandModel> compressIsland(List<IslandModel> islandModels, IslandModel joined, int indexFirstIsland){
        islandModels.set(indexFirstIsland, joined);
        islandModels.remove(indexFirstIsland + 1);
        return islandModels;
    }

    private List<ColorTower> checkAndFixTower(List<ColorTower> colorTowers){
        List<ColorTower> fixed = new ArrayList<>(colorTowers.size());
        //se per errore ho dei colori delle torri assegnati due volte a utenti diversi li sistemo
        if(colorTowers.size() == 3) {
            if (colorTowers.get(0) == colorTowers.get(1) || colorTowers.get(0) == colorTowers.get(2) || colorTowers.get(1) == colorTowers.get(2)){
                fixed.set(0, ColorTower.WHITE);
                fixed.set(1, ColorTower.BLACK);
                fixed.set(2, ColorTower.GREY);
            }
        }
        else if (colorTowers.size() == 2) {
            if (colorTowers.get(0) == colorTowers.get(1)){
                fixed.set(0, ColorTower.WHITE);
                fixed.set(1, ColorTower.BLACK);
            }
        }
        else { //gioco a 4
            if (colorTowers.get(0) == colorTowers.get(1) || colorTowers.get(2) == colorTowers.get(3)
                || colorTowers.get(0) != colorTowers.get(2)|| colorTowers.get(1) != colorTowers.get(3)
            ){
                fixed.set(0, ColorTower.WHITE);
                fixed.set(1, ColorTower.BLACK);
                fixed.set(2, ColorTower.WHITE);
                fixed.set(3, ColorTower.BLACK);
            }
        }
        return fixed;
    }

    //bagSize è da decidere in base al # di giocatori
    private void assignBag(){
        int bagSize = 120;
        List<ColorPawns> bag = new ArrayList<>(bagSize);
        int equalNumber = bagSize/5;

        bag = fillListWithColors( bag,  bagSize, equalNumber);
        this.gameModel.setBag(bag);
    }

    private void assignTowerStudent(List<PlayerModel> players, List<ColorTower> colorTowers){
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
                    playerController.setTower(colorTowers.get(i.get()), 8);
                else
                    playerController.setTower(colorTowers.get(i.get()), finalNumTower);
            }else //caso 2-3 giocatori
                playerController.setTower(colorTowers.get(i.get()), finalNumTower);
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

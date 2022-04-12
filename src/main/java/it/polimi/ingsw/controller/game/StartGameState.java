package it.polimi.ingsw.controller.game;

import it.polimi.ingsw.controller.player.PlayerState;
import it.polimi.ingsw.controller.player.PlayerInitialState;
import it.polimi.ingsw.model.ColorPawns;
import it.polimi.ingsw.model.ColorTower;
import it.polimi.ingsw.model.cards.AssistantCardModel;
import it.polimi.ingsw.model.game.CloudModel;
import it.polimi.ingsw.model.game.GameMode;
import it.polimi.ingsw.model.game.GameModel;
import it.polimi.ingsw.model.islands.IslandModel;
import it.polimi.ingsw.model.player.PlayerModel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;


//l'unico metodo che serve dall'esterno è setInitialGameConfiguration. Tutti gli altri sono dei metodi utility ma non servono al di fuori della classe
public class StartGameState implements GameState {
    private GameModel gameModel;

    @Override
    public GameModel getGameModel() {
        return this.gameModel;
    }

    public StartGameState(GameModel gameModel){
        this.gameModel = gameModel;
    }

    public void setInitialGameConfiguration(List<PlayerModel> players, List<ColorTower> colorTowers, GameMode mode){
        setIslandController();
        assignTowerColorToStudent(players, colorTowers);

        assignBag();
        generateDeck();

        setClouds(players.size());

        setInitialStudentEntrance(players);


        this.gameModel.setGameMode(mode);
        this.gameModel.setPlayers(players);

        assignCardsToPlayers();

        if(mode == GameMode.ESPERTO){
            players.forEach(p ->{
                PlayerState playerState = new PlayerInitialState(p);
                playerState.addCoins();
            });
            //imposta carte personaggio
        }
    }

    //UTILITY METHODS
    private void setInitialStudentEntrance(List<PlayerModel> players){
        int playerNumber = players.size();
        int initialSizeStudentEntrance = 7; //gioco a 4 o 2
        if (playerNumber == 3) initialSizeStudentEntrance = 9; //gioco a 3

        int finalInitialSizeStudentEntrance = initialSizeStudentEntrance;
        players.forEach(p->{
            int bagSize = this.gameModel.getBag().size();
            List<ColorPawns> studentInEntrance = this.gameModel.getBag().subList(bagSize - 1 - finalInitialSizeStudentEntrance,bagSize - 1);
            this.gameModel.getBag().subList(bagSize - 1 - finalInitialSizeStudentEntrance,bagSize - 1).clear(); //toglie gli studenti appena spostati
            p.setStudentInEntrance(studentInEntrance);
        });
    }

    private void setClouds(int playerSize){
        int cloudsNumber, sizeStudentsClouds;

        if(playerSize % 2 == 0) sizeStudentsClouds = 3;
        else sizeStudentsClouds = 4;

        cloudsNumber = playerSize;
        List<CloudModel> cloudModels = new ArrayList<>(cloudsNumber);
        for(int i = 0; i < cloudsNumber; i++){
            cloudModels.add(new CloudModel(sizeStudentsClouds));
        }
        this.gameModel.setCloudsModel(cloudModels);
    }
    private void assignBag(){
        int bagSize = 120;
        List<ColorPawns> bag = new ArrayList<>(bagSize);
        int equalNumber = bagSize/5;

        bag = fillListWithColors( bag,  bagSize, equalNumber);
        this.gameModel.setBag(bag);
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


    //imposta le isole, con madre natura e studenti INIZIALI
    private void setIslandController(){
        int motherNatureIndex = (int)(Math.random() * 12); //numero casuale fra 0 e 11
        List<IslandModel> islands = new ArrayList<>(12);

        int sizeIslandWithStudents = 10;
        int equalNumber = 10/2;
        List<ColorPawns> colors = new ArrayList<>(sizeIslandWithStudents);
        colors = fillListWithColors(colors, sizeIslandWithStudents, equalNumber);
        //colors è una lista con 10 colori, 2 per ogni colore, riempita casualmente: come se fosse il sacchetto

        //counterForColors mi scorre gli elementi dell'array colors, viene incrementato solo quando assegno a un'isola
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

    private void generateDeck(){
        byte j = 0;
        for(int i = 0; i < 10; i++ ){
            if(i % 2 == 0) j++;
            this.gameModel.addCardToDeck(i, new AssistantCardModel(i + 1, j));
        }
    }

    private void assignCardsToPlayers(){
        List<AssistantCardModel> deck = this.gameModel.getDeck();
        Collections.shuffle(deck);
        this.gameModel.getPlayersModel().get(0).setDeckAssistantCardModel(deck.subList(0, 9));
        this.gameModel.getPlayersModel().get(1).setDeckAssistantCardModel(deck.subList(10, 19));
        if(this.gameModel.getPlayersNumber() != 2) this.gameModel.getPlayersModel().get(2).setDeckAssistantCardModel(deck.subList(20, 29)); //se ho 3 o 4 giocatori
        if(this.gameModel.getPlayersNumber() == 4) this.gameModel.getPlayersModel().get(3).setDeckAssistantCardModel(deck.subList(30, 39));
    }


}

package it.polimi.ingsw.controller.game;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import it.polimi.ingsw.controller.player.PlayerState;
import it.polimi.ingsw.controller.player.PlayerInitialState;
import it.polimi.ingsw.model.colors.ColorPawns;
import it.polimi.ingsw.model.colors.ColorTower;
import it.polimi.ingsw.model.cards.AssistantCardModel;
import it.polimi.ingsw.model.enums.PhaseGame;
import it.polimi.ingsw.model.game.CloudModel;
import it.polimi.ingsw.model.enums.GameMode;
import it.polimi.ingsw.model.game.GameModel;
import it.polimi.ingsw.model.islands.IslandModel;
import it.polimi.ingsw.model.player.PlayerModel;
import it.polimi.ingsw.network.message.InitialConfigurationRequestMessage;
import it.polimi.ingsw.network.message.Message;
import it.polimi.ingsw.view.VirtualView;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.Stream;


//l'unico metodo che serve dall'esterno è setInitialGameConfiguration. Tutti gli altri sono dei metodi utility ma non servono al di fuori della classe
public class StartGameState extends GameController implements GameState {
    private final GameModel gameModel;


    public StartGameState(GameModel gameModel){
        this.gameModel = gameModel;
        this.gameModel.setGameState(PhaseGame.START);
    }

    @Override
    public GameModel getGameModel() {
        return this.gameModel;
    }

    @Override
    public PhaseGame getState() {
        return this.gameModel.getGameState();
    }
    //DA TOGLIERE, LO SOSTITUISCE QUELLO CHE ACCETTA I MESSAGGI (IDEM PER LE ALTRE CLASSI)
    /**
     * Methods that initialize the game configuration. In particular:
     * instantiate the 12 islands with mother nature and random students,instantiate the bag with the students, the clouds,the tower for the players and the students in the entrance
     *
     * @param players List of the players
     * @param colorTowers List of the color of the tower in the same order of the player that own them. (i.e. player1 in position 0 own the tower Grey -> the tower grey is in the 0 position of this List)
     * @param mode The Game Mode: beginner or advanced. Based on this the player will have coins at the start.
     */
    public void setInitialGameConfiguration(List<PlayerModel> players, List<ColorTower> colorTowers, GameMode mode){
        setIslandController(); //initialize the 12 islands
        assignBag();            //generate the bag randomly with 120 students

        generateDeck();         //generate the deck

        setClouds(players.size());

        this.gameModel.setGameMode(mode);
        this.gameModel.setPlayers(players);

        assignTowerColorToStudent(colorTowers);
        setInitialStudentEntrance();
        //System.out.println(this.gameModel.getBag().size());
        assignCardsToPlayers();

        if(mode == GameMode.ESPERTO){
            players.forEach(p ->{
                PlayerState playerState = new PlayerInitialState(p);
                playerState.addCoins();
            });
            //imposta carte personaggio
        }
    }


    public void setInitialGameConfiguration(Message receivedMessage){
        List<String> playersString = ((InitialConfigurationRequestMessage) receivedMessage).getPlayers();
        List<PlayerModel> players = new ArrayList<>();
        playersString.forEach(p->{
            players.add(GameModel.getInstance().getPlayerByNickname(p));
        });

        List<ColorTower> colorTowers = ((InitialConfigurationRequestMessage) receivedMessage).getTowers();

        GameMode mode = ((InitialConfigurationRequestMessage) receivedMessage).getGameMode();


        setIslandController(); //initialize the 12 islands
        assignBag();            //generate the bag randomly with 120 students

        generateDeck();         //generate the deck

        setClouds(players.size());

        this.gameModel.setGameMode(mode);
        this.gameModel.setPlayers(players);

        assignTowerColorToStudent(colorTowers);
        setInitialStudentEntrance();
        //System.out.println(this.gameModel.getBag().size());
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

    /**
     * Utility method, called in setInitialGameConfiguration. Set the student in entrance based on the players number.
     */
    private void setInitialStudentEntrance(){
        int playerNumber = this.gameModel.getPlayersModel().size();
        int numStudentEntrance = 7; //gioco a 4 o 2
        if (playerNumber == 3) numStudentEntrance = 9; //gioco a 3

        int i = 0;
        int bagSize = this.gameModel.getBag().size();

        for (PlayerModel p :this.gameModel.getPlayersModel()) {
            List<ColorPawns> studentInEntrance = this.gameModel.getBag().subList(bagSize - 1 - (1+i)*numStudentEntrance,bagSize - i*numStudentEntrance);
            p.setStudentInEntrance(studentInEntrance);
            i++;
            //System.out.println(i);
        }
        //System.out.println(bagSize - 1 - numStudentEntrance - (i-1)*numStudentEntrance);
        //this.gameModel.getBag().subList(bagSize - numStudentEntrance - (i-1)*numStudentEntrance, bagSize).clear();
        //for(int j = bagSize-1; j > bagSize - numStudentEntrance -1 - (i-1)*numStudentEntrance; j--) this.gameModel.getBag().remove(j);
        this.gameModel.setBag(this.gameModel.getBag().subList(0, bagSize - numStudentEntrance - (i-1)*numStudentEntrance));

        //System.out.println(this.gameModel.getBag().size());
    }

    //da testare
    public void setInitialStudentEntranceForSinglePlayer(PlayerModel player){
        int playerNumber = this.gameModel.getPlayersModel().size();
        int numStudentEntrance = 7; //gioco a 4 o 2
        if (playerNumber == 3) numStudentEntrance = 9; //gioco a 3

        int bagSize = this.gameModel.getBag().size();

        List<ColorPawns> studentInEntrance = this.gameModel.getBag().subList(bagSize - 1 - numStudentEntrance,bagSize - numStudentEntrance);
        player.setStudentInEntrance(studentInEntrance);


        //System.out.println(bagSize - 1 - numStudentEntrance - (i-1)*numStudentEntrance);
        //this.gameModel.getBag().subList(bagSize - numStudentEntrance - (i-1)*numStudentEntrance, bagSize).clear();
        //for(int j = bagSize-1; j > bagSize - numStudentEntrance -1 - (i-1)*numStudentEntrance; j--) this.gameModel.getBag().remove(j);
        this.gameModel.setBag(this.gameModel.getBag().subList(0, bagSize - numStudentEntrance));

        //System.out.println(this.gameModel.getBag().size());
    }

    /**
     * Utility method, called in setInitialGameConfiguration.
     * @param playerSize Number of players in the Game. Is the same number of clouds.
     */
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

    /**
     * Utility method, called in setInitialGameConfiguration.
     * Create randomly a bag of students of size 120. With all colours.
     */
    void assignBag(){
        int bagSize = 120;
        List<ColorPawns> bag = new ArrayList<>(bagSize);
        int equalNumber = 24;

        bag = fillListWithColors( bag, bagSize, equalNumber);
        this.gameModel.setBag(bag);
    }

    //prende una generica lista di studenti e la riempie casualmente
    //usata per riempire la bag e le isole iniziali
    //size: dimensione da riempire (bag: 120, isole: 10)
    //equalNumber: quantità uguali per ogni colore (bag: 24(=120/5)  isole: 2)
    /**
     * Takes a generic list of ColorPawns and fills it randomly. Mainly used for the bag and initial islands.
     * @param list List of ColorPawns to fill randomly.
     * @param equalNumber Number of same colors that will be in the List.
     * @return A list of ColorPawns filled with random values in the same quantity for every value.
     */
    List<ColorPawns> fillListWithColors(List<ColorPawns> list, int size, int equalNumber){
        List<ColorPawns> listGreen = new ArrayList<>(Collections.nCopies(equalNumber, ColorPawns.GREEN));
        List<ColorPawns> listRed = new ArrayList<>(Collections.nCopies(equalNumber, ColorPawns.RED));
        List<ColorPawns> listYellow = new ArrayList<>(Collections.nCopies(equalNumber, ColorPawns.YELLOW));
        List<ColorPawns> listPink = new ArrayList<>(Collections.nCopies(equalNumber, ColorPawns.PINK));
        List<ColorPawns> listBlue = new ArrayList<>(Collections.nCopies(equalNumber, ColorPawns.BLUE));

        List<ColorPawns> listToRet = Stream.of(listGreen, listRed, listYellow, listPink, listBlue)
                        .flatMap(Collection::stream)
                                .collect(Collectors.toList());

        Collections.shuffle(listToRet);
        return listToRet;
    }

    //se per errore ho dei colori delle torri assegnati due volte a utenti diversi li sistemo con valori di default

    /**
     * Check the List of towers of the player correspondence. If two players choose the same color of tower it assign default values.
     * @param colorTowers List of tower-player correspondence.
     * @return The list of the tower-player correspondence updated (if errors occurred).
     */
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

    /**
     * Set the tower color and number to the student correspondence.
     * @param colorTowers Tower-player correspondence List.
     */
    private void assignTowerColorToStudent(List<ColorTower> colorTowers){
        List<ColorTower> fixedColorTowers = checkAndFixTower(colorTowers); //controllo che non ci siano errori nella lista delle torri
        List<PlayerModel> players = this.gameModel.getPlayersModel();
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

    
    /**
     * Set islands, with mother nature and initial students configuration.
     */
    private void setIslandController(){
        int motherNatureIndex = (int)(Math.random() * 12); //numero casuale fra 0 e 11
        List<IslandModel> islands = new ArrayList<>(12);

        int sizeIslandWithStudents = 10;
        int equalNumber = 2;
        List<ColorPawns> colors = new ArrayList<>(sizeIslandWithStudents);
        colors = fillListWithColors(colors, sizeIslandWithStudents, equalNumber);
        //colors è una lista con 10 colori, 2 per ogni colore, riempita casualmente: come se fosse il sacchetto

        int indexMirrorMotherNature = (motherNatureIndex + 6) % 12;

        //counterForColors mi scorre gli elementi dell'array colors, viene incrementato solo quando assegno a un'isola
        for (int i = 0, counterForColors = 0; i < 12; i++) {
            if(i != motherNatureIndex && i != indexMirrorMotherNature) {
                islands.add(new IslandModel(false, colors.get(counterForColors)));
                counterForColors++;
            }
            else if(i == indexMirrorMotherNature){//posizione specchio di madre natura dove non ci sono studenti
                islands.add(new IslandModel(false));
            }
            else if(i == motherNatureIndex) //posizione di madre natura
                islands.add(new IslandModel(true));
        }
        this.gameModel.setIslands(islands);
    }


    /**
     * Generate random cards and put them in deck of GameModel.
     */
    void generateDeck(){
        for(int k = 0; k < 4; k++) {
            byte j = 0;
            for (int i = 0; i < 10; i++) {
                if (i % 2 == 0) j++;
                this.gameModel.addCardToDeck(new AssistantCardModel(i + 1, j));
            }
        }
    }

    /**
     * Shuffle the deck and gives ten card to each player.
     */
    private void assignCardsToPlayers(){
        List<AssistantCardModel> deck = this.gameModel.getDeck();
        Collections.shuffle(deck);
        AtomicInteger i = new AtomicInteger();


        deck.forEach(c->{
            if(i.get() < 10)
                c.setOwner(this.gameModel.getPlayersModel().get(0));
            if(i.get() < 20 && i.get() >=10)
                c.setOwner(this.gameModel.getPlayersModel().get(1));
            if(i.get() < 30 && i.get() >=20 && this.gameModel.getPlayersNumber() != 2)
                c.setOwner(this.gameModel.getPlayersModel().get(2));
            if(i.get() < 40 && i.get() >=30 && this.gameModel.getPlayersNumber() == 4)
                c.setOwner(this.gameModel.getPlayersModel().get(3));
            i.getAndIncrement();
        });
        this.gameModel.getPlayersModel().get(0).setDeckAssistantCardModel(deck.subList(0, 10));
        this.gameModel.getPlayersModel().get(1).setDeckAssistantCardModel(deck.subList(10, 20));
        if(this.gameModel.getPlayersNumber() != 2) this.gameModel.getPlayersModel().get(2).setDeckAssistantCardModel(deck.subList(20, 30)); //se ho 3 o 4 giocatori
        if(this.gameModel.getPlayersNumber() == 4) this.gameModel.getPlayersModel().get(3).setDeckAssistantCardModel(deck.subList(30, 40));

    }


}

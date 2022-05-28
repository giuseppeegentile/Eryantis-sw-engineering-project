package it.polimi.ingsw.view.cli;

import it.polimi.ingsw.controller.ClientController;
import it.polimi.ingsw.model.cards.AssistantCardModel;
import it.polimi.ingsw.model.cards.CharacterCardModel;
import it.polimi.ingsw.model.colors.ColorPawns;
import it.polimi.ingsw.model.colors.ColorTower;
import it.polimi.ingsw.model.enums.GameMode;
import it.polimi.ingsw.model.game.CloudModel;
import it.polimi.ingsw.model.game.GameModel;
import it.polimi.ingsw.model.islands.IslandModel;
import it.polimi.ingsw.model.player.PlayerModel;
import it.polimi.ingsw.network.message.Message;
import it.polimi.ingsw.network.message.TextMessage;
import it.polimi.ingsw.observer.ViewObservable;
import it.polimi.ingsw.view.View;

import java.io.PrintStream;
import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

import static java.lang.Byte.parseByte;
import static java.lang.Integer.parseInt;


//sout per fare printout

public class Cli extends ViewObservable implements View {

    private final PrintStream out;
    private Thread inputThread;
    private final List<ColorCli> listColor = List.of(ColorCli.RED, ColorCli.BLUE, ColorCli.GREEN, ColorCli.PINK, ColorCli.YELLOW);
    private static final String STR_INPUT_CANCELED = "User input canceled.";

    /**
     * Default constructor.
     */
    public Cli() {
        out = System.out;
    }

    public String readLine() throws ExecutionException {
        FutureTask<String> futureTask = new FutureTask<>(new InputReadTask());
        inputThread = new Thread(futureTask);
        inputThread.start();

        String input = null;

        try {
            input = futureTask.get();
        } catch (InterruptedException e) {
            futureTask.cancel(true);
            Thread.currentThread().interrupt();
        }
        return input;
    }

    public void init() {
        out.println("" +
                        "888888 8888Yb 88    db    88b 88 888888 Yb  dP .dPY8 \n" +
                        "88__   88__dP 88   dPYb   88Yb88   88    YbdP  `Ybo. \n" +
                        "88     88 Yb  88  dP__Yb  88 Y88   88     8P   o.`Y8b \n" +
                        "888888 88  Yb 88 dP    Yb 88  Y8   88    dP    8bodP' \n");

        out.println("Welcome to Eriantys Board Game!");

        try {
            askServerInfo();   //il test si blocca, non capisco perché
        } catch (ExecutionException e) {
            out.println(STR_INPUT_CANCELED);
        }
    }

    public void askServerInfo() throws ExecutionException {
        Map<String, String> serverInfo = new HashMap<>();
        String defaultAddress = "localhost";
        String defaultPort = "16847";
        boolean validInput;

        out.println("Please specify the following settings. Press enter to use the default value Localhost.");

        do {
            out.print("Enter the server address: ");

            String address = readLine();

            if (address.equals("")) {
                serverInfo.put("address", defaultAddress);
                validInput = true;
            } else if (ClientController.isValidIpAddress(address)) {
                serverInfo.put("address", address);
                validInput = true;
            } else {
                out.println("Invalid address!");
                clearCli();
                validInput = false;
            }
        } while (!validInput);

        do {
            out.print("Enter the server port [" + defaultPort + "]: ");
            String port = readLine();

            if (port.equals("")) {
                serverInfo.put("port", defaultPort);
                validInput = true;
            } else {
                if (ClientController.isValidPort(port)) {
                    serverInfo.put("port", port);
                    validInput = true;
                } else {
                    out.println("Invalid port!");
                    validInput = false;
                }
            }
        } while (!validInput);

        notifyObserver(obs -> obs.onUpdateServerInfo(serverInfo));
    }


    @Override
    public void showWinMessage(PlayerModel winner) {
        out.println("Game finished: " + winner.getNickname() + " WINS!");
        System.exit(0);
    }

    @Override
    public void showSkippingMotherMovement(String activeNick) {
        out.println(activeNick + ", influence is not being calculated because there is a prohibition card activated on this island");
    }

    @Override
    public void showMessageJoiningIsland(Message message) {
        out.println(((TextMessage)message).getText() + "\n");
    }

    @Override
    public void askMoveCloudToEntrance(String nickname, List<CloudModel> clouds) {
        out.println(nickname + ", from which cloud do you want to take the students?\nPlease select the index of the cloud:\n");
        StringBuilder str = new StringBuilder();
        int i = 1;
        out.println(clouds.size());
        for(CloudModel cloud : clouds){
            if(cloud!=null) {
                str.append(i + " -> ");
                for (ColorPawns color : cloud.getStudents()) {
                    if(color!=null) {
                        str.append(ColorCli.getEquivalentColorPawn(color)).append(color + "  ").append(ColorCli.RESET);
                    }
                }
                str.append("\n");
                i++;
            }
        }
        out.println(str);
        int chosenIndex = parseInt(read())-1;
        //int chosenIndex = askUntilValid(clouds.size(), "You've entered an invalid number, please select a cloud from the list shown\n", str) - 1;
        notifyObserver(obs -> obs.onChosenCloud(nickname, chosenIndex));
    }

    @Override
    public void askMotherNatureMovements(PlayerModel player, byte maxMovement) {
        out.println("Type the number of movements you want mothernature to make (it can be between 0 and " + (int) maxMovement + ").\n");

        byte movementChosen = parseByte(read());

        while(movementChosen > maxMovement || movementChosen < 0){
            movementChosen = parseByte(read());
            if(movementChosen > maxMovement || movementChosen < 0)
                out.println("You've entered an invalid number, it can be between 0 and " + (int) maxMovement + "\n");
        }
        byte finalMovementChosen = movementChosen;
        notifyObserver(obs -> obs.onUpdateMotherNature(player, finalMovementChosen));
    }

    @Override
    public void askMoveEntranceToHall(String player, List<ColorPawns> colorPawns, int numberStudentsHaveToMove) {
        List<ColorPawns> colors = new ArrayList<>();
        out.println("You have to move " + numberStudentsHaveToMove + " to your hall");
        if(numberStudentsHaveToMove != 0) {
            askingMoveStudents(colorPawns, colors, numberStudentsHaveToMove, "hall");
            notifyObserver(obs -> obs.onUpdateStudentToHall(player, colors));
        }else{
            out.println("You moved all the students available for this turn to the island..Skipping to the mother nature movement.\n");
            notifyObserver(obs -> obs.onUpdateStudentToHall(player, null));
        }
    }



    @Override
    public void askMoveEntranceToIsland(String player, List<ColorPawns> entrance, List<IslandModel> islands) {
        StringBuilder str = new StringBuilder();
        List<ColorPawns> colors = new ArrayList<>();

        out.println("Choose an island from the following list: \n");
        int sizeIslands= islands.size();
        showIslands(player, islands);
        int indexIsland = askUntilValid( sizeIslands, "Invalid index for island, must be between 1 and "+ sizeIslands, str) - 1;


        out.println("How many students do you want to move to the island?\n");
        int numberStudents;
        numberStudents = parseInt(read());
        if(GameModel.getInstance().getPlayersNumber()==3 && (numberStudents < 0 || numberStudents > 4)){
            numberStudents = askUntilValid(4, "Must be a number between 0 and 4: \n", str);
        }else if(GameModel.getInstance().getPlayersNumber()%2== 0 && (numberStudents < 0 || numberStudents > 3)) {
            numberStudents = askUntilValid(3, "Must be a number between 0 and 3: \n", str);
        }
        askingMoveStudents(entrance, colors, numberStudents, "island");
        notifyObserver(obs -> obs.onUpdateStudentToIsland(player, colors, indexIsland));
    }

    private void askingMoveStudents(List<ColorPawns> entrance, List<ColorPawns> colors, int numberStudents, String destination) {
        int finalChosenIndex;
        int chosenIndex;

        for(int j=0; j<numberStudents; j++) {
            StringBuilder str = new StringBuilder();
            str.append("Type the index of the students you want to move from your entrance to the "+destination + " :\n");
            int i=0;
            for(ColorPawns color : entrance){
                i+=1;
                str.append(i).append(" -> ").append(ColorCli.getEquivalentColorPawn(color)).append(color).append(ColorCli.RESET).append("\n");
            }
            chosenIndex = askUntilValid(entrance.size(), "Please select a valid index of the student from the list shown\n", str);
            finalChosenIndex = chosenIndex - 1;
            colors.add(entrance.get(finalChosenIndex));
            entrance.remove(finalChosenIndex);
        }
    }

    /*@Override
    public void showHallMessage(String player, Map<ColorPawns, Integer> hall) {

    }

    @Override
    public void showEntranceMessage(String player, List<ColorPawns> entrance) {

    }*/

    @Override
    public void showCemeteryMessage(String player, List<AssistantCardModel> cemetery) {
        out.println("This is the cemetery of the current round: ");
        for(AssistantCardModel assistantCard : cemetery){
            out.println(assistantCard.getOwner().getNickname() + " -> Priority = " + assistantCard.getPriority() + ", Mothernature movements = " + assistantCard.getMotherNatureMovement());
        }
    }

    @Override
    public void showTextMessage(String player, String text) {
        out.println(text + "\n");
    }

    @Override
    public void showIslandMessage(String nickname, IslandModel islandModel, int islandIndex) {
        StringBuilder strBoardBld = new StringBuilder();
        out.println(nickname + ", this is the updated island N " + islandIndex + 1 + "\n");
        strBoardBld.append(" -----------\n");

        buildIsland(islandModel, strBoardBld);
        strBoardBld.append(ColorCli.RESET).append("|\n");

        if(islandModel.getMotherNature())
            strBoardBld.append("|    ").append(ColorCli.RED).append("M").append(ColorCli.RESET).append(ColorCli.getEquivalentColorCliTower(islandModel.getTowerColor())).append(" T").append(ColorCli.RESET).append("    |\n");
        else
            strBoardBld.append("|    ").append(ColorCli.getEquivalentColorCliTower(islandModel.getTowerColor())).append(" T ").append(ColorCli.RESET).append("    |\n");
        strBoardBld.append(" -----------\n");
        out.println(strBoardBld);
    }

    @Override
    public void showCloudsMessage(String nickname, List<CloudModel> clouds) {
        StringBuilder strBoardBld = new StringBuilder();
        for(CloudModel cloud : clouds){
            if (cloud != null){
                strBoardBld.append(" -----------                ");
            }
        }
        strBoardBld.append("\n");
        for (CloudModel cloud : clouds) {
            if (cloud != null) {
                buildCloud(cloud, strBoardBld);
                strBoardBld.append(ColorCli.RESET).append("|               ");
            }
        }
        strBoardBld.append("\n");
        for(CloudModel cloud : clouds){
            if (cloud != null){
                strBoardBld.append(" -----------                ");
            }
        }
        out.println(strBoardBld);
    }

    @Override
    public void showMoveMotherNatureMessage(PlayerModel player, byte movement) {
        out.println(player.getNickname() + " has moved Mothernature " + (int)movement + " positions.\n");
    }


    /*@Override
    public void updateIslands(String nickname) {

    }

    @Override                                         c'è già showDeckMessage
    public void showCards(PlayerModel playerModel) {

    }

    @Override
    public void askGetFromBag() {

    }

    @Override
    public void showProfsMessage(String player, List<ColorPawns> profs) {

    }*/

    @Override
    public void showInvalidTower(String player, ColorTower colorTower) {
        out.println("Oh no! The tower color " + ColorCli.getEquivalentColorTower(colorTower) + colorTower + ColorCli.RESET + " is already taken\n");
    }

    @Override
    public void showLoginResult(boolean nicknameAccepted, boolean connectionSuccessful, String nickname) {
        clearCli();
        if (nicknameAccepted && connectionSuccessful) {
            out.println("Hi, " + nickname + "! You connected to the server.");
        } else if (connectionSuccessful) {
            askNickname();
        } else if (nicknameAccepted) {
            out.println("Lobby is full, disconnection...");

            System.exit(1);
        } else {
            showErrorAndExit("Could not contact server.");
        }
    }

    /*@Override
    public void showTowerMessage(String player, ColorTower colorTower, int towerNumber) {

    }*/

    @Override
    public void askPlayersNumber() {
        int playerNumber;
        out.println("How many players you want to play with? (2 to 4) ");
        playerNumber = parseInt(read());

        notifyObserver(obs -> obs.onUpdatePlayersNumber(playerNumber));
    }

    @Override
    public void askGameMode() {
        int mode = 0;
        while (mode < 1 || mode > 2){
            out.println("Enter the Game Modality: ");
            out.println("1 - PRINCIPIANTE: ");
            out.println("2 - ESPERTO: ");

            mode = parseInt(read());
        }
        GameMode finalMode = List.of(GameMode.PRINCIPIANTE, GameMode.ESPERTO).get(mode-1);

        notifyObserver(obs -> obs.onUpdateGameMode(finalMode));
    }

    @Override
    public void showPlayerBoardMessage(String nickname, List<ColorTower> towers, Map<ColorPawns, Integer> hall, List<ColorPawns> entrance,List<ColorPawns> profs) {
        StringBuilder strBoardBld = new StringBuilder();
        List<ColorPawns> colors = new ArrayList<>();
        colors.add(ColorPawns.GREEN);
        colors.add(ColorPawns.RED);
        colors.add(ColorPawns.YELLOW);
        colors.add(ColorPawns.PINK);
        colors.add(ColorPawns.BLUE);
        strBoardBld.append("-----------------------------------\n");
        strBoardBld.append("  Entry          Hall         Profs\n");
        for(ColorPawns color : colors){
            strBoardBld.append("|  ").append(ColorCli.getEquivalentColoCliStudent(color)).append(" ").append(Collections.frequency(entrance, color)).append(ColorCli.RESET).append("   | ");
            int numberStudents = hall.get(color);
            for (int i=0; i<10; i++){
                if (numberStudents>0){
                    strBoardBld.append(ColorCli.getEquivalentColoCliStudent(color)).append("0 ");
                    numberStudents--;
                } else {
                    strBoardBld.append(ColorCli.RESET).append("  ");
                }
            }
            strBoardBld.append(ColorCli.RESET).append("| ");
            if (profs.contains(color)){
                strBoardBld.append(ColorCli.getEquivalentColoCliStudent(color)).append("0 ").append(ColorCli.RESET).append("|\n");
            } else {
                strBoardBld.append("  |\n");
            }
        }
        strBoardBld.append("-----------------------------------\n");
        strBoardBld.append("  Towers ");
        for (int i=0; i<towers.size(); i++){
            strBoardBld.append(ColorCli.getEquivalentColorCliTower(towers.get(0))).append("0 ");
        }
        strBoardBld.append(ColorCli.RESET).append("\n");
        out.println(strBoardBld);
    }


    /*@Override
    public void showDeckMessage(String player, List<AssistantCardModel> playerDeck) {
        clearCli();
        int j = 0;
        int i;
        out.println("These are your available assistant cards " + player + "!\n");
        for (i = 0; i<playerDeck.size(); i++)
            if (!(playerDeck.get(i).getPriority() == 0 && playerDeck.get(i).getMotherNatureMovement() == 0)){
                out.println(j + " -> Priority = " + playerDeck.get(i).getPriority() + ", Mothernature movements = " + playerDeck.get(i).getMotherNatureMovement() + "\n");
                j++;
            }
    }*/

    /*@Override
    public void updateTowerOnIsland(String nickname, IslandModel islandModel) {

    }*/


    @Override
    public void showEndTurn(String nick) {
        //clearCli();
        out.println(nick + " your turn is finished!\n");
    }

    @Override
    public void showInvalidNickname(String nickname) {
        out.println("Oh no! The nickname " + ColorCli.RED + nickname + ColorCli.RESET + " is already taken\n");
    }

    @Override
    public void showStartTurn(String nick) {
        out.println(nick + " it's your turn!\n");
    }

    @Override
    public void showInvalidCloud(String nick) {
        out.println(nick + ", the cloud you've chosen is empty.\nPlease select another one.\n");
    }

    @Override
    public void errorCard(String player, AssistantCardModel card) {
        out.println(player + "has played a wrong card.\n");
    }

    @Override
    public void showDisconnectionMessage(String nickname, String message) {
        out.println(nickname + " " + message + "\n");
    }

    @Override
    public void showGenericMessage(String message) {
        out.println(message);
    }

    @Override
    public void showInvalidMovementMessage(String nick, byte movementAllowed, byte movementInserted) {
        out.println(nick + ", you're trying to move Mothernature " + movementInserted + " positions but you're allowed to move her " + movementAllowed + ".\nPlease try again.\n");
    }

    @Override
    public void showInvalidNumberOfStudentMoved(String nickname) {
        out.println(nickname + ", you haven't moved 3 students in this round.\n");
    }

    @Override
    public void askNickname() {
        out.print("Enter your nickname: ");
        try {
            String nickname = readLine();
            notifyObserver(obs -> obs.onUpdateNickname(nickname));
        } catch (ExecutionException e) {
            out.println(STR_INPUT_CANCELED);
        }
    }

    @Override
    public void showErrorAndExit(String error) {
        inputThread.interrupt();

        out.println("\nERROR: " + error);
        out.println("EXIT.");

        System.exit(1);
    }

    private int askUntilValid(int size, String invalidMessage, StringBuilder stringBuilder){
        int chosenIndex = 0;
        while(chosenIndex > size || chosenIndex <= 0){
            out.println(stringBuilder);
            chosenIndex = parseInt(read());//2
            if(chosenIndex > size || chosenIndex <= 0)
                out.println(invalidMessage);
        }
        return chosenIndex;
    }

    @Override
    public void askPlayCard(String nickname, List<AssistantCardModel> playerDeck, List<AssistantCardModel> cemetery) {
        StringBuilder stringBuilder = new StringBuilder();
        int i = 1;
        List<Integer> indexes = new ArrayList<>(); //indici che può selezionare
        out.println(nickname + ", select your assistant card for this round.\nThis is your deck:");
        for (AssistantCardModel card : playerDeck) {
            if(card.getPriority() != 0 && card.getMotherNatureMovement() != 0)
                if (cemetery.size()>0) {
                    for (AssistantCardModel cemeteryCard : cemetery)
                        // (!(cemeteryCard.getPriority() == card.getPriority() && cemeteryCard.getMotherNatureMovement() == card.getMotherNatureMovement())
                        if (!(cemeteryCard.getPriority() == card.getPriority() && cemeteryCard.getMotherNatureMovement() == card.getMotherNatureMovement())) {
                            stringBuilder.append(i).append(" -> Priority = ").append(card.getPriority()).append(", Mothernature movements = ").append(card.getMotherNatureMovement()).append("\n");
                            indexes.add(i);
                        }
                }else{
                    stringBuilder.append(i).append(" -> Priority = ").append(card.getPriority()).append(", Mothernature movements = ").append(card.getMotherNatureMovement()).append("\n");
                    indexes.add(i);
                }
            i++;
        }
        String message = "You've entered an invalid number, please select a card from the list shown\n";
        int chosenIndex = askUntilValid(playerDeck.size(), message, stringBuilder);
        while(!indexes.contains(chosenIndex)){
            chosenIndex = askUntilValid(playerDeck.size(), message, stringBuilder);
        }
        /*for(AssistantCardModel card : cemetery) {
            if (playerDeck.get(chosenIndex - 1).getPriority() == card.getPriority() && playerDeck.get(chosenIndex-1).getMotherNatureMovement() == card.getMotherNatureMovement())
                System.out.println("You've chosen a card already played, please select a different card from the list shown");
        }*/
        int finalChosenIndex = chosenIndex - 1;
        notifyObserver(obs -> obs.onUpdateCardPlayed(nickname, finalChosenIndex));
    }

    @Override
    public void showOrderPhase(String nickname, List<PlayerModel> order) {
        StringBuilder stringBuilder = new StringBuilder("                           ");
        for(int i = 0; i < order.size()-1; i++){
            stringBuilder.append(order.get(0).getNickname()).append(", ");
        }
        stringBuilder.append(order.get(order.size()-1).getNickname()).append("\n");
        out.println(stringBuilder);
    }

    @Override
    public void askTowerColor(String nickMessage, List<ColorTower> availableColorTowers) {
        StringBuilder str = new StringBuilder();
        str.append("Type the index of the color of the tower you want from the following list: \n");
        int i = 0;
        for(ColorTower t: availableColorTowers){
            i+=1;
            str.append(i).append(" -> ").append(t.name()).append("\n");
        }
        String message = "You've entered an invalid number, please select a card from the list shown\n";
        int finalChosenIndex = askUntilValid(availableColorTowers.size(), message,str)-1;

        notifyObserver(obs -> obs.onUpdateTower(availableColorTowers.get(finalChosenIndex)));
    }


    private String read(){
        String read;
        try {
            read = readLine();
            return read;
        } catch (ExecutionException e) {
            out.println(STR_INPUT_CANCELED);
            return "";
        }
    }


    @Override
    public void askPlayCharacterCard(String activePlayer, List<CharacterCardModel> characterDeck) {
        StringBuilder stringBuilder = new StringBuilder();
        int i = 1;
        out.println(activePlayer + ", select your character card for this round.\nThis is your deck:");
        for (CharacterCardModel card : characterDeck) {
            if(card.enoughCoins()) {
                stringBuilder.append(i).append(" -> Money needed for effect = ").append(card.getMoneyOnCard()).append(", Effect = ").append(card.getEffect().getDescription()).append("\n");
            }
            i++;
        }
        String message = "You've entered an invalid number, please select a card from the list shown\n";
        int chosenIndex = askUntilValid(characterDeck.size(), message, stringBuilder);
        int finalChosenIndex = chosenIndex - 1;
        CharacterCardModel cardPlayed = characterDeck.get(finalChosenIndex);
        notifyObserver(obs -> obs.onUpdateCharacterCardPlayed(activePlayer, cardPlayed));
    }

    /**
     * Shows the lobby screen on the terminal.
     *
     * @param nicknameList list of players.
     */
    @Override
    public void showLobbyMessage(List<String> nicknameList) {
        out.println("LOBBY:");
        for (String nick : nicknameList) {
            out.println(nick + "\n");
        }
    }


    /**
     * Clears the CLI terminal.
     */
    public void clearCli() {
        out.print(ColorCli.CLEAR);
        out.flush();
    }


    @Override
    public void showIslands(String nickname, List<IslandModel> islands) {
        StringBuilder strBoardBld = new StringBuilder();
        strBoardBld.append(" -----------       ".repeat(islands.size() / 2));
        strBoardBld.append("\n");
        for (int i=0; i<islands.size()/2; i++) {
            buildIsland(islands.get(i), strBoardBld);
            strBoardBld.append(ColorCli.RESET).append("|      ");
        }
        strBoardBld.append("\n");
        for (int i=0; i<islands.size()/2; i++) {
            if(islands.get(i).getMotherNature())
                if(islands.get(i).getTowerColor() == ColorTower.NULL)
                    strBoardBld.append("|    ").append(ColorCli.RED).append(" M").append(ColorCli.RESET).append("     |      ");
                else
                strBoardBld.append("|    ").append(ColorCli.RED).append("M").append(ColorCli.RESET).append(ColorCli.getEquivalentColorCliTower(islands.get(i).getTowerColor())).append(" T").append(ColorCli.RESET).append("    |      ");
            else
                if(islands.get(i).getTowerColor() == ColorTower.NULL)
                    strBoardBld.append("|           |      ");
                else
                    strBoardBld.append("|    ").append(ColorCli.getEquivalentColorCliTower(islands.get(i).getTowerColor())).append(" T ").append(ColorCli.RESET).append("    |      ");
        }
        strBoardBld.append("\n");
        strBoardBld.append(" -----------       ".repeat(islands.size() / 2));

        out.println(strBoardBld);
        strBoardBld.append("\n");

        StringBuilder strBoardBld2 = new StringBuilder();

        strBoardBld2.append(" -----------       ".repeat(Math.max(0, islands.size() - 1 - (islands.size() / 2 - 1))));

        strBoardBld2.append("\n");
        for (int i=islands.size()-1; i>islands.size()/2-1; i--) {
            buildIsland(islands.get(i), strBoardBld2);
            strBoardBld2.append(ColorCli.RESET).append("|      ");
        }
        strBoardBld2.append("\n");
        for (int i=islands.size()-1; i>islands.size()/2-1; i--) {
            if(islands.get(i).getMotherNature())
                if(islands.get(i).getTowerColor() == ColorTower.NULL)
                    strBoardBld2.append("|    ").append(ColorCli.RED).append(" M").append(ColorCli.RESET).append("     |      ");
                else
                    strBoardBld2.append("|    ").append(ColorCli.RED).append("M").append(ColorCli.RESET).append(ColorCli.getEquivalentColorCliTower(islands.get(i).getTowerColor())).append(" T").append(ColorCli.RESET).append("    |      ");
            else
            if(islands.get(i).getTowerColor() == ColorTower.NULL)
                strBoardBld2.append("|           |      ");
            else
                strBoardBld2.append("|    ").append(ColorCli.getEquivalentColorCliTower(islands.get(i).getTowerColor())).append(" T ").append(ColorCli.RESET).append("    |      ");
        }
        strBoardBld2.append("\n");

        strBoardBld2.append(" -----------       ".repeat(Math.max(0, islands.size() - 1 - (islands.size() / 2 - 1))));

        strBoardBld2.append("\n");
        out.println(strBoardBld2);
    }

    private void buildIsland(IslandModel island, StringBuilder strBoardBld) {
        strBoardBld.append("| ");
        listColor.forEach(c->{
            int occurrence = Collections.frequency(island.getStudents(), ColorPawns.getEquivalentColorPawns(c.name()));
            strBoardBld.append(ColorCli.getEquivalentColoCliStudent(ColorPawns.getEquivalentColorPawns(c.name()))).append(occurrence).append(" ").append(ColorCli.RESET);
        });
    }

    private void buildCloud(CloudModel cloudModel, StringBuilder strBoardBld) {
        strBoardBld.append("| ");
        listColor.forEach(c->{
            int occurrence = Collections.frequency(cloudModel.getStudents(), ColorPawns.getEquivalentColorPawns(c.name()));
            strBoardBld.append(ColorCli.getEquivalentColorPawn(ColorPawns.getEquivalentColorPawns(c.name()))).append(occurrence).append(" ").append(ColorCli.RESET);
        });
    }
}

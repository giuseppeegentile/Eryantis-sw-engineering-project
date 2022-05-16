package it.polimi.ingsw;

import it.polimi.ingsw.controller.ClientController;
import it.polimi.ingsw.network.client.Client;
import it.polimi.ingsw.view.cli.Cli;

public class ClientApp {

    public static void main(String[] args) {
        Cli view = new Cli();
        ClientController clientController = new ClientController(view);
        view.addObserver(clientController);

        view.init();
    }
}

package it.polimi.ingsw;

import it.polimi.ingsw.controller.ClientController;
import it.polimi.ingsw.view.cli.Cli;
import it.polimi.ingsw.view.gui.JavaFXGui;
import javafx.application.Application;

/**
 * Game's client application
 */

public class ClientApp {

    public static void main(String[] args) {

        boolean cli = false; //default launch gui

        for(String param: args){ //if --c or --cli when running the jar, the cli starts
            if (param.equals("--c") || param.equals("--cli")) {
                cli = true;
                break;
            }
        }
        if(cli){
            Cli view = new Cli();
            ClientController clientController = new ClientController(view);
            view.addObserver(clientController);
            view.init();
        }else Application.launch(JavaFXGui.class);


    }
}

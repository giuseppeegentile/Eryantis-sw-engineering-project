package it.polimi.ingsw;

import com.sun.javafx.beans.event.AbstractNotifyListener;
import it.polimi.ingsw.controller.ClientController;
import it.polimi.ingsw.network.client.Client;
import it.polimi.ingsw.view.cli.Cli;
import it.polimi.ingsw.view.gui.JavaFXGui;
import javafx.application.Application;

public class ClientApp {

    public static void main(String[] args) {

        boolean cli = false; //default launch gui

        for(String param: args){
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

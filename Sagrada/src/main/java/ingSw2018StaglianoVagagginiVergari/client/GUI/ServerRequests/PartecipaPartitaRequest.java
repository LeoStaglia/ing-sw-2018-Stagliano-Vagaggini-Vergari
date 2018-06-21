package ingSw2018StaglianoVagagginiVergari.client.GUI.ServerRequests;

import Eccezioni.FullGameException;
import ingSw2018StaglianoVagagginiVergari.client.GUI.AlertPopup;
import ingSw2018StaglianoVagagginiVergari.client.GUI.DataGameObserver;
import ingSw2018StaglianoVagagginiVergari.client.GUI.SceltaSchema;
import ingSw2018StaglianoVagagginiVergari.client.GUI.TransitionHandler;
import ingSw2018StaglianoVagagginiVergari.common.RemoteController;
import javafx.application.Platform;

import java.io.IOException;
import java.rmi.RemoteException;

public class PartecipaPartitaRequest extends Thread{

    private RemoteController controller;
    private String username;
    private DataGameObserver dataGameObserver;

    public PartecipaPartitaRequest(RemoteController controller, String username, DataGameObserver dataGameObserver){
        this.controller=controller;
        this.username=username;
        this.dataGameObserver=dataGameObserver;
    }


    @Override
    public void run() {
        try{
            controller.partecipaPartita(dataGameObserver, username);
        }catch(RemoteException ex){
            ex.printStackTrace();
        }catch(FullGameException ex){
            Platform.runLater(()->new AlertPopup().display("Attenzione", ex.getMessage()));
        }catch(InterruptedException ex){
            ex.printStackTrace();
        }

        synchronized (dataGameObserver) {
            if (dataGameObserver.isUsernameOK()){
                dataGameObserver.setUsername(username);
            }
            while (!dataGameObserver.isUpdated()) {
                try {
                    dataGameObserver.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            dataGameObserver.setUpdate(false);
        }
        try {
            TransitionHandler.setSceltaSchemaScene(new SceltaSchema());
        }catch (IOException ex){
            ex.printStackTrace();
        }
        Platform.runLater(()->TransitionHandler.toSceltaSchema());

    }
}

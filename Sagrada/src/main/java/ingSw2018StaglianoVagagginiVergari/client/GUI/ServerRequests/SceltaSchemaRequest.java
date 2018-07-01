package ingSw2018StaglianoVagagginiVergari.client.GUI.ServerRequests;

import ingSw2018StaglianoVagagginiVergari.client.GUI.*;
import ingSw2018StaglianoVagagginiVergari.common.RemoteController;
import javafx.application.Platform;

import java.io.IOException;
import java.rmi.RemoteException;

public class SceltaSchemaRequest extends Thread {

    private RemoteController controller;
    private boolean carta1;
    private boolean fronteScelto;
    private DataGameObserver dataGameObserver;

    public SceltaSchemaRequest(RemoteController controller, boolean carta1, boolean fronteScelto, DataGameObserver dataGameObserver){
        this.controller = controller;
        this.carta1=carta1;
        this.fronteScelto=fronteScelto;
        this.dataGameObserver=dataGameObserver;
    }

    @Override
    public void run() {
        try {
            controller.scegliSchema(dataGameObserver,dataGameObserver.getUsername(), carta1, fronteScelto);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        if (!dataGameObserver.isUpdated()) {
            synchronized (dataGameObserver) {
                while (!dataGameObserver.isUpdated()) {
                    try {
                        dataGameObserver.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        Platform.runLater(()->TransitionHandler.toGameScene());
    }
}

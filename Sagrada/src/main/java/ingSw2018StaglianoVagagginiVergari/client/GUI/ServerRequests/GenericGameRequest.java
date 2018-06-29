package ingSw2018StaglianoVagagginiVergari.client.GUI.ServerRequests;

import ingSw2018StaglianoVagagginiVergari.client.GUI.DataGameObserver;
import ingSw2018StaglianoVagagginiVergari.client.GUI.GUIStates.ToolCardStates.FactoryToolCardStates;
import ingSw2018StaglianoVagagginiVergari.client.GUI.GameScene;
import ingSw2018StaglianoVagagginiVergari.client.GUI.SagradaGUI;
import ingSw2018StaglianoVagagginiVergari.common.RemoteController;
import ingSw2018StaglianoVagagginiVergari.server.model.carteUtensile.DiluentePerPastaSalda;

import java.rmi.RemoteException;
import java.util.ArrayList;

public class GenericGameRequest extends Thread{

    private DataGameObserver dataGameObserver;

    private RemoteController controller;

    private GameScene gameScene;

    private ArrayList<Integer> parametri;

    public GenericGameRequest(DataGameObserver dataGameObserver, RemoteController controller, ArrayList<Integer> parametri, GameScene gameScene){
        this.parametri=parametri;
        this.controller=controller;
        this.dataGameObserver=dataGameObserver;
        this.gameScene = gameScene;
    }

    @Override
    public void run() {
        try {
            controller.svolgimentoPartita(dataGameObserver, parametri);
        }  catch (RemoteException e) {
            e.printStackTrace();
        }
    }
}

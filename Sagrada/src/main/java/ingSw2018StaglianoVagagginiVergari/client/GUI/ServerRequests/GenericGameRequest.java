package ingSw2018StaglianoVagagginiVergari.client.GUI.ServerRequests;

import ingSw2018StaglianoVagagginiVergari.client.GUI.DataGameObserver;
import ingSw2018StaglianoVagagginiVergari.client.GUI.GUIStates.ToolCardStates.FactoryToolCardStates;
import ingSw2018StaglianoVagagginiVergari.client.GUI.GameScene;
import ingSw2018StaglianoVagagginiVergari.client.GUI.SagradaGUI;
import ingSw2018StaglianoVagagginiVergari.common.RemoteController;

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
            if (parametri.get(0) == 2){
                if (dataGameObserver.isCartaUtilizzabile()){
                    FactoryToolCardStates toolCardStates = new FactoryToolCardStates(gameScene);
                    gameScene.setState(toolCardStates.getToolCardState(dataGameObserver.cartaUtensile(parametri.get(1))));
                }
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }
}

package ingSw2018StaglianoVagagginiVergari.client.GUI.ServerRequests;

import ingSw2018StaglianoVagagginiVergari.client.GUI.AlertPopup;
import ingSw2018StaglianoVagagginiVergari.client.GUI.DataGameObserver;
import ingSw2018StaglianoVagagginiVergari.client.GUI.SagradaGUI;
import ingSw2018StaglianoVagagginiVergari.client.GUI.TransitionHandler;
import ingSw2018StaglianoVagagginiVergari.common.RemoteController;
import ingSw2018StaglianoVagagginiVergari.common.RemoteMultiController;
import javafx.application.Platform;

import java.rmi.RemoteException;

public class LoginRequest extends Thread{

    private RemoteMultiController multiController;
    private RemoteController controller;
    private String username;
    private DataGameObserver dataGameObserver;

    public LoginRequest(RemoteMultiController multiController, String username, DataGameObserver dataGameObserver){

        this.multiController=multiController;
        this.username=username;
        this.dataGameObserver=dataGameObserver;

    }

    @Override
    public void run() {
        int login=0;
        try{
            controller=multiController.CercaController(username);
            if (controller!=null){
                SagradaGUI.getRequestHandler().getDataGameObserver().setUsername(username);
                SagradaGUI.getRequestHandler().setController(controller);
                login = controller.login(dataGameObserver, username);
            }else{
                SagradaGUI.getRequestHandler().getDataGameObserver().setUsername(null);
                Platform.runLater(()->TransitionHandler.toLoginScene());
                Platform.runLater(()->new AlertPopup().display("Attenzione", "NON SEI REGISTRATO A NESSUNA PARTITA"));
            }
        }catch(RemoteException ex){
            ex.printStackTrace();
        }

        if (login==1){
            SagradaGUI.getRequestHandler().getDataGameObserver().setUsername(username);
            Platform.runLater(()->TransitionHandler.toGameScene());
        }else{
            SagradaGUI.getRequestHandler().getDataGameObserver().setUsername(username);
            Platform.runLater(()->TransitionHandler.toSceltaSchema());
        }


    }
}

package ingSw2018StaglianoVagagginiVergari.client.GUI.ServerRequests;

import ingSw2018StaglianoVagagginiVergari.client.GUI.DataGameObserver;
import ingSw2018StaglianoVagagginiVergari.common.RemoteController;

import java.rmi.RemoteException;
import java.util.ArrayList;

public class UtilizzoCartaUtensileRequest extends Thread{

    private DataGameObserver dataGameObserver;

    private RemoteController controller;

    private ArrayList<Integer> parametri;

    public UtilizzoCartaUtensileRequest(DataGameObserver dataGameObserver, RemoteController controller, ArrayList<Integer> parametri){
        this.parametri=parametri;
        this.controller=controller;
        this.dataGameObserver=dataGameObserver;
    }

    @Override
    public void run() {
        try {
            controller.usaCartaUtensile(dataGameObserver, parametri);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }
}

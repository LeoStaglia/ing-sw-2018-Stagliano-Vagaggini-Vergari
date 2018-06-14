package ingSw2018StaglianoVagagginiVergari.server.Controller;

import ingSw2018StaglianoVagagginiVergari.common.RemoteMultiController;
import ingSw2018StaglianoVagagginiVergari.server.model.Utente;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;


public class MultiController extends UnicastRemoteObject implements RemoteMultiController {
    ArrayList<Controller> controllers;
    public MultiController() throws RemoteException {
         controllers=new ArrayList<>();
    }

    public Controller AssegnaController() throws RemoteException {
        for (Controller c: controllers) {
            ControllerStatus status;
            status= ControllerStatus.AggiuntaObserver;
            if(c.getStatus()==status){
                return c;
            }
        }
        Controller controller=new Controller();
        controllers.add(controller);
        return controller;
    }


    public Controller CercaController(String username) throws RemoteException {
        for (Controller controller : controllers) {
            for (Utente u: controller.getPartita().getListaGiocatori()) {
                if (u.getId().equals(username)) return controller;
            }

        }
        return null;
    }
}

package ingSw2018StaglianoVagagginiVergari.server.Controller;

import ingSw2018StaglianoVagagginiVergari.common.RemoteMultiController;
import ingSw2018StaglianoVagagginiVergari.server.model.Utente;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;


public class MultiController extends UnicastRemoteObject implements RemoteMultiController {
    ArrayList<Controller> controllers;
    private int timerTurn;
    private int joinTimer;
    private int schemeTimer;
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
        if(timerTurn!=0) {
            controller.setTimerTurn(timerTurn);
        }else controller.setTimerTurn(20000);
        if(joinTimer!=0){
            controller.setJoinTimer(joinTimer);
        }else controller.setJoinTimer(6000);
        if(schemeTimer!=0){
            controller.setSchemeTimer(schemeTimer);
        }else controller.setSchemeTimer(8000);
        controllers.add(controller);
        return controller;
    }


    public Controller CercaController(String username) throws RemoteException {
        for (Controller controller : controllers) {
            if (controller.getStatus() == ControllerStatus.AggiuntaObserver) {
                for (String user : controller.getPartita().getGameObservers().keySet()) {
                    if (username.equals(user)) return controller;
                }
            } else {
                for (Utente u : controller.getPartita().getListaGiocatori()) {
                    if (u.getId().equals(username)) return controller;
                }

            }
        }
            return null;

    }

    public void setTimerTurn(int timerTurn) {
        this.timerTurn = timerTurn;
    }

    public void setJoinTimer(int joinTimer) {
        this.joinTimer = joinTimer;
    }

    public void setSchemeTimer(int schemeTimer) {
        this.schemeTimer = schemeTimer;
    }
}

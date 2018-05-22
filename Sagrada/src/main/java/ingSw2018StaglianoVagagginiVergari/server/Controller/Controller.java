package ingSw2018StaglianoVagagginiVergari.server.Controller;


import Eccezioni.FullGameException;
import ingSw2018StaglianoVagagginiVergari.common.GameObserver;
import ingSw2018StaglianoVagagginiVergari.common.RemoteController;
import ingSw2018StaglianoVagagginiVergari.server.model.Partita;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class Controller extends UnicastRemoteObject implements RemoteController {

    private Partita partita;

    private Timer t;

    private ControllerStatus status;

    public Controller() throws RemoteException {
        partita = new Partita();
        status = ControllerStatus.Preparazione;
        t= new Timer();
    }

    public synchronized void partecipaPartita(GameObserver view) throws InterruptedException, RemoteException, FullGameException{
        if (status==ControllerStatus.AggiuntaObserver) {
            if (partita.numberOfObserver() == 4) {
                throw new FullGameException("The game is full");
            }

            partita.addObserver(view);

            if (partita.numberOfObserver() == 2) {
                    t.schedule(new TimerTask() {
                        @Override
                        public void run() {
                            try {
                                if (getStatus()==ControllerStatus.AggiuntaObserver) {
                                    setStatus(ControllerStatus.Preparazione);
                                    partita.preparaPartita();
                                    setStatus(ControllerStatus.SceltaSchema);
                                }
                            } catch (RemoteException e) {
                                e.printStackTrace();
                            }
                        }
                    }, 2000);

                }else if (partita.numberOfObserver() == 4) {
                    setStatus(ControllerStatus.Preparazione);
                    t.cancel();
                    partita.preparaPartita();
                    setStatus(ControllerStatus.SceltaSchema);
                }
        }else{
            throw new FullGameException("La partita è già in corso.");
        }
    }
    public synchronized void abbandonaPartita(GameObserver view){
        if (getStatus()==ControllerStatus.AggiuntaObserver) {
            partita.removeObserver(view);
            if (partita.numberOfObserver() < 2) {
                t.cancel();
            }
        }
    }

    //Fase di scelta dello schema, il controller riceve la view e l'id, setta la faccia della carta schema scelta.
    public void scegliSchema(GameObserver view, String idUser, boolean fronte){
        partita.sceltaSchema(view, idUser, fronte);

    }


    public synchronized void setStatus(ControllerStatus status) {
        this.status = status;
    }
    public synchronized ControllerStatus getStatus(){
        return status;
    }

}


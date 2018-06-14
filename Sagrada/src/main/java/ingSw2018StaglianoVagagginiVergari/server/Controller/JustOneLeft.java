package ingSw2018StaglianoVagagginiVergari.server.Controller;

import ingSw2018StaglianoVagagginiVergari.server.model.Partita;

import java.rmi.RemoteException;

public class JustOneLeft extends Thread{
    private Partita partita;
    private Controller controller;

    public JustOneLeft(Partita partita,Controller controller){
        this.partita=partita;
        this.controller=controller;

    }

    @Override
    public void run() {
        while(true){
            if(partita.getGameObservers().size()==1) {
                controller.getT().cancel();
                try {
                    partita.calcolaPunteggioFinale();
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
                break;
            }
            }
            return;
        }
    }

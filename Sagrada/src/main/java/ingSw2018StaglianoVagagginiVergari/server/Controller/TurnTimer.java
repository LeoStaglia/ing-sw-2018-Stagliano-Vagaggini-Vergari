package ingSw2018StaglianoVagagginiVergari.server.Controller;

import ingSw2018StaglianoVagagginiVergari.common.GameObserver;
import ingSw2018StaglianoVagagginiVergari.server.model.Partita;

import java.io.IOException;
import java.rmi.RemoteException;
import java.util.TimerTask;

public class TurnTimer extends TimerTask{
    Controller controller;
    Partita partita;
    public TurnTimer(Controller controller, Partita partita){
      this.partita=partita;
      this.controller=controller;
    }
    @Override
    public void run() {
        try {
            GameObserver currentObserver = partita.getGameObservers().get(partita.getCurrentPlayer().getId());
            controller.passaTurno(partita.getGameObservers().get(partita.getCurrentPlayer().getId()));
            if (partita.getGameObservers().size() > 0) {
                if (currentObserver!=null) {
                    try {
                        currentObserver.notifyTurnTimer();
                    }catch(IOException ex){

                    }
                }

            }


        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }
}

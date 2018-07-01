package ingSw2018StaglianoVagagginiVergari.server.Controller;

import com.sun.tools.internal.xjc.reader.xmlschema.bindinfo.BIConversion;
import ingSw2018StaglianoVagagginiVergari.common.GameObserver;
import ingSw2018StaglianoVagagginiVergari.server.model.Partita;
import ingSw2018StaglianoVagagginiVergari.server.model.Utente;

import java.io.IOException;
import java.rmi.RemoteException;
import java.util.TimerTask;

public class SchemeTimer extends TimerTask {
    Controller controller;
    Partita partita;

    public SchemeTimer(Controller controller, Partita partita){
        this.partita=partita;
        this.controller=controller;
    }
    @Override
    public void run() {

        for (Utente u: partita.getListaGiocatori()){
            if(u.getPlancia().getCartaSchema()==null){
                GameObserver view= partita.getGameObservers().get(u.getId());
                try {
                    controller.scegliSchema(view,u.getId(),true,true);
                    view.notifySchemeTimer();
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        }


    }
}

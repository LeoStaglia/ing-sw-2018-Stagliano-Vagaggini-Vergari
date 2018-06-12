package ingSw2018StaglianoVagagginiVergari.server.Controller;

import ingSw2018StaglianoVagagginiVergari.client.View;
import ingSw2018StaglianoVagagginiVergari.common.GameObserver;
import ingSw2018StaglianoVagagginiVergari.server.model.Partita;
import ingSw2018StaglianoVagagginiVergari.server.model.Utente;

import java.rmi.RemoteException;
import java.util.HashMap;

public class Pinger extends Thread {

    private Partita partita;
    private Controller controller;
    private HashMap<GameObserver, Integer> pingFailed;

    public Pinger(Partita partita, Controller controller){
        this.partita=partita;
        this.controller=controller;
        pingFailed=new HashMap<>();
        for (Utente u: partita.getListaGiocatori()){
            pingFailed.put(partita.getGameObservers().get(u.getId()), 0);
        }
    }

    @Override
    public void run() {
        while (true) {

            HashMap<String, GameObserver> gameObserverClone = new HashMap<>();

            gameObserverClone = (HashMap<String, GameObserver>) partita.getGameObservers().clone();
            for (String username : gameObserverClone.keySet()) {
                if (partita.pingClient(partita.getGameObservers().get(username))) {
                    pingFailed.replace(partita.getGameObservers().get(username), 0);
                } else {
                    int n = pingFailed.get(partita.getGameObservers().get(username));
                    n++;
                    pingFailed.replace(partita.getGameObservers().get(username), n);
                }
                if (pingFailed.get(partita.getGameObservers().get(username))==3){
                    partita.removeObserver(username);
                }
            }

        }
    }

}

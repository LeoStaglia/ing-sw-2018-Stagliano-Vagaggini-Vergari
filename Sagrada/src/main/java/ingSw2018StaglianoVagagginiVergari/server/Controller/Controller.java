package ingSw2018StaglianoVagagginiVergari.server.Controller;


import Eccezioni.FullGameException;
import Eccezioni.MossaIllegaleException;
import ingSw2018StaglianoVagagginiVergari.common.GameObserver;
import ingSw2018StaglianoVagagginiVergari.common.RemoteController;
import ingSw2018StaglianoVagagginiVergari.server.model.CartaUtensile;
import ingSw2018StaglianoVagagginiVergari.server.model.Partita;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.*;

/* import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;*/

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
    public void scegliSchema(GameObserver view, String idUser,boolean carta1, boolean fronte){
        partita.sceltaSchema(view, idUser, fronte);

    }



    Set<Integer> azioniGiocatore= new HashSet<Integer>();

    public void inizializzaAzioniGiocatore(){
        for(int i=0;i<3;i++){
            azioniGiocatore.add(i);
        }
    }

    public synchronized void svolgimentoPartita(GameObserver view,ArrayList<Integer> parametri) throws MossaIllegaleException {
        if (azioniGiocatore.contains(parametri.get(0))) {
            int n=parametri.get(0);
            if (n == 0) this.piazzaDado(view,parametri.get(1),parametri.get(2));
            if (n == 1) this.scegliCartaUtensile(view,parametri.get(1));
            if (n == 2) this.passaTurno(view);
            azioniGiocatore.remove(parametri.get(0));
        }


    }
    public synchronized void piazzaDado(GameObserver view, int i,int j) throws MossaIllegaleException{
        partita.getCurrentPlayer().getPlancia().piazzaDado(i,j,partita.getDadoSelezionato());
    }


    public synchronized void scegliCartaUtensile(GameObserver view,int k) {
        if (partita.getListaCartaUtensile().get(k).getCosto()<= partita.getCurrentPlayer().getSegnalini()) {
            partita.getCurrentPlayer().setSegnalini(partita.getCurrentPlayer().getSegnalini() - partita.getListaCartaUtensile().get(k).getCosto());
            // TODO  da segnalare

            switch (partita.getListaCartaUtensile().get(k).getId()) {
                case 1:
                    setStatus(ControllerStatus.CartaU1);
                    break;
                case 2:
                    setStatus(ControllerStatus.CartaU2);
                    break;
                case 3:
                    setStatus(ControllerStatus.CartaU3);
                    break;
                case 4:
                    setStatus(ControllerStatus.CartaU4);
                    break;
                case 5:
                    setStatus(ControllerStatus.CartaU5);
                    break;
                case 6:
                    setStatus(ControllerStatus.CartaU6);
                    break;
                case 7:
                    setStatus(ControllerStatus.CartaU7);
                    break;
                case 8:
                    setStatus(ControllerStatus.CartaU8);
                    break;
                case 9:
                    setStatus(ControllerStatus.CartaU9);
                    break;
                case 10:
                    setStatus(ControllerStatus.CartaU10);
                    break;
                case 11:
                    setStatus(ControllerStatus.CartaU11);
                    break;
                case 12:
                    setStatus(ControllerStatus.CartaU12);
                    break;
            }
        }
    }


    public synchronized void usaCartaUtensile(GameObserver view,ArrayList<Integer> parametri){
        switch (status) {
            case CartaU1:
                for (CartaUtensile u : partita.getListaCartaUtensile()) {
                    if (u.getId() == 1) {
                        u.usaEffettoCarta(partita);
                    }
                }

                break;
            case CartaU2:
                for (CartaUtensile u : partita.getListaCartaUtensile()) {
                    if (u.getId() == 2) {
                        u.usaEffettoCarta(partita);
                    }
                }
                break;
            case CartaU3:
                for (CartaUtensile u : partita.getListaCartaUtensile()) {
                    if (u.getId() == 3) {
                        u.usaEffettoCarta(partita);
                    }
                }
                break;
            case CartaU4:
                for (CartaUtensile u : partita.getListaCartaUtensile()) {
                    if (u.getId() == 4) {
                        u.usaEffettoCarta(partita);
                    }
                }
                break;
            case CartaU5:
                for (CartaUtensile u : partita.getListaCartaUtensile()) {
                    if (u.getId() == 5) {
                        u.usaEffettoCarta(partita);
                    }
                }
                break;
            case CartaU6:
                for (CartaUtensile u : partita.getListaCartaUtensile()) {
                    if (u.getId() == 6) {
                        u.usaEffettoCarta(partita);
                    }
                }
                break;
            case CartaU7:
                for (CartaUtensile u : partita.getListaCartaUtensile()) {
                    if (u.getId() == 7) {
                        u.usaEffettoCarta(partita);
                    }
                }
                break;
            case CartaU8:
                for (CartaUtensile u : partita.getListaCartaUtensile()) {
                    if (u.getId() == 8) {
                        u.usaEffettoCarta(partita);
                    }
                }
                break;
            case CartaU9:
                for (CartaUtensile u : partita.getListaCartaUtensile()) {
                    if (u.getId() == 9) {
                        u.usaEffettoCarta(partita);
                    }
                }
                break;
            case CartaU10:
                for (CartaUtensile u : partita.getListaCartaUtensile()) {
                    if (u.getId() == 10) {
                        u.usaEffettoCarta(partita);
                    }
                }
                break;
            case CartaU11:
                for (CartaUtensile u : partita.getListaCartaUtensile()) {
                    if (u.getId() == 11) {
                        u.usaEffettoCarta(partita);
                    }
                }
                break;
            case CartaU12:
                for (CartaUtensile u : partita.getListaCartaUtensile()) {
                    if (u.getId() == 12) {
                        u.usaEffettoCarta(partita);
                    }
                }

                break;

            default:
                setStatus(ControllerStatus.SvolgimentoPartita);
                break;

        }
        setStatus(ControllerStatus.SvolgimentoPartita);

    }



    public synchronized void passaTurno(GameObserver view) {
        inizializzaAzioniGiocatore();
        if (partita.getTurno() < 10) partita.incrementaTurno();
        else {
            if (partita.getTracciatoDelRound().getRoundAttuale() < 10) {
                partita.reInizializzaTurno();
                partita.setOrdineRound();
                partita.nextRound();
            } else { setStatus(ControllerStatus.CalcoloPunteggio);
                partita.calcolaPunteggioFinale();                                               }

        }

    }



    public synchronized void setStatus(ControllerStatus status) {
        this.status = status;
    }
    public synchronized ControllerStatus getStatus(){
        return status;
    }

}


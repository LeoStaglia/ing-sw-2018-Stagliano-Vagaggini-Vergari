package ingSw2018StaglianoVagagginiVergari.server.Controller;


import Eccezioni.FullGameException;
import Eccezioni.MossaIllegaleException;
import ingSw2018StaglianoVagagginiVergari.common.GameObserver;
import ingSw2018StaglianoVagagginiVergari.common.RemoteController;
import ingSw2018StaglianoVagagginiVergari.server.model.CartaUtensile;
import ingSw2018StaglianoVagagginiVergari.server.model.Partita;
import ingSw2018StaglianoVagagginiVergari.server.model.carteUtensile.*;

import java.rmi.Remote;
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

    private boolean timerSetted;

    public Controller() throws RemoteException {
        partita = new Partita();
        status = ControllerStatus.AggiuntaObserver;
    }

    public synchronized void partecipaPartita(GameObserver view) throws InterruptedException, RemoteException, FullGameException{
        if (status==ControllerStatus.AggiuntaObserver) {
            if (partita.numberOfObserver() == 4) {
                throw new FullGameException("The game is full");
            }

            partita.addObserver(view);

            if (partita.numberOfObserver() == 2) {
                    t = new Timer();
                    t.schedule(new TimerTask() {
                        @Override
                        public void run() {
                            try {
                                if (getStatus()==ControllerStatus.AggiuntaObserver) {
                                    setStatus(ControllerStatus.Preparazione);
                                    partita.preparaPartita();
                                    setStatus(ControllerStatus.SceltaSchema);
                                    System.out.println("preparazione partita effettuata");
                                }
                            } catch (RemoteException e) {
                                e.printStackTrace();
                            }
                        }
                    }, 6000);
                timerSetted=true;

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
            if (partita.numberOfObserver() < 2 && timerSetted) {
                t.cancel();
                timerSetted=false;
            }
        }
    }

    //Fase di scelta dello schema, il controller riceve la view e l'id, setta la faccia della carta schema scelta.
    public void scegliSchema(GameObserver view, String idUser,boolean carta1, boolean fronte) throws RemoteException{
        partita.sceltaSchema(view, idUser,carta1, fronte);
        if(partita.contaSchemi())  setStatus(ControllerStatus.SvolgimentoPartita);
    }



    Set<Integer> azioniGiocatore= new HashSet<Integer>();

    public void inizializzaAzioniGiocatore(){
        for(int i=1;i<=3;i++){
            azioniGiocatore.add(i);
        }
    }

    public synchronized void svolgimentoPartita(GameObserver view,ArrayList<Integer> parametri) throws MossaIllegaleException, RemoteException {
        if (azioniGiocatore.contains(parametri.get(0))) {
            int n=parametri.get(0);
            if (n == 1) {
                partita.setDadoSelezionato(partita.getRiserva().get(parametri.get(1)));
                this.piazzaDado(view,parametri.get(2),parametri.get(3));
            }
            if (n == 2) this.scegliCartaUtensile(view,parametri.get(1));
            if (n == 3) this.passaTurno(view);
            azioniGiocatore.remove(parametri.get(0));
        }


    }
    public synchronized void piazzaDado(GameObserver view, int i,int j) throws MossaIllegaleException {

        partita.getCurrentPlayer().getPlancia().calcolaMosse(partita.getDadoSelezionato(), false, false);
        partita.getCurrentPlayer().getPlancia().piazzaDado(i, j, partita.getDadoSelezionato());


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
                        PinzaSgrossatrice carta= (PinzaSgrossatrice)u;
                        carta.setScelta(parametri.get(0));
                        u.usaEffettoCarta(partita);
                    }
                }

                break;
            case CartaU2:
                for (CartaUtensile u : partita.getListaCartaUtensile()) {
                    if (u.getId() == 2) {
                        PennelloPerEglomise carta= (PennelloPerEglomise) u;
                        u.usaEffettoCarta(partita);
                    }
                }
                break;
            case CartaU3:
                for (CartaUtensile u : partita.getListaCartaUtensile()) {
                    if (u.getId() == 3) {
                        AlesatoreRame carta= (AlesatoreRame) u;
                        u.usaEffettoCarta(partita);
                    }
                }
                break;
            case CartaU4:
                for (CartaUtensile u : partita.getListaCartaUtensile()) {
                    if (u.getId() == 4) {
                        Lathekin carta= (Lathekin) u;
                        carta.setxCell(parametri.get(0));
                        carta.setyCell(parametri.get(1));
                        carta.setxDie(parametri.get(2));
                        carta.setyDie(parametri.get(3));
                        u.usaEffettoCarta(partita);
                    }
                }
                break;
            case CartaU5:
                for (CartaUtensile u : partita.getListaCartaUtensile()) {
                    if (u.getId() == 5) {
                        TaglierinaCircolare carta= (TaglierinaCircolare) u;
                        carta.setI(parametri.get(0));
                        u.usaEffettoCarta(partita);
                    }
                }
                break;
            case CartaU6:
                for (CartaUtensile u : partita.getListaCartaUtensile()) {
                    if (u.getId() == 6) {
                        PennelloPastaSalda carta= (PennelloPastaSalda) u;
                        carta.setxCell(parametri.get(0));
                        carta.setyCell(parametri.get(1));
                        u.usaEffettoCarta(partita);
                    }
                }
                break;
            case CartaU7:
                for (CartaUtensile u : partita.getListaCartaUtensile()) {
                    if (u.getId() == 7) {
                        Martelletto carta= (Martelletto) u;
                        u.usaEffettoCarta(partita);
                    }
                }
                break;
            case CartaU8:
                for (CartaUtensile u : partita.getListaCartaUtensile()) {
                    if (u.getId() == 8) {
                        TenagliaARotelle carta= (TenagliaARotelle) u;
                        u.usaEffettoCarta(partita);
                    }
                }
                break;
            case CartaU9:
                for (CartaUtensile u : partita.getListaCartaUtensile()) {
                    if (u.getId() == 9) {
                        RigaInSughero carta= (RigaInSughero) u;
                        u.usaEffettoCarta(partita);
                    }
                }
                break;
            case CartaU10:
                for (CartaUtensile u : partita.getListaCartaUtensile()) {
                    if (u.getId() == 10) {
                        TamponeDiamantato carta= (TamponeDiamantato) u;
                        u.usaEffettoCarta(partita);
                    }
                }
                break;
            case CartaU11:
                for (CartaUtensile u : partita.getListaCartaUtensile()) {
                    if (u.getId() == 11) {
                        DiluentePerPastaSalda carta= (DiluentePerPastaSalda) u;
                        u.usaEffettoCarta(partita);
                    }
                }
                break;
            case CartaU12:
                for (CartaUtensile u : partita.getListaCartaUtensile()) {
                    if (u.getId() == 12) {
                        TaglierinaManuale carta=(TaglierinaManuale) u;
                        carta.setR(parametri.get(0));
                        carta.setI(parametri.get(1));
                        carta.setJ(parametri.get(2));
                        carta.setX(parametri.get(3));
                        carta.setY(parametri.get(4));

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


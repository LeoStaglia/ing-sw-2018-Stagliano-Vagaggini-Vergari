package ingSw2018StaglianoVagagginiVergari.server.Controller;


import Eccezioni.FullGameException;
import Eccezioni.MossaIllegaleException;
import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;
import ingSw2018StaglianoVagagginiVergari.common.GameObserver;
import ingSw2018StaglianoVagagginiVergari.common.RemoteController;
import ingSw2018StaglianoVagagginiVergari.server.model.CartaUtensile;
import ingSw2018StaglianoVagagginiVergari.server.model.Partita;
import ingSw2018StaglianoVagagginiVergari.server.model.Utente;
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

    public synchronized void partecipaPartita(GameObserver view, String username) throws InterruptedException, RemoteException, FullGameException{
        if (status==ControllerStatus.AggiuntaObserver) {
            if (partita.numberOfObserver() == 4) {
                throw new FullGameException("The game is full");
            }

            partita.addObserver(view, username);

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
    public synchronized void abbandonaPartita(GameObserver view, String username) throws RemoteException {
        if (getStatus()==ControllerStatus.AggiuntaObserver) {
            partita.removeObserver(username);
            if (partita.numberOfObserver() < 2 && timerSetted) {
                t.cancel();
                timerSetted=false;
            }
        }
    }

    //Fase di scelta dello schema, il controller riceve la view e l'id, setta la faccia della carta schema scelta.
    public void scegliSchema(GameObserver view, String idUser,boolean carta1, boolean fronte) throws RemoteException{
        partita.sceltaSchema(view, idUser,carta1, fronte);
        if(partita.contaSchemi()) {
            setStatus(ControllerStatus.SvolgimentoPartita);
             new Thread(new Runnable() { //deamon that checks the connection with clients
                @Override
                public void run() {
                    while (true) {
                        HashMap<String, GameObserver> gameObserverClone = new HashMap<>();
                        gameObserverClone=(HashMap<String, GameObserver>)partita.getGameObservers().clone();
                        for (String username : gameObserverClone.keySet()) {
                            if (partita.pingClient(partita.getGameObservers().get(username))){

                            }else{
                                try {
                                    partita.removeObserver(username);
                                } catch (RemoteException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    }
                }
            }).start();
        }

        partita.inizializzaAzioniGiocatore();
    }







    public synchronized void svolgimentoPartita(GameObserver view,ArrayList<Integer> parametri) throws MossaIllegaleException, RemoteException {
        for (Utente u: partita.getOrdineRound()) {
            System.out.println(u.getId());
        }

        if (partita.getAzioniGiocatore().contains(parametri.get(0))|| partita.getAzioniGiocatore().contains(4)) {
            int n=parametri.get(0);
            if(partita.getAzioniGiocatore().contains(4)&&!(partita.getAzioniGiocatore().contains(1))){
                partita.getAzioniGiocatore().remove(4);
                n=4;
            }
            else partita.getAzioniGiocatore().remove(parametri.get(0));
            if (n == 1 || n==4) {
                partita.setDadoSelezionato(parametri.get(1));
                partita.piazzamentoDado(parametri.get(2), parametri.get(3), false, false);
            }
            if (n == 2) this.scegliCartaUtensile(view,parametri.get(1));
            if (n == 3) this.passaTurno(view);

        }


    }
    /*public synchronized void piazzaDado(GameObserver view, int i,int j) throws MossaIllegaleException {

        partita.getCurrentPlayer().getPlancia().calcolaMosse(partita.getDadoSelezionato(), false, false);
        partita.getCurrentPlayer().getPlancia().piazzaDado(i, j, partita.getDadoSelezionato());


    }*/

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


    public synchronized void usaCartaUtensile(GameObserver view,ArrayList<Integer> parametri) throws RemoteException{
        switch (status) {
            case CartaU1:
                for (CartaUtensile u : partita.getListaCartaUtensile()) {
                    if (u.getId() == 1) {
                        partita.setDadoSelezionato(parametri.get(0));
                        PinzaSgrossatrice carta= (PinzaSgrossatrice)u;
                        carta.setScelta(parametri.get(1));
                        carta.usaEffettoCarta(partita);
                    }
                }

                break;
            case CartaU2:
                for (CartaUtensile u : partita.getListaCartaUtensile()) {
                    if (u.getId() == 2) {
                        PennelloPerEglomise carta= (PennelloPerEglomise) u;
                        carta.setxDie(parametri.get(0));
                        carta.setyDie(parametri.get(1));
                        carta.setxCell(parametri.get(2));
                        carta.setyCell(parametri.get(3));
                        carta.usaEffettoCarta(partita);
                    }
                }
                break;
            case CartaU3:
                for (CartaUtensile u : partita.getListaCartaUtensile()) {
                    if (u.getId() == 3) {
                        AlesatoreRame carta= (AlesatoreRame) u;
                        carta.setxDie(parametri.get(0));
                        carta.setyDie(parametri.get(1));
                        carta.setxCell(parametri.get(2));
                        carta.setyCell(parametri.get(3));
                        carta.usaEffettoCarta(partita);
                    }
                }
                break;
            case CartaU4:
                for (CartaUtensile u : partita.getListaCartaUtensile()) {
                    if (u.getId()== 4) {
                        Lathekin carta= (Lathekin) u;
                        carta.setxDie(parametri.get(0));
                        carta.setyDie(parametri.get(1));
                        carta.setxCell(parametri.get(2));
                        carta.setyCell(parametri.get(3));
                        carta.usaEffettoCarta(partita);
                    }
                }
                break;
            case CartaU5:
                for (CartaUtensile u : partita.getListaCartaUtensile()) {
                    if (u.getId() == 5) {
                        TaglierinaCircolare carta= (TaglierinaCircolare) u;
                        partita.setDadoSelezionato(parametri.get(0));
                        carta.setI(parametri.get(1));
                        carta.usaEffettoCarta(partita);
                    }
                }
                break;
            case CartaU6:
                 for (CartaUtensile u : partita.getListaCartaUtensile()) {
                    if (u.getId() == 6) {
                        if (partita.getAzioniGiocatore().contains(1)) {
                            PennelloPastaSalda carta = (PennelloPastaSalda)u;
                            if (carta.isFase1()) {
                                partita.setDadoSelezionato(parametri.get(0));
                                try{
                                    carta.usaEffettoCarta(partita);
                                }catch(MossaIllegaleException e){

                                }
                                if (carta.isPiazzabile())
                                    return;
                            } else {
                                if (partita.getAzioniGiocatore().contains(1)) {
                                    partita.getAzioniGiocatore().remove(1);
                                }
                                carta.setxCell(parametri.get(0));
                                carta.setyCell(parametri.get(1));
                                try {
                                    carta.usaEffettoCarta(partita);
                                } catch (MossaIllegaleException e) {
                                    partita.getAzioniGiocatore().add(1);
                                }

                            }
                        }else{
                            partita.updateGenerale();
                        }
                    }
                 }
                break;
            case CartaU7:
                 for (CartaUtensile u : partita.getListaCartaUtensile()) {
                     if (u.getId()==7) {
                         Martelletto carta = (Martelletto) partita.getListaCartaUtensile().get(0);
                         carta.usaEffettoCarta(partita);
                     }
                 }
                break;
            case CartaU8:
               for (CartaUtensile u : partita.getListaCartaUtensile()) {
                    if (u.getId() == 8) {
                        TenagliaARotelle carta= (TenagliaARotelle) u;
                        carta.usaEffettoCarta(partita);
                    }
               }
                break;
            case CartaU9:
                for (CartaUtensile u : partita.getListaCartaUtensile()) {
                    if (u.getId() == 9) {
                        RigaInSughero carta= (RigaInSughero) u;
                        partita.setDadoSelezionato(parametri.get(0));
                        carta.setxCell(parametri.get(1));
                        carta.setyCell(parametri.get(2));
                        carta.usaEffettoCarta(partita);
                    }
                }
                break;
            case CartaU10:
               for (CartaUtensile u : partita.getListaCartaUtensile()) {
                    if (u.getId()== 10) {
                        TamponeDiamantato carta= (TamponeDiamantato)u;
                        carta.setScelta(parametri.get(0));
                        carta.usaEffettoCarta(partita);
                    }
                }
                break;
            case CartaU11:
                for (CartaUtensile u : partita.getListaCartaUtensile()) {
                    if (u.getId() == 11) {
                        DiluentePerPastaSalda carta= (DiluentePerPastaSalda) u;
                        carta.setScelta(parametri.get(0));
                        carta.setNumeroScelto(parametri.get(1));
                        carta.setX(parametri.get(2));
                        carta.setY(parametri.get(3));

                        carta.usaEffettoCarta(partita);
                    }
                }
                break;
            case CartaU12:
                for (CartaUtensile u : partita.getListaCartaUtensile()) {
                    if (partita.getListaCartaUtensile().get(0).getId()==12) {
                        TaglierinaManuale carta = (TaglierinaManuale) partita.getListaCartaUtensile().get(0);
                        carta.setR(parametri.get(0));
                        carta.setNumeroDadi(parametri.get(1));carta.setI(parametri.get(2));
                        carta.setJ(parametri.get(3));
                        carta.setX(parametri.get(4));
                        carta.setY(parametri.get(5));

                        carta.usaEffettoCarta(partita);

                    }
                }

                break;

            default:
                setStatus(ControllerStatus.SvolgimentoPartita);
                break;

        }
        setStatus(ControllerStatus.SvolgimentoPartita);

    }



    public synchronized void passaTurno(GameObserver view) throws RemoteException {
        partita.inizializzaAzioniGiocatore();
        if(partita.getTurno()==partita.getOrdineRound().size()){
            if (partita.getTracciatoDelRound().getRoundAttuale() < 10) {
                partita.nextRound();
            } else { setStatus(ControllerStatus.CalcoloPunteggio);
                partita.calcolaPunteggioFinale();                                               }

        }
        else partita.incrementaTurno();


    }

    @Override
    public int login(GameObserver view, String username, String token) throws RemoteException {
        if (partita.getUserTokens().keySet().contains(username)){
            if (partita.getUserTokens().get(username).equals(token)){
                if (partita.replaceObserver(username, view)){
                    return 1;
                }else{
                    return 2;
                }

            }else{
                return 0;
            }
        }else{
           return 0;
        }
    }

    public synchronized void setStatus(ControllerStatus status) {
        this.status = status;
    }
    public synchronized ControllerStatus getStatus(){
        return status;
    }

}


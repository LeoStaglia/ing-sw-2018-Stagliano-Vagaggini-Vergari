package ingSw2018StaglianoVagagginiVergari.server.model;

import Eccezioni.MossaIllegaleException;
import ingSw2018StaglianoVagagginiVergari.common.GameObserver;
import ingSw2018StaglianoVagagginiVergari.common.RemoteController;
import ingSw2018StaglianoVagagginiVergari.server.model.carteSchema.CartaSchema;
import ingSw2018StaglianoVagagginiVergari.server.model.carteSchema.FactorySchema;
import ingSw2018StaglianoVagagginiVergari.server.model.carteSchema.Schema;

import java.io.IOException;
import java.rmi.RemoteException;
import java.util.*;
import java.util.concurrent.TimeUnit;

public class Partita {

    private HashMap<String, GameObserver> gameObservers;
   // private HashMap<String, String> userTokens;
    private Dado dadoSelezionato = null;
    private ArrayList<String> setOfColors = new ArrayList<String>();
    private ArrayList<Dado> riserva = new ArrayList<Dado>();
    private ArrayList<Dado> sacchetto = new ArrayList<Dado>();

    private ArrayList<Utente> listaGiocatori = new ArrayList<Utente>();
    private Utente giocatoreCorrente;  // currentPlayer

    ArrayList<Utente> ordineRound = new ArrayList<Utente>();  // mantiene l'ordine del round

    private int turno = 1;
    private TracciatoDelRound tracciatoDelRound = new TracciatoDelRound();
    private HashMap<String, Integer> listaPunteggiUtente = new HashMap<String, Integer>();
    private HashMap<String, Integer> listaPunteggiUtenteObiettivoPrivato = new HashMap<String, Integer>();

    private HashMap<Utente, Coppia<Schema, Schema>> scelteSchemi = new HashMap<>();
    private ArrayList<Integer> mazzoCarteUtensile = new ArrayList<Integer>();
    private ArrayList<Integer> mazzoCarteObiettivoPubblico = new ArrayList<Integer>();
    private ArrayList<CartaUtensile> listaCartaUtensile = new ArrayList<CartaUtensile>();
    private ArrayList<CartaObiettivoPubblico> listaCartaObiettivoPubblico = new ArrayList<CartaObiettivoPubblico>();
    private ArrayList<Plancia> listaPlance = new ArrayList<>();
    private ArrayList<Constraint> listaObiettiviPrivati = new ArrayList<>();
    private ArrayList<Schema> listaCarteSchema = new ArrayList<>();
    private Random random = new Random();

    HashSet<Integer> azioniGiocatore = new HashSet<Integer>();

    //  private String idPartita;   it is needed only in case of multiple games


    // public ArrayList<Dado> getDadiFromSacchetto(int n){}  // n is equal to the number of dice I want to get

    public Partita() {
        riempiSetofColors();
        riempiSacchetto();
       // Utente.inizializzaTokenSet();
        inizializzaCarteSchema();
        inizializzaAzioniGiocatore();
        inizializzaMazzoCarteUtensile();
        inizializzaMazzoCarteObiettivoPubblico();
        inizializzaPlance();
        inizializzaObiettiviPrivati();
        gameObservers = new HashMap<>();
       // userTokens = new HashMap<>();
    }

    public boolean replaceObserver(String username, GameObserver view) throws RemoteException {
        //TODO rendere parametrico l'update su un ArrayList di view.
        if (gameObservers.get(username) != null) {
            GameObserver oldView = gameObservers.get(username);
            if (pingClient(oldView)) {
                oldView.notifyExit();
            }
            removeObserver(username);
        }
        gameObservers.put(username, view);
        for (Utente u : listaGiocatori) {
            if (u.getId().equals(username)) {
                if (u.getPlancia().getCartaSchema() != null) {

                    HashMap<String, String[][]> planceGiocatori = new HashMap<>();
                    for (Utente utente : listaGiocatori) {
                        planceGiocatori.put(utente.getId(), utente.getPlancia().rappresentazionePlancia());
                    }

                    ArrayList<String> listCartaUtensile = new ArrayList<>();


                    HashMap<String, Integer> segnaliniGiocatori = new HashMap<>();
                    for (Utente user : listaGiocatori) {
                        segnaliniGiocatori.put(user.getId(), user.getSegnalini());
                    }

                    for (CartaUtensile c : listaCartaUtensile) {
                        StringBuilder builder = new StringBuilder();
                        if (c.getCosto() == 1) {
                            builder.append("F");
                        } else {
                            builder.append("T");
                        }
                        builder.append(c.getNome());
                        builder.append("*");
                        builder.append(c.getDescrizione());
                        listCartaUtensile.add(builder.toString());
                    }

                    ArrayList<String> dadiRiserva = new ArrayList<>();
                    for (Dado d : riserva) {
                        dadiRiserva.add(d.toString());
                    }


                    ArrayList<String> carteObiettivoPubblico = new ArrayList<>();
                    for (CartaObiettivoPubblico c : listaCartaObiettivoPubblico) {
                        StringBuilder builder = new StringBuilder();
                        builder.append(c.getNome());
                        builder.append("*");
                        builder.append(c.getDescrizione());
                        carteObiettivoPubblico.add(builder.toString());
                    }

                    HashMap<String, String> listCarteObiettivoPrivato = new HashMap<>();

                    for (Utente utente : listaGiocatori) {
                        listCarteObiettivoPrivato.put(utente.getId(), utente.getObiettivoPrivato().get(0).getColore().getDescrizione());
                    }
                    ArrayList<String> tracciato = new ArrayList<>();

                    for (Dado d : tracciatoDelRound.getRimanenzeRiservaOn()) {
                        tracciato.add(d.toString());
                    }
                    view.updateView(planceGiocatori, listCartaUtensile, getCurrentPlayer().getId(), getTurno(),getTracciatoDelRound().getRoundAttuale(),segnaliniGiocatori, dadiRiserva, "null", carteObiettivoPubblico, listCarteObiettivoPrivato, tracciato, azioniGiocatore);
                    return true;
                } else {
                    for (Utente utente : listaGiocatori) {
                        if (utente.getId().equals(username)) {

                            Integer[] difficoltàCarteSchema = new Integer[4];
                            String[] nomeCarteSchema = new String[4];
                            difficoltàCarteSchema[0] = scelteSchemi.get(utente).getFirst().getDifficoltaFronte();
                            difficoltàCarteSchema[1] = scelteSchemi.get(utente).getFirst().getDifficoltaRetro();
                            difficoltàCarteSchema[2] = scelteSchemi.get(utente).getSecond().getDifficoltaRetro();
                            difficoltàCarteSchema[3] = scelteSchemi.get(utente).getSecond().getDifficoltaRetro();
                            nomeCarteSchema[0] = scelteSchemi.get(utente).getFirst().getNomeFronte();
                            nomeCarteSchema[1] = scelteSchemi.get(utente).getFirst().getNomeRetro();
                            nomeCarteSchema[2] = scelteSchemi.get(utente).getSecond().getNomeFronte();
                            nomeCarteSchema[3] = scelteSchemi.get(utente).getSecond().getNomeRetro();




                            view.notifyUser(utente.getId(), scelteSchemi.get(utente).getFirst().stringRepresentation(true), scelteSchemi.get(utente).getFirst().stringRepresentation(false), scelteSchemi.get(utente).getSecond().stringRepresentation(true), scelteSchemi.get(utente).getSecond().stringRepresentation(false), utente.getObiettivoPrivato().get(0).getColore().getDescrizione(), difficoltàCarteSchema, nomeCarteSchema);
                            return false;
                        }
                    }
                }
            }
        }
        return false;
    }

    public void addObserver(GameObserver view, String username) throws RemoteException {
        if (!(gameObservers.containsKey(username))) {
            gameObservers.put(username, view);
            view.updateUsername(true);
        } else {
            view.updateUsername(false);
        }
    }

    public int numberOfObserver() {
        return gameObservers.size();
    }

    public void inizializzaAzioniGiocatore() {
        azioniGiocatore.clear();
        for (int i = 1; i <= 3; i++) {
            azioniGiocatore.add(i);
        }
    }

    public HashSet<Integer> getAzioniGiocatore() {
        return azioniGiocatore;
    }

    public void preparaPartita() throws RemoteException {


        for (String username : gameObservers.keySet()) {
            GameObserver view = gameObservers.get(username);
            Utente u = new Utente(this.getPlanciaFromList(), this.getPrivatoFromList());
            u.setId(username);
            listaGiocatori.add(u);
           // userTokens.put(username, u.getToken());
            scelteSchemi.put(u, new Coppia<Schema, Schema>(getSchemaFromList(), getSchemaFromList()));

            Integer[] difficoltàCarteSchema = new Integer[4];
            String[] nomeCarteSchema = new String[4];
            difficoltàCarteSchema[0] = scelteSchemi.get(u).getFirst().getDifficoltaFronte();
            difficoltàCarteSchema[1] = scelteSchemi.get(u).getFirst().getDifficoltaRetro();
            difficoltàCarteSchema[2] = scelteSchemi.get(u).getSecond().getDifficoltaRetro();
            difficoltàCarteSchema[3] = scelteSchemi.get(u).getSecond().getDifficoltaRetro();
            nomeCarteSchema[0] = scelteSchemi.get(u).getFirst().getNomeFronte();
            nomeCarteSchema[1] = scelteSchemi.get(u).getFirst().getNomeRetro();
            nomeCarteSchema[2] = scelteSchemi.get(u).getSecond().getNomeFronte();
            nomeCarteSchema[3] = scelteSchemi.get(u).getSecond().getNomeRetro();

            view.notifyUser(u.getId(),scelteSchemi.get(u).getFirst().stringRepresentation(true), scelteSchemi.get(u).getFirst().stringRepresentation(false), scelteSchemi.get(u).getSecond().stringRepresentation(true), scelteSchemi.get(u).getSecond().stringRepresentation(false), u.getObiettivoPrivato().get(0).toString(), difficoltàCarteSchema, nomeCarteSchema);
        }
        riempiRiserva();
        setListaCartaObiettivoPubblico();
        setListaCartaUtensile();
        inizializzaOrdineRound();
        setGiocatoreCorrente();

    }

    public void sceltaSchema(GameObserver view, String idUser, boolean carta1, boolean fronte) throws RemoteException {
        for (Utente u : listaGiocatori) {
            if (u.getId().equals(idUser)) {
                if (carta1) {
                    u.getPlancia().inserisciCartaSchema(scelteSchemi.get(u).getFirst());
                    u.scegliFacciaSchema(fronte);
                } else {
                    u.getPlancia().inserisciCartaSchema(scelteSchemi.get(u).getSecond());
                    u.scegliFacciaSchema(fronte);
                }
            }
            view.notifyScheme(carta1, fronte);
        }

    }


    private void inizializzaObiettiviPrivati() {
        riempiSetofColors();
        for (String colore : setOfColors) {
            for (Constraint c : Constraint.values()) {
                if (c.getDescrizione().equals(colore)) {
                    listaObiettiviPrivati.add(c);
                }
            }
        }

    }

    private void inizializzaPlance() {
        riempiSetofColors();
        for (String colore : setOfColors) {
            listaPlance.add(new Plancia(colore));

        }

    }

    public Schema getSchemaFromList() {
        if (listaCarteSchema.size() == 0) {
            inizializzaCarteSchema();
        }
        return listaCarteSchema.remove(random.nextInt(listaCarteSchema.size()));
    }

    private void inizializzaCarteSchema() {
        for (int i = 1; i <= 12; i++) {
            listaCarteSchema.add(FactorySchema.get(i));
        }
    }


    public Plancia getPlanciaFromList() {
        return listaPlance.remove(random.nextInt(listaPlance.size()));
    }

    public Constraint getPrivatoFromList() {
        return listaObiettiviPrivati.remove(random.nextInt(listaObiettiviPrivati.size()));
    }


    public void incrementaTurno() throws RemoteException {
        this.turno = this.turno + 1;
        this.setGiocatoreCorrente();
        updateGenerale();

    }

    public void reInizializzaTurno() {
        this.turno = 1;
        riempiRiserva();
    }

    // populate the SetofColors
    public void riempiSetofColors() {
        if (!setOfColors.contains("Blu")) setOfColors.add("Blu");
        if (!setOfColors.contains("Viola")) setOfColors.add("Viola");
        if (!setOfColors.contains("Verde")) setOfColors.add("Verde");
        if (!setOfColors.contains("Rosso")) setOfColors.add("Rosso");
        if (!setOfColors.contains("Giallo")) setOfColors.add("Giallo");
    }

    // populate the Dice Bag
    public void riempiSacchetto() {
        this.riempiSetofColors();
        for (String colore : setOfColors) {
            for (int i = 0; i < 18; i++) {
                Dado d = new Dado(colore);
                sacchetto.add(d);
            }

        }
    }

    // print out the Dice Bag
    public void stampaSacchetto() {
        for (Dado d : sacchetto) {
            System.out.println("Dado " + d.getNumero() + " " + d.getColore());
        }
    }

    // populate the Drafts Pool
    public void riempiRiserva() {
        for (int i = 0; i < (listaGiocatori.size() * 2 + 1); i++) {
            riserva.add(i, sacchetto.remove(random.nextInt(sacchetto.size() - i)));
        }
    }

    // print out the Drafts Pool
    public void stampaRiserva() {   // method to print out the pool of dice
        for (Dado d : riserva) {
            System.out.println("Dado " + d.getNumero() + " " + d.getColore());
        }
    }

    // choose a die from the pool of dice given its position
    public Dado getDadofromRiserva(int i) {
        // modificato in i piuttosto che i-1
        return riserva.remove(i);

    }

    // reinsert a die in the Draft Pool
    public void reInserisciDadoinRiserva(Dado d) {
        riserva.add(d);
    }

    //reinsert a die in the Dice Bag
    public void reInserisciDadoInSacchetto(Dado d) {
        sacchetto.add(d);
    }

    // remove a Player from the List of Players (game not started)
    public void removeUtente(Utente user) {
        listaGiocatori.remove(user);
    }

    // add a Player from the List of Players
    public void addUtente(Utente user) {
        listaGiocatori.add(user);
    }

    //get the number of players
    public int getNumeroGiocatori() {
        return listaGiocatori.size();
    }


    public void inizializzaOrdineRound() {
        for (int k = 0; k < 2 * listaGiocatori.size(); k++) {
            if (k < listaGiocatori.size()) {
                ordineRound.add(listaGiocatori.get(k));
            } else if (k < 2 * listaGiocatori.size()) {
                ordineRound.add(listaGiocatori.get(2 * listaGiocatori.size() - 1 - k));
            }
        }
    }

    // method to be called after the end of each round to set the correct order, perhaps before calling nextRound()
    public void setOrdineRound() {

        ordineRound.add(listaGiocatori.size(), ordineRound.get(0));
        ordineRound.add(listaGiocatori.size(), ordineRound.get(0));
        ordineRound.remove(0);
        ordineRound.remove(ordineRound.size() - 1);

    }

    public Utente getCurrentPlayer() {
        return giocatoreCorrente;
    }


    // method called after the end of a turn to update the current player
    public void setGiocatoreCorrente() {
        this.giocatoreCorrente = ordineRound.get(turno - 1);
    }

    // set the current player as the one passed
    public void setGiocatoreCorrente(Utente u) {
        this.giocatoreCorrente = u;
    }

    // return 1 if there is no player
    public boolean isEmpty() {
        return (listaGiocatori.size() == 0);
    }

    // increments the round
    public void nextRound() throws RemoteException {
        if (ordineRound.size() < 2 * listaGiocatori.size())
            setOrdineRoundTool8End(); //called if the tool 8 was used once or more in a round
        setOrdineRound();
        tracciatoDelRound.setRimanenzeRiservaOn(tracciatoDelRound.getRoundAttuale(), riserva.remove(0));
        tracciatoDelRound.addRimanenzeRiservaOut(riserva);
        riserva.clear();

        tracciatoDelRound.nextRound();
        reInizializzaTurno();
        setGiocatoreCorrente();
        updateGenerale();
    }

    // method called by card tool 7;
    public void rilanciaDadiInRiserva() {
        for (Dado d : riserva) {
            d.lanciaDado();
        }
    }

    // method called by card tool 8
    public void setOrdineRoundTool8Start() {

            //ordineRound.add(turno, getCurrentPlayer());  //TODO rimuovere in quanto vecchia implementazione
            ordineRound.remove(ordineRound.size() - turno);
    }

    //method called after the use of the card tool 8 to restore the initial situation da testare
    public void setOrdineRoundTool8End() {
        ordineRound.clear();
        this.inizializzaOrdineRound();
        for (int i = 1; i < this.getTracciatoDelRound().getRoundAttuale(); i++) {
            this.setOrdineRound();
        }
    }

    // method to set the current die
    public void setDadoSelezionato(Dado dadoSelezionato) {
        this.dadoSelezionato = dadoSelezionato;
    }

    public void setDadoSelezionato(int posizione) {
        this.dadoSelezionato = riserva.remove(posizione);
    }

    // populate the stack of Tool Cards(12)
    private void inizializzaMazzoCarteUtensile() {
        for (int i = 1; i <= 12; i++) {
            mazzoCarteUtensile.add(i);
       }
    }


    // populate the stack of Public Objective Cards(10)
    public void inizializzaMazzoCarteObiettivoPubblico() {
        for (int i = 1; i <= 10; i++) {
            mazzoCarteObiettivoPubblico.add(i);
        }
    }

    //method to set the List of Tool Cards in multiplayer
    public void setListaCartaUtensile() {
        if (listaGiocatori.size() > 1) {
            for (int i = 0; i < 3; i++) {
                int o = mazzoCarteUtensile.remove(random.nextInt(mazzoCarteUtensile.size()));
                listaCartaUtensile.add(FactoryCartaUtensile.getCartaUtensile(o));
            }
        }
    }


    //method to set the List of Tool Cards in singlePlayer, n is the difficult(1-5) chosen by the player
    public void setListaCartaUtensile(int n) {
        if (listaGiocatori.size() == 1 && n > 0) {

            for (int i = 0; i < n; i++) {
                int o = mazzoCarteUtensile.remove(random.nextInt(mazzoCarteUtensile.size()));
                listaCartaUtensile.add(FactoryCartaUtensile.getCartaUtensile(o));
            }
        }
    }

    //method to set the List of Public Objective Cards
    public void setListaCartaObiettivoPubblico() {

        if (listaGiocatori.size() > 1) {
            for (int i = 0; i < 3; i++) {
                int o = mazzoCarteObiettivoPubblico.remove(random.nextInt(mazzoCarteObiettivoPubblico.size()));
                listaCartaObiettivoPubblico.add(FactoryCartaObiettivoPubblico.getCartaObiettivoPubblico(o));

            }
        } else {
            for (int i = 0; i < 2; i++) {
                int o = mazzoCarteObiettivoPubblico.remove(random.nextInt(mazzoCarteObiettivoPubblico.size()));
                listaCartaObiettivoPubblico.add(FactoryCartaObiettivoPubblico.getCartaObiettivoPubblico(o));
            }
        }

    }


    public void calcolaPunteggioFinale() throws RemoteException {

        String vincitore = null;
        for (Utente u : listaGiocatori) {
            int punteggio = u.getSegnalini();
            int punteggioObiettivoPrivato = 0;

            for (int i = 0; i < 4; i++) {
                for (int j = 0; j < 5; j++) {
                    if (u.getPlancia().leggiPlancia(i, j) == null) punteggio = punteggio - 1;
                    else if (u.getPlancia().leggiPlancia(i, j).getColore().equalsIgnoreCase(u.getObiettivoPrivato().get(0).getColore().getDescrizione())) {
                        punteggio += u.getPlancia().leggiPlancia(i, j).getNumero();
                    }
                }

            }

            punteggioObiettivoPrivato = punteggio - u.getSegnalini();

            for (CartaObiettivoPubblico c : listaCartaObiettivoPubblico) {
                punteggio += c.calcolaPunti(u.getPlancia());

            }

            listaPunteggiUtenteObiettivoPrivato.put(u.getId(), punteggioObiettivoPrivato);
            listaPunteggiUtente.put(u.getId(), punteggio);


        }
        if(gameObservers.size()==1) {
            for (String username:getGameObservers().keySet())
                  {
                vincitore=username;
            }
        }else vincitore = calcolaVincitore(listaPunteggiUtente, listaPunteggiUtenteObiettivoPrivato);

        for (String username : gameObservers.keySet()) {
            GameObserver view = gameObservers.get(username);
            view.updateViewPunteggio(listaPunteggiUtente, vincitore);
        }
    }

    public String calcolaVincitore(HashMap<String, Integer> listaPunteggiUtente, HashMap<String, Integer> listaPunteggiUtenteObiettivoPrivato) {
        String vincitore = listaGiocatori.get(0).getId();
        for (Utente u : listaGiocatori) {
            if (listaPunteggiUtente.get(u.getId()) > listaPunteggiUtente.get(vincitore)) vincitore = u.getId();
            else if (listaPunteggiUtente.get(u.getId()) == listaPunteggiUtente.get(vincitore)) {
                if (listaPunteggiUtenteObiettivoPrivato.get(u.getId()) > listaPunteggiUtenteObiettivoPrivato.get(vincitore))
                    vincitore = u.getId();
                else if (listaPunteggiUtenteObiettivoPrivato.get(u.getId()) == listaPunteggiUtenteObiettivoPrivato.get(vincitore)) {
                    Utente u1 = null;
                    for (Utente user : listaGiocatori) {
                        if (vincitore.equals(user.getId())) u1 = user;
                        break;
                    }
                    if (u.getSegnalini() > u1.getSegnalini()) vincitore = u.getId();
                    else if (u.getSegnalini() == u1.getSegnalini()) {

                        // con questa implementazione con ordine 1 2 3 4 vince 1 a parità di segnalini
                        for (Utente utente : ordineRound) {
                            if (utente.getId().equals(vincitore)) {
                                break;
                            }
                            if (utente.getId().equals(u.getId())) {
                                vincitore = u.getId();
                                break;
                            }
                        }
                    }
                }

            }

        }
        return vincitore;
    }

    public boolean contaSchemi() throws RemoteException {
        int n = 0;
        for (Utente u : listaGiocatori) {
            if (u.getPlancia().getCartaSchema() == null) return false;
        }
        // preparazione updateView iniziale
        updateGenerale();


        return true;
    }

    public synchronized void updateGenerale() throws RemoteException {
        HashMap<String, GameObserver> gameObserverClone = new HashMap<>();
        gameObserverClone = (HashMap<String, GameObserver>) gameObservers.clone();
        for (String username : gameObserverClone.keySet()) {
            GameObserver view = gameObserverClone.get(username);

            HashMap<String, String[][]> planceGiocatori = new HashMap<>();
            for (Utente u : listaGiocatori) {
                planceGiocatori.put(u.getId(), u.getPlancia().rappresentazionePlancia());
            }

            HashMap<String, Integer> segnaliniGiocatori = new HashMap<>();
            for (Utente u : listaGiocatori) {
                segnaliniGiocatori.put(u.getId(), u.getSegnalini());
            }



            ArrayList<String> listCartaUtensile = new ArrayList<>();


            for (CartaUtensile c : listaCartaUtensile) {
                StringBuilder builder = new StringBuilder();
                if (c.getCosto() == 1) {
                    builder.append("F");
                } else {
                    builder.append("T");
                }
                builder.append(c.getNome());
                builder.append("*");
                builder.append(c.getDescrizione());
                listCartaUtensile.add(builder.toString());
            }

            ArrayList<String> dadiRiserva = new ArrayList<>();
            for (Dado d : riserva) {
                dadiRiserva.add(d.toString());
            }


            ArrayList<String> carteObiettivoPubblico = new ArrayList<>();
            for (CartaObiettivoPubblico c : listaCartaObiettivoPubblico) {
                StringBuilder builder = new StringBuilder();
                builder.append(c.getNome());
                builder.append("*");
                builder.append(c.getDescrizione());
                carteObiettivoPubblico.add(builder.toString());
            }
            HashMap<String, String> listCarteObiettivoPrivato = new HashMap<>();

            for (Utente u : listaGiocatori) {
                listCarteObiettivoPrivato.put(u.getId(), u.getObiettivoPrivato().get(0).getColore().getDescrizione());
            }
            ArrayList<String> tracciato = new ArrayList<>();

            for (Dado d : tracciatoDelRound.getRimanenzeRiservaOn()) {
                tracciato.add(d.toString());
            }
            if (pingClient(view)) {
                view.updateView(planceGiocatori, listCartaUtensile, getCurrentPlayer().getId(), getTurno(), getTracciatoDelRound().getRoundAttuale(), segnaliniGiocatori, dadiRiserva, "null", carteObiettivoPubblico, listCarteObiettivoPrivato, tracciato, azioniGiocatore);
            } else {
                removeObserver(username);
            }
        }
    }


    public synchronized Utente cercaUtente(String username){
        for (Utente u: listaGiocatori) {
            if(u.getId().equals(username)) return u;
        }
        return null;
    }

    public synchronized void updateCurrentPlayer() throws RemoteException {
        HashMap<String, GameObserver> gameObserverClone = new HashMap<>();
        gameObserverClone = (HashMap<String, GameObserver>) gameObservers.clone();
        for (String username : gameObserverClone.keySet()) {
            GameObserver view = gameObserverClone.get(getCurrentPlayer().getId());
            HashMap<String, String[][]> planceGiocatori = new HashMap<>();
            for (Utente u : listaGiocatori) {
                planceGiocatori.put(u.getId(), u.getPlancia().rappresentazionePlancia());
            }

            HashMap<String, Integer> segnaliniGiocatori = new HashMap<>();
            for (Utente u : listaGiocatori) {
                segnaliniGiocatori.put(u.getId(), u.getSegnalini());
            }


            ArrayList<String> listCartaUtensile = new ArrayList<>();


            for (CartaUtensile c : listaCartaUtensile) {
                StringBuilder builder = new StringBuilder();
                if (c.getCosto() == 1) {
                    builder.append("F");
                } else {
                    builder.append("T");
                }
                builder.append(c.getNome());
                builder.append("*");
                builder.append(c.getDescrizione());
                listCartaUtensile.add(builder.toString());
            }

            ArrayList<String> dadiRiserva = new ArrayList<>();
            for (Dado d : riserva) {
                dadiRiserva.add(d.toString());
            }


            ArrayList<String> carteObiettivoPubblico = new ArrayList<>();
            for (CartaObiettivoPubblico c : listaCartaObiettivoPubblico) {
                StringBuilder builder = new StringBuilder();
                builder.append(c.getNome());
                builder.append("*");
                builder.append(c.getDescrizione());
                carteObiettivoPubblico.add(builder.toString());
            }
            HashMap<String, String> listCarteObiettivoPrivato = new HashMap<>();


            listCarteObiettivoPrivato.put(getCurrentPlayer().getId(), getCurrentPlayer().getObiettivoPrivato().get(0).getColore().getDescrizione());

            ArrayList<String> tracciato = new ArrayList<>();

            for (Dado d : tracciatoDelRound.getRimanenzeRiservaOn()) {
                tracciato.add(d.toString());
            }
            if (pingClient(view)) {
                view.updateView(planceGiocatori, listCartaUtensile, getCurrentPlayer().getId(), getTurno(), getTracciatoDelRound().getRoundAttuale(),segnaliniGiocatori, dadiRiserva, "null", carteObiettivoPubblico, listCarteObiettivoPrivato, tracciato, azioniGiocatore);
            } else {
                removeObserver(getCurrentPlayer().getId());
            }
        }
    }


    public synchronized void updateMessage(String message) throws RemoteException {
        HashMap<String, GameObserver> gameObserverClone = new HashMap<>();
        gameObserverClone = (HashMap<String, GameObserver>) gameObservers.clone();

        GameObserver view = gameObserverClone.get(getCurrentPlayer().getId());
        if (pingClient(view)){
            view.updateMessage(message);
        }else{
            removeObserver(getCurrentPlayer().getId());
        }
    }

    public synchronized void updatePagamento() throws RemoteException {
        HashMap<String, GameObserver> gameObserverClone = new HashMap<>();
        gameObserverClone = (HashMap<String, GameObserver>) gameObservers.clone();

        GameObserver view = gameObserverClone.get(getCurrentPlayer().getId());

        if (pingClient(view)) {
            view.updatePagamento();
            this.updateCurrentPlayer();
        } else {
            removeObserver(getCurrentPlayer().getId());
        }
    }




    public boolean pingClient(GameObserver view){
        boolean result=false;
        try{
            view.ping();
            result=true;
        }catch(RemoteException ex){
            return result;
        }
        return result;
    }


    public void updateTool11(String dado) throws RemoteException {
          GameObserver view = getGameObservers().get(getCurrentPlayer().getId());
            view.updateViewTool11(dado);
        }

    public void updateTool4(boolean fase) throws RemoteException {
            GameObserver view = getGameObservers().get(getCurrentPlayer().getId());
            view.updateViewTool4(fase);
    }

    public void updateTool12(boolean fase,int numeroTracciatoRound) throws RemoteException {
        GameObserver view = getGameObservers().get(getCurrentPlayer().getId());
        view.updateViewTool12(fase,numeroTracciatoRound);
    }

    public void updateTool6piazzato(boolean piazzato) throws RemoteException {
        GameObserver view = getGameObservers().get(getCurrentPlayer().getId());
        view.updateViewTool6piazzato(piazzato);
    }




    public void piazzamentoDado(int i,int j, boolean utensile1, boolean utensile2) throws RemoteException{
        getCurrentPlayer().getPlancia().calcolaMosse(dadoSelezionato, utensile1, utensile2);
        try{
            getCurrentPlayer().getPlancia().piazzaDado(i, j, dadoSelezionato);
            updateGenerale();
         //   System.out.println("dado posizionato");
        }catch(MossaIllegaleException e){
            azioniGiocatore.add(1);
            riserva.add(dadoSelezionato);
            updateMessage("MOSSA NON VALIDA");
            updateCurrentPlayer();
            //todo errorHandler - dopo che il metodo piazzadado viene chiamato una volta, non viene più chiamata l'eccezione???

            /*for (String username:gameObservers.keySet()){
                GameObserver view = gameObservers.get(username);
                view.setPiazzamentoScorretto(getCurrentPlayer().getId());
            }*/


        }
    }


    // various getter

    public void setRiserva(ArrayList<Dado> riserva) {
        this.riserva = riserva;
    }

    public ArrayList<Dado> getRiserva() {
        return riserva;
    }

    public ArrayList<Utente> getListaGiocatori() {
        return listaGiocatori;
    }

    public ArrayList<Dado> getSacchetto() {
        return sacchetto;
    }

    public TracciatoDelRound getTracciatoDelRound() {
        return tracciatoDelRound;
    }

    public ArrayList<CartaUtensile> getListaCartaUtensile() {
        return listaCartaUtensile;
    }

    public ArrayList<CartaObiettivoPubblico> getListaCartaObiettivoPubblico() {
        return listaCartaObiettivoPubblico;
    }

    public Random getRandom() {
        return random;
    }

    public ArrayList<String> getSetOfColors() {
        return setOfColors;
    }

    public ArrayList<Utente> getOrdineRound() {
        return ordineRound;
    }

    public Dado getDadoSelezionato() {
        return dadoSelezionato;
    }

    public int getTurno() {
        return turno;
    }

    public Dado getDadoRandomFromSacchetto(){

        return sacchetto.remove(random.nextInt(sacchetto.size()));

    }

    public synchronized HashMap<String, GameObserver> getGameObservers() {
        return gameObservers;
    }

   // public HashMap<String, String> getUserTokens() {
       // return userTokens;
    //}

    public void removeObserver(String username) {
        gameObservers.remove(username);
        for (String user : gameObservers.keySet()) {
            try {
                if (!(user).equals(username))
                    gameObservers.get(user).notifyUserExit(username);
            }catch (RemoteException e){

            }
        }
    }


    // end various getter

}


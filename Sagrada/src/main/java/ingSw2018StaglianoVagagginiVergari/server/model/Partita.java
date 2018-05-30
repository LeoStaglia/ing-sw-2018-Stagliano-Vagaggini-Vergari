package ingSw2018StaglianoVagagginiVergari.server.model;

import Eccezioni.MossaIllegaleException;
import ingSw2018StaglianoVagagginiVergari.common.GameObserver;
import ingSw2018StaglianoVagagginiVergari.common.RemoteController;
import ingSw2018StaglianoVagagginiVergari.server.model.carteSchema.CartaSchema;
import ingSw2018StaglianoVagagginiVergari.server.model.carteSchema.FactorySchema;
import ingSw2018StaglianoVagagginiVergari.server.model.carteSchema.Schema;

import java.rmi.RemoteException;
import java.util.*;

public class Partita {

    private HashMap<String, GameObserver> gameObservers;
    private Dado dadoSelezionato = null;
    private ArrayList<String> setOfColors = new ArrayList<String>();
    private ArrayList<Dado> riserva = new ArrayList<Dado>();
    private ArrayList<Dado> sacchetto = new ArrayList<Dado>();

    private ArrayList<Utente> listaGiocatori = new ArrayList<Utente>();
    private Utente giocatoreCorrente;  // currentPlayer

    ArrayList<Utente> ordineRound = new ArrayList<Utente>();  // mantiene l'ordine del round

    private int turno=1;
    private TracciatoDelRound tracciatoDelRound = new TracciatoDelRound();
    private HashMap<String,Integer> listaPunteggiUtente=new HashMap<String,Integer>();
    private HashMap<String,Integer> listaPunteggiUtenteObiettivoPrivato=new HashMap<String,Integer>();

    private HashMap<Utente, Coppia<Schema, Schema>> scelteSchemi= new HashMap<>();
    private ArrayList<Integer> mazzoCarteUtensile= new ArrayList<Integer>();
    private ArrayList<Integer> mazzoCarteObiettivoPubblico= new ArrayList<Integer>();
    private ArrayList<CartaUtensile> listaCartaUtensile = new ArrayList<CartaUtensile>();
    private ArrayList<CartaObiettivoPubblico> listaCartaObiettivoPubblico = new ArrayList<CartaObiettivoPubblico>();
    private ArrayList<Plancia> listaPlance = new ArrayList<>();
    private ArrayList<Constraint> listaObiettiviPrivati = new ArrayList<>();
    private ArrayList<Schema> listaCarteSchema= new ArrayList<>();
    private Random random = new Random();

    HashSet<Integer> azioniGiocatore= new HashSet<Integer>();

    //  private String idPartita;   it is needed only in case of multiple games


    // public ArrayList<Dado> getDadiFromSacchetto(int n){}  // n is equal to the number of dice I want to get

    public Partita(){
        riempiSetofColors();
        riempiSacchetto();
        //Utente.inizializzaIdSet();
        inizializzaCarteSchema();
        inizializzaMazzoCarteUtensile();
        inizializzaMazzoCarteObiettivoPubblico();
        inizializzaPlance();
        inizializzaObiettiviPrivati();
        gameObservers= new HashMap<>();
    }

    public void addObserver(GameObserver view, String username) throws RemoteException{
        if (!(gameObservers.containsKey(username))) {
            gameObservers.put(username, view);
            view.updateUsername(true);
        }else{
            //TODO gestisci update
            view.updateUsername(false);
        }
    }
    public int numberOfObserver(){
        return gameObservers.size();
    }
    public void removeObserver(GameObserver view, String username){
        gameObservers.remove(username, view);
    }

    public void inizializzaAzioniGiocatore(){
        for(int i=1;i<=3;i++){
            azioniGiocatore.add(i);
        }
    }

    public HashSet<Integer> getAzioniGiocatore() {
        return azioniGiocatore;
    }

    public void preparaPartita() throws RemoteException {


        for (String username: gameObservers.keySet()){
            GameObserver view = gameObservers.get(username);
            Utente u = new Utente(this.getPlanciaFromList(), this.getPrivatoFromList());
            u.setId(username);
            listaGiocatori.add(u);
            scelteSchemi.put(u, new Coppia<Schema, Schema>(getSchemaFromList(), getSchemaFromList()));
            view.notifyUser(u.getId(),scelteSchemi.get(u).getFirst().stringRepresentation(true),scelteSchemi.get(u).getFirst().stringRepresentation(false),scelteSchemi.get(u).getSecond().stringRepresentation(true), scelteSchemi.get(u).getSecond().stringRepresentation(false), u.getObiettivoPrivato().get(0).toString());
        }
        riempiRiserva();
        setListaCartaObiettivoPubblico();
        setListaCartaUtensile();
        inizializzaOrdineRound();
        setGiocatoreCorrente();

    }

    public void sceltaSchema(GameObserver view, String idUser,boolean carta1, boolean fronte) throws RemoteException{
        for (Utente u: listaGiocatori){
            if (u.getId().equals(idUser)){
                if (carta1){
                    u.getPlancia().inserisciCartaSchema(scelteSchemi.get(u).getFirst());
                    u.scegliFacciaSchema(fronte);
                }else{
                    u.getPlancia().inserisciCartaSchema(scelteSchemi.get(u).getSecond());
                    u.scegliFacciaSchema(fronte);
                }
            }
            view.notifyScheme(carta1, fronte);
        }

    }


    private void inizializzaObiettiviPrivati(){
        riempiSetofColors();
        for (String colore: setOfColors){
            for (Constraint c: Constraint.values()){
                if (c.getDescrizione().equals(colore)){
                    listaObiettiviPrivati.add(c);
                }
            }
        }

    }
    private void inizializzaPlance(){
        riempiSetofColors();
        for (String colore: setOfColors){
            listaPlance.add(new Plancia(colore));

        }

    }

    public Schema getSchemaFromList(){
        if (listaCarteSchema.size()==0){
            inizializzaCarteSchema();
        }
        return listaCarteSchema.remove(random.nextInt(listaCarteSchema.size()));
    }

    private void inizializzaCarteSchema(){
        for (int i = 1;  i<=12; i++) {
            listaCarteSchema.add(FactorySchema.get(i));
        }
    }


    public Plancia getPlanciaFromList(){
        return listaPlance.remove(random.nextInt(listaPlance.size()));
    }
    public Constraint getPrivatoFromList(){
        return listaObiettiviPrivati.remove(random.nextInt(listaObiettiviPrivati.size()));
    }


    public void incrementaTurno()throws RemoteException {
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
        if(!setOfColors.contains("Blu")) setOfColors.add("Blu");
        if(!setOfColors.contains("Viola"))setOfColors.add("Viola");
        if(!setOfColors.contains("Verde"))setOfColors.add("Verde");
        if(!setOfColors.contains("Rosso"))setOfColors.add("Rosso");
        if(!setOfColors.contains("Giallo"))setOfColors.add("Giallo");
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
    public void reInserisciDadoInSacchetto(Dado d){ sacchetto.add(d);}

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
        //TODO possibile inserimento metodo se nel round è stata utilizzata la carta 8
        setOrdineRound();
        tracciatoDelRound.setRimanenzeRiservaOn(tracciatoDelRound.getRoundAttuale(),riserva.remove(0));
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
        if (turno < 4) {
            ordineRound.add(turno, getCurrentPlayer());
            ordineRound.remove(ordineRound.size() - turno);
        }
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
        //for (int i = 1; i <= 12; i++) {
            //mazzoCarteUtensile.add(i);
        //}
        mazzoCarteUtensile.add(12);
        mazzoCarteUtensile.add(12);
        mazzoCarteUtensile.add(12);

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
                int o=mazzoCarteUtensile.remove(random.nextInt(mazzoCarteUtensile.size()));
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
                int o= mazzoCarteObiettivoPubblico.remove(random.nextInt(mazzoCarteObiettivoPubblico.size()));
                listaCartaObiettivoPubblico.add(FactoryCartaObiettivoPubblico.getCartaObiettivoPubblico(o));

            }
        }
        else {
            for (int i = 0; i < 2; i++) {
                int o= mazzoCarteObiettivoPubblico.remove(random.nextInt(mazzoCarteObiettivoPubblico.size()));
                listaCartaObiettivoPubblico.add(FactoryCartaObiettivoPubblico.getCartaObiettivoPubblico(o));
            }
        }

    }



    public void calcolaPunteggioFinale() throws RemoteException{

        String vincitore;
        for (Utente u:listaGiocatori) {
            int punteggio=u.getSegnalini();
            int punteggioObiettivoPrivato=0;

            for (int i=0;i<4;i++) {
                for(int j=0;j<5;j++){
                    if(u.getPlancia().leggiPlancia(i,j)==null) punteggio=punteggio-1;
                    else if(u.getPlancia().leggiPlancia(i,j).getColore().equalsIgnoreCase(u.getObiettivoPrivato().get(0).getColore().getDescrizione())){
                        punteggio+=u.getPlancia().leggiPlancia(i,j).getNumero();
                    }
                }

            }

            punteggioObiettivoPrivato=punteggio-u.getSegnalini();

            for (CartaObiettivoPubblico c:listaCartaObiettivoPubblico) {
                punteggio+=c.calcolaPunti(u.getPlancia());

            }

            listaPunteggiUtenteObiettivoPrivato.put(u.getId(),punteggioObiettivoPrivato);
            listaPunteggiUtente.put(u.getId(),punteggio);


        }
        vincitore=calcolaVincitore(listaPunteggiUtente,listaPunteggiUtenteObiettivoPrivato);

        for (String username: gameObservers.keySet()){
            GameObserver view = gameObservers.get(username);
            view.updateViewPunteggio(listaPunteggiUtente,vincitore);
        }
        // TODO aggiungere calcolo SinglePlayer
    }

    public String calcolaVincitore(HashMap<String,Integer> listaPunteggiUtente,HashMap<String,Integer> listaPunteggiUtenteObiettivoPrivato){
        String vincitore=listaGiocatori.get(0).getId();
        for(Utente u:listaGiocatori){
            if(listaPunteggiUtente.get(u.getId())>listaPunteggiUtente.get(vincitore)) vincitore=u.getId();
            else if(listaPunteggiUtente.get(u.getId())==listaPunteggiUtente.get(vincitore)){
                if(listaPunteggiUtenteObiettivoPrivato.get(u.getId())>listaPunteggiUtenteObiettivoPrivato.get(vincitore)) vincitore=u.getId();
                else if(listaPunteggiUtenteObiettivoPrivato.get(u.getId())==listaPunteggiUtenteObiettivoPrivato.get(vincitore)){
                    Utente u1=null;
                    for (Utente user:listaGiocatori) {
                        if(vincitore.equals(user.getId())) u1=user;
                        break;
                    }
                    if(u.getSegnalini()>u1.getSegnalini()) vincitore=u.getId();
                    else if(u.getSegnalini()==u1.getSegnalini()){

                        // con questa implementazione con ordine 1 2 3 4 vince 1 a parità di segnalini
                        for (Utente utente:ordineRound) {
                            if(utente.getId().equals(vincitore)){
                                break;
                            }
                            if(utente.getId().equals(u.getId())){
                                vincitore=u.getId();
                                break;
                            }
                        }
                    }
                }

            }

        }
        return vincitore;
    }

    public boolean contaSchemi() throws RemoteException{
        int n=0;
        for (Utente u:listaGiocatori) {
            if(u.getPlancia().getCartaSchema()==null) return false;
        }
        // preparazione updateView iniziale

        updateGenerale();



        return true;
    }

    public void updateGenerale() throws RemoteException{
        for (String username:gameObservers.keySet()) {
            GameObserver view = gameObservers.get(username);
            HashMap<String,String[][]> planceGiocatori=new HashMap<>();
            for (Utente u:listaGiocatori) {
                planceGiocatori.put(u.getId(),u.getPlancia().rappresentazionePlancia());
            }

            ArrayList<String> listCartaUtensile = new ArrayList<>();


            for (CartaUtensile c: listaCartaUtensile) {
                StringBuilder builder=new StringBuilder();
                if(c.getCosto()==1) {builder.append("F");}
                else{ builder.append("T");}
                builder.append(c.getNome());
                builder.append("*");
                builder.append(c.getDescrizione());
                listCartaUtensile.add(builder.toString());
            }

            ArrayList<String> dadiRiserva=new ArrayList<>();
            for (Dado d:riserva) {
                dadiRiserva.add(d.toString());
            }


            ArrayList<String> carteObiettivoPubblico=new ArrayList<>();
            for (CartaObiettivoPubblico c:listaCartaObiettivoPubblico) {
                carteObiettivoPubblico.add(c.getNome());
                //TODO inserire descrizione carta
            }
            HashMap<String,String> listCarteObiettivoPrivato=new HashMap<>();

            for (Utente u:listaGiocatori) {
                listCarteObiettivoPrivato.put(u.getId(),u.getObiettivoPrivato().get(0).getColore().getDescrizione());
            }
            ArrayList<String> tracciato = new ArrayList<>();

            for (Dado d: tracciatoDelRound.getRimanenzeRiservaOn()){
                tracciato.add(d.toString());
            }

            view.updateView(planceGiocatori,listCartaUtensile,getCurrentPlayer().getId(),getTurno(),getTracciatoDelRound().getRoundAttuale(),dadiRiserva,"null",carteObiettivoPubblico,listCarteObiettivoPrivato, tracciato,azioniGiocatore);
        }

    }


    public void piazzamentoDado(int i,int j, boolean utensile1, boolean utensile2) throws RemoteException{
        getCurrentPlayer().getPlancia().calcolaMosse(dadoSelezionato, utensile1, utensile2);
        try{
            getCurrentPlayer().getPlancia().piazzaDado(i, j, dadoSelezionato);
            updateGenerale();
            System.out.println("dado posizionato");
        }catch(MossaIllegaleException e){
            riserva.add(dadoSelezionato);
            //todo errorHandler - dopo che il metodo piazzadado viene chiamato una volta, non viene più chiamata l'eccezione???

            for (String username:gameObservers.keySet()){
                GameObserver view = gameObservers.get(username);
                view.printPiazzamentoScorretto(getCurrentPlayer().getId());
            }




        }
    }


    // various getter

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

    public HashMap<String, GameObserver> getGameObservers() {
        return gameObservers;
    }
    // end various getter

}


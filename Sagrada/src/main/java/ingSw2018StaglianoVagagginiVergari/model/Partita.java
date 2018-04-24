package ingSw2018StaglianoVagagginiVergari.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Partita {

    private Dado dadoSelezionato = null;
    private List<String> setOfColors = new ArrayList<String>();
    private List<Dado> riserva = new ArrayList<Dado>();
    private List<Dado> sacchetto = new ArrayList<Dado>();

    private List<Utente> listaGiocatori = new ArrayList<Utente>();
    private Utente giocatoreCorrente = new Utente();  // currentPlayer

    ArrayList<Utente> ordineRound = new ArrayList<Utente>();  // mantiene l'ordine del round

    private int turno = 1;
    private TracciatoDelRound tracciatoDelRound = new TracciatoDelRound();

    private List<Integer> mazzoCarteUtensile= new ArrayList<Integer>();
    private List<Integer> mazzoCarteObiettivoPubblico= new ArrayList<Integer>();
    private List<CartaUtensile> listaCartaUtensile = new ArrayList<CartaUtensile>();
    private List<CartaObiettivoPubblico> listaCartaObiettivoPubblico = new ArrayList<CartaObiettivoPubblico>();
    private Random random = new Random();

    //  private String idPartita;   it is needed only in case of multiple games


    // public ArrayList<Dado> getDadiFromSacchetto(int n){}  // n is equal to the number of dice I want to get

    public void incrementaTurno() {
        this.turno = this.turno + 1;
    }

    public void reInizializzaTurno() {
        this.turno = 1;
    }

    // populate the SetofColors
    public void riempiSetofColors() {
        setOfColors.add("Blu");
        setOfColors.add("Viola");
        setOfColors.add("Verde");
        setOfColors.add("Rosso");
        setOfColors.add("Giallo");
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
        return riserva.remove(i - 1);

    }

    // reinsert a die in the Draft Pool
    public void reInserisciDadoinRiserva(Dado d) {
        riserva.add(d);
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
        return ordineRound.get(turno - 1);
    }

    // return 1 if there is no player
    public boolean isEmpty() {
        return (listaGiocatori.size() == 0);
    }

    // increments the round
    public void nextRound() {
        tracciatoDelRound.nextRound();
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
        this.inizializzaOrdineRound();
        for (int j = 0; j < 2 * listaGiocatori.size(); j++) {
            this.ordineRound.remove(0);
        }
        for (int i = 0; i < this.getTracciatoDelRound().getRoundAttuale(); i++) {
            this.setOrdineRound();
        }
    }

    // method to set the current die
    public void setDadoSelezionato(Dado dadoSelezionato) {
        this.dadoSelezionato = dadoSelezionato;
    }

    // populate the stack of Tool Cards(12)
    private void InizializzaMazzoCarteUtensile() {
        for (int i = 0; i < 12; i++) {
            mazzoCarteUtensile.add(i);
        }
    }


    // populate the stack of Public Objective Cards(10)
    public void InizializzaMazzoCarteObiettivoPubblico() {
        for (int i = 0; i < 10; i++) {
            mazzoCarteObiettivoPubblico.add(i);
        }
    }

//method to set the List of Tool Cards in multiplayer
    public void setListaCartaUtensile(List<CartaUtensile> listaCartaUtensile) {

        if (listaGiocatori.size() > 1) {
            for (int i = 0; i < 3; i++) {
                int o=mazzoCarteUtensile.remove(random.nextInt(mazzoCarteUtensile.size()));
                listaCartaUtensile.add(FactoryCartaUtensile.getCartaUtensile(o));
            }
        }
    }



    //method to set the List of Tool Cards in singlePlayer, n is the difficult(1-5) chosen by the player
    public void setListaCartaUtensile(List<CartaUtensile> listaCartaUtensile, int n) {
        if (listaGiocatori.size() == 1 && n > 0) {

            for (int i = 0; i < n; i++) {
                int o = mazzoCarteUtensile.remove(random.nextInt(mazzoCarteUtensile.size()));
                listaCartaUtensile.add(FactoryCartaUtensile.getCartaUtensile(o));
            }
        }
    }

    //method to set the List of Public Objective Cards
     public void setListaCartaObiettivoPubblico(List<CartaObiettivoPubblico> listaCartaObiettivoPubblico) {

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

    // various getter

    public List<Dado> getRiserva() {
        return riserva;
    }

    public List<Utente> getListaGiocatori() {
        return listaGiocatori;
    }

    public List<Dado> getSacchetto() {
        return sacchetto;
    }

    public TracciatoDelRound getTracciatoDelRound() {
        return tracciatoDelRound;
    }

    public List<CartaUtensile> getListaCartaUtensile() {
        return listaCartaUtensile;
    }

    public List<CartaObiettivoPubblico> getListaCartaObiettivoPubblico() {
        return listaCartaObiettivoPubblico;
    }

    public Random getRandom() {
        return random;
    }

    public Utente getGiocatoreCorrente() {
        return giocatoreCorrente;
    }

    public List<String> getSetOfColors() {
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

    // end various getter


}

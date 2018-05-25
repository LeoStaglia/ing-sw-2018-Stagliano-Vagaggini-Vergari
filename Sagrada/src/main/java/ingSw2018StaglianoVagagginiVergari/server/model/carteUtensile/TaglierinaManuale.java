package ingSw2018StaglianoVagagginiVergari.server.model.carteUtensile;

import Eccezioni.MossaIllegaleException;
import ingSw2018StaglianoVagagginiVergari.server.model.CartaUtensile;
import ingSw2018StaglianoVagagginiVergari.server.model.Dado;
import ingSw2018StaglianoVagagginiVergari.server.model.Partita;

public class TaglierinaManuale implements CartaUtensile {

    boolean costo ;
    String Nome = "TaglierinaManuale";
    int Id = 12;
    String descrizione="Muovi fino a due dadi dello\n" +
            " stesso colore di un solo dado sul\n Tracciato dei Round\n\n" +
            "Devi rispettare tutte le restrizioni\n" +
            " di piazzamento";

    int r; // indice del dado sul tracciato del round selezionato da cui prendere il colore.
    int i; // coordinata i dado su plancia
    int j; // coordinata j dado su plancia
    int x; // coordinata x nuovo piazzamento su plancia
    int y; // coordinata y nuovo piazzamento su plancia

    // Utente Utilizzatore;   quando avremo dichiarato Utente, decommentare






    //----------------------------all this part is required for Singleton Pattern----------------------------------

    private static TaglierinaManuale instance = new TaglierinaManuale();

    private TaglierinaManuale(){   //il costruttore è privato per il singleton pattern
        costo=false;
    }

    public static TaglierinaManuale getInstance(){
        return instance;
    }


    //----------------------------


    public void setR(int r) {
        this.r = r;
    }

    public void setI(int i) {
        this.i = i;
    }

    public void setJ(int j) {
        this.j = j;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void usaEffettoCarta(Partita p) {
        costo=true;
        String color=null;
        color=p.getTracciatoDelRound().getRimanenzeRiservaOn(r).getColore();

           Dado d;
            if(p.getCurrentPlayer().getPlancia().leggiPlancia(i,j).getColore().equals(color)) {
                d=p.getCurrentPlayer().getPlancia().leggiPlancia(i,j); // d è uguale a Dado selezionato
                p.getCurrentPlayer().getPlancia().rimuoviDado(i, j);
                p.getCurrentPlayer().getPlancia().calcolaMosse(d, false, false);

                try {p.getCurrentPlayer().getPlancia().piazzaDado(x, y, d);}
                catch (MossaIllegaleException e){
                    //TODO gestione Mossa Illegale
                }
            }

            //TODO scelta utente se vuole proseguire o meno al secondo lancio da gestire nel controller
        }

    public int getCosto() {
        if(costo){
            return 2;
        }
        else return 1;
    }

    public int getId() {
        return Id;
    }

    @Override
    public String getDescrizione() {
        return descrizione;
    }

    @Override
    public String getNome() {
        return Nome;
    }
}


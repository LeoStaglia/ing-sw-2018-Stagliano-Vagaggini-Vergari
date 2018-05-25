package ingSw2018StaglianoVagagginiVergari.server.model.carteUtensile;

import ingSw2018StaglianoVagagginiVergari.server.model.CartaUtensile;
import ingSw2018StaglianoVagagginiVergari.server.model.Partita;

public class PinzaSgrossatrice implements CartaUtensile {
    boolean costo ;
    String Nome = "PinzaSgrossatrice";
    int Id = 1;
    int scelta;
    String descrizione="Dopo aver scelto un dado,\n" +
            "aumenta o diminuisci il valore\n" +
            "del dado scelto di 1\n\n" +
            "N.B. Non puoi cambiare un 6 in 1 o un 1 in 6";
    // Utente Utilizzatore;   quando avremo dichiarato Utente, decommentare






    //----------------------------all this part is required for Singleton Pattern----------------------------------

    private static PinzaSgrossatrice instance = new PinzaSgrossatrice();

    private PinzaSgrossatrice(){   //il costruttore è privato per il singleton pattern
        costo=false;
    }

    public static PinzaSgrossatrice getInstance(){
        return instance;
    }


    //----------------------------


    public void setScelta(int scelta) {
        this.scelta = scelta;
    }

    public void usaEffettoCarta(Partita p) {  // viene passata solo la Partita, dato che si ha traccia dell'utente corrente
        costo=true;
        if(scelta==1) p.getDadoSelezionato().incrementa();
        if(scelta==-1) p.getDadoSelezionato().decrementa();


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
}

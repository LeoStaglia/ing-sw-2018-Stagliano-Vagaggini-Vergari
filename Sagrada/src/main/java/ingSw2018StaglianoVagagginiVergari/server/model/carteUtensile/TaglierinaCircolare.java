package ingSw2018StaglianoVagagginiVergari.server.model.carteUtensile;

import ingSw2018StaglianoVagagginiVergari.server.model.CartaUtensile;
import ingSw2018StaglianoVagagginiVergari.server.model.Dado;
import ingSw2018StaglianoVagagginiVergari.server.model.Partita;

public class TaglierinaCircolare implements CartaUtensile {
    boolean costo ;
    String Nome = "TaglierinaCircolare";
    int Id = 5;
    int i;    // i is the number of the round covered by the die
    // Utente Utilizzatore;   quando avremo dichiarato Utente, decommentare






    //----------------------------all this part is required for Singleton Pattern----------------------------------

    private static TaglierinaCircolare instance = new TaglierinaCircolare();

    private TaglierinaCircolare(){   //il costruttore è privato per il singleton pattern
        costo=false;
    }

    public static TaglierinaCircolare getInstance(){
        return instance;
    }


    //----------------------------


    public void usaEffettoCarta(Partita p) {
        costo=true;
        Dado d1;
        d1=p.getDadoSelezionato();
         p.setDadoSelezionato(p.getTracciatoDelRound().getRimanenzeRiservaOn(i));
         p.getTracciatoDelRound().setRimanenzeRiservaOn(i,d1);


    }

    public void setI(int i) {
        this.i = i;
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
}

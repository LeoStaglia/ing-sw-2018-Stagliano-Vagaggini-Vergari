package ingSw2018StaglianoVagagginiVergari.server.model.carteUtensile;
import ingSw2018StaglianoVagagginiVergari.model.*;
import ingSw2018StaglianoVagagginiVergari.server.model.CartaUtensile;

public class PennelloPerEglomise implements CartaUtensile {
    int segnaliniFavore ;
    String Nome = "PennelloPerEglomise";
    int Id = 2;
    // Utente Utilizzatore;   quando avremo dichiarato Utente, decommentare






    //----------------------------all this part is required for Singleton Pattern----------------------------------

    private static PennelloPerEglomise instance = new PennelloPerEglomise();

    private PennelloPerEglomise(){   //il costruttore è privato per il singleton pattern
        segnaliniFavore=0;
    }

    public static PennelloPerEglomise getInstance(){
        return instance;
    }


    //----------------------------




    public void usaEffettoCarta() {  // viene passata solo la Partita, dato che si ha traccia dell'utente corrente
        System.out.println("");

    }





}

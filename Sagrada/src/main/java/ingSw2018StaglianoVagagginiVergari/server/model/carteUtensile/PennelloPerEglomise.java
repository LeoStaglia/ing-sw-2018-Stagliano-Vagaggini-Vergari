package ingSw2018StaglianoVagagginiVergari.server.model.carteUtensile;
import ingSw2018StaglianoVagagginiVergari.server.model.CartaUtensile;
import ingSw2018StaglianoVagagginiVergari.server.model.Partita;

public class PennelloPerEglomise implements CartaUtensile {
    boolean costo ;
    String Nome = "PennelloPerEglomise";
    int Id = 2;
    // Utente Utilizzatore;   quando avremo dichiarato Utente, decommentare






    //----------------------------all this part is required for Singleton Pattern----------------------------------

    private static PennelloPerEglomise instance = new PennelloPerEglomise();

    private PennelloPerEglomise(){   //il costruttore è privato per il singleton pattern
        boolean costo=false ;
    }

    public static PennelloPerEglomise getInstance(){
        return instance;
    }


    //----------------------------




    public void usaEffettoCarta(Partita p) {  // viene passata solo la Partita, dato che si ha traccia dell'utente corrente
        System.out.println("");

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


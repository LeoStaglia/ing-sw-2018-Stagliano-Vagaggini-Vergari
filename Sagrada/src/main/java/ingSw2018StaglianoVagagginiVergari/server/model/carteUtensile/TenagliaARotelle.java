package ingSw2018StaglianoVagagginiVergari.server.model.carteUtensile;

import ingSw2018StaglianoVagagginiVergari.server.model.CartaUtensile;
import ingSw2018StaglianoVagagginiVergari.server.model.Partita;

public class TenagliaARotelle implements CartaUtensile {
    boolean costo ; ;
    String Nome = "TenagliaARotelle";
    int Id = 8;
    // Utente Utilizzatore;   quando avremo dichiarato Utente, decommentare






    //----------------------------all this part is required for Singleton Pattern----------------------------------

    private static TenagliaARotelle instance = new TenagliaARotelle();

    private TenagliaARotelle(){   //il costruttore è privato per il singleton pattern
        costo=false;
    }

    public static TenagliaARotelle getInstance(){
        return instance;
    }


    //----------------------------




    public void usaEffettoCarta(Partita p) {  // viene passata solo la Partita, dato che si ha traccia dell'utente corrente
        costo=true;
        p.setOrdineRoundTool8Start();

        // devo fare in modo di segnalare che questa carta è stata utilizzata al fine di richiamare SetOrdineRoundTool8End
          // oppure richiamo ogni volta setOrdineRoundTool8End()

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

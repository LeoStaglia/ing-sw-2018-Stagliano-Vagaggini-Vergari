package ingSw2018StaglianoVagagginiVergari.server.model.carteUtensile;

import ingSw2018StaglianoVagagginiVergari.server.model.CartaUtensile;
import ingSw2018StaglianoVagagginiVergari.server.model.Partita;

public class Martelletto implements CartaUtensile {
    boolean costo ;
    String Nome = "Martelletto";
    int Id = 7;
    String descrizione="Tira nuovamente\n" +
            "tutti i dadi della Riserva\n\n" +
            "N.B. Questa carta può essera usata\n" +
            "solo durante il tuo secondo turno,\n" +
            "prima di scegliere il secondo dado";
    // Utente Utilizzatore;   quando avremo dichiarato Utente, decommentare






    //----------------------------all this part is required for Singleton Pattern----------------------------------

    private static Martelletto instance = new Martelletto();

    private Martelletto(){   //il costruttore è privato per il singleton pattern
        costo=false;
    }

    public static Martelletto getInstance(){
        return instance;
    }


    //----------------------------




    public void usaEffettoCarta(Partita p) {
        costo=true;
       p.rilanciaDadiInRiserva();

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
package ingSw2018StaglianoVagagginiVergari.server.model.carteUtensile;

import ingSw2018StaglianoVagagginiVergari.server.model.CartaUtensile;
import ingSw2018StaglianoVagagginiVergari.server.model.Partita;


public class PennelloPerEglomise implements CartaUtensile {
    private boolean costo ;
    private String Nome = "Pennello Per Eglomise";
    private int id = 2;
    String descrizione="Muovi un qualsiasi dado nella tua\n" +
            "vetrata ignorando le restrizioni\n" +
            "di colore\n\n" +
            "N.B. Devi rispettare tutte le altre\n" +
            "restrizioni di piazzamento";
    // Utente Utilizzatore;   quando avremo dichiarato Utente, decommentare






    //----------------------------all this part is required for Singleton Pattern----------------------------------

    private static PennelloPerEglomise instance = new PennelloPerEglomise();
    public static PennelloPerEglomise getInstance(){
        return instance;
    }


    //----------------------------



    public void usaEffettoCarta(Partita p) {

        p.getCurrentPlayer().getPlancia().calcolaMosse(p.getDadoSelezionato(), true, false);

    }

    @Override
    public int getCosto() {
        if (costo) {

            return 2;
        }else{
            return 1;
        }
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public String getDescrizione() {
        return descrizione;
    }
}

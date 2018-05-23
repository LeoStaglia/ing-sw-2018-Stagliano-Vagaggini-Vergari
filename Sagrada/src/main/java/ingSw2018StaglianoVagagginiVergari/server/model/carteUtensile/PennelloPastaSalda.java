package ingSw2018StaglianoVagagginiVergari.server.model.carteUtensile;


import Eccezioni.MossaIllegaleException;
import ingSw2018StaglianoVagagginiVergari.server.model.CartaUtensile;
import ingSw2018StaglianoVagagginiVergari.server.model.Partita;

public class PennelloPastaSalda implements CartaUtensile {


    private boolean costo;
    private int id = 6;
    private String Nome = "Pennello Per Pasta Salda";
    private int xCell;
    private int yCell;


    @Override
    public void usaEffettoCarta(Partita p) {
        Boolean[][] mossePermesse;
        p.getDadoSelezionato().lanciaDado();
        boolean piazzabile = false;

        mossePermesse = p.getCurrentPlayer().getPlancia().calcolaMosse(p.getDadoSelezionato(), false, false);
        for (int i=0; !piazzabile && i<4; i++){
            for (int j=0; j<5; j++){
                if (mossePermesse[i][j]== true){
                    piazzabile=true;
                    break;
                }
            }
        }

        if (!piazzabile){
            p.reInserisciDadoinRiserva(p.getDadoSelezionato());
        }else{
            try{
                p.getCurrentPlayer().getPlancia().piazzaDado(xCell, yCell, p.getDadoSelezionato());
            }catch(MossaIllegaleException e){
                //TODO notifica piazzamento impossibile alla View
            }
        }


    }

    @Override
    public int getCosto() {
         if (costo){
             return 2;
         }else
             return 1;
    }

    @Override
    public int getId() {
        return id;
    }
}

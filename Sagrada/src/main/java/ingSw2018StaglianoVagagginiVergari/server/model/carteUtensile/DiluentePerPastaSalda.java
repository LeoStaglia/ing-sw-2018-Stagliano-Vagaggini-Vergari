package ingSw2018StaglianoVagagginiVergari.server.model.carteUtensile;

import Eccezioni.MossaIllegaleException;
import ingSw2018StaglianoVagagginiVergari.server.model.CartaUtensile;
import ingSw2018StaglianoVagagginiVergari.server.model.Dado;
import ingSw2018StaglianoVagagginiVergari.server.model.Partita;


public class DiluentePerPastaSalda implements CartaUtensile {

    private boolean costo ;
    private String Nome = "DiluentePerPastaSalda";
    private int id = 11;








    //----------------------------all this part is required for Singleton Pattern----------------------------------

    private static DiluentePerPastaSalda instance = new DiluentePerPastaSalda();

    public static DiluentePerPastaSalda getInstance(){
        return instance;
    }


    //----------------------------


    @Override
    public void usaEffettoCarta(Partita PartitaCorrente)  {

        Dado DadoInMano;

        //todo passed from the view
        int scelta=0;
        int numeroScelto=0;
        int x=0;
        int y=0;


        DadoInMano = PartitaCorrente.getDadofromRiserva(scelta);

        PartitaCorrente.reInserisciDadoInSacchetto(DadoInMano);

        DadoInMano = PartitaCorrente.getDadoRandomFromSacchetto();

        String Colore = DadoInMano.getColore();

        DadoInMano = new Dado(Colore,numeroScelto);

        PartitaCorrente.getCurrentPlayer().getPlancia().calcolaMosse(DadoInMano,false,false);


        try {
            PartitaCorrente.getCurrentPlayer().getPlancia().piazzaDado(x,y,DadoInMano);
        }
        catch (MossaIllegaleException e){
            //todo need to define the behaviour
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

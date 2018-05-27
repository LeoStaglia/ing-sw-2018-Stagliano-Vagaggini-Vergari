package ingSw2018StaglianoVagagginiVergari.server.model.carteUtensile;


import Eccezioni.MossaIllegaleException;
import ingSw2018StaglianoVagagginiVergari.server.model.CartaUtensile;
import ingSw2018StaglianoVagagginiVergari.server.model.Dado;
import ingSw2018StaglianoVagagginiVergari.server.model.Partita;

import java.rmi.RemoteException;

public class AlesatoreRame implements CartaUtensile {

    private boolean costo ;
    private String Nome = "Alesatore per lamina di rame";
    private int id = 3;
    private int xDie;
    private int yDie;
    private int xCell;
    private int yCell;
    String descrizione="Muovi un qualsiasi dado nella tua\n" +
            "vetrata ignorando le restrizioni\n" +
            "di valore\n\n" +
            "N.B. Devi rispettare tutte le altre\n" +
            "restrizioni di piazzamento";


    // Utente Utilizzatore;   quando avremo dichiarato Utente, decommentare






    //----------------------------all this part is required for Singleton Pattern----------------------------------

    private static AlesatoreRame instance = new AlesatoreRame();
    public static AlesatoreRame getInstance(){
        return instance;
    }

    //----------------------------

    @Override
    public void usaEffettoCarta(Partita p) throws RemoteException{
        costo=true;
        Dado d= p.getCurrentPlayer().getPlancia().leggiPlancia(xDie, yDie);
        p.getCurrentPlayer().getPlancia().rimuoviDado(xDie, yDie);
        p.getCurrentPlayer().getPlancia().calcolaMosse(d, false, true);
        try{
            p.getCurrentPlayer().getPlancia().piazzaDado(xCell, yCell, d);
        }catch(MossaIllegaleException e){

        }
        p.updateGenerale();
    }

    @Override
    public int getCosto() {
        if(costo){
            return 2;
        }
        else return 1;
    }

    @Override
    public int getId() {
        return id;
    }


    @Override
    public String getDescrizione() {
        return descrizione;
    }

    @Override
    public String getNome() {
        return Nome;
    }

    public void setxDie(int xDie) {
        this.xDie = xDie;
    }

    public void setyDie(int yDie) {
        this.yDie = yDie;
    }

    public void setxCell(int xCell) {
        this.xCell = xCell;
    }

    public void setyCell(int yCell) {
        this.yCell = yCell;
    }
}



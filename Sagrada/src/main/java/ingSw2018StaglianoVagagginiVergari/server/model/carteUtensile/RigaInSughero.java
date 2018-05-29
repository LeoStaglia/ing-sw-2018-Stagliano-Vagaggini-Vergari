package ingSw2018StaglianoVagagginiVergari.server.model.carteUtensile;


import Eccezioni.MossaIllegaleException;
import ingSw2018StaglianoVagagginiVergari.server.model.CartaUtensile;
import ingSw2018StaglianoVagagginiVergari.server.model.Partita;

import java.rmi.RemoteException;

public class RigaInSughero implements CartaUtensile {

    private boolean costo ;
    private String Nome = "Riga In Sughero";
    private int id = 9;
    String descrizione="Dopo aver scelto un dado,\n" +
            "piazzalo in una casella che non\n" +
            "sia adiacente a un altro dado\n\n" +
            "Devi rispettare tutte le restrizioni\n" +
            "di piazzamento";
    private int xCell;
    private int yCell;






    //----------------------------all this part is required for Singleton Pattern----------------------------------

    private static RigaInSughero instance = new RigaInSughero();
    public static RigaInSughero getInstance(){
        return instance;
    }

    //----------------------------

    @Override
    public void usaEffettoCarta(Partita PartitaCorrente) throws RemoteException {
        costo=true;
        PartitaCorrente.getCurrentPlayer().getPlancia().Tool9(PartitaCorrente.getDadoSelezionato());  //versione in cui il metodo è dentro a Plancia
        try {PartitaCorrente.getCurrentPlayer().getPlancia().piazzaDado(xCell, yCell,PartitaCorrente.getDadoSelezionato());}
        catch (MossaIllegaleException e){}
        PartitaCorrente.updateGenerale();

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

    public void setxCell(int xCell) {
        this.xCell = xCell;
    }

    public void setyCell(int yCell) {
        this.yCell = yCell;
    }
}

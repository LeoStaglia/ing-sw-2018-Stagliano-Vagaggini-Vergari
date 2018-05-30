package ingSw2018StaglianoVagagginiVergari.server.model.carteUtensile;

import Eccezioni.MossaIllegaleException;
import ingSw2018StaglianoVagagginiVergari.server.model.CartaUtensile;
import ingSw2018StaglianoVagagginiVergari.server.model.Dado;
import ingSw2018StaglianoVagagginiVergari.server.model.Partita;

import java.rmi.Remote;
import java.rmi.RemoteException;


public class Lathekin implements CartaUtensile {

    private boolean costo ;
    private String Nome = "Lathekin";
    private int id = 4;
    String descrizione="Muovi esattamente due dadi,\n" +
            "rispettando tutte le restrizioni di\n" +
            "piazzamento";
    private int xDie;
    private int yDie;
    private int xCell;
    private int yCell;
    private boolean secondPhase=false;
    // Utente Utilizzatore;   quando avremo dichiarato Utente, decommentare


    /*scelta implementativa:i dadi vengono mossi uno per volta, quindi si potrà muovere il secondo dado solo dopo aver piazzato il primo
    con le relative nuove restrizioni introdotte*/



    //----------------------------all this part is required for Singleton Pattern----------------------------------

    private static Lathekin instance = new Lathekin();

    public static Lathekin getInstance(){
        return instance;
    }


    //----------------------------


    @Override
    public void usaEffettoCarta(Partita p) throws RemoteException{
        costo=true;
        if (p.getCurrentPlayer().getPlancia().leggiPlancia(xDie, yDie)!=null){
            Dado d = p.getCurrentPlayer().getPlancia().leggiPlancia(xDie, yDie); // d è uguale a Dado selezionato
            p.getCurrentPlayer().getPlancia().rimuoviDado(xDie, yDie);
            p.getCurrentPlayer().getPlancia().calcolaMosse(d, false, false);
            try{
                p.getCurrentPlayer().getPlancia().piazzaDado(xCell, yCell, d);
                if (!secondPhase){
                    p.getAzioniGiocatore().add(2);
                    secondPhase=true;
                }else{
                    secondPhase=false;
                }
            }catch(MossaIllegaleException e){
                //TODO notifica di piazzamento impossibile alla View
            }
            p.updateGenerale();
        }else{
            //TODO notifica di errore delle coordinate del dado alla View
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

    @Override
    public String getDescrizione() {
        return descrizione;
    }

    @Override
    public String getNome() {
        return Nome;
    }
}

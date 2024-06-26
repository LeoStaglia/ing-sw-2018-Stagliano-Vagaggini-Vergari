package ingSw2018StaglianoVagagginiVergari.server.model.carteUtensile;

import Eccezioni.MossaIllegaleException;
import ingSw2018StaglianoVagagginiVergari.server.model.CartaUtensile;
import ingSw2018StaglianoVagagginiVergari.server.model.Dado;
import ingSw2018StaglianoVagagginiVergari.server.model.Partita;

import java.rmi.RemoteException;


public class PennelloPerEglomise implements CartaUtensile {
    private boolean costo ;
    private String Nome = "Pennello Per Eglomise";
    private int id = 2;
    private int xDie;
    private int yDie;
    private int xCell;
    private int yCell;
    String descrizione="Muovi un qualsiasi dado nella tua\n" +
            "vetrata ignorando le restrizioni\n" +
            "di colore\n\n" +
            "N.B. Devi rispettare tutte le altre\n" +
            "restrizioni di piazzamento";
    // Utente Utilizzatore;   quando avremo dichiarato Utente, decommentare




    public void usaEffettoCarta(Partita p) throws RemoteException {
        Dado[][] grigliaGiocoCopy=new Dado[4][5];
        setGrigliaGiocoCopy(p,grigliaGiocoCopy);
        if (p.getCurrentPlayer().getPlancia().leggiPlancia(xDie, yDie)!=null) {
            Dado d = p.getCurrentPlayer().getPlancia().leggiPlancia(xDie, yDie);
            p.getCurrentPlayer().getPlancia().rimuoviDado(xDie, yDie);
            p.getCurrentPlayer().getPlancia().calcolaMosse(d, true, false);
            try {
                p.getCurrentPlayer().getPlancia().piazzaDado(xCell, yCell, d);
                p.getCurrentPlayer().setSegnalini(p.getCurrentPlayer().getSegnalini() - getCosto());
                p.getAzioniGiocatore().remove(2);
                costo = true;
                p.updateGenerale();
            } catch (MossaIllegaleException e) {
                p.getCurrentPlayer().getPlancia().setGrigliaGioco(grigliaGiocoCopy);
                p.updateMessage("MOSSA NON VALIDA !!");
                p.updateCurrentPlayer();
            }
        }else {
            p.getCurrentPlayer().getPlancia().setGrigliaGioco(grigliaGiocoCopy);
            p.updateMessage("MOSSA NON VALIDA !!");
            p.updateCurrentPlayer();
        }



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

    public void setGrigliaGiocoCopy(Partita p,Dado[][] grigliaGiocoCopy) {
        for(int i=0;i<4;i++){
            for(int j=0;j<5;j++){
                grigliaGiocoCopy[i][j]=p.getCurrentPlayer().getPlancia().getGrigliaGioco()[i][j];
            }
        }
    }

    @Override
    public void reset() {

    }
}

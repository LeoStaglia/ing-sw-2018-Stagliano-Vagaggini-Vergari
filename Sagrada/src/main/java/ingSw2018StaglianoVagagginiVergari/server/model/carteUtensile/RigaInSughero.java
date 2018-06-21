package ingSw2018StaglianoVagagginiVergari.server.model.carteUtensile;


import Eccezioni.MossaIllegaleException;
import ingSw2018StaglianoVagagginiVergari.server.model.CartaUtensile;
import ingSw2018StaglianoVagagginiVergari.server.model.Dado;
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


    @Override
    public void usaEffettoCarta(Partita PartitaCorrente) throws RemoteException {
        Dado[][] grigliaGiocoCopy=new Dado[4][5];
        setGrigliaGiocoCopy(PartitaCorrente,grigliaGiocoCopy);
        if(PartitaCorrente.getAzioniGiocatore().contains(1)) {
            PartitaCorrente.getCurrentPlayer().getPlancia().Tool9(PartitaCorrente.getDadoSelezionato());  //versione in cui il metodo è dentro a Plancia
            try {
                PartitaCorrente.getCurrentPlayer().getPlancia().piazzaDado(xCell, yCell, PartitaCorrente.getDadoSelezionato());
                PartitaCorrente.getCurrentPlayer().setSegnalini(PartitaCorrente.getCurrentPlayer().getSegnalini() - getCosto());
                PartitaCorrente.getAzioniGiocatore().remove(2);
                PartitaCorrente.getAzioniGiocatore().remove(1);
                costo = true;
                PartitaCorrente.updateGenerale();

            } catch (MossaIllegaleException e) {
                PartitaCorrente.reInserisciDadoinRiserva(PartitaCorrente.getDadoSelezionato());
                PartitaCorrente.getCurrentPlayer().getPlancia().setGrigliaGioco(grigliaGiocoCopy);
                PartitaCorrente.updateMessage("MOSSA NON VALIDA !!");
                PartitaCorrente.updateCurrentPlayer();
            }
        }
        else {
            PartitaCorrente.reInserisciDadoinRiserva(PartitaCorrente.getDadoSelezionato());
            PartitaCorrente.updateMessage("HAI GIA' PIAZZATO UN DADO IN QUESTO TURNO!!");
            PartitaCorrente.updateCurrentPlayer();
        }


        //TODO CASO NON PIAZZABILE--Mauritimo


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

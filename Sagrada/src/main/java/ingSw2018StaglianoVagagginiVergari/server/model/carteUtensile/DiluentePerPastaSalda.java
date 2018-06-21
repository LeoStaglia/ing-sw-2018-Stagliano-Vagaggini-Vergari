package ingSw2018StaglianoVagagginiVergari.server.model.carteUtensile;

import Eccezioni.MossaIllegaleException;
import ingSw2018StaglianoVagagginiVergari.common.GameObserver;
import ingSw2018StaglianoVagagginiVergari.server.model.CartaUtensile;
import ingSw2018StaglianoVagagginiVergari.server.model.Dado;
import ingSw2018StaglianoVagagginiVergari.server.model.Partita;

import java.rmi.RemoteException;
import java.util.ArrayList;


public class DiluentePerPastaSalda implements CartaUtensile {

    private boolean costo ;
    private String Nome = "Diluente Per Pasta Salda";
    private int id = 11;
    int scelta;
    int numeroScelto;
    int x;
    int y;
    boolean secondPhase=false;
   private Dado[][] grigliaGiocoCopy=new Dado[4][5];
   private ArrayList<Dado> riservaCopy=new ArrayList<>();
    String descrizione="Dopo aver scelto un dado, riponilo nel Sacchetto,\n" +
            "poi pescane uno dal Sacchetto\n\n"+
            "N.B. Scegli il valore del nuovo dado e\n" +
            " piazzalo, rispettando tutte le restrizioni di\n" +
            "piazzamento";




    @Override
    public void usaEffettoCarta(Partita PartitaCorrente) throws RemoteException {

        if (!secondPhase){
            setGrigliaGiocoCopy(PartitaCorrente,grigliaGiocoCopy);
            setRiservaCopy(PartitaCorrente,riservaCopy);
            PartitaCorrente.getAzioniGiocatore().add(2);
            secondPhase=true;
            PartitaCorrente.updateGenerale();
            PartitaCorrente.updateTool11(PartitaCorrente.getDadoSelezionato().toString());

        }else{
            secondPhase=false;
            PartitaCorrente.getCurrentPlayer().getPlancia().calcolaMosse(PartitaCorrente.getDadoSelezionato(),false,false);
            try {
                PartitaCorrente.getAzioniGiocatore().remove(2); //se si arriva a questo punto ho già eseguito la prima parte dell'effetto
                costo=true;
                PartitaCorrente.getCurrentPlayer().getPlancia().piazzaDado(x,y,PartitaCorrente.getDadoSelezionato());
                PartitaCorrente.getAzioniGiocatore().remove(1);
                PartitaCorrente.getCurrentPlayer().setSegnalini(PartitaCorrente.getCurrentPlayer().getSegnalini() - getCosto());
                PartitaCorrente.updateGenerale();
            }
            catch (MossaIllegaleException e){
             //   PartitaCorrente.getCurrentPlayer().getPlancia().setGrigliaGioco(grigliaGiocoCopy);
               // PartitaCorrente.setRiserva(riservaCopy);
               // riservaCopy.clear();
                PartitaCorrente.reInserisciDadoinRiserva(PartitaCorrente.getDadoSelezionato());
                PartitaCorrente.updateMessage("MOSSA NON VALIDA IL DADO è STATO REINSERITO IN RISERVA !!");
                PartitaCorrente.updateCurrentPlayer();
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

    @Override
    public String getDescrizione() {
        return descrizione;
    }

    @Override
    public String getNome() {
        return Nome;
    }

    public void setScelta(int scelta) {
        this.scelta = scelta;
    }

    public void setNumeroScelto(int numeroScelto) {
        this.numeroScelto = numeroScelto;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public boolean isSecondPhase() {
        return secondPhase;
    }

    public void setGrigliaGiocoCopy(Partita p,Dado[][] grigliaGiocoCopy) {
        for(int i=0;i<4;i++){
            for(int j=0;j<3;j++){
                grigliaGiocoCopy[i][j]=p.getCurrentPlayer().getPlancia().getGrigliaGioco()[i][j];
            }
        }
    }

    public void setRiservaCopy(Partita p,ArrayList<Dado> riservaCopy){
        for (Dado d:p.getRiserva()) {
            riservaCopy.add(d);
        }

    }


    @Override
    public void reset() {
        secondPhase=false;
        grigliaGiocoCopy=new Dado[4][5];
        riservaCopy=new ArrayList<>();
    }
}

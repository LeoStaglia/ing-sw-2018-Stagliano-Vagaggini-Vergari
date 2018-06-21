package ingSw2018StaglianoVagagginiVergari.server.model.carteUtensile;

import ingSw2018StaglianoVagagginiVergari.server.model.CartaUtensile;
import ingSw2018StaglianoVagagginiVergari.server.model.Partita;

import java.rmi.RemoteException;

public class PinzaSgrossatrice implements CartaUtensile {
    boolean costo ;
    String Nome = "Pinza Sgrossatrice";
    int Id = 1;
    int scelta;
    String descrizione="Dopo aver scelto un dado,\n" +
            "aumenta o diminuisci il valore\n" +
            "del dado scelto di 1\n\n" +
            "N.B. Non puoi cambiare un 6 in 1 o un 1 in 6";
    // Utente Utilizzatore;   quando avremo dichiarato Utente, decommentare




    public void setScelta(int scelta) {
        this.scelta = scelta;
    }

    public void usaEffettoCarta(Partita p) throws RemoteException {  // viene passata solo la Partita, dato che si ha traccia dell'utente corrente
        if(scelta==1) {
            if(p.getDadoSelezionato().incrementa());
            else {
                p.reInserisciDadoinRiserva(p.getDadoSelezionato());
                p.updateMessage("NON PUOI INCREMENTARE UN 6 !!!");
                p.updateCurrentPlayer();
                return;
            }
        }
        if(scelta==-1){
            if(p.getDadoSelezionato().decrementa());
            else {
                p.reInserisciDadoinRiserva(p.getDadoSelezionato());
                p.updateMessage("NON PUOI DECREMENTARE UN 1 !!!");
                p.updateCurrentPlayer();
                return;
            }
        }
        p.reInserisciDadoinRiserva(p.getDadoSelezionato());
        p.getCurrentPlayer().setSegnalini(p.getCurrentPlayer().getSegnalini() - getCosto());
        p.getAzioniGiocatore().remove(2);
        costo=true;
        p.updateGenerale();


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

    @Override
    public void reset() {

    }
}

package ingSw2018StaglianoVagagginiVergari.server.model.carteUtensile;

import ingSw2018StaglianoVagagginiVergari.server.model.CartaUtensile;
import ingSw2018StaglianoVagagginiVergari.server.model.Partita;
import ingSw2018StaglianoVagagginiVergari.server.model.Utente;

import java.rmi.RemoteException;

public class Martelletto implements CartaUtensile {
    boolean costo ;
    String Nome = "Martelletto";
    int Id = 7;
    String descrizione="Tira nuovamente\n" +
            "tutti i dadi della Riserva\n\n" +
            "N.B. Questa carta può essera usata\n" +
            "solo durante il tuo secondo turno,\n" +
            "prima di scegliere il secondo dado";
    // Utente Utilizzatore;   quando avremo dichiarato Utente, decommentare




    public void usaEffettoCarta(Partita p) throws RemoteException {
        int conta=0;
        for(int i=p.getTurno()-1;i<p.getOrdineRound().size();i++){
            if(p.getOrdineRound().get(i)==p.getCurrentPlayer()) conta++;
        }

        if(conta==1 && p.getAzioniGiocatore().contains(1)) {
            costo=true;
            p.rilanciaDadiInRiserva();
            p.getCurrentPlayer().setSegnalini(p.getCurrentPlayer().getSegnalini() - getCosto());
            p.getAzioniGiocatore().remove(2);
            p.updateGenerale();
        }
        else {
            if(conta==2){
                p.updateMessage("NON E'IL TUO SECONDO TURNO");
                p.updateCurrentPlayer();
            }
            else{
                p.updateMessage("HAI GIA' PIAZZATO UN DADO IN QUESTO TURNO !!!");
                p.updateCurrentPlayer();
            }
        }


       //TODO eccezione in caso si sia al primo turno? o la carta non ha effetto?


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

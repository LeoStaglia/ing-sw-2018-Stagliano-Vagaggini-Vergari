package ingSw2018StaglianoVagagginiVergari.server.model.carteUtensile;

import ingSw2018StaglianoVagagginiVergari.server.model.CartaUtensile;
import ingSw2018StaglianoVagagginiVergari.server.model.Dado;
import ingSw2018StaglianoVagagginiVergari.server.model.Partita;

import java.rmi.RemoteException;

public class TaglierinaCircolare implements CartaUtensile {
    boolean costo ;
    String Nome = "Taglierina Circolare";
    int Id = 5;
    String descrizione="Dopo aver scelto un dado,\n" +
            "scambia quel dado con un dado\n" +
            "sul Tracciato dei Round";
    int i;    // i is the number of the round covered by the die
    // Utente Utilizzatore;   quando avremo dichiarato Utente, decommentare




    public void usaEffettoCarta(Partita p) throws RemoteException {
        p.reInserisciDadoinRiserva(p.getTracciatoDelRound().removeRimanenzeRiservaOn(i));
        p.getTracciatoDelRound().setRimanenzeRiservaOn(i,p.getDadoSelezionato());
        p.getCurrentPlayer().setSegnalini(p.getCurrentPlayer().getSegnalini() - getCosto());
        p.getAzioniGiocatore().remove(2);
        costo=true;
        p.updateGenerale();


    }

    public void setI(int i) {
        this.i = i;
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

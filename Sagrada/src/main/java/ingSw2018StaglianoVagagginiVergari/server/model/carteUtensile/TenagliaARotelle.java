package ingSw2018StaglianoVagagginiVergari.server.model.carteUtensile;

import ingSw2018StaglianoVagagginiVergari.server.model.CartaUtensile;
import ingSw2018StaglianoVagagginiVergari.server.model.Partita;

import java.rmi.RemoteException;

public class TenagliaARotelle implements CartaUtensile {
    boolean costo ; ;
    String Nome = "Tenaglia A Rotelle";
    int Id = 8;
    String descrizione="Dopo il tuo primo turno scegli\n" +
            "immediatamente un altro dado\n\n" +
            "N.B. Salta il tuo secondo turno in\n" +
            "questo round";
    // Utente Utilizzatore;   quando avremo dichiarato Utente, decommentare






    //----------------------------all this part is required for Singleton Pattern----------------------------------

    private static TenagliaARotelle instance = new TenagliaARotelle();

    private TenagliaARotelle(){   //il costruttore è privato per il singleton pattern
        costo=false;
    }

    public static TenagliaARotelle getInstance(){
        return instance;
    }


    //----------------------------




    public void usaEffettoCarta(Partita p) throws RemoteException{  // viene passata solo la Partita, dato che si ha traccia dell'utente corrente
        int conta=0;
        for(int i=p.getTurno()-1;i<p.getOrdineRound().size();i++){
            if(p.getOrdineRound().get(i)==p.getCurrentPlayer()) conta++;
        }

        if(conta==2) {
            p.setOrdineRoundTool8Start();
            p.getCurrentPlayer().setSegnalini(p.getCurrentPlayer().getSegnalini() - getCosto());
            p.getAzioniGiocatore().remove(2);
            p.getAzioniGiocatore().add(4);
            costo = true;
            p.updateGenerale();
        }
        else{
            p.updateMessage("NON PUOI USARE QUESTA CARTA NEL TUO SECONDO TURNO");
            p.updateCurrentPlayer();
        }

        // devo fare in modo di segnalare che questa carta è stata utilizzata al fine di richiamare SetOrdineRoundTool8End
          // oppure richiamo ogni volta setOrdineRoundTool8End()

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
}

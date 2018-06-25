package ingSw2018StaglianoVagagginiVergari.server.model.carteUtensile;


import Eccezioni.MossaIllegaleException;
import ingSw2018StaglianoVagagginiVergari.common.GameObserver;
import ingSw2018StaglianoVagagginiVergari.server.model.CartaUtensile;
import ingSw2018StaglianoVagagginiVergari.server.model.Partita;

import java.rmi.RemoteException;

public class PennelloPastaSalda implements CartaUtensile {


    private boolean costo;
    private int id = 6;
    String descrizione="Dopo aver scelto un dado, tira\n" +
            "nuovamente quel dado\n\n" +
            "Se non puoi piazzarlo, riponilo\n" +
            "nella Riserva";
    private String Nome = "Pennello Per Pasta Salda";
    private int xCell;
    private int yCell;
    private boolean fase1=true;
    private boolean piazzabile=false;



    @Override
    public void usaEffettoCarta(Partita p) throws RemoteException,MossaIllegaleException{
        Boolean[][] mossePermesse;
        if(fase1) {
            p.getAzioniGiocatore().remove(2);
            p.getDadoSelezionato().lanciaDado();


            mossePermesse = p.getCurrentPlayer().getPlancia().calcolaMosse(p.getDadoSelezionato(), false, false);
            for (int i = 0; !piazzabile && i < 4; i++) {
                for (int j = 0; j < 5; j++) {
                    if (mossePermesse[i][j]) {
                        piazzabile = true;
                        break;
                    }
                }
            }
           // for (String username: p.getGameObservers().keySet()){
                GameObserver view = p.getGameObservers().get(p.getCurrentPlayer().getId());
                view.updateViewTool6Bool(piazzabile);
                if (piazzabile){
                    p.updateTool6piazzato(false);
                    view.updateViewTool6Die(p.getDadoSelezionato().toString());
                    fase1=false;
                }
            //}
            if (!piazzabile){
                p.reInserisciDadoinRiserva(p.getDadoSelezionato());
                p.getCurrentPlayer().setSegnalini(p.getCurrentPlayer().getSegnalini() - getCosto());
                p.getAzioniGiocatore().remove(2);
                costo=true;
                p.updateTool6piazzato(false);
                p.updateMessage("Il dado pescato non è piazzabile, è stato reinserito in riserva !!");
                p.updateGenerale();
            }
        }else{
            try{
                p.getCurrentPlayer().getPlancia().piazzaDado(xCell, yCell, p.getDadoSelezionato());
                piazzabile=false;
                p.getCurrentPlayer().setSegnalini(p.getCurrentPlayer().getSegnalini() - getCosto());
                p.getAzioniGiocatore().remove(2);
                p.getAzioniGiocatore().remove(1);
                costo=true;
                p.updateTool6piazzato(true);
                p.updateGenerale();
                fase1=true;

            }catch(MossaIllegaleException e){
                fase1=false;
                p.updateTool6piazzato(false);
                p.updateMessage("MOSSA NON VALIDA !!");
                p.updateCurrentPlayer();
                throw e;
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

    public void setxCell(int xCell) {
        this.xCell = xCell;
    }

    public void setyCell(int yCell) {
        this.yCell = yCell;
    }

    public void setFase(boolean fase1) {
        this.fase1 = fase1;
    }

    public void setPiazzabile(boolean piazzabile) {
        this.piazzabile = piazzabile;
    }

    @Override
    public String getDescrizione() {
        return descrizione;
    }

    @Override
    public String getNome() {
        return Nome;
    }

    public boolean isFase1() {
        return fase1;
    }

    public boolean isPiazzabile() {
        return piazzabile;
    }

    @Override
    public void reset() {
        fase1=true;
        piazzabile=false;
    }
}

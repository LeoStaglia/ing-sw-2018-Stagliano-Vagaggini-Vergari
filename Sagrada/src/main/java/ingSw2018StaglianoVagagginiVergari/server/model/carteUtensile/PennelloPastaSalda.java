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


//----------------------------all this part is required for Singleton Pattern----------------------------------

    private static PennelloPastaSalda instance = new PennelloPastaSalda();
    public static PennelloPastaSalda getInstance(){
        return instance;
    }


    //----------------------------




    @Override
    public void usaEffettoCarta(Partita p) throws RemoteException, MossaIllegaleException{
        costo=true;
        Boolean[][] mossePermesse;
        if(fase1) {
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
            for (GameObserver view: p.getGameObservers()){
                view.updateViewTool6Bool(piazzabile);
                if (piazzabile){
                    view.updateViewTool6Die(p.getDadoSelezionato().toString());
                    fase1=false;
                }
            }
            if (!piazzabile){
                p.reInserisciDadoinRiserva(p.getDadoSelezionato());
                p.updateGenerale();
            }
        }else{
            try{
                p.getCurrentPlayer().getPlancia().piazzaDado(xCell, yCell, p.getDadoSelezionato());
                piazzabile=false;
                p.updateGenerale();
                fase1=true;

            }catch(MossaIllegaleException e){
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
}

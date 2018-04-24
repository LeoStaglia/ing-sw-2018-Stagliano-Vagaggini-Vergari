package ingSw2018StaglianoVagagginiVergari.model;

import java.io.Serializable;

public class Utente implements Serializable{

    //here we use an array for a future implementation of single player mode;
    private CartaObiettivoPrivato[] obiettivoPrivato;
    private Plancia plancia;
    private int segnalini;
    private String id;

    public String getId() {
        return id;
    }

    //multiplayer constructor
    public Utente(Plancia plancia, Constraint coloreObiettivoPrivato){

        this.plancia=plancia;
        obiettivoPrivato = new CartaObiettivoPrivato[1];
        obiettivoPrivato[1] = new CartaObiettivoPrivato(coloreObiettivoPrivato);

    }
    //singleplayer constructor
    public Utente(Plancia plancia, Constraint coloreObiettivoPrivato1, Constraint coloreObiettivoPrivato2){

        this.plancia=plancia;
        obiettivoPrivato = new CartaObiettivoPrivato[2];
        obiettivoPrivato[1] = new CartaObiettivoPrivato(coloreObiettivoPrivato1);
        obiettivoPrivato[2] = new CartaObiettivoPrivato(coloreObiettivoPrivato2);

    }

    public CartaObiettivoPrivato[] getObiettivoPrivato() {
        CartaObiettivoPrivato[] risultato;
        if (obiettivoPrivato.length==1){
            risultato = new CartaObiettivoPrivato[1];
            risultato[1] = new CartaObiettivoPrivato(obiettivoPrivato[1].getColore());
        }else{
            risultato = new CartaObiettivoPrivato[2];
            risultato[1] = new CartaObiettivoPrivato(obiettivoPrivato[1].getColore());
            risultato[2] = new CartaObiettivoPrivato(obiettivoPrivato[2].getColore());
        }
        return risultato;
    }
    public void scegliFacciaSchema(boolean fronteScelto){

        plancia.getCartaSchema().scegliFaccia(fronteScelto);
        if (fronteScelto){
            setSegnalini(plancia.getCartaSchema().getDifficoltaFronte());
        }else{
            setSegnalini(plancia.getCartaSchema().getDifficoltaRetro());
        }


    }
    public Plancia getPlancia(){
        return plancia;
    }

    public int getSegnalini() {
        return segnalini;
    }

    private void setSegnalini(int difficolta){
        this.segnalini=difficolta;
    }
}

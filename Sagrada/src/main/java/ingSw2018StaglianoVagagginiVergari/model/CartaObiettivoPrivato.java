package ingSw2018StaglianoVagagginiVergari.model;

import java.io.Serializable;

public class CartaObiettivoPrivato implements Serializable {
    private Constraint colore;

    public CartaObiettivoPrivato(Constraint colore){
        this.colore=colore;
    }

    public Constraint getColore() {
        return colore;
    }

    @Override
    public String toString(){
        return "Somma dei valori su tutti i dadi di colore "+colore.getDescrizione();
    }
}

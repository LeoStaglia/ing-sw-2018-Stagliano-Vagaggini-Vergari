package ingSw2018StaglianoVagagginiVergari.server.model;

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
        return colore.getDescrizione();
    }
}

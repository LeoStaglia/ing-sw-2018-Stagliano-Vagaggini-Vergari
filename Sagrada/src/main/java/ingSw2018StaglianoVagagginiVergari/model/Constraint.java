package ingSw2018StaglianoVagagginiVergari.model;

import java.io.Serializable;

public enum Constraint implements Serializable{
    NONE("N"), GIALLO("Giallo"), ROSSO("Rosso"), VIOLA("Viola"), VERDE("Verde"), BLU("Blu"), UNO("1"), DUE("2"), TRE("3"), QUATTRO("4"), CINQUE("5"), SEI("6");
    private String descrizione;
    Constraint(String descrizione){
        this.descrizione=descrizione;
    }

    public String getDescrizione() {
        return descrizione;
    }
}

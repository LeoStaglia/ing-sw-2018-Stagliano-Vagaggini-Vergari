package ingSw2018StaglianoVagagginiVergari.model.carteSchema;

import ingSw2018StaglianoVagagginiVergari.model.Constraint;

import java.io.Serializable;

public abstract class CartaSchema implements Serializable, Schema{
    private String nomeFronte;
    private String nomeRetro;
    private int difficoltaFronte;
    private int difficoltaRetro;
    private Constraint[][] fronte;
    private Constraint[][] retro;
    private boolean fronteScelto;

    public CartaSchema(String nomeFronte, String nomeRetro, int difficoltaFronte, int difficoltaRetro, Constraint[][] fronte, Constraint[][] retro){
        this.nomeFronte=nomeFronte;
        this.nomeRetro=nomeRetro;
        this.difficoltaFronte=difficoltaFronte;
        this.difficoltaRetro=difficoltaRetro;
        this.fronte=fronte;
        this.retro=retro;
    }

    public  Constraint getRestrizione(int i, int j){
        if (fronteScelto){
            return fronte[i][j];
        }else{
            return retro[i][j];
        }
    }

    public void scegliFaccia(boolean fronteScelto){
        this.fronteScelto=fronteScelto;
    }

    @Override
    public int getDifficoltaFronte() {
        return difficoltaFronte;
    }

    @Override
    public int getDifficoltaRetro() {
        return difficoltaRetro;
    }

    public String getNomeFronte() {
        return nomeFronte;
    }

    public String getNomeRetro() {
        return nomeRetro;
    }
}

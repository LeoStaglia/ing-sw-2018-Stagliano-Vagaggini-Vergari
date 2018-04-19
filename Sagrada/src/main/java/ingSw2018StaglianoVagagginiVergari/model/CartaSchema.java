package ingSw2018StaglianoVagagginiVergari.model;

import java.io.Serializable;

public abstract class CartaSchema implements Serializable{
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


    public void stampaCarta(){
        if (fronteScelto){
            for(int i=0; i<fronte.length; i++){
                for (int j=0; j<fronte[i].length; j++){
                    if (j<=fronte[i].length-2)
                        System.out.print(fronte[i][j].getDescrizione()+" ");
                    else if(j==fronte[i].length-1){
                        System.out.println(fronte[i][j].getDescrizione());
                    }
                }
            }
        }else{
            for(int i=0; i<retro.length; i++) {
                for (int j = 0; j < retro[i].length; j++) {
                    System.out.println(retro[i][j].getDescrizione());
                }
            }
        }
    }

}

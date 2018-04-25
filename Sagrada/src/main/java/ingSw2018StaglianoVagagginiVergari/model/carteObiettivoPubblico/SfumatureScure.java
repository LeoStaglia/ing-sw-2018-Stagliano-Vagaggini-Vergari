package ingSw2018StaglianoVagagginiVergari.model.carteObiettivoPubblico;

import ingSw2018StaglianoVagagginiVergari.model.CartaObiettivoPubblico;
import ingSw2018StaglianoVagagginiVergari.model.Plancia;

public class SfumatureScure implements CartaObiettivoPubblico {

    private final int puntiVittoria = 2;
    private final String nome = "SfumatureScure";

    //----------------------------all this part is required for Singleton Pattern----------------------------------

    private static SfumatureScure instance = new SfumatureScure();

    private SfumatureScure(){
    }

    public static SfumatureScure getInstance(){
        return instance;
    }

    //----------------------------


    public int getPuntiVittoria() {
        return puntiVittoria;
    }


    public String getNome() {
        return nome;
    }



    //set di 5 e 6 ovunque
    public int calcolaPunti(Plancia planciagioco) {

        int cinque = 0;
        int sei = 0;

        for (int i=0; i<4; i++){
            for(int j=0; j<5; j++){

                if (planciagioco.leggiPlancia(i,j) != null){


                    if (planciagioco.leggiPlancia(i,j).getNumero()==5)
                        cinque=cinque+1;
                    if (planciagioco.leggiPlancia(i,j).getNumero()==6)
                        sei=sei+1;

                }

            }
        }

        if (cinque<sei)
            return cinque*puntiVittoria;
        else
            return sei*puntiVittoria;

    }



}

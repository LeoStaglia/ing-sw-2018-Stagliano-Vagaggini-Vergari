package ingSw2018StaglianoVagagginiVergari.server.model.carteObiettivoPubblico;

import ingSw2018StaglianoVagagginiVergari.server.model.CartaObiettivoPubblico;
import ingSw2018StaglianoVagagginiVergari.server.model.Plancia;

public class SfumatureMedie implements CartaObiettivoPubblico {

    private final int puntiVittoria = 2;
    private final String nome = "SfumatureMedie";

    //----------------------------all this part is required for Singleton Pattern----------------------------------

    private static SfumatureMedie instance = new SfumatureMedie();

    private SfumatureMedie(){
    }

    public static SfumatureMedie getInstance(){
        return instance;
    }

    //----------------------------


    public int getPuntiVittoria() {
        return puntiVittoria;
    }


    public String getNome() {
        return nome;
    }


    public int calcolaPunti(Plancia planciagioco) {

        int tre = 0;
        int quattro = 0;

        for (int i=0; i<4; i++){
            for(int j=0; j<5; j++){

                if (planciagioco.leggiPlancia(i,j) != null){


                    if (planciagioco.leggiPlancia(i,j).getNumero()==3)
                        tre=tre+1;
                    if (planciagioco.leggiPlancia(i,j).getNumero()==4)
                        quattro=quattro+1;


                }

            }
        }

        if (tre<quattro)
            return tre*puntiVittoria;
        else
            return quattro*puntiVittoria;

    }



}

package ingSw2018StaglianoVagagginiVergari.model.carteObiettivoPubblico;

import ingSw2018StaglianoVagagginiVergari.model.CartaObiettivoPubblico;
import ingSw2018StaglianoVagagginiVergari.model.Plancia;

public class SfumatureDiverseRiga implements CartaObiettivoPubblico {

    private final int puntiVittoria = 5;
    private final String nome = "SfumatureDiverseRiga";

    //----------------------------all this part is required for Singleton Pattern----------------------------------

    private static SfumatureDiverseRiga instance = new SfumatureDiverseRiga();

    private SfumatureDiverseRiga(){
    }

    public static SfumatureDiverseRiga getInstance(){
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

        int uno = 0;
        int due = 0;
        int tre = 0;
        int quattro = 0;
        int cinque = 0;
        int sei = 0;
        int counter = 0;


        for (int i=0; i<4; i++){

            uno = 0;
            due = 0;
            tre = 0;
            quattro = 0;
            cinque = 0;
            sei = 0;

            for(int j=0; j<5; j++){

                if (planciagioco.leggiPlancia(i,j) != null){

                    if (planciagioco.leggiPlancia(i,j).getNumero() == 1)
                        uno = 1;
                    else if (planciagioco.leggiPlancia(i,j).getNumero() == 2)
                        due = 1;
                    else if (planciagioco.leggiPlancia(i,j).getNumero() == 3)
                        tre = 1;
                    else if (planciagioco.leggiPlancia(i,j).getNumero() == 4)
                        quattro = 1;
                    else if (planciagioco.leggiPlancia(i,j).getNumero() == 5)
                        cinque = 1;
                    else if (planciagioco.leggiPlancia(i,j).getNumero() == 6)
                        sei = 1;

                }


            }

            if (  (uno + due + tre + quattro + cinque + sei ) == 5 )
                counter= counter+1;
        }

        return counter*puntiVittoria;

    }


}


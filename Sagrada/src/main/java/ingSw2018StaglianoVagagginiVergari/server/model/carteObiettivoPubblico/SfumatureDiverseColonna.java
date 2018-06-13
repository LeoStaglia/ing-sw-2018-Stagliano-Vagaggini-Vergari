package ingSw2018StaglianoVagagginiVergari.server.model.carteObiettivoPubblico;

import ingSw2018StaglianoVagagginiVergari.server.model.CartaObiettivoPubblico;
import ingSw2018StaglianoVagagginiVergari.server.model.Plancia;

public class SfumatureDiverseColonna implements CartaObiettivoPubblico {

    private final int puntiVittoria = 4;
    private final String nome = "SfumatureDiverseColonna";
    private String descrizione="Sfumature Diverse Colonna\n" +
                                "Colonne senza sfumature ripetute";

    //----------------------------all this part is required for Singleton Pattern----------------------------------

    private static SfumatureDiverseColonna instance = new SfumatureDiverseColonna();

    private SfumatureDiverseColonna(){
    }

    public static SfumatureDiverseColonna getInstance(){
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


        for (int i=0; i<5; i++){

            uno = 0;
            due = 0;
            tre = 0;
            quattro = 0;
            cinque = 0;
            sei = 0;

            for(int j=0; j<4; j++){

                if (planciagioco.leggiPlancia(j,i) != null){

                    if (planciagioco.leggiPlancia(j,i).getNumero() == 1)
                        uno = 1;
                    else if (planciagioco.leggiPlancia(j,i).getNumero() == 2)
                        due = 1;
                    else if (planciagioco.leggiPlancia(j,i).getNumero() == 3)
                        tre = 1;
                    else if (planciagioco.leggiPlancia(j,i).getNumero() == 4)
                        quattro = 1;
                    else if (planciagioco.leggiPlancia(j,i).getNumero() == 5)
                        cinque = 1;
                    else if (planciagioco.leggiPlancia(j,i).getNumero() == 6)
                        sei = 1;

                }


            }

            if (  (uno + due + tre + quattro + cinque + sei ) == 4 )
                counter= counter+1;
        }

        return counter*puntiVittoria;

    }

    public String getDescrizione() {
        return descrizione;
    }
}

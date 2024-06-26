package ingSw2018StaglianoVagagginiVergari.server.model.carteObiettivoPubblico;

import ingSw2018StaglianoVagagginiVergari.server.model.CartaObiettivoPubblico;
import ingSw2018StaglianoVagagginiVergari.server.model.Plancia;

public class SfumatureChiare implements CartaObiettivoPubblico {

    private final int puntiVittoria = 2;
    private final String nome = "SfumatureChiare";
    private String descrizione="Sfumature Chiare\n" +
            "Set di 1 e 2 ovunque";

    //----------------------------all this part is required for Singleton Pattern----------------------------------

    private static SfumatureChiare instance = new SfumatureChiare();

    private SfumatureChiare(){
    }

    public static SfumatureChiare getInstance(){
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

        for (int i=0; i<4; i++){
            for(int j=0; j<5; j++){

                if (planciagioco.leggiPlancia(i,j) != null){


                    if (planciagioco.leggiPlancia(i,j).getNumero()==1)
                        uno=uno+1;
                    if (planciagioco.leggiPlancia(i,j).getNumero()==2)
                        due=due+1;


                }
            }
        }

        if (uno<due)
            return uno*puntiVittoria;
        else
            return due*puntiVittoria;

    }

    public String getDescrizione() {
        return descrizione;
    }
}

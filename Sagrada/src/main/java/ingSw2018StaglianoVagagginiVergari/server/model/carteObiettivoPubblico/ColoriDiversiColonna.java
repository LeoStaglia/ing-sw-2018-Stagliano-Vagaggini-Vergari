package ingSw2018StaglianoVagagginiVergari.server.model.carteObiettivoPubblico;

import ingSw2018StaglianoVagagginiVergari.server.model.CartaObiettivoPubblico;
import ingSw2018StaglianoVagagginiVergari.server.model.Plancia;

public class ColoriDiversiColonna implements CartaObiettivoPubblico {

    private final int puntiVittoria = 5;
    private final String nome = "ColoriDiversiColonna";
    private String descrizione="Colori diversi-Colonna\n" +
                                "Colonne senza colori ripetuti";

    //----------------------------all this part is required for Singleton Pattern----------------------------------

    private static ColoriDiversiColonna instance = new ColoriDiversiColonna();

    private ColoriDiversiColonna(){
    }

    public static ColoriDiversiColonna getInstance(){
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

        int rosso = 0;
        int giallo = 0;
        int verde = 0;
        int blu = 0;
        int viola = 0;
        int counter = 0;

        for (int i=0; i<5; i++){
            rosso = 0;
            giallo = 0;
            verde = 0;
            blu = 0;
            viola = 0;

            for(int j=0; j<4; j++){

                if (planciagioco.leggiPlancia(j,i) != null){

                    if (planciagioco.leggiPlancia(j,i).getColore().equalsIgnoreCase("rosso") == true)
                        rosso = 1;
                    else if (planciagioco.leggiPlancia(j,i).getColore().equalsIgnoreCase("giallo") == true)
                        giallo = 1;
                    else if (planciagioco.leggiPlancia(j,i).getColore().equalsIgnoreCase("verde") == true)
                        verde = 1;
                    else if (planciagioco.leggiPlancia(j,i).getColore().equalsIgnoreCase("blu") == true)
                        blu = 1;
                    else if (planciagioco.leggiPlancia(j,i).getColore().equalsIgnoreCase("viola") == true)
                        viola = 1;


                }


            }

            if ( (rosso + giallo + verde + blu + viola ) == 4)
                counter = counter+1;
        }

        return counter*puntiVittoria;



    }

    public String getDescrizione() {
        return descrizione;
    }
}

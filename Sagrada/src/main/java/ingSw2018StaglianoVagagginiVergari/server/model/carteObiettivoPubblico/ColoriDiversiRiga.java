package ingSw2018StaglianoVagagginiVergari.server.model.carteObiettivoPubblico;

import ingSw2018StaglianoVagagginiVergari.server.model.CartaObiettivoPubblico;
import ingSw2018StaglianoVagagginiVergari.server.model.Plancia;

public class ColoriDiversiRiga implements CartaObiettivoPubblico {

    private final int puntiVittoria = 6;
    private final String nome = "ColoriDiversiRiga";

    //----------------------------all this part is required for Singleton Pattern----------------------------------

    private static ColoriDiversiRiga instance = new ColoriDiversiRiga();

    private ColoriDiversiRiga(){
    }

    public static ColoriDiversiRiga getInstance(){
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

        boolean rosso = false;
        boolean giallo = false;
        boolean verde = false;
        boolean blu = false;
        boolean viola = false;
        int counter = 0;


        for (int i=0; i<4; i++){

            rosso = false;
            giallo = false;
            verde = false;
            blu = false;
            viola = false;

            for(int j=0; j<5; j++){

                if (planciagioco.leggiPlancia(i,j) != null){

                    if (planciagioco.leggiPlancia(i,j).getColore().equalsIgnoreCase("rosso") == true)
                        rosso = true;
                    else if (planciagioco.leggiPlancia(i,j).getColore().equalsIgnoreCase("giallo") == true)
                        giallo = true;
                    else if (planciagioco.leggiPlancia(i,j).getColore().equalsIgnoreCase("verde") == true)
                        verde = true;
                    else if (planciagioco.leggiPlancia(i,j).getColore().equalsIgnoreCase("blu") == true)
                        blu = true;
                    else if (planciagioco.leggiPlancia(i,j).getColore().equalsIgnoreCase("viola") == true)
                        viola = true;

                }


            }

            if (rosso && giallo && verde && blu && viola)
                counter= counter+1;
        }

        return counter*puntiVittoria;



    }


}

package ingSw2018StaglianoVagagginiVergari.model.carteObiettivoPubblico;

import ingSw2018StaglianoVagagginiVergari.model.CartaObiettivoPubblico;
import ingSw2018StaglianoVagagginiVergari.model.Plancia;

import java.util.ArrayList;

public class VarietaDiColore implements CartaObiettivoPubblico {

    private final int puntiVittoria = 4;
    private final String nome = "VarietaDiColore";

    //----------------------------all this part is required for Singleton Pattern----------------------------------

    private static VarietaDiColore instance = new VarietaDiColore();

    private VarietaDiColore(){
    }

    public static VarietaDiColore getInstance(){
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
        int verde = 0;
        int giallo = 0;
        int blu = 0;
        int viola = 0;

        for (int i=0; i<4; i++){
            for(int j=0; j<5; j++){

                if (planciagioco.leggiPlancia(i,j) != null){


                    if (planciagioco.leggiPlancia(i,j).getColore().equalsIgnoreCase("rosso") == true)
                        rosso=rosso+1;
                    else if (planciagioco.leggiPlancia(i,j).getColore().equalsIgnoreCase("verde") == true)
                        verde=verde+1;
                    else if (planciagioco.leggiPlancia(i,j).getColore().equalsIgnoreCase("giallo") == true)
                        giallo=giallo+1;
                    else if (planciagioco.leggiPlancia(i,j).getColore().equalsIgnoreCase("blu") == true)
                        blu=blu+1;
                    else if (planciagioco.leggiPlancia(i,j).getColore().equalsIgnoreCase("viola") == true)
                        viola=viola+1;


                }

            }
        }

        //used to determine the minimum occurrence of number
        ArrayList<Integer> list = new ArrayList<Integer>();
        list.add(rosso);
        list.add(verde);
        list.add(giallo);
        list.add(blu);
        list.add(viola);
        int min = list.get(0);
        for (int i : list){
            min = min < i ? min : i;
        }


        return min*puntiVittoria;


    }


}

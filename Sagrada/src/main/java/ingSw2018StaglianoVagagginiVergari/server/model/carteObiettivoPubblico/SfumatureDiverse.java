package ingSw2018StaglianoVagagginiVergari.server.model.carteObiettivoPubblico;

import ingSw2018StaglianoVagagginiVergari.server.model.CartaObiettivoPubblico;
import ingSw2018StaglianoVagagginiVergari.server.model.Plancia;

import java.util.ArrayList;

public class SfumatureDiverse implements CartaObiettivoPubblico {

    private final int puntiVittoria = 5;
    private final String nome = "SfumatureDiverse";

    //----------------------------all this part is required for Singleton Pattern----------------------------------

    private static SfumatureDiverse instance = new SfumatureDiverse();

    private SfumatureDiverse(){
    }

    public static SfumatureDiverse getInstance(){
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

        for (int i=0; i<4; i++){
            for(int j=0; j<5; j++){

                if (planciagioco.leggiPlancia(i,j) != null){

                    if (planciagioco.leggiPlancia(i,j).getNumero()==1)
                        uno=uno+1;
                    else if (planciagioco.leggiPlancia(i,j).getNumero()==2)
                        due=due+1;
                    else if (planciagioco.leggiPlancia(i,j).getNumero()==3)
                        tre=tre+1;
                    else if (planciagioco.leggiPlancia(i,j).getNumero()==4)
                        quattro=quattro+1;
                    else if (planciagioco.leggiPlancia(i,j).getNumero()==5)
                        cinque=cinque+1;
                    else if (planciagioco.leggiPlancia(i,j).getNumero()==6)
                        sei=sei+1;

                }

            }
        }

        //used to determine the minimum occurrence of number
        ArrayList<Integer> list = new ArrayList<Integer>();
        list.add(uno);
        list.add(due);
        list.add(tre);
        list.add(quattro);
        list.add(cinque);
        list.add(sei);
        int min = list.get(0);
        for (int i : list){
            min = min < i ? min : i;
        }

        return min*puntiVittoria;

    }


}

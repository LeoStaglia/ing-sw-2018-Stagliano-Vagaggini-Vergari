package ingSw2018StaglianoVagagginiVergari.server.model.carteObiettivoPubblico;



import ingSw2018StaglianoVagagginiVergari.server.model.CartaObiettivoPubblico;
import ingSw2018StaglianoVagagginiVergari.server.model.Dado;
import ingSw2018StaglianoVagagginiVergari.server.model.Plancia;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class DiagonaliColorate implements CartaObiettivoPubblico {

    private final int puntiVittoria = 1;
    private final String nome = "DiagonaliColorate";
    private String descrizione="Diagonali Colorate\n" +
            "Numero di dadi dello stesso colore diagonalmente adiacenti";

    //----------------------------all this part is required for Singleton Pattern----------------------------------

    private static DiagonaliColorate instance = new DiagonaliColorate();

    private DiagonaliColorate(){
    }

    public static DiagonaliColorate getInstance(){
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

        int somma = 0;

        ArrayList<Dado> planci = new ArrayList<Dado>();


        //Generation of an ArrayList from a Matrix

        for (int i=0; i<4; i++){
            for (int j=0; j<5; j++){
                if (planciagioco.leggiPlancia(i,j)==null) {
                    planci.add(new Dado("null"));  //Dado colored "null" to solve the problem of null dice
                }

                else
                    planci.add(planciagioco.leggiPlancia(i,j));

            }
        }

        Set<String> setOfColors = new HashSet<String>();
        setOfColors.add("rosso");
        setOfColors.add("giallo");
        setOfColors.add("verde");
        setOfColors.add("viola");
        setOfColors.add("blu");



        ArrayList<Integer> vett= new ArrayList<Integer>();
        vett.add(3);
        vett.add(3);
        vett.add(2);
        vett.add(1);
        vett.add(0);


        ArrayList<Integer> marca = new ArrayList<Integer>();
        for (String color : setOfColors) {


            for(int m=0;m<20;m++){
                marca.add(0);
            }

            for (int i = 0; i < 5; i++) {
                for (int k = 0; k < vett.get(i); k++) {

                    if ( planci.get(i + 6 * k).getColore().equalsIgnoreCase(planci.get(i + 6 * (k + 1)).getColore()) && planci.get(i + 6 * k).getColore().equalsIgnoreCase(color)) {

                        marca.set(i + 6 * k, 1);
                        marca.set(i + 6 * (k+1), 1);

                    }
                    if (planci.get(19 - i - 6 * k).getColore().equalsIgnoreCase(planci.get(19 - i - 6 * (k + 1)).getColore())  &&  planci.get(19 - i - 6 * k).getColore().equalsIgnoreCase(color) ) {

                        marca.set(19 - i - 6 * k, 1);
                        marca.set(19 - i - 6 * (k+1), 1);

                    }
                    if (planci.get(4 - i + 4 * k).getColore().equalsIgnoreCase(planci.get(4 - i + 4 * (k + 1)).getColore())  &&  planci.get(4 - i + 4 * k).getColore().equalsIgnoreCase(color )) {

                        marca.set(4 - i + 4 * k, 1);
                        marca.set(4 - i + 4 * (k+1), 1);

                    }
                    if (planci.get(15 + i - 4 * k).getColore().equalsIgnoreCase(planci.get(15 + i - 4 * (k + 1)).getColore()) && planci.get(15 + i - 4 * k).getColore().equalsIgnoreCase(color) ) {

                        marca.set(15 + i - 4 * k, 1);
                        marca.set(15 + i - 4 * (k+1), 1);

                    }

                }
            }




            for (int j = 0; j < 20; j++) {
                    if (marca.get(j) == 1) {
                        somma = somma + 1;
                    }

            }
            for(int m=0;m<20;m++){
                marca.remove(0);
            }


        }

        return somma*puntiVittoria;

    }

    public String getDescrizione() {
        return descrizione;
    }
}

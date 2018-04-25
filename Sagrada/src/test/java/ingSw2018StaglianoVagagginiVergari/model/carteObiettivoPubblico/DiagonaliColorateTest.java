package ingSw2018StaglianoVagagginiVergari.model.carteObiettivoPubblico;

import ingSw2018StaglianoVagagginiVergari.model.CartaObiettivoPubblico;
import ingSw2018StaglianoVagagginiVergari.model.Dado;
import ingSw2018StaglianoVagagginiVergari.model.FactoryCartaObiettivoPubblico;
import ingSw2018StaglianoVagagginiVergari.model.Plancia;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

class DiagonaliColorateTest {
    @Test
    void getInstance() {
    }

    @Test
    void getPuntiVittoria() {
    }

    @Test
    void getNome() {
    }

    @Test
    void calcolaPunti() {

        CartaObiettivoPubblico carta1 = FactoryCartaObiettivoPubblico.getCartaObiettivoPubblico(9);



        /*-------------------- FINAL VERSION
        *
        *
        * You can run multiple tests on the algorithm:
        *
        *
        * to add a test just increase NumeroTest, then add the values of the die
        * inside the game board in a row of the MatriceValoriDadiTest and insert
        * the correct result inside the Matrix MatriceRisultati
        *
        * If you want to insert a "null dice", just insert "n" in the MatriceValoriDadiTest
        *
        *
        * */




        Plancia plancia1 = Mockito.mock(Plancia.class);

        int NumeroTest = 3;

        Dado[][][] MatriceDadi = new Dado[NumeroTest][4][5];

        //g r b y p


        String[][][] MatriceColoriDadiTest = {     //  ogni riga corrisponde ai valori dei dadi rispetto al test

                {{"verde","n","n","verde","n"}, {"blu","verde","blu","n","verde"}, {"giallo","blu","verde","rosso","n"}, {"n","giallo","rosso","verde","n"}}, // test 0

                {{"n","n","n","n","n"}, {"n","blu","n","n","giallo"}, {"blu","n","blu","giallo","n"}, {"n","n","giallo","blu","n"}}, // test 1

                {{"verde","n","rosso","n","n"}, {"n","n","rosso","n","n"}, {"n","n","n","verde","n"}, {"n","n","n","n","n"}}, // test 2


        };


        int [] MatriceRisultati = {
                13,  //risultato del test 0
                7,    //risultato del test 1
                0   //risultato del test 2
        };

        for (int TestCorrente=0; TestCorrente<NumeroTest;TestCorrente++){
            for (int i=0;i<4;i++){
                for(int j=0;j<5;j++){


                    if(MatriceColoriDadiTest[TestCorrente][i][j]!= "n")  //se non è null
                        MatriceDadi[TestCorrente][i][j] = Mockito.mock(Dado.class);  //viene mockato l'oggetto dado
                    else {
                        MatriceDadi[TestCorrente][i][j] = null;
                        //System.out.println("ho assegnato il valore nullo");
                    }
                    when(plancia1.leggiPlancia(i,j)).thenReturn(MatriceDadi[TestCorrente][i][j]);

                    if (MatriceDadi[TestCorrente][i][j] != null)
                        when(MatriceDadi[TestCorrente][i][j].getColore()).thenReturn(MatriceColoriDadiTest[TestCorrente][i][j]);

                }
            }

            assertEquals(MatriceRisultati[TestCorrente],carta1.calcolaPunti(plancia1));

        }


    }

}
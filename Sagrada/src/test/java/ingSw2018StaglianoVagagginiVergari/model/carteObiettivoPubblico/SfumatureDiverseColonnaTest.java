package ingSw2018StaglianoVagagginiVergari.model.carteObiettivoPubblico;

import ingSw2018StaglianoVagagginiVergari.server.model.CartaObiettivoPubblico;
import ingSw2018StaglianoVagagginiVergari.server.model.Dado;
import ingSw2018StaglianoVagagginiVergari.server.model.FactoryCartaObiettivoPubblico;
import ingSw2018StaglianoVagagginiVergari.server.model.Plancia;
import org.junit.Test;
import org.mockito.Mockito;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

public class SfumatureDiverseColonnaTest {

    @Test
    public void calcolaPunti() {

        CartaObiettivoPubblico carta1 = FactoryCartaObiettivoPubblico.getCartaObiettivoPubblico(4);
        System.out.println("Carta: "+carta1.getNome()+"  " +carta1.getDescrizione());



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
        * If you want to insert a "null dice", just insert 0 in the MatriceValoriDadiTest
        *
        *
        * */




        Plancia plancia1 = Mockito.mock(Plancia.class);

        int NumeroTest = 3;

        Dado[][][] MatriceDadi = new Dado[NumeroTest][4][5];


        int[][][] MatriceValoriDadiTest = {     //  ogni riga corrisponde ai valori dei dadi rispetto al test

                {{1,1,1,1,1}, {2,2,2,2,2}, {3,3,3,3,0}, {4,4,4,0,4}}, // test 0

                {{1,5,5,5,5}, {2,5,5,5,5}, {4,5,5,5,5}, {6,5,5,5,5}}, // test 1

                {{6,6,6,6,6}, {6,6,6,6,6}, {5,5,5,5,5}, {0, 0, 0, 0, 0}}, // test 2


        };


        int [] MatriceRisultati = {
                12,  //risultato del test 0
                4,    //risultato del test 1
                0   //risultato del test 2
        };

        for (int TestCorrente=0; TestCorrente<NumeroTest;TestCorrente++){
            for (int i=0;i<4;i++){
                for(int j=0;j<5;j++){


                    if(MatriceValoriDadiTest[TestCorrente][i][j]!= 0)
                        MatriceDadi[TestCorrente][i][j] = Mockito.mock(Dado.class);  //viene mockato l'oggetto dado
                    else {
                        MatriceDadi[TestCorrente][i][j] = null;
                        //System.out.println("ho assegnato il valore nullo");
                    }
                    when(plancia1.leggiPlancia(i,j)).thenReturn(MatriceDadi[TestCorrente][i][j]);

                    if (MatriceDadi[TestCorrente][i][j] != null)
                        when(MatriceDadi[TestCorrente][i][j].getNumero()).thenReturn(MatriceValoriDadiTest[TestCorrente][i][j]);

                }
            }

            assertEquals(MatriceRisultati[TestCorrente],carta1.calcolaPunti(plancia1));

        }


    }

}
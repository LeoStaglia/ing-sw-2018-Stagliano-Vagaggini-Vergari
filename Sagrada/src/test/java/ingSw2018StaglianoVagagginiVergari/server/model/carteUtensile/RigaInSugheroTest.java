package ingSw2018StaglianoVagagginiVergari.server.model.carteUtensile;

import Eccezioni.MossaIllegaleException;
import ingSw2018StaglianoVagagginiVergari.server.model.*;
import ingSw2018StaglianoVagagginiVergari.server.model.carteSchema.FactorySchema;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RigaInSugheroTest {



    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_BLACK = "\u001B[30m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_CYAN = "\u001B[36m";
    public static final String ANSI_WHITE = "\u001B[37m";



    @Test
    void usaEffettoCarta() throws MossaIllegaleException {










        Partita PartitaCorrente = new Partita();

        Utente User = new Utente( new Plancia("Rosso"), Constraint.ROSSO);

        PartitaCorrente.addUtente(User);
        PartitaCorrente.setGiocatoreCorrente(User);
        PartitaCorrente.setDadoSelezionato(new Dado("Verde",5));





        //---------- instantiation  of tool card

        CartaUtensile carta1 = FactoryCartaUtensile.getCartaUtensile(9);

        //System.out.println(PartitaCorrente.getCurrentPlayer()());

        PartitaCorrente.getCurrentPlayer().getPlancia().inserisciCartaSchema(FactorySchema.get(3));  //the scheme card is AuroraeMagnificus


        //---------- insert die into the game board



        Dado tmp = new Dado("Rosso",5);

        PartitaCorrente.getCurrentPlayer().getPlancia().calcolaMosse(tmp,false,false);
        PartitaCorrente.getCurrentPlayer().getPlancia().piazzaDado(0,0,tmp);





        tmp = new Dado("Giallo",4);

        PartitaCorrente.getCurrentPlayer().getPlancia().calcolaMosse(tmp,false,false);
        PartitaCorrente.getCurrentPlayer().getPlancia().piazzaDado(1,0,tmp);





        tmp = new Dado("Viola",6);
        PartitaCorrente.getCurrentPlayer().getPlancia().calcolaMosse(tmp,false,false);
        PartitaCorrente.getCurrentPlayer().getPlancia().piazzaDado(1,1,tmp);




        tmp = new Dado("Rosso",1);
        PartitaCorrente.getCurrentPlayer().getPlancia().calcolaMosse(tmp,false,false);
        PartitaCorrente.getCurrentPlayer().getPlancia().piazzaDado(2,1,tmp);






        System.out.println();




        for (int i = 0; i < 4; i++) {
            System.out.println();
            for (int j = 0; j < 5; j++) {

                if (PartitaCorrente.getCurrentPlayer().getPlancia().leggiPlancia(i,j)!=null){

                    if (PartitaCorrente.getCurrentPlayer().getPlancia().leggiPlancia(i, j).getColore().equalsIgnoreCase("rosso")){
                        System.out.print(ANSI_RED + PartitaCorrente.getCurrentPlayer().getPlancia().leggiPlancia(i, j).getNumero() + ANSI_RESET);

                    }

                    else if (PartitaCorrente.getCurrentPlayer().getPlancia().leggiPlancia(i, j).getColore().equalsIgnoreCase("giallo")){
                        System.out.print(ANSI_YELLOW + PartitaCorrente.getCurrentPlayer().getPlancia().leggiPlancia(i, j).getNumero() + ANSI_RESET);

                    }

                    else if (PartitaCorrente.getCurrentPlayer().getPlancia().leggiPlancia(i, j).getColore().equalsIgnoreCase("verde")){
                        System.out.print(ANSI_GREEN + PartitaCorrente.getCurrentPlayer().getPlancia().leggiPlancia(i, j).getNumero() + ANSI_RESET);

                    }
                    else if (PartitaCorrente.getCurrentPlayer().getPlancia().leggiPlancia(i, j).getColore().equalsIgnoreCase("blu")){
                        System.out.print(ANSI_BLUE + PartitaCorrente.getCurrentPlayer().getPlancia().leggiPlancia(i, j).getNumero() + ANSI_RESET);

                    }
                    else if (PartitaCorrente.getCurrentPlayer().getPlancia().leggiPlancia(i, j).getColore().equalsIgnoreCase("viola")){
                        System.out.print(ANSI_PURPLE + PartitaCorrente.getCurrentPlayer().getPlancia().leggiPlancia(i, j).getNumero() + ANSI_RESET);

                    }

                }else{
                    System.out.print("N");
                }



            }
        }

        //---


        System.out.println();


        carta1.usaEffettoCarta(PartitaCorrente);





    }

}
package ingSw2018StaglianoVagagginiVergari.model.carteUtensile;

import Eccezioni.MossaIllegaleException;
import ingSw2018StaglianoVagagginiVergari.server.model.*;
import ingSw2018StaglianoVagagginiVergari.server.model.carteUtensile.TamponeDiamantato;
import org.junit.jupiter.api.Test;

import java.rmi.RemoteException;

import static org.junit.jupiter.api.Assertions.assertTrue;

class TamponeDiamantatoTest {

    @Test
    void usaEffettoCarta_test1() throws MossaIllegaleException, RemoteException {


        class DadoPosizione{
            Dado d;
            int x;
            int y;

            public DadoPosizione(Dado d, int x, int y){
                this.d = d;
                this.x = x;
                this.y = y;

            }

        }

        //parametri da cambiare per inizializzare la partita
        Constraint coloreObiettivoPrivato = Constraint.ROSSO;       //da settare

        int schemaScelto = 3;

        //instanzio la partita
        Partita PartitaCorrente = new Partita();
        Utente User = new Utente( new Plancia("colore della vetrata") , coloreObiettivoPrivato);
        PartitaCorrente.addUtente(User);
        PartitaCorrente.setGiocatoreCorrente(User);
        CartaUtensile carta1 = FactoryCartaUtensile.getCartaUtensile(10);




        PartitaCorrente.getRiserva().add(new Dado("Verde",1));
        PartitaCorrente.getRiserva().add(new Dado("Giallo",2));
        PartitaCorrente.getRiserva().add(new Dado("Rosso",3));
        PartitaCorrente.getRiserva().add(new Dado("Viola",4));
        PartitaCorrente.getRiserva().add(new Dado("Viola",6));
        PartitaCorrente.getRiserva().add(new Dado("Blu",5));


        //====================


        System.out.println("\nRiserva prima dell'effetto carta"+PartitaCorrente.getRiserva());

        ((TamponeDiamantato) carta1).setScelta(0);  //1verde ->6 verde

        carta1.usaEffettoCarta(PartitaCorrente);


        System.out.println("Riserva dopo l'effetto carta"+PartitaCorrente.getRiserva());

        assertTrue(PartitaCorrente.getRiserva().get(5).getNumero() == 6);

        //====================


        System.out.println("\nRiserva prima dell'effetto carta"+PartitaCorrente.getRiserva());

        ((TamponeDiamantato) carta1).setScelta(0); // 2 giallo -> 5 giallo

        carta1.usaEffettoCarta(PartitaCorrente);


        System.out.println("Riserva dopo l'effetto carta"+PartitaCorrente.getRiserva());

        assertTrue(PartitaCorrente.getRiserva().get(5).getNumero() == 5);
        //====================


        System.out.println("\nRiserva prima dell'effetto carta"+PartitaCorrente.getRiserva());

        ((TamponeDiamantato) carta1).setScelta(0); // 3 ->4

        carta1.usaEffettoCarta(PartitaCorrente);


        System.out.println("Riserva dopo l'effetto carta"+PartitaCorrente.getRiserva());

        assertTrue(PartitaCorrente.getRiserva().get(5).getNumero() == 4);
        //====================


        System.out.println("\nRiserva prima dell'effetto carta"+PartitaCorrente.getRiserva());

        ((TamponeDiamantato) carta1).setScelta(0); // 4 -> 3

        carta1.usaEffettoCarta(PartitaCorrente);


        System.out.println("Riserva dopo l'effetto carta"+PartitaCorrente.getRiserva());

        assertTrue(PartitaCorrente.getRiserva().get(5).getNumero() == 3);
        //====================


        System.out.println("\nRiserva prima dell'effetto carta"+PartitaCorrente.getRiserva());

        ((TamponeDiamantato) carta1).setScelta(0); // 6 -> 1

        carta1.usaEffettoCarta(PartitaCorrente);


        System.out.println("Riserva dopo l'effetto carta"+PartitaCorrente.getRiserva());

        assertTrue(PartitaCorrente.getRiserva().get(5).getNumero() == 1);
        //====================



        System.out.println("\nRiserva prima dell'effetto carta"+PartitaCorrente.getRiserva());

        ((TamponeDiamantato) carta1).setScelta(0); // 5 -> 2

        carta1.usaEffettoCarta(PartitaCorrente);


        System.out.println("Riserva dopo l'effetto carta"+PartitaCorrente.getRiserva());

        assertTrue(PartitaCorrente.getRiserva().get(5).getNumero() == 2);




        System.out.println("parametri della carta: "+carta1.getId()+"  "+carta1.getDescrizione()+" "+carta1.getNome()+ " "+carta1.getCosto());

        carta1.reset();

    }

}
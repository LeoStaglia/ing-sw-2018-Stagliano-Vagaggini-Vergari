package ingSw2018StaglianoVagagginiVergari.model;

import ingSw2018StaglianoVagagginiVergari.server.model.Dado;
import ingSw2018StaglianoVagagginiVergari.server.model.Partita;
import ingSw2018StaglianoVagagginiVergari.server.model.Utente;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;
import static org.mockito.Mockito.*;

class PartitaTest {

    @Test
    void setOrdineRoundTool8Start() {
        Partita p=new Partita();
        ArrayList<Utente> test=new ArrayList<Utente>();


        Utente mockedUtente1= Mockito.mock(Utente.class);
        when(mockedUtente1.getId()).thenReturn("1");
        Utente mockedUtente2= Mockito.mock(Utente.class);
        when(mockedUtente1.getId()).thenReturn("2");
        Utente mockedUtente3= Mockito.mock(Utente.class);
        when(mockedUtente1.getId()).thenReturn("3");
        Utente mockedUtente4= Mockito.mock(Utente.class);
        when(mockedUtente1.getId()).thenReturn("4");

        test.add(mockedUtente2);
        test.add(mockedUtente3);
        test.add(mockedUtente3);
        test.add(mockedUtente4);
        test.add(mockedUtente1);
        test.add(mockedUtente1);
        test.add(mockedUtente4);
        test.add(mockedUtente2);

        p.getListaGiocatori().add(mockedUtente1);
        p.getListaGiocatori().add(mockedUtente2);
        p.getListaGiocatori().add(mockedUtente3);
        p.getListaGiocatori().add(mockedUtente4);
        p.inizializzaOrdineRound();
        p.incrementaTurno();
        p.nextRound();
        p.setOrdineRound();
        for (Utente u:p.ordineRound) {
            System.out.println(u);
        }
        p.setOrdineRoundTool8Start();

        System.out.println(p.getTurno());

        for (Utente u:p.ordineRound) {
            System.out.println(u);
        }

        assertIterableEquals(p.getOrdineRound(),test);






    }

    @Test
    void setOrdineRoundTool8End() {
        Partita p=new Partita();
        ArrayList<Utente> test=new ArrayList<Utente>();


        Utente mockedUtente1= Mockito.mock(Utente.class);
        when(mockedUtente1.getId()).thenReturn("1");
        Utente mockedUtente2= Mockito.mock(Utente.class);
        when(mockedUtente1.getId()).thenReturn("2");
        Utente mockedUtente3= Mockito.mock(Utente.class);
        when(mockedUtente1.getId()).thenReturn("3");
        Utente mockedUtente4= Mockito.mock(Utente.class);
        when(mockedUtente1.getId()).thenReturn("4");



        test.add(mockedUtente3);
        test.add(mockedUtente4);
        test.add(mockedUtente1);
        test.add(mockedUtente2);
        test.add(mockedUtente2);
        test.add(mockedUtente1);
        test.add(mockedUtente4);
        test.add(mockedUtente3);


        p.getListaGiocatori().add(mockedUtente1);
        p.getListaGiocatori().add(mockedUtente2);
        p.getListaGiocatori().add(mockedUtente3);
        p.getListaGiocatori().add(mockedUtente4);

        p.inizializzaOrdineRound();
        p.nextRound();
        p.nextRound();
        p.setOrdineRound();
        p.setOrdineRound();

        p.incrementaTurno();
        p.incrementaTurno();

        p.setOrdineRoundTool8Start();

        p.setOrdineRoundTool8End();

        assertIterableEquals(p.getOrdineRound(),test);

    }

    @Test
    void reInserisciDadoinRiserva() {
        Partita p=new Partita();
        for(int i=0; i<9;i++) {
            Dado d=Mockito.mock(Dado.class);
            when(d.getNumero()).thenReturn(i);
            p.getRiserva().add(d);
        }
        Dado d1= p.getDadofromRiserva(5);
        p.reInserisciDadoinRiserva(d1);
        assertEquals(p.getRiserva().get(8),d1);
    }

    @Test
    void getNumeroGiocatori() {
        Partita p=new Partita();
        Utente mockedUtente1= Mockito.mock(Utente.class);
        when(mockedUtente1.getId()).thenReturn("1");
        Utente mockedUtente2= Mockito.mock(Utente.class);
        p.getListaGiocatori().add(mockedUtente1);
        p.getListaGiocatori().add(mockedUtente2);
        assertEquals(2,p.getNumeroGiocatori());

    }

    @Test
    void inizializzaOrdineRound() {
        Partita p=new Partita();
        ArrayList<Utente> test=new ArrayList<Utente>();
        Utente mockedUtente1= Mockito.mock(Utente.class);
        when(mockedUtente1.getId()).thenReturn("1");
        Utente mockedUtente2= Mockito.mock(Utente.class);
        when(mockedUtente1.getId()).thenReturn("2");
        Utente mockedUtente3= Mockito.mock(Utente.class);
        when(mockedUtente1.getId()).thenReturn("3");
        Utente mockedUtente4= Mockito.mock(Utente.class);
        when(mockedUtente1.getId()).thenReturn("4");
        test.add(mockedUtente1);
        test.add(mockedUtente2);
        test.add(mockedUtente3);
        test.add(mockedUtente4);
        test.add(mockedUtente4);
        test.add(mockedUtente3);
        test.add(mockedUtente2);
        test.add(mockedUtente1);
        p.getListaGiocatori().add(mockedUtente1);
        p.getListaGiocatori().add(mockedUtente2);
        p.getListaGiocatori().add(mockedUtente3);
        p.getListaGiocatori().add(mockedUtente4);
        p.inizializzaOrdineRound();
        assertIterableEquals(p.getOrdineRound(),test);
    }

    @Test
    void setOrdineRound() {
        Partita p=new Partita();
        ArrayList<Utente> test=new ArrayList<Utente>();
        Utente mockedUtente1= Mockito.mock(Utente.class);
        when(mockedUtente1.getId()).thenReturn("1");
        Utente mockedUtente2= Mockito.mock(Utente.class);
        when(mockedUtente1.getId()).thenReturn("2");
        Utente mockedUtente3= Mockito.mock(Utente.class);
        when(mockedUtente1.getId()).thenReturn("3");
        Utente mockedUtente4= Mockito.mock(Utente.class);
        when(mockedUtente1.getId()).thenReturn("4");
        p.getListaGiocatori().add(mockedUtente1);
        p.getListaGiocatori().add(mockedUtente2);
        p.getListaGiocatori().add(mockedUtente3);
        p.getListaGiocatori().add(mockedUtente4);

        test.add(mockedUtente3);
        test.add(mockedUtente4);
        test.add(mockedUtente1);
        test.add(mockedUtente2);
        test.add(mockedUtente2);
        test.add(mockedUtente1);
        test.add(mockedUtente4);
        test.add(mockedUtente3);

        p.ordineRound.add(mockedUtente1);
        p.ordineRound.add(mockedUtente2);
        p.ordineRound.add(mockedUtente3);
        p.ordineRound.add(mockedUtente4);
        p.ordineRound.add(mockedUtente4);
        p.ordineRound.add(mockedUtente3);
        p.ordineRound.add(mockedUtente2);
        p.ordineRound.add(mockedUtente1);

        p.setOrdineRound();
        p.setOrdineRound();
        assertIterableEquals(p.getOrdineRound(),test);


    }

    @Test
    void getCurrentPlayer() {
        Partita p=new Partita();
        int n=7;
        for(int i=1;i<n;i++) {
            p.incrementaTurno();
        }
        Utente mockedUtente1= Mockito.mock(Utente.class);
        when(mockedUtente1.getId()).thenReturn("1");
        Utente mockedUtente2= Mockito.mock(Utente.class);
        when(mockedUtente1.getId()).thenReturn("2");
        Utente mockedUtente3= Mockito.mock(Utente.class);
        when(mockedUtente1.getId()).thenReturn("3");
        Utente mockedUtente4= Mockito.mock(Utente.class);
        when(mockedUtente1.getId()).thenReturn("4");

        p.getOrdineRound().add(mockedUtente1);
        p.getOrdineRound().add(mockedUtente2);
        p.getOrdineRound().add(mockedUtente3);
        p.getOrdineRound().add(mockedUtente4);
        p.getOrdineRound().add(mockedUtente4);
        p.getOrdineRound().add(mockedUtente3);
        p.getOrdineRound().add(mockedUtente2);
        p.getOrdineRound().add(mockedUtente1);
        assertEquals(p.getCurrentPlayer(),mockedUtente2);
    }

    @Test
    void isEmpty() {
        Partita p=new Partita();
        Utente mockedUtente1= Mockito.mock(Utente.class);
        assertEquals(p.isEmpty(),true);
        p.getListaGiocatori().add(mockedUtente1);
        assertEquals(p.isEmpty(),false);
    }
}
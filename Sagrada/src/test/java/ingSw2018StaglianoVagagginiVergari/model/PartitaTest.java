package ingSw2018StaglianoVagagginiVergari.model;

import ingSw2018StaglianoVagagginiVergari.server.model.Dado;
import ingSw2018StaglianoVagagginiVergari.server.model.Partita;
import ingSw2018StaglianoVagagginiVergari.server.model.Utente;
import org.junit.Test;
import org.mockito.Mockito;

import java.rmi.RemoteException;
import java.util.ArrayList;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

public class PartitaTest {

    @Test
    public void setOrdineRoundTool8Start() throws RemoteException {
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
        p.riempiRiserva();
        p.setGiocatoreCorrente();
        p.nextRound();
        p.incrementaTurno();
        for (Utente u:p.getOrdineRound()) {
            System.out.println(u);
        }
        p.setOrdineRoundTool8Start();

        System.out.println(p.getTurno());

        for (Utente u:p.getOrdineRound()) {
            System.out.println(u);
        }

        //assertIterableEquals(p.getOrdineRound(),test);
        assertTrue(test.size() == p.getOrdineRound().size());
        for (int i=0; i<test.size();i++)
            assertTrue(p.getOrdineRound().get(i).equals(test.get(i)));






    }

    @Test
    public void setOrdineRoundTool8End() throws RemoteException {
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
        p.riempiRiserva();
        p.setGiocatoreCorrente();
        p.nextRound();
        p.nextRound();

        p.incrementaTurno();
        p.incrementaTurno();


        p.setOrdineRoundTool8Start();
        for (Utente u:p.getOrdineRound()) {
            System.out.println(u);
        }
        System.out.println();

        p.setOrdineRoundTool8End();

        for (Utente u:p.getOrdineRound()) {
            System.out.println(u);
        }
        System.out.println();


        //assertIterableEquals(p.getOrdineRound(),test);
        assertTrue(test.size() == p.getOrdineRound().size());
        for (int i=0; i<test.size();i++)
            assertTrue(p.getOrdineRound().get(i).equals(test.get(i)));

    }

    @Test
    public void reInserisciDadoinRiserva() {
        Partita p=new Partita();
        for(int i=0; i<9;i++) {
            Dado d=Mockito.mock(Dado.class);
            when(d.getNumero()).thenReturn(i);
            p.getRiserva().add(d);
        }
        Dado d1= p.getDadofromRiserva(5);
        p.reInserisciDadoinRiserva(d1);
        assertTrue(p.getRiserva().get(8).equals(d1));
    }

    @Test
    public void getNumeroGiocatori() {
        Partita p=new Partita();
        Utente mockedUtente1= Mockito.mock(Utente.class);
        when(mockedUtente1.getId()).thenReturn("1");
        Utente mockedUtente2= Mockito.mock(Utente.class);
        p.getListaGiocatori().add(mockedUtente1);
        p.getListaGiocatori().add(mockedUtente2);
        assertTrue(p.getNumeroGiocatori() == 2);


    }

    @Test
    public void inizializzaOrdineRound() {
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
        //assertIterableEquals(p.getOrdineRound(),test);
        assertTrue(test.size() == p.getOrdineRound().size());
        for (int i=0; i<test.size();i++)
            assertTrue(p.getOrdineRound().get(i).equals(test.get(i)));
    }

    @Test
    public void setOrdineRound() {
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

        p.getOrdineRound().add(mockedUtente1);
        p.getOrdineRound().add(mockedUtente2);
        p.getOrdineRound().add(mockedUtente3);
        p.getOrdineRound().add(mockedUtente4);
        p.getOrdineRound().add(mockedUtente4);
        p.getOrdineRound().add(mockedUtente3);
        p.getOrdineRound().add(mockedUtente2);
        p.getOrdineRound().add(mockedUtente1);

        p.setOrdineRound();
        p.setOrdineRound();
        //assertIterableEquals(p.getOrdineRound(),test);
        assertTrue(test.size() == p.getOrdineRound().size());
        for (int i=0; i<test.size();i++)
            assertTrue(p.getOrdineRound().get(i).equals(test.get(i)));


    }

    @Test
    public void getCurrentPlayer() throws RemoteException {
        Partita p=new Partita();
        int n=7;

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

        for(int i=1;i<n;i++) {
            p.incrementaTurno();
        }

        assertTrue(p.getCurrentPlayer().equals(mockedUtente2));
    }

    @Test
    public void isEmpty() {
        Partita p=new Partita();
        Utente mockedUtente1= Mockito.mock(Utente.class);
        assertTrue(p.isEmpty() == true);
        p.getListaGiocatori().add(mockedUtente1);
        assertTrue(p.isEmpty() == false);
    }
}
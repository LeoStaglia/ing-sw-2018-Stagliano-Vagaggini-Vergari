package ingSw2018StaglianoVagagginiVergari.model;

import Eccezioni.MossaIllegaleException;
import ingSw2018StaglianoVagagginiVergari.client.View;
import ingSw2018StaglianoVagagginiVergari.server.model.*;
import ingSw2018StaglianoVagagginiVergari.server.model.carteSchema.FactorySchema;
import org.junit.Test;
import org.mockito.Mockito;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;

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


    @Test
    public void calcolaVincitore(){


        Partita PartitaCorrente = new Partita();



        Utente marco = new Utente( new Plancia("colore della vetrata") , Constraint.ROSSO);
        Utente maurizio = new Utente( new Plancia("colore della vetrata") , Constraint.ROSSO);
        Utente leonardo = new Utente( new Plancia("colore della vetrata") , Constraint.ROSSO);

        marco.setId("marco");
        maurizio.setId("maurizio");
        leonardo.setId("leonardo");


        PartitaCorrente.addUtente(marco);
        PartitaCorrente.addUtente(maurizio);
        PartitaCorrente.addUtente(leonardo);

        PartitaCorrente.getOrdineRound().add(marco);
        PartitaCorrente.getOrdineRound().add(maurizio);
        PartitaCorrente.getOrdineRound().add(leonardo);




        System.out.println("\n\nquesta è la lista dei giocatori: "+PartitaCorrente.getListaGiocatori().get(0).getId()+"\n\n");


        ArrayList<Utente> listaGiocatori = new ArrayList<Utente>();


        marco.setSegnalini(1);
        maurizio.setSegnalini(6);
        leonardo.setSegnalini(12);


        listaGiocatori.add(marco);
        listaGiocatori.add(maurizio);
        listaGiocatori.add(leonardo);


        HashMap<String, Integer> listaPunteggiUtente = new HashMap<>();
        HashMap<String, Integer> listaPunteggiUtenteObiettivoPrivato = new HashMap<>();


        listaPunteggiUtente.put("marco",1);
        listaPunteggiUtente.put("maurizio",2);
        listaPunteggiUtente.put("leonardo",3);

        listaPunteggiUtenteObiettivoPrivato.put("marco",5);
        listaPunteggiUtenteObiettivoPrivato.put("maurizio",6);
        listaPunteggiUtenteObiettivoPrivato.put("leonardo",2);

        System.out.println("prova:");
        System.out.println("listaPunteggiUtente "+listaPunteggiUtente+"listaPunteggiUtenteObiettivoPrivato "+listaPunteggiUtenteObiettivoPrivato);

        String vincitore = new String();

        vincitore = PartitaCorrente.calcolaVincitore(listaPunteggiUtente,listaPunteggiUtenteObiettivoPrivato);

        assertTrue(vincitore.equals("leonardo"));




    }

    @Test
    public void piazzamentoDado(){


        Dado dadoSelezionato = new Dado("Verde",6);

        Partita PartitaCorrente = new Partita();

        Utente marco = new Utente( new Plancia("colore della vetrata") , Constraint.ROSSO);

        PartitaCorrente.setGiocatoreCorrente(marco);
        PartitaCorrente.setDadoSelezionato(dadoSelezionato);

        marco.setId("marco");
        PartitaCorrente.addUtente(marco);
        PartitaCorrente.getOrdineRound().add(marco);


        int i=0;
        int j=0;



        View view = Mockito.mock(View.class );


        String schema = "LuzCelestial";
        Boolean fronte = true;

        int schemaN = 0;

        if (schema.equals("KaleidoscopicDream")){
            schemaN=1;
        }else if(schema.equals("Virtus")){
            schemaN=2;
        }else if(schema.equals("AuroraeMagnificus")){
            schemaN=3;
        }else if(schema.equals("ViaLux")){
            schemaN=4;
        }else if(schema.equals("SunCatcher")){
            schemaN=5;
        }else if(schema.equals("Bellesguard")){
            schemaN=6;
        }else if(schema.equals("Gravitas")){
            schemaN=7;
        }else if(schema.equals("FractalDrops")){
            schemaN=8;
        }else if(schema.equals("LuxAstram")){
            schemaN=9;
        }else if(schema.equals("ChromaticSplendor")){
            schemaN=10;
        }else if(schema.equals("Firelight")){
            schemaN=11;
        }else if(schema.equals("LuzCelestial")) {
            schemaN = 12;
        }


        PartitaCorrente.getCurrentPlayer().getPlancia().inserisciCartaSchema(FactorySchema.get(schemaN));
        PartitaCorrente.getCurrentPlayer().scegliFacciaSchema(fronte);

        System.out.println(PartitaCorrente.getCurrentPlayer().getPlancia().getCartaSchema());

        try {
            PartitaCorrente.piazzamentoDado(i,j,false,false);
        } catch (RemoteException e) {
            e.printStackTrace();
        }

        assertTrue(PartitaCorrente.getCurrentPlayer().getPlancia().leggiPlancia(0,0).getNumero() == 6);
        assertTrue(PartitaCorrente.getCurrentPlayer().getPlancia().leggiPlancia(0,0).getColore().equals("Verde"));


        i=3;
        j=3;

        try {
            PartitaCorrente.piazzamentoDado(i,j,false,false);
        } catch (RemoteException e) {
            e.printStackTrace();
        }

        assertTrue(PartitaCorrente.getCurrentPlayer().getPlancia().leggiPlancia(3,3) == null);



    }

    @Test
    public void calcolaPunteggioFinale() throws MossaIllegaleException {

        Partita PartitaCorrente = new Partita();
        PartitaCorrente.setListaCartaObiettivoPubblico();
        PartitaCorrente.setListaCartaUtensile();



        Utente marco = new Utente( new Plancia("colore della vetrata") , Constraint.VERDE);
        Utente maurizio = new Utente( new Plancia("colore della vetrata") , Constraint.ROSSO);
        Utente leonardo = new Utente( new Plancia("colore della vetrata") , Constraint.ROSSO);

        marco.setId("marco");
        maurizio.setId("maurizio");
        leonardo.setId("leonardo");




        marco.getPlancia().inserisciCartaSchema(FactorySchema.get(12));
        marco.scegliFacciaSchema(true);
        Dado d1 = new Dado("Verde",6);
        marco.getPlancia().calcolaMosse(d1,false,false);
        marco.getPlancia().piazzaDado(0,0,d1);

        maurizio.getPlancia().inserisciCartaSchema(FactorySchema.get(12));
        maurizio.scegliFacciaSchema(true);
        Dado d2 = new Dado("Verde",6);
        maurizio.getPlancia().calcolaMosse(d2,false,false);
        maurizio.getPlancia().piazzaDado(0,0,d2);

        leonardo.getPlancia().inserisciCartaSchema(FactorySchema.get(12));
        leonardo.scegliFacciaSchema(true);
        //Dado d1 = new Dado("Verde",6);
        //leonardo.getPlancia().calcolaMosse(d1,false,false);
        //leonardo.getPlancia().piazzaDado(0,0,d1);




        PartitaCorrente.addUtente(marco);
        PartitaCorrente.addUtente(maurizio);
        PartitaCorrente.addUtente(leonardo);

        PartitaCorrente.getOrdineRound().add(marco);
        PartitaCorrente.getOrdineRound().add(maurizio);
        PartitaCorrente.getOrdineRound().add(leonardo);

        PartitaCorrente.setListaCartaUtensile();
        PartitaCorrente.inizializzaMazzoCarteObiettivoPubblico();
        PartitaCorrente.setListaCartaObiettivoPubblico();




        System.out.println("\n\nquesta è la lista dei giocatori: "+PartitaCorrente.getListaGiocatori()+"\n\n");


        marco.setSegnalini(1);
        maurizio.setSegnalini(6);
        leonardo.setSegnalini(12);

        /*
        ArrayList<Utente> listaGiocatori = new ArrayList<Utente>();





        listaGiocatori.add(marco);
        listaGiocatori.add(maurizio);
        listaGiocatori.add(leonardo);
        */


        try {
            PartitaCorrente.calcolaPunteggioFinale();
        } catch (RemoteException e) {
            e.printStackTrace();
        }

        System.out.println("marco "+PartitaCorrente.getListaPunteggiUtente().get("marco"));
        System.out.println("maurizio "+PartitaCorrente.getListaPunteggiUtente().get("maurizio"));
        System.out.println("leonardo "+PartitaCorrente.getListaPunteggiUtente().get("leonardo"));

        assertTrue(PartitaCorrente.getListaPunteggiUtente().get("marco") == -12);
        assertTrue(PartitaCorrente.getListaPunteggiUtente().get("maurizio") == -13);
        assertTrue(PartitaCorrente.getListaPunteggiUtente().get("leonardo") == -8);


    }





}
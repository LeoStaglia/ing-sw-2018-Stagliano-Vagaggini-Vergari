package ingSw2018StaglianoVagagginiVergari.model.carteUtensile;

import Eccezioni.MossaIllegaleException;
import ingSw2018StaglianoVagagginiVergari.client.View;
import ingSw2018StaglianoVagagginiVergari.server.model.*;
import ingSw2018StaglianoVagagginiVergari.server.model.carteSchema.FactorySchema;
import org.junit.Test;
import org.mockito.Mockito;

import java.rmi.RemoteException;

import static org.junit.Assert.assertTrue;


public class MartellettoTest {
    @Test   //conta==1 && p.getAzioniGiocatore().contains(1)
    public void usaEffettoCarta_test1() throws MossaIllegaleException, RemoteException {


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
        CartaUtensile carta1 = FactoryCartaUtensile.getCartaUtensile(7);

        PartitaCorrente.getRiserva().add(new Dado("Giallo", 1));
        PartitaCorrente.getRiserva().add(new Dado("Rosso", 2));
        PartitaCorrente.getRiserva().add(new Dado("Verde", 3));



        PartitaCorrente.getCurrentPlayer().setId("giocatore");
        View view = Mockito.mock(View.class );
        PartitaCorrente.addObserver(view,"giocatore");








        String schema = "Bellesguard";
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









        PartitaCorrente.getOrdineRound().add(PartitaCorrente.getCurrentPlayer());
        PartitaCorrente.setOrdineRound();
        System.out.println("turno:"+ PartitaCorrente.getTurno());








        Dado dadoSelezionato = new Dado("Viola",6);     //il dado selezionato
        PartitaCorrente.setDadoSelezionato(dadoSelezionato);




        System.out.println("Riserva prina dell'effetto carta"+PartitaCorrente.getRiserva());



        carta1.usaEffettoCarta(PartitaCorrente);




        System.out.println("Riserva dopo l'effetto carta"+PartitaCorrente.getRiserva());



        assertTrue(PartitaCorrente.getRiserva().get(0).getColore().equals("Giallo"));
        assertTrue(PartitaCorrente.getRiserva().get(1).getColore().equals("Rosso"));
        assertTrue(PartitaCorrente.getRiserva().get(2).getColore().equals("Verde"));








    }

    @Test   // caso in cui ho già posizionato un dado
    public void usaEffettoCarta_test2() throws MossaIllegaleException, RemoteException {


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
        CartaUtensile carta1 = FactoryCartaUtensile.getCartaUtensile(7);

        PartitaCorrente.getRiserva().add(new Dado("Giallo", 1));
        PartitaCorrente.getRiserva().add(new Dado("Rosso", 2));
        PartitaCorrente.getRiserva().add(new Dado("Verde", 3));



        PartitaCorrente.getCurrentPlayer().setId("giocatore");
        View view = Mockito.mock(View.class );
        PartitaCorrente.addObserver(view,"giocatore");








        String schema = "Bellesguard";
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









        PartitaCorrente.getOrdineRound().add(PartitaCorrente.getCurrentPlayer());
        PartitaCorrente.setOrdineRound();
        System.out.println("turno:"+ PartitaCorrente.getTurno());








        Dado dadoSelezionato = new Dado("Viola",6);     //il dado selezionato
        PartitaCorrente.setDadoSelezionato(dadoSelezionato);




        System.out.println("Riserva prina dell'effetto carta"+PartitaCorrente.getRiserva());


        PartitaCorrente.getAzioniGiocatore().remove(1);
        System.out.println( "queste sono le azioni possibili: " + PartitaCorrente.getAzioniGiocatore());


        carta1.usaEffettoCarta(PartitaCorrente);




        System.out.println("Riserva dopo l'effetto carta"+PartitaCorrente.getRiserva());



        assertTrue(PartitaCorrente.getRiserva().get(0).getColore().equals("Giallo") && PartitaCorrente.getRiserva().get(0).getNumero() == 1 );
        assertTrue(PartitaCorrente.getRiserva().get(1).getColore().equals("Rosso") && PartitaCorrente.getRiserva().get(1).getNumero() == 2 );
        assertTrue(PartitaCorrente.getRiserva().get(2).getColore().equals("Verde") && PartitaCorrente.getRiserva().get(2).getNumero() == 3 );










    }

    @Test   // caso in cui ho già posizionato un dado
    public void usaEffettoCarta_test3() throws MossaIllegaleException, RemoteException {


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
        CartaUtensile carta1 = FactoryCartaUtensile.getCartaUtensile(7);

        PartitaCorrente.getRiserva().add(new Dado("Giallo", 1));
        PartitaCorrente.getRiserva().add(new Dado("Rosso", 2));
        PartitaCorrente.getRiserva().add(new Dado("Verde", 3));



        PartitaCorrente.getCurrentPlayer().setId("giocatore");
        View view = Mockito.mock(View.class );
        PartitaCorrente.addObserver(view,"giocatore");








        String schema = "Bellesguard";
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






        PartitaCorrente.getOrdineRound().add(PartitaCorrente.getCurrentPlayer());
        PartitaCorrente.getOrdineRound().add( new Utente( new Plancia("colore della vetrata") , coloreObiettivoPrivato) );
        PartitaCorrente.setOrdineRound();
        System.out.println("turno:"+ PartitaCorrente.getTurno());








        Dado dadoSelezionato = new Dado("Viola",6);     //il dado selezionato
        PartitaCorrente.setDadoSelezionato(dadoSelezionato);




        System.out.println("Riserva prina dell'effetto carta"+PartitaCorrente.getRiserva());


        System.out.println( "queste sono le azioni possibili: " + PartitaCorrente.getAzioniGiocatore());


        carta1.usaEffettoCarta(PartitaCorrente);




        System.out.println("Riserva dopo l'effetto carta"+PartitaCorrente.getRiserva());



        assertTrue(PartitaCorrente.getRiserva().get(0).getColore().equals("Giallo") && PartitaCorrente.getRiserva().get(0).getNumero() == 1 );
        assertTrue(PartitaCorrente.getRiserva().get(1).getColore().equals("Rosso") && PartitaCorrente.getRiserva().get(1).getNumero() == 2 );
        assertTrue(PartitaCorrente.getRiserva().get(2).getColore().equals("Verde") && PartitaCorrente.getRiserva().get(2).getNumero() == 3 );







        System.out.println("parametri della carta: "+carta1.getId()+"  "+carta1.getDescrizione()+" "+carta1.getNome()+ " "+carta1.getCosto());

        carta1.reset();



    }

}
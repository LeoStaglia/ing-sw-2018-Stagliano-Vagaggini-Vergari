package ingSw2018StaglianoVagagginiVergari.model.carteUtensile;

import Eccezioni.MossaIllegaleException;
import ingSw2018StaglianoVagagginiVergari.client.Printer;
import ingSw2018StaglianoVagagginiVergari.client.View;
import ingSw2018StaglianoVagagginiVergari.server.model.*;
import ingSw2018StaglianoVagagginiVergari.server.model.carteSchema.FactorySchema;
import ingSw2018StaglianoVagagginiVergari.server.model.carteUtensile.PennelloPastaSalda;
import org.junit.Test;
import org.mockito.Mockito;

import java.rmi.RemoteException;
import java.util.ArrayList;

import static org.junit.Assert.assertTrue;


public class PennelloPastaSaldaTest {






    @Test(expected = MossaIllegaleException.class)
        //fase 1 , dado piazzabile
    public void    usaEffettoCarta_test1() throws MossaIllegaleException, RemoteException {


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
        CartaUtensile carta1 = FactoryCartaUtensile.getCartaUtensile(6);


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





        Dado dadoSelezionato = new Dado("Giallo",1);     //il dado selezionato
        PartitaCorrente.setDadoSelezionato(dadoSelezionato);

        System.out.println("\nDado selezionato prima dell'effetto carta"+PartitaCorrente.getDadoSelezionato());


        ((PennelloPastaSalda) carta1).setxCell(1);
        ((PennelloPastaSalda) carta1).setyCell(0);


        ((PennelloPastaSalda) carta1).setFase(true);
        ((PennelloPastaSalda) carta1).setPiazzabile(false);

        carta1.usaEffettoCarta(PartitaCorrente);




        assertTrue(PartitaCorrente.getDadoSelezionato().getColore().equals("Giallo"));


        (new Printer()).printPlancia(PartitaCorrente.getCurrentPlayer().getPlancia().rappresentazionePlancia());


        carta1.usaEffettoCarta(PartitaCorrente);

        (new Printer()).printPlancia(PartitaCorrente.getCurrentPlayer().getPlancia().rappresentazionePlancia());

        assertTrue(PartitaCorrente.getCurrentPlayer().getPlancia().leggiPlancia(1,0).getColore().equals("Giallo"));




        System.out.println("\n\n\nA\n\n\n");
        Dado dadoSelezionato2 = new Dado("Verde",2);     //il dado selezionato
        PartitaCorrente.setDadoSelezionato(dadoSelezionato2);

        ((PennelloPastaSalda) carta1).setxCell(2);
        ((PennelloPastaSalda) carta1).setyCell(2);

        ((PennelloPastaSalda) carta1).setFase(false);




        carta1.usaEffettoCarta(PartitaCorrente);
        //assertThrows(MossaIllegaleException.class, ()->carta1.usaEffettoCarta(PartitaCorrente));

        carta1.reset();




    }

    @Test  //fase 1 , dado non piazzabile
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
        CartaUtensile carta1 = FactoryCartaUtensile.getCartaUtensile(6);
        ((PennelloPastaSalda) carta1).setFase(true);



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


        //inserisco i dadi da posizionare nell'arraylist
        ArrayList<DadoPosizione> DadiEPosizioni = new ArrayList<>();

        DadiEPosizioni.add(new DadoPosizione(new Dado("Blu",5),0,0));
        DadiEPosizioni.add(new DadoPosizione(new Dado("Rosso",4),1,0));
        DadiEPosizioni.add(new DadoPosizione(new Dado("Viola",6),2,0));
        DadiEPosizioni.add(new DadoPosizione(new Dado("Giallo",2),3,0));




        //aggiunti dopo oer prova

        DadiEPosizioni.add(new DadoPosizione(new Dado("Rosso",6),0,1));
        DadiEPosizioni.add(new DadoPosizione(new Dado("Viola",3),1,1));
        DadiEPosizioni.add(new DadoPosizione(new Dado("Giallo",5),2,1));
        DadiEPosizioni.add(new DadoPosizione(new Dado("Blu",4),3,1));


        DadiEPosizioni.add(new DadoPosizione(new Dado("Verde",1),0,2));
        DadiEPosizioni.add(new DadoPosizione(new Dado("Blu",2),1,2));
        DadiEPosizioni.add(new DadoPosizione(new Dado("Viola",6),2,2));
        DadiEPosizioni.add(new DadoPosizione(new Dado("Rosso",3),3,2));


        DadiEPosizioni.add(new DadoPosizione(new Dado("Rosso",3),0,3));
        DadiEPosizioni.add(new DadoPosizione(new Dado("Giallo",4),1,3));
        DadiEPosizioni.add(new DadoPosizione(new Dado("Blu",2),2,3));
        DadiEPosizioni.add(new DadoPosizione(new Dado("Viola",1),3,3));

        DadiEPosizioni.add(new DadoPosizione(new Dado("Giallo",5),0,4));
        DadiEPosizioni.add(new DadoPosizione(new Dado("Viola",6),1,4));
        DadiEPosizioni.add(new DadoPosizione(new Dado("Rosso",3),2,4));
        DadiEPosizioni.add(new DadoPosizione(new Dado("Verde",4),3,4));







        for (DadoPosizione dxy : DadiEPosizioni){

            System.out.println("\nposiziono:");
            PartitaCorrente.getCurrentPlayer().getPlancia().calcolaMosse(dxy.d,false,false);
            PartitaCorrente.getCurrentPlayer().getPlancia().piazzaDado(dxy.x,dxy.y,dxy.d);
            (new Printer()).printPlancia(PartitaCorrente.getCurrentPlayer().getPlancia().rappresentazionePlancia());

        }




        Dado dadoSelezionato = new Dado("Giallo",1);     //il dado selezionato
        PartitaCorrente.setDadoSelezionato(dadoSelezionato);

        System.out.println("\nDado selezionato prima dell'effetto carta"+PartitaCorrente.getDadoSelezionato());
        System.out.println("Riserva prina dell'effetto carta"+PartitaCorrente.getRiserva());


        ((PennelloPastaSalda) carta1).setxCell(3);
        ((PennelloPastaSalda) carta1).setyCell(3);


        //these two instructions are required to set default values to the card
        ((PennelloPastaSalda) carta1).setFase(true);
        ((PennelloPastaSalda) carta1).setPiazzabile(false);

        carta1.usaEffettoCarta(PartitaCorrente);




        System.out.println("\nDado selezionato dopo dell'effetto carta"+PartitaCorrente.getDadoSelezionato());
        System.out.println("Riserva dopo dell'effetto carta"+PartitaCorrente.getRiserva());





        assertTrue(PartitaCorrente.getRiserva().get(0).getColore().equals("Giallo"));


        System.out.println("parametri della carta: "+carta1.getId()+"  "+carta1.getDescrizione()+" "+carta1.getNome()+ " "+carta1.getCosto() + ""+((PennelloPastaSalda) carta1).isFase1()+" "+((PennelloPastaSalda) carta1).isPiazzabile());

        carta1.reset();



    }








}
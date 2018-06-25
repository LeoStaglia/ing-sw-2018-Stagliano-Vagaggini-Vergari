package ingSw2018StaglianoVagagginiVergari.model.carteUtensile;

import Eccezioni.MossaIllegaleException;
import ingSw2018StaglianoVagagginiVergari.client.Printer;
import ingSw2018StaglianoVagagginiVergari.client.View;
import ingSw2018StaglianoVagagginiVergari.server.model.*;
import ingSw2018StaglianoVagagginiVergari.server.model.carteSchema.FactorySchema;
import ingSw2018StaglianoVagagginiVergari.server.model.carteUtensile.DiluentePerPastaSalda;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.rmi.RemoteException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertTrue;

class DiluentePerPastaSaldaTest {

    @Test   //fase 1 , dado piazzabile
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
        CartaUtensile carta1 = FactoryCartaUtensile.getCartaUtensile(11);


        PartitaCorrente.getCurrentPlayer().setId("giocatore");
        View view = Mockito.mock(View.class );
        PartitaCorrente.addObserver(view,"giocatore");

        PartitaCorrente.getRiserva().add(new Dado("Giallo",4));
        PartitaCorrente.getRiserva().add(new Dado("Verde",5));
        PartitaCorrente.getRiserva().add(new Dado("Rosso",6));





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

        System.out.println("\nDado selezionato prima dell'effetto carta "+PartitaCorrente.getDadoSelezionato());


        ((DiluentePerPastaSalda) carta1).setX(1);
        ((DiluentePerPastaSalda) carta1).setY(0);




        //System.out.println("ora sono nella seconda fase  "+ ((DiluentePerPastaSalda) carta1).secondPhase );



        carta1.usaEffettoCarta(PartitaCorrente);




        assertTrue(PartitaCorrente.getAzioniGiocatore().contains(2));


        (new Printer()).printPlancia(PartitaCorrente.getCurrentPlayer().getPlancia().rappresentazionePlancia());


        //System.out.println("ora sono nella seconda fase  "+ ((DiluentePerPastaSalda) carta1).secondPhase );


        carta1.usaEffettoCarta(PartitaCorrente);

        (new Printer()).printPlancia(PartitaCorrente.getCurrentPlayer().getPlancia().rappresentazionePlancia());

        assertTrue(PartitaCorrente.getCurrentPlayer().getPlancia().leggiPlancia(1,0).getColore().equals("Giallo"));







        //System.out.println("\n\n\nciaon\n\n\n");
        Dado dadoSelezionato2 = new Dado("Verde",2);     //il dado selezionato
        PartitaCorrente.setDadoSelezionato(dadoSelezionato2);

        ((DiluentePerPastaSalda) carta1).setX(3);
        ((DiluentePerPastaSalda) carta1).setY(3);

        ((DiluentePerPastaSalda) carta1).setFase(true);


        System.out.println("riserva prima "+PartitaCorrente.getRiserva());
        System.out.println("dado in mano "+PartitaCorrente.getDadoSelezionato());

        carta1.usaEffettoCarta(PartitaCorrente);

        System.out.println("riserva dopo "+PartitaCorrente.getRiserva());


        System.out.println(PartitaCorrente.getRiserva().get(3));

        assertTrue(PartitaCorrente.getRiserva().get(3).getColore().equals("Verde"));
        assertTrue(PartitaCorrente.getRiserva().get(3).getNumero() == 2);














        carta1.reset();



    }

    @Test  //fase 1 , dado non piazzabile
    void usaEffettoCarta_test2() throws MossaIllegaleException, RemoteException {


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
        CartaUtensile carta1 = FactoryCartaUtensile.getCartaUtensile(11);
        ((DiluentePerPastaSalda) carta1).setFase(true);



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

        DadiEPosizioni.add(new DadoPosizione(new Dado("Blu",6),0,0));
        DadiEPosizioni.add(new DadoPosizione(new Dado("Rosso",4),1,0));
        DadiEPosizioni.add(new DadoPosizione(new Dado("Viola",6),2,0));
        DadiEPosizioni.add(new DadoPosizione(new Dado("Giallo",2),3,0));

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


        ((DiluentePerPastaSalda) carta1).setX(3);
        ((DiluentePerPastaSalda) carta1).setY(3);


        //these two instructions are required to set default values to the card
        ((DiluentePerPastaSalda) carta1).setFase(true);


        carta1.usaEffettoCarta(PartitaCorrente);




        System.out.println("\nDado selezionato dopo dell'effetto carta"+PartitaCorrente.getDadoSelezionato());
        System.out.println("Riserva dopo dell'effetto carta"+PartitaCorrente.getRiserva());





        assertTrue(PartitaCorrente.getRiserva().get(0).getColore().equals("Giallo"));



        System.out.println("parametri della carta: "+carta1.getId()+"  "+carta1.getDescrizione()+" "+carta1.getNome()+ " "+carta1.getCosto()+" "+((DiluentePerPastaSalda) carta1).isSecondPhase());


        carta1.reset();
    }





}
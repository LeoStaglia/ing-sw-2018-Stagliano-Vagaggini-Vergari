package ingSw2018StaglianoVagagginiVergari.model.carteUtensile;

import Eccezioni.MossaIllegaleException;
import ingSw2018StaglianoVagagginiVergari.client.Printer;
import ingSw2018StaglianoVagagginiVergari.client.View;
import ingSw2018StaglianoVagagginiVergari.server.model.*;
import ingSw2018StaglianoVagagginiVergari.server.model.carteSchema.FactorySchema;
import ingSw2018StaglianoVagagginiVergari.server.model.carteUtensile.RigaInSughero;
import org.junit.Test;
import org.mockito.Mockito;

import java.rmi.RemoteException;
import java.util.ArrayList;

import static org.junit.Assert.assertTrue;


public class RigaInSugheroTest {

    @Test //test nel caso il piazzamento vada a buon fine
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
        Dado dadoSelezionato = new Dado("Viola",3);     //da settare

        int schemaScelto = 3;

        //instanzio la partita
        Partita PartitaCorrente = new Partita();
        Utente User = new Utente( new Plancia("colore della vetrata") , coloreObiettivoPrivato);
        PartitaCorrente.addUtente(User);
        PartitaCorrente.setGiocatoreCorrente(User);
        PartitaCorrente.setDadoSelezionato(dadoSelezionato);
        CartaUtensile carta1 = FactoryCartaUtensile.getCartaUtensile(9);

        PartitaCorrente.getCurrentPlayer().setId("giocatore");
        View view = Mockito.mock(View.class );
        PartitaCorrente.addObserver(view,"giocatore");








        String schema = "SunCatcher";
        Boolean fronte = true;      //da settare

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

        DadiEPosizioni.add(new DadoPosizione(new Dado("Giallo",3),0,0));
        DadiEPosizioni.add(new DadoPosizione(new Dado("Rosso",6),1,0));
        DadiEPosizioni.add(new DadoPosizione(new Dado("Verde",4),1,1));



        //con la carta posiziono un dado viola in 3,4


        //posiziono i dadi nella plancia
        for (DadoPosizione dxy : DadiEPosizioni){

            System.out.println("\nposiziono:");
            PartitaCorrente.getCurrentPlayer().getPlancia().calcolaMosse(dxy.d,false,false);
            PartitaCorrente.getCurrentPlayer().getPlancia().piazzaDado(dxy.x,dxy.y,dxy.d);
            (new Printer()).printPlancia(PartitaCorrente.getCurrentPlayer().getPlancia().rappresentazionePlancia());

        }


        //uso l'effetto dellla carta e vedo se effettivamente viene posizionato
        ((RigaInSughero) carta1).setxCell(3);
        ((RigaInSughero) carta1).setyCell(4);
        PartitaCorrente.getAzioniGiocatore().add(1);
        carta1.usaEffettoCarta(PartitaCorrente);

        System.out.println("\nFINALE:");
        (new Printer()).printPlancia(PartitaCorrente.getCurrentPlayer().getPlancia().rappresentazionePlancia());

        //vedo se la carta ha effettivamente fatto variare la plancia, posizionando i dadi


        assertTrue(PartitaCorrente.getCurrentPlayer().getPlancia().leggiPlancia(3,4).getNumero() == 3);
        assertTrue(PartitaCorrente.getCurrentPlayer().getPlancia().leggiPlancia(3,4).getColore().equals("Viola"));

    }

    @Test  //test nel caso in cui non posso posizionare perchè il dado non può andare nella posizione scelta
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
        Dado dadoSelezionato = new Dado("Verde",3);     //da settare

        int schemaScelto = 3;

        //instanzio la partita
        Partita PartitaCorrente = new Partita();
        Utente User = new Utente( new Plancia("colore della vetrata") , coloreObiettivoPrivato);
        PartitaCorrente.addUtente(User);
        PartitaCorrente.setGiocatoreCorrente(User);
        PartitaCorrente.setDadoSelezionato(dadoSelezionato);
        CartaUtensile carta1 = FactoryCartaUtensile.getCartaUtensile(9);

        PartitaCorrente.getCurrentPlayer().setId("giocatore");
        View view = Mockito.mock(View.class );
        PartitaCorrente.addObserver(view,"giocatore");





        String schema = "SunCatcher";
        Boolean fronte = true;      //da settare

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

        DadiEPosizioni.add(new DadoPosizione(new Dado("Giallo",3),0,0));
        DadiEPosizioni.add(new DadoPosizione(new Dado("Rosso",6),1,0));
        DadiEPosizioni.add(new DadoPosizione(new Dado("Verde",4),1,1));



        //con la carta posiziono un dado viola in 3,4


        //posiziono i dadi nella plancia
        for (DadoPosizione dxy : DadiEPosizioni){

            System.out.println("\nposiziono:");
            PartitaCorrente.getCurrentPlayer().getPlancia().calcolaMosse(dxy.d,false,false);
            PartitaCorrente.getCurrentPlayer().getPlancia().piazzaDado(dxy.x,dxy.y,dxy.d);
            (new Printer()).printPlancia(PartitaCorrente.getCurrentPlayer().getPlancia().rappresentazionePlancia());

        }


        //uso l'effetto dellla carta e vedo se effettivamente viene posizionato
        ((RigaInSughero) carta1).setxCell(3);
        ((RigaInSughero) carta1).setyCell(4);
        PartitaCorrente.getAzioniGiocatore().add(1);


        carta1.usaEffettoCarta(PartitaCorrente);




        System.out.println("\nFINALE:");
        (new Printer()).printPlancia(PartitaCorrente.getCurrentPlayer().getPlancia().rappresentazionePlancia());

        //vedo se la carta ha effettivamente fatto variare la plancia, posizionando i dadi




        assertTrue(PartitaCorrente.getCurrentPlayer().getPlancia().leggiPlancia(3,4) == null);



    }

    @Test  //test nel caso in cui non posso posizionare perchè è gia stato posizionato un dado nel turno
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
        Dado dadoSelezionato = new Dado("Viola",3);     //da settare

        int schemaScelto = 3;

        //instanzio la partita
        Partita PartitaCorrente = new Partita();
        Utente User = new Utente( new Plancia("colore della vetrata") , coloreObiettivoPrivato);
        PartitaCorrente.addUtente(User);
        PartitaCorrente.setGiocatoreCorrente(User);
        PartitaCorrente.setDadoSelezionato(dadoSelezionato);
        CartaUtensile carta1 = FactoryCartaUtensile.getCartaUtensile(9);

        PartitaCorrente.getCurrentPlayer().setId("giocatore");
        View view = Mockito.mock(View.class );
        PartitaCorrente.addObserver(view,"giocatore");







        String schema = "SunCatcher";
        Boolean fronte = true;      //da settare

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

        DadiEPosizioni.add(new DadoPosizione(new Dado("Giallo",3),0,0));
        DadiEPosizioni.add(new DadoPosizione(new Dado("Rosso",6),1,0));
        DadiEPosizioni.add(new DadoPosizione(new Dado("Verde",4),1,1));



        //con la carta posiziono un dado viola in 3,4


        //posiziono i dadi nella plancia
        for (DadoPosizione dxy : DadiEPosizioni){

            System.out.println("\nposiziono:");
            PartitaCorrente.getCurrentPlayer().getPlancia().calcolaMosse(dxy.d,false,false);
            PartitaCorrente.getCurrentPlayer().getPlancia().piazzaDado(dxy.x,dxy.y,dxy.d);
            (new Printer()).printPlancia(PartitaCorrente.getCurrentPlayer().getPlancia().rappresentazionePlancia());

        }


        //uso l'effetto dellla carta e vedo se effettivamente viene posizionato
        ((RigaInSughero) carta1).setxCell(3);
        ((RigaInSughero) carta1).setyCell(4);
        PartitaCorrente.getAzioniGiocatore().clear();


            carta1.usaEffettoCarta(PartitaCorrente);




        System.out.println("\nFINALE:");
        (new Printer()).printPlancia(PartitaCorrente.getCurrentPlayer().getPlancia().rappresentazionePlancia());

        //vedo se la carta ha effettivamente fatto variare la plancia, posizionando i dadi




        assertTrue(PartitaCorrente.getCurrentPlayer().getPlancia().leggiPlancia(3,4) == null);



        System.out.println("parametri della carta: "+carta1.getId()+"  "+carta1.getDescrizione()+" "+carta1.getNome()+ " "+carta1.getCosto());

        carta1.reset();

    }




}
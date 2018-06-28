package ingSw2018StaglianoVagagginiVergari.model.carteUtensile;

import Eccezioni.MossaIllegaleException;
import ingSw2018StaglianoVagagginiVergari.client.View;
import ingSw2018StaglianoVagagginiVergari.server.model.*;
import ingSw2018StaglianoVagagginiVergari.server.model.carteSchema.FactorySchema;
import ingSw2018StaglianoVagagginiVergari.server.model.carteUtensile.PinzaSgrossatrice;
import org.junit.Test;
import org.mockito.Mockito;

import java.rmi.RemoteException;

import static org.junit.Assert.assertTrue;


public class PinzaSgrossatriceTest {
    @Test
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
        CartaUtensile carta1 = FactoryCartaUtensile.getCartaUtensile(1);


        PartitaCorrente.getCurrentPlayer().setId("giocatore");
        View view = Mockito.mock(View.class );
        PartitaCorrente.addObserver(view,"giocatore");









        String schema = "ChromaticSplendor";
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











        //==================== 1° caso incremento


        Dado dadoSelezionato = new Dado("Verde",1);     //da settare
        PartitaCorrente.setDadoSelezionato(dadoSelezionato);

        System.out.println("\nDado selezionato prima dell'effetto carta"+PartitaCorrente.getDadoSelezionato());

        ((PinzaSgrossatrice) carta1).setScelta(1); //incrementa

        carta1.usaEffettoCarta(PartitaCorrente);


        System.out.println("Riserva dopo l'effetto carta"+PartitaCorrente.getRiserva());

        assertTrue(PartitaCorrente.getRiserva().get(0).getNumero() == 2);



        //==================== 2° caso decremento


        dadoSelezionato = new Dado("Verde",3);     //da settare
        PartitaCorrente.setDadoSelezionato(dadoSelezionato);

        System.out.println("\nDado selezionato prima dell'effetto carta"+PartitaCorrente.getDadoSelezionato());

        ((PinzaSgrossatrice) carta1).setScelta(-1); //incrementa

        carta1.usaEffettoCarta(PartitaCorrente);


        System.out.println("Riserva dopo l'effetto carta"+PartitaCorrente.getRiserva());

        assertTrue(PartitaCorrente.getRiserva().get(1).getNumero() == 2);


        //==================== 3° caso incremento il 6


        dadoSelezionato = new Dado("Viola",6);     //da settare
        PartitaCorrente.setDadoSelezionato(dadoSelezionato);

        System.out.println("\nDado selezionato prima dell'effetto carta"+PartitaCorrente.getDadoSelezionato());

        ((PinzaSgrossatrice) carta1).setScelta(1); //incrementa

        carta1.usaEffettoCarta(PartitaCorrente);


        System.out.println("Riserva dopo l'effetto carta"+PartitaCorrente.getRiserva());

        assertTrue(PartitaCorrente.getRiserva().get(2).getNumero() == 6);


        //==================== 4° caso decremento 1


        dadoSelezionato = new Dado("Viola",1);     //da settare
        PartitaCorrente.setDadoSelezionato(dadoSelezionato);

        System.out.println("\nDado selezionato prima dell'effetto carta"+PartitaCorrente.getDadoSelezionato());

        ((PinzaSgrossatrice) carta1).setScelta(-1); //incrementa

        carta1.usaEffettoCarta(PartitaCorrente);


        System.out.println("Riserva dopo l'effetto carta"+PartitaCorrente.getRiserva());

        assertTrue(PartitaCorrente.getRiserva().get(3).getNumero() == 1);





        System.out.println("parametri della carta: "+carta1.getId()+"  "+carta1.getDescrizione()+" "+carta1.getNome()+ " "+carta1.getCosto());

        carta1.reset();


    }

}
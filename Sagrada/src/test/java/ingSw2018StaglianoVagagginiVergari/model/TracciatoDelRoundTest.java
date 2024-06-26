package ingSw2018StaglianoVagagginiVergari.model;

import ingSw2018StaglianoVagagginiVergari.server.model.Dado;
import ingSw2018StaglianoVagagginiVergari.server.model.TracciatoDelRound;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;


public class TracciatoDelRoundTest {
    @Test
    public void getRoundAttuale() {
        TracciatoDelRound t=new TracciatoDelRound();
        int a=t.getRoundAttuale()+1;
        t.nextRound();
        assertEquals(a,t.getRoundAttuale());

    }

    @Test
    public void nextRound() {
        TracciatoDelRound t=new TracciatoDelRound();
        int a=t.getRoundAttuale();
        t.nextRound();
        assertEquals(a,t.getRoundAttuale()-1);
    }

    @Test
    public void addRimanenzeRiservaOut() {
        TracciatoDelRound t=new TracciatoDelRound();
        ArrayList<Dado> rimanenzeRiservaOut=new ArrayList<Dado>();
        ArrayList<Dado> rimanenze=new ArrayList<Dado>();
        rimanenzeRiservaOut=t.getRimanenzeRiservaOut();

        Dado d1= Mockito.mock(Dado.class);
        Dado d2=Mockito.mock(Dado.class);
        rimanenze.add(d1);
        rimanenze.add(d2);
        t.addRimanenzeRiservaOut(rimanenze);

        //assertIterableEquals(rimanenzeRiservaOut,rimanenze);
        assertEquals(rimanenze.get(0), rimanenzeRiservaOut.get(0));
        assertEquals(rimanenze.get(1), rimanenzeRiservaOut.get(1));


    }

    @Test
    public void addRimanenzeRiservaOn() {
        TracciatoDelRound t=new TracciatoDelRound();
        Dado d1= Mockito.mock(Dado.class);
        Dado d2=Mockito.mock(Dado.class);
        t.addRimanenzeRiservaOn(d1);
        t.addRimanenzeRiservaOn(d2);
        assertEquals(t.getRimanenzeRiservaOn(1),d1);
        assertEquals(t.getRimanenzeRiservaOn(2),d2);



    }



}
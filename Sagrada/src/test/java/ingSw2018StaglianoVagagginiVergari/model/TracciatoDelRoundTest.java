package ingSw2018StaglianoVagagginiVergari.model;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;

class TracciatoDelRoundTest {
    @Test
    void getRoundAttuale() {
        TracciatoDelRound t=new TracciatoDelRound();
        int a=t.getRoundAttuale()+1;
        t.nextRound();
        assertEquals(a,t.getRoundAttuale());

    }

    @Test
    void nextRound() {
        TracciatoDelRound t=new TracciatoDelRound();
        int a=t.getRoundAttuale();
        t.nextRound();
        assertEquals(a,t.getRoundAttuale()-1);
    }

    @Test
    void addRimanenzeRiservaOut() {
        TracciatoDelRound t=new TracciatoDelRound();
        HashSet<Dado> rimanenzeRiservaOut=new HashSet<Dado>();
        HashSet<Dado> rimanenze=new HashSet<Dado>();
        rimanenzeRiservaOut=t.getRimanenzeRiservaOut();

        Dado d1= Mockito.mock(Dado.class);
        Dado d2=Mockito.mock(Dado.class);
        rimanenze.add(d1);
        rimanenze.add(d2);
        t.addRimanenzeRiservaOut(rimanenze);
        assertIterableEquals(rimanenzeRiservaOut,rimanenze);


    }

    @Test
    void addRimanenzeRiservaOn() {
        TracciatoDelRound t=new TracciatoDelRound();
        Dado d1= Mockito.mock(Dado.class);
        Dado d2=Mockito.mock(Dado.class);
        t.addRimanenzeRiservaOn(d1);
        t.addRimanenzeRiservaOn(d2);
        assertEquals(t.getRimanenzeRiservaOn(1),d1);
        assertEquals(t.getRimanenzeRiservaOn(2),d2);



    }

    @Test
    void getRimanenzeRiservaOn() {

    }

    @Test
    void calcolaSommaRimanenzeRiserva() {
    }

}
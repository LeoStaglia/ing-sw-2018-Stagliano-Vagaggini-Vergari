package ingSw2018StaglianoVagagginiVergari.model;

import ingSw2018StaglianoVagagginiVergari.server.model.Constraint;
import ingSw2018StaglianoVagagginiVergari.server.model.carteSchema.KaleidoscopicDream;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;


public class KaleidoscopicDreamTest{




    @Test
    public void get() {
       //Verifying singleton integrity.
        KaleidoscopicDream k1 = KaleidoscopicDream.get();
        KaleidoscopicDream k2 = KaleidoscopicDream.get();
        assertEquals(k1, k2);

    }



    @Test
    public void getRestrizione() {
        KaleidoscopicDream k = KaleidoscopicDream.get();
        //<---------------------------------------> Testing the integrity of the front face of the card and the method getRestrizione()
        k.scegliFaccia(true); //here we can test the method called scegliFaccia
        ArrayList<Constraint> constraints = new ArrayList<Constraint>();
        constraints.add(Constraint.GIALLO);
        constraints.add(Constraint.BLU);
        constraints.add(Constraint.NONE);
        constraints.add(Constraint.NONE);
        constraints.add(Constraint.UNO);
        constraints.add(Constraint.VERDE);
        constraints.add(Constraint.NONE);
        constraints.add(Constraint.CINQUE);
        constraints.add(Constraint.NONE);
        constraints.add(Constraint.QUATTRO);
        constraints.add(Constraint.TRE);
        constraints.add(Constraint.NONE);
        constraints.add(Constraint.ROSSO);
        constraints.add(Constraint.NONE);
        constraints.add(Constraint.VERDE);
        constraints.add(Constraint.DUE);
        constraints.add(Constraint.NONE);
        constraints.add(Constraint.NONE);
        constraints.add(Constraint.BLU);
        constraints.add(Constraint.GIALLO);
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 5; j++) {
                assertEquals(k.getRestrizione(i, j), constraints.remove(0));
            }
        }
        //<---------------------------------------> Testing the integrity of the back face of the card and the method getRestrizione()
        k.scegliFaccia(false);
        constraints.add(Constraint.VIOLA);
        constraints.add(Constraint.SEI);
        constraints.add(Constraint.NONE);
        constraints.add(Constraint.NONE);
        constraints.add(Constraint.TRE);
        constraints.add(Constraint.CINQUE);
        constraints.add(Constraint.VIOLA);
        constraints.add(Constraint.TRE);
        constraints.add(Constraint.NONE);
        constraints.add(Constraint.NONE);
        constraints.add(Constraint.NONE);
        constraints.add(Constraint.DUE);
        constraints.add(Constraint.VIOLA);
        constraints.add(Constraint.UNO);
        constraints.add(Constraint.NONE);
        constraints.add(Constraint.NONE);
        constraints.add(Constraint.UNO);
        constraints.add(Constraint.CINQUE);
        constraints.add(Constraint.VIOLA);
        constraints.add(Constraint.QUATTRO);
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 5; j++) {
                assertEquals(k.getRestrizione(i, j), constraints.remove(0));
            }

        }


    }


}
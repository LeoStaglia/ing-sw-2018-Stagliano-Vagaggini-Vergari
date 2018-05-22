package ingSw2018StaglianoVagagginiVergari.server.model.carteSchema;

import ingSw2018StaglianoVagagginiVergari.server.model.Constraint;

import java.io.Serializable;
import java.util.ArrayList;

public class Virtus extends CartaSchema implements Serializable{
    private static Virtus istanza;
    private static Constraint[][] costruisciGrigliaFronte(){
        ArrayList<Constraint> constraints = new ArrayList<Constraint>();
        constraints.add(Constraint.QUATTRO);
        constraints.add(Constraint.NONE);
        constraints.add(Constraint.DUE);
        constraints.add(Constraint.CINQUE);
        constraints.add(Constraint.VERDE);

        constraints.add(Constraint.NONE);
        constraints.add(Constraint.NONE);
        constraints.add(Constraint.SEI);
        constraints.add(Constraint.VERDE);
        constraints.add(Constraint.DUE);

        constraints.add(Constraint.NONE);
        constraints.add(Constraint.TRE);
        constraints.add(Constraint.VERDE);
        constraints.add(Constraint.QUATTRO);
        constraints.add(Constraint.NONE);

        constraints.add(Constraint.CINQUE);
        constraints.add(Constraint.VERDE);
        constraints.add(Constraint.UNO);
        constraints.add(Constraint.NONE);
        constraints.add(Constraint.NONE);
        Constraint[][] risultato = new Constraint[4][5];
        for (int i=0; i<4; i++){
            for (int j=0; j<5; j++){
                risultato[i][j]= constraints.remove(0);
            }
        }
        return risultato;

    }
    private static Constraint[][] costruisciGrigliaRetro(){
        ArrayList<Constraint> constraints = new ArrayList<Constraint>();
        constraints.add(Constraint.DUE);
        constraints.add(Constraint.NONE);
        constraints.add(Constraint.CINQUE);
        constraints.add(Constraint.NONE);
        constraints.add(Constraint.UNO);

        constraints.add(Constraint.GIALLO);
        constraints.add(Constraint.SEI);
        constraints.add(Constraint.VIOLA);
        constraints.add(Constraint.DUE);
        constraints.add(Constraint.ROSSO);

        constraints.add(Constraint.NONE);
        constraints.add(Constraint.BLU);
        constraints.add(Constraint.QUATTRO);
        constraints.add(Constraint.VERDE);
        constraints.add(Constraint.NONE);

        constraints.add(Constraint.NONE);
        constraints.add(Constraint.TRE);
        constraints.add(Constraint.NONE);
        constraints.add(Constraint.CINQUE);
        constraints.add(Constraint.NONE);
        Constraint[][] risultato = new Constraint[4][5];
        for (int i=0; i<4; i++){
            for (int j=0; j<5; j++){
                risultato[i][j]= constraints.remove(0);
            }
        }
        return risultato;

    }

    private Virtus(){
        super("Virtus", "Symphony of Light", 5,6,costruisciGrigliaFronte(), costruisciGrigliaRetro());
    }

    public static Virtus get(){
        if (istanza==null){
            istanza= new Virtus();
        }
        return istanza;
    }

}

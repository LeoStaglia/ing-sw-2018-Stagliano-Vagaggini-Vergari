package ingSw2018StaglianoVagagginiVergari.server.model.carteSchema;

import ingSw2018StaglianoVagagginiVergari.server.model.Constraint;

import java.io.Serializable;
import java.util.ArrayList;

public class Gravitas extends CartaSchema implements Serializable{
    private static Gravitas istanza;
    private static Constraint[][] costruisciGrigliaFronte(){
        ArrayList<Constraint> constraints = new ArrayList<Constraint>();
        constraints.add(Constraint.UNO);
        constraints.add(Constraint.NONE);
        constraints.add(Constraint.TRE);
        constraints.add(Constraint.BLU);
        constraints.add(Constraint.NONE);

        constraints.add(Constraint.NONE);
        constraints.add(Constraint.DUE);
        constraints.add(Constraint.BLU);
        constraints.add(Constraint.NONE);
        constraints.add(Constraint.NONE);

        constraints.add(Constraint.SEI);
        constraints.add(Constraint.BLU);
        constraints.add(Constraint.NONE);
        constraints.add(Constraint.QUATTRO);
        constraints.add(Constraint.NONE);

        constraints.add(Constraint.BLU);
        constraints.add(Constraint.CINQUE);
        constraints.add(Constraint.DUE);
        constraints.add(Constraint.NONE);
        constraints.add(Constraint.UNO);
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
        constraints.add(Constraint.SEI);
        constraints.add(Constraint.BLU);
        constraints.add(Constraint.NONE);
        constraints.add(Constraint.NONE);
        constraints.add(Constraint.UNO);

        constraints.add(Constraint.NONE);
        constraints.add(Constraint.CINQUE);
        constraints.add(Constraint.BLU);
        constraints.add(Constraint.NONE);
        constraints.add(Constraint.NONE);

        constraints.add(Constraint.QUATTRO);
        constraints.add(Constraint.ROSSO);
        constraints.add(Constraint.DUE);
        constraints.add(Constraint.BLU);
        constraints.add(Constraint.NONE);

        constraints.add(Constraint.VERDE);
        constraints.add(Constraint.SEI);
        constraints.add(Constraint.GIALLO);
        constraints.add(Constraint.TRE);
        constraints.add(Constraint.VIOLA);
        Constraint[][] risultato = new Constraint[4][5];
        for (int i=0; i<4; i++){
            for (int j=0; j<5; j++){
                risultato[i][j]= constraints.remove(0);
            }
        }
        return risultato;

    }

    private Gravitas(){
        super("Gravitas", "Water of Life", 5,6,costruisciGrigliaFronte(), costruisciGrigliaRetro());
    }

    public static Gravitas get(){
        if (istanza==null){
            istanza= new Gravitas();
        }
        return istanza;
    }

}

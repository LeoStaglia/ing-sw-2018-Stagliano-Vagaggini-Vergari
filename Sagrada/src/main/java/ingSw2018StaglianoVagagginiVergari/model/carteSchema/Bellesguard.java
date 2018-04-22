package ingSw2018StaglianoVagagginiVergari.model.carteSchema;

import ingSw2018StaglianoVagagginiVergari.model.Constraint;

import java.io.Serializable;
import java.util.ArrayList;

public class Bellesguard extends CartaSchema implements Serializable{
    private static Bellesguard istanza;
    private static Constraint[][] costruisciGrigliaFronte(){
        ArrayList<Constraint> constraints = new ArrayList<Constraint>();
        constraints.add(Constraint.BLU);
        constraints.add(Constraint.SEI);
        constraints.add(Constraint.NONE);
        constraints.add(Constraint.NONE);
        constraints.add(Constraint.GIALLO);

        constraints.add(Constraint.NONE);
        constraints.add(Constraint.TRE);
        constraints.add(Constraint.BLU);
        constraints.add(Constraint.NONE);
        constraints.add(Constraint.NONE);

        constraints.add(Constraint.NONE);
        constraints.add(Constraint.CINQUE);
        constraints.add(Constraint.SEI);
        constraints.add(Constraint.DUE);
        constraints.add(Constraint.NONE);

        constraints.add(Constraint.NONE);
        constraints.add(Constraint.QUATTRO);
        constraints.add(Constraint.NONE);
        constraints.add(Constraint.UNO);
        constraints.add(Constraint.VERDE);
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
        constraints.add(Constraint.NONE);
        constraints.add(Constraint.NONE);
        constraints.add(Constraint.SEI);
        constraints.add(Constraint.NONE);
        constraints.add(Constraint.NONE);

        constraints.add(Constraint.NONE);
        constraints.add(Constraint.CINQUE);
        constraints.add(Constraint.BLU);
        constraints.add(Constraint.QUATTRO);
        constraints.add(Constraint.NONE);

        constraints.add(Constraint.TRE);
        constraints.add(Constraint.VERDE);
        constraints.add(Constraint.GIALLO);
        constraints.add(Constraint.VIOLA);
        constraints.add(Constraint.DUE);

        constraints.add(Constraint.UNO);
        constraints.add(Constraint.QUATTRO);
        constraints.add(Constraint.ROSSO);
        constraints.add(Constraint.CINQUE);
        constraints.add(Constraint.TRE);
        Constraint[][] risultato = new Constraint[4][5];
        for (int i=0; i<4; i++){
            for (int j=0; j<5; j++){
                risultato[i][j]= constraints.remove(0);
            }
        }
        return risultato;

    }

    private Bellesguard(){
        super("Bellesguard", "Batllo", 3,5,costruisciGrigliaFronte(), costruisciGrigliaRetro());
    }

    public static Bellesguard get(){
        if (istanza==null){
            istanza= new Bellesguard();
        }
        return istanza;
    }

}

package ingSw2018StaglianoVagagginiVergari.model.carteSchema;

import ingSw2018StaglianoVagagginiVergari.model.Constraint;

import java.io.Serializable;
import java.util.ArrayList;

public class LuxAstram extends CartaSchema implements Serializable{
    private static LuxAstram istanza;
    private static Constraint[][] costruisciGrigliaFronte(){
        ArrayList<Constraint> constraints = new ArrayList<Constraint>();
        constraints.add(Constraint.NONE);
        constraints.add(Constraint.UNO);
        constraints.add(Constraint.VERDE);
        constraints.add(Constraint.VIOLA);
        constraints.add(Constraint.QUATTRO);
        constraints.add(Constraint.SEI);
        constraints.add(Constraint.VIOLA);
        constraints.add(Constraint.DUE);
        constraints.add(Constraint.CINQUE);
        constraints.add(Constraint.VERDE);
        constraints.add(Constraint.UNO);
        constraints.add(Constraint.VERDE);
        constraints.add(Constraint.CINQUE);
        constraints.add(Constraint.TRE);
        constraints.add(Constraint.VIOLA);
        constraints.add(Constraint.NONE);
        constraints.add(Constraint.NONE);
        constraints.add(Constraint.NONE);
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
        constraints.add(Constraint.NONE);
        constraints.add(Constraint.NONE);
        constraints.add(Constraint.UNO);
        constraints.add(Constraint.NONE);
        constraints.add(Constraint.NONE);
        constraints.add(Constraint.UNO);
        constraints.add(Constraint.VERDE);
        constraints.add(Constraint.TRE);
        constraints.add(Constraint.BLU);
        constraints.add(Constraint.DUE);

        constraints.add(Constraint.BLU);
        constraints.add(Constraint.CINQUE);
        constraints.add(Constraint.QUATTRO);
        constraints.add(Constraint.SEI);
        constraints.add(Constraint.VERDE);
        constraints.add(Constraint.NONE);
        constraints.add(Constraint.BLU);
        constraints.add(Constraint.CINQUE);
        constraints.add(Constraint.VERDE);
        constraints.add(Constraint.NONE);
        Constraint[][] risultato = new Constraint[4][5];
        for (int i=0; i<4; i++){
            for (int j=0; j<5; j++){
                risultato[i][j]= constraints.remove(0);
            }
        }
        return risultato;

    }

    private LuxAstram(){
        super("Lux Astram", "Lux Mundi", 5,6,costruisciGrigliaFronte(), costruisciGrigliaRetro());
    }

    public static LuxAstram get(){
        if (istanza==null){
            istanza= new LuxAstram();
        }
        return istanza;
    }

}

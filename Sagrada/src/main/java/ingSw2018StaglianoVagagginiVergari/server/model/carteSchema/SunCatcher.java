package ingSw2018StaglianoVagagginiVergari.server.model.carteSchema;

import ingSw2018StaglianoVagagginiVergari.server.model.Constraint;

import java.io.Serializable;
import java.util.ArrayList;

public class SunCatcher extends CartaSchema implements Serializable{
    private static SunCatcher istanza;
    private static Constraint[][] costruisciGrigliaFronte(){
        ArrayList<Constraint> constraints = new ArrayList<Constraint>();
        constraints.add(Constraint.NONE);
        constraints.add(Constraint.BLU);
        constraints.add(Constraint.DUE);
        constraints.add(Constraint.NONE);
        constraints.add(Constraint.GIALLO);

        constraints.add(Constraint.NONE);
        constraints.add(Constraint.QUATTRO);
        constraints.add(Constraint.NONE);
        constraints.add(Constraint.ROSSO);
        constraints.add(Constraint.NONE);

        constraints.add(Constraint.NONE);
        constraints.add(Constraint.NONE);
        constraints.add(Constraint.CINQUE);
        constraints.add(Constraint.GIALLO);
        constraints.add(Constraint.NONE);

        constraints.add(Constraint.VERDE);
        constraints.add(Constraint.TRE);
        constraints.add(Constraint.NONE);
        constraints.add(Constraint.NONE);
        constraints.add(Constraint.VIOLA);
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
        constraints.add(Constraint.VIOLA);
        constraints.add(Constraint.NONE);
        constraints.add(Constraint.NONE);
        constraints.add(Constraint.CINQUE);

        constraints.add(Constraint.CINQUE);
        constraints.add(Constraint.NONE);
        constraints.add(Constraint.VIOLA);
        constraints.add(Constraint.NONE);
        constraints.add(Constraint.NONE);

        constraints.add(Constraint.ROSSO);
        constraints.add(Constraint.SEI);
        constraints.add(Constraint.NONE);
        constraints.add(Constraint.VIOLA);
        constraints.add(Constraint.NONE);

        constraints.add(Constraint.GIALLO);
        constraints.add(Constraint.ROSSO);
        constraints.add(Constraint.CINQUE);
        constraints.add(Constraint.QUATTRO);
        constraints.add(Constraint.TRE);
        Constraint[][] risultato = new Constraint[4][5];
        for (int i=0; i<4; i++){
            for (int j=0; j<5; j++){
                risultato[i][j]= constraints.remove(0);
            }
        }
        return risultato;

    }

    private SunCatcher(){
        super("Sun Catcher", "Shadow Thief", 3,5,costruisciGrigliaFronte(), costruisciGrigliaRetro());
    }

    public static SunCatcher get(){
        if (istanza==null){
            istanza= new SunCatcher();
        }
        return istanza;
    }

}

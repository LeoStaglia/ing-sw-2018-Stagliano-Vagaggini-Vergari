package ingSw2018StaglianoVagagginiVergari.server.model.carteSchema;

import ingSw2018StaglianoVagagginiVergari.server.model.Constraint;

import java.io.Serializable;
import java.util.ArrayList;

public class ViaLux extends CartaSchema implements Serializable{
    private static ViaLux istanza;
    private static Constraint[][] costruisciGrigliaFronte(){
        ArrayList<Constraint> constraints = new ArrayList<Constraint>();
        constraints.add(Constraint.GIALLO);
        constraints.add(Constraint.NONE);
        constraints.add(Constraint.SEI);
        constraints.add(Constraint.NONE);
        constraints.add(Constraint.NONE);

        constraints.add(Constraint.NONE);
        constraints.add(Constraint.UNO);
        constraints.add(Constraint.CINQUE);
        constraints.add(Constraint.NONE);
        constraints.add(Constraint.DUE);

        constraints.add(Constraint.TRE);
        constraints.add(Constraint.GIALLO);
        constraints.add(Constraint.ROSSO);
        constraints.add(Constraint.VIOLA);
        constraints.add(Constraint.NONE);

        constraints.add(Constraint.NONE);
        constraints.add(Constraint.NONE);
        constraints.add(Constraint.QUATTRO);
        constraints.add(Constraint.TRE);
        constraints.add(Constraint.ROSSO);
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
        constraints.add(Constraint.UNO);
        constraints.add(Constraint.ROSSO);
        constraints.add(Constraint.TRE);
        constraints.add(Constraint.NONE);
        constraints.add(Constraint.SEI);

        constraints.add(Constraint.CINQUE);
        constraints.add(Constraint.QUATTRO);
        constraints.add(Constraint.ROSSO);
        constraints.add(Constraint.DUE);
        constraints.add(Constraint.NONE);

        constraints.add(Constraint.NONE);
        constraints.add(Constraint.NONE);
        constraints.add(Constraint.CINQUE);
        constraints.add(Constraint.ROSSO);
        constraints.add(Constraint.UNO);

        constraints.add(Constraint.NONE);
        constraints.add(Constraint.NONE);
        constraints.add(Constraint.NONE);
        constraints.add(Constraint.TRE);
        constraints.add(Constraint.ROSSO);
        Constraint[][] risultato = new Constraint[4][5];
        for (int i=0; i<4; i++){
            for (int j=0; j<5; j++){
                risultato[i][j]= constraints.remove(0);
            }
        }
        return risultato;

    }

    private ViaLux(){
        super("Via Lux", "Industria", 4,5,costruisciGrigliaFronte(), costruisciGrigliaRetro());
    }

    public static ViaLux get(){
        if (istanza==null){
            istanza= new ViaLux();
        }
        return istanza;
    }

}

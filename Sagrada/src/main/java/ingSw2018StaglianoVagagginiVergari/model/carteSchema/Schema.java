package ingSw2018StaglianoVagagginiVergari.model.carteSchema;

import ingSw2018StaglianoVagagginiVergari.model.Constraint;

public interface Schema {
    public void scegliFaccia(boolean fronteScelto);
    public Constraint getRestrizione(int i, int j);
    public int getDifficoltaFronte();
    public int getDifficoltaRetro();



}

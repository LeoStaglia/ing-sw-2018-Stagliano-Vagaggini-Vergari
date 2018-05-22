package ingSw2018StaglianoVagagginiVergari.server.model.carteSchema;

import ingSw2018StaglianoVagagginiVergari.server.model.Constraint;

public interface Schema {
    public void scegliFaccia(boolean fronteScelto);
    public Constraint getRestrizione(int i, int j);
    public int getDifficoltaFronte();
    public int getDifficoltaRetro();
    public String[][] stringRepresentation(Boolean fronte);


}

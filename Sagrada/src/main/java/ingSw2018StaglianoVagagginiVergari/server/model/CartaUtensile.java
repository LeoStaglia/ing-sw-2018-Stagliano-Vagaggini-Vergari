package ingSw2018StaglianoVagagginiVergari.server.model;

public interface CartaUtensile {

    public void usaEffettoCarta(Partita p);
    public int getCosto();
    public int getId();
    public String getDescrizione();
    public String getNome();
}

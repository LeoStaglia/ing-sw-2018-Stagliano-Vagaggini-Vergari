package ingSw2018StaglianoVagagginiVergari.server.model;

import java.rmi.RemoteException;

public interface CartaUtensile {

    public void usaEffettoCarta(Partita p) throws RemoteException;
    public int getCosto();
    public int getId();
    public String getDescrizione();
    public String getNome();
}

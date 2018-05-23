package ingSw2018StaglianoVagagginiVergari.common;

import Eccezioni.FullGameException;
import java.rmi.Remote;
import java.rmi.RemoteException;

public interface RemoteController extends Remote{

    public void partecipaPartita(GameObserver view) throws InterruptedException, RemoteException, FullGameException;
    public void abbandonaPartita(GameObserver view) throws RemoteException;
    public void scegliSchema(GameObserver view, String idUser,boolean carta1, boolean fronte)throws RemoteException;

}

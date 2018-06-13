package ingSw2018StaglianoVagagginiVergari.common;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface RemoteMultiController extends Remote {
    public RemoteController AssegnaController() throws RemoteException;
    public RemoteController CercaController(String username) throws RemoteException;
}

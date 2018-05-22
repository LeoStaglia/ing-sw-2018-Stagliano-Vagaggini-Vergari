package ingSw2018StaglianoVagagginiVergari.common;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface GameObserver extends Remote {
    public void notifyUser(String id, String[][] schemaFronte, String[][] schemaRetro, String obiettivoPrivato) throws RemoteException;
    public void notifyScheme(boolean fronteScelto);
}

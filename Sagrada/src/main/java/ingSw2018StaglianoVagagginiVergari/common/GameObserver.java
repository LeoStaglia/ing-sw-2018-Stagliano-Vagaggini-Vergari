package ingSw2018StaglianoVagagginiVergari.common;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface GameObserver extends Remote {
    public void notifyUser(String id, String[][] schemaFronte1, String[][] schemaRetro1 ,String[][] schemaFronte2,String[][] schemaRetro2 ,String obiettivoPrivato) throws RemoteException;
    public void notifyScheme(boolean carta1, boolean fronteScelto) throws RemoteException;
}

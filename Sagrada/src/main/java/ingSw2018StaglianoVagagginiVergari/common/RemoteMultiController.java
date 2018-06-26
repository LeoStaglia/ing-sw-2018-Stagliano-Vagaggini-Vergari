package ingSw2018StaglianoVagagginiVergari.common;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface RemoteMultiController extends Remote {
    /** method to assign an available controller (game not started)*/
    public RemoteController AssegnaController() throws RemoteException;
    /** method to find a controller which control a game with a given user registered
     * @param username the username to find */
    public RemoteController CercaController(String username) throws RemoteException;
}

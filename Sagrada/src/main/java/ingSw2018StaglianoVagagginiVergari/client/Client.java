package ingSw2018StaglianoVagagginiVergari.client;

import ingSw2018StaglianoVagagginiVergari.common.RemoteController;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class Client {
    public static void main(String[] args) throws RemoteException, NotBoundException, InterruptedException {
        Registry registry = LocateRegistry.createRegistry(7500);
        RemoteController controller = (RemoteController)registry.lookup("controller");
        new View(controller).run();

    }
}
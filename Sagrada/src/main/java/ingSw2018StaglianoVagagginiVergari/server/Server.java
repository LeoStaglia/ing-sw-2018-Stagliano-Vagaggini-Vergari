package ingSw2018StaglianoVagagginiVergari.server;

import ingSw2018StaglianoVagagginiVergari.server.Controller.Controller;

import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class Server {
    public static void main(String[] args) throws RemoteException, AlreadyBoundException {
        Controller controller = new Controller();
        Registry registry = LocateRegistry.createRegistry(7501);
        registry.bind("controller", controller);
        System.out.println("Server rmi is started!");
    }
}

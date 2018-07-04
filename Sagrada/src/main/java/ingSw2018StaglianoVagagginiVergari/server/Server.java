package ingSw2018StaglianoVagagginiVergari.server;

import ingSw2018StaglianoVagagginiVergari.server.Controller.MultiController;

import java.io.IOException;
import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class Server {
    public static void main(String[] args) throws IOException, AlreadyBoundException {
        int turnTimer = 0;
        int joinTimer=0;
        int schemeTimer=0;


        if(args[0]!=null) {
            turnTimer = Integer.parseInt(args[0]);
        }
        if(args[1]!=null){
            joinTimer=Integer.parseInt((args[1]));
        }
        if(args[2]!=null){
            schemeTimer=Integer.parseInt(args[2]);
        }
        MultiController controller = new MultiController();
        controller.setTimerTurn(turnTimer);
        controller.setJoinTimer(joinTimer);
        controller.setSchemeTimer(schemeTimer);
        Registry registry = LocateRegistry.createRegistry(7501);
        registry.bind("controller", controller);
        UnbindRegistyThread t=new UnbindRegistyThread(Thread.currentThread(),registry);
        t.start();
        System.out.println("Server rmi is started!");
        SagradaServerPool socketServer = new SagradaServerPool(1700, controller);
        socketServer.run();
    }
}

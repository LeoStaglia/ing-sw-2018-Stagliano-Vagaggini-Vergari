package ingSw2018StaglianoVagagginiVergari.client;

import Eccezioni.MossaIllegaleException;
import ingSw2018StaglianoVagagginiVergari.common.RemoteController;

import java.io.IOException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) throws RemoteException, NotBoundException, InterruptedException,IOException, MossaIllegaleException {

        Scanner stdin=new Scanner(System.in);
        int cmd;

        System.out.println("Premi (1) per Socket,(2) per RMI:");
        cmd=stdin.nextInt();

        if(cmd==1) {

            ClientSocket clientSocket = new ClientSocket("127.0.0.1", 1700);
            clientSocket.init();
            ProxyClient controller = new ProxyClient(clientSocket);
            controller.run();
            clientSocket.close();
        }else {

            Registry registry = LocateRegistry.getRegistry(7501);
            RemoteController controller = (RemoteController) registry.lookup("controller");
            new View(controller).run();
        }

    }
}

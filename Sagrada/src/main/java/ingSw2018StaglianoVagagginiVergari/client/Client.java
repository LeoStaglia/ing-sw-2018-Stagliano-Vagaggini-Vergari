package ingSw2018StaglianoVagagginiVergari.client;

import Eccezioni.MossaIllegaleException;
import ingSw2018StaglianoVagagginiVergari.common.RemoteController;
import ingSw2018StaglianoVagagginiVergari.common.RemoteMultiController;
import ingSw2018StaglianoVagagginiVergari.server.Controller.MultiController;

import java.io.IOException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) throws RemoteException, NotBoundException, InterruptedException,IOException, MossaIllegaleException {

        Scanner stdin = new Scanner(System.in);
        int cmd = 0;
        while (cmd != 1 && cmd != 2){
            System.out.println("Premi (1) per Socket,(2) per RMI:");
            try {
                cmd = stdin.nextInt();
            } catch (InputMismatchException e) {
                cmd = 0;
                stdin.nextLine(); //flush the buffer
            }
        }


        if(cmd==1) {

            ClientSocket clientSocket = new ClientSocket(args[2],Integer.parseInt(args[0]));
            clientSocket.init();
            ProxyClient controller = new ProxyClient(clientSocket);

            View view=new View();
            view.setMultiController(controller);
            controller.setView(view);
            view.run();
            clientSocket.close();
        }if (cmd==2) {

            Registry registry = LocateRegistry.getRegistry(args[1]);
            RemoteMultiController multiController = (RemoteMultiController) registry.lookup("controller");
            View view= new View();
            view.setMultiController(multiController);
            view.run();
        }

    }
}

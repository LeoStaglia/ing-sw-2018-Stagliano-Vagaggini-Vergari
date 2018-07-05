package ingSw2018StaglianoVagagginiVergari.server;

import ingSw2018StaglianoVagagginiVergari.server.Controller.MultiController;

import java.io.IOException;
import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Server {
    public static void main(String[] args) throws IOException, AlreadyBoundException {
        int turnTimer = 0;
        int joinTimer=0;
        int schemeTimer=0;

        Scanner inp= new Scanner(System.in);
        int def = 0;
        int portas=1700;
        int portar=7501;
        String mioIp="127.0.0.1";

        while (true) {
            System.out.println("Inserisci 0 per usare config di default, un altro valore per settare parametri di rete");
            try{
                def = inp.nextInt();
            }catch (InputMismatchException e){
                System.out.println("scelta non valida");
                inp.nextLine();
            }
            break;

        }
        if(def==0);
        else {
            while (true){
            System.out.println("Inserisci porta socket");
            try {
                portas = inp.nextInt();
            }catch (InputMismatchException e){
                System.out.println("porta non valida");
                inp.nextLine();
            }
                break;
            }

            while (true){
                System.out.println("Inserisci porta rmi");
            try {
                portar = inp.nextInt();

            }catch (InputMismatchException e){
                System.out.println("porta non valida");
                inp.nextLine();
            }
                break;

            }

            System.out.println("Inserisci ip del server");
            mioIp=inp.next();

            while (true) {
                System.out.println("Inserisci timer del turno (millisecondi)");
               try{
                   turnTimer = inp.nextInt();
               }catch (InputMismatchException e){
                   System.out.println("scelta non valida");
               }
               break;
            }
            while (true) {
                System.out.println("Inserisci timer di attesa registrazione (millisecondi)");
               try {
                   joinTimer = inp.nextInt();
               }catch (InputMismatchException e){
                   System.out.println("scelta non valida");
               }
                break;
            }
            while (true) {
                System.out.println("Inserisci timer degli schemi (millisecondi)");
                try{
                    schemeTimer = inp.nextInt();
                }catch (InputMismatchException e){
                    System.out.println("scelta non valida");
                }
                break;
            }
        }

        System.setProperty("java.rmi.server.hostname",mioIp);

        MultiController controller = new MultiController();
        controller.setTimerTurn(turnTimer);
        controller.setJoinTimer(joinTimer);
        controller.setSchemeTimer(schemeTimer);
        Registry registry = LocateRegistry.createRegistry(portar);
        registry.bind("controller", controller);
        UnbindRegistyThread t=new UnbindRegistyThread(Thread.currentThread(),registry);
        t.start();
        System.out.println("Server rmi is started!");
        SagradaServerPool socketServer = new SagradaServerPool(portas, controller);
        socketServer.run();
    }
}

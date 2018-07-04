package ingSw2018StaglianoVagagginiVergari.client;

import Eccezioni.MossaIllegaleException;
import ingSw2018StaglianoVagagginiVergari.client.GUI.SagradaGUI;

import java.io.IOException;
import java.rmi.NotBoundException;
import java.util.InputMismatchException;
import java.util.Scanner;

public class LaunchClient {
    public static void main(String[] args) throws InterruptedException, IOException, MossaIllegaleException, NotBoundException {
        Scanner inp= new Scanner(System.in);
        int def;
        String[] parametri=new String[3];
        String portas="1700";
        String portar="7501";
        String ip="127.0.0.1";

        while (true) {
            System.out.println("Inserisci 0 per usare config di default, un altro valore per settare parametri di rete");
            try{
                def = inp.nextInt();
                break;
            }catch (InputMismatchException e){
                System.out.println("scelta non valida");
                inp.nextLine();
            }

        }
        if(def==0){}
        else {
            System.out.println("Inserisci porta socket");
            portas = inp.next();
            System.out.println("Inserisci porta rmi");
            portar = inp.next();
            System.out.println("Inserisci ip");
            ip = inp.next();
            parametri[0]=portas;
            parametri[1]=portar;
            parametri[2]=ip;
        }

        int command=0;
        Scanner input = new Scanner(System.in);

        while (command !=1 && command !=2) {
            System.out.println("Premi 1 per giocare con GUI, 2 per giocare con CLI");
            while (true) {
               try{ command = input.nextInt();
                        break;
               } catch (InputMismatchException e){
                   System.out.println("input non valido");
                   input.nextLine();
               }
            }
        }

        switch (command) {
            case 1: {
                SagradaGUI.main(parametri);
                break;
            }
            case 2:{
                Client.main(parametri);
                break;
            }

            default: return;
        }

    }
}

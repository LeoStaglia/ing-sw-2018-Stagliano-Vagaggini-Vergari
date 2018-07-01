package ingSw2018StaglianoVagagginiVergari.client;

import Eccezioni.MossaIllegaleException;
import ingSw2018StaglianoVagagginiVergari.client.GUI.SagradaGUI;

import java.io.IOException;
import java.rmi.NotBoundException;
import java.util.Scanner;

public class LaunchClient {
    public static void main(String[] args) throws InterruptedException, IOException, MossaIllegaleException, NotBoundException {
        int command=0;
        Scanner input = new Scanner(System.in);

        while (command !=1 && command !=2) {
            System.out.println("Premi 1 per giocare con GUI, 2 per giocare con CLI");
            command = input.nextInt();
        }

        switch (command) {
            case 1: {
                SagradaGUI.main(args);
                break;
            }
            case 2:{
                Client.main(args);
                break;
            }

            default: return;
        }

    }
}

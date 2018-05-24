package ingSw2018StaglianoVagagginiVergari.client;

import Eccezioni.FullGameException;
import ingSw2018StaglianoVagagginiVergari.common.GameObserver;
import ingSw2018StaglianoVagagginiVergari.common.RemoteController;

import java.io.IOException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Scanner;

public class View extends UnicastRemoteObject implements GameObserver, Remote {

    private String id;
    private RemoteController controller;
    private Scanner command;
    private ViewStatus status;
    private String[][] schemaFronte1;
    private String[][] schemaRetro1;
    private String[][] schemaFronte2;
    private String[][] schemaRetro2;
    private String obiettivoPrivato;
    private boolean updateView;
    private boolean fronteScelto;
    private boolean carta1;


    public View(RemoteController controller) throws RemoteException {
        this.controller=controller;
        setStatus(ViewStatus.Preparazione);
        command = new Scanner(System.in);
        updateView=false;
    }
    public void run() throws IOException, InterruptedException {
        int cmd=0;
        boolean carta1=false;
        while(cmd!=1 && cmd!=2) {
            System.out.println("(1) Per partecipare alla partita (2) per uscire");
            cmd = command.nextInt();
        }
            if (cmd == 2) {
                System.exit(0);
            } else if (cmd == 1) {
                try {
                    controller.partecipaPartita(this);
                } catch (FullGameException e) {
                    System.out.println(e.getMessage());
                    System.exit(0);
                }
                System.out.println("Preparazione partita... Attendi. (0) per uscire");
                while (status==ViewStatus.Preparazione){
                    if (System.in.available()>0){
                        if (command.nextInt()==0){
                            controller.abbandonaPartita(this);
                            System.exit(0);
                        }
                    }
                }
                System.out.println(status);
                while(!updateView){
                    System.out.print("");
                }
                updateView=false;
                if (getStatus()==ViewStatus.SelezioneSchema){
                    System.out.println("CARTA 1: Fronte");
                    stampaSchema(true, true);
                    System.out.println("CARTA 1: Retro");
                    stampaSchema(true, false);
                    System.out.println("CARTA 2: Fronte");
                    stampaSchema(false, true);
                    System.out.println("CARTA 2: Retro");
                    stampaSchema(false, false);
                    cmd=0;
                    while(cmd!=1 && cmd!=2) {
                        System.out.println("(1) per scegliere la prima carta (2) per scegliere la seconda carta");
                        cmd = command.nextInt();
                    }
                    if (cmd==1){
                        carta1=true;
                    }else{
                        carta1 = false;
                    }
                    cmd=0;
                    while(cmd!=1 && cmd!=2) {
                        System.out.println("(1) per scegliere il fronte (2) per scegliere il retro");
                        cmd = command.nextInt();
                    }
                    if (cmd==1){
                        controller.scegliSchema(this, id, carta1, true);
                    }else{
                        controller.scegliSchema(this, id, carta1, false);
                    }
                    while(!updateView){

                    }
                    updateView=false;
                    System.out.println("Lo schema che hai scelto è il seguente:");
                    stampaSchema(carta1, fronteScelto);

                }
            }

    }

    @Override
    public synchronized void notifyUser(String id, String[][] schemaFronte1, String[][] schemaRetro1, String[][] schemaFronte2, String[][] schemaRetro2, String obiettivoPrivato) throws RemoteException {
        this.id = id;
        this.setStatus(ViewStatus.SelezioneSchema);
        this.schemaFronte1=schemaFronte1;
        this.schemaRetro1=schemaRetro1;
        this.schemaFronte2=schemaFronte2;
        this.schemaRetro2=schemaRetro2;
        this.obiettivoPrivato=obiettivoPrivato;
        updateView=true;
        System.out.println("NOTIFICA");
    }

    public synchronized void setStatus(ViewStatus status){
        this.status=status;
    }

    public synchronized ViewStatus getStatus(){
        return this.status;
    }


    private void stampaSchema(boolean carta1,boolean fronte){
        for (int i=0; i<4; i++){
            System.out.println("");
            for(int j = 0; j<5; j++){
                if (carta1) {
                    if (fronte) {
                        System.out.print(schemaFronte1[i][j]);
                    } else {
                        System.out.print(schemaRetro1[i][j]);
                    }
                    if (j != 4) {
                        System.out.print(" ");
                    }
                }else{
                    if (fronte) {
                        System.out.print(schemaFronte2[i][j]);
                    } else {
                        System.out.print(schemaRetro2[i][j]);
                    }
                    if (j != 4) {
                        System.out.print(" ");
                    }
                }
            }
        }
        System.out.println("");
    }

    @Override
    public synchronized void notifyScheme(boolean carta1, boolean fronteScelto) {
        updateView=true;
        this.fronteScelto=fronteScelto;
        this.carta1=carta1;
    }
}

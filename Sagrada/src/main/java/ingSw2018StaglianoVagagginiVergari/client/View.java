package ingSw2018StaglianoVagagginiVergari.client;

import Eccezioni.FullGameException;
import ingSw2018StaglianoVagagginiVergari.common.GameObserver;
import ingSw2018StaglianoVagagginiVergari.common.RemoteController;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Scanner;

public class View extends UnicastRemoteObject implements GameObserver, Remote {

    private String id;
    private RemoteController controller;
    private Scanner command;
    private ViewStatus status;
    private String[][] schemaFronte;
    private String[][] schemaRetro;
    private String obiettivoPrivato;
    private boolean updateView;
    private boolean fronteScelto;


    public View(RemoteController controller) throws RemoteException {
        this.controller=controller;
        status = ViewStatus.Preparazione;
        command = new Scanner(System.in);
        updateView=false;
    }
    public void run() throws RemoteException, InterruptedException {
        int cmd=0;
        while(cmd!=1 || cmd!=2) {
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
                }
                synchronized (this) {
                    System.out.println("Preparazione partita... Attendi. (0) per uscire");
                    while (status == ViewStatus.Preparazione){
                        cmd = command.nextInt();
                        if (cmd==0){
                            controller.abbandonaPartita(this);
                            System.exit(0);
                        }
                    }
                }
                if (getStatus()==ViewStatus.SelezioneSchema){
                    System.out.println("(1) per fronte (2) per retro");
                    int comando = command.nextInt();
                    while(comando!=2 || comando!=1){
                        comando = command.nextInt();
                    }
                    if (comando==1){
                        controller.scegliSchema(this, id, true);
                    }else if (comando==2){
                        controller.scegliSchema(this, id, false);
                    }
                    while(!updateView){

                    }
                    updateView=false;
                    System.out.println("Hai scelto lo schema");
                    if (fronteScelto){
                        stampaSchema(true);
                    }else{
                        stampaSchema(false);
                    }
                }
            }

    }

    @Override
    public synchronized void notifyUser(String id, String[][] schemaFronte, String[][] retro, String obiettivoPrivato) throws RemoteException {
        this.id = id;
        this.setStatus(ViewStatus.SelezioneSchema);
        this.schemaFronte=schemaFronte;
        this.schemaRetro=schemaRetro;
        this.obiettivoPrivato=obiettivoPrivato;

    }

    public synchronized void setStatus(ViewStatus status){
        this.status=status;
    }

    public synchronized ViewStatus getStatus(){
        return this.status;
    }


    private void stampaSchema(boolean fronte){
        for (int i=0; i<4; i++){
            System.out.println("");
            for(int j = 0; j<5; j++){
                if (fronte){
                    System.out.print(schemaFronte[i][j]);
                }else{
                    System.out.println(schemaRetro[i][j]);
                }
                if (j!=4){
                    System.out.print(" ");
                }
            }
        }
        System.out.println("");
    }

    @Override
    public synchronized void notifyScheme(boolean fronteScelto) {
        updateView=true;
        this.fronteScelto=fronteScelto;
    }
}

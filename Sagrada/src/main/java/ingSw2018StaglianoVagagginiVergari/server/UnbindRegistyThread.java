package ingSw2018StaglianoVagagginiVergari.server;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.Registry;

public class UnbindRegistyThread extends Thread {

    private Thread father;
    private Registry registro;

    public UnbindRegistyThread(Thread father,Registry registry){
        this.father=father;
        this.registro=registry;
    }

    @Override
    public void run() {
        while(father.isAlive()){
            System.out.print("");
        }
        try {
            registro.unbind("controller");
        } catch (RemoteException e) {
            e.printStackTrace();
        } catch (NotBoundException e) {
            e.printStackTrace();
        }
    }
}

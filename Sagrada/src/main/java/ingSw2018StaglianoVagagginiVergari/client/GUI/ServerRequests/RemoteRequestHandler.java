package ingSw2018StaglianoVagagginiVergari.client.GUI.ServerRequests;

import ingSw2018StaglianoVagagginiVergari.client.ClientSocket;
import ingSw2018StaglianoVagagginiVergari.client.GUI.AlertPopup;
import ingSw2018StaglianoVagagginiVergari.client.GUI.DataGameObserver;
import ingSw2018StaglianoVagagginiVergari.client.GUI.GameScene;
import ingSw2018StaglianoVagagginiVergari.client.GUI.TransitionHandler;
import ingSw2018StaglianoVagagginiVergari.client.ProxyClient;
import ingSw2018StaglianoVagagginiVergari.common.RemoteController;
import ingSw2018StaglianoVagagginiVergari.common.RemoteMultiController;
import ingSw2018StaglianoVagagginiVergari.server.Controller.MultiController;
import javafx.application.Platform;

import javax.xml.crypto.Data;
import java.io.IOException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;

public class RemoteRequestHandler {

    private RemoteMultiController multiController;
    private RemoteController controller;
    private DataGameObserver dataGameObserver;
    private ClientSocket clientSocket;
    private boolean RMI;

    private String ip;
    private int portar;
    private int portas;
    private String mioIp;


    public RemoteRequestHandler(DataGameObserver dataGameObserver){
        this.dataGameObserver = dataGameObserver;
    }



    public void partecipaPartita(String username){
        try {
            controller = multiController.CercaController(username);
            if (controller == null) {
                controller = multiController.AssegnaController();
                new PartecipaPartitaRequest(controller, username, dataGameObserver).start();
            } else {
                Platform.runLater(()->TransitionHandler.toNewGameScene());
                new AlertPopup().display("Attenzione", "Username non valido");
            }
        }catch (RemoteException e) {
            new AlertPopup().display("Attenzione", "Problemi nella connessione RMI");
        }

    }
    public void scegliSchema(boolean carta1, boolean fronte){
        new SceltaSchemaRequest(controller, carta1, fronte, dataGameObserver).start();
    }

    public void genericGameRequest(ArrayList<Integer> parametri, GameScene gameScene){
        new GenericGameRequest(dataGameObserver,controller, parametri,gameScene).start();
    }

    public void usoCartaUtensileRequest(ArrayList<Integer> parametri){
        new UtilizzoCartaUtensileRequest(dataGameObserver, controller, parametri).start();
    }

    public void setController(RemoteController controller) {
        this.controller = controller;
    }

    public void login(String username){
        new LoginRequest(multiController, username, dataGameObserver).start();
    }

    public void setRMI(boolean RMI) {
        this.RMI = RMI;
    }
    public void setController(){
        if (RMI) {
            if (multiController==null) {
                try {
                    System.setProperty("java.rmi.server.hostname",mioIp);
                    Registry registry = LocateRegistry.getRegistry(ip,portar);
                    multiController = (RemoteMultiController) registry.lookup("controller");
                } catch (RemoteException ex) {
                    new AlertPopup().display("Attenzione", "Problemi nella connessione RMI");
                } catch (NotBoundException ex) {
                    new AlertPopup().display("Attenzione", "Non è stato possibile contattare il server");
                }
            }
        }else{

            ClientSocket clientSocket = new ClientSocket( ip,portas);
            try {
                clientSocket.init();
                this.clientSocket=clientSocket;
                ProxyClient controller = new ProxyClient(clientSocket);
                this.multiController=controller;
                controller.setView(dataGameObserver);
            } catch (IOException e) {
                e.printStackTrace();
            }


        }

    }

    public boolean isRMI() {
        return RMI;
    }

    public ClientSocket getClientSocket() {
        return clientSocket;
    }

    public void closeSocketConnection(){
        try {
            clientSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public RemoteController getController() {
        return controller;
    }

    public DataGameObserver getDataGameObserver() {
        return dataGameObserver;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public void setPortar(int portar) {
        this.portar = portar;
    }

    public void setPortas(int portas) {
        this.portas = portas;
    }

    public void setMioIp(String mioIp) {
        this.mioIp = mioIp;
    }
}

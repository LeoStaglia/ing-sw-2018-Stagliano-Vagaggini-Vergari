package ingSw2018StaglianoVagagginiVergari.client.GUI.ServerRequests;

import ingSw2018StaglianoVagagginiVergari.client.ClientSocket;
import ingSw2018StaglianoVagagginiVergari.client.GUI.AlertPopup;
import ingSw2018StaglianoVagagginiVergari.client.GUI.DataGameObserver;
import ingSw2018StaglianoVagagginiVergari.client.GUI.GameScene;
import ingSw2018StaglianoVagagginiVergari.client.ProxyClient;
import ingSw2018StaglianoVagagginiVergari.common.RemoteController;
import ingSw2018StaglianoVagagginiVergari.common.RemoteMultiController;
import ingSw2018StaglianoVagagginiVergari.server.Controller.MultiController;

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

    public RemoteRequestHandler(DataGameObserver dataGameObserver){
        this.dataGameObserver = dataGameObserver;
    }

    public void partecipaPartita(String username){
        setController(username);
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

    public void setRMI(boolean RMI) {
        this.RMI = RMI;
    }
    private void setController(String username){
        if (RMI) {
            if (multiController==null) {
                try {
                    Registry registry = LocateRegistry.getRegistry(7501);
                    multiController = (RemoteMultiController) registry.lookup("controller");
                } catch (RemoteException ex) {
                    new AlertPopup().display("Attenzione", "Problemi nella connessione RMI");
                } catch (NotBoundException ex) {
                    new AlertPopup().display("Attenzione", "Non è stato possibile contattare il server");
                }
            }
        }else{

            ClientSocket clientSocket = new ClientSocket("127.0.0.1", 1700);
            try {
                clientSocket.init();
              //  this.clientSocket=clientSocket;
                ProxyClient controller = new ProxyClient(clientSocket);
                this.multiController=controller;
                controller.setView(dataGameObserver);
            } catch (IOException e) {
                e.printStackTrace();
            }


        }
        try {
            if (multiController!=null) {
                controller = multiController.CercaController(username);
                if (controller == null) {
                    controller = multiController.AssegnaController();
                    new PartecipaPartitaRequest(controller, username, dataGameObserver).start();
                } else {
                    new AlertPopup().display("Attenzione", "Username non valido");
                }
            }
        }catch (RemoteException e) {
            new AlertPopup().display("Attenzione", "Problemi nella connessione RMI");
        }

    }

    public ClientSocket getClientSocket() {
        return clientSocket;
    }

    public RemoteController getController() {
        return controller;
    }

    public DataGameObserver getDataGameObserver() {
        return dataGameObserver;
    }
}

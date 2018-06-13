package ingSw2018StaglianoVagagginiVergari.server.Controller;

import Eccezioni.FullGameException;
import ingSw2018StaglianoVagagginiVergari.common.GameObserver;

import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public class ClientHandler implements Runnable,GameObserver {
    private Socket socket;
    private final ObjectInputStream in;
    private final ObjectOutputStream out;
    private boolean stop;
    private Controller controller;
    private MultiController multiController;

    public ClientHandler(Socket s, MultiController controller) throws IOException {
        this.socket = s;
        this.out = new ObjectOutputStream(s.getOutputStream());
        this.in = new ObjectInputStream(s.getInputStream());
        this.multiController = controller;
    }

    private void printError(String message) {
        System.err.println(">>> ERROR@" + socket.getRemoteSocketAddress() + ": " + message);
    }

    @Override
    public void run() {
        try {
            do {
                ArrayList<Object> response = null;
                try {
                        response = (ArrayList<Object>) in.readObject();  //receive data from socket

                }catch(EOFException e){  //TODO mex
                    this.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                } catch (ClassNotFoundException e1) {
                    e1.printStackTrace();
                }
                if (response != null) {
                    try {
                        InvocaController(response);
                    } catch (InterruptedException e1) {
                        e1.printStackTrace();
                    } catch (RemoteException e1) {
                        e1.printStackTrace();
                    } catch (FullGameException e1) {
                        e1.printStackTrace();
                    }

                }
            } while (!stop);
        } catch (Exception e) {
            printError(e.getClass().getSimpleName() + " - " + e.getMessage());
        }

        close();
    }

    public void stop() {
        stop = true;
    }

    private void close() {
        stop = true;
        if (in != null) {
            try {
                in.close();
            } catch (IOException e) {
                printError("Errors in closing - " + e.getMessage());
            }
        }

        if (out != null) {
            try {
                out.close();
            } catch (IOException e) {
                printError("Errors in closing - " + e.getMessage());
            }
        }

        try {
            socket.close();
        } catch (IOException e) {
            printError("Errors in closing - " + e.getMessage());
        }
    }

    public ObjectInputStream getIn() {
        return in;
    }

    public ObjectOutputStream getOut() {
        return out;
    }

// --- Overridden methods


    @Override
    public void notifyUser(String id, String token, String[][] schemaFronte1, String[][] schemaRetro1, String[][] schemaFronte2, String[][] schemaRetro2, String obiettivoPrivato, Integer[] difficoltàCarteSchema, String[] nomeCarteSchema) throws RemoteException {
        ArrayList<Object> parametriInviati=new ArrayList<>();
        parametriInviati.add(id);
        parametriInviati.add(token);
        parametriInviati.add(schemaFronte1.clone());
        parametriInviati.add(schemaRetro1.clone());
        parametriInviati.add(schemaFronte2.clone());
        parametriInviati.add(schemaRetro2.clone());
        parametriInviati.add(obiettivoPrivato);
        parametriInviati.add(difficoltàCarteSchema.clone());
        parametriInviati.add(nomeCarteSchema.clone());
        parametriInviati.add("notifyUser");
        WriteOut(parametriInviati);

    }

    @Override
    public void notifyScheme(boolean carta1, boolean fronteScelto) throws RemoteException {
        ArrayList<Object> parametriInviati=new ArrayList<>();
        parametriInviati.add(carta1);
        parametriInviati.add(fronteScelto);
        parametriInviati.add("notifyScheme");
        WriteOut(parametriInviati);





    }

    @Override
    public void updateView(HashMap<String, String[][]> planceGiocatori, ArrayList<String> listaCartaUtensile, String giocatoreCorrente, int turno, int round,HashMap<String, Integer> segnaliniGiocatori, ArrayList<String> dadiRiserva, String dadoSelezionato, ArrayList<String> carteObiettivoPubblico, HashMap<String, String> obiettiviPrivati, ArrayList<String> tracciatoDelRound, HashSet<Integer> azioniGiocatore) throws RemoteException {
        ArrayList<Object> parametriInviati=new ArrayList<>();
        parametriInviati.add(planceGiocatori.clone());
        parametriInviati.add(listaCartaUtensile.clone());
        parametriInviati.add(giocatoreCorrente);
        parametriInviati.add(turno);
        parametriInviati.add(round);
        parametriInviati.add(dadiRiserva.clone());
        parametriInviati.add(dadoSelezionato);
        parametriInviati.add(carteObiettivoPubblico.clone());
        parametriInviati.add(obiettiviPrivati.clone());
        parametriInviati.add(tracciatoDelRound.clone());
        parametriInviati.add(azioniGiocatore.clone());
        parametriInviati.add(segnaliniGiocatori);
        parametriInviati.add("updateView");
        WriteOut(parametriInviati);

    }

    @Override
    public void updateViewPunteggio(HashMap<String, Integer> punteggi, String vincitore) throws RemoteException {
        ArrayList<Object> parametriInviati=new ArrayList<>();
        parametriInviati.add(punteggi.clone());
        parametriInviati.add(vincitore);
        parametriInviati.add("updateViewPunteggio");
        WriteOut(parametriInviati);

    }

    @Override
    public void setPiazzamentoScorretto(String giocatoreCorrente) throws RemoteException {
        ArrayList<Object> parametriInviati=new ArrayList<>();
        parametriInviati.add(giocatoreCorrente);
        parametriInviati.add("setPiazzamentoScorretto");
        WriteOut(parametriInviati);
    }

    @Override
    public void updateViewTool6Bool(boolean piazzabile) throws RemoteException {
        ArrayList<Object> parametriInviati=new ArrayList<>();
        parametriInviati.add(piazzabile);
        parametriInviati.add("updateViewTool6Bool");
        WriteOut(parametriInviati);
    }

    @Override
    public void updateViewTool6Die(String Dado) throws RemoteException {
        ArrayList<Object> parametriInviati=new ArrayList<>();
        parametriInviati.add(Dado);
        parametriInviati.add("updateViewTool6Die");
        WriteOut(parametriInviati);



    }

    @Override
    public void updateViewTool6piazzato(boolean piazzato) throws RemoteException {
        ArrayList<Object> parametriInviati=new ArrayList<>();
        parametriInviati.add(piazzato);
        parametriInviati.add("updateViewTool6piazzato");
        WriteOut(parametriInviati);
    }

    @Override
    public void updateUsername(boolean usernameOK) throws RemoteException {
        ArrayList<Object> parametriInviati=new ArrayList<>();
        parametriInviati.add(usernameOK);
        parametriInviati.add("updateUsername");
        WriteOut(parametriInviati);
    }

    @Override
    public void notifyExit() throws RemoteException {
        ArrayList<Object> parametriInviati=new ArrayList<>();
        parametriInviati.add("notifyExit");
        WriteOut(parametriInviati);
    }

    @Override
    public void ping() throws RemoteException {
        if (!socket.isClosed()) {
            return;
        } else {
            throw new RemoteException();
        }
    }

    @Override
    public void notifyUserExit(String username) throws RemoteException {
        ArrayList<Object> parametriInviati=new ArrayList<>();
        parametriInviati.add(username);
        parametriInviati.add("notifyUserExit");
        WriteOut(parametriInviati);

    }

    @Override
    public void updateViewTool11(String Dado) throws RemoteException {
        ArrayList<Object> parametriInviati=new ArrayList<>();
        parametriInviati.add(Dado);
        parametriInviati.add("updateViewTool11");
        WriteOut(parametriInviati);

    }

    @Override
    public void updateMessage(String message) throws RemoteException {
        ArrayList<Object> parametriInviati=new ArrayList<>();
        parametriInviati.add(message);
        parametriInviati.add("updateMessage");
        WriteOut(parametriInviati);

    }

    @Override
    public void updateViewTool4(boolean fase) throws RemoteException {
        ArrayList<Object> parametriInviati=new ArrayList<>();
        parametriInviati.add(fase);
        parametriInviati.add("updateViewTool4");
        WriteOut(parametriInviati);

    }

    @Override
    public void updateViewTool12(boolean fase) throws RemoteException {
        ArrayList<Object> parametriInviati=new ArrayList<>();
        parametriInviati.add(fase);
        parametriInviati.add("updateViewTool12");
        WriteOut(parametriInviati);

    }

    @Override
    public void updatePagamento() throws RemoteException {

    }

    public void sbloccoUtensile(){
        ArrayList<Object> parametriInviati=new ArrayList<>();

        parametriInviati.add("sbloccoUtensile");
        WriteOut(parametriInviati);
    }

    public void esitoUsername(int esito){
        ArrayList<Object> parametriInviati=new ArrayList<>();
        parametriInviati.add(esito);
        parametriInviati.add("username");
        WriteOut(parametriInviati);
    }

    @Override
    public void notifyTurnTimer() throws RemoteException {
        ArrayList<Object> parametriInviati=new ArrayList<>();
        parametriInviati.add("notifyTurnTimer");
        WriteOut(parametriInviati);
    }


    public void WriteOut(ArrayList<Object> parametriInviati) {
        try {
            out.writeObject(parametriInviati);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    public void InvocaController(ArrayList<Object> parametriRicevuti) throws InterruptedException, RemoteException, FullGameException {
        String comando;
        comando=(String) parametriRicevuti.remove(parametriRicevuti.size()-1);

        switch (comando){
            case "partecipaPartita":{

                String username=(String)parametriRicevuti.get(0);
                controller.partecipaPartita(this,username);
                break;
            }
            case "abbandonaPartita":{
                String username;
                username=(String)parametriRicevuti.get(0);
                controller.abbandonaPartita(this,username);

                break;

            }
            case "scegliSchema":{
                String idUser=(String) parametriRicevuti.get(0);
                boolean carta1=(boolean) parametriRicevuti.get(1);
                boolean fronte=(boolean) parametriRicevuti.get(2);
                controller.scegliSchema(this,idUser,carta1,fronte);

                break;

            }
            case "svolgimentoPartita":{
                ArrayList<Integer> parametri=(ArrayList<Integer>) parametriRicevuti.get(0);
                controller.svolgimentoPartita(this,parametri);
                if(parametri.get(0)==2 || parametri.get(0)==3){
                    sbloccoUtensile();
                }
                break;

            }
            case "usaCartaUtensile":{
                ArrayList<Integer> parametri=(ArrayList<Integer>) parametriRicevuti.get(0);
                controller.usaCartaUtensile(this,parametri);
                break;
            }
            case "login":{
                int log;
                String username=(String) parametriRicevuti.get(0);
                String token=(String) parametriRicevuti.get(1);
                log=controller.login(this,username,token);
                System.out.println("esito"+log);
                esitoUsername(log);
                break;
            }
            case "assegnaController":{
                controller=multiController.AssegnaController();
                sbloccoUtensile();
                break;
            }
            case"cercaController":{
                String username=(String) parametriRicevuti.get(0);
                controller=multiController.CercaController(username);
                if(controller==null){
                    esitoUsername(1);
                }
                sbloccoUtensile();
                break;
            }
        }
    }
}





package ingSw2018StaglianoVagagginiVergari.client;

import Eccezioni.FullGameException;
import ingSw2018StaglianoVagagginiVergari.common.GameObserver;
import ingSw2018StaglianoVagagginiVergari.common.RemoteController;

import java.io.IOException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public class ProxyClient implements Runnable,RemoteController {


    Object monitor = new Object(); //LAST

    Printer stampante = new Printer();

    // reference to networking layer
    private ClientSocket clientSocket;
    //  private Thread receiver;
    Thread receiver;

    // the view
    private View view;


    // Pieces of the model


    public ProxyClient(ClientSocket clientSocket) {
        this.clientSocket = clientSocket;
        try {
            this.view = new View(this);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public void ClientListener() {
        // start a receiver thread
        receiver = new Thread(
                () -> {
                    ArrayList<Object> response;
                    do {
                        response = clientSocket.nextResponse();

                        if (response != null) {
                            try {
                                this.InvocaView(response);

                            } catch (RemoteException e) {
                                e.printStackTrace();
                            }
                        }

                    } while (response != null);
                }
        );
        receiver.start();
    }




    public void run() {
        try {
            view.run();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }



    @Override
    public void partecipaPartita(GameObserver view, String username) throws InterruptedException, RemoteException, FullGameException {
        ArrayList<Object> parametriInviati=new ArrayList<>();
        ArrayList<Object> parametriRicevuti=new ArrayList<>();
        parametriInviati.add(username);
        parametriInviati.add("partecipaPartita");

        clientSocket.request(parametriInviati);





      /*  parametriRicevuti=clientSocket.nextResponse();


        InvocaView(parametriRicevuti);*/
    }

    @Override
    public void abbandonaPartita(GameObserver view, String username) throws RemoteException {
        ArrayList<Object> parametriInviati=new ArrayList<>();
        ArrayList<Object> parametriRicevuti=new ArrayList<>();
        parametriInviati.add(username);
        parametriInviati.add("abbandonaPartita");
        clientSocket.request(parametriInviati);

       // InvocaView(parametriRicevuti);
        }

    @Override
    public void scegliSchema(GameObserver view, String idUser, boolean carta1, boolean fronte) throws RemoteException {
        ArrayList<Object> parametriInviati=new ArrayList<>();
        ArrayList<Object> parametriRicevuti=new ArrayList<>();
        parametriInviati.add(idUser);
        parametriInviati.add(carta1);
        parametriInviati.add(fronte);
        parametriInviati.add("scegliSchema");
        clientSocket.request(parametriInviati);
        /*
        parametriRicevuti=clientSocket.nextResponse();
        InvocaView(parametriRicevuti);*/

    }

    @Override
    public void svolgimentoPartita(GameObserver view, ArrayList<Integer> parametri) throws RemoteException {
        ArrayList<Object> parametriInviati=new ArrayList<>();
        ArrayList<Object> parametriRicevuti=new ArrayList<>();
        parametriInviati.add(parametri.clone());
        parametriInviati.add("svolgimentoPartita");
        clientSocket.request(parametriInviati);
      //  if(parametri.get(0)!=2 && parametri.get(0)!=3) {
            bloccoMonitor( "svolgimentoPartita");
      // }


    }






       /* parametriRicevuti=clientSocket.nextResponse();
        InvocaView(parametriRicevuti);*/


    @Override
    public void usaCartaUtensile(GameObserver view, ArrayList<Integer> parametri) throws RemoteException {
        ArrayList<Object> parametriInviati=new ArrayList<>();
        ArrayList<Object> parametriRicevuti=new ArrayList<>();
        parametriInviati.add(parametri.clone());
        parametriInviati.add("usaCartaUtensile");
        clientSocket.request(parametriInviati);
        bloccoMonitor( "usaCartaUtensile");

      /*  parametriRicevuti=clientSocket.nextResponse();
        InvocaView(parametriRicevuti);
        */

    }

    @Override
    public int login(GameObserver view, String username, String token) throws RemoteException {
        ArrayList<Object> parametriInviati=new ArrayList<>();
        ArrayList<Object> parametriRicevuti=new ArrayList<>();
        parametriInviati.add(username);
        parametriInviati.add(token);
        parametriInviati.add("login");

        clientSocket.request(parametriInviati);

       /* parametriRicevuti=clientSocket.nextResponse();
        InvocaView(parametriRicevuti);*/
        return 0;
    }

    // public void notifyUser(String id,String token, String[][] schemaFronte1, String[][] schemaRetro1 ,String[][] schemaFronte2,String[][] schemaRetro2 ,String obiettivoPrivato)
    //public void notifyScheme(boolean carta1, boolean fronteScelto) throws RemoteException;
    //public void updateView(HashMap< String,String[][]> planceGiocatori , ArrayList<String> listaCartaUtensile, String giocatoreCorrente, int turno, int round, ArrayList<String > dadiRiserva, String dadoSelezionato, ArrayList<String> carteObiettivoPubblico, HashMap<String,String> obiettiviPrivati, ArrayList<String> tracciatoDelRound, HashSet<Integer> azioniGiocatore) throws RemoteException;
    public void InvocaView(ArrayList<Object> parametriRicevuti) throws RemoteException {
        String comando;
        comando=(String) parametriRicevuti.remove(parametriRicevuti.size()-1);

        switch (comando) {
            case "notifyUser": {
                String id = (String) parametriRicevuti.get(0);
                String token = (String) parametriRicevuti.get(1);
                String[][] schemaFronte1 = (String[][]) parametriRicevuti.get(2);
                String[][] schemaRetro1 = (String[][]) parametriRicevuti.get(3);
                String[][] schemaFronte2 = (String[][]) parametriRicevuti.get(4);
                String[][] schemaRetro2 = (String[][]) parametriRicevuti.get(5);
                String obiettivoPrivato = (String) parametriRicevuti.get(6);
                Integer[] difficoltàCarteSchema= (Integer[]) parametriRicevuti.get(7);
                String[] nomeCarteSchema= (String[]) parametriRicevuti.get(8);
                view.notifyUser(id, token, schemaFronte1, schemaRetro1, schemaFronte2, schemaRetro2, obiettivoPrivato,difficoltàCarteSchema,nomeCarteSchema);
                break;
            }
            case "notifyScheme": {
                boolean carta1 = (boolean) parametriRicevuti.get(0);
                boolean fronteScelto = (boolean) parametriRicevuti.get(1);
                view.notifyScheme(carta1, fronteScelto);
                break;
            }
            case "updateView": {
                HashMap<String, String[][]> planceGiocatori = (HashMap<String, String[][]>) parametriRicevuti.get(0);
                ArrayList<String> listaCartaUtensile = (ArrayList<String>) parametriRicevuti.get(1);
                String giocatoreCorrente = (String) parametriRicevuti.get(2);
                int turno = (int) parametriRicevuti.get(3);
                int round = (int) parametriRicevuti.get(4);
                ArrayList<String> dadiRiserva = (ArrayList<String>) parametriRicevuti.get(5);
                String dadoSelezionato = (String) parametriRicevuti.get(6);
                ArrayList<String> carteObiettivoPubblico = (ArrayList<String>) parametriRicevuti.get(7);
                HashMap<String, String> obiettiviPrivati = (HashMap<String, String>) parametriRicevuti.get(8);
                ArrayList<String> tracciatoDelRound = (ArrayList<String>) parametriRicevuti.get(9);
                HashSet<Integer> azioniGiocatore = (HashSet<Integer>) parametriRicevuti.get(10);
                int segnalini=(int) parametriRicevuti.get(11);
                view.updateView(planceGiocatori, listaCartaUtensile, giocatoreCorrente, turno, round,segnalini, dadiRiserva, dadoSelezionato, carteObiettivoPubblico, obiettiviPrivati, tracciatoDelRound, azioniGiocatore);
                sbloccaMonitor( "updateView");
                break;
            }
            case "updateViewPunteggio": {
                HashMap<String, Integer> punteggi = (HashMap<String, Integer>) parametriRicevuti.get(0);
                String vincitore = (String) parametriRicevuti.get(1);
                view.updateViewPunteggio(punteggi, vincitore);
                break;
            }

            //public void setPiazzamentoScorretto(String giocatoreCorrente) throws RemoteException;
            case "setPiazzamentoScorretto": {
                String giocatoreCorrente = (String) parametriRicevuti.get(0);
                view.setPiazzamentoScorretto(giocatoreCorrente);
                break;
            }

            case "updateViewTool6Bool":{
                boolean piazzabile= (boolean) parametriRicevuti.get(0);
                view.updateViewTool6Bool(piazzabile);
                break;
            }
            case "updateViewTool6Die":{
                String Dado= (String) parametriRicevuti.get(0);
                view.updateViewTool6Die(Dado);
                sbloccaMonitor( "updateViewTool6Die");
                break;
            }
            case "updateViewTool6piazzato":{
                boolean piazzato=(boolean) parametriRicevuti.get(0);
                view.updateViewTool6piazzato(piazzato);
                break;
            }
            case "updateUsername": {
                boolean usernameOK = (boolean) parametriRicevuti.get(0);
                view.updateUsername(usernameOK);
                break;
            }

            case "notifyExit":{
                view.notifyExit();
                break;
            }
            case "ping":{
                view.ping();
                break;
            }
            case "notifyUserExit":{
                String username=(String) parametriRicevuti.get(0);
                view.notifyUserExit(username);
                break;
            }
            case "sbloccoUtensile":{
                System.out.println();
                sbloccaMonitor( "sbloccoUtensile");
                break;
            }

            /*


            public void updateViewTool4 (boolean fase) throws RemoteException;
            public void updateViewTool12 (boolean fase) throws RemoteException;*/

            case "updateViewTool11":{
                String Dado=(String) parametriRicevuti.get(0);
                view.updateViewTool11(Dado);
                sbloccaMonitor( "updateViewTool11");
                break;
            }

            case "updateMessage":{
                String message=(String) parametriRicevuti.get(0);
                view.updateMessage(message);
                break;
            }
            case "updateViewTool4":{
                boolean fase=(boolean) parametriRicevuti.get(0);
                view.updateViewTool4(fase);
                break;
            }
            case "updateViewTool12":{
                boolean fase=(boolean) parametriRicevuti.get(0);
                view.updateViewTool12(fase);
                break;
            }
        }

    }

    public void bloccoMonitor( String stringa) {
        synchronized (monitor) {

            try {


                //stampante.printScritta("STO BLOCCANDO IN ATTESA DI RISPOSTA con la wait - "+stringa, "verde");
                monitor.wait();
                //stampante.printScritta("SONO STATO SBLOCCATO ", "verde");


            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void sbloccaMonitor( String stringa){
        synchronized (monitor) {
            monitor.notifyAll();
            //stampante.printScritta("HO SBLOCCATO IL MONITOR con notifyall -  "+stringa, "verde");
        }
    }
    }



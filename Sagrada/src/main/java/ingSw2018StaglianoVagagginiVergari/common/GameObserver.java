package ingSw2018StaglianoVagagginiVergari.common;

import com.sun.org.apache.regexp.internal.RE;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;

public interface GameObserver extends Remote {
    public void notifyUser(String id,String token, String[][] schemaFronte1, String[][] schemaRetro1 ,String[][] schemaFronte2,String[][] schemaRetro2 ,String obiettivoPrivato) throws RemoteException;
    public void notifyScheme(boolean carta1, boolean fronteScelto) throws RemoteException;
    public void updateView(HashMap< String,String[][]> planceGiocatori , ArrayList<String> listaCartaUtensile, String giocatoreCorrente, int turno, int round, ArrayList<String > dadiRiserva, String dadoSelezionato, ArrayList<String> carteObiettivoPubblico, HashMap<String,String> obiettiviPrivati, ArrayList<String> tracciatoDelRound,HashSet<Integer> azioniGiocatore) throws RemoteException;
    public void updateViewPlanciaGiocatoreCorrente(  String[][] plancia) throws RemoteException;

    public void updateViewTurno(int turno) throws RemoteException;

    public void updateViewRound(int round) throws RemoteException;

    public void updateViewGiocatoreCorrente(String giocatoreCorrente) throws RemoteException;

    public void updateViewPlanceGiocatori(HashMap< String,String[][]> planceGiocatori) throws RemoteException;

    public void updateViewCarteUtensile(ArrayList<String> listaCartaUtensile) throws RemoteException;

    public void updateViewDadiRiserva(ArrayList<String > dadiRiserva) throws RemoteException;

    public void updateViewDadoSelezionato(String dadoSelezionato) throws RemoteException;

    public void updateViewPunteggio(HashMap<String, Integer> punteggi,String vincitore) throws RemoteException;

    public void setPiazzamentoScorretto(String giocatoreCorrente) throws RemoteException;

    public void updateViewTool6Bool(boolean piazzabile) throws RemoteException;

    public void updateViewTool6Die(String Dado) throws RemoteException;

    public void updateViewTool6piazzato (boolean piazzato) throws RemoteException;

    public void updateUsername(boolean usernameOK) throws RemoteException;
    public void notifyExit() throws RemoteException;
    public void ping() throws RemoteException;
    public void notifyUserExit(String username) throws RemoteException;
    public void updateViewTool11(String Dado) throws RemoteException;
    public void updateMessage (String message) throws RemoteException;
    public void updateViewTool4 (boolean fase) throws RemoteException;
    public void updateViewTool12 (boolean fase) throws RemoteException;
    public void notifyTurnTimer() throws RemoteException;
}

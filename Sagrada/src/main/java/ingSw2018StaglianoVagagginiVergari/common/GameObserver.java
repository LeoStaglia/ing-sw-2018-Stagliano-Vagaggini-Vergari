package ingSw2018StaglianoVagagginiVergari.common;

import com.sun.org.apache.regexp.internal.RE;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;

public interface GameObserver extends Remote {

    /**
     * @param id the username associated to the view
     * @param schemaFronte1 Scheme Card 1 front String representation
     * @param schemaRetro1  Scheme Card 1 back  String representation
     * @param schemaFronte2 Scheme Card 2 front String representation
     * @param schemaRetro2  Scheme Card 2 back  String representation
     * @param obiettivoPrivato Private Objective card assigned to the user String representation
     * @param difficoltàCarteSchema the list of difficult for each Scheme
     * @param nomeCarteSchema the list of names of all the Schemes
     */
    public void notifyUser(String id,String[][] schemaFronte1, String[][] schemaRetro1 ,String[][] schemaFronte2,String[][] schemaRetro2 ,String obiettivoPrivato, Integer[] difficoltàCarteSchema, String[] nomeCarteSchema) throws RemoteException;

    /**
     * @param carta1 true if card 1 is chosen
     * @param fronteScelto true if the front is chosen
     */
    public void notifyScheme(boolean carta1, boolean fronteScelto) throws RemoteException;

    /**
     * @param planceGiocatori Representation of the Window Frame of all players
     * @param listaCartaUtensile list of the ToolCard extracted in the game
     * @param giocatoreCorrente the current player id
     * @param turno turn
     * @param round round
     * @param segnaliniGiocatori list of the favor tokens of each player
     * @param dadiRiserva the DraftPool
     * @param dadoSelezionato the selected die
     * @param carteObiettivoPubblico list of the Public Objective card extracted
     * @param obiettiviPrivati list of the Private Objectives
     * @param tracciatoDelRound RoundTrack
     * @param azioniGiocatore the available action for the player
     */
    public void updateView(HashMap< String,String[][]> planceGiocatori , ArrayList<String> listaCartaUtensile, String giocatoreCorrente, int turno, int round, HashMap<String, Integer> segnaliniGiocatori, ArrayList<String > dadiRiserva, String dadoSelezionato, ArrayList<String> carteObiettivoPubblico, HashMap<String,String> obiettiviPrivati, ArrayList<String> tracciatoDelRound,HashSet<Integer> azioniGiocatore) throws RemoteException;
   /* public void updateViewPlanciaGiocatoreCorrente(  String[][] plancia) throws RemoteException;

    public void updateViewTurno(int turno) throws RemoteException;

    public void updateViewRound(int round) throws RemoteException;

    public void updateViewGiocatoreCorrente(String giocatoreCorrente) throws RemoteException;

    public void updateViewPlanceGiocatori(HashMap< String,String[][]> planceGiocatori) throws RemoteException;

    public void updateViewCarteUtensile(ArrayList<String> listaCartaUtensile) throws RemoteException;

    public void updateViewDadiRiserva(ArrayList<String > dadiRiserva) throws RemoteException;

    public void updateViewDadoSelezionato(String dadoSelezionato) throws RemoteException; */

    /**
     * @param punteggi association between user and its score
     * @param vincitore the winner
     */
    public void updateViewPunteggio(HashMap<String, Integer> punteggi,String vincitore) throws RemoteException;

    public void setPiazzamentoScorretto(String giocatoreCorrente) throws RemoteException;

    /**
     * @param piazzabile true if the new die extracted has at least one valid collocation in the Window Frame
     */
    public void updateViewTool6Bool(boolean piazzabile) throws RemoteException;

    /**
     * @param Dado the new Die
     */
    public void updateViewTool6Die(String Dado) throws RemoteException;

    /**
     * @param piazzato true if the new Die has been placed
     */
    public void updateViewTool6piazzato (boolean piazzato) throws RemoteException;

    /**
     * @param usernameOK true if the chosen username is OK
     */
    public void updateUsername(boolean usernameOK) throws RemoteException;

    public void notifyExit() throws RemoteException;
    public void ping() throws RemoteException;

    /**
     * @param username the username of the player disconnected
     */
    public void notifyUserExit(String username) throws RemoteException;

    /**
     * @param Dado the new Die
     */
    public void updateViewTool11(String Dado) throws RemoteException;

    /**
     * @param message the message to print out
     */
    public void updateMessage (String message) throws RemoteException;

    /**
     * @param fase the phase of tool card 4
     */
    public void updateViewTool4 (boolean fase) throws RemoteException;

    /**
     * @param fase the phase of tool card 12
     * @param numeroTracciatoRound the position on the roundTrack of the die from which you want to catch the color
     */
    public void updateViewTool12 (boolean fase, int numeroTracciatoRound) throws RemoteException;
    public void notifyTurnTimer() throws RemoteException;
    public void updatePagamento(boolean utilizzabile) throws RemoteException;
}

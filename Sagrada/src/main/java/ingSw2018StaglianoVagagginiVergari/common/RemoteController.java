package ingSw2018StaglianoVagagginiVergari.common;

import Eccezioni.FullGameException;
import Eccezioni.MossaIllegaleException;
import ingSw2018StaglianoVagagginiVergari.server.model.Partita;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

public interface RemoteController extends Remote{

    /** Register the user to a game
     * @param view the view which wants to partecipate
     * @param username the username chosen by the client
     * @throws InterruptedException .
     * @throws RemoteException   Network problem
     * @throws FullGameException in case of try of registration to an already started game
     */
    public void partecipaPartita(GameObserver view, String username) throws InterruptedException, RemoteException, FullGameException;

    /** Deregister user from the game
     * @param view the view that wants to quit
     * @param username the username of that view
     * @throws RemoteException Network problem
     */
    public void abbandonaPartita(GameObserver view, String username) throws RemoteException;

    /** Assign the scheme chosen by the user
     * @param view .
     * @param idUser the user id
     * @param carta1 the chosen card
     * @param fronte true for front - false for back */
    public void scegliSchema(GameObserver view, String idUser,boolean carta1, boolean fronte)throws RemoteException;

    /** Handle the action requests made by the user
     * @param view  the view that request the action
     * @param parametri the sequence of operation and data required
     * @throws RemoteException Network error
     */
    public void svolgimentoPartita(GameObserver view,ArrayList<Integer> parametri) throws RemoteException;

    /**Use a ToolCard*/
    public void usaCartaUtensile(GameObserver view,ArrayList<Integer> parametri) throws RemoteException;
    /** method call to reconnect to a game by login*/
    public int login(GameObserver view, String username) throws RemoteException;
    public Partita getPartita() throws RemoteException;
   // public void ClientListener() throws RemoteException;



    }

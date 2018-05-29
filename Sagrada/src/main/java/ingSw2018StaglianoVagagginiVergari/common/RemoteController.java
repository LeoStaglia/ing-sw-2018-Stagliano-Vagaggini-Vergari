package ingSw2018StaglianoVagagginiVergari.common;

import Eccezioni.FullGameException;
import Eccezioni.MossaIllegaleException;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

public interface RemoteController extends Remote{

    public void partecipaPartita(GameObserver view, String username) throws InterruptedException, RemoteException, FullGameException;
    public void abbandonaPartita(GameObserver view, String username) throws RemoteException;
    public void scegliSchema(GameObserver view, String idUser,boolean carta1, boolean fronte)throws RemoteException;
    public void svolgimentoPartita(GameObserver view,ArrayList<Integer> parametri) throws MossaIllegaleException, RemoteException;
    public void usaCartaUtensile(GameObserver view,ArrayList<Integer> parametri) throws RemoteException;



    }

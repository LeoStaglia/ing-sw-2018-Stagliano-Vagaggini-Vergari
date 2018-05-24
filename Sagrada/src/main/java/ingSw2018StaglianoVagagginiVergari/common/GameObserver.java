package ingSw2018StaglianoVagagginiVergari.common;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;

public interface GameObserver extends Remote {
    public void notifyUser(String id, String[][] schemaFronte1, String[][] schemaRetro1 ,String[][] schemaFronte2,String[][] schemaRetro2 ,String obiettivoPrivato) throws RemoteException;
    public void notifyScheme(boolean carta1, boolean fronteScelto) throws RemoteException;
    public void updateView(HashMap< String,String[][]> planceGiocatori , HashMap<String,String> listaCartaUtensile, String giocatoreCorrente, int turno, int round, ArrayList<String > dadiRiserva, String dadoSelezionato) throws RemoteException;

    }

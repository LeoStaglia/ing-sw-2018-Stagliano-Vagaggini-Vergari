package ingSw2018StaglianoVagagginiVergari.client.GUI;

import ingSw2018StaglianoVagagginiVergari.client.GUI.GUIStates.DefaultState;
import ingSw2018StaglianoVagagginiVergari.client.GUI.GUIStates.NotCurrentState;
import ingSw2018StaglianoVagagginiVergari.client.GUI.GUIStates.ToolCardStates.*;
import ingSw2018StaglianoVagagginiVergari.common.GameObserver;
import javafx.application.Platform;
import javafx.scene.Scene;

import java.io.IOException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public class DataGameObserver extends UnicastRemoteObject implements GameObserver{


    private boolean isUsernameOK = false;
    private String username;
    private boolean updateView = false;
    private String token;
    private String[][] schemaFronte1;
    private String[][] schemaRetro1;
    private String[][] schemaFronte2;
    private String[][] schemaRetro2;
    private String obiettivoPrivato;
    private boolean carta1;
    private boolean fronteScelto;
    private HashMap<String, String[][]> planceGiocatori;
    private ArrayList<String> listaCartaUtensile;
    private String giocatoreCorrente;
    private int turno;
    private int round;
    private ArrayList<String> dadiRiserva;
    private String dadoSelezionato;
    private ArrayList<String> carteObiettivoPubblico;
    private HashMap<String, String> obiettiviPrivati;
    private ArrayList<String> tracciatoDelRound;
    private HashSet<Integer> azioniGiocatore;
    private GameScene gameScene;
    private OtherBoards otherBoards;
    private Integer[] difficoltaCarteSchema;
    private String[] nomeCarteSchema;
    private boolean cartaUtilizzabile = true;
    private boolean lathekinPhase2 = false;
    private Boolean tool12Phase2;
    private int numeroTracciatoRound;
    private HashMap<String, Integer> punteggi;
    private String vincitore;

    protected DataGameObserver() throws RemoteException {

    }

    public ArrayList<String> getDadiRiserva() {
        return dadiRiserva;
    }

    public void setCartaUtilizzabile(boolean cartaUtilizzabile) {
        this.cartaUtilizzabile = cartaUtilizzabile;
    }

    public boolean isCartaUtilizzabile() {
        return cartaUtilizzabile;
    }

    public String cartaUtensile(int index){
        return listaCartaUtensile.get(index).substring(1, listaCartaUtensile.get(index).indexOf('*'));
    }

    public HashMap<String, Integer> getPunteggi() {
        return punteggi;
    }

    public String getVincitore() {
        return vincitore;
    }

    public void setGameScene(GameScene gameScene) {
        this.gameScene = gameScene;
    }

    public void setOtherBoards(OtherBoards otherBoards) {
        this.otherBoards = otherBoards;
    }

    public String[] getNomeCarteSchema() {
        return nomeCarteSchema;
    }

    public Integer[] getDifficoltaCarteSchema() {
        return difficoltaCarteSchema;
    }

    public synchronized boolean isCurrentPlayer(){
        return username.equals(giocatoreCorrente);
    }

    public boolean isLathekinPhase2(){
        return lathekinPhase2;
    }

    public HashSet<Integer> getAzioniGiocatore() {
        return azioniGiocatore;
    }

    public int getNumeroTracciatoRound(){
        return numeroTracciatoRound;
    }

    public Boolean getTool12Phase2() {
        return tool12Phase2;
    }

    public void setTool12Phase2(Boolean tool12Phase2) {
        this.tool12Phase2 = tool12Phase2;
    }

    @Override
    public void updatePagamento(boolean cartaUtilizzabile) throws RemoteException {
        setCartaUtilizzabile(cartaUtilizzabile);
    }

    public int roundTrackSize(){
        return tracciatoDelRound.size();
    }


    @Override
    public synchronized void notifyUser(String id,String[][] schemaFronte1, String[][] schemaRetro1 ,String[][] schemaFronte2,String[][] schemaRetro2 ,String obiettivoPrivato, Integer[] difficoltàCarteSchema, String[] nomeCarteSchema) throws RemoteException{
        this.token=token;
        this.schemaFronte1=schemaFronte1;
        this.schemaRetro1=schemaRetro1;
        this.schemaFronte2=schemaFronte2;
        this.schemaRetro2=schemaRetro2;
        this.obiettivoPrivato= obiettivoPrivato;
        this.difficoltaCarteSchema=difficoltàCarteSchema;
        this.nomeCarteSchema=nomeCarteSchema;
        updateView=true;
        notifyAll();
    }

    @Override
    public void notifyScheme(boolean carta1, boolean fronteScelto) throws RemoteException {
        this.carta1=carta1;
        this.fronteScelto=fronteScelto;
    }

    @Override
    public synchronized void updateView(HashMap< String,String[][]> planceGiocatori , ArrayList<String> listaCartaUtensile, String giocatoreCorrente, int turno, int round, HashMap<String, Integer> segnaliniGiocatori, ArrayList<String > dadiRiserva, String dadoSelezionato, ArrayList<String> carteObiettivoPubblico, HashMap<String,String> obiettiviPrivati, ArrayList<String> tracciatoDelRound,HashSet<Integer> azioniGiocatore) throws RemoteException {
        this.giocatoreCorrente=giocatoreCorrente;
        boolean different=false;
        if (this.planceGiocatori!=null) {
            for (String username : planceGiocatori.keySet()) {
                if (!matrixOfStringEquals(this.planceGiocatori.get(username), planceGiocatori.get(username))) {
                    different=true;
                    if (username.equals(this.username)) {
                        Platform.runLater(()->gameScene.renderPlayerGridArea(planceGiocatori.get(username), this.username));
                    }
                }
            }
            if (different){
                otherBoards.setPlanceGiocatori(planceGiocatori);
                Platform.runLater(()->otherBoards.render());
            }
            this.planceGiocatori=planceGiocatori;
        }else{
            otherBoards.setPlanceGiocatori(planceGiocatori);
            Platform.runLater(()->otherBoards.render());
            Platform.runLater(()->gameScene.renderPlayerGridArea(planceGiocatori.get(username), username));
        }
        if (isCurrentPlayer()){
            if (!lathekinPhase2 && !(tool12Phase2!=null && tool12Phase2)) {
                Platform.runLater(() -> gameScene.setCurrentPlayer());
                gameScene.setState(new DefaultState(gameScene, azioniGiocatore));
            }
        }else{
            Platform.runLater(()->gameScene.setNotCurrentPlayer());
            gameScene.setState(new NotCurrentState(gameScene));
        }
        if (turno!=this.turno){
            Platform.runLater(()->gameScene.updateTurn(turno));
        }
        this.turno=turno;
        if (round!=this.round){
            Platform.runLater(()->gameScene.updateRound(round));
        }
        this.round=round;
        if (this.dadiRiserva==null || (!arraylistOfString(this.dadiRiserva, dadiRiserva))){
            Platform.runLater(()->gameScene.renderDraftPoolArea(dadiRiserva));
        }
        this.dadiRiserva=dadiRiserva;

        if (this.tracciatoDelRound==null){
            this.tracciatoDelRound=tracciatoDelRound;
            Platform.runLater(()->gameScene.renderRoundTrack(tracciatoDelRound));
        }else{
            if (!arraylistOfString(this.tracciatoDelRound, tracciatoDelRound)){
                this.tracciatoDelRound=tracciatoDelRound;
                Platform.runLater(()->gameScene.renderRoundTrack(tracciatoDelRound));
            }
        }
        if (this.listaCartaUtensile==null){
            this.listaCartaUtensile=listaCartaUtensile;
            Platform.runLater(()->gameScene.renderToolCardsArea(listaCartaUtensile));
        }else if(!arraylistOfString(this.listaCartaUtensile, listaCartaUtensile)){
            this.listaCartaUtensile=listaCartaUtensile;
            Platform.runLater(()->gameScene.renderToolCardsArea(listaCartaUtensile));
        }
        if(this.carteObiettivoPubblico==null){
            Platform.runLater(()->gameScene.renderPublicObjectiveArea(carteObiettivoPubblico));
            this.carteObiettivoPubblico=carteObiettivoPubblico;
        }
        if (this.obiettivoPrivato==null){
            Platform.runLater(()->gameScene.renderObiettivoPrivato(obiettiviPrivati.get(username)));
            this.obiettivoPrivato=obiettiviPrivati.get(username);
        }

        this.round=round;
        updateView=true;
        notifyAll();

    }



    @Override
    public void updateViewPunteggio(HashMap<String, Integer> punteggi, String vincitore) throws RemoteException {
        Platform.runLater(()->TransitionHandler.toPointsScene(punteggi, vincitore));
    }

    @Override
    public void setPiazzamentoScorretto(String giocatoreCorrente) throws RemoteException {

    }

    @Override
    public void updateViewTool6Bool(boolean piazzabile) throws RemoteException {

    }

    @Override
    public void updateViewTool6Die(String Dado) throws RemoteException {
        Tool6Popup popup = new Tool6Popup();
        popup.setDie(GraphicRenderer.renderDie(Dado));
        gameScene.setState(new PennelloPerPastaSaldaState(gameScene, false));
        Platform.runLater(()->popup.display());
    }

    @Override
    public void updateViewTool6piazzato(boolean piazzato) throws RemoteException {

    }

    @Override
    public  void updateUsername(boolean usernameOK) throws RemoteException {
        isUsernameOK=true;
    }

    @Override
    public void notifyExit() throws RemoteException {

    }

    @Override
    public void ping() throws RemoteException {

    }

    @Override
    public void notifyUserExit(String username) throws RemoteException {
        Platform.runLater(()->new AlertPopup().display("Attenzione", username+" è uscito dalla partita"));
    }

    @Override
    public void updateViewTool11(String Dado) throws RemoteException {
        new DiluentePerPastaSaldaPopupHandler(gameScene, Dado, this).start();
    }

    @Override
    public void updateMessage(String message) throws RemoteException {
        Platform.runLater(()->new AlertPopup().display("Attenzione", message));
    }

    @Override
    public synchronized void updateViewTool4(boolean fase) throws RemoteException {
        lathekinPhase2 = fase;
        notifyAll();
    }

    @Override
    public void updateViewTool12(boolean fase, int numeroTracciatoRound) throws RemoteException {
        this.numeroTracciatoRound=numeroTracciatoRound;
        tool12Phase2=fase;
    }

    @Override
    public void notifyTurnTimer() throws RemoteException {

    }

    public void setUsername(String username) {
        this.username = username;
    }

    public boolean isUsernameOK() {
        return isUsernameOK;
    }

    public boolean isUpdated(){
        return updateView;
    }

    public String getUsername() {
        return username;
    }

    public String getToken() {
        return token;
    }

    public String[][] getSchemaFronte1() {
        return schemaFronte1;
    }

    public String[][] getSchemaRetro1() {
        return schemaRetro1;
    }

    public String[][] getSchemaFronte2() {
        return schemaFronte2;
    }

    public String[][] getSchemaRetro2() {
        return schemaRetro2;
    }

    public String getObiettivoPrivato() {
        return obiettivoPrivato;
    }

    public void setUpdate(boolean updateView){
        this.updateView=updateView;
    }

    private boolean matrixOfStringEquals(String[][] matr1, String[][] matr2){

        if (matr1==null || matr2==null){
            return false;
        }

        if (matr1==matr2){
            return true;
        }

        if (matr1.length!=matr2.length){
            return false;
        }

        for (int i=0; i<matr1.length; i++){
            if (matr1[i].length!=matr2[i].length){
                return false;
            }
        }
        for (int i=0; i<matr1.length; i++){
            for (int j=0; j<matr1[i].length; j++){
                if(!matr1[i][j].equalsIgnoreCase(matr2[i][j])){
                    return false;
                }
            }
        }
        return true;
    }

    private boolean arraylistOfString(ArrayList<String> list1, ArrayList<String> list2){
        if (list1==list2){
            return true;
        }
        if (list1.size()!=list2.size()){
            return false;
        }
        for (int i=0; i<list1.size(); i++){
            if (!list1.get(i).equals(list2.get(i)))
                return false;
        }
        return true;
    }

}

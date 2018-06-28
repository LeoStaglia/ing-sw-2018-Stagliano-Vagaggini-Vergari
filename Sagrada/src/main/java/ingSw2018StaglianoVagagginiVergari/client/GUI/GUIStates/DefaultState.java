package ingSw2018StaglianoVagagginiVergari.client.GUI.GUIStates;

import ingSw2018StaglianoVagagginiVergari.client.GUI.AlertPopup;
import ingSw2018StaglianoVagagginiVergari.client.GUI.GUIStates.ToolCardStates.FactoryToolCardStates;
import ingSw2018StaglianoVagagginiVergari.client.GUI.GameScene;
import ingSw2018StaglianoVagagginiVergari.client.GUI.SagradaGUI;
import ingSw2018StaglianoVagagginiVergari.client.GUI.ServerRequests.RemoteRequestHandler;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Stack;

public class DefaultState implements EventHandler<MouseEvent>, GUIState{

    private GameScene gameScene;

    private  HashSet<Integer> azioniGiocatore;

    private Integer indiceRiserva;

    private ArrayList<Integer> placementPair;

    private ArrayList<Integer> parametriController;


    public DefaultState(GameScene gameScene, HashSet<Integer> azioniGiocatore){
        this.gameScene = gameScene;
        this.azioniGiocatore=azioniGiocatore;
        parametriController = new ArrayList<>();
        placementPair= new ArrayList<>();
    }

    @Override
    public void handle(MouseEvent event) {
        if (provenienzaRiserva(event)){
            if (azioniGiocatore.contains(1) || azioniGiocatore.contains(4)) {
                indiceRiserva=indexReserveDice(event);
            }else{
                new AlertPopup().display("Attenzione", "Hai già piazzato un dado");
            }
        }else if(provenienzaGriglia(event)){
            if (indiceRiserva!=null){
                placementPair=indexGrid(event);
                gameScene.setState(new LoadingState());
                parametriController.add(1);
                parametriController.add(indiceRiserva);
                parametriController.addAll(placementPair);
                SagradaGUI.getRequestHandler().genericGameRequest(parametriController, gameScene);
            }
        }else if(gameScene.getToolCardList().contains(event.getSource())){
            if (azioniGiocatore.contains(2)) {
                indiceRiserva = null;
                placementPair.clear();
                parametriController.clear();
                parametriController.add(2);
                int cardIndex = 0;
                for (StackPane toolcard : gameScene.getToolCardList()) {
                    if (toolcard == event.getSource()) {
                        break;
                    }
                    cardIndex++;
                }
                parametriController.add(cardIndex);
                if (!((SagradaGUI.getRequestHandler().getDataGameObserver().cartaUtensile(cardIndex).equals("Taglierina Circolare") || SagradaGUI.getRequestHandler().getDataGameObserver().cartaUtensile(cardIndex).equals("Taglierina Manuale")) && SagradaGUI.getRequestHandler().getDataGameObserver().roundTrackSize()==0)) {
                    SagradaGUI.getRequestHandler().genericGameRequest(parametriController, gameScene);
                }else{
                    new AlertPopup().display("Attenzione", "Carta non utilizzabile, tracciato del round vuoto!");
                }
            }else{
                new AlertPopup().display("Attenzione", "Hai già usato una carta.");
            }

        }

    }

    private boolean provenienzaGriglia(MouseEvent event){

        for(Node n:gameScene.getPlayerGrid().getChildren()){
            if (n==event.getSource()){
                return true;
            }
        }
        return false;
    }

    private ArrayList<Integer> indexGrid(MouseEvent event){
        ArrayList<Integer> result = new ArrayList<>();
        for (Node n:gameScene.getPlayerGrid().getChildren()){
            if (n==event.getSource()){
                result.add(GridPane.getRowIndex(n));
                result.add(GridPane.getColumnIndex(n));
                break;
            }

        }

        return result;
    }

    private boolean provenienzaRiserva(MouseEvent event){
        for (Node n:gameScene.getRiserva().getChildren()){
            if (n==event.getSource()){
                return true;
            }
        }
        return false;
    }

    private int indexReserveDice(MouseEvent event){
        int result=0;
        for (Node n:gameScene.getRiserva().getChildren()){
            if (n==event.getSource()){
                return result;
            }
            result++;
        }
        return result;
    }
}

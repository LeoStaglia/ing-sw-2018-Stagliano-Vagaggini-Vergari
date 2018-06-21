package ingSw2018StaglianoVagagginiVergari.client.GUI.GUIStates.ToolCardStates;

import ingSw2018StaglianoVagagginiVergari.client.GUI.AlertPopup;
import ingSw2018StaglianoVagagginiVergari.client.GUI.GUIStates.GUIState;
import ingSw2018StaglianoVagagginiVergari.client.GUI.GUIStates.LoadingState;
import ingSw2018StaglianoVagagginiVergari.client.GUI.GameScene;
import ingSw2018StaglianoVagagginiVergari.client.GUI.SagradaGUI;
import ingSw2018StaglianoVagagginiVergari.server.model.carteUtensile.RigaInSughero;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;

import java.util.ArrayList;

public class RigaInSugheroState implements GUIState{

    private GameScene gameScene;

    private Integer indiceRiserva;

    private ArrayList<Integer> placementPair;

    private ArrayList<Integer> parametriController;

    public RigaInSugheroState(GameScene gameScene){
        this.gameScene=gameScene;
        placementPair=new ArrayList<>();
        parametriController=new ArrayList<>();
    }

    @Override
    public void handle(MouseEvent event) {
        if (provenienzaRiserva(event)) {
            indiceRiserva = indexReserveDice(event);
        } else if (provenienzaGriglia(event)) {
            if (indiceRiserva != null) {
                placementPair = indexGrid(event);
                gameScene.setState(new LoadingState());
                parametriController.add(indiceRiserva);
                parametriController.addAll(placementPair);
                SagradaGUI.getRequestHandler().usoCartaUtensileRequest(parametriController);
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

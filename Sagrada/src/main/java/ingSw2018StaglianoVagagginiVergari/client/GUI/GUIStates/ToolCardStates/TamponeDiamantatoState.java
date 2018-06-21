package ingSw2018StaglianoVagagginiVergari.client.GUI.GUIStates.ToolCardStates;

import ingSw2018StaglianoVagagginiVergari.client.GUI.AlertPopup;
import ingSw2018StaglianoVagagginiVergari.client.GUI.GUIStates.GUIState;
import ingSw2018StaglianoVagagginiVergari.client.GUI.GameScene;
import ingSw2018StaglianoVagagginiVergari.client.GUI.SagradaGUI;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;

import java.util.ArrayList;

public class TamponeDiamantatoState implements GUIState{

    private GameScene gameScene;
    private Integer indiceRiserva;
    private ArrayList<Integer> parametriController;


    public TamponeDiamantatoState(GameScene gameScene){
        this.gameScene=gameScene;
        parametriController = new ArrayList<>();
    }

    @Override
    public void handle(MouseEvent event) {
        if (!provenienzaRiserva(event)){
            new AlertPopup().display("Attenzione", "Seleziona un dado dalla riserva!");
        }else{
            indiceRiserva=indexReserveDice(event);
            parametriController.add(indiceRiserva);
            SagradaGUI.getRequestHandler().usoCartaUtensileRequest(parametriController);
        }
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

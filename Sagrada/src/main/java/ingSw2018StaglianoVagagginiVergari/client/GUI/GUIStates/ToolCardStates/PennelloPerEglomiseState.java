package ingSw2018StaglianoVagagginiVergari.client.GUI.GUIStates.ToolCardStates;

import ingSw2018StaglianoVagagginiVergari.client.GUI.AlertPopup;
import ingSw2018StaglianoVagagginiVergari.client.GUI.GUIStates.GUIState;
import ingSw2018StaglianoVagagginiVergari.client.GUI.GUIStates.LoadingState;
import ingSw2018StaglianoVagagginiVergari.client.GUI.GameScene;
import ingSw2018StaglianoVagagginiVergari.client.GUI.SagradaGUI;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;

import java.util.ArrayList;

public class PennelloPerEglomiseState implements GUIState {

    private GameScene gameScene;

    private ArrayList<Integer> indiciDado;

    private ArrayList<Integer> indiciPiazzamento;

    private ArrayList<Integer> parametriController;

    public PennelloPerEglomiseState(GameScene gameScene){
        this.gameScene=gameScene;
        indiciDado=new ArrayList<>();
        indiciPiazzamento=new ArrayList<>();
        parametriController=new ArrayList<>();
    }

    @Override
    public void handle(MouseEvent event) {
        if (!provenienzaGriglia(event)){
            new AlertPopup().display("Attenzione", "Seleziona un dado nella tua vetrata.");
        }else{
            if (!indiciDado.isEmpty()){
                indiciPiazzamento=indexGrid(event);
            }else{
                indiciDado=indexGrid(event);
            }
            if (!indiciPiazzamento.isEmpty() && !indiciDado.isEmpty()) {
                parametriController.addAll(indiciDado);
                parametriController.addAll(indiciPiazzamento);
                gameScene.setState(new LoadingState());
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
}

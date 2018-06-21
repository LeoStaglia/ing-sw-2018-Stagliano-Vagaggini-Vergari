package ingSw2018StaglianoVagagginiVergari.client.GUI.GUIStates.ToolCardStates;

import ingSw2018StaglianoVagagginiVergari.client.GUI.AlertPopup;
import ingSw2018StaglianoVagagginiVergari.client.GUI.GUIStates.GUIState;
import ingSw2018StaglianoVagagginiVergari.client.GUI.GameScene;
import ingSw2018StaglianoVagagginiVergari.client.GUI.SagradaGUI;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;

import java.util.ArrayList;

public class PennelloPerPastaSaldaState implements GUIState{

    public GameScene gameScene;
    public boolean fase1=true;
    public ArrayList<Integer> parametriController;

    public PennelloPerPastaSaldaState(GameScene gameScene,boolean fase1){
        this.gameScene=gameScene;
        this.fase1=fase1;
        parametriController=new ArrayList<>();
    }

    @Override
    public void handle(MouseEvent event) {
        if (fase1){
            if (!provenienzaRiserva(event)){
                new AlertPopup().display("Attenzione", "Seleziona un dado dalla riserva.");
            }else{
                parametriController.add(indexReserveDice(event));
                SagradaGUI.getRequestHandler().usoCartaUtensileRequest(parametriController);
                fase1=false;
            }
        }else{
            parametriController.clear();
            parametriController.add(2);
            int i;
            for (i=0; i<3; i++) {
                if (SagradaGUI.getRequestHandler().getDataGameObserver().cartaUtensile(i).equals("Pennello Per Pasta Salda")) {
                    break;
                }
            }
            parametriController.add(i);
            SagradaGUI.getRequestHandler().genericGameRequest(parametriController);
            if (!provenienzaGriglia(event)){
                new AlertPopup().display("Attenzione", "Scegli dove posizionare il dado!");
            }else{
                parametriController.clear();
                parametriController.addAll(indexGrid(event));
                fase1=true;
                SagradaGUI.getRequestHandler().usoCartaUtensileRequest(parametriController);
            }
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


    private boolean provenienzaGriglia(MouseEvent event){

        for(Node n:gameScene.getPlayerGrid().getChildren()){
            if (n==event.getSource()){
                return true;
            }
        }
        return false;
    }
}

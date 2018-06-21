package ingSw2018StaglianoVagagginiVergari.client.GUI.GUIStates.ToolCardStates;

import ingSw2018StaglianoVagagginiVergari.client.GUI.AlertPopup;
import ingSw2018StaglianoVagagginiVergari.client.GUI.GUIStates.GUIState;
import ingSw2018StaglianoVagagginiVergari.client.GUI.GUIStates.LoadingState;
import ingSw2018StaglianoVagagginiVergari.client.GUI.GameScene;
import ingSw2018StaglianoVagagginiVergari.client.GUI.SagradaGUI;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;

import java.util.ArrayList;

public class LathekinState implements GUIState{

    private GameScene gameScene;

    private ArrayList<Integer> indiciDado;

    private ArrayList<Integer> indiciPiazzamento;


    private ArrayList<Integer> parametriController;

    public LathekinState(GameScene gameScene){
        this.gameScene=gameScene;
        indiciDado=new ArrayList<>();
        parametriController = new ArrayList<>();
        indiciPiazzamento=new ArrayList<>();
    }

    @Override
    public void handle(MouseEvent event) {
        if (!provenienzaGriglia(event)){
            new AlertPopup().display("Attenzione", "Seleziona un dado dalla tua vetrata!");
        }else{
            if (indiciDado.isEmpty()) {
                indiciDado.addAll(indexGrid(event));
            }else{
                indiciPiazzamento.addAll(indexGrid(event));
                if (!indiciPiazzamento.isEmpty() && !indiciDado.isEmpty()){
                    gameScene.setState(new LoadingState());
                    parametriController.addAll(indiciDado);
                    parametriController.addAll(indiciPiazzamento);
                    SagradaGUI.getRequestHandler().usoCartaUtensileRequest(parametriController);
                    if (!SagradaGUI.getRequestHandler().getDataGameObserver().isLathekinPhase2()){
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                synchronized (SagradaGUI.getRequestHandler().getDataGameObserver()){
                                    while(!SagradaGUI.getRequestHandler().getDataGameObserver().isLathekinPhase2()){
                                        try {
                                            SagradaGUI.getRequestHandler().getDataGameObserver().wait();
                                        } catch (InterruptedException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                }
                                parametriController.clear();
                                int i;
                                for (i=0; i<3; i++){
                                    if (SagradaGUI.getRequestHandler().getDataGameObserver().cartaUtensile(i).equals("Lathekin")){
                                        break;
                                    }
                                }
                                parametriController.add(2);
                                parametriController.add(i);
                                SagradaGUI.getRequestHandler().genericGameRequest(parametriController);
                                gameScene.setState(new LathekinState(gameScene));
                            }
                        }).start();

                    }
                }
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

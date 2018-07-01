package ingSw2018StaglianoVagagginiVergari.client.GUI.GUIStates.ToolCardStates;

import ingSw2018StaglianoVagagginiVergari.client.GUI.GameScene;
import ingSw2018StaglianoVagagginiVergari.client.GUI.SagradaGUI;

import java.util.ArrayList;

public class TaglierinaManualePhaseHandler extends Thread {

    private GameScene gameScene;

    public TaglierinaManualePhaseHandler(GameScene gameScene){
        this.gameScene = gameScene;
    }

    @Override
    public void run() {
        synchronized (SagradaGUI.getRequestHandler().getDataGameObserver()){
            while(SagradaGUI.getRequestHandler().getDataGameObserver().getTool12Phase2()==null){
                try {
                    SagradaGUI.getRequestHandler().getDataGameObserver().wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
        System.out.println(SagradaGUI.getRequestHandler().getDataGameObserver().getTool12Phase2());
        if (SagradaGUI.getRequestHandler().getDataGameObserver().getTool12Phase2()){
            SagradaGUI.getRequestHandler().getDataGameObserver().setTool12Phase2(null);
            ArrayList<Integer> parametriController = new ArrayList<>();
            parametriController.add(2);
            int i;
            for (i=0; i<3; i++){
                if (SagradaGUI.getRequestHandler().getDataGameObserver().cartaUtensile(i).equals("Taglierina Manuale")){
                    break;
                }
            }
            parametriController.add(i);
            SagradaGUI.getRequestHandler().genericGameRequest(parametriController, gameScene);
            gameScene.setState(new TaglierinaManualeState(gameScene, false, SagradaGUI.getRequestHandler().getDataGameObserver().getNumeroTracciatoRound()));
        }
    }
}

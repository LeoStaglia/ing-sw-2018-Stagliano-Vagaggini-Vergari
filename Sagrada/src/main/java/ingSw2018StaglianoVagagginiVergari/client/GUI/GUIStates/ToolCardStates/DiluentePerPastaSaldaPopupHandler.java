package ingSw2018StaglianoVagagginiVergari.client.GUI.GUIStates.ToolCardStates;

import ingSw2018StaglianoVagagginiVergari.client.GUI.DataGameObserver;
import ingSw2018StaglianoVagagginiVergari.client.GUI.GameScene;
import ingSw2018StaglianoVagagginiVergari.client.GUI.GraphicRenderer;
import ingSw2018StaglianoVagagginiVergari.client.GUI.Tool11Popup;
import javafx.application.Platform;

public class DiluentePerPastaSaldaPopupHandler extends Thread{

    private String Dado;

    private GameScene gameScene;

    private DataGameObserver dataGameObserver;

    public DiluentePerPastaSaldaPopupHandler(GameScene gameScene, String Dado, DataGameObserver dataGameObserver){
        this.gameScene=gameScene;
        this.Dado=Dado;
        this.dataGameObserver=dataGameObserver;
    }


    @Override
    public void run() {
        Tool11Popup popup = new Tool11Popup();
        popup.setDie(GraphicRenderer.renderDie(Dado));
        Platform.runLater(()->popup.display());
        synchronized (dataGameObserver) {
            while (popup.getValoreDado() == null) {
                try {
                    dataGameObserver.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
        if (dataGameObserver.isCurrentPlayer()) {
            DiluentePerPastaSaldaState state = new DiluentePerPastaSaldaState(gameScene, false);
            state.setValoreDado(popup.getValoreDado());
            gameScene.setState(state);
        }
    }
}

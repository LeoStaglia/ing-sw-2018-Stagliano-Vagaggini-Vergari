package ingSw2018StaglianoVagagginiVergari.client.GUI.GUIStates.ToolCardStates;

import ingSw2018StaglianoVagagginiVergari.client.GUI.GUIStates.GUIState;
import ingSw2018StaglianoVagagginiVergari.client.GUI.GameScene;

public class FactoryToolCardStates {

    private GameScene gameScene;

    public FactoryToolCardStates(GameScene gameScene){
        this.gameScene=gameScene;
    }

    public GUIState getToolCardState(String cardName){
        if (cardName.equals("Pennello Per Eglomise")){
            return new PennelloPerEglomiseState(gameScene);
        }else if (cardName.equals("Alesatore per lamina di rame")){
            return new AlesatoreRameState(gameScene);
        }else if (cardName.equals("Pinza Sgrossatrice")){
            return new PinzaSgrossatriceState(gameScene);
        }else if(cardName.equals("Lathekin")){
            return new LathekinState(gameScene);
        }else if(cardName.equals("Taglierina Circolare")){
            return new TaglierinaCircolareState(gameScene);
        }else if (cardName.equals("Pennello Per Pasta Salda")){
            return new PennelloPerPastaSaldaState(gameScene, true);
        }else if (cardName.equals("Diluente Per Pasta Salda")){
            return new DiluentePerPastaSaldaState(gameScene, true);
        }else if (cardName.equals("Tenaglia A Rotelle")){
            return new TenagliaARotelleState(gameScene);
        }else if (cardName.equals("Riga In Sughero")){
            return new RigaInSugheroState(gameScene);
        }else if(cardName.equals("Tampone Diamantato")) {
            return new TamponeDiamantatoState(gameScene);
        }else if(cardName.equals("Taglierina Manuale")){
            return new TaglierinaManualeState(gameScene, true, 0);
        }else{
            return null;
        }
    }

}

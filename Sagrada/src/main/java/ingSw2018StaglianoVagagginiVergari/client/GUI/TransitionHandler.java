package ingSw2018StaglianoVagagginiVergari.client.GUI;

import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.HashMap;

public class TransitionHandler {
    private static Scene welcomeWindow;
    private static Stage primaryStage;
    private static Scene newGameScene;
    private static Scene sceltaSchemaScene;
    private static Scene gameScene;
    private static Scene otherBoardsScene;

    public TransitionHandler(Stage primaryStage){
        this.primaryStage=primaryStage;
    }

    private static void goTo(Scene scene){
        primaryStage.setScene(scene);
    }
    public static void toNewGameScene(){
        goTo(newGameScene);
    }
    public static void toWelcomeWindow(){
        goTo(welcomeWindow);
    }
    public static void toOtherBoards(){
        goTo(otherBoardsScene);
    }

    public static void toPointsScene(HashMap<String, Integer> punteggi, String vincitore){
        PointsScene pointsScene = new PointsScene();
        pointsScene.renderPunteggiGiocatore(punteggi, vincitore);
        Scene scene = new Scene(pointsScene.getRoot());
        goTo(scene);
    }


    public static void setGameScene(GameScene gameScene){
        TransitionHandler.gameScene=new Scene(gameScene.getRoot());
    }
    public static void setWelcomeWindow(WelcomeWindow welcomeWindow) {
        TransitionHandler.welcomeWindow = new Scene(welcomeWindow.getRoot());
    }

    public static void setOtherBoardsScene(OtherBoards otherBoardsScene) {
        TransitionHandler.otherBoardsScene = new Scene(otherBoardsScene.getRoot());
    }

    public static void setNewGameScene(NewGameScene newGameScene) {
        TransitionHandler.newGameScene = new Scene(newGameScene.getRoot());
    }
    public static void setSceltaSchemaScene(SceltaSchema sceltaSchema){
        sceltaSchemaScene=new Scene(sceltaSchema.getRoot());
    }
    public static void toSceltaSchema(){
        goTo(sceltaSchemaScene);
    }

    public static void toGameScene(){
        goTo(gameScene);
    }

    public static void setPrimaryStage(Stage primaryStage) {
        TransitionHandler.primaryStage = primaryStage;
    }
}

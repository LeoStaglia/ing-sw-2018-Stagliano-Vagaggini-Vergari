package ingSw2018StaglianoVagagginiVergari.client.GUI;

import com.sun.org.apache.bcel.internal.generic.NEW;
import ingSw2018StaglianoVagagginiVergari.client.GUI.ServerRequests.RemoteRequestHandler;
import ingSw2018StaglianoVagagginiVergari.common.GameObserver;
import ingSw2018StaglianoVagagginiVergari.common.RemoteController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
//import sun.plugin2.ipc.windows.WindowsEvent;

import java.beans.EventHandler;
import java.io.IOException;
import java.rmi.NoSuchObjectException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public class SagradaGUI extends Application{

    private static RemoteRequestHandler requestHandler;


    @Override
    public void start(Stage primaryStage) throws Exception{

        requestHandler = new RemoteRequestHandler(new DataGameObserver());
        requestHandler.setRMI(true);
        requestHandler.setController();
        TransitionHandler.setPrimaryStage(primaryStage);
        OtherBoards otherBoards = new OtherBoards();
        TransitionHandler.setOtherBoardsScene(otherBoards);
        GameScene gameScene = new GameScene();
        TransitionHandler.setGameScene(gameScene);
        requestHandler.getDataGameObserver().setGameScene(gameScene);
        requestHandler.getDataGameObserver().setOtherBoards(otherBoards);
        WelcomeWindow welcomeWindow = new WelcomeWindow();
        try {
            Pane loadginRoot = FXMLLoader.load(getClass().getResource("/loadingScene.fxml"));
            Scene loadingScene = new Scene(loadginRoot);
            TransitionHandler.setLoadingScene(loadingScene);
        } catch (IOException e) {
            e.printStackTrace();
        }
        TransitionHandler.setLoginScene(new LoginView());
        TransitionHandler.setWelcomeWindow(welcomeWindow);
        TransitionHandler.setNewGameScene(new NewGameScene());
        TransitionHandler.toWelcomeWindow();
        primaryStage.setResizable(false);
        primaryStage.setOnCloseRequest(new javafx.event.EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                try {
                    UnicastRemoteObject.unexportObject(requestHandler.getDataGameObserver(), true);
                } catch (NoSuchObjectException e) {
                    e.printStackTrace();
                }
            }
        });
        primaryStage.show();
    }



    public static void main(String[] args) {
        launch(args);
    }

    public static RemoteRequestHandler getRequestHandler(){
        return requestHandler;
    }




}

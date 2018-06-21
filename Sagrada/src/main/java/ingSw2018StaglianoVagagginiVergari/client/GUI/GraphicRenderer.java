package ingSw2018StaglianoVagagginiVergari.client.GUI;

import javafx.event.EventHandler;
import javafx.scene.Node; //TODO levare dopo prove
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;

import java.util.ArrayList;

public class GraphicRenderer {
    public static void renderSchemeGrid(String[][] contents, GridPane grid){
        for (int i=0; i<contents.length; i++){
            for (int j=0; j<contents[i].length; j++){
                StackPane container = new StackPane();
                container.setStyle("-fx-border-color: black");
                if (isConstraint(contents[i][j])){
                    container.getChildren().add(renderConstraint(contents[i][j]));
                    grid.add(container, j, i);
                }
            }
        }


    }

    public static void renderPlayerGrid(String[][] contents, GridPane grid){
        grid.getChildren().clear();
        for (int i=0; i<contents.length; i++){
            for (int j=0; j<contents[i].length; j++){
                StackPane container = new StackPane();
                container.setStyle("-fx-border-color: black");
                if (isConstraint(contents[i][j])){
                    container.getChildren().add(renderConstraint(contents[i][j]));
                    grid.add(container, j, i);
                }else{
                    container.getChildren().add(renderDie(contents[i][j]));
                    grid.add(container, j, i);
                }
            }
        }
    }
    public static void renderRoundTrack(ArrayList<String> tracciatoDelRound, HBox roundTrack, GameScene gameScene){
        for (String dado:tracciatoDelRound){
            ImageView renderDado = renderDie(dado);
            StackPane container = new StackPane(renderDado);
            container.setScaleX(0.8);
            container.setScaleY(0.8);
            roundTrack.getChildren().add(container);
            container.setOnMouseEntered(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    container.setStyle("-fx-border-color: blue");
                }
            });
            container.setOnMouseExited(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    container.setStyle("-fx-border-color: none");
                }
            });
            container.setOnMouseClicked(gameScene::handleClick);
        }
        for (int i=tracciatoDelRound.size()+1; i<=10; i++){
            ImageView imageView = new ImageView(new Image("/RoundTrack/"+i+".png"));
            StackPane container = new StackPane(imageView);
            container.setScaleX(0.8);
            container.setScaleY(0.8);
            roundTrack.getChildren().add(container);
        }


    }

    public static void renderPlayerGrid(String[][] contents, GridPane grid, boolean playable, GameScene gameScene){

        grid.getChildren().clear();
        for (int i=0; i<contents.length; i++){
            for (int j=0; j<contents[i].length; j++){
                StackPane container = new StackPane();
                container.setStyle("-fx-border-color: black");
                container.setOnMouseEntered(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        container.setStyle("-fx-border-color: red");
                        container.toFront();
                    }
                });
                container.setOnMouseExited(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        container.setStyle("-fx-border-color: black");
                        container.toBack();
                    }
                });

                container.setOnMouseClicked(gameScene::handleClick);

                if (isConstraint(contents[i][j])){
                    container.getChildren().add(renderConstraint(contents[i][j]));
                    grid.add(container, j, i);
                }else{
                    container.getChildren().add(renderDie(contents[i][j]));
                    grid.add(container, j, i);
                }
                if (playable){
                    container.setOnMouseClicked(gameScene::handleClick);
                }
            }
        }

    }

    public static void renderRiserva(ArrayList<String> dadiRiserva, HBox riserva, GameScene gameScene){
        for (String dado:dadiRiserva){
            ImageView renderDado = renderDie(dado);
            StackPane container = new StackPane(renderDado);
            container.setScaleX(0.5);
            container.setScaleY(0.5);
            riserva.getChildren().add(container);
            container.setOnMouseEntered(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    container.setStyle("-fx-border-color: blue");
                }
            });
            container.setOnMouseExited(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    container.setStyle("-fx-border-color: none");
                }
            });
            container.setOnMouseClicked(gameScene::handleClick);
        }
    }

    private static ImageView renderConstraint(String constraint){
        return new ImageView(new Image("/Constraint/"+constraint+".png", 94.0, 95.0, true, true));
    }
    public static ImageView renderDie(String die){
        return new ImageView(new Image("/Dadi/"+die+".png", 94.0, 95.0, true, true));
    }

    private static boolean isConstraint(String gridElement){
        if (gridElement.equalsIgnoreCase("1") || gridElement.equalsIgnoreCase("2") || gridElement.equalsIgnoreCase("3") || gridElement.equalsIgnoreCase("4") || gridElement.equalsIgnoreCase("5") || gridElement.equalsIgnoreCase("6") || gridElement.equalsIgnoreCase("N") || gridElement.equalsIgnoreCase("rosso") || gridElement.equalsIgnoreCase("blu") || gridElement.equalsIgnoreCase("giallo") || gridElement.equalsIgnoreCase("verde") || gridElement.equalsIgnoreCase("viola")){
            return true;
        }
        return false;
    }

    public static void privateObjectiveRender(PrivateObjective privateObjective){
        privateObjective.getPrivateObjectiveImage().setImage(new Image("/ObiettiviPrivati/"+SagradaGUI.getRequestHandler().getDataGameObserver().getObiettivoPrivato().toLowerCase()+".png"));
    }


    public static void scale(GUIElement element, double scaleFactor){
        element.getRoot().setScaleX(scaleFactor);
        element.getRoot().setScaleY(scaleFactor);
    }
    public static void translate(GUIElement element, double translateFactor){
        element.getRoot().setScaleX(translateFactor);
        element.getRoot().setScaleY(translateFactor);
    }

}

package ingSw2018StaglianoVagagginiVergari.client.GUI;

import ingSw2018StaglianoVagagginiVergari.client.GUI.GUIStates.GUIState;
import ingSw2018StaglianoVagagginiVergari.client.GUI.GUIStates.LoadingState;
import ingSw2018StaglianoVagagginiVergari.client.GUI.ServerRequests.RemoteRequestHandler;
import ingSw2018StaglianoVagagginiVergari.client.IntegerNonBlockingScanner;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Stack;

public class GameScene {

    private Pane roundTrackArea;

    private HBox riserva;

    private StackPane privateObjectiveArea;

    private ArrayList<StackPane> publicObjectiveList;

    private ArrayList<StackPane> toolCardList;

    private ArrayList<Label> costiCarteUtensili;

    private Button boardsViewButton;

    private Label roundNumber;

    private Label turnNumber;

    private StackPane playerGridArea;

    private Pane root;

    private GUIState state;

    private PlayerGrid playerGrid;

    private RoundTrack roundTrack;

    private PrivateObjective privateObjective;

    private Button passaTurnButton;

    private Button otherBoards;

    private StackPane obiettivoPrivato;

    public GameScene() throws IOException {
        publicObjectiveList = new ArrayList<>();

        toolCardList = new ArrayList<>();
        costiCarteUtensili=new ArrayList<>();

        root = FXMLLoader.load(getClass().getResource("/gameScene.fxml"));
        roundTrackArea = (Pane)root.lookup("#roundTrackArea");
        riserva = (HBox) root.lookup("#riserva");

        obiettivoPrivato = (StackPane) root.lookup("#obiettivoPrivato");


        toolCardList.add((StackPane) root.lookup("#toolcard1"));

        toolCardList.add((StackPane) root.lookup("#toolcard2"));

        toolCardList.add((StackPane) root.lookup("#toolcard3"));


        publicObjectiveList.add((StackPane) root.lookup("#obiettivoPubblico1"));

        publicObjectiveList.add((StackPane) root.lookup("#obiettivoPubblico2"));

        publicObjectiveList.add((StackPane) root.lookup("#obiettivoPubblico3"));


        costiCarteUtensili.add((Label)root.lookup("#costoUtensile1"));

        costiCarteUtensili.add((Label)root.lookup("#costoUtensile2"));

        costiCarteUtensili.add((Label)root.lookup("#costoUtensile3"));


        otherBoards = (Button)root.lookup("#otherBoards");

        otherBoards.setOnAction((ActionEvent event)->TransitionHandler.toOtherBoards());

        passaTurnButton = (Button) root.lookup("#passTurn");

        passaTurnButton.setOnAction((ActionEvent event)->passaTurno(event));

        boardsViewButton = (Button)root.lookup("#boardsViewButton");

        roundNumber = (Label)root.lookup("#roundNumber");

        turnNumber = (Label)root.lookup("#turnNumber");

        playerGridArea = (StackPane)root.lookup("#playerGridArea");

        playerGrid = new PlayerGrid();
        playerGridArea.getChildren().add(playerGrid.getRoot());
        roundTrack=new RoundTrack();
        roundTrackArea.getChildren().add(roundTrack.getRoot());
        privateObjective= new PrivateObjective();


    }

    public GridPane getPlayerGrid() {
        return playerGrid.getPlayableGrid();
    }

    public Button getPassaTurnButton(){
        return passaTurnButton;
    }

    public HBox getRiserva() {
        return riserva;
    }

    public HBox getRoundTrack(){
        return roundTrack.getRoundTrack();
    }

    public void updateTurn(int turn){
        turnNumber.setText(Integer.toString(turn));
    }

    public void updateRound(int round){
        roundNumber.setText(Integer.toString(round));
    }

    public void passaTurno(ActionEvent event){

        if (SagradaGUI.getRequestHandler().getDataGameObserver().isCurrentPlayer()) {
            this.setState(new LoadingState());
            ArrayList<Integer> parametri = new ArrayList<>();
            parametri.add(3);
            SagradaGUI.getRequestHandler().genericGameRequest(parametri, this);
        }else{

        }
    }

    public ArrayList<StackPane> getToolCardList() {
        return toolCardList;
    }

    public void handleClick(MouseEvent event){
        state.handle(event);
    }

    public synchronized GUIState getState() {
        return state;
    }

    public synchronized void setState(GUIState state){
        this.state=state;
    }

    public void renderPlayerGridArea(String[][] contents, String username){
        playerGrid.setPlayerName(username);
        GraphicRenderer.renderPlayerGrid(contents, playerGrid.getPlayableGrid(), true, this);
    }
    public void renderDraftPoolArea(ArrayList<String> dadiRiserva){
        riserva.getChildren().clear();
        GraphicRenderer.renderRiserva(dadiRiserva, riserva, this);
    }
    public void renderRoundTrack(ArrayList<String> tracciatoDelRound){
        roundTrack.getRoundTrack().getChildren().clear();
        roundTrack.getRoundTrack().setSpacing(10);
        GraphicRenderer.renderRoundTrack(tracciatoDelRound, roundTrack.getRoundTrack(), this);
    }
    public void renderPublicObjectiveArea(ArrayList<String> carteObiettivoPubblico) {
        int i=0;
        for (StackPane obiettivoPubblico: publicObjectiveList){
            ImageView imageView = new ImageView(new Image("/ObiettiviPubblici/"+carteObiettivoPubblico.get(i).substring(0, carteObiettivoPubblico.get(i).indexOf('*'))+".png"));
            obiettivoPubblico.getChildren().add(imageView);
            i++;

        }

    }


    public void renderToolCardsArea(ArrayList<String> listaCarteUtensile){
        int i=0;
        for (StackPane toolcard:toolCardList){
            if (listaCarteUtensile.get(i).charAt(0)=='T'){
                costiCarteUtensili.get(i).setText("2");
            }else{
                costiCarteUtensili.get(i).setText("1");
            }
            ImageView imageView = new ImageView(new Image("/Utensili/"+listaCarteUtensile.get(i).substring(1, listaCarteUtensile.get(i).indexOf('*'))+".png"));
            toolcard.getChildren().add(imageView);
            toolcard.setOnMouseEntered(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    toolcard.setStyle("-fx-border-color: blue");
                }
            });
            toolcard.setOnMouseExited(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    toolcard.setStyle("-fx-border-color: none");
                }
            });

            toolcard.setOnMouseClicked((MouseEvent event)->handleClick(event));

            i++;
        }
    }

    public void renderObiettivoPrivato(String obiettivoPrivato){
        ImageView imageView = new ImageView(new Image("/ObiettiviPrivati/"+obiettivoPrivato+".png",230, 321, true, true));
        this.obiettivoPrivato.getChildren().add(imageView);
    }

    public void setCurrentPlayer(){
        passaTurnButton.setVisible(true);
        playerGrid.setCurrent();
    }
    public void setNotCurrentPlayer(){
        playerGrid.setNotCurrent();
        passaTurnButton.setVisible(false);
    }

    public Pane getRoot() {
        return root;
    }
}

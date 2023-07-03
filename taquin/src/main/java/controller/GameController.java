package controller;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import model.Board;
import model.Main;
import model.Position;

import java.util.Observable;
import java.util.Observer;

public class GameController extends Controller implements Observer {

    /**
     * Window parameters
     */
    private static final double gridWidth = 50;
    private static final double gridHeight = 50;
    private static final double windowWidth = 800;
    private static final double windowHeigth = 533;

    /**
     * Thread used to manage the board
     */
    private Thread t;

    /**
     * FXML object
     */
    @FXML
    private GridPane gridPane;

    /**
     * Default constructor
     */
    public GameController() {
        //Nothing
    }

    /**
     * FMXL initialization function, we're not using it here
     */
    @FXML
    private void initialize() {
        //Nothing
    }

    /**
     * Function displaying the grid
     */
    private void draw() {

        for(int i = 0; i < board.getLength(); i++) {
            for(int j = 0; j < board.getHeight(); j++) {

                gridPane.addColumn(j);
                StackPane stackPane;
                if (!board.isFree(i, j)) {
                    stackPane = getRectangle(board.getAgent(i, j).getAgentId() + "");
                } else {
                    stackPane = getRectangle("");
                }
                gridPane.add(stackPane, j, i);
            }
        }
    }

    /**
     * Initializing function called in the setMain() method
     */
    private void init(){

        gridPane.getChildren().clear();
        gridPane.getColumnConstraints().clear();
        gridPane.getRowConstraints().clear();
        gridPane.setPrefSize(gridWidth * board.getLength(), gridHeight * board.getHeight());

        gridPane.setLayoutX((windowWidth - gridPane.getPrefWidth()) / 2);
        gridPane.setLayoutY((windowHeigth - gridPane.getPrefHeight()) / 2);

        this.draw();
    }

    /**
     * Redefining setMain()
     * @param main
     * @param board
     */
    public void setMain(Main main, Board board){
        super.main = main;
        super.board = board;
        this.init();
        super.board.addObserver(this);
        t = new Thread(board);
        t.start();
    }

    /**
     * Returns a rectangular FXML object made of a string
     * @param content
     * @return
     */
    private StackPane getRectangle(String content){
        Label label = new Label(content);
        Rectangle rectangle = new Rectangle();
        rectangle.setWidth(gridWidth);
        rectangle.setHeight(gridHeight);
        rectangle.setStroke(Color.BLACK);
        rectangle.setFill(Color.WHITE);
        return new StackPane(rectangle, label);
    }

    /**
     * Updates the board when the agents are moving
     * @param observable
     * @param o
     */
    @Override
    public void update(Observable observable, Object o) {

        Position[] positions = (Position[]) (o);
        Position oldPos = new Position(positions[0]);
        Position newPos = new Position(positions[1]);
        Platform.runLater(() -> {
            this.updateDisplay(oldPos, newPos);
        });
    }

    /**
     * Updates the window
     * @param oldPos
     * @param newPos
     */
    private void updateDisplay(Position oldPos, Position newPos) {
        int id = board.getId(newPos);
        StackPane newStackPane = getRectangle(((id == -1) ? "" : id) + (board.checkCase(newPos) ? "+" : ""));
        gridPane.add(this.getRectangle(""), oldPos.getY(), oldPos.getX());
        gridPane.add(newStackPane, newPos.getY(), newPos.getX());
        if(board.finish()) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Taquin solved !");
            alert.setContentText("All agents have found their spots.");
            alert.show();
        }
    }

    /**
     * stop() method redefined
     */
    public void stop(){
        board.stop();
    }
}

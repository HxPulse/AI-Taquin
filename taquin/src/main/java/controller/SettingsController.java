package controller;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import model.Board;
import model.BoardFactory;
import model.Main;

public class SettingsController extends Controller {

    /**
     * FXML Objects
     */
    @FXML
    private Spinner<Integer> spinnerSize;

    @FXML
    private Spinner<Integer> spinnerAgents;

    @FXML
    private Button launch;

    /**
     * Default constructor
     */
    public SettingsController(){
        //Nothing
    }

    /**
     * Window initialization function
     * Spinners initialization
     */
    @FXML
    private void initialize(){

        // Factory value
        int sizeMax = 8;
        SpinnerValueFactory<Integer> valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(3, sizeMax, 5, 1);

        // Spinners initialization
        spinnerSize.setValueFactory(valueFactory);

        valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, sizeMax*sizeMax -1, 20, 1);

        spinnerAgents.setValueFactory(valueFactory);
    }

    /**
     * Game start
     */
    private void launchGame(){
        Platform.runLater(() -> {
            // Board creation
            int size = spinnerSize.getValue();
            int maxi = Math.min(spinnerAgents.getValue(), (size*size)-1);

            board = BoardFactory.getNewBoard(size, maxi);
            main.startGame(board);
        });
    }

    /**
     * setMain() method
     * @param main
     * @param board
     */
    @Override
    public void setMain(Main main, Board board) {
        super.main = main;
        launch.setOnMouseClicked(MouseEvent -> launchGame());
    }

    /**
     * stop() method
     */
    @Override
    public void stop() {
        //Nothing atm
    }
}

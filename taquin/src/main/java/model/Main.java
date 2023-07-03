package model;

import controller.Controller;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;


public class Main extends Application {

    /**
     * Primary Stage used
     */
    private Stage primaryStage;

    /**
     * BorderPane used
     */
    private BorderPane rootLayout;

    /**
     * Controller linked to the connection
     */
    private Controller controller;

    /**
     * Default constructor
     */
    public Main() {
        //Nothing here
    }

    @Override
    public void start(Stage primaryStage) throws Exception{

        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("Window settings");

        initRootLayout();
        launchSettings(rootLayout, this);
    }

    /**
     * Initializes the window using the BorderPane corresponding to our display
     */
    private void initRootLayout(){
        try {
            FXMLLoader loader = new FXMLLoader();

            loader.setLocation(Objects.requireNonNull(getClass().getClassLoader().getResource("root.fxml")));
            rootLayout = loader.load();

            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);
            primaryStage.setResizable(false);
            primaryStage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Launches the game
     * @param rootLayout
     * @param main
     * @param board
     */
    private void launchGame(BorderPane rootLayout, Main main, Board board){
        this.primaryStage.setTitle("Taquin game");
        displayView(rootLayout, main, "game.fxml", board);
    }

    /**
     * Launches the settings
     * @param rootLayout
     * @param main
     */
    private void launchSettings(BorderPane rootLayout, Main main) {
        displayView(rootLayout, main, "settings.fxml", null);
    }

    /**
     * Displays the view
     * @param rootLayout
     * @param main
     * @param root
     * @param board
     */
    private void displayView(BorderPane rootLayout, Main main, String root, Board board){
        try
        {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Objects.requireNonNull(getClass().getClassLoader().getResource(root)));
            AnchorPane content = loader.load();

            controller = loader.getController();
            controller.setMain(main, board);
            rootLayout.setCenter(content);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }


    /**
     * Main
     * @param args
     */
    public static void main(String[] args) {
        launch(args);
    }

    /**
     * Redefines the stop method called when clicking on the red cross
     */
    @Override
    public void stop(){
        controller.stop();
    }

    /**
     * View change
     * @param board
     */
    public void startGame(Board board) {
        launchGame(rootLayout, this, board);
    }
}

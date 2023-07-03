package controller;

import model.Board;
import model.Main;

public abstract class Controller {

    /**
     * Main used in the controllers
     */
    protected Main main;

    /**
     * Board used in the controller
     */
    protected Board board;

    /**
     * setMain() method
     * @param main
     * @param board
     */
    public abstract void setMain(Main main, Board board);

    /**
     * stop() method
     */
    public abstract void stop();
}

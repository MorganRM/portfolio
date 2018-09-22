/* *****************************************
 * CSCI205 - Software Engineering and Design
 * Spring 2017
 *
 * Name: Still going to decide on
 * Date: Apr 14, 2017
 * Time: 2:35:02 AM
 *
 * Project: csci205FinalProject
 * Package: csci205FinalProject
 * File: GameMain
 * Description:
 *
 * ****************************************
 */
package csci205FinalProject;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

/**
 *
 * @author Laura
 *
 *
 */
public class GameMain extends Application implements EventHandler<KeyEvent> {

    //height and width of original game scene
    public static double SCENE_HEIGHT = 360;
    public static double SCENE_WIDTH = 750;

    //initialize a gameWorld object here, eventually will be SuperOmar.io
    SuperOmario gameWorld;

    // This is the main gameLoop
    private AnimationTimer gameLoop;

    // This is a container for the background image
    ImageView backgroundImageView;

    Stage primaryStage;

    @Override
    public void init() throws Exception {
        super.init();
        gameWorld = new SuperOmario(60, "GameTest");

    }

    @Override
    public void start(Stage primaryStage) {

        this.primaryStage = primaryStage;

        gameWorld.initialize(primaryStage);

        // create scene
        Scene scene = gameWorld.getGameScene();

        // show the stage
        primaryStage.setScene(scene);
        primaryStage.show();

        // if we want a full screen game
//        primaryStage.setFullScreen(true);
        primaryStage.show();

        //add event handler to restart game
        gameWorld.getGameScene().addEventHandler(KeyEvent.KEY_PRESSED, this);
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void handle(KeyEvent key) {
        //if game is over and user presses S key
        if (gameWorld.isGameOver() && key.getCode() == KeyCode.S) {

            //remove current screens (whether win or lose)
            gameWorld.getSceneNodes().getChildren().remove(
                    gameWorld.getLoseScreen());
            gameWorld.getSceneNodes().getChildren().remove(
                    gameWorld.getWinScreen());
            gameWorld.getSceneNodes().getChildren().remove(
                    gameWorld.getRestartText());

            //and create a new game
            gameWorld = new SuperOmario(60, "Super Omario");
            start(primaryStage);

        }
    }

}

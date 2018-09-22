/* *****************************************
 * CSCI205 - Software Engineering and Design
 * Spring 2017
 *
 * Name: Still going to decide on
 * Date: Apr 15, 2017
 * Time: 2:22:50 AM
 *
 * Project: csci205FinalProject
 * Package: csci205FinalProject
 * File: SuperOmario
 * Description:
 *
 * ****************************************
 */
package csci205FinalProject;

import csci205FinalProject.Sprite.BackgroundManager;
import csci205FinalProject.Sprite.Coffee;
import csci205FinalProject.Sprite.Enemy;
import csci205FinalProject.Sprite.EnemyManager;
import csci205FinalProject.Sprite.ImageViewSprite;
import csci205FinalProject.Sprite.Life;
import csci205FinalProject.Sprite.Platform;
import csci205FinalProject.Sprite.Player;
import csci205FinalProject.Sprite.WinFlag;
import java.net.URL;
import javafx.animation.AnimationTimer;
import javafx.animation.PauseTransition;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.media.AudioClip;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 * SuperOmario - an instance of GameWorld that is a knock-off super mario game
 *
 * @author Laura
 */
public class SuperOmario extends GameWorld {

    //the player sprite
    private Player player;

    //javafx class to play .mp3 files
    private static MediaPlayer mediaPlayer;

    //number of lives left, starts at 3
    private int lives = 3;

    //Text displaying the number of lives left on the screen
    private Text livesDisplay;

    //true if in collision, false otherwise
    private boolean collision = false;

    //true if scrolling, false  otherwise
    private boolean scrolling = false;

    //number of coffees player has
    private int coffees;

    //Text to display
    private Text coffeesDisplay;

    //Class that cycles through animations for the player and the winFlag
    private ImageViewSprite anim;

    //javafx pane that all background nodes are attached to
    Pane backgroundLayer;

    //player image (with animations)
    ImageView imageViewMario;

    //image shown at start of game
    private ImageView startMenu;

    //image shown when player loses game
    private ImageView loseScreen;

    //image shown when player wins game
    private ImageView winScreen;

    //Text telling user how to restart
    private Text restartText;
    //Text telling user how to pause
    private Text pauseText;

    //class that manages arrays of platforms and coffees
    private BackgroundManager backgroundManager;
    //class that manages array of enemies
    private EnemyManager enemyManager;

    //true once player either loses or wins the game, false otherwise
    private boolean gameOver = false;

    //bucknell flag that player must get to in order to win the game
    private WinFlag flag;

    //true if player is at flag, false otherwise
    private boolean onFlag = false;

    /**
     * initialize gameworld with specified number of frames per second in the
     * loop and a specified title
     *
     * @param framesPerSec
     * @param title
     */
    public SuperOmario(int framesPerSec, String title) {
        super(framesPerSec, title);
        player = null;

    }

    /**
     * runs any time a key is pressed by the user
     *
     * @param key
     */
    @Override
    public void handle(KeyEvent key) {
        //if user presses right arrow key
        if (key.getCode() == KeyCode.RIGHT) {

            //start player animation
            this.anim.getImageView().setImage(new Image(
                    "/spritesheet2.png"));
            if (player.onGround()) {

                anim.start();

            }
            //if the player is beyond halfway through the game scene
            if (player.getPositionX() >= (this.getGameScene().getWidth() / 2)) {

                //make sure game scene won't scroll beyond background image
                //if the current game scene is at the end of the background width
                if (-(this.background.getPositionX()) >= (this.background.getNode().getFitWidth() - this.getGameScene().getWidth())) {

                    stopScrolling();
                    //80 is the normal speed of the player
                    player.setVelocityX(80);

                    //make sure player cant go past edge of image
                    //if the game scene is at the edge of the background image, and the player is at the edge of the game scene
                    if (player.getPositionX() >= this.getGameScene().getWidth()) {
                        //player can't move right
                        player.setVelocityX(0);
                    }
                }
                //if current game scene is not at the end of the background image
                else {
                    //scroll the background at the player's normal speed
                    scroll(-80);
                }
            }
            //if not scrolling, simply set the player's velocity
            else {
                //80 is the player's normal speed
                player.setVelocityX(80);
            }

            key.consume();

        }
        // if the user presses the left key
        else if (key.getCode() == KeyCode.LEFT) {

            //start player animation
            this.anim.getImageView().setImage(new Image(
                    "/spritesheet_flipped2.png"));
            if (player.onGround()) {

                anim.start();
            }
            //if the player is at the left quarter of the screen and going left
            if (player.getPositionX() <= (this.getGameScene().getWidth() / 4)) {
                //make sure game scene won't scroll beyond background image
                //if the game scene is at the left edge of the background image
                if (-(this.background.getPositionX()) <= 0) {
                    //don't scroll
                    stopScrolling();
                    //just set the players velocity to its normal speed, going left (so negative)
                    player.setVelocityX(-80);

                    //make sure player cant go past edge of image
                    //if player is at the left edge of the screen
                    if (player.getPositionX() <= 0.0) {
                        //player can't move left
                        player.setVelocityX(0);
                    }

                }
                //if current game scene is not at the left edge of the background image
                else {
                    //scroll the background at the player's normal speed
                    scroll(80);
                }
            }
            //if not scrolling, simply set the player's velocity
            else {
                //80 is the player's normal speed, negative means going to the left
                player.setVelocityX(-80);
            }
            key.consume();

        }
        //if the user presses the up arrow key
        //and the player is standing on a platform
        else if (key.getCode() == KeyCode.UP && player.onGround() == true) {
            //player jumps
            player.addVelocityY(-300);
            //and is no longer standing on a project
            player.setOnGround(false);
        }
        //if the user presses the 'P' key
        else if (key.getCode() == KeyCode.P) {
            //pause the game
            getGameLoop().stop();
        }
        //if the user presses the 'S' key, and the game is not over yet
        else if (key.getCode() == KeyCode.S && !this.gameOver) {
            //make sure that the start menu is not showing
            getSceneNodes().getChildren().remove(startMenu);
            //start or unpause the game
            getGameLoop().start();
        }

    }

    /**
     * initialize the game
     *
     * @param primaryStage
     */
    @Override
    public void initialize(final Stage primaryStage) {

        //Set the game's title
        primaryStage.setTitle(getTitle());

        //Create the scene
        setSceneNodes(new Group());
        setGameScene(new Scene(getSceneNodes(), GameMain.SCENE_WIDTH,
                               GameMain.SCENE_HEIGHT));
        primaryStage.setScene(getGameScene());

        //create the background layer
        backgroundLayer = new Pane();
        //add the background image to it
        backgroundLayer.getChildren().add(
                backgroundImageView);

        this.getSceneNodes().getChildren().add(backgroundLayer);
        backgroundLayer.toBack();

        //create the winning flag
        this.flag = new WinFlag(this);

        //create the player
        player = new Player(this);

        //set player image
        imageViewMario = (ImageView) player.getNode();
        this.anim = new ImageViewSprite(imageViewMario,
                                        new Image(
                                                "/spritesheet2.png"),
                                        7,
                                        1, 7,
                                        38, 60,
                                        7);

        //create the platforms and coffees
        backgroundManager = new BackgroundManager(this);

        //create the enemies
        enemyManager = new EnemyManager(this);

        //play music
        playMusic();

        //do bindings (game is somewhat resizeable)
        bindBackground();
        bindPlatforms();
        bindEnemies();
        bindPlayer();
        bindCoffees();
        bindLives();

        //add event handler for key press
        this.getGameScene().addEventHandler(KeyEvent.KEY_PRESSED, this);

        //the Animation timer runs the game, checks collisions and updates sprites after every frame
        final AnimationTimer gameLoop = getGameLoop();

        //display lives left
        livesDisplay();

        //display number coffees had
        coffeesDisplay();

        //add event handler for key release
        setKeyReleasedHandler();

        //image that comes up at beginning of game to introduce it, press S to start
        addStartMenu();

        //text on background to explain keys
        addKeyInstructions();

        //pause game until player starts it
        getGameLoop().stop();
    }

    /**
     * displays text that explains relevant key presses (Arrow keys to move, p
     * to pause, s to start)
     */
    public void addKeyInstructions() {
        pauseText = new Text((getGameScene().getWidth() - 110), 12,
                             "Arrow keys to move\nPress 'p' to pause\nPress 's' to start");
        pauseText.setFont(new Font("Arial", 12));
        pauseText.setFill(Color.WHITE);
        getSceneNodes().getChildren().add(pauseText);

        double propXPos = pauseText.getX() / getGameScene().getWidth();

        pauseText.xProperty().bind(
                getGameScene().widthProperty().multiply(propXPos));

        //create restart text, don't show yet
        restartText = new Text(
                (getGameScene().getWidth() / 2) - 90,
                (getGameScene().getHeight() - 50),
                "Press 'S' to Restart Game");

        restartText.setFont(new Font("Arial", 16));
        restartText.setFill(Color.WHITE);
    }

    /**
     * creates and displays the start menu that is shown at the beginning of the
     * game
     */
    public void addStartMenu() {
        startMenu = new ImageView(
                getClass().getResource("/startscreen.png").toExternalForm());
        startMenu.setX(0);
        startMenu.setY(0);

        startMenu.setFitWidth(this.getGameScene().getWidth());
        startMenu.setFitHeight(this.getGameScene().getHeight());
        startMenu.toFront();
//        startMenu.setX((getGameScene().getWidth() / 3));
//        startMenu.setY(
//                (getGameScene().getHeight() / 3));

        getSceneNodes().getChildren().add(startMenu);
    }

    /**
     * creates and displays the screen shown when a user loses the game
     */
    public void showLoseScreen() {
        loseScreen = new ImageView(getClass().getResource(
                "/losescreen.png").toExternalForm());
        loseScreen.setX(0);
        loseScreen.setY(0);

        loseScreen.setFitWidth(this.getGameScene().getWidth());
        loseScreen.setFitHeight(this.getGameScene().getHeight());

        loseScreen.toFront();

        getSceneNodes().getChildren().add(loseScreen);
        //also show restart text
        getSceneNodes().getChildren().add(restartText);

    }

    /**
     * creates and displays the screen shown when a user wins the game
     */
    public void showWinScreen() {
        winScreen = new ImageView(getClass().getResource(
                "/winScreen.png").toExternalForm());
        winScreen.setX(0);
        winScreen.setY(0);

        winScreen.setFitWidth(this.getGameScene().getWidth());
        winScreen.setFitHeight(this.getGameScene().getHeight());

        winScreen.toFront();
        playWinMusic();

        getSceneNodes().getChildren().add(winScreen);
        //also show restart text
        getSceneNodes().getChildren().remove(restartText);
        getSceneNodes().getChildren().add(restartText);

    }

    /**
     * moves background image to the left or the right so that the player can
     * get to the rest of the game
     *
     * @param velocity, velocity of scrolling
     */
    public void scroll(double velocity) {
        setScrollSpeed(velocity);
        //player doesnt move
        player.setVelocityX(0);
        //background does
        this.background.setVelocityX(getScrollSpeed());
        //move platforms accordingly
        for (Platform i : this.backgroundManager.getPlatforms()) {
            i.setVelocityX(getScrollSpeed());
        }
        //move coffees accordingly
        for (Coffee i : this.backgroundManager.getCoffees()) {
            i.setVelocityX(getScrollSpeed());
        }
        //move flag accordingly
        this.flag.setVelocityX(getScrollSpeed());

        //velocity of enemies during scrolling is handled within the enemy class
//        for (Enemy j : this.enemyManager.getEnemies()) {
        //if we weren't already scrolling, we now are
        if (!scrolling) {
//                j.addVelocityX(-80);
            scrolling = true;
        }
//        }
    }

    public void stopScrolling() {
        //stop moving the background
        this.background.setVelocityX(0);
        //stop moving other sprites
        this.flag.setVelocityX(0);
        for (Platform i : this.backgroundManager.getPlatforms()) {
            i.setVelocityX(0);
        }
        for (Coffee i : this.backgroundManager.getCoffees()) {
            i.setVelocityX(0);
        }
        //again, enemy velocity is handled within enemy class
//        for (Enemy j : this.enemyManager.getEnemies()) {

        //if we were scrolling, we now aren't
        if (scrolling) {
//                j.addVelocityX(80);
            scrolling = false;
        }
//
//        }
    }

    /**
     * creates and displays the text showing number of lives left
     */
    public void livesDisplay() {
        lives = 3;
        livesDisplay = new Text(0, 12, String.format("Lives: "));
        livesDisplay.setFont(new Font("Arial", 15));
        livesDisplay.setFill(Color.WHITE);
        getSceneNodes().getChildren().add(livesDisplay);
    }

    /**
     * creates and displays the text showing number of coffees the player has
     */
    public void coffeesDisplay() {
        coffees = 0;
        coffeesDisplay = new Text(0, 30,
                                  String.format("Coffees: %d",
                                                coffees));
        coffeesDisplay.setFont(new Font("Arial", 15));
        coffeesDisplay.setFill(Color.WHITE);
        getSceneNodes().getChildren().add(coffeesDisplay);

        double propXPos = coffeesDisplay.getX() / getGameScene().getWidth();

        coffeesDisplay.xProperty().bind(
                getGameScene().widthProperty().multiply(propXPos));
    }

    /**
     * binds player height and width to game screen height and width, keeping
     * original ratio of player height to width
     */
    public void bindPlayer() {
        player.getNode().fitHeightProperty().bind(
                getGameScene().heightProperty().multiply(player.getPropHeight()));
        player.getNode().fitWidthProperty().bind(
                getGameScene().heightProperty().multiply(
                        player.getPropHeight()).multiply(player.getPropRatio()));
    }

    /**
     * binds enemy height and width to game screen height and width
     */
    public void bindEnemies() {
        for (Enemy j : enemyManager.getEnemies()) {

            j.getNode().fitWidthProperty().bind(
                    getGameScene().widthProperty().multiply(j.getPropWidth()));
            //width is based on the height
            j.getNode().fitHeightProperty().bind(
                    getGameScene().heightProperty().multiply(j.getPropHeight()));
        }
    }

    /**
     * binds platform position and dimensions to the game screen dimensions
     */
    public void bindPlatforms() {
        for (Platform i : backgroundManager.getPlatforms()) {

            i.getNode().xProperty().bind(
                    getGameScene().widthProperty().multiply(
                            i.getPropXPos()));
            i.getNode().yProperty().bind(
                    getGameScene().heightProperty().multiply(
                            i.getPropYPos()));
            i.getNode().heightProperty().bind(
                    getGameScene().heightProperty().multiply(
                            i.getPropHeight()));
            i.getNode().widthProperty().bind(
                    getGameScene().widthProperty().multiply(i.getPropWidth()));

        }
    }

    /**
     * binds coffee position and dimensions to the game screen dimensions
     */
    public void bindCoffees() {
        for (Coffee i : backgroundManager.getCoffees()) {

            i.getNode().xProperty().bind(
                    getGameScene().widthProperty().multiply(
                            i.getPropXPos()));
            i.getNode().yProperty().bind(
                    getGameScene().heightProperty().multiply(
                            i.getPropCenter()));
            i.getNode().fitHeightProperty().bind(
                    getGameScene().heightProperty().multiply(
                            i.getPropHeight()));
            i.getNode().fitWidthProperty().bind(
                    getGameScene().heightProperty().multiply(
                            i.getPropHeight()).multiply(i.getPropRatio()));

        }
    }

    /**
     * binds life position and dimensions to the game screen dimensions
     */
    public void bindLives() {

        for (Life i : backgroundManager.getLives()) {

            i.getNode().xProperty().bind(
                    getGameScene().widthProperty().multiply(
                            i.getPropXPos()));
            i.getNode().yProperty().bind(
                    getGameScene().heightProperty().multiply(
                            i.getPropYPos()));
            i.getNode().fitHeightProperty().bind(
                    getGameScene().heightProperty().multiply(
                            i.getPropHeight()));
            i.getNode().fitWidthProperty().bind(
                    getGameScene().heightProperty().multiply(
                            i.getPropHeight()));

        }

    }

    /**
     * plays an mp3 file during the game
     */
    public void playMusic() {
        // music for start menu
        final URL resource = getClass().getResource("/omario.mp3");
        final Media media = new Media(resource.toString());
        if (this.mediaPlayer != null) {
            this.mediaPlayer.stop();
        }
        this.mediaPlayer = new MediaPlayer(media);
        mediaPlayer.play();
    }

    /**
     * Pauses the main music and plays the sound pertaining to "Game Over"
     */
    public void playGameOverMusic() {
        final URL resource = getClass().getResource("/you-have-failed.mp3");
        this.mediaPlayer.pause();
        final AudioClip gameOver = new AudioClip(resource.toString());
        PauseTransition pause = new PauseTransition(Duration.seconds(3));
        pause.setOnFinished(finish -> this.playMusic());
        gameOver.play();
        pause.play();
    }

    /**
     * This music will be played every time the player gets a coffee
     */
    public void playCoffeeMusic() {
        final URL resource = getClass().getResource("/coffee.wav");
        final AudioClip coffeeGained = new AudioClip(resource.toString());
        coffeeGained.play();
    }

    /**
     * This music will be played when the player wins the game
     */
    public void playWinMusic() {
        final URL resource = getClass().getResource("/winning_sound.mp3");
        this.mediaPlayer.pause();
        final AudioClip gameWon = new AudioClip(resource.toString());
        PauseTransition pause = new PauseTransition(Duration.seconds(7));
        pause.setOnFinished(finish -> this.playMusic());
        gameWon.play();
        pause.play();
    }

    /**
     * binds the background image dimensions to the game screen dimensions
     */
    public void bindBackground() {
        backgroundImageView.fitWidthProperty().bind(
                getGameScene().widthProperty().multiply(4));
        backgroundImageView.fitHeightProperty().bind(
                getGameScene().heightProperty());
    }

    /**
     * runs any time a key pressed by the user is released
     */
    private void setKeyReleasedHandler() {
        this.getGameScene().setOnKeyReleased(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent key) {
                //if user releases right arrow key
                if (key.getCode() == KeyCode.RIGHT) {
                    //stop player animation
                    anim.stop();
                    //stop player movement
                    player.setVelocityX(0);
                    //stop scrolling
                    stopScrolling();
                }
                //if user releases left arrow key
                else if (key.getCode() == KeyCode.LEFT) {
                    //stop player movement
                    player.setVelocityX(0);
                    //stop player animation
                    anim.stop();
                    //stop scrolling
                    stopScrolling();
                }

            }
        });
    }

    /**
     * updates every sprite's position, runs once every frame
     *
     * @param time, time passed since last frame
     */
    @Override
    public void updateSprites(double time) {
        if (background != null) {
            //update position with known velocity and time passed
            background.update(time);
        }
        if (player != null) {
//            player.isOnGround();
            //run the player's animation while it is moving right or left
            if (player.onGround() && (player.getVelocityX() != 0 | this.background.getVelocityX() != 0)) {
                anim.start();
            }
            //update position with known velocity and time passed
            player.update(time);
            //if player falls through floor of game
            //500 is there to make the reset take longer
            if (player.getPositionY() >= getGameScene().getHeight() + 500) {
                //reset player position to upper left corner of screen
                player.setPosition(0, 0);
                //player will accelerate from 0 to the platform, just as in the beginning of the game
                player.setVelocityY(0);
                //player loses a life
                if (lives > 0) {
                    //remove life image
                    int lastLife = backgroundManager.getLives().size() - 1;
                    backgroundManager.remove(
                            backgroundManager.getLives().get(lastLife));
                }
                //subtract a life
                lives -= 1;
            }

            //if the player has no more lives
            if (this.lives == 0) {
                //end the game
                getGameLoop().stop();
                this.gameOver = true;
                playGameOverMusic();
                //player has lost
                showLoseScreen();
            }
            if (!player.onGround()) {
                //stop player animation if the player is in the air
                anim.stop();
            }
            //if the player is more than halfway past the game scene width
            if (player.getPositionX() >= (this.getGameScene().getWidth() / 2)) {
                //make sure game scene won't scroll beyond background image
                //if the game scene is at the end of the background image
                if (-(this.background.getPositionX()) >= (this.background.getNode().getFitWidth() - this.getGameScene().getWidth())) {
                    //don't scroll
                    stopScrolling();

                    //make sure player cant go past edge of image
                    //if player at edge of image
                    if (player.getPositionX() >= this.getGameScene().getWidth()) {
                        //player stops moving
                        player.setVelocityX(0);
                    }
                }
                //if player is moving right, scroll the background
                //fixes a bug where background was not scrolling if the right arrow key was held down
                else if (!this.isScrolling() && player.getVelocityX() > 0) {
                    scroll(-80);
                }
            }
        }
        if (enemyManager != null) {
            for (int i = 0; i < enemyManager.getEnemies().size(); i++) {
                //update the velocity and position of all enemies
                this.enemyManager.getEnemies().get(i).update(time);
            }
        }
        if (backgroundManager != null) {
            //update velocity and position of all platforms
            for (Platform i : backgroundManager.getPlatforms()) {
                i.update(time);

            }
            //update velocity and position of all coffees
            for (Coffee j : backgroundManager.getCoffees()) {
                j.update(time);
            }
        }
        //update position of flag
        if (flag != null) {
            flag.update(time);
        }

    }

    /**
     * checks if the player has hit a sprite, and updates the game accordingly
     * runs every frame
     */
    @Override
    public void checkCollisons() {
        if (backgroundManager != null) {
            for (Platform i : backgroundManager.getPlatforms()) {
                //if player lands on top of platform
                if (i.intersects(player) && (player.getVelocityY() >= 0)) {
                    double playerFeet = player.getPositionY() + player.getHeight();
                    //if the player's feet are within the platform
                    if (playerFeet <= (i.getPositionY() + i.getHeight() + 1)) {
                        player.setVelocityY(0);
                        // Y position for both sprites is at the top left corner
                        // set player on top of platform
                        double newY = i.getPositionY() - player.getHeight();
                        player.setPosition(player.getPositionX(), newY);
                        player.setOnGround(true);
                    }

                }
            }
            boolean hitCoffee = false;
            int coffeeNum = 0;
            for (int i = 0; i < backgroundManager.getCoffees().size(); i++) {
                //if player hits a coffee
                if (backgroundManager.getCoffees().get(i).intersects(player)) {
                    hitCoffee = true;
                    //record index in array of coffees
                    coffeeNum = i;
                }
            }
            if (hitCoffee) {
                //remove that index from the array
                backgroundManager.remove(backgroundManager.getCoffees().get(
                        coffeeNum));
                //add to number of coffees player has
                coffees += 1;
                playCoffeeMusic();
                coffeesDisplay.setText(String.format("Coffees: %d",
                                                     coffees));
                if (coffees == 10) {
                    lives += 1;
                    int size = backgroundManager.getLives().size();
                    double newX = backgroundManager.getLives().get(size - 1).getPositionX() + 6;
                    backgroundManager.getLives().add(new Life(this, newX, 2));
                    bindLives();
                    coffees = 0;
                    coffeesDisplay.setText(String.format("Coffees: %d",
                                                         coffees));
                }
            }
        }
        if (enemyManager != null) {
            //true if player should flash, false otherwise
            boolean setOpaque = false;
            //true if player on top of enemy (i.e., player kills enemy) false otherwise
            boolean topCollision = false;
            int enemyNum = 0;
            //check if the player has hit any enemies
            for (int i = 0; i < enemyManager.getEnemies().size(); i++) {
                if (enemyManager.getEnemies().get(i).intersects(player)) {
                    setOpaque = true;
                    //if it killed the enemy
                    if (isTopCollision(i)) {
                        topCollision = true;
                        //store index of enemy killed
                        enemyNum = i;
                    }
                    //otherwise, player loses a life
                    //only lose life when collision begins, not continuously throughout collision
                    else if (!collision) {

                        //remove image of life
                        if (lives > 0) {
                            int lastLife = backgroundManager.getLives().size() - 1;
                            backgroundManager.remove(
                                    backgroundManager.getLives().get(lastLife));
                        }
                        //subtract from lives
                        lives -= 1;

                        collision = true;
                    }
                }
            }
            if (topCollision) {
                //if enemy killed
                //enemy dissapears
                enemyManager.remove(enemyManager.getEnemies().get(
                        enemyNum));
                //bounce off enemy
                if (player.getVelocityY() > 200) {
                    player.addVelocityY(-400);
                }
                else {
                    player.addVelocityY(-200);
                }
                player.setOnGround(false);
            }
            //player flashes when it loses a life
            else if (setOpaque) {
                player.getNode().setOpacity(0);
            }
            else {
                player.getNode().setOpacity(1);
                collision = false;
            }
        }

        if (this.flag != null) {
            //if player reaches flag
            if (this.flag.intersects(player)) {
                //if player wasn't previously on flag
                if (!onFlag) {
                    //play flag animation
                    this.flag.startAnimation();
                    onFlag = true;
                }
                //if player was on flag and the animation has finished playing
                else if (onFlag && this.flag.isAnimationFinished()) {
                    //stop the flag animation
                    this.flag.stopAnimation();
                    //player wins the game
                    gameOver = true;
                    getGameLoop().stop();
                    this.showWinScreen();
                }
            }
            else if (!this.flag.intersects(player)) {
                onFlag = false;
            }
        }
    }

    /**
     * checks if player on top of enemy, i.e., kills enemy
     *
     * @param i, index of enemy
     * @return true is player killed enemy, false otherwise
     */
    public boolean isTopCollision(int i) {
        double enemyXPos = enemyManager.getEnemies().get(i).getPositionX();
        //if the player is over the enemy
        //plus or minus 15 pixels to make landing on enemy earlier
        boolean xInRange = ((enemyXPos - 15) <= player.getPositionX()) && (player.getPositionX() < (enemyXPos + 15));
        //and the player is moving down (jumping on top of enemy)
        if ((xInRange) && (player.getVelocityY() > 0)) {
            return true;
        }
        else {
            return false;
        }
    }

    public BackgroundManager getBackgroundManager() {
        return backgroundManager;
    }

    public boolean isScrolling() {
        return scrolling;
    }

    public boolean isGameOver() {
        return gameOver;
    }

    public Text getRestartText() {
        return restartText;
    }

    public ImageView getLoseScreen() {
        return loseScreen;
    }

    public ImageView getWinScreen() {
        return winScreen;
    }

}

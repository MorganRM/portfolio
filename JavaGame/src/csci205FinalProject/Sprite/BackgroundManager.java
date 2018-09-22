/* *****************************************
* CSCI205 - Software Engineering and Design
* Spring 2017
*
* Name: Still going to decide on
* Date: Apr 20, 2017
* Time: 6:58:19 PM
*
* Project: csci205FinalProject
* Package: csci205FinalProject
* File: BackgroundManager
* Description:
*
* ****************************************
 */
package csci205FinalProject.Sprite;

import csci205FinalProject.GameWorld;
import java.util.ArrayList;

/**
 *
 * @author Morgan
 */
public class BackgroundManager {

    //current game
    GameWorld gameWorld;

    //all platforms
    ArrayList<Platform> platforms;
    Platform platform;

    //all coffees
    ArrayList<Coffee> coffees;

    //all lives
    ArrayList<Life> lives;

    /**
     * *
     *
     * @param g
     */
    public BackgroundManager(GameWorld g) {
        gameWorld = g;
        platforms = new ArrayList();
        //create and add platforms
        //constructor - GameWorld g, double width, double height, double x, double y
        platforms.add(new Platform(g, 300, 10, 0, 230));
        platforms.add(new Platform(g, 300, 10, 400, 180));
        platforms.add(new Platform(g, 100, 10, 200, 130));
        platforms.add(new Platform(g, 100, 10, 350, 130));
        platforms.add(new Platform(g, 100, 10, 500, 130));
        platforms.add(
                new Platform(g, 700, 10, (g.getGameScene().getWidth() - 40),
                             (g.getGameScene().getHeight() - 32)));
        platforms.add(new Platform(g, 300, 10, 900, 230));
        platforms.add(new Platform(g, 100, 10, 850, 130));
        platforms.add(new Platform(g, 100, 10, 1000, 130));
        platforms.add(new Platform(g, 100, 10, 1250, 100));
        platforms.add(new Platform(g, 300, 10, 1100, 180));
        platforms.add(new Platform(g, 100, 10, 1450, 130));
        platforms.add(new Platform(g, 300, 10, 1650, 180));
        platforms.add(new Platform(g, 100, 10, 2000, 130));
        platforms.add(new Platform(g, 100, 10, 2000, 270));
        platforms.add(new Platform(g, 200, 10, 2150, 200));
        platforms.add(new Platform(g, 100, 10, 2350, 130));
        //end of level platform
        //doesn't have enemy
        platforms.add(new Platform(g, 500, 10, 2400, 350));
//        platform.getNode().setOpacity(0);

        coffees = new ArrayList();
        //create and add coffees
        //Constructor - GameWorld g, double width, double height,
        coffees.add(new Coffee(g, 100, 210));
        coffees.add(new Coffee(g, 450, 160));
        coffees.add(new Coffee(g, 250, 110));
        coffees.add(new Coffee(g, 400, 110));
        coffees.add(new Coffee(g, 550, 110));

        coffees.add(new Coffee(g, 920, 210));
        coffees.add(new Coffee(g, 900, 110));
        coffees.add(new Coffee(g, 1075, 110));
        coffees.add(new Coffee(g, 1300, 80));
        coffees.add(new Coffee(g, 1350, 160));

        coffees.add(new Coffee(g, 1475, 110));
        coffees.add(new Coffee(g, 1750, 160));
        coffees.add(new Coffee(g, 2005, 110));
        coffees.add(new Coffee(g, 2025, 110));
        coffees.add(new Coffee(g, 2060, 250));
        coffees.add(new Coffee(g, 2080, 250));
        coffees.add(new Coffee(g, 2250, 180));
        coffees.add(new Coffee(g, 2420, 110));

        //create and add all lives
        lives = new ArrayList();
        lives.add(new Life(g, 20, 2));
        lives.add(new Life(g, 26, 2));
        lives.add(new Life(g, 32, 2));

    }

    /**
     *
     * @return
     */
    public ArrayList<Platform> getPlatforms() {
        return platforms;
    }

    public ArrayList<Coffee> getCoffees() {
        return coffees;
    }

    public ArrayList<Life> getLives() {
        return lives;
    }

    public void remove(Coffee coffee) {
        coffees.remove(coffee);
        gameWorld.getSceneNodes().getChildren().remove(coffee.getNode());
    }

    public void remove(Life life) {
        lives.remove(life);
        gameWorld.getSceneNodes().getChildren().remove(life.getNode());
    }
}

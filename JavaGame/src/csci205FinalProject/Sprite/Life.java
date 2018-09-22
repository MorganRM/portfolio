/* *****************************************
* CSCI205 - Software Engineering and Design
* Spring 2017
*
* Name: Still going to decide on
* Date: Apr 30, 2017
* Time: 5:09:49 PM
*
* Project: csci205FinalProject
* Package: csci205FinalProject.Sprite
* File: Life
* Description:
*
* ****************************************
 */
package csci205FinalProject.Sprite;

import csci205FinalProject.GameWorld;
import javafx.scene.image.ImageView;

/**
 *
 * @author Laura
 */
public class Life extends Sprite {

    //current game
    private GameWorld game;

    //life dimensions
    private double width;
    private double height;

    //dimensions proportional to game scene
    private double propWidth;
    private double propHeight;

    //position proportional to game scene
    private double propYPos;
    private double propXPos;

    public Life(GameWorld g, double x, double y) {
        super();
        game = g;
        //set dimensions
        width = 10;
        height = 10;

        //set image
        this.setImage(getClass().getResource("/life.png").toExternalForm());
        this.node = new ImageView(image);

        //set position
        setPosition(x, y);
        setDimensions(width, height);

        //base bindings off of ratio between initial image dimensions, and initial game dimensions
        propWidth = this.getWidth() / g.getGameScene().getWidth();
        propHeight = this.getHeight() / g.getGameScene().getHeight();

        propYPos = (this.getPositionY() / g.getGameScene().getHeight());
        propXPos = this.getPositionX() / g.getGameScene().getWidth();

        g.getSceneNodes().getChildren().add(node);
    }

    public double getWidth() {
        return width;
    }

    public double getHeight() {
        return height;
    }

    public double getPropWidth() {
        return propWidth;
    }

    public double getPropHeight() {
        return propHeight;
    }

    public double getPropYPos() {
        return propYPos;
    }

    public double getPropXPos() {
        return propXPos;
    }

    @Override
    public ImageView getNode() {
        return (ImageView) this.node;
    }

}

/* *****************************************
* CSCI205 - Software Engineering and Design
* Spring 2017
*
* Name: Still going to decide on
* Date: Apr 20, 2017
* Time: 10:38:51 PM
*
* Project: csci205FinalProject
* Package: csci205FinalProject.Sprite
* File: Platform
* Description:
*
* ****************************************
 */
package csci205FinalProject.Sprite;

import csci205FinalProject.GameWorld;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 *
 * @author Morgan
 */
public class Platform extends Sprite {

    //current game
    private GameWorld game;

    //position proportional to game scene
    private Double propYPos;
    private Double propXPos;

    //dimensions proportional to game scene
    Double propHeight;
    Double propWidth;

    public Platform(GameWorld g, double width, double height, double x, double y) {
        super();
        game = g;
        //set node
        this.node = new Rectangle(width, height, Color.DARKGREEN);

        //set position
        this.setPosition(x, y);

        //base bindings off of ratio between initial image dimensions, and initial game dimensions
        this.propYPos = y / g.getGameScene().getHeight();
        propXPos = x / g.getGameScene().getWidth();

        propHeight = height / g.getGameScene().getHeight();
        propWidth = width / g.getGameScene().getWidth();

//        propYPos = new ReadOnlyDoubleWrapper(y / g.getGameScene().getHeight()).getReadOnlyProperty();
        this.setDimensions(width, height);

        g.getSceneNodes().getChildren().add(node);
    }

    @Override
    public Rectangle getNode() {
        return (Rectangle) this.node;
    }

    public Double getPropYPos() {
        return propYPos;
    }

    public Double getPropXPos() {
        return propXPos;
    }

    public Double getPropHeight() {
        return propHeight;
    }

    public Double getPropWidth() {
        return propWidth;
    }

    @Override
    public void update(double time) {
        super.update(time);

        //    positionX = this.getNode().xProperty().getValue();
        //set y position to the y property of the node, which is bound to the game dimensions
        positionY = this.getNode().yProperty().getValue();

    }

}

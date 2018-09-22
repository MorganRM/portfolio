/* *****************************************
 * CSCI205 - Software Engineering and Design
 * Spring 2017
 *
 * Name: Still going to decide on
 * Date: Apr 27, 2017
 * Time: 6:48:08 PM
 *
 * Project: csci205FinalProject
 * Package: csci205FinalProject.Sprite
 * File: Coffee
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
public class Coffee extends Sprite {

    //current game
    private GameWorld game;

    //width of coffee
    private double width;
    //height of coffee
    private double height;

    //center y point of coffee
    private double center;

    //proportional width to game scene
    private double propWidth;
    //proportional height to game scene
    private double propHeight;
    //proportional ratio of image width to height
    private double propRatio;
    //proportional X position
    private double propXPos;
    //proportional center Y position
    private double propCenter;

    public Coffee(GameWorld g, double x, double y) {
        super();
        game = g;
        //set dimensions
        this.width = 15;
        this.height = 18;

        //set image
        this.setImage(getClass().getResource("/coffee.png").toExternalForm());
        this.node = new ImageView(image);

        //set position
        this.setPosition(x, y);

        this.setDimensions(width, height);
        //set velocity
        this.setVelocityY(20);

        center = y;

        //base bindings off of ratio between initial image dimensions, and initial game dimensions
        propWidth = this.getWidth() / g.getGameScene().getWidth();
        propHeight = this.getHeight() / g.getGameScene().getHeight();

        propRatio = (this.getNode().getImage().getWidth() / this.getNode().getImage().getHeight());

        propCenter = (this.getCenter() / g.getGameScene().getHeight());
        propXPos = this.getPositionX() / g.getGameScene().getWidth();

        g.getSceneNodes().getChildren().add(node);

    }

    @Override
    public ImageView getNode() {
        return (ImageView) this.node;
    }

    @Override
    public void update(double time) {
        //update position
        super.update(time);

        //update velocity
        updateVelocity();

        //update center
        center = this.getNode().yProperty().getValue();
    }

    public void updateVelocity() {
        //coffees hover around a center point between a set bottom and top edge
        boolean belowBottom = this.getPositionY() >= (this.center + 2);
        boolean aboveTop = this.getPositionY() <= (this.center - 2);

        if (this.getVelocityY() > 0 && belowBottom) {
            this.setVelocityY(-getVelocityY());
        }
        else if (this.getVelocityY() < 0 && aboveTop) {
            this.setVelocityY(-getVelocityY());
        }
    }

    public double getPropHeight() {
        return propHeight;
    }

    public double getPropWidth() {
        return propWidth;
    }

    public double getPropXPos() {
        return propXPos;
    }

    public double getPropRatio() {
        return propRatio;
    }

    public double getCenter() {
        return center;
    }

    public double getPropCenter() {
        return propCenter;
    }

}

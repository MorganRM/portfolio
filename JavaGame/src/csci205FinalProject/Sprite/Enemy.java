/* *****************************************
 * CSCI205 - Software Engineering and Design
 * Spring 2017
 *
 * Name: Still going to decide on
 * Date: Apr 24, 2017
 * Time: 4:43:05 PM
 *
 * Project: csci205FinalProject
 * Package: csci205FinalProject.Sprite
 * File: Enemy
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
public class Enemy extends Sprite {

    //current game
    private GameWorld game;

    //enemy width
    private double width;
    //enemy height
    private double height;

    //right edge of platform that enemy is on
    private double rightEdge;
    //left edge of platform
    private double leftEdge;

    //dimensions proportional to game scene
    private double propHeight;
    private double propWidth;

    //platform enemy is on
    private Platform platform;

    //either 80 or -80 depending on direction of scroll
    private static double scrollSpeed;
    //normal speed of enemy
    private static double BASE_VELOCITY = 50;

    public Enemy(GameWorld g, Platform p) {
        super();
        game = g;
        //set dimensions
        width = 20;
        height = 13;

        //set image
        this.setImage(getClass().getResource("/bluescreen.png").toExternalForm());
        this.node = new ImageView(image);

        //set on top of platform
        this.rightEdge = p.getPositionX() + p.getWidth();
        this.leftEdge = p.getPositionX();
        this.setPosition(leftEdge,
                         (p.getPositionY() - (this.getHeight())));

        this.setDimensions(width, height);
        this.setVelocityX(BASE_VELOCITY);

        //ratios for bindings based on initial game dimensions
        propHeight = this.getHeight() / game.getGameScene().getHeight();
        propWidth = this.getWidth() / game.getGameScene().getWidth();

        this.platform = p;

        scrollSpeed = game.getScrollSpeed();

        g.getSceneNodes().getChildren().add(node);

    }

    public void updateEdges() {

        this.rightEdge = this.platform.getPositionX() + this.platform.getWidth();
        this.leftEdge = this.platform.getPositionX();
    }

    //reverse direction at end of a platform
    public void updateVelocity() {
        boolean nearRightEdge = ((rightEdge - this.getWidth() - 1) <= this.getPositionX());
        boolean nearLeftEdge = (this.getPositionX() <= leftEdge + 1);

        //if we start scrolling
        if (game.isScrolling()) {
            scrollSpeed = game.getScrollSpeed();
            //if going left
            if (this.getVelocityX() < 0) {
                this.setVelocityX((scrollSpeed / 2) - BASE_VELOCITY);
                //if near left edge
                if (nearLeftEdge) {
                    this.setVelocityX((scrollSpeed / 2) + BASE_VELOCITY);
                }
            }

            //if going right
            else if (this.getVelocityX() > 0) {
                this.setVelocityX((scrollSpeed / 2) + BASE_VELOCITY);
                if (nearRightEdge) {
                    this.setVelocityX((scrollSpeed / 2) - BASE_VELOCITY);
                }
            }

        } //if we stop scrolling

        else if (!game.isScrolling()) {
            scrollSpeed = game.getScrollSpeed();
            //if going left
            if (this.getVelocityX() < 0) {
                this.setVelocityX(-(BASE_VELOCITY));
                //if near left edge
                if (nearLeftEdge) {
                    this.setVelocityX(BASE_VELOCITY);
                }
            }
            //if going right
            else if (this.getVelocityX() > 0) {
                this.setVelocityX(BASE_VELOCITY);
                //if near right edge
                if (nearRightEdge) {
                    this.setVelocityX(-BASE_VELOCITY);
                }
            }
        } //if near edges, reverse direction
        else if ((nearRightEdge
                  && (this.getVelocityX()
                      > 0)) || (nearLeftEdge && (this.getVelocityX() < 0))) {
            this.setVelocityX(-(this.getVelocityX()));
        }

    }

    public void setWidth(double width) {
        this.width = width;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public double getWidth() {
        return width;
    }

    public final double getHeight() {
        return height;
    }

    public Platform getPlatform() {
        return platform;
    }

    public double getPropHeight() {
        return propHeight;
    }

    public double getPropWidth() {
        return propWidth;
    }

    @Override
    public ImageView getNode() {
        return (ImageView) this.node;
    }

    @Override
    public void update(double time) {
        super.update(time);

        updateEdges();
        updateVelocity();

        super.update(time);

        //set y position on top of platform
        this.setPosition(this.getPositionX(),
                         (this.platform.getPositionY() - (this.getHeight())));

    }

    public double getRightEdge() {
        return rightEdge;
    }

    public double getLeftEdge() {
        return leftEdge;
    }

    public void setRightEdge(double rightEdge) {
        this.rightEdge = rightEdge;
    }

    public void setLeftEdge(double leftEdge) {
        this.leftEdge = leftEdge;
    }

    public GameWorld getGame() {
        return game;
    }

}

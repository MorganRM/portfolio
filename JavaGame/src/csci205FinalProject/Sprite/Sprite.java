/* *****************************************
 * CSCI205 - Software Engineering and Design
 * Spring 2017
 *
 * Name: Still going to decide on
 * Date: Apr 12, 2017
 * Time: 10:29:45 AM
 *
 * Project: csci205FinalProject
 * Package: csci205FinalProject
 * File: Sprite
 * Description:
 *
 * ****************************************
 */
package csci205FinalProject.Sprite;

import javafx.geometry.Bounds;
import javafx.scene.Node;
import javafx.scene.image.Image;

/**
 *
 * @author Morgan and Mateen
 * @see
 * https://github.com/tutsplus/Introduction-to-JavaFX-for-Game-Development/blob/master/Sprite.java
 */
public class Sprite {

    protected Image image;
//    protected ImageView node;
    protected double positionX;
    protected double positionY;
    protected double velocityX;
    protected double velocityY;
    protected double accelerationX;
    protected double accelerationY;
    protected double width;
    protected double height;

    protected Node node;

    public Sprite() {
        positionX = 0;
        positionY = 0;
        velocityX = 0;
        velocityY = 0;
        accelerationX = 0;
        accelerationY = 0;

    }

    public void setImage(Image i) {
        image = i;
        width = i.getWidth();
        height = i.getHeight();
    }

    public void setImage(String fileName) {
        Image i = new Image(fileName);
        setImage(i);
    }

    public void setPosition(double x, double y) {
        positionX = x;
        positionY = y;
        node.relocate(x, y);
    }

    public void setVelocity(double x, double y) {
        velocityX = x;
        velocityY = y;
    }

    public void addVelocityX(double x) {
        velocityX += x;
    }

    public void addVelocityY(double y) {
        velocityY += y;
    }

    public void setAccelerationX(double accelerationX) {
        this.accelerationX = accelerationX;

    }

    public void setAccelerationY(double accelerationY) {
        this.accelerationY = accelerationY;
    }

    public void changeVelocity(double time) {
        velocityX += (this.accelerationX * time);
        velocityY += (this.accelerationY * time);
    }

    /**
     * Takes in the current time and calculates what the new position should be
     * based on the current velocity
     *
     * @param time
     */
    public void update(double time) {

        this.changeVelocity(time);
        positionX += velocityX * time;
        positionY += velocityY * time;
        node.relocate(positionX, positionY);

    }

    /**
     *
     * @return a new Rectangle2D object
     */
    public Bounds getBoundary() {
//        return new Rectangle2D(positionX, positionY, width, height);

        return this.node.getBoundsInParent();
    }

    /**
     * A method to determine if the boundary of the current Sprite class
     * intersects with that of another Sprite object
     *
     * @param s is a Sprite object
     * @return a boolean that shows if intersection occurs.
     */
    public boolean intersects(Sprite s) {
        return s.getBoundary().intersects(this.getBoundary());

    }

    public boolean intersectsTop(Sprite s) {
        if ((s.positionY - s.height < this.positionY) && (s.positionY - s.height > this.positionY - this.height)) {
            return true;
        } else {
            return true;
        }
    }

    public boolean sideIntersects(Sprite s) {
//        return s.getBoundary().
        return false;
    }

    public String toString() {
        return "Current Position: (" + positionX + "," + positionY + ")" + "Current Velocity: (" + velocityX + "," + velocityY + ")";
    }

    public void setVelocityX(double velocityX) {
        this.velocityX = velocityX;
    }

    public void setVelocityY(double velocityY) {
        this.velocityY = velocityY;
    }

    public double getPositionX() {
        return positionX;
    }

    public double getPositionY() {
        return positionY;
    }

    public double getVelocityX() {
        return velocityX;
    }

    public double getVelocityY() {
        return velocityY;
    }

    public Image getImage() {
        return this.image;
    }

    public double getWidth() {
        return width;
    }

    public void setWidth(double w) {
        this.width = w;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double h) {
        this.height = h;
    }

    public void setDimensions(double w, double h) {
        this.width = w;
        this.height = h;
    }

    public Node getNode() {
        return node;
    }

}

/* *****************************************
* CSCI205 - Software Engineering and Design
* Spring 2017
*
* Name: Still going to decide on
* Date: Apr 30, 2017
* Time: 5:27:44 PM
*
* Project: csci205FinalProject
* Package: csci205FinalProject.Sprite
* File: WinFlag
* Description:
*
* ****************************************
 */
package csci205FinalProject.Sprite;

import csci205FinalProject.GameWorld;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 *
 * @author Morgan
 */
public class WinFlag extends Sprite {

    ImageViewSprite animation;

    public WinFlag(GameWorld g) {
        super();
        //set image
        this.node = new ImageView(
                getClass().getResource("/flagresting.png").toExternalForm());

        //set position at very end of game
        this.setPosition(2700, 270);

        //set animation
        this.animation = new ImageViewSprite(this.getNode(),
                                             new Image(
                                                     "/flagsheet.png"),
                                             5,
                                             1, 5,
                                             27, 70,
                                             5);
        g.getSceneNodes().getChildren().add(this.node);
    }

    @Override
    public ImageView getNode() {
        return (ImageView) this.node;
    }

    public void startAnimation() {
        this.animation.start();
    }

    public void stopAnimation() {
        this.animation.stop();
    }

    public boolean isAnimationFinished() {
        return this.animation.isFinished();
    }

}

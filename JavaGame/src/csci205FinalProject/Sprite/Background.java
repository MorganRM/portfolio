/* *****************************************
* CSCI205 - Software Engineering and Design
* Spring 2017
*
* Name: Still going to decide on
* Date: Apr 28, 2017
* Time: 1:25:03 AM
*
* Project: csci205FinalProject
* Package: csci205FinalProject.Sprite
* File: Background
* Description:
*
* ****************************************
 */
package csci205FinalProject.Sprite;

import csci205FinalProject.GameWorld;
import javafx.scene.image.ImageView;

/**
 *
 * @author Morgan
 */
public class Background extends Sprite {

    public Background(GameWorld g) {
        //we treat the background as a sprite
        super();
        //set the background image
        this.setImage(getClass().getResource(
                "/background1.png").toExternalForm());
        this.node = new ImageView(image);

        //set position of background at beginning of game Scene
        node.relocate(0, 0);
        width = this.getImage().getWidth();
        height = this.getImage().getHeight();
    }

    @Override
    public ImageView getNode() {
        return (ImageView) node;
    }

    public void update(double time) {
        super.update(time);
        node.relocate(positionX, positionY);

    }

}

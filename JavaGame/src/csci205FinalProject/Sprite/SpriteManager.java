/* *****************************************
 * CSCI205 - Software Engineering and Design
 * Spring 2017
 *
 * Name: Still going to decide on
 * Date: Apr 14, 2017
 * Time: 9:54:48 PM
 *
 * Project: csci205FinalProject
 * Package: csci205FinalProject.Sprite
 * File: SpriteManager
 * Description:
 *
 * ****************************************
 */
package csci205FinalProject.Sprite;

import java.util.ArrayList;

/**
 *
 * @author Mateen Qureshi
 */
public class SpriteManager {

    // number of Sprite objects to be used
    private int numOfSprites;
    // create an array of Sprite objects
    private ArrayList<Sprite> listOfSprites;
    // signifies the current Sprite object that will be processed
    private int currentSprite;

    /**
     * Instantiates a Sprite Animation.
     *
     * @param numOfSprites is the number of Sprite objects to be used
     */
    public SpriteManager(int numOfSprites) {
        this.numOfSprites = numOfSprites;
        this.listOfSprites = new ArrayList<>();
        for (int i = 0; i < this.numOfSprites; i++) {
            listOfSprites.add(new Sprite());
        }
        this.currentSprite = 0;
    }

    /**
     * Adds a Sprite object to the listOfSprites
     *
     * @param newSprite
     */
    public void addSprites(Sprite newSprite) {
        this.listOfSprites.add(newSprite);
        this.numOfSprites++;
    }

    /**
     * Updates the state of the animation by incrementing the current state and
     * keeping the current state within the bounds of the List
     */
    public void update() {
        if (currentSprite == this.numOfSprites) {
            // reset to the starting state
            this.currentSprite = 0;
        }
        else {
            // increment the counter for state/frame
            currentSprite += 1;
        }
    }

    public int getNumOfSprites() {
        return numOfSprites;
    }

    public ArrayList<Sprite> getListOfSprites() {
        return listOfSprites;
    }

    public int getCurrentSprite() {
        return currentSprite;
    }

    public void setCurrentSprite(int currentSprite) {
        this.currentSprite = currentSprite;
    }

}

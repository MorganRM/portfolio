/* *****************************************
 * CSCI205 - Software Engineering and Design
 * Spring 2017
 *
 * Name: Still going to decide on
 * Date: Apr 30, 2017
 * Time: 8:36:16 PM
 *
 * Project: csci205FinalProject
 * Package: csci205FinalProject.Sprite
 * File: SpriteManagerTest
 * Description:
 *
 * ****************************************
 */
package csci205FinalProject.Sprite;

import junit.framework.TestCase;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author Mateen Qureshi
 */
public class SpriteManagerTest extends TestCase {

    public static final double EPSILON = 1.0E-12;
    public static final int NUMBER_OF_SPRITES = 4;
    public SpriteManager instance;

    public SpriteManagerTest() {
    }

    @Before
    public void setUp() throws Exception {
        super.setUp();
        instance = new SpriteManager(NUMBER_OF_SPRITES);

    }

    @After
    public void tearDown() throws Exception {
        super.tearDown();
        instance = null;
    }

    /**
     * Test of addSprites method, of class SpriteManager.
     */
    @Test
    public void testAddSprites() {
        System.out.println("addSprites");
        Sprite newSprite = new Sprite();
        instance.addSprites(newSprite);
        int expNumOfSprites = NUMBER_OF_SPRITES + 1;
        int actualNumOfSprites = instance.getNumOfSprites();
        assertEquals(expNumOfSprites, actualNumOfSprites);

    }

    /**
     * Test of update method, of class SpriteManager.
     */
    @Test
    public void testUpdate() {
        System.out.println("update");
        instance.setCurrentSprite(NUMBER_OF_SPRITES);
        instance.update();
        int expCurrentSprite = 0;
        int actualCurrentSprite = instance.getCurrentSprite();
        assertEquals(expCurrentSprite, actualCurrentSprite);
        instance.setCurrentSprite(0);
        instance.update();
        expCurrentSprite = 1;
        actualCurrentSprite = instance.getCurrentSprite();
        assertEquals(expCurrentSprite, actualCurrentSprite);

    }

}

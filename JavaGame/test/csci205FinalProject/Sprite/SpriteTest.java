/* *****************************************
 * CSCI205 - Software Engineering and Design
 * Spring 2017
 *
 * Name: Still going to decide on
 * Date: Apr 30, 2017
 * Time: 2:38:41 AM
 *
 * Project: csci205FinalProject
 * Package: csci205FinalProject.Sprite
 * File: SpriteTest
 * Description:
 *
 * ****************************************
 */
package csci205FinalProject.Sprite;

import com.sun.javafx.geom.BaseBounds;
import com.sun.javafx.geom.transform.BaseTransform;
import com.sun.javafx.jmx.MXNodeAlgorithm;
import com.sun.javafx.jmx.MXNodeAlgorithmContext;
import com.sun.javafx.sg.prism.NGNode;
import com.sun.prism.Graphics;
import javafx.scene.Node;
import junit.framework.TestCase;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author Mateen Qureshi
 */
public class SpriteTest extends TestCase {

    static final double EPSILON = 1.0E-12;
    private Sprite instance;

    public SpriteTest() {
    }

    @Before
    @Override
    public void setUp() throws Exception {
        super.setUp();
        instance = new Sprite();
    }

    @After
    public void tearDown() throws Exception {
        super.tearDown();
        instance = null;
    }

    /**
     * Test of changeVelocity method, of class Sprite.
     */
    @Test
    public void testChangeVelocity() {
        System.out.println("changeVelocity");
        double time = 1.0;
        Sprite oldInstance = this.instance;
        instance.setAccelerationX(1);
        instance.changeVelocity(time);
        assertEquals(instance.getVelocityX(), 1, EPSILON);
        assertEquals(instance.getVelocityY(), 0, EPSILON);

    }

    /**
     * Test of update method, of class Sprite.
     */
    @Test
    public void testUpdate() {
        System.out.println("update");
        double time = 1.0;
        instance.node = new Node() {

            @Override
            protected NGNode impl_createPeer() {
                return new NGNode() {

                    @Override
                    protected void renderContent(Graphics g) {

                    }

                    @Override
                    protected boolean hasOverlappingContents() {
                        return false;
                    }
                };
            }

            @Override
            public BaseBounds impl_computeGeomBounds(BaseBounds bounds,
                                                     BaseTransform tx) {
                return bounds;
            }

            @Override
            protected boolean impl_computeContains(double localX, double localY) {
                return false;
            }

            @Override
            public Object impl_processMXNode(MXNodeAlgorithm alg,
                                             MXNodeAlgorithmContext ctx) {
                return new Object();
            }
        };
        Sprite oldInstance = this.instance;
        instance.setAccelerationX(1);
        instance.setAccelerationY(1);
        instance.update(time);
        assertEquals(instance.getPositionX(), 1, EPSILON);
        assertEquals(instance.getPositionY(), 1, EPSILON);

    }

    /**
     * Test of intersects method, of class Sprite.
     */
    @Test
    public void testIntersects() {
        System.out.println("intersects");
        Sprite s = new Sprite();
        s.node = new Node() {

            @Override
            protected NGNode impl_createPeer() {
                return new NGNode() {

                    @Override
                    protected void renderContent(Graphics g) {

                    }

                    @Override
                    protected boolean hasOverlappingContents() {
                        return false;
                    }
                };
            }

            @Override
            public BaseBounds impl_computeGeomBounds(BaseBounds bounds,
                                                     BaseTransform tx) {
                return bounds;
            }

            @Override
            protected boolean impl_computeContains(double localX, double localY) {
                return false;
            }

            @Override
            public Object impl_processMXNode(MXNodeAlgorithm alg,
                                             MXNodeAlgorithmContext ctx) {
                return new Object();
            }
        };
        instance.node = new Node() {

            @Override
            protected NGNode impl_createPeer() {
                return new NGNode() {

                    @Override
                    protected void renderContent(Graphics g) {

                    }

                    @Override
                    protected boolean hasOverlappingContents() {
                        return false;
                    }
                };
            }

            @Override
            public BaseBounds impl_computeGeomBounds(BaseBounds bounds,
                                                     BaseTransform tx) {
                return bounds;
            }

            @Override
            protected boolean impl_computeContains(double localX, double localY) {
                return false;
            }

            @Override
            public Object impl_processMXNode(MXNodeAlgorithm alg,
                                             MXNodeAlgorithmContext ctx) {
                return new Object();
            }
        };
        instance.setPosition(2, 2);
        boolean expResult = false;
        boolean result = instance.intersects(s);
        assertEquals(expResult, result);
        s.setPosition(1, 2);
        expResult = false;
        result = instance.intersects(s);
        assertEquals(expResult, result);
    }

    /**
     * Test of intersectsTop method, of class Sprite.
     */
    @Test
    public void testIntersectsTop() {
        System.out.println("intersectsTop");
        Sprite s = new Sprite();
        instance.node = new Node() {

            @Override
            protected NGNode impl_createPeer() {
                return new NGNode() {

                    @Override
                    protected void renderContent(Graphics g) {

                    }

                    @Override
                    protected boolean hasOverlappingContents() {
                        return false;
                    }
                };
            }

            @Override
            public BaseBounds impl_computeGeomBounds(BaseBounds bounds,
                                                     BaseTransform tx) {
                return bounds;
            }

            @Override
            protected boolean impl_computeContains(double localX, double localY) {
                return false;
            }

            @Override
            public Object impl_processMXNode(MXNodeAlgorithm alg,
                                             MXNodeAlgorithmContext ctx) {
                return new Object();
            }
        };
        s.node = new Node() {

            @Override
            protected NGNode impl_createPeer() {
                return new NGNode() {

                    @Override
                    protected void renderContent(Graphics g) {

                    }

                    @Override
                    protected boolean hasOverlappingContents() {
                        return false;
                    }
                };
            }

            @Override
            public BaseBounds impl_computeGeomBounds(BaseBounds bounds,
                                                     BaseTransform tx) {
                return bounds;
            }

            @Override
            protected boolean impl_computeContains(double localX, double localY) {
                return false;
            }

            @Override
            public Object impl_processMXNode(MXNodeAlgorithm alg,
                                             MXNodeAlgorithmContext ctx) {
                return new Object();
            }
        };
        instance.setPosition(2, 2);
        instance.setHeight(2);
        s.setPosition(2, 2);
        s.setHeight(2);
        boolean expResult = true;
        boolean result = instance.intersectsTop(s);
        assertEquals(expResult, result);
    }

}

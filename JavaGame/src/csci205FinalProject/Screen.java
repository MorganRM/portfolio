/* *****************************************
* CSCI205 - Software Engineering and Design
* Spring 2017
*
* Name: Still going to decide on
* Date: Apr 14, 2017
* Time: 2:18:42 AM
*
* Project: csci205FinalProject
* Package: csci205FinalProject
* File: Screen
* Description:
*
* ****************************************
 */
package csci205FinalProject;

import java.awt.*;
import javax.swing.JFrame;

/**
 *
 * @author Laura
 */
public class Screen {

    private GraphicsDevice device;

    public Screen() {
        GraphicsEnvironment env = GraphicsEnvironment.getLocalGraphicsEnvironment();
        device = env.getDefaultScreenDevice();
    }

    public void setFullScreen(DisplayMode mode, JFrame window) {
        window.setUndecorated(true);
        window.setResizable(true);
        device.setFullScreenWindow(window);
    }
}

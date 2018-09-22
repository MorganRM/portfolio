/* *****************************************
* CSCI205 - Software Engineering and Design
* Spring 2017
*
* Name: Stephen Haberle, Morgan Muller
* Date: Mar 31, 2017
* Time: 6:23:28 PM
*
* Project: csci205_hw
* Package: hw3.neuralnet.annmvc
* File: ANNModel
* Description: ANN Model class
*
* ****************************************
 */
package hw3.neuralnet.annmvc;

import hw3.neuralnet.ANN;
import java.io.FileNotFoundException;

/**
 * ANN Model Class
 *
 * @author StephenHaberle
 */
public class ANNModel {

    private ANN ann;

    /**
     * Constructor
     *
     * @throws FileNotFoundException if file is not found
     */
    public ANNModel() throws FileNotFoundException {
        this.ann = new ANN(2, 3, 1); //these are hard coded, will need to change that.

    }

    public void loadANN(ANN newAnn) {
        this.ann = newAnn;
    }

    public ANN getAnn() {
        return ann;
    }

}

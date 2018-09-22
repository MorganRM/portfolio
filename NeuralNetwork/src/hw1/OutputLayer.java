/* *****************************************
* CSCI205 - Software Engineering and Design
* Spring 2017
*
* Name: Morgan Muller, Stephen Haberle
* Date: Mar 5, 2017
* Time: 2:04:53 PM
*
* Project: csci205_hw
* Package: hw1
* File: OutputLayer
* Description:
*
* ****************************************
 */
package hw1;

import java.util.Arrays;

/**
 *
 * @author Morgan
 */
public class OutputLayer extends HiddenLayer {

    OutputLayer(int numNeurons) {
        super(numNeurons);
    }

    /**
     * Populate the layers with neurons
     */
    @Override
    public void populate() {
        int out = 1;
        for (int i = 0; i < numNeurons; i++) {
            this.neurons[i] = new Neuron();
            // these neurons will have 1 output each, since each one is a single
            //    output to the entire ANN
        }
    }

    /**
     * Sets a pointer to the previous layer.
     *
     * @param prev
     */
    public void setPrevLayer(Layer prev) {
        this.prevLayer = prev;
    }

//    public double[] getOuputs() {
//        return this.outputs;
//    }
    /**
     * Throws an exception if we try to access the next layer of the last layer
     *
     * @return
     * @throws Exception
     */
    /**
     * Gets the next layer.
     *
     * @return the next layer in the LL
     * @throws Exception
     */
    @Override
    public Layer getNextLayer() throws Exception {
        throw new Exception("No next Layer for type OutputLayer");
    }

    /**
     * Gets the previous layer.
     *
     * @return the previous layer in the LL
     */
    @Override
    public Layer getPrevLayer() {
        return this.prevLayer;
    }

    @Override
    public String toString() {
        String result = "";
        result += String.format("Output Layer: \n");
        result += String.format("    %s\n", Arrays.toString(this.neurons));
        return result;
    }

}

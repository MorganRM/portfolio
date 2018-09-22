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
package hw2;

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
     *
     * @author Morgan
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
     * @author Morgan
     * @param prev
     */
    public void setPrevLayer(Layer prev) {
        this.prevLayer = prev;
    }

    /**
     * Gets the next layer.
     *
     * @author Morgan
     * @return the next layer in the LL
     * @throws Exception when there is no next layer to get.
     */
    @Override
    public Layer getNextLayer() throws Exception {
        throw new Exception("No next Layer for type OutputLayer");
    }

    /**
     * Gets the previous layer.
     *
     * @author Morgan
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

    /**
     * Adds a neuron to the layer.
     *
     * @author Morgan
     * @param weights - array of weights of edges at the input
     */
    @Override
    public void addNeuron(double[] weights) {
        for (int i = 0; i < this.numNeurons; i++) {
            if (this.neurons[i] == null) {
                this.neurons[i] = new Neuron(weights);
                break;
            }
        }
    }

    @Override
    public String getLayerWeights() {
        String s = "";
        for (int i = 0; i < this.neurons.length; i++) {
            s += String.format("%s%n", this.neurons[i].getWeightsStr());
        }
        return s;
    }

    @Override
    public String getLayerName() {
        return "Output Layer";
    }

}

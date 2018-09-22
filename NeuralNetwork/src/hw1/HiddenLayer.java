/* *****************************************
* CSCI205 - Software Engineering and Design
* Spring 2017
*
* Name: Morgan Muller, Stephen Haberle
* Date: Mar 5, 2017
* Time: 2:04:44 PM
*
* Project: csci205_hw
* Package: hw1
* File: HiddenLayer
* Description:
*
* ****************************************
 */
package hw1;

/**
 * A class that represents a hidden layer. Done using a linked list.
 *
 * @author Morgan
 */
public class HiddenLayer extends Layer {

    protected Neuron neurons[];
    protected int numNeurons;
    private double[] storedInputs;
    private double[] storedOutputs;

    /**
     * Constructor
     *
     * @param numNeurons
     */
    HiddenLayer(int numNeurons) {
        this.prevLayer = null;
        this.nextLayer = null;
        this.numNeurons = numNeurons;
        this.neurons = new Neuron[numNeurons];

    }

    /**
     * Populates the layer with neurons.
     */
    @Override
    public void populate() {
        for (int i = 0; i < this.numNeurons; i++) {
            this.neurons[i] = new Neuron();
        }
    }

    /**
     * Sets a pointer to the previous layer.
     *
     * @param prevLayer
     */
    public void setPrevLayer(Layer prevLayer) {
        this.prevLayer = prevLayer;
    }

    /**
     * Sets a pointer to the next layer.
     *
     * @param nextLayer
     */
    public void setNextLayer(Layer nextLayer) {
        this.nextLayer = nextLayer;
    }

    @Override
    public Layer getNextLayer() throws Exception {
        return this.nextLayer;
    }

    @Override
    public Layer getPrevLayer() {
        return this.prevLayer;
    }

    /**
     * Returns the number of neurons in the layer. This is for use by other
     * classes, this class shouldn't need this
     *
     * @return number of neurons in the layer.
     */
    @Override
    public int getNumInputs() {
        return this.numNeurons;
    }

    /**
     * Returns the number of neurons in the layer.
     *
     * @return number of neurons in layer
     */
    @Override
    public int getNumOutputs() {
        return this.numNeurons;
    }

    @Override
    public String toString() {
        String result = "";
        result += String.format("Hidden Layer: \n");
        for (int i = 0; i < this.numNeurons; i++) {
            result += String.format("    %s\n", this.neurons[i]);
        }
        return result;
    }

    /**
     * Implements the forward feed algorithm. Passes inputs to the neurons.
     *
     * @param inputs
     * @return the outputs of the neurons in the layer.
     */
    public double[] forwardFeed(double[] inputs) {
        this.storedInputs = inputs;
        double[] outputs = new double[numNeurons];
        int i = 0;
        for (Neuron n : neurons) {
            outputs[i++] = n.fnet(inputs);
        }
        this.storedOutputs = outputs;
        return outputs;
    }

    /**
     * Implements the backpropigation algorithm. Reweights all of the neurons.
     *
     * @param error
     */
    public void backprop(double error) {
        int i = 0;
        for (Neuron n : neurons) {
            n.reweight(this.storedInputs, this.storedOutputs[i++],
                       error); //only one output per neuron, but each input has a weight that will change
        }
    }

}

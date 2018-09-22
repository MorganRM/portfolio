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
* Description: A class that implements a hidden layer.
*
* ****************************************
 */
package hw2;

/**
 * A class that represents a hidden layer. Done using a linked list.
 *
 * @author Morgan and Stephen
 */
public class HiddenLayer extends Layer {

    protected Neuron neurons[];
    protected int numNeurons;
    private double[] storedInputs;
    private double[] storedOutputs;

    /**
     * Constructor
     *
     * @author Morgan
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
     *
     * @author Morgan
     */
    @Override
    public void populate() {
        for (int i = 0; i < this.numNeurons; i++) {
            this.neurons[i] = new Neuron();
        }
    }

    /**
     * Adds a neuron to the layer.
     *
     * @param weights - array of the weights of the edges at the neuron input
     * @author Morgan
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

    /**
     * Sets a pointer to the previous layer.
     *
     * @param prevLayer - previous layer in the ANN
     * @author Morgan
     */
    public void setPrevLayer(Layer prevLayer) {
        this.prevLayer = prevLayer;
    }

    /**
     * Sets a pointer to the next layer.
     *
     * @param nextLayer - next layer in the ANN
     * @author Morgan
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
     * @author Morgan
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
     * @author Stephen
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
     * @author Stephen
     * @param error
     * @param isFirstLayer
     * @param nextLayer
     */
    public void backprop(double error, boolean isFirstLayer,
                         HiddenLayer nextLayer) {
        int i = 0;
        for (Neuron n : neurons) {
            n.reweight(this.storedInputs, this.storedOutputs[i],
                       error, isFirstLayer, i, nextLayer);
            i++;
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
        return "Hidden Layer";
    }

}

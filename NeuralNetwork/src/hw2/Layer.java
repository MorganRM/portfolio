/* *****************************************
* CSCI205 - Software Engineering and Design
* Spring 2017
*
* Name: Morgan Muller, Stephen Haberle
* Date: Mar 4, 2017
* Time: 9:23:53 PM
*
* Project: csci205_hw
* Package: hw1
* File: Layer
* Description:
*
* ****************************************
 */
package hw2;

/**
 * Most of these functions serve the purpose of allowing us to use the more
 * specific Layer classes (sort of) interchangeably in other classes
 *
 * @author Morgan
 */
public abstract class Layer implements java.io.Serializable {

    // a list that will store the neurons in that layer
    protected Layer nextLayer;
    protected Layer prevLayer;
    protected int numInputs;
    protected int numOutputs;

    /**
     * A placeholder to make other classes aware that any extension of the Layer
     * class will have a toString function.
     *
     * @author Morgan
     * @return
     */
    @Override
    public String toString() {
        return "";
    }

    /**
     * Adds a neuron to the layer.
     *
     * @param weights - double array of weights at the neuron input.
     * @throws Exception when the neuron cannot be added to the layer.
     */
    public void addNeuron(double[] weights) throws Exception {
        throw new Exception("Cannot add Neuron to this type of Layer");
    }

    /**
     * Placeholder to populate the layers with neurons.
     *
     * @author Morgan
     * @throws Exception when it cannot populate the layer.
     */
    public void populate() throws Exception {
        throw new Exception("Cannot populate this type of Layer");
    }

    public int getNumInputs() {
        return this.numInputs;
    }

    public int getNumOutputs() {
        return this.numOutputs;
    }

    public Layer getPrevLayer() throws Exception {
        return this.prevLayer;
    }

    public Layer getNextLayer() throws Exception {
        return this.nextLayer;
    }

    public String getLayerWeights() throws Exception {
        throw new Exception("nope");
    }

    public String getLayerName() {
        return "Layer";
    }

}

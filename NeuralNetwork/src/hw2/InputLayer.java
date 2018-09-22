/* *****************************************
* CSCI205 - Software Engineering and Design
* Spring 2017
*
* Name: Morgan Muller, Stephen Haberle
* Date: Mar 5, 2017
* Time: 2:04:32 PM
*
* Project: csci205_hw
* Package: hw1
* File: InputLayer
* Description:
*
* ****************************************
 */
package hw2;

import java.util.Arrays;

/**
 * Represents an input layer. These currently aren't being used in our
 * implementation. Done using a linked list.
 *
 * @author Morgan
 */
public final class InputLayer extends Layer {

    private Layer nextLayer;
    private int numInputs;
    private double inputs[];
    private int numOutputs;

    /**
     * Constructor
     *
     * @param numInputs
     */
    InputLayer(int numInputs) {
        this.nextLayer = null;
        this.numInputs = numInputs;
        this.inputs = new double[numInputs];
        this.numOutputs = numInputs;

    }

    /**
     * Sets a pointer to the next layer.
     *
     * @author Morgan
     * @param nextLayer - the next layer in the ANN
     *
     */
    public void setNextLayer(Layer nextLayer) {
        this.nextLayer = nextLayer;
    }

    /**
     * Sets the inputs for this layer
     *
     * @author Morgan
     * @param in - the input layer
     */
    public void setInputs(double in[]) {
        System.arraycopy(in, 0, this.inputs, 0, this.inputs.length);
    }

    /**
     * Returns the number of inputs.
     *
     * @return number of inputs
     */
    @Override
    public int getNumInputs() {
        return numInputs;
    }

    /**
     * Returns the number of outputs.
     *
     * @return number of outputs
     */
    @Override
    public int getNumOutputs() {
        return numOutputs;
    }

    public double[] getInputs() {
        return inputs;
    }

    /**
     * Returns the next later in the LL
     *
     * @return next layer in the linked list.
     */
    @Override
    public Layer getNextLayer() {
        return this.nextLayer;
    }

    /**
     * Returns the previous layer in the linked list.
     *
     * @return an exception because InputLayer should not have a prev
     * @throws Exception
     */
    @Override
    public Layer getPrevLayer() throws Exception {
        throw new Exception("No prev Layer for type InputLayer");
    }

    @Override
    public String toString() {
        String result = "";
        result += String.format("Input Layer: \n");
        result += String.format("    %s\n", Arrays.toString(this.inputs));

        return result;
    }

    @Override
    public String getLayerName() {
        return "Input Layer";
    }

}

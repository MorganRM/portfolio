/* *****************************************
* CSCI205 - Software Engineering and Design
* Spring 2017
*
* Name: NAMES of team members
* Date: Mar 4, 2017
* Time: 5:22:51 PM
*
* Project: csci205_hw
* Package: hw1
* File: Neuron
* Description:
*
* ****************************************
 */
package hw2;

import java.util.Arrays;

/**
 * A class representing the neurons in the ANN.
 *
 * @author Stephen Haberle
 * @author Morgan Muller
 */
public class Neuron implements java.io.Serializable {

    private static double learningParam = 0.3;
    private static double momentumConst = 0.5;
    // this is just here to make this parameter easy to find when we have to change it
    private final int BIAS_INPUT = 1;
    private double[] weights;
    private double biasWeight; //keep bias weight seperate to make passing inputs less messy
    private double[] prevDeltaWeights;
    private double prevBiasDelta = 0;

    /**
     * Constructor
     *
     * @author Stephen
     */
    public Neuron() {
        this.biasWeight = (0.5 - Math.random()) * 4.8; //creates random bias weight
    }

    /**
     * A constructor in which we already know the weights
     *
     * @author Stephen and Morgan
     * @param weights
     */
    public Neuron(double[] weights) {
        System.out.println(Arrays.toString(weights));
        this.weights = Arrays.copyOfRange(weights, 1, weights.length);
        this.biasWeight = weights[0]; ///the first weight in the array is the bias weight
        this.prevDeltaWeights = new double[weights.length]; //used for momentum calculations
    }

    /**
     * Sets the weight array of the neuron
     *
     * @author Morgan
     * @param weights
     */
    public void setWeights(double[] weights) {
        this.weights = Arrays.copyOfRange(weights, 1, weights.length);
        this.biasWeight = weights[0]; //first weight is for bias
    }

    /**
     * // * An activation function that uses the sigmoidal function
     *
     * @author Stephen
     * @param net // * @return double that between 0 and 1
     * @return output of activation function
     */
    public double activation(double net) {
        return 1 / (1 + Math.exp(-net));
    }

    /**
     * Calculates the net input function after it is put through the sigmoid
     * activation function. Sets random weights if neuron is empty.
     *
     * @author Stephen
     * @param inputs - a double array of the inputs to the neuron
     * @return the output of the neuron
     */
    public double fnet(double[] inputs) {
        if (weights == null) {
            this.biasWeight /= inputs.length;
            this.prevDeltaWeights = new double[inputs.length]; //stored for momentum
            this.weights = new double[inputs.length];
            for (int i = 0; i < inputs.length; i++) {
                this.weights[i] = (0.5 - Math.random()) * 4.8 / weights.length;
            }
        }
        return this.activation(net(inputs)); //added bias explicitly
    }

    /**
     * Sums the inputs time and weights.
     *
     * @author Stephen
     * @param inputs
     * @return
     */
    public double net(double[] inputs) {
        double sum = 0;
        for (int i = 0; i < inputs.length; i++) {
            sum += inputs[i] * weights[i];
        }
        return sum + this.biasWeight * this.BIAS_INPUT;
    }

    /**
     * Prints out the inputs of the neuron and the weights.
     *
     * @return string representation of the neuron.
     */
    @Override
    public String toString() {
        String result = String.format("     Weights: %s\n",
                                      Arrays.toString(this.weights));
        return result;
    }

    /**
     * Reweights the neurons for learning.
     *
     * @author Stephen
     * @param storedInputs - the inputs from the neurons in the previous layers
     * @param storedOutput - the output of neuron from the forward feed
     * @param error - the error for the iteration
     * @param isFirstLayer - a boolean that represents if the algorithm is
     * working in the first hidden layer
     * @param neuronNumInLayer - number of neurons in the layer
     * @param nextLayer - the next layer in the ANN (to the right).
     */
    public void reweight(double[] storedInputs, double storedOutput,
                         double error, boolean isFirstLayer,
                         int neuronNumInLayer, HiddenLayer nextLayer) {

        double deltak = storedOutput * (1 - storedOutput) * error;
        double deltaWeight;

        if (isFirstLayer) {
            double deltaj = storedOutput * (1 - storedOutput) * deltak * sumWeights(
                    nextLayer, neuronNumInLayer);
            for (int i = 0; i < this.weights.length; i++) {
                deltaWeight = Neuron.learningParam * storedInputs[i] * deltaj;
                weights[i] = weights[i] + deltaWeight + Neuron.momentumConst * prevDeltaWeights[i];
                this.prevDeltaWeights[i] = deltaWeight; //stores detla-weights for momentum calculation
            }
            //bias calculations
            deltaWeight = Neuron.learningParam * 1 * deltaj;
            this.biasWeight = this.biasWeight + deltaWeight + Neuron.momentumConst * this.prevBiasDelta;
            this.prevBiasDelta = deltaWeight; //stores for momentum calculation
        }
        else {
            for (int i = 0; i < this.weights.length; i++) {
                deltaWeight = Neuron.learningParam * storedInputs[i] * deltak;
                weights[i] = weights[i] + deltaWeight + Neuron.momentumConst * prevDeltaWeights[i];
                this.prevDeltaWeights[i] = deltaWeight;
            }
            //bias calclations
            deltaWeight = Neuron.learningParam * 1 * deltak;
            this.biasWeight = this.biasWeight + deltaWeight + Neuron.momentumConst * this.prevBiasDelta;
            this.prevBiasDelta = deltaWeight;
        }
    }

    /**
     * Sums the weights of the edges of the neurons output.
     *
     * @author Stephen
     * @param nextLayer - the layer the edge connects to
     * @param neuronNumInLayer - the sequential number the neuron is in the
     * layer.
     * @return the sum of the weights
     */
    private double sumWeights(HiddenLayer nextLayer, int neuronNumInLayer) {
        double sumWeights = 0;
        for (Neuron n : nextLayer.neurons) {
            sumWeights += n.weights[neuronNumInLayer];
        }
        return sumWeights;
    }

    public String getWeightsStr() {
        String s = this.biasWeight + ", " + Arrays.toString(this.weights);
        s = s.replace("[", "");
        s = s.replace("]", "");
        return s;
    }

}

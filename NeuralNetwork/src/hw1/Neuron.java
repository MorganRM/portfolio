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
package hw1;

import java.util.Arrays;

/**
 * A class representing the neurons in the ANN.
 *
 * @author Stephen Haberle
 * @author Morgan Muller
 */
public class Neuron {

    private static double learningParam = 0.3;
    // this is just here to make this parameter easy to find when we have to change it
    private final int BIAS_INPUT = 1;
    private double[] weights;
    private double biasWeight; //keep bias weight seperate to make passing inputs less messy

    /**
     * Constructor
     */
    public Neuron() {
        this.biasWeight = -0.5 + Math.random(); //creates random bias weight
    }

    /**
     * // * An activation function that uses the sigmoidal func
     *
     * @param net // * @return double that between 0 and 1
     * @return output of activation function
     */
    public double activation(double net) {
        return 1 / (1 + Math.exp(-net));
    }

    public double fnet(double[] inputs) {
        if (weights == null) {
            this.weights = new double[inputs.length];
            for (int i = 0; i < inputs.length; i++) {
                this.weights[i] = -0.5 + Math.random(); //TODO: change this to randomly generate -+2.4/m
            }
        }
        double sum = 0;
        for (int i = 0; i < inputs.length; i++) {
            sum += inputs[i] * weights[i];
        }
        return this.activation(sum + this.biasWeight * this.BIAS_INPUT); //added bias explicitly
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
     * Reweights the neurons using deltak equation from backpropigation alg.
     *
     * @param storedInputs
     * @param storedOutput
     * @param error
     */
    public void reweight(double[] storedInputs, double storedOutput,
                         double error) {
        double deltak = storedOutput * (1 - storedOutput) * error;
        double weightSum = 0; //for detla j
        for (int i = 0; i < this.weights.length; i++) {
            weightSum += weights[i] * deltak;
            weights[i] = weights[i] + Neuron.learningParam * storedInputs[i] * deltak;
        }
        //TODO: ask about bias and the difference between the layers
        //double deltaj =
        // this.biasWeight = Neuron.learningParam * -1 *
        //this is not the equation they gave
        this.biasWeight += Neuron.learningParam * deltak;
    }
}

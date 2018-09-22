/* *****************************************
* CSCI205 - Software Engineering and Design
* Spring 2017
*
* Name: Morgan Muller, Stephen Haberle
* Date: Mar 5, 2017
* Time: 3:49:45 AM
*
* Project: csci205_hw
* Package: hw1
* File: Main
* Description:
*
* ****************************************
 */
package hw1;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * A class that will hold the entire ANN - Layers, Neurons and all
 *
 * @author Morgan
 */
public class ANN {

    public static final double LEARNING_PARAM = 0.3;
    public InputLayer inputLayer;
    public OutputLayer outputLayer;
    public int numInputs;
    public int numOutputs;
    public int numHiddenLayers;
    public int numHLNodes;
    private double acceptableSSE;
    private double currentSSE = 0; //what the current SSN of our ANN is
    public LinkedList trainingIn;   /// TODO: Make these private and add getters
    public LinkedList trainingOut;

    /**
     * Constructor
     *
     * @param acceptableSSE
     */
    public ANN(double acceptableSSE) {
        this.acceptableSSE = acceptableSSE;
    }

    /**
     * Function that controls the learning mode. The iterating of the learning
     * will be done here.
     */
    public void learningMode() {
        double[] actual = {};
        int j = 0;
        do {
            this.currentSSE = 0;
            for (int i = 0; i < trainingIn.size(); i++) {
                actual = forwardFeed((double[]) trainingIn.get(i)); //forward feed for each input
                this.currentSSE += this.currentSSE(actual,
                                                   (double[]) trainingOut.get(i)) / trainingIn.size(); //doing an AVG SSE for whole ANN.
                double error = mean((double[]) trainingOut.get(i), actual); //E_k(i)

                this.backprop(error); //readjust weights -> learn
            }
        } while (this.acceptableSSE < this.currentSSE);
    }

    /**
     * Calculates current SSE.
     *
     * @param actual
     * @param expected
     * @return the SSE for the iteration
     */
    private double currentSSE(double[] actual, double[] expected) {
        assert actual.length == expected.length;
        double sum = 0;
        for (int i = 0; i < actual.length; i++) {
            sum += Math.pow((actual[i] - expected[i]), 2);
        }
        return 0.5 * sum;
    }

    /**
     * A function that implements the classification mode.
     *
     * @param inputs
     * @return the outputs of the network
     */
    public double[] classify(double[] inputs) {
        return forwardFeed(inputs);
    }

    /**
     * Calculates the error E_k for each iteration.
     *
     * @param expected
     * @param actual
     * @return the error
     */
    private double mean(double[] expected, double[] actual) { //not SSE
        assert expected.length == actual.length;
        double avg = 0;
        for (int i = 0; i < expected.length; i++) {
            avg += expected[i] - actual[i];
        }
        return avg / expected.length;
    }

    /**
     * Implements the backpropigation algorithm.
     *
     * @param error
     */
    private void backprop(double error) {
        Layer currentLayer = this.inputLayer.getNextLayer();
        while (currentLayer instanceof HiddenLayer) {
            ((HiddenLayer) currentLayer).backprop(error);
            try {
                currentLayer = currentLayer.getNextLayer();
            } catch (Exception ex) {
                break;
            }
        }
    }

    /**
     * Implements the forward feed algorithm.
     *
     * @param inputs
     * @return
     */
    private double[] forwardFeed(double[] inputs) {
        Layer currentLayer = this.inputLayer.getNextLayer();
        double[] outputs = inputs;
        while (currentLayer instanceof HiddenLayer) {
            outputs = ((HiddenLayer) currentLayer).forwardFeed(outputs);
            try {
                currentLayer = currentLayer.getNextLayer();
            } catch (Exception ex) {
                break;
            }

        }

        return outputs;
    }

    /**
     * Initializes a double linked list of different types of layers to make up
     * an ANN
     *
     * @param numInputs - the number of input nodes
     * @param numOutputs - the number of output nodes
     * @param numHiddenLayers - the number of hidden layers
     * @param numHLNodes - the number of nodes in each hidden layer
     */
    public void createLayers(int numInputs, int numOutputs, int numHiddenLayers,
                             int numHLNodes) {
        this.numInputs = numInputs;
        this.numOutputs = numOutputs;
        this.numHiddenLayers = numHiddenLayers;
        this.numHLNodes = numHLNodes;

        InputLayer in = new InputLayer(numInputs);
        OutputLayer out = new OutputLayer(numOutputs);
        if (numHiddenLayers == 0) {
            out.setPrevLayer(in);
            in.setNextLayer(out);
        }
        else if (numHiddenLayers == 1) {
            HiddenLayer HL = new HiddenLayer(numHLNodes);
            in.setNextLayer(HL);
            HL.setNextLayer(out);
            HL.setPrevLayer(in);
            out.setPrevLayer(HL);
        }
        else {
            HiddenLayer HL;
            HiddenLayer HLprev = null;
            for (int i = 0; i < numHiddenLayers; i++) {
                HL = new HiddenLayer(numHLNodes);
                if (i == 0) {
                    in.setNextLayer(HL);
                    HL.setPrevLayer(in);
                }
                else if (i == (numHiddenLayers - 1)) {
                    out.setPrevLayer(HL);
                    HL.setNextLayer(out);
                    HL.setPrevLayer(HLprev);
                    HLprev.setNextLayer(HL);            // This warning isn't a problem
                }
                else {
                    HLprev.setNextLayer(HL);            // Neither is this one
                    HL.setPrevLayer(HLprev);
                }
                HLprev = HL;
            }
        }
        this.outputLayer = out;
        this.inputLayer = in;

    }

    /**
     * Fills all Hidden Layers and Output Layer of the ANN with randomized
     * Neurons
     *
     * @throws Exception
     */
    public void populateANN() throws Exception {
        Layer L = this.inputLayer.getNextLayer();
        for (int i = 0; i < this.numHiddenLayers; i++) {
            L.populate();
            L = L.getNextLayer();
        }
        L.populate();
    }

    /**
     * Prints all each Layer of the ANN
     *
     */
    public void printANN() throws Exception {
        InputLayer L = this.inputLayer;
        System.out.println(L.toString());
        if (L.getNextLayer() instanceof HiddenLayer) {
            HiddenLayer H = (HiddenLayer) L.getNextLayer();
            System.out.println(H.toString());
            for (int i = 1; i < this.numHiddenLayers; i++) {
                H = (HiddenLayer) H.getNextLayer();
                System.out.println(H.toString());
            }
            OutputLayer O = (OutputLayer) H.getNextLayer();
            System.out.println(O.toString());
        }
        else {
            OutputLayer O = (OutputLayer) L.getNextLayer();
            System.out.println(O.toString());
        }
    }

    /**
     * Parse a training file and imports the data to the ANN.
     *
     * @param numInNeurons
     * @param numHL
     * @param numNeurons
     * @param numOutNeurons
     * @param fName
     * @throws FileNotFoundException
     * @throws IOException
     */
    public void importTrainingFile(int numInNeurons, int numHL, int numNeurons,
                                   int numOutNeurons, String fName) throws FileNotFoundException, IOException { //

        String input = new String(Files.readAllBytes(Paths.get(fName)));

        Pattern p = Pattern.compile("(\\d|.)++,?");
        Matcher m = p.matcher(input);

        LinkedList in = new LinkedList(); // I'm using linked lists here because they were
        LinkedList out = new LinkedList();  /// the only kind of list i could get working
        /// the functionality is similar
        while (m.find()) {
            //System.out.println("Found " + m.group());
            List<String> items = Arrays.asList(m.group().split(","));
            //System.out.println(items);
            double[] inArray = new double[numInNeurons];
            double[] outArray = new double[numOutNeurons];
            int j = 0;
            for (int i = 0; i < (numInNeurons); i++) {
                inArray[i] = Double.parseDouble(items.get(j));
                j++;
            }
            for (int i = 0; i < (numOutNeurons); i++) {
                outArray[i] = Double.parseDouble(items.get(j));
                j++;
            }

            in.add(inArray);
            out.add(outArray);

//                System.out.println("inArray = " + Arrays.toString(inArray));
//                System.out.println("outArray = " + Arrays.toString(outArray));
        }

        this.trainingIn = in;
        this.trainingOut = out;

    }

    /**
     * Returns the input layer.
     *
     * @return
     */
    public InputLayer getInputLayer() {
        return inputLayer;
    }

    /**
     * Returns the output layer.
     *
     * @return
     */
    public OutputLayer getOutputLayer() {
        return outputLayer;
    }

    /**
     * Adjusts the weights of the neurons based on the classification file.
     *
     * @param f
     * @throws FileNotFoundException
     */
    public void adjustWeights(File f) throws FileNotFoundException {
        double[][] layerWeights = parseClassInput(f);
//        System.out.println(Arrays.toString(layerWeights));

    }

    /**
     * Parses a classification input file.
     *
     * @param f
     * @return a 2d-array containing an array of weights for each layer.
     * @throws FileNotFoundException
     */
    public double[][] parseClassInput(File f) throws FileNotFoundException {

        Scanner s = new Scanner(f);
        String line;
        int numLines = findNumLines(f);
        System.out.println("numLines");
        double[][] out = new double[numLines][];
        int i = 0;
        while (s.hasNextLine()) {
            line = s.nextLine();
            String[] splitLine = line.split(",");
            double[] a = convertStringtoDouble(splitLine);
            out[i] = a;
            i++;
        }
        System.out.println(Arrays.toString(out));
        return out;
    }

    /**
     * Finds the number of lines in a file.
     *
     * @param f
     * @return number of lines
     * @throws FileNotFoundException
     */
    private int findNumLines(File f) throws FileNotFoundException {
        Scanner s = new Scanner(f);
        int i = 0;
        System.out.println("a");
        while (s.hasNextLine()) {
            System.out.println("b");
            i++;
        }
        return i;
    }

    /**
     * Converts a string array to a double array.
     *
     * @param a
     * @return double array representation of the string array.
     */
    private double[] convertStringtoDouble(String[] a) {
        double[] o = new double[a.length];
        for (int i = 0; i < a.length; i++) {
            o[i] = Double.parseDouble(a[i]);
        }
        return o;
    }

}

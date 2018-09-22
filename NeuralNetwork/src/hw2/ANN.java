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
package hw2;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Scanner;
import java.util.regex.Pattern;

/**
 * A class that will hold the entire ANN - Layers, Neurons and all
 *
 * @author Morgan and Stephen
 */
public class ANN implements java.io.Serializable {

    private static final double LEARNING_PARAM = 0.3;
    private InputLayer inputLayer;
    private OutputLayer outputLayer;
    private int numInputs;
    private int numOutputs;
    private int numHiddenLayers;
    private int numHLNodes;
    private double acceptableSSE;
    private double currentSSE = 0; //what the current SSN of our ANN is
    private LinkedList trainingIn;   /// TODO: Make these private and add getters
    private LinkedList trainingOut;
    private LinkedList<Layer> layers;      //// this is how we should have been managing the layers from the beginning tbh :/

    /**
     * Constructor
     *
     * @param acceptableSSE - an accept SSE to the user.
     */
    public ANN(double acceptableSSE) {
        this.acceptableSSE = acceptableSSE;
        this.layers = new LinkedList();
    }

    /**
     * Function that controls the learning mode. The iterating of the learning
     * will be done here.
     *
     * @author Stephen
     */
    public void learningMode() throws UnsupportedEncodingException, Exception {
        double[] actual = {};
        do {
//            System.out.println("Current SSE: " + currentSSE);
            this.currentSSE = 0;
            for (int i = 0; i < trainingIn.size(); i++) {
                this.addToClassFile(i, (double[]) trainingIn.get(i));
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
     * @author Stephen
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
     * @author Stephen
     * @return the outputs of the network
     */
    public double[] classify(double[] inputs) {
        return forwardFeed(inputs);
    }

    /**
     * Calculates the error E_k for each iteration.
     *
     * @author Stephen
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
     * @author Stephen
     * @param error - the error on the last iteration
     */
    private void backprop(double error) {

        boolean isFirstLayer;
        Layer currentLayer;
        Layer prevLayer;
        Layer nextLayer;
        for (int i = this.layers.size() - 1; i > 0; i--) { //never want to get 0 becasue input layer uselss
            currentLayer = this.layers.get(i);
            prevLayer = this.layers.get(i - 1);

            if (i == (this.layers.size() - 1)) { //Ensures we do not get index out of bounds on last index
                nextLayer = currentLayer;
            }
            else {
                nextLayer = this.layers.get(i + 1);
            }

            isFirstLayer = prevLayer instanceof InputLayer;
            ((HiddenLayer) currentLayer).backprop(error, isFirstLayer,
                                                  (HiddenLayer) nextLayer);

        }
    }

    /**
     * Implements the forward feed algorithm.
     *
     * @author Stephen
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
     * @author Morgan
     * @param numInputs - the number of input nodes
     * @param numOutputs - the number of output nodes
     * @param numHiddenLayers - the number of hidden layers
     * @param numHLNodes - the number of nodes in each hidden layer
     *
     */
    public void createLayers(int numInputs, int numOutputs, int numHiddenLayers,
                             int numHLNodes) {
        this.numInputs = numInputs;
        this.numOutputs = numOutputs;
        this.numHiddenLayers = numHiddenLayers;
        this.numHLNodes = numHLNodes;

        InputLayer in = new InputLayer(numInputs);
        layers.add(in);
        OutputLayer out = new OutputLayer(numOutputs);
        if (numHiddenLayers == 0) {
            out.setPrevLayer(in);
            in.setNextLayer(out);
        }
        else if (numHiddenLayers == 1) {
            HiddenLayer HL = new HiddenLayer(numHLNodes);
            layers.add(HL);
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
                layers.add(HL);
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
        layers.add(out);
        this.outputLayer = out;
        this.inputLayer = in;

    }

    /**
     * Fills all Hidden Layers and Output Layer of the ANN with randomized
     * Neurons
     *
     * @author Morgan
     * @throws Exception if the layer cannot get the next layer in the linked
     * list.
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
     * @author Morgan
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
     * @author Morgan and Stephen
     * @param numInNeurons - number of input neurons
     * @param numHL - number of hidden layers
     * @param numNeurons - number of neurons in the hidden layers
     * @param numOutNeurons - number of output neurons
     * @param fName - file name
     * @throws FileNotFoundException if file is not found
     * @throws IOException if file name is illegal
     * @see
     * <a href="http://stackoverflow.com/questions/6754552/regex-to-find-a-float-probably-a-really-simple-question></a>
     */
    public void importTrainingFile(int numInNeurons, int numHL, int numNeurons,
                                   int numOutNeurons, String fName) throws FileNotFoundException, IOException { //

        String input = new String(Files.readAllBytes(Paths.get(fName)));
        Scanner s = new Scanner(new File(fName));

        Pattern p = Pattern.compile("([+-]?\\d*\\.?\\d*,?)+\\n?");

        LinkedList in = new LinkedList();
        LinkedList out = new LinkedList();

        String line;
        while (s.hasNextLine()) {
            line = s.nextLine().trim();
            String[] items = line.split(",");
            double[] inArray = new double[numInNeurons];
            double[] outArray = new double[numOutNeurons];
            int j = 0;
            for (int i = 0; i < (numInNeurons); i++) {
                inArray[i] = Double.parseDouble(items[j]);
                j++;
            }
            for (int i = 0; i < (numOutNeurons); i++) {
                outArray[i] = Double.parseDouble(items[j]);
                j++;
            }

            in.add(inArray);
            out.add(outArray);
        }

        this.trainingIn = in;
        this.trainingOut = out;
        System.out.println(
                "Done reading from file (read " + in.size() + " lines)");
    }

    /**
     * Writes to the ANNTrainingLog.csv file
     *
     * @param epoch the current epoch
     * @param input the current input
     * @throws java.io.FileNotFoundException when the file is not found
     * @throws java.io.UnsupportedEncodingException when the file encoding is
     * not supported
     */
    public void addToClassFile(int epoch, double[] input) throws FileNotFoundException, UnsupportedEncodingException, Exception {
        try ( ////
                PrintWriter writer = new PrintWriter(
                        new FileWriter("ANNTrainingLog.csv", true))) {
            writer.append("Epoch " + epoch + ", " + LocalDateTime.now() + "\n");
            writer.append("Input: ," + Arrays.toString(input) + "\n");

            for (int i = 1; i < this.layers.size(); i++) {
                writer.append(this.layers.get(i).getLayerName() + i + "\n");
                writer.append(this.layers.get(i).getLayerWeights() + "\n");
            }
        } catch (FileNotFoundException writer) {
            System.out.print("Could not find file");
        }
    }

    /**
     * Returns the input layer.
     *
     * @return the input layer
     */
    public InputLayer getInputLayer() {
        return inputLayer;
    }

    /**
     * Returns the output layer.
     *
     * @return the output layer
     */
    public OutputLayer getOutputLayer() {
        return outputLayer;
    }

    /**
     * Returns training input LinkedList.
     *
     * @return training input LinkedList
     */
    public LinkedList getTrainingIn() {
        return trainingIn;
    }

}

/* *****************************************
* CSCI205 - Software Engineering and Design
* Spring 2017
*
* Name: Morgan Muller, Stephen Haberle
* Date: Mar 5, 2017
* Time: 4:57:03 PM
*
* Project: csci205_hw
* Package: hw1
* File: ANNClient
* Description:
*
* ****************************************
 */
package hw2;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Scanner;

/**
 *
 * @author Morgan
 */
public class ANNClient {

    /**
     * Makes an ANN with some arbitrary parameters. Populates each layer with
     * randomly weighted neurons and outputs a formatted string that shows the
     * inputs and weights of each neuron
     *
     * @param args - not used
     * @throws java.lang.Exception
     */
    public static void main(String[] args) throws Exception {
        //We need to ask the user for an acceptable SSE later. Have to pass that to the ANN class.
        ANN ann;
        int numInputs;
        int numOutputs;
        int numHiddenLayers;
        int numHLNodes;
        double acceptableSSE;
        String classFileName;
        String trainFileName;
        boolean quit = false;

        Scanner in = new Scanner(System.in);
        System.out.println(
                "Welcome to the ANN.");

        while (quit == false) {

            System.out.println(
                    "Do you want to enter training mode (1) or classification mode (2)?");
            System.out.println("1: training mode");
            System.out.println("2: testing mode");
            System.out.println("3: quit");
            switch (in.nextInt()) {
                case 1:
                    System.out.println("You're entering training mode.");
                    System.out.println("Please enter the number of inputs.");
                    numInputs = in.nextInt();
                    System.out.println(
                            "Please enter the number of hidden layers.");
                    numHiddenLayers = in.nextInt();
                    System.out.println(
                            "Please enter the number of neurons in the hidden layers.");
                    numHLNodes = in.nextInt();
                    System.out.println("Please enter the number of outputs.");
                    numOutputs = in.nextInt();
                    System.out.println("Please enter the filename: ");
                    trainFileName = in.next();
                    System.out.println(
                            "Please enter an SSE that you find acceptable.");
                    acceptableSSE = in.nextDouble();
                    System.out.println(
                            "Thank you! We are now generating your ANN....");
                    ann = new ANN(acceptableSSE);
                    System.out.println("Creating the layers...");
                    ann.createLayers(numInputs, numOutputs, numHiddenLayers,
                                     numHLNodes);
                    System.out.println("Populating layers with neurons....");
                    ann.populateANN();

                    try {
                        ann.importTrainingFile(numInputs, numHLNodes, numInputs,
                                               numOutputs, trainFileName);
                    } catch (IOException e) {
                        System.out.println("File not found!");
                        break;
                    }

                    PrintWriter writer = new PrintWriter("ANNTrainingLog.csv",
                                                         "UTF-8");
                    writer.println("ANN Training Log " + LocalDateTime.now());
                    writer.println("Number of Inputs: " + numInputs);
                    writer.println("Number of Outputs: " + numOutputs);
                    writer.close();

                    ann.learningMode();
                    ann.printANN();
                    for (int i = 0; i < ann.getTrainingIn().size(); i++) {
                        System.out.println(Arrays.toString(
                                (double[]) ann.getTrainingIn().get(i)));
                        System.out.println(Arrays.toString(ann.classify(
                                (double[]) ann.getTrainingIn().get(i))) + "\n");
                    }

                    System.out.println(
                            "Enter a file to save the classification to.");
                    String classFile = in.next();
                    try {
                        //ann.saveClassFile(classFile);
                        serialize(ann, classFile);
                    } catch (Exception e) {
                        System.out.println("Failed to create file!");
                        break;
                    }
                    break;

                case 2:
                    System.out.println("You're entering testing mode.");
                    System.out.println("Please enter the number of inputs.");
                    numInputs = in.nextInt();
                    System.out.println(
                            "Please enter the number of hidden layers.");
                    numHiddenLayers = in.nextInt();
                    System.out.println(
                            "Please enter the number of neurons in the hidden layers.");
                    numHLNodes = in.nextInt();
                    System.out.println("Please enter the number of outputs.");
                    numOutputs = in.nextInt();
                    System.out.println(
                            "Please enter an SSE that you find acceptable.");
                    acceptableSSE = in.nextDouble();

                    System.out.println(
                            "Please enter the file name containing the weights you want to enter.");

                    classFileName = in.next();
                    System.out.println("Opening file...");
                    try {
                        File f = new File(classFileName);
                    } catch (Exception e) {
                        System.out.println("File not found!");
                        break;
                    }
                    System.out.println("Parsing file....");

                    ann = deserialize(classFileName);

                    ann.printANN();

                    System.out.println("Printing deserialized ANN:");
                    ann.printANN();

                    System.out.println("Please enter an input file:");
                    String inputFile = in.next();

                    ann.importTrainingFile(numInputs, numHLNodes, numInputs,
                                           numOutputs, inputFile);

                    for (int i = 0; i < ann.getTrainingIn().size(); i++) {
                        System.out.println(Arrays.toString(
                                (double[]) ann.getTrainingIn().get(i)));
                        System.out.println(Arrays.toString(ann.classify(
                                (double[]) ann.getTrainingIn().get(i))) + "\n");
                    }

                    break;

                case 3:
                    System.out.println("Goodbye!");
                    quit = true;
                    break;
                default:
                    System.out.println(
                            "This is not a proper input. Please try again.");
                    break;
            }
        }

        //add get inputs file functionality
        //add forward feed functionality
    }

    public static void serialize(ANN ann, String filename) throws FileNotFoundException, IOException {
        FileOutputStream fileOut = new FileOutputStream(filename);
        ObjectOutputStream out = new ObjectOutputStream(fileOut);
        out.writeObject(ann);
        out.close();
        fileOut.close();
    }

    public static ANN deserialize(String filename) throws FileNotFoundException, IOException, ClassNotFoundException {
        FileInputStream fileIn = new FileInputStream(filename);
        ObjectInputStream in = new ObjectInputStream(fileIn);
        ANN ann = (ANN) in.readObject();
        in.close();
        fileIn.close();
        return ann;
    }

}

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
package hw1;

import java.io.File;
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

        Scanner in = new Scanner(System.in);
        System.out.println(
                "Welcome to the our ANN. Do you want to enter training mode (1) or classification mode (2)?");
        System.out.println("1: training mode");
        System.out.println("2: classification mode");
        switch (in.nextInt()) {
            case 1:
                System.out.println("You're entering training mode.");
                System.out.println("Please enter the number of inputs.");
                numInputs = in.nextInt();
                System.out.println("Please enter the number of hidden layers.");
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
                System.out.println(
                        "(xor gets stuck sometimes, re-run if it gets stuck)");

//                ann.printANN();
                ann.importTrainingFile(numInputs, numHLNodes, numInputs,
                                       numOutputs, trainFileName);
                ann.learningMode();
                ann.printANN();
                for (int i = 0; i < ann.trainingIn.size(); i++) {
                    System.out.println(Arrays.toString(
                            (double[]) ann.trainingIn.get(i)));
                    System.out.println(Arrays.toString(ann.classify(
                            (double[]) ann.trainingIn.get(i))) + "\n");
                }
                break;
//=======
//        Scanner sc = new Scanner(System.in);
//
//        while (true) {
//            try {
//                System.out.print("Enter number of inputs: ");
//                int numInNeurons = sc.nextInt();
//
//                System.out.print("Enter number of hidden layers: ");
//                int numHL = sc.nextInt();
//
//                System.out.print("Enter number of neurons per layer: ");
//                int numNeurons = sc.nextInt();
//
//                System.out.print("Enter number of output neurons: ");
//                int numOutNeurons = sc.nextInt();
//
//                System.out.print("Enter name of training file: ");
//                String f = sc.next();
//
////                System.out.print("Enter SSE: ");
////                double sse = sc.nextDouble();
////
//// For testing:
////                int numInNeurons = 2;
////                int numHL = 2;
////                int numNeurons = 2;
////                int numOutNeurons = 1;
////                String f = "trainingfile.txt";
//                ann.importTrainingFile(numInNeurons, numHL, numNeurons,
//                                       numOutNeurons,
//                                       f);
//                break;
//
//            } catch (InputMismatchException numInNeurons) {
//                System.out.println("Wrong type of input! Try again.");
//            }
//        }
//
//        // We can create an ANN with a given number of hidden layers, inputs,
//        //    outputs, and nodes in the hidden layers:
//        ann.createLayers(numInputs, numOutputs, numHiddenLayers, numHLNodes);
//
//        ann.populateANN();
//>>>>>>> 3/7_Training_File//=======
//        Scanner sc = new Scanner(System.in);
//
//        while (true) {
//            try {
//                System.out.print("Enter number of inputs: ");
//                int numInNeurons = sc.nextInt();
//
//                System.out.print("Enter number of hidden layers: ");
//                int numHL = sc.nextInt();
//
//                System.out.print("Enter number of neurons per layer: ");
//                int numNeurons = sc.nextInt();
//
//                System.out.print("Enter number of output neurons: ");
//                int numOutNeurons = sc.nextInt();
//
//                System.out.print("Enter name of training file: ");
//                String f = sc.next();
//
////                System.out.print("Enter SSE: ");
////                double sse = sc.nextDouble();
////
//// For testing:
////                int numInNeurons = 2;
////                int numHL = 2;
////                int numNeurons = 2;
////                int numOutNeurons = 1;
////                String f = "trainingfile.txt";
//                ann.importTrainingFile(numInNeurons, numHL, numNeurons,
//                                       numOutNeurons,
//                                       f);
//                break;
//
//            } catch (InputMismatchException numInNeurons) {
//                System.out.println("Wrong type of input! Try again.");
//            }
//        }
//
//        // We can create an ANN with a given number of hidden layers, inputs,
//        //    outputs, and nodes in the hidden layers:
//        ann.createLayers(numInputs, numOutputs, numHiddenLayers, numHLNodes);
//
//        ann.populateANN();
//>>>>>>> 3/7_Training_File

            case 2:
                System.out.println("You're entering classification mode.");
                System.out.println("Please enter the number of inputs.");
                numInputs = in.nextInt();
                System.out.println("Please enter the number of hidden layers.");
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
                        "Thank you! We are now generating your ANN....");
                ann = new ANN(acceptableSSE);
                System.out.println("Creating the layers...");
                ann.createLayers(numInputs, numOutputs, numHiddenLayers,
                                 numHLNodes);
                System.out.println("Populating layers with neurons....");
                ann.populateANN();

                System.out.println(
                        "Please enter the file name containing the weights you want to enter.");
                classFileName = in.next();
                System.out.println("Opening file...");
                File f = new File(classFileName);
                System.out.println("Parsing file....");
                System.out.println("Adjusting weights...");
                ann.adjustWeights(f);
                break;
            default:
                System.out.println("You dun messed up");
                break;
        }

//        //ann.printANN();
//        for (int i = 0; i < ann.trainingIn.size(); i++) {
//            System.out.println(Arrays.toString(
//                    (double[]) ann.trainingIn.get(i)));
//            System.out.println(Arrays.toString(ann.classify(
//                    (double[]) ann.trainingIn.get(i))) + "\n");
//        }
    }
}

//        //Used to know when we can stop learning.
//        double acceptableSSE = 0.001;
////        ANN ann = new ANN(acceptableSSE);
////        int numInputs = 2;
////        int numOutputs = 1;
////        int numHiddenLayers = 1;
////        int numHLNodes = 3;
//
//        // We can create an ANN with a given number of hidden layers, inputs,
//        //    outputs, and nodes in the hidden layers:
//        ann.createLayers(numInputs, numOutputs, numHiddenLayers, numHLNodes);
//        //ann.printANN();
//        ann.populateANN();
//        ann.learningMode();
//        ann.printANN();
//        double[][] trainingInputs = {{0, 0}, {0, 1}, {1, 0}, {1, 1}};
//        for (double[] input : trainingInputs) {
//            System.out.println(Arrays.toString(input));
//            System.out.println(Arrays.toString(ann.classify(input)) + "\n");
////        }
//    }


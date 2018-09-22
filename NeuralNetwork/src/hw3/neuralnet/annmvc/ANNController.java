/* *****************************************
* CSCI205 - Software Engineering and Design
* Spring 2017
*
* Name: Stephen Haberle, Morgan Muller
* Date: Mar 31, 2017
* Time: 6:23:21 PM
*
* Project: csci205_hw
* Package: hw3.neuralnet.annmvc
* File: ANNController
* Description: ANN Controller class
*
* ****************************************
 */
package hw3.neuralnet.annmvc;

import hw3.neuralnet.ANN;
import hw3.neuralnet.data.LabeledInstance;
import hw3.neuralnet.data.UnlabeledInstance;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Optional;
import javafx.beans.binding.Bindings;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.GridPane;
import javafx.util.StringConverter;
import javafx.util.converter.NumberStringConverter;

/**
 * ANN Controller class
 *
 * @author Stephen Haberle, Morgan Muller
 */
public class ANNController implements EventHandler<ActionEvent> {

    private ANNLearnTask theTask;
    private Thread theThread;
    private ANNModel theModel; // this is acutally the model
    private ANNView theView;
    private Button learnButton;
    private Button openButton;
    private int inputFileIndex = 0;

    /**
     * Constructor
     *
     * @param theModel the model of the ANN
     * @param theView the view of the ANN
     */
    public ANNController(ANNModel theModel, ANNView theView) {
        this.theModel = theModel;
        this.theView = theView;
        this.learnButton = theView.getLearnButton();
        theView.getLearnButton().setOnAction(this);
        theView.getSetLearningBtn().setOnAction(this);
        theView.getSetMomentumBtn().setOnAction(this);
        theView.getPauseEpochBtn().setOnAction(this);
        theView.getNextEpochBtn().setOnAction(this);
        theView.getExitMenuItem().setOnAction(this);
        theView.getResumeBtn().setOnAction(this);
        theView.getOpenSavedConfigMenuItem().setOnAction(this); ////
        theView.getOpenTrainingDataMenuItem().setOnAction(this);  /////
        theView.getSaveCurrentConfigMenuItem().setOnAction(this);  /////
        theView.getChangeANNConfig().setOnAction(this);
        theView.getClassifyBtn().setOnAction(this);
        theThread = null;
        theTask = null;

    }

    /**
     * Method that handles action events.
     *
     * @author Morgan
     * @param event
     */
    @Override
    public void handle(ActionEvent event) {

        if (event.getSource() == this.theView.getSetLearningBtn()) {
            implSetLearningParam();
        }
        else if (event.getSource() == this.theView.getSetMomentumBtn()) {
            implSetMomentum();
        }
        else if (event.getSource() == this.theView.getLearnButton()) {
            implLearnButton();
        }
        else if (event.getSource() == this.theView.getResumeBtn()) {
            implResumeBtn();
        }
        else if (event.getSource() == this.theView.getPauseEpochBtn()) {
            theTask.pauseThread();
            System.out.println("Pause!!");
        }
        else if (event.getSource() == this.theView.getNextEpochBtn()) {
            theTask.step();
            if (theTask.isThreadSuspend() == true) {
                theTask.resumeThread();
            }
        }
        else if (event.getSource() == this.theView.getExitMenuItem()) {
            System.exit(0);
        }
        else if (event.getSource() == this.theView.getSaveCurrentConfigMenuItem()) {
            try {
                implSaveConfig();
            } catch (IOException ex) {

            }
        }
        else if (event.getSource() == this.theView.getOpenSavedConfigMenuItem()) {
            try {
                implOpenConfig();
            } catch (IOException ex) {
            } catch (ClassNotFoundException ex) {
            }
        }
        else if (event.getSource() == this.theView.getOpenTrainingDataMenuItem()) {
            try {
                implOpenTrainData();
            } catch (FileNotFoundException ex) {
                System.out.println("File Not Found");
            }
        }
        else if (event.getSource() == this.theView.getClassifyBtn()) {
            implClassify();
        }
        else if (event.getSource() == this.theView.getChangeANNConfig()) {
            implChangeANN();
        }

    }

    /**
     * Implements the classify functions of the ANN.
     *
     * @author Morgan
     */
    public void implClassify() {

        LabeledInstance inst;

        inst = theModel.getAnn().getTrainData().get(this.inputFileIndex);

        theModel.getAnn().classifyInstance(inst);
        this.inputFileIndex++;

    }

    /**
     * Implements the resume button of the ANN.
     *
     * @author Morgan
     */
    private void implResumeBtn() {
        System.out.println("Start");
        theTask.resumeThread();
        theTask.setThreadStep(false);
    }

    /**
     * Implements the set learning param button of the ANN.
     *
     * @author Morgan
     *
     * @throws NumberFormatException if parsing error occurs
     */
    private void implSetLearningParam() throws NumberFormatException {
        System.out.println("Set Learning Param");
        ANN.setLearningRate(Double.parseDouble(
                this.theView.getTxtFieldLearning().getText()));
        System.out.printf("%f\n", ANN.getLearningRate());
    }

    /**
     * Implements the set momentum button.
     *
     * @author Morgan
     * @throws NumberFormatException if parsing error occurs
     */
    private void implSetMomentum() throws NumberFormatException {
        System.out.println("Set Momentum Param");
        ANN.setMomentum(Double.parseDouble(
                this.theView.getTxtFieldMomentum().getText()));
    }

    /**
     * Implements the learn button.
     *
     * @author Morgan
     */
    private void implLearnButton() {
        if (theTask != null) {
            theTask.stopThread();
        }

        theTask = new ANNLearnTask(theModel.getAnn(), theView);
        theView.getCurrentSSEText().textProperty().bind(
                theTask.valueProperty().asString("%.5f"));
        theView.getCurrentEpochText().textProperty().bind(
                theTask.workDoneProperty().asString());
        theThread = new Thread(theTask);
        theThread.setDaemon(true);
        theThread.start();
    }

    /**
     * implements the save config file menu.
     *
     * @author Morgan
     * @throws IOException if there is an error saving the file
     */
    private void implSaveConfig() throws IOException {
        TextInputDialog dialog = new TextInputDialog("");
        dialog.setHeaderText("Configuration");
        dialog.setTitle("Save Configuration File");
        dialog.setContentText("Enter a name for your configuration file: ");

        while (true) {
            Optional<String> result = dialog.showAndWait();

            if (result.isPresent()) {
                String filename = System.getProperty("user.dir") + "\\" + result.get();
                System.out.println(filename);
                ANNMain.serialize(this.theModel.getAnn(), filename);
                return;
            }
            else {
                return;
            }

        }
    }

    /**
     * Implements the open config file menu option.
     *
     * @throws IOException if there is an error opening the file
     * @throws FileNotFoundException if the file cannot be found
     * @throws ClassNotFoundException if the class cannot be found
     */
    private void implOpenConfig() throws IOException, FileNotFoundException, ClassNotFoundException {
        TextInputDialog dialog = new TextInputDialog("");
        dialog.setHeaderText("Configuration");
        dialog.setTitle("Open Configuration File");
        dialog.setContentText("Enter the name of your configuration file: ");

        while (true) {
            Optional<String> result = dialog.showAndWait();

            if (result.isPresent()) {
                String filename = result.get();
                theModel.loadANN(ANNMain.deserialize(filename));
                return;
            }
            else {
                return;
            }

        }

    }

    /**
     * Implements the open training data file menu option.
     *
     * @author Morgan
     * @throws FileNotFoundException if the file cannot be found
     */
    private void implOpenTrainData() throws FileNotFoundException {
        TextInputDialog dialog = new TextInputDialog("");
        dialog.setHeaderText("Training");
        dialog.setTitle("Open Training File");
        dialog.setContentText("Enter the name of your training file: ");

        while (true) {
            Optional<String> result = dialog.showAndWait();

            if (result.isPresent()) {
                String filename = result.get();
                ANN.importNewTrainData(filename);

                return;
            }
            else {
                return;

            }

        }
    }

    /**
     * Implements the change ANN file menu button.
     *
     * @author Morgan
     * @see
     * <a href="http://code.makery.ch/blog/javafx-dialogs-official/"></a>
     */
    private void implChangeANN() {
        Dialog<ArrayList<String>> dialog = new Dialog<>();
        dialog.setTitle("Change ANN Configuration");
        dialog.setHeaderText("Enter ANN information");

        ButtonType enterButton = new ButtonType("Confirm", ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(enterButton,
                                                       ButtonType.CANCEL);

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        TextField inputs = new TextField();
        inputs.setPromptText("Inputs");
        TextField hidden = new TextField();
        hidden.setPromptText("Hidden Nodes");
        TextField outputs = new TextField();
        outputs.setPromptText("Outputs");

        grid.add(new Label("Number of Inputs: "), 0, 0);
        grid.add(new Label("Number of Hidden Neurons"), 0, 1);
        grid.add(new Label("Number of Outputs : "), 0, 2);
        grid.add(inputs, 1, 0);
        grid.add(hidden, 1, 1);
        grid.add(outputs, 1, 2);

        dialog.getDialogPane().setContent(grid);

//        Platform.runLater(() -> inputs.requestFocus());
//
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == enterButton) {
                ArrayList<String> textFields = new ArrayList<>();
                textFields.add(inputs.getText());
                textFields.add(hidden.getText());
                textFields.add(outputs.getText());
                return textFields;
            }
            return null;
        });

        while (true) {
            Optional<ArrayList<String>> result = dialog.showAndWait();

            if (result.isPresent()) {
                result.ifPresent(ANNInfo -> {
                    int numInputs = Integer.parseInt(ANNInfo.get(0));
                    int numHiddenNodes = Integer.parseInt(ANNInfo.get(1));
                    int numOutputs = Integer.parseInt(ANNInfo.get(2));

                    try {
                        ANN newAnn = new ANN(numInputs, numHiddenNodes,
                                             numOutputs);
                        this.theModel.loadANN(newAnn);
                        this.theView.generateNewLayerPane();
                    } catch (FileNotFoundException ex) {

                    }
                });

                break;
            }
            else {
                break;
            }

        }

    }

    /**
     * Creates a task to do the learning in another thread.
     *
     * @author Morgan
     *
     */
    class ANNLearnTask extends Task<Double> {

        private final boolean oneStep;
        private final ANN ann;
//        private int epoch;
        private boolean threadSuspend;
        private boolean threadStep;
        private boolean stop;
        private ANNView theView;

        /**
         * Creates an ANNLearnTask.
         *
         * @param ann the ANN object
         * @param theView the view object
         */
        public ANNLearnTask(ANN ann, ANNView theView) {  // the ann input is actually the model
            this.oneStep = false;
            this.ann = ann;
            this.threadStep = false;
            this.threadSuspend = false;
            this.stop = false;
            this.theView = theView;

        }

        /**
         * call - you must override this method in your Task class! This handles
         * the actual computations in the separate thread!
         *
         * @author Morgan
         * @return 0.0
         * @throws Exception
         */
        @Override
        protected Double call() throws Exception {

            System.out.println(ann.getTrainData());
            ANN theANN = this.ann;

            System.out.println(theANN);

            //initialize properties to bind input node's text properties and input values
            ArrayList<DoubleProperty> inputProps = new ArrayList<>();
            ArrayList<Double> inputValues;

            //initialize properties to bind output node's text properties and output values
            ArrayList<DoubleProperty> outputProps = new ArrayList<>();
            ArrayList<Double> outputValues;

            //create properties for the inputs
            for (int i = 0; i < this.ann.getInputLayer().getInputValues().size(); i++) {
                DoubleProperty tmp = new SimpleDoubleProperty();
                inputProps.add(tmp);
            }

            //create properties for the outputs
            for (int i = 0; i < this.ann.getOutputLayer().getOutputValues().size(); i++) {
                DoubleProperty tmp = new SimpleDoubleProperty();
                outputProps.add(tmp);
            }

            //declare a converter to convert the double to a string
            StringConverter<Number> converter = new NumberStringConverter();

            //bind the input property to text object
            for (int j = 0; j < inputProps.size(); j++) {
                Bindings.bindBidirectional(
                        this.theView.getInputText().get(j).textProperty(),
                        inputProps.get(j),
                        converter);
            }

            //bind the output property to text object
            for (int l = 0; l < outputProps.size(); l++) {
                Bindings.bindBidirectional(
                        this.theView.getOutputText().get(l).textProperty(),
                        outputProps.get(l),
                        converter);
            }

            double totalError = 0.0;
            ArrayList<ArrayList<Double>> output = theANN.classifyInstances(
                    ann.getTrainData());
            System.out.println("OUTPUT " + output.toString());

            int epoch;
            int i = 0;

            for (epoch = 0; epoch < ANN.maxEpochs; epoch++) {
                if (stop) {
                    return 0.0;
                }

                synchronized (this) {
                    while (this.threadSuspend == true) {
                        System.out.println("Pause!!");
                        wait();
                    }
                }

                theANN.learn(ann.getTrainData(), true, 1);
                if (epoch % 1000 == 0) {
                    output = theANN.classifyInstances(ann.getTrainData());
//                    ANN.printResults(output, ann.getTrainData(), true, 0.1);
                    double error = ANN.computeOutputError(ann.getTrainData(),
                                                          output);
                    System.out.println(
                            "Epoch[" + epoch + "]  Average Error: " + error);

                    inputValues = this.ann.getTrainData().get(i);
//                    System.out.println(inputValues.toString());
                    for (int k = 0; k < inputProps.size(); k++) {
                        inputProps.get(k).set(inputValues.get(k));

                    }

                    ArrayList<Double> output1 = theANN.classifyInstance(
                            (UnlabeledInstance) inputValues);

                    for (int l = 0; l < outputProps.size(); l++) {
                        outputProps.get(l).set(output1.get(l));
                    }

                    if (error <= ANN.errStopThresh) {
                        System.out.println("SUCCESS!");
                        break;
                    }
                    i++;

                    updateValue(error);
                }

                updateProgress(epoch, ANN.maxEpochs);

                if (this.threadStep == true) {
                    System.out.printf("Step!!... epoch =  %d\n", epoch);
                    synchronized (this) {
                        wait();
                    }

                }

                if (isCancelled()) {
                    updateMessage("Cancelled");
                    break;
                }
            }
            System.out.println("DONE!");
            System.out.println("Epochs: " + epoch);

            return 0.0;   // placeholder
        }

        synchronized void step() {
            notify();
            this.threadStep = true;
        }

        public void setThreadStep(boolean threadStep) {
            this.threadStep = threadStep;
        }

        public void pauseThread() {
            this.threadSuspend = true;

        }

        synchronized void resumeThread() {
            this.threadSuspend = false;
            notify();
        }

        public boolean isOneStep() {
            return oneStep;
        }

        public boolean isThreadSuspend() {
            return threadSuspend;
        }

        public boolean isThreadStep() {
            return threadStep;
        }

        public void stopThread() {
            this.stop = true;
        }

    }

}

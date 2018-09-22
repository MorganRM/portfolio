/* *****************************************
* CSCI205 - Software Engineering and Design
* Spring 2017
*
* Name: Stephen Haberle, Morgan Muller
* Date: Mar 31, 2017
* Time: 6:23:38 PM
*
* Project: csci205_hw
* Package: hw3.neuralnet.annmvc
* File: ANNView
* Description: ANN View class.
*
* ****************************************
 */
package hw3.neuralnet.annmvc;

import hw3.neuralnet.Layer;
import java.util.ArrayList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;

/**
 * ANN view class.
 *
 * @author StephenHaberle
 */
public class ANNView {

    private BorderPane root;
    private ANNModel theModel;
    private Pane layerPane;
    private ArrayList<Circle> inputCircles;
    private ArrayList<Text> inputText;
    private ArrayList<Circle> hiddenCircles;
    private ArrayList<Circle> outputCircles;
    private ArrayList<Text> outputText;
    private ArrayList<Line> inputToHiddenLines;
    private ArrayList<Line> hiddenToOutputLines;
    private TextField txtFieldLearning;
    private TextField txtFieldMomentum;
    private Button setLearningBtn;
    private Button setMomentumBtn;
    private RadioButton rbLinearStrat;
    private RadioButton rbLogisticStrat;
    private RadioButton rbReLUStrat;
    private RadioButton rbSoftplusStrat;
    private Button learnButton;
    private Button pauseEpochBtn;
    private Button resumeBtn;
    private Button nextEpochBtn;
    private Button classifyBtn;
    private Button nextInstanceBtn;
    private Text currentSSEText;
    private Text currentEpochText;
    private HBox bottomPane;
    private MenuBar menuBar;
    private VBox leftPane;

    private MenuItem changeANNConfig;
    private MenuItem testDataFilesMenuItem;
    private MenuItem openTrainingDataMenuItem;
    private MenuItem saveCurrentConfigMenuItem;
    private MenuItem openSavedConfigMenuItem;
    private MenuItem exitMenuItem;
    private Menu exitMenu;
    private Menu configMenu;
    private Menu fileMenu;

    /**
     * Class that represents the view of the ANN GUI.
     *
     * @author Stephen
     * @param theModel - the model of the ANN
     */
    public ANNView(ANNModel theModel) {
        this.theModel = theModel;

        root = new BorderPane();
        root.setPrefSize(750, 600);
        root.setPadding(new Insets(15, 15, 15, 15));

        //generate menu bar
        menuBar = generateMenuBar();

        //generate left pane
        leftPane = generateLeftPane();

        //generate bottom pane
        generateBottomPane();

        generateNewLayerPane();

        //add elements
//        root.setCenter(layerPane);
        root.setTop(menuBar);
        root.setLeft(leftPane);
        root.setBottom(bottomPane);

    }

    /**
     * Generate a new ANN pane.
     *
     * @author Stephen
     */
    public final void generateNewLayerPane() {
        //set up layer pane. Used to store all layer panes.
        layerPane = new Pane();

        //generate input layer
        generateInputLayer();

        //generate hidden layer
        generateHiddenLayer();

        //generate input to hidden lines
        generateInputToHiddenLines();

        //generate output layer
        generateOutputLayer();

        //generate hidden to output lines
        generateHiddenToOutputLines();

        addElements();

        root.setCenter(layerPane);
        BorderPane.setAlignment(layerPane, Pos.CENTER);
    }

    /**
     * Generate bottom pane.
     *
     * @author Stephen
     */
    private void generateBottomPane() {
        //generate bottom pane
        bottomPane = new HBox(10);
        classifyBtn = new Button("Classify!");
        nextInstanceBtn = new Button("Step to Next Instance");

        bottomPane.getChildren().addAll(classifyBtn, nextInstanceBtn);
    }

    /**
     * Generates the left pane of the GUI.
     *
     * @author Stephen
     * @return left pane object
     */
    private VBox generateLeftPane() {
        //generate left menu
        VBox leftPane = new VBox();
        leftPane.setPadding(new Insets(10));

        //learning rate
        Label learningLbl = new Label("Learning rate:");
        txtFieldLearning = new TextField();
        txtFieldLearning.setPrefWidth(5);
        setLearningBtn = new Button("Set learning rate");
        leftPane.getChildren().addAll(learningLbl, txtFieldLearning,
                                      setLearningBtn);

        //momentum rate
        Label momentumLbl = new Label("Momentum rate:");
        txtFieldMomentum = new TextField();
        txtFieldMomentum.setPrefWidth(5);
        setMomentumBtn = new Button("Set momentum rate");
        leftPane.getChildren().addAll(new Label(), momentumLbl, txtFieldMomentum,
                                      setMomentumBtn);

        //activation strat
        ToggleGroup activationGroup = new ToggleGroup();

        rbLinearStrat = new RadioButton("Linear");
        rbLogisticStrat = new RadioButton("Logistic");
        rbReLUStrat = new RadioButton("ReLU");
        rbSoftplusStrat = new RadioButton("Softplus");

        activationGroup.getToggles().addAll(rbLogisticStrat, rbLinearStrat,
                                            rbReLUStrat, rbSoftplusStrat);
        rbLogisticStrat.setSelected(true);
        Label activationStratLbl = new Label("Select activation strategy:");

        leftPane.getChildren().addAll(new Label(), activationStratLbl,
                                      rbLogisticStrat, rbLinearStrat,
                                      rbReLUStrat, rbSoftplusStrat);

        //add learn button
        learnButton = new Button("Learn!");
        leftPane.getChildren().addAll(new Label(), learnButton);

        //add pause, resume, and next epoch buttons
        pauseEpochBtn = new Button("Pause Epoch");

        resumeBtn = new Button("Resume");

        nextEpochBtn = new Button("Step to Next Epoch");

        leftPane.getChildren().addAll(pauseEpochBtn, resumeBtn, nextEpochBtn);

        //add current SSE and current Epoch num
        Label currentSSELbl = new Label("Current AVG SSE:");
        currentSSEText = new Text("0");

        Label currentEpochLbl = new Label("Current Epoch:");
        currentEpochText = new Text("0");

        leftPane.getChildren().addAll(new Label(), currentSSELbl, currentSSEText,
                                      new Label(),
                                      currentEpochLbl, currentEpochText);

        return leftPane;
    }

    /**
     * Generates menu bar.
     *
     * @author Stephen
     * @return menu bar object
     */
    private MenuBar generateMenuBar() {
        //create menus
        fileMenu = new Menu("File");
        configMenu = new Menu("Config");
        exitMenu = new Menu("Exit");
        //add items to file menu
        openSavedConfigMenuItem = new MenuItem("Open Saved Config");
        saveCurrentConfigMenuItem = new MenuItem("Save Current Config");
        openTrainingDataMenuItem = new MenuItem(
                "Open Training Data and Test Data Files");

        fileMenu.getItems().addAll(openSavedConfigMenuItem,
                                   saveCurrentConfigMenuItem,
                                   openTrainingDataMenuItem);
        //add items to config menu
        changeANNConfig = new MenuItem("Change ANN Config");
        configMenu.getItems().add(changeANNConfig);
        exitMenuItem = new MenuItem("Exit ANN");
        exitMenu.getItems().add(exitMenuItem);
        //add menus to bar
        menuBar = new MenuBar();
        menuBar.getMenus().addAll(fileMenu, configMenu, exitMenu);
        return menuBar;
    }

    /**
     * Generates the lines that represent the edges between the input and hidden
     * nodes.
     */
    private void generateInputToHiddenLines() {

        this.inputToHiddenLines = new ArrayList<>();
        for (int i = 0; i < inputCircles.size(); i++) {
            for (int j = 0; j < hiddenCircles.size(); j++) {
                Line tmp = new Line();
                tmp.setStrokeWidth(3);
                tmp.startXProperty().bind(inputCircles.get(i).centerXProperty());
                tmp.startYProperty().bind(inputCircles.get(i).centerYProperty());
                tmp.endXProperty().bind(hiddenCircles.get(j).centerXProperty());
                tmp.endYProperty().bind(hiddenCircles.get(j).centerYProperty());

//                DoubleProperty b = new SimpleDoubleProperty(
//                        this.theModel.getAnn().getEdges(0).getEdge(j, i).getWeight() * 10);
//                tmp.strokeWidthProperty().bind(b);
//
//                DoubleProperty a = new SimpleDoubleProperty(
//                        this.theModel.getAnn().getEdges(0).getEdge(j, i).getWeight());
//
//                tmp.fillProperty().bind(
//                        Bindings.when(Bindings.greaterThan(0, a)).then(
//                                Color.BLUE).otherwise(Color.RED));
                this.inputToHiddenLines.add(tmp);
            }
        }
    }

    /**
     * Generates the lines that represent the hidden to output node edges.
     *
     * @author Stephen
     */
    private void generateHiddenToOutputLines() {

        this.hiddenToOutputLines = new ArrayList<>();
        for (int i = 0; i < hiddenCircles.size(); i++) {
            for (int j = 0; j < outputCircles.size(); j++) {
                Line tmp = new Line();
                tmp.setStrokeWidth(3);
                tmp.startXProperty().bind(hiddenCircles.get(i).centerXProperty());
                tmp.startYProperty().bind(hiddenCircles.get(i).centerYProperty());
                tmp.endXProperty().bind(outputCircles.get(j).centerXProperty());
                tmp.endYProperty().bind(outputCircles.get(j).centerYProperty());
                this.hiddenToOutputLines.add(tmp);
            }
        }
    }

    /**
     * Adds the nodes and edges elements to the layer pane.
     *
     * @author Stephen
     */
    private void addElements() {
        layerPane.getChildren().addAll(inputToHiddenLines);
        layerPane.getChildren().addAll(hiddenToOutputLines);
        layerPane.getChildren().addAll(inputCircles);
        layerPane.getChildren().addAll(inputText);
        layerPane.getChildren().addAll(hiddenCircles);
        layerPane.getChildren().addAll(outputCircles);
        layerPane.getChildren().addAll(outputText);

    }

    /**
     * Generates the input layer.
     *
     * @author Stephen
     */
    private void generateInputLayer() {
        this.inputCircles = new ArrayList<>();
        this.inputText = new ArrayList<>();
        Layer inputLayer = this.theModel.getAnn().getInputLayer();
        int numInNeurons = inputLayer.getNumNeurons();
        for (int i = 0; i < numInNeurons; i++) {
            Circle tmp = new Circle(50, Color.GREY);
            tmp.centerYProperty().bind(
                    layerPane.heightProperty().divide(8.0).multiply(1));
            tmp.centerXProperty().bind(layerPane.widthProperty().divide(
                    numInNeurons + 1).multiply(i + 1));
            Text tmpTxt = new Text("0");
            tmpTxt.xProperty().bind(tmp.centerXProperty());
            tmpTxt.yProperty().bind(tmp.centerYProperty());

            this.inputText.add(tmpTxt);
            this.inputCircles.add(tmp);
        }

    }

    /**
     * Generates the hidden layer.
     *
     * @author Stephen
     */
    private void generateHiddenLayer() {
        this.hiddenCircles = new ArrayList<>();
        Layer hiddenLayer = this.theModel.getAnn().getLayer(1);
        int numHidNeurons = hiddenLayer.getNumNeurons();

        for (int i = 0; i < numHidNeurons; i++) {
            Circle tmp = new Circle(50, Color.BLUE);
            tmp.centerYProperty().bind(
                    layerPane.heightProperty().divide(8.0).multiply(4));
            tmp.centerXProperty().bind(layerPane.widthProperty().divide(
                    numHidNeurons + 1).multiply(i + 1));
            Text tmpTxt = new Text("0");
            tmpTxt.xProperty().bind(tmp.centerXProperty());
            tmpTxt.yProperty().bind(tmp.centerYProperty());

//            layerPane.getChildren().add(tmp);
            this.hiddenCircles.add(tmp);
        }

    }
//

    /**
     * Generates the output layer.
     *
     * @author Stephen
     */
    private void generateOutputLayer() {
        this.outputCircles = new ArrayList<>();
        this.outputText = new ArrayList<>();
        Layer outputLayer = this.theModel.getAnn().getOutputLayer();
        int numOutNeurons = outputLayer.getNumNeurons();

        for (int i = 0; i < numOutNeurons; i++) {
            Circle tmp = new Circle(50, Color.RED);
            Text tmpText = new Text("0");
            tmp.centerYProperty().bind(
                    layerPane.heightProperty().divide(8.0).multiply(7));
            tmp.centerXProperty().bind(layerPane.widthProperty().divide(
                    numOutNeurons + 1).multiply(i + 1));
            tmpText.xProperty().bind(tmp.centerXProperty());
            tmpText.yProperty().bind(tmp.centerYProperty());
            this.outputText.add(tmpText);
            this.outputCircles.add(tmp);
        }

    }

    public BorderPane getRootNode() {
        return root;
    }

    public Button getLearnButton() {
        return this.learnButton;
    }

    public TextField getTxtFieldLearning() {
        return txtFieldLearning;
    }

    public TextField getTxtFieldMomentum() {
        return txtFieldMomentum;
    }

    public Button getSetLearningBtn() {
        return setLearningBtn;
    }

    public Button getSetMomentumBtn() {
        return setMomentumBtn;
    }

    public Button getPauseEpochBtn() {
        return pauseEpochBtn;
    }

    public Button getNextEpochBtn() {
        return nextEpochBtn;
    }

    public Button getResumeBtn() {
        return resumeBtn;
    }

    public MenuBar getMenuBar() {
        return menuBar;
    }

    public MenuItem getChangeANNConfig() {
        return changeANNConfig;
    }

    public MenuItem getTestDataFilesMenuItem() {
        return testDataFilesMenuItem;
    }

    public MenuItem getOpenTrainingDataMenuItem() {
        return openTrainingDataMenuItem;
    }

    public MenuItem getSaveCurrentConfigMenuItem() {
        return saveCurrentConfigMenuItem;
    }

    public MenuItem getOpenSavedConfigMenuItem() {
        return openSavedConfigMenuItem;
    }

    public MenuItem getExitMenuItem() {
        return exitMenuItem;
    }

    public Text getCurrentSSEText() {
        return currentSSEText;
    }

    public Text getCurrentEpochText() {
        return currentEpochText;
    }

    public Button getClassifyBtn() {
        return classifyBtn;
    }

    public Button getNextInstanceBtn() {
        return nextInstanceBtn;
    }

    public ArrayList<Text> getInputText() {
        return inputText;
    }

    public ArrayList<Text> getOutputText() {
        return outputText;
    }

}

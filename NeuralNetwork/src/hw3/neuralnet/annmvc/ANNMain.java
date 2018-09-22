/* *****************************************
* CSCI205 - Software Engineering and Design
* Spring 2017
*
* Name: Stephen Haberle, Morgan Muller
* Date: Mar 31, 2017
* Time: 6:24:10 PM
*
* Project: csci205_hw
* Package: hw3.neuralnet.annmvc
* File: ANNMain
* Description: ANN Main class
*
* ****************************************
 */
package hw3.neuralnet.annmvc;

import hw3.neuralnet.ANN;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author StephenHaberle
 */
public class ANNMain extends Application {

    private ANNModel theModel;
    private ANNView theView;
    private ANNController theCtrler;

    /**
     * Initial function.
     *
     * @author Stephen
     * @throws Exception
     */
    @Override
    public void init() throws Exception {
        super.init();
        this.theModel = new ANNModel();
        this.theView = new ANNView(this.theModel);
        this.theCtrler = new ANNController(this.theModel, this.theView);
    }

    /**
     * Start method.
     *
     * @author Stephen
     * @param primaryStage
     */
    @Override
    public void start(Stage primaryStage) {
        Scene scene = new Scene(this.theView.getRootNode());

        primaryStage.setTitle("ANN");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

    /**
     * Writes an ANN to a file.
     *
     * @author Morgan
     * @param ann - ann object
     * @param filename - name of file to save to
     * @throws FileNotFoundException if file cannot be created
     * @throws IOException
     */
    public static void serialize(ANN ann, String filename) throws FileNotFoundException, IOException {
        FileOutputStream fileOut = new FileOutputStream(filename);
        ObjectOutputStream out = new ObjectOutputStream(fileOut);
        out.writeObject(ann);
        out.close();
        fileOut.close();
    }

    /**
     * Loads a serialized ANN.
     *
     * @param filename file name of the ANN
     * @return returns ANN object
     * @throws FileNotFoundException if file cannot be found
     * @throws IOException
     * @throws ClassNotFoundException if class cannot be found
     */
    public static ANN deserialize(String filename) throws FileNotFoundException, IOException, ClassNotFoundException {
        FileInputStream fileIn = new FileInputStream(filename);
        ObjectInputStream in = new ObjectInputStream(fileIn);
        ANN ann = (ANN) in.readObject();
        in.close();
        fileIn.close();
        return ann;
    }

}

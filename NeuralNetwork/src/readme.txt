HW2:

An ANN made by Stephen Haberle and Morgan Muller.

RESOURCES
The primary resources we used for this project were the homework were the assignment sheet, the videos by Prof. Dancy, and verbal explanation of ANNs from TA Ben Matase, Devon Wasson, Eric Marshall, and Prof. King.

CONFIG
We serialize our object after is is trained and that can be imported into our project and loaded into testing mode. Our training log prints the configuration of the layers, including the edge weights stored in each neuron.

NOTES
Our ANN can use multiple hidden layers. We have tested this using a basic input two input one output training file with four data points. For example, using the basic binary xor truth table and using 2 hidden layers with 3 neurons the ANN learns how to do XOR. 

When using multiple hidden layers with the training data posted on moodle, there is so many data points that it takes the ANN forever to learn it. However, using the xor training data with one hidden layer and your choice of neurons in the layer the ANN learns correctly. In theory the ANN can handle multiple outputs but when we tested it with the threeClassTrain file it took an extremely long time because it has so many data points that we did not see an output. 





















__________________________________________________________________________________________
HW1:

An ANN made by Stephen Haberle and Morgan Muller. 

RESOURCES
The primary resources we used for this project were the homework were the assignment sheet, the videos by Prof. Dancy, and verbal explanation of ANNs from TA Ben Matase, Devon Wasson, and Eric Marshall. 

CONFIG
We currently have a bug in our classifcation implementation and inputs are not being parsed correctly, but our configuration for those file will be as followed:
Each line will represent a hidden layer. Each line will have weights seperated by commas. So, for a 3 layer, 2 neuron per layer network the config file must look as follows:

2.0,7.0
2.3,9.1
1.6,5.2

NOTES
Our ANN supports multiple hidden layers with multiple neurons per hidden layer. Our ANN works consistently with AND, OR, NOR, NAND using any combiantion of those settings. For XOR, however, it works best with multiple layers and mutiple neurons per layer. Our ANN is also not consistent with multiple outputs. That is something we need to refine for the future. 

Our ANN currently does not read in weights from a file and alter the ANN to match those weights. We have a bug we are battling with. Because we can't read the weights, Classification is not fully implemented. 

Because we ran out of time dealing with various bugs/conceptual challeneges, the following are not implemented:
- The user cannot set the learning parameter, it is hardcoded at 0.3. This will be easily fixed in the future

- Classification mode does not work because it cannot read inputs from the file at the moment. the ANN class has a classify() method that will perform the task given hardcoded inputs, however. The method will take those inputs and forward feed them through the ANN.

- In training mode, all initial edge weights are a random number between -0.5 and 0.5 We did not have enough time to make it generate [-2.4/m, 2.4/m] but we marked it as a todo.

- We did not have enough time to create JUnit tests. We have been doing most of our testing using the ANNClient class. This will be corrected for the future.

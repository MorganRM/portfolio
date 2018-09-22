An ANN made by Stephen Haberle and Morgan Muller. 



RESOURCES

The primary resources we used for this project were the homework were the assignment sheet, the videos by Prof. Dancy, and verbal explanation of ANNs from TA Ben Matase, Devon Wasson, and Eric Marshall. 



CONFIG

Classification mode (renamed to testing mode in our program) assumes that it will take a file in the format of the test files given to us, WITHOUT the heading at the top. 
Our log file prints out most of the relevant information, except the average SSE.



NOTES

jUnit tests have not been implemented, but the program has been tested thouroughly. We do have functionality that supports multiple hidden layers, especially with simple training files. Some of the given training files take a very long time to train with more than one hidden layer, and it helps to have in mind a specific configuration of neurons for a certain function. We did not implement multiple activation functions due to time constraints. We did, however, implement the momentum parameter.
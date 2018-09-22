/* *****************************************
 * CSCI205 - Software Engineering and Design
 * Fall 2016
 *
 * Name: Prof. King
 * Project: NeuralNet
 * Package: neuralnet.strategy
 * File: LogisticActivationStrategy
 *
 * ****************************************
 */package hw3.neuralnet.strategy;

public class LogisticActivationStrategy implements ActivationStrategy {

    @Override
    public double f(double x) {
        return 1.0f / (1.0f + Math.exp(-x));
    }

    @Override
    public double fDeriv(double x) {
        return f(x) * (1 - f(x));
    }

}

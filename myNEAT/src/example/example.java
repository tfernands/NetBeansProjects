/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package example;

import java.util.Arrays;
import neat.evo.NeatGeneticEngine;
import neat.genome.Genome;
import neat.genome.GeneFactory;
import neat.genome.Node;

/**
 *
 * @author Thales
 */
public class example{
    
    static double[][] inputs = {
        {0,0},
        {1,0},
        {0,1},
        {1,1},
    };
    static double[][] targets = {
        {0},
        {1},
        {1},
        {0},
    };

    public static void main(String[] args){
        
        NeatGeneticEngine nge = new NeatGeneticEngine(2,1,150) {
            @Override
            public double evaluateGenome(Genome g) {
                double fitness = 4;
                for (int j = 0; j < inputs.length; j++){
                    double[] out = g.feedForward(inputs[j]);
                    for (int k = 0; k < out.length; k++){
                        fitness -= Math.abs(targets[j][k] - out[k]);
                    }
                }
                return fitness;
                //return +g.enabledConnections.size();
            }
        };
        
        for (int i = 0; i < 150; i++){
            nge.evolve();
            System.out.println(nge);
        }
        System.out.println(nge.best);
        for (double[] input : inputs) {
            System.out.println("Input: " + Arrays.toString(input) + " guess: " + nge.best.feedForward(input)[0]);
        }
        

        Genome g = new Genome(2,1);
        for (int i = 0; i < g.getActiveConnectionsId().length; i++){
            g.getConnections().get(g.getActiveConnectionsId()[i]).disable();
        }
        GeneFactory.addNode(g, Node.HIDDEN);
        GeneFactory.addNode(g, Node.HIDDEN);
        GeneFactory.addNode(g, Node.HIDDEN);
        GeneFactory.addConnection(g, 1, 4, 20);
        GeneFactory.addConnection(g, 1, 5, -20);
        GeneFactory.addConnection(g, 2, 4, 20);
        GeneFactory.addConnection(g, 2, 5, -20);
        GeneFactory.addConnection(g, 4, 3, 20);
        GeneFactory.addConnection(g, 5, 3, 20);
        GeneFactory.addConnection(g, 6, 4, -10);
        GeneFactory.addConnection(g, 6, 5, 30);
        GeneFactory.addConnection(g, 6, 3, -30);
        System.out.println(g);
        System.out.println(g.feedForward(inputs[3])[0]);
    }
    
}

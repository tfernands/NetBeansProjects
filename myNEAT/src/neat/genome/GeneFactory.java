/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package neat.genome;

import neat.genome.framework.Cantor;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Thales
 */
public class GeneFactory {
    
    private static int globalInnovation = 0;
    private static final Map<Integer,Integer> innovations = new HashMap<>();
    
    public static Connection addConnection(Genome genome, int inId, int outId, double weight){
        Connection connection;
        int cantorPair = Cantor.sheepPair(inId, outId);
        if (innovations.containsKey(cantorPair)){
            connection = new Connection(genome.getNodes().get(inId), genome.getNodes().get(outId), weight, true, innovations.get(cantorPair));
        }else{
            globalInnovation++;
            innovations.put(cantorPair, globalInnovation);
            connection = new Connection(genome.getNodes().get(inId), genome.getNodes().get(outId), weight, true, globalInnovation);
        }
        genome.addConnectionGene(connection);
        return connection;
    }
    
    public static Node addNode(Genome genome, byte type){
        Node n = new Node(genome.getNodes().size()+1,type);
        genome.addNodeGene(n);
        return n;
    }
    
    public static int readInnovation(){
        return globalInnovation;
    }
    
}



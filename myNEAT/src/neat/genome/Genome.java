/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package neat.genome;

import static neat.genome.Node.HIDDEN;
import static neat.genome.Node.OUTPUT;
import java.util.Map;
import java.util.Random;
import static neat.genome.Node.SENSOR;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Objects;
import neat.genome.framework.Cantor;
import neat.evo.NeatGeneticEngine;

/**
 *
 * @author Thales
 */
public final class Genome {
    
    //GENOME
    private final Map<Integer, Node> nodes;
    private final Map<Integer, Connection> connections;
    
    //arraylist for store reference only to enabled connections and possible connections
    private final ArrayList<Connection> enabledConnections;
    private final Map<Integer,Integer[]> connectionsAvailables;
    public final int sensorsCount;
    public final int outputsCount;
    
    private int callId; //NN
    
    public double fitness;
    public double averageFitness;
    
    public Genome(int sensors, int outputs){
        sensorsCount = sensors;
        outputsCount = outputs;
        nodes = new LinkedHashMap<>(sensors+outputs);
        connections = new LinkedHashMap<>(sensors*outputs);
        enabledConnections = new ArrayList<>(connections.size());
        connectionsAvailables = new LinkedHashMap<>();
        for (int i = 1; i <= sensors; i++){
            Node sensor = GeneFactory.addNode(this,Node.SENSOR);
            addNodeGene(sensor);
        }
        for (int i = 1; i <= outputs; i++){
            Node output = GeneFactory.addNode(this,Node.OUTPUT);
            addNodeGene(output);
            for (int s = 1; s <= sensors; s++){
                GeneFactory.addConnection(this,nodes.get(s).id,output.id, 1);
            }
        }
    }
    
    protected void addNodeGene(Node gene){
        updatePossibleConnections(gene);
        nodes.put(gene.id,gene);
    }
    protected void addConnectionGene(Connection gene){
        int cantorPair = Cantor.sheepPair(gene.in.id, gene.out.id);
        if (gene.expression){
            if (!connectionsAvailables.containsKey(cantorPair)){
                cantorPair = Cantor.sheepPair(gene.out.id, gene.in.id);
            }
            connectionsAvailables.remove(cantorPair);
            enabledConnections.add(gene);
        }else{
            connectionsAvailables.put(cantorPair, new Integer[]{gene.in.id,gene.out.id});
        }
        connections.put(gene.inovation,gene);
    }
    
    public Map<Integer, Node> getNodes(){
        return nodes;
    }
    public Map<Integer, Connection> getConnections(){
        return connections;
    }
    public int[] getActiveConnectionsId(){
        int[] ids = new int[enabledConnections.size()];
        for (int i = 0; i < enabledConnections.size(); i++) ids[i] = enabledConnections.get(i).inovation;
        return ids;
    }
    public int getAvailablesConnectionsNumber(){
        return connectionsAvailables.size();
    }
    public double getFitness(){
        return fitness;
    }
    
    //MUTATION
        //WEIGHTS MUTATION
    public void mutateWeights(Random r, double mutationRate, double uniformeMutationRate, double uniformeRange, double randomRange){
        for (Connection c: connections.values()){
            if (r.nextDouble() <= mutationRate){
                if (r.nextDouble() <= uniformeMutationRate){
                    c.weight += r.nextDouble()*uniformeRange-uniformeRange/2;
                }else{
                    c.weight = r.nextDouble()*randomRange-randomRange/2;
                }
            }
        }
    }
    
        //STRUCTURAL MUTATION
    public void mutateConnection(Random r){//EXCLUDING RNN FOR NOW
        if (connectionsAvailables.isEmpty()) return;
        //pick random nodes
        Integer[] newNodes = connectionsAvailables.values().toArray(new Integer[0][0])[r.nextInt(connectionsAvailables.size())];
        //INOVATION NUMBER LEFTING
        GeneFactory.addConnection(this, newNodes[0], newNodes[1], r.nextDouble()*NeatGeneticEngine.RANDOM_WEIGHT_RANGE-NeatGeneticEngine.RANDOM_WEIGHT_RANGE/2);
    }
    public void mutateNode(Random r){
        if (enabledConnections.isEmpty()) return;
        Connection c = enabledConnections.get(r.nextInt(enabledConnections.size()));
        enabledConnections.remove(c);
        connectionsAvailables.put(Cantor.sheepPair(c.in.id,c.out.id), new Integer[]{c.in.id,c.out.id});
        c.disable();
        
        Node node = GeneFactory.addNode(this,Node.HIDDEN);
        GeneFactory.addConnection(this, c.in.id, node.id, 1);
        GeneFactory.addConnection(this, node.id, c.out.id, c.weight);
    }
    
    //MATING
    public static Genome crossover(Genome a, Genome b, Random random){
        Genome child = new Genome(a.sensorsCount, a.outputsCount);
        Genome bestParent;
        Genome otherParent;
        
        if (a.getFitness() > b.getFitness()){
            bestParent = a;
            otherParent = b;
        }else{
            bestParent = b;
            otherParent = a;
        }
        
        //Copy nodes of the best fit parent
        for (Node n: bestParent.nodes.values()){
            child.addNodeGene(n.copy());
        }
        
        //crossover connections
        for (int innovation : bestParent.getConnections().keySet()){
            if (otherParent.getConnections().containsKey(innovation)){ //matching gene
                Connection bestParentConnection = bestParent.getConnections().get(innovation);
                Connection otherParentConnection = otherParent.getConnections().get(innovation);
                Connection childConnection;
                if (random.nextBoolean()){
                    childConnection = bestParentConnection.clone(child);
                }else{
                    childConnection = otherParentConnection.clone(child);
                }
                if (bestParentConnection.expression == false || otherParentConnection.expression == false){
                    if (random.nextDouble() < NeatGeneticEngine.DISABLE_GENE_INHERITON){
                        childConnection.disable();
                    }
                }
                child.addConnectionGene(childConnection);
            }else{ //disjoint or excess gene
                child.addConnectionGene(bestParent.getConnections().get(innovation).clone(child));
            }
        }
        return child;
    }
    
    public static double compatibilityDistance(Genome a, Genome b, double c1, double c2, double c3){
        double[] temp = getEDW(a,b);
        double D = temp[0];//disjoint
        double E = temp[1];//excess
        double W = temp[2];//Averate weight differences
        double N = a.connections.size() > b.connections.size() ? a.connections.size() : b.connections.size(); if (N < 20) N = 1;
        return (c1*E+c2*D)/N+c3*W;
    }
    private static double[] getEDW(Genome a, Genome b){
        double[] EDW = new double[3];
        Integer[] keyA = a.getConnections().keySet().toArray(new Integer[0]);
        Integer[] keyB = b.getConnections().keySet().toArray(new Integer[0]);
        Arrays.sort(keyA);
        Arrays.sort(keyB);

        int indexA = 0;
        int indexB = 0;
        int equalityCount = 0;
        while(indexA < keyA.length && indexB < keyB.length){
            if (Objects.equals(keyA[indexA], keyB[indexB])){
                EDW[2] += Math.abs(a.connections.get(keyA[indexA]).weight - b.connections.get(keyB[indexB]).weight);
                indexA++;
                indexB++;
                equalityCount++;
            }else{
                if (keyA[indexA] > keyB[indexB]){
                    while (indexB < keyB.length && keyA[indexA] > keyB[indexB] ){
                        EDW[1]++;
                        indexB++;
                    }
                    if (indexB == keyB.length && keyB[indexB-1] < keyA[indexA]){
                        EDW[0] = keyA.length - indexA;
                    }
                }else{
                    while (indexA < keyA.length && keyB[indexB] > keyA[indexA]){
                        EDW[1]++;
                        indexA++;
                    }
                    if (indexA == keyA.length && keyA[indexA-1] < keyB[indexB]){
                        EDW[0] = keyB.length - indexB;
                    }
                }
                     
            }
        }
        if (equalityCount > 0){
            EDW[2] /= (double)equalityCount;
        }
        return EDW;
    } 
    
    public Genome copy(){
        Genome genome = new Genome(sensorsCount, outputsCount);
        for (Node n : nodes.values()){
            genome.addNodeGene(n.copy());
        }
        for (Connection c : connections.values()){
            genome.addConnectionGene(c.clone(this));
        }
        genome.fitness = fitness;
        return genome;
    }
    
    public double[] feedForward(double... inputs){
        //inputs_neurons bias is the input value
        callId++;
        for (int i = 1; i <= sensorsCount; i++){
            nodes.get(i).cache = inputs[i-1];
        }
        //create a array of outputs
        int outputStartIndex = sensorsCount+outputsCount;
        double[] output = new double[outputsCount];
        for (int i = 0; i < outputsCount; i++){
            output[i] = nodes.get(outputStartIndex+i).getValue(callId);
        }
        return output;
    }
    
    @Override
    public String toString(){
        String string = "    Nodes:        Fitness: "+fitness+"\n";
        if (nodes.size() > 0){
            String l1 = "        | ";
            String l2 = "        | ";
            for (Node n: nodes.values()){
                l1 += "Id: "+String.format("%2.0f",(double)n.id)+" | ";
                l2 += nodeTypeToString(n)+" | ";
            }
            string += l1+"\n";
            string += l2+"\n";
        }
        string += "\n    Connections:\n";
        if (connections.size() > 0){
            String l1 = "        | ";
            String l2 = "        | ";
            String l3 = "        | ";
            String l4 = "        | ";
            String l5 = "        | ";
            for (Connection c: connections.values()){
                l1 += "In: "+String.format("%9.0f",(float)c.in.id)+" | ";
                l2 += "Out: "+String.format("%8.0f",(float)c.out.id)+" | ";
                l3 += "Weight:"+String.format("%6.2f", c.weight)+" | ";
                l4 += "Exp.:"+expressionToString(c)+" | ";
                l5 += "Innov: "+String.format("%6.0f",(float)c.inovation)+" | ";
            }
            string += l1+"\n";
            string += l2+"\n";
            string += l3+"\n";
            string += l4+"\n";
            string += l5+"\n";
        }
        return string;
    }
    private String nodeTypeToString(Node n){
        switch (n.type) {
            case SENSOR:
                return "Sensor";
            case HIDDEN:
                return "Hidden";
            case OUTPUT:
                return "Output";
        }
        return "null";
    }
    private String expressionToString(Connection c){
        if (c.expression == true){
            //     "1234567890123 |";
            return " Enabled";
        }else{
            return "\033[0;31m" + "Disabled" + "\033[0m";
        }
    }
    
    //NOT PREPARED FOR RNN
    private static int getTotalOfConnections(int sensor, int hidden, int output){
        return sensor*(hidden+output)+hidden*output+hidden*(hidden-1)/2;
    }
    private void updatePossibleConnections(Node n){
        if (n.type == Node.HIDDEN){
            //sensors to hidden
            for (int s = 1; s <= sensorsCount; s++){
                connectionsAvailables.put(Cantor.sheepPair(s, n.id),new Integer[]{s, n.id});
            }
            //hidden to outputs
            for (int o = sensorsCount+1; o <= sensorsCount+outputsCount; o++){
                connectionsAvailables.put(Cantor.sheepPair(n.id, o),new Integer[]{n.id, o});
            }
            //hidden to hiddens
            for (int h = sensorsCount+outputsCount+1; h <= nodes.size(); h++){
                connectionsAvailables.put(Cantor.sheepPair(h,n.id),new Integer[]{h,n.id});
            }
        }
    }
    public static int[][] possibleConnections(int sensor, int hidden, int output){
        int[][] connections = new int[getTotalOfConnections(sensor,hidden,output)][];
        int index = 0;
        int sensorIndex = 1;
        int outputIndex = sensor+1;
        int hiddenIndex = sensor+output+1;
        int total = hiddenIndex+hidden;
        //Sensor to outputs
        for (int s = sensorIndex; s < outputIndex; s++){
            for (int o = outputIndex; o < hiddenIndex; o++){
                connections[index] = new int[]{s,o}; 
                index++;
            }
        }
        //Sensor to hiddens
        for (int s = sensorIndex; s < outputIndex; s++){
            for (int h = hiddenIndex; h < total; h++){
                connections[index] = new int[]{s,h}; 
                index++;
            }
        }
        //hiddens to output
        for (int h = hiddenIndex; h < total; h++){
            for (int o = outputIndex; o < hiddenIndex; o++){
                connections[index] = new int[]{h,o}; 
                index++;
            }
        }
        //hiddens to hidden
        for (int h1 = hiddenIndex; h1 < total-1; h1++){
            for (int h2 = h1+1; h2 < total; h2++){
                connections[index] = new int[]{h1,h2}; 
                index++;
            }
        }
        return connections;
    }
}
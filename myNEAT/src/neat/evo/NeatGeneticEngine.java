/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package neat.evo;

import neat.genome.Genome;
import neat.genome.GeneFactory;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 *
 * @author Thales
 */
public abstract class NeatGeneticEngine {
    
    //WEIGHTS MUTATION
    public static final double WEIGHTS_MUTATION = 0.8;
    public static final double WEIGHTS_UNIFORM_MUTATION = 0.9;
    public static final double UNIFORM_WEIGHT_RANGE = 0.2;
    public static final double RANDOM_WEIGHT_RANGE = 4;

    //STRUCTURAL MUTATION
    public static final double NODE_MUTATION = 0.03;
    public static final double CONNECTION_MUTATION = 0.05;

    //MATING PROBABILITIES
    //--public static final double INTERSPECIES_MATING = 0.001;
    //--public static final double CROSSOVER_EXCLUSION = 0.25;
    public static final double DISABLE_GENE_INHERITON = 0.75;

    //SPECIATION COEFICIENTS
    public static final double C1 = 1.0;
    public static final double C2 = 1.0;
    public static final double C3 = 0.4;
    public static final double COMPATIBILITY_THRESHOLD = 4.0;
    
    private final Random r = new Random();
    
    private final ArrayList<Genome> population;
    private final ArrayList<Genome> nextGeneration;
    private final ArrayList<Species> species;
    private final Map<Genome, Species> speciesMap;
    
    private int specieIdCount = 1;
    public final int populationCount;
    public int generation;
    public Genome best;
    
    public double tempAjustedFitnessSum;
    
    public NeatGeneticEngine(int inputsNumber, int outputsNumber, int popSize){
        populationCount = popSize;
        population = new ArrayList<>(popSize);
        nextGeneration = new ArrayList<>(popSize);
        speciesMap = new HashMap<>(popSize);
        species = new ArrayList<>(20);
        for (int i = 0; i < populationCount; i++){
            population.add(new Genome(inputsNumber,outputsNumber));
        }
        best = population.get(0);
    }
    
    public abstract double evaluateGenome(Genome g);
    
    public void evolve(){
        System.out.println(toString());
        for (Genome g : population){
            g.fitness = evaluateGenome(g);
        }
        for (Genome g : population){
            boolean speciesfinded = false;
            for (Species s : species){ //loop throgh species
                if (Genome.compatibilityDistance(g, s.getReferenceGenome(),C1,C2,C3) <= COMPATIBILITY_THRESHOLD){ //if found one
                    s.addGenome(g); //add genome to it
                    speciesMap.put(g, s);
                    speciesfinded = true;
                    break;
                }
            }
            if (!speciesfinded){ //if none species was found
                Species newSpecies = new Species(g,specieIdCount++); //create a new specie
                species.add(newSpecies); //add new species
                speciesMap.put(g, newSpecies);
            }
        }
        
        for (int i = 0; i < species.size(); i++){ //loop throgh species 
            
            if (species.get(i).getMembers().isEmpty()){
                species.remove(i);//and remove innative ones
                i--;
                continue;
            }
            
            fastAdjustFitness(species.get(i));//calc explicit fitness sharing
            
            tempAjustedFitnessSum += species.get(i).adjustedFitnessSum;
            if (species.get(i).getBestGenome().fitness > best.fitness){
                best = species.get(i).getBestGenome();
            }
            
        }
        //if (debug(2)) return;
        //reprodution
        for (Species s : species){
            nextGeneration.add(s.getBestGenome().copy());
            population.remove(s.getWorseGenome());
        }
        
        for (int i = 0; i < species.size(); i++){
            int offsprings = (int) ((populationCount-species.size())/(tempAjustedFitnessSum/species.get(i).adjustedFitnessSum));
            while (offsprings > 0){
                Genome p1 = selectorGenome(species.get(i),r);
                Genome p2 = selectorGenome(species.get(i),r);
                Genome child = Genome.crossover(p1, p2, r);
                child.mutateWeights(r,WEIGHTS_MUTATION,WEIGHTS_UNIFORM_MUTATION,UNIFORM_WEIGHT_RANGE,RANDOM_WEIGHT_RANGE);
                if (r.nextDouble() <= NODE_MUTATION){
                    child.mutateNode(r);
                }
                if (r.nextDouble() <= CONNECTION_MUTATION){ 
                    child.mutateConnection(r);
                }
                nextGeneration.add(child);
                offsprings--;
            }
        }
        for (Species s : species){
            s.reset(r);
        }
        population.clear();
        population.addAll(nextGeneration);
        nextGeneration.clear();
        tempAjustedFitnessSum = 0;
        generation++;
    }
    
    private static void adjusteSpeciesFitness(Species s){
        for (Genome i : s.getMembers()){
            double sharingSum = 0;
            for (Genome j : s.getMembers()){
                sharingSum += Genome.compatibilityDistance(i, j,C1,C2,C3) > COMPATIBILITY_THRESHOLD ? 0 : 1;
            }
            i.fitness = i.fitness/sharingSum;
            s.updateFitness(i);
        } 
    }
    private static void fastAdjustFitness(Species s){
        for (Genome i : s.getMembers()){
            i.fitness = i.fitness/s.getMembers().size();
            s.updateFitness(i);
        } 
    }

    private static Genome selectorGenome(Species s, Random r){
        double fitnessSum = 0;
        for (Genome g: s.getMembers()){
            fitnessSum += g.getFitness();
        }
        int select = 0;
        double selector = r.nextDouble()*fitnessSum;
        while(selector > 0){
            selector -= s.getMembers().get(select).getFitness();
            select++;
        }
        select--;
        return s.getMembers().get(select);
    }
    
    @Override
    public String toString(){
        if (speciesMap.get(best) == null) return "";
        return "Generation: "+generation+" | Species count: "+species.size()+" | Best: "+ best.fitness+ " of species "+speciesMap.get(best).id;
    }
}

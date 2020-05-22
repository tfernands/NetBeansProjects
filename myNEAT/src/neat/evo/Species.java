/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package neat.evo;

import neat.genome.Genome;
import java.util.ArrayList;
import java.util.Random;

/**
 *
 * @author Thales
 */
public final class Species {
    
    private Genome reference;
    private Genome bestGenome;
    private Genome worseGenome;
    private final ArrayList<Genome> members = new ArrayList<>();
    public double adjustedFitnessSum = 0;
    public final int id;
    
    public Species(Genome reference, int id){
        this.reference = reference;
        this.id = id;
        addGenome(reference);
    }
    
    public ArrayList<Genome> getMembers(){
        return members;
    }
    
    public Genome getBestGenome(){
        return bestGenome;
    }
    public Genome getWorseGenome(){
        return bestGenome;
    }
    
    public void updateFitness(Genome g){
        adjustedFitnessSum += g.fitness;
        if (bestGenome.fitness < g.fitness){
            bestGenome = g;
        }else
        if (worseGenome != null && worseGenome.fitness > g.fitness){
            worseGenome = g;
        }
    }
    
    public Genome getReferenceGenome(){
        return reference;
    }
    
    public void addGenome(Genome genome){
        members.add(genome);
        if (bestGenome == null){
            bestGenome = genome;
        }else
        if (worseGenome == null){
            worseGenome = genome;
        }
    }
    
    public void reset(Random r){
        int newSpecimenIndex = r.nextInt(members.size());
        reference = members.get(newSpecimenIndex);
        members.clear();
        adjustedFitnessSum = 0;
        bestGenome = null;
        worseGenome = null;
    }
}

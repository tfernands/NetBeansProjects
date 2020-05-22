/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package neat.genome;

/**
 *
 * @author Thales
 */
public class Connection {
    
    public final Node in;
    public final Node out;
    public double weight;
    public boolean expression;
    public final int inovation;
    
    protected Connection(final Node in, final Node out, double weight, boolean expression, final int inovation){
        this.in = in;
        this.out = out;
        this.weight = weight;
        this.expression = expression;
        this.inovation = inovation;
        out.in.add(this);
    }

    public void disable(){
        expression = false;
    }
    public void enable(){
        expression = true;
    }
    
    public Connection clone(Genome g){
        return new Connection(g.getNodes().get(in.id), g.getNodes().get(out.id), weight, expression, inovation);
    }
    
    @Override
    public String toString(){
        String expressionString;
        if (expression == true){
            expressionString =  "Enabled";
        }else{
            expressionString = "\033[0;31m" + "Disabled" + "\033[0m";
        }
        return "Connection: "+in.id+" -> "+out.id+", Weight: "+String.format("%1.2f", weight)+", Exp.: "+expressionString+", Innov: "+inovation;
    }
}

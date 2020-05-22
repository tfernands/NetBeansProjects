/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package neat.genome;

import java.util.ArrayList;

/**
 *
 * @author Thales
 */
public class Node {
    //CONSTANTS
    public static final byte SENSOR = 0;
    public static final byte HIDDEN = 1;
    public static final byte OUTPUT = 2;
    
    public final int id;
    public final byte type;
    public ArrayList<Connection> in;
    
    private int callId = -1;
    public double cache;

    protected Node(final int id, final byte type){
        this.id = id;
        this.type = type;
        if (type != SENSOR) in = new ArrayList<>();
    }
    
    public double getValue(int callId){
        if (type == SENSOR || this.callId == callId) return cache;
        if (in == null || in.isEmpty()) return 1;
        this.callId = callId;
        cache = 0;
        for (Connection c: in){
            cache += c.in.getValue(callId)*c.weight;
        }
        cache = sigmoid(cache);
        return cache;
    }
    
    private static double sigmoid(double x){
        return 1./(1+Math.exp(-x));
    }
    
    public Node copy(){
        Node n = new Node(id,type);
        return n;
    }
    
    @Override
    public String toString(){
        String t = null;
        switch (type) {
            case SENSOR:
                t = "SENSOR";
                break;
            case HIDDEN:
                t = "HIDDEN";
                break;
            case OUTPUT:
                t = "OUTPUT";
                break;
            default:
                break;
        }
        return "Node "+id+" "+t;
    }
    
}

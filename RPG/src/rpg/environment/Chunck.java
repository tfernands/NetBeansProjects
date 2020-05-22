/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rpg.environment;

import processing.core.PApplet;
import processing.core.PVector;
import rpg.environment.biomes.Biome;
import static rpg.processing.PCanvas.canvas;
import rpg.render.Camera;
/**
 *
 * @author Thales
 */
public class Chunck{
    
    public final static int SIZE = 96;

    public final Biome biome;
    
    public Chunck(Biome biome){
        this.biome = biome;
    }
    
    public void display(int x, int y){
        biome.texture().update();
        PVector result = new PVector(x,y);
        result = Camera.getResultPos(result);
        biome.texture().displayTile(result, SIZE);
    }
    public static void displayBorder(int x, int y){
        PVector pos = new PVector(x,y);
        PVector p0 = new PVector(pos.x,pos.y);
        PVector p1 = new PVector(pos.x+SIZE,pos.y);
        PVector p2 = new PVector(pos.x+SIZE,pos.y+SIZE);
        PVector p3 = new PVector(pos.x,pos.y+SIZE);
        p0 = Camera.getResultPos(p0);
        p1 = Camera.getResultPos(p1);
        p2 = Camera.getResultPos(p2);
        p3 = Camera.getResultPos(p3);
        canvas.noFill();
        canvas.stroke(2550,0,0);
        canvas.beginShape();
        canvas.vertex(p0.x,p0.y);
        canvas.vertex(p1.x,p1.y);
        canvas.vertex(p2.x,p2.y);
        canvas.vertex(p3.x,p3.y);
        canvas.endShape(PApplet.CLOSE);
    }
}


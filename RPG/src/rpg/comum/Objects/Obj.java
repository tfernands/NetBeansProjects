/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rpg.comum.Objects;

import processing.core.PVector;
import static rpg.processing.PCanvas.canvas;
import rpg.render.Camera;


/**
 *
 * @author Thales
 */
public class Obj{
    
    public PVector pos;
    public PVector size;
    
    public Obj(){
        pos = new PVector();
        size = new PVector(1,1);
    }

    public void display(float x, float y, float scale){
        canvas.rect(x,y,size.x,size.y);
    }
    
}

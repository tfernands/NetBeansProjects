/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rpg.personagens;

import processing.core.PImage;
import processing.core.PVector;
import rpg.render.Camera;
import rpg.comum.Objects.Obj;
import rpg.comum.Objects.SpriteSequence;
import static rpg.processing.PCanvas.canvas;

/**
 *
 * @author Thales
 */
public class Personagem extends Obj{
    
    public String name;
    public SpriteSequence sprite_left;
    public SpriteSequence sprite_right;
    public SpriteSequence sprite_up;
    public SpriteSequence sprite_down;
    
    
    private boolean up,down,left,right;
    public float velocity;
    
    public Personagem(String name){
        this.name = name;
        velocity = 1;
    }
    
    public void update(){
        size.set(sprite_down.getWidth(),sprite_down.getHeight());
        PVector v = new PVector();
        if (up && !down) v.add(0,-velocity);
        if (down && !up) v.add(0,velocity);
        if (left && !right) v.add(-velocity,0);
        if (right && !left) v.add(velocity,0);
        if (v != null){
            v.rotate(-Camera.direction);
            pos.add(v);
        }
    }
    
    public void control(boolean up, boolean down, boolean left, boolean right){
        this.up = up;
        this.down = down;
        this.left = left;
        this.right = right;
    }
    
    @Override
    public void display(float x, float y, float scale){
        x = x-size.x/2*scale; y = y-size.y*scale;
        PImage f;
        if (up){
            sprite_up.update();
            f = sprite_up.getFrame();
        }else
        if (down){
            sprite_down.update();
            f = sprite_down.getFrame();
        }else
        if (left){
            sprite_left.update();
            f = sprite_left.getFrame();
        }else
        if (right){
            sprite_right.update();
            f = sprite_right.getFrame();
        }else f = sprite_down.getFrame();
        canvas.image(f,x,y,size.x*scale,size.y*scale);
    }

}

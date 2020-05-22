/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rpg.comum.Objects;

import processing.core.PImage;

/**
 *
 * @author Thales
 */
public class SpriteSequence {
    PImage[] sequence;
    int frame;
    int timer;
    int timeInterval;
    
    public SpriteSequence(PImage[] sprite, int interval){
        sequence = sprite;
        frame = 0;
        timeInterval = interval;
    }
    public void update(){
        timer++;
        if (timer > timeInterval){
            frame++;
            timer = 0;
        }
        if (frame >=  sequence.length) frame = 0;
    }
    
    public int getWidth(){
        return sequence[0].width;
    }
    public int getHeight(){
        return sequence[0].height;
    }
    
    public PImage getFrame(){
        return sequence[frame];
    }
}

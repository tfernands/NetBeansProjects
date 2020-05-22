/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rpg.comum.Objects;

import processing.core.PImage;
import static rpg.processing.PCanvas.canvas;
/**
 *
 * @author Thales
 */
public class SpriteDisplay extends Obj{
    
    public PImage sprite;

    public SpriteDisplay(PImage sprite) {
        this.sprite = sprite;
        size.set(sprite.width,sprite.height);
    }

    @Override
    public void display(float x, float y, float scale){
        canvas.image(sprite,x-size.x/2*scale,y-size.y*scale,size.x*scale,size.y*scale);
    }
}

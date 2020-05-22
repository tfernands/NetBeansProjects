/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rpg.processing;

import java.util.Random;
import processing.core.PApplet;
import processing.core.PImage;
import rpg.comum.Controls;
import rpg.render.Camera;
import static rpg.comum.Controls.*;
import rpg.render.RenderBuffer;
import rpg.comum.Objects.SpriteDisplay;
import rpg.comum.Objects.SpriteSequence;
import rpg.comum.ImageInstance;
import rpg.environment.Chunck;
import rpg.environment.Map;
import rpg.personagens.Personagem;

/**
 *
 * @author Thales
 */
public class Canvas extends PApplet{

    Personagem p;
    SpriteDisplay[] rocks;
    SpriteDisplay[] trees;
    PImage background;
    
    Map map = new Map();
            
    Chunck chunck;
    
    @Override
    public void settings(){
        size(800,600);
        noSmooth();
    }
    @Override
    public void setup(){
        PCanvas.set(this);
        surface.setResizable(true);
        initMago();
        initWorld();
        map.generateMap(ImageInstance.MAP_0);
    }
    
    @Override
    public void draw(){
        background(0,0,64);
        Controls.update();
        
        Camera.axis.set(width/2,height/2);
        Camera.pos.set(p.pos.x, p.pos.y);
        Camera.control();
        map.display();
        Chunck.displayBorder(0,0);
        p.update();
        RenderBuffer.add(p);
        bufferWorld();
        RenderBuffer.render();
        
        
        p.control(up, down, left, right);
    }
    
    
    
    public void initMago(){
        int animFrames = 8;
        p = new Personagem("Mage");
        p.sprite_down = new SpriteSequence(ImageInstance.SEQUENCE_MAGE_UP,animFrames);
        p.sprite_up = new SpriteSequence(ImageInstance.SEQUENCE_MAGE_DOWN,animFrames);
        p.sprite_left = new SpriteSequence(ImageInstance.SEQUENCE_MAGE_LEFT,animFrames);
        p.sprite_right = new SpriteSequence(ImageInstance.SEQUENCE_MAGE_RIGHT,animFrames);
    }
    
    public void initWorld(){
        rocks = new SpriteDisplay[50];
        trees = new SpriteDisplay[80];
        Random r = new Random();
        for (int i = 0; i < rocks.length; i++){
            rocks[i] = new SpriteDisplay(ImageInstance.ROCKS[r.nextInt(2)]);
            rocks[i].pos.set(random(-100,100),random(-100,100));
        }
        for (int i = 0; i < trees.length; i++){
            trees[i] = new SpriteDisplay(ImageInstance.TREES[r.nextInt(4)]);
            trees[i].pos.set(random(-500,500),random(-500,500));
        }
    }
    
    public void bufferWorld(){
        for (SpriteDisplay item : trees) {
            RenderBuffer.add(item);
        }
        for (SpriteDisplay item : rocks) {
            RenderBuffer.add(item);
        }
    }
    
    @Override
    public void keyPressed(){
        if (key == 'w') up = true;
        if (key == 's') down = true;
        if (key == 'a') left = true;
        if (key == 'd') right = true;
        if (key == 'q') cameraRotateLeft = true;
        if (key == 'e') cameraRotateRight = true;
        if (key == 'z') cameraZoomIn = true;
        if (key == 'x') cameraZoomOut = true;
    }
    
    @Override
    public void keyReleased(){
        if (key == 'w') up = false;
        if (key == 's') down = false;
        if (key == 'a') left = false;
        if (key == 'd') right = false;
        if (key == 'q') cameraRotateLeft = false;
        if (key == 'e') cameraRotateRight = false;
        if (key == 'z') cameraZoomIn = false;
        if (key == 'x') cameraZoomOut = false;
    }
}

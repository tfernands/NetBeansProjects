/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rpg.environment;

import processing.core.PImage;
import static rpg.comum.Util.*;
import rpg.environment.biomes.WaterDeep;

/**
 *
 * @author Thales
 */
public class Map {
    
    private static final int DEEPWATER = toColor(0,0,64);
    private static final int WATER = toColor(0,160,230);
    private static final int SAND = toColor(255,200,10);
    private static final int GRASS = toColor(180,230,30);
    private static final int FLOREST = toColor(150,200,20);
    
    Chunck[][] chuncks;
    int offsetX;
    int offsetY;
    
    public void generateMap(PImage img){
        chuncks = new Chunck[img.width][img.height];
        offsetX = -img.width/2;
        offsetY = -img.height/2;
        for (int i = 0; i < img.pixels.length; i++){
            final int x = i%img.width;
            final int y = i/img.width;

            if (img.pixels[i] == DEEPWATER){
                chuncks[x][y] = new Chunck(WaterDeep);
            }else
            if (img.pixels[i] == WATER){
                chuncks[x][y] = new Chunck(Water);
            }else
            if (img.pixels[i] == SAND){
                chuncks[x][y] = new Chunck(Sand);
            }else
            if (img.pixels[i] == GRASS){
                chuncks[x][y] = new Chunck(Grass);
            }
            
            else{
                chuncks[x][y] = new Chunck(Null);
            }
            
        }
    }
    
    public void display(){
        for (int y = 0; y < chuncks.length; y++){
            for (int x = 0; x < chuncks.length; x++){
                chuncks[x][y].display(x+offsetX, y+offsetY);
            }
        }
    }
    
}

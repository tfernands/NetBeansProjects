/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rpg.comum;

import processing.core.PImage;
import static rpg.comum.Util.setBackgroundTransparent;
import static rpg.processing.PCanvas.applet;

/**
 *
 * @author Thales
 */
public final class ImageInstance {
    
    public static final String ROOT = "D:/Projetos/Programação/JAVA/RPG/";
    
    public static final PImage NULL = applet.loadImage(ROOT+"environment/null.png");
    
    //PERSONAGENS
    public static final PImage[] SEQUENCE_MAGE_UP = loadSequence(ROOT+"personagens/mago/magofrente",".png",2);
    public static final PImage[] SEQUENCE_MAGE_DOWN = loadSequence(ROOT+"personagens/mago/magocostas",".png",2);;
    public static final PImage[] SEQUENCE_MAGE_LEFT = loadSequence(ROOT+"personagens/mago/magoladoesq",".png",2);
    public static final PImage[] SEQUENCE_MAGE_RIGHT = loadSequence(ROOT+"personagens/mago/magoladodir",".png",2);
    
    //ASSETS
    public static final PImage[] TREES = loadSequence(ROOT+"environment/trees/tree-",".png",4);
    public static final PImage[] ROCKS = loadSequence(ROOT+"environment/rocks/rock-",".png",3);
    
    //TEXTURES
    public static final PImage GRASS_TEXTURE = applet.loadImage(ROOT+"environment/grass.png");
    public static final PImage SAND_TEXTURE = applet.loadImage(ROOT+"environment/sand.png");
    public static final PImage WATER_TEXTURE = applet.loadImage(ROOT+"environment/water.png");
    public static final PImage WATER_DEEP_TEXTURE = applet.loadImage(ROOT+"environment/water_deep.png");
    
    //Map
    public static PImage MAP_0 = applet.loadImage(ROOT+"environment/maps/mapa0.png");
    
    public final static PImage[] loadSequence(String directory, String extension, int count){
        PImage[] sequence = new PImage[count];
        for (int i = 0 ; i < count; i++){
            sequence[i] = setBackgroundTransparent(applet.loadImage(directory+i+extension));
        }
        return sequence;
    }
}

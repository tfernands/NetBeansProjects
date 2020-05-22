/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rpg.comum;

import static processing.core.PConstants.ARGB;
import processing.core.PImage;
import static rpg.processing.PCanvas.applet;

/**
 *
 * @author Thales
 */
public class Util {
    
    public static int transparentColor = toColor(255,0,255);
    
    
    public static PImage setBackgroundTransparent(PImage img) {
        PImage imgA = applet.createImage(img.width, img.height, ARGB);
        for (int i = 0; i < img.pixels.length; i++){
            if (img.pixels[i] == transparentColor) imgA.pixels[i] = applet.color(0,0);
            else imgA.pixels[i] = img.pixels[i];
        }
        return imgA;
    }
    
    public static PImage resize(PImage pequena, int scale) {
        PImage grande = applet.createImage(pequena.width*scale, pequena.height*scale, ARGB);
        //int offsetX = x*scale;
        //int offsetY = y*grande.width*scale;
        for (int i = 0; i < pequena.pixels.length; i++){
            int x = i%pequena.width;
            int y = i/pequena.width;
            for (int j = 0; j < scale; j++){
                for (int k = 0; k < scale; k++){
                    grande.pixels[x*scale+y*grande.width*scale+j+k*grande.width] = pequena.pixels[i];
                }
            }
        }
        return grande;
    }
    
    
    public static int getAlpha(int c){
        return (c >> 24) & 0xFF;
    }
    public static int getRed(int c){
        return (c >> 16) & 0xFF;
    }
    public static int getGreen(int c){
        return (c >> 8) & 0xFF;
    }
    public static int getBlue(int c){
        return c & 0xFF;
      }
    public static int toColor(int r, int g, int b, int a){
        if(r>255){r=255;}if(g>255){g=255;}if(b>255){b=255;}if(a>255){a=255;}
        if(r<0){r=0;}if(g<0){g=0;}if(b<0){b=0;}if(a<0){a=0;}
        return (a<<24|r<<16|g<<8|b);
      }
    public static int toColor(int r, int g, int b){
        if(r>255){r=255;}if(g>255){g=255;}if(b>255){b= 255;}
        if(r<0){r=0;}if(g<0){g=0;}if(b<0){b=0;}
        return (255<<24|r<<16|g<<8|b);
      }
    public static int toColor(int bw, int a){
        if(bw>255){bw=255;}if(a>255){a=255;}
        if(bw<0){bw=0;}if(a<0){a=0;}
        return (a<<24|bw<<16|bw<<8|bw);
      }
    public static int toColor(int bw){
        if(bw>255){bw=255;}
        if(bw<0){bw=0;}
        return (255<<24|bw<<16|bw<<8|bw);
      }
    public static int colorAddInt(int c, int intensity){
        int r = getRed(c) + intensity,
            g = getGreen(c) + intensity,
            b = getBlue(c) + intensity;
        return toColor(r,g,b,getAlpha(c));
      }
    public static int colorSetAlpha(int c, int alpha){
        int r = getRed(c),
            g = getGreen(c),
            b = getBlue(c),
            a = alpha;
        return toColor(r,g,b,getAlpha(c));
    }
    public static int colorAddColor(int c, int c2){
        int r = getRed(c) + getRed(c2),
            g = getGreen(c) + getGreen(c2),
            b = getBlue(c) + getBlue(c2),
            a = getAlpha(c) + getAlpha(c2);
        return toColor(r,g,b,a);
    }
    
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rpg.comum;

import static rpg.processing.PCanvas.applet;

/**
 *
 * @author Thales
 */
public class Controls {
    public static int frameCount;
    public static boolean keyReleased;
    public static boolean up;
    public static boolean down;
    public static boolean left;
    public static boolean right;
    public static boolean cameraRotateLeft;
    public static boolean cameraRotateRight;
    public static boolean cameraZoomIn;
    public static boolean cameraZoomOut;
    
    public static void update(){
        if (keyReleased && frameCount > 0){
            frameCount = 0;
            keyReleased = false;
        }
        if (applet.keyPressed){
            frameCount++;
        }else if (frameCount > 0){
            keyReleased = true;
        }
        
    }
}

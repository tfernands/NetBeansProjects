/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rpg.render;

import processing.core.PApplet;
import static processing.core.PApplet.map;
import static processing.core.PConstants.PI;
import processing.core.PVector;
import rpg.comum.Controls;
import static rpg.comum.Controls.*;
import static rpg.processing.PCanvas.applet;

/**
 *
 * @author Thales
 */
public class Camera {
    public static int zoom = 2;
    public static PVector pos = new PVector();
    public static PVector axis = new PVector();
    public static float direction = 0f;
    private static float inclination = 0.4f;
    private static float inclinationAngle = 0.4f;
    
    public static void control(){
        if (applet.mousePressed){
            setInclination(Camera.getInclination()+map(applet.mouseY-applet.pmouseY, 0, applet.height, 0, 1));
            float mX = applet.mouseX-applet.pmouseX;
            mX *= -(applet.mouseY-applet.height/2f)/200f;
            direction += map(mX, 0, applet.width, 0, PI);
        }
        if (cameraRotateLeft){
            direction += 0.02f;
        }
        if (cameraRotateRight){
            direction -= 0.02f;
        }
        if (cameraZoomIn && Controls.frameCount == 1){
            if (zoom < 20) zoom *= 2;
        }
        if (cameraZoomOut && Controls.frameCount == 1){
            if (zoom > 1) zoom /= 2;
        }
    }
    
    public static void setInclination(float value){
        if (value < 0.2) value = 0.2f;
        if (value > 0.9) value = 0.9f;
        inclination = value;
        inclinationAngle = PApplet.map(inclination,1,0,PApplet.HALF_PI,0);
    }
    public static float getInclination(){
        return inclination;
    }
    public static float getInclinationAngle(){
        return inclinationAngle;
    }
    
    public static PVector getResultPos(PVector p){
        PVector r = p.copy();
        r.sub(pos);
        r.rotate(direction);
        r.y *= Camera.getInclination();
        r.z = p.z/PApplet.tan(getInclinationAngle());
        r.x += axis.x/zoom;
        r.y += axis.y/zoom;
        r.x *= zoom;
        r.y *= zoom;
        return r;
    }
    
    public static PVector localTransform(PVector p){
        PVector r = p.copy();
        r.rotate(Camera.direction);
        r.y *= Camera.getInclination();
        r.z = p.z/PApplet.tan(getInclinationAngle());
        r.x *= Camera.zoom;
        r.y *= Camera.zoom;
        return r;
    }
}

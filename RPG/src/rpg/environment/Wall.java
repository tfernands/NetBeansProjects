/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rpg.environment;

/**
 *
 * @author Thales
 */
public class Wall {
    float x1;
    float y1;
    float x2;
    float y2;
    float height;
    
    public Wall(float x1, float y1, float x2, float y2, float height){
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;
        this.height = height;
    }
    
    public void display(){
        
    }
}

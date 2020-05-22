/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rpg.render;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import processing.core.PApplet;
import processing.core.PVector;
import rpg.comum.Objects.Obj;
import rpg.comum.Util;
import static rpg.processing.PCanvas.canvas;

/**
 *
 * @author Thales
 */
public class RenderBuffer {
    
    public static final int BORDER = 50; //offset da borda
    private static final ArrayList<Buffer> BUFFER = new ArrayList<>();

    public static void add(Obj a){
        Buffer b = new Buffer(a);
        if (b.x > BORDER && b.x < canvas.width-BORDER && b.y > BORDER && b.y < canvas.height-BORDER+b.obj.size.y*Camera.zoom)
            BUFFER.add(new Buffer(a));
    }
    
    public static void render(){
        canvas.noStroke();
        canvas.fill(0,100);
        for (Buffer o : BUFFER){
            canvas.ellipse(o.worldPosition.x, o.worldPosition.y,o.obj.size.x*Camera.zoom,o.obj.size.x*Camera.zoom*Camera.getInclination());
        }
        Collections.sort(BUFFER, new ObjComparator());
        for (Buffer o : BUFFER){
            int index = (int)PApplet.map(o.y,0,canvas.height/2,255*Camera.getInclination(),255);
            if (index < 0) index = 0;
            if (index > 255) index = 255;
            canvas.tint(Util.toColor(index,index,index));
            o.obj.display(o.x, o.y, Camera.zoom);
        }
        canvas.noTint();
        BUFFER.clear();
    }
    
    private static class Buffer{
        public Obj obj;
        public PVector worldPosition;
        public float zCameraDist;
        public float x;
        public float y;
        public Buffer(Obj obj){
            this.obj = obj;
            worldPosition = Camera.getResultPos(obj.pos);
            float tan = PApplet.tan(Camera.getInclinationAngle());
            zCameraDist = worldPosition.y/tan+obj.pos.z*tan;
            x = worldPosition.x;
            y = worldPosition.y-worldPosition.z;
        }
    }
    private static class ObjComparator implements Comparator<Buffer> {   
        @Override
        public int compare(Buffer a, Buffer b) {
            float za = a.zCameraDist;
            float zb = b.zCameraDist;
            if (za > zb) return 1;
            if (zb > za) return -1;
            return 0;
        }
    }
}





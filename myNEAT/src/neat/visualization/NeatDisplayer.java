/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package neat.visualization;

import neat.genome.Connection;
import neat.genome.Node;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 *
 * @author Thales
 */
public final class NeatDisplayer extends SBtn{
    
    //referencia a rede neural
    public Genome genome;
    public int geneSize = 0;
    
    //botoes para interação com os neuronios
    public Map<Integer,SBtn> nodes = new HashMap<>();
    public ArrayList<SBtn> sensors = new ArrayList<>();
    public ArrayList<SBtn> hiddens = new ArrayList<>();
    public ArrayList<SBtn> outputs = new ArrayList<>();
    
    public Map<Integer,Edge> connections = new HashMap<>();
    
    public Vector2D sbtnSize = new Vector2D(1,1);

    public NeatDisplayer(PCamera camera){
        super(camera);
    }
    
    public void setGenome(Genome genome){
        this.genome = genome;
        geneSize = genome.getConnections().size()+genome.getNodes().size();
        createStructure();
        updatePos();
    }
    
    @Override
    public void set(double x, double y, double size){
        try { 
            throw new Exception("set(x,y,sizeX,sizeY,size) must be called instead");
        } catch (Exception ex) {
            Logger.getLogger(NeatDisplayer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    @Override
    public void set(double x, double y, double sizeX, double sizeY){
        try { 
            throw new Exception("set(x,y,sizeX,sizeY,size) must be called instead");
        } catch (Exception ex) {
            Logger.getLogger(NeatDisplayer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    @Override
    public void set(double x, double y, double sizeX, double sizeY, double size){
        isAEllipse = true;
        pos.set(x,y);
        this.size.set(sizeX,sizeY);
        sbtnSize.set(size,size);
        updatePos();
    }
    
    public void updateStructure(){
        if (genome == null) return;
        if (geneSize != genome.getConnections().size()+genome.getNodes().size()){
            geneSize = genome.getConnections().size()+genome.getNodes().size();
            SBtn btn  = null;
            System.out.println("Innovation Detected!");
            for (Node n : genome.getNodes().values()){
                if (!nodes.containsKey(n.id)){
                    System.out.println("Node "+n.id+" added!");
                    btn = new SBtn(camera);
                    btn.pos.set(F.random(pos.x+sbtnSize.x,pos.x+size.x-sbtnSize.x),F.random(pos.y+sbtnSize.x-sbtnSize.x, pos.y+size.y-sbtnSize.x));
                    btn.size = sbtnSize;
                    btn.id = n.id;
                    nodes.put(n.id,btn);
                    switch (n.type) {
                        case Node.SENSOR:
                            sensors.add(btn);
                            break;
                        case Node.HIDDEN:
                            hiddens.add(btn);
                            break;
                        case Node.OUTPUT:
                            outputs.add(btn);
                            break;
                    }
                }
            }
            for (Connection c: genome.getConnections().values()){
                if (!connections.containsKey(c.inovation)){
                    if (c.expression){
                        System.out.println("Connection "+c.inovation+" added!");
                        Vector2D a = nodes.get(c.in.id).pos;
                        Vector2D b = nodes.get(c.out.id).pos;
                        if (btn != null){
                            if (btn.id != c.in.id){
                                btn.pos.set(a);
                            }
                        }
                        connections.put(c.inovation,new Edge(a,b));
                    }
                }else{
                    if (!c.expression){
                        System.out.println("Connection "+c.inovation+" disabled!");
                        connections.remove(c.inovation);
                    }
                }
            }
            System.out.println(genome);
        }
    }

    public void relax(double v, double length){
        if (genome == null) return;
        final double offset = sbtnSize.x/2;
        final double minLength = length;
        
        for (SBtn n1: nodes.values()){
            for (SBtn n2: nodes.values()){
                if (n1 != n2 && genome.getNodes().get(n1.id).type == Node.HIDDEN){
                    Vector2D f = Vector2D.sub(n1.pos, n2.pos);
                    f.normalize();
                    f.div(Vector2D.distSq(n1.pos, n2.pos));
                    n1.pos.add(f);
                }
            }
        }
        for (Connection c: genome.getConnections().values()){
            if (!c.expression) continue;
            if (connections.containsKey(c.inovation)){
                SBtn a = nodes.get(c.in.id);
                SBtn b = nodes.get(c.out.id);
                Edge e = connections.get(c.inovation).copy();
                e.p1.add(offset,offset);
                e.p2.add(offset,offset);
                Vector2D f = e.getVector();
                double d = f.mag();
                f.normalize();
                f.mult(minLength-d);
                if (genome.getNodes().get(a.id).type == Node.HIDDEN){
                    a.pos.add(f.copy().mult(-v));
                }
                if (genome.getNodes().get(b.id).type == Node.HIDDEN){
                    b.pos.add(f.mult(v));
                }
            }
        }
        
    }
    
    @Override
    public void display(){
        if (genome == null) return;
        final double offset = sbtnSize.x/2;
        final double spacing = 0.2;
        final float arrowSize = (float)(4/camera.getZoom());
        canvas.strokeWeight((float)(0.5/camera.getZoom()));
        for (Connection c: genome.getConnections().values()){
            if (!c.expression) continue;
            if (connections.containsKey(c.inovation)){
                Edge e = connections.get(c.inovation);
                Vector2D v = e.getVector();
                Vector2D n = v.copy().normalize();
                n.mult(offset+spacing);
                float x = (float)(e.p1.x+offset+n.x);
                float y = (float)(e.p1.y+offset+n.y);
                drawArrow(x,y,(float)(v.mag()-(offset+spacing)*2),(float)(-v.angleOrigin()+Math.PI/2),arrowSize);
                
                if (c.weight >= 0){
                    canvas.fill(255);
                }else{
                    canvas.fill(0);
                }
                canvas.ellipse(x,y,arrowSize,arrowSize);

                canvas.textSize(12);
                canvas.fill(0);
                canvas.pushMatrix();
                canvas.scale((float)(1/camera.getZoom()));
                canvas.text(String.format("%.1f",c.weight),(float)(((e.p1.x+e.p2.x)/2+offset)*camera.getZoom()),(float)(((e.p1.y+e.p2.y)/2+offset)*camera.getZoom()));
                canvas.popMatrix();
            }
        }
        for (SBtn btn: nodes.values()){
            canvas.fill(255);
            btn.display();
            canvas.textSize(12);
            canvas.fill(0);
            canvas.pushMatrix();
            canvas.scale((float)(1/camera.getZoom()));
            canvas.text(btn.id,(float) ((btn.pos.x+offset)*camera.getZoom()),(float) ((btn.pos.y+offset)*camera.getZoom()));
            canvas.popMatrix();
        }
    }
    
    private void drawArrow(float cx, float cy, float len, float angle, float size){
        canvas.pushMatrix();
        canvas.translate(cx, cy);
        canvas.rotate(angle);
        canvas.line(0,0,len, 0);
        canvas.line(len, 0, len - size, -size);
        canvas.line(len, 0, len - size, size);
        canvas.popMatrix();
    }
    
    private void createStructure(){
        nodes.clear();
        sensors.clear();
        hiddens.clear();
        outputs.clear();
        connections.clear();
        //Update arrays;
        for (Node n : genome.getNodes().values()){
            SBtn btn = new SBtn(camera);
            btn.size = sbtnSize;
            btn.id = n.id;
            nodes.put(n.id,btn);
            switch (n.type) {
                case Node.SENSOR:
                    sensors.add(btn);
                    break;
                case Node.HIDDEN:
                    hiddens.add(btn);
                    break;
                case Node.OUTPUT:
                    outputs.add(btn);
                    break;
            }
        }
        for (Connection c: genome.getConnections().values()){
            if (c.expression){
                Vector2D a = nodes.get(c.in.id).pos;
                Vector2D b = nodes.get(c.out.id).pos;
                connections.put(c.inovation,new Edge(a,b));
            }
        }
    }
    
    private void updatePos(){
        //update positions
        for (int i = 0; i < sensors.size(); i++){
            double spacing = size.y/sensors.size();
            double posY = pos.y+spacing*i+spacing/2-sbtnSize.y/2;
            sensors.get(i).pos.set(pos.x,posY);
        }
        for (SBtn btn: hiddens){
            btn.pos.set(F.random(pos.x+sbtnSize.x,pos.x+size.x-sbtnSize.x),F.random(pos.y+sbtnSize.x-sbtnSize.x, pos.y+size.y-sbtnSize.x));
        }
        for (int i = 0; i < outputs.size(); i++){
            double spacing = size.y/outputs.size();
            double posY = pos.y+spacing*i+spacing/2-sbtnSize.y/2;
            outputs.get(i).pos.set(pos.x+size.x-sbtnSize.x,posY);
        }
    }
}

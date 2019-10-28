/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package applicationdesign;

import java.awt.Point;
import java.util.ArrayList;
import javafx.geometry.Point2D;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;

/**
 *
 * @author 66945
 */
public class TreeSprite {
    //UI
    Polygon chevron;
    
    //Background
    Sprite sprite;
    boolean open;
    int scriptDiff;
    Rectangle detectionBounds;
    
    public TreeSprite(Sprite sprt) {
        sprite = sprt;
        open = false;
        
        scriptDiff = 0;
        
        chevron = new Polygon();
        chevron.getPoints().addAll(new Double[] {
            10.0, 10.0,
            20.0, 15.0,
            10.0, 20.0
        });
        
        detectionBounds = new Rectangle();
        
        detectionBounds.setX(10.0);
        detectionBounds.setY(10.0);
        
        detectionBounds.setWidth(10);
        detectionBounds.setHeight(10);
    }
    
    public Sprite getSprite() {
        return sprite;
    }
    
    public ArrayList<TreeScript> getTreeScripts() {
        ArrayList<TreeScript> treeScripts = new ArrayList<>();
        
        for(int i = 0; i < sprite.getScripts().size(); i++) {            
            treeScripts.add(new TreeScript(sprite.getScripts().get(i)));
        }
        
        return treeScripts;
    }
    
    public Polygon getPolygon() {
        return chevron;
    }
    
    public Rectangle getDetectionBounds() {
        return detectionBounds;
    }
    
    public boolean pointInBounds(Point p) {
        return detectionBounds.contains(new Point2D(p.x, p.y));
    }
    
    public void setScriptDiff(int diff) {
        scriptDiff = diff;
        detectionBounds.setY(10.0 + scriptDiff);
    }
    
    public double[] getTriangleXArray() {
        Object[] pointsObj = chevron.getPoints().toArray();
        double[] xArr = new double[pointsObj.length / 2];
        
        for(int i = 0; i < xArr.length; i++)
            xArr[i] = Double.parseDouble(pointsObj[i * 2].toString());
        
        return xArr;
    }
    
    public double[] getTriangleYArray() {
        Object[] pointsObj = chevron.getPoints().toArray();
        double[] yArr = new double[pointsObj.length / 2];
        
        for(int i = 0; i < yArr.length; i++)
            yArr[i] = Double.parseDouble(pointsObj[(i * 2) + 1].toString()) + scriptDiff;
        
        return yArr;
    }
    
    public void open() {
        open = true;
        chevron.getPoints().clear();
        chevron.getPoints().addAll(new Double[] {
            10.0, 10.0,
            20.0, 10.0,
            15.0, 20.0
        });
    }
    
    public void close() {
        open = false;
        chevron.getPoints().clear();
        chevron.getPoints().addAll(new Double[] {
            10.0, 10.0,
            20.0, 15.0,
            10.0, 20.0
        });
    }
    
    public boolean isOpen() {
        return open;
    }
}

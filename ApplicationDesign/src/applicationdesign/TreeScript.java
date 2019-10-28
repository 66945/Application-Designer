/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package applicationdesign;

import java.awt.Point;
import java.io.File;
import javafx.geometry.Point2D;
import javafx.scene.shape.Rectangle;

/**
 *
 * @author 66945
 */
public class TreeScript {
    File script;
    String scriptName;
    int scriptDiff;    
    Rectangle detectionBounds;
    
    public TreeScript(File scrpt) {
        script = scrpt;
        
        String path = script.getPath();
        scriptName = path.substring(path.lastIndexOf("\\") + 1);
        
        scriptDiff = 0;
        
        detectionBounds = new Rectangle();
        
        detectionBounds.setX(30);
        detectionBounds.setY(10);
        
        detectionBounds.setWidth(100);
        detectionBounds.setHeight(15);
    }
    
    public String getName() {
        return scriptName;
    }
    
    public Rectangle getDetectionBounds() {
        return detectionBounds;
    }
    
    public boolean pointInBounds(Point p) {
        return detectionBounds.contains(new Point2D(p.x, p.y));
    }
    
    public void setScriptDiff(int diff) {
        scriptDiff = diff;
        detectionBounds.setY(scriptDiff - 10);
    }
}

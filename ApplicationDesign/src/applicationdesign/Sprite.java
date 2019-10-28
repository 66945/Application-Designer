/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package applicationdesign;

import java.awt.Point;
import java.io.File;
import java.util.ArrayList;
import javafx.geometry.Point2D;
import javafx.scene.image.Image;
import javafx.scene.shape.Rectangle;

/**
 *
 * @author 66945
 */
public class Sprite {    
    Point position;
    Point scale;
    Point rScale;
    Image image;
    String name;
    ArrayList<File> scripts;
    
    boolean selected;
    Rectangle detectionBounds;
    
    public Sprite(String nm, Point pos, Point scl, String imagePath) {
        name = nm;
        position = pos;
        scale = scl;
        image = new Image(imagePath);
        
        selected = false;
        
        scripts = new ArrayList<>();
        
        detectionBounds = new Rectangle();
        
        //Acount for negative values
        rScale = new Point(Math.abs(scale.x), Math.abs(scale.y));
        
        detectionBounds.setX(position.x - (image.getWidth() * ((double) rScale.x / 100)) / 2);
        detectionBounds.setY(position.y - (image.getHeight() * ((double) rScale.y / 100)) / 2);
        
        detectionBounds.setWidth(image.getWidth() * ((double) rScale.x / 100));
        detectionBounds.setHeight(image.getHeight() * ((double) rScale.y / 100));
    }
    
    public boolean pointInBounds(Point p) {
        return detectionBounds.contains(new Point2D(p.x, p.y));
    }
    
    public String getName() {
        return name;
    }
    
    public Point getPosition() {
        return position;
    }
    
    public void setPosition(Point pos) {
        position = pos;
        detectionBounds.setX(position.x - (image.getWidth() * ((double) rScale.x / 100)) / 2);
        detectionBounds.setY(position.y - (image.getHeight() * ((double) rScale.y / 100)) / 2);
    }
    
    public Point getScale() {
        return scale;
    }
    
    public void setScale(Point scl) {
        scale = scl;
        
        rScale = new Point(Math.abs(scale.x), Math.abs(scale.y));
        
        detectionBounds.setX(position.x - (image.getWidth() * ((double) rScale.x / 100)) / 2);
        detectionBounds.setY(position.y - (image.getHeight() * ((double) rScale.y / 100)) / 2);
        
        detectionBounds.setWidth(image.getWidth() * ((double) rScale.x / 100));
        detectionBounds.setHeight(image.getHeight() * ((double) rScale.y / 100));
    }
    
    public Image getImage() {
        return image;
    }
    
    public void setImage(Image img) {
        image = img;
        detectionBounds.setX((image.getWidth() * ((double) rScale.x / 100)) / 2);
        detectionBounds.setY((image.getHeight() * ((double) rScale.y / 100)) / 2);
        
        detectionBounds.setWidth(image.getWidth() * ((double) rScale.x / 100));
        detectionBounds.setHeight(image.getHeight() * ((double) rScale.y / 100));
    }
    
    public void select() {
        selected = true;
    }
    
    public void deselect() {
        selected = false;
    }
    
    public boolean isSelected() {
        return selected;
    }
    
    public ArrayList<File> getScripts() {
        return scripts;
    }
    
    public void addScript(File script) {
        scripts.add(script);
    }
    
    public void removeScript(File script) {
        scripts.remove(script);
    }
    
    public Rectangle getDetectionBounds(){
        return detectionBounds;
    }
    
    //=============================================Static methods=================================================
    
    public static Sprite getSelectedSprite(ArrayList<Sprite> sprites) {
        for(Sprite sprite : sprites) {
            if(sprite.isSelected()) {
                return sprite;
            }
        }
        
        return null;
    }
}

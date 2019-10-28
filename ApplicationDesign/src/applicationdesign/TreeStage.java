/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package applicationdesign;

import java.awt.Point;
import java.util.ArrayList;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

/**
 *
 * @author 66945
 */
public class TreeStage {
    //UI stuffs
    Group root;
    Canvas canvas;
    GraphicsContext context;
    
    //Background stuffs
    ArrayList<TreeSprite> treeSprites;
    ArrayList<TreeScript> treeScripts;
    
    public TreeStage(ArrayList<Sprite> sprts) {
        root = new Group();
        
        canvas = new Canvas(200, 500);
        context = canvas.getGraphicsContext2D();
        
        root.getChildren().add(canvas);
        
        treeSprites = new ArrayList<>();
        treeScripts = new ArrayList<>();
        
        for(int i  = 0; i < sprts.size(); i++) {
            treeSprites.add(new TreeSprite(sprts.get(i)));
        }
        
        //Action handlers
        root.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                for(TreeSprite treeSprite : treeSprites) {                    
                    if(treeSprite.pointInBounds(new Point((int) event.getX(), (int) event.getY()))) {
                        if(treeSprite.isOpen())
                            treeSprite.close();
                        else
                            treeSprite.open();
                    }
                }
                
                for(TreeScript treeScript : treeScripts) {                    
                    if(treeScript.pointInBounds(new Point((int) event.getX(), (int) event.getY()))) {
                        System.out.println(treeScript.getName());
                    }
                }
                
                renderTree();
            }
        });
        
        renderTree();
    }
    
    public void renderTree() { 
        context.clearRect(0, 0, 200, 500);
        
        treeScripts.clear();
        int scriptDiff = 0;
        
        for(TreeSprite treeSprite : treeSprites) {
            if(treeSprite.isOpen())
                context.setFill(Color.CADETBLUE);
            else
                context.setFill(Color.BLACK);
            
            //acount for scripts in y direction
            treeSprite.setScriptDiff(scriptDiff);
            
            context.fillPolygon(treeSprite.getTriangleXArray(), treeSprite.getTriangleYArray(), 3);
            
            context.setFill(Color.BLACK);
            
            context.fillText(treeSprite.getSprite().getName(), 25, scriptDiff + 20);
            
            scriptDiff += 20;
            
            if(treeSprite.isOpen()) {
                context.setFill(Color.GRAY);
                
                ArrayList<TreeScript> spriteTreeScripts = treeSprite.getTreeScripts();
                
                for(TreeScript treeScript : spriteTreeScripts) {
                    scriptDiff += 15;
                    treeScript.setScriptDiff(scriptDiff);
                    context.fillText(treeScript.getName(), 30, scriptDiff);
                    
                    treeScripts.add(treeScript);
                }
            }
        }
    }
    
    public Group getPane() {
        return root;
    }
}

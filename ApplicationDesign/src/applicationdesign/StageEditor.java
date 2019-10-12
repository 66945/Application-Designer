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
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;

/**
 *
 * @author 66945
 */
public class StageEditor {
    //UI variables
    Group root;
    Canvas stageCanvas;
    GraphicsContext context;
    
    //Background variables
    ArrayList<Sprite> sprites;
    int width;
    int height;
    
    public StageEditor(int w, int h, ArrayList<Sprite> sprts) {
        width = w;
        height = h;
        
        root = new Group();
        stageCanvas = new Canvas(width, height);
        context = stageCanvas.getGraphicsContext2D();
        
        sprites = sprts;
        renderStage();
        
        root.getChildren().add(stageCanvas);
        
        root.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
                Point clickPoint = new Point((int) e.getX(), (int) e.getY());
                
                for(Sprite sprite : sprites) {
                    if(sprite.pointInBounds(clickPoint))
                        sprite.select();
                    else
                        sprite.deselect();
                }
                
                renderStage();
            }
        });
    }
    
    public void renderStage() {
        context.clearRect(0, 0, width, height);
        
        for(Sprite sprite : sprites) {
            Image img = sprite.getImage();
            
            double x = sprite.getPosition().x;
            double y = sprite.getPosition().y;
            
            double sx = sprite.getScale().x;
            double sy = sprite.getScale().y;
            
            //Acount for scale being in percentages
            sx /= 100;
            sy /= 100;
            
            //Acount for position in middle
            x -= (img.getWidth() * sx) / 2;
            y -= (img.getHeight() * sy) / 2;
            
            context.drawImage(img, x, y, img.getWidth() * sx, img.getHeight() * sy);
            
            //Show selection
            if(sprite.isSelected()) {
                //Draw detection bounds
                Rectangle rect = sprite.getDetectionBounds();
                context.setStroke(Color.RED);
                context.strokeRect(rect.getX(), rect.getY(), rect.getWidth(), rect.getHeight());
                
                //Write name of selected sprite
                context.setFill(Color.BLACK);
                context.setFont(Font.font("Consolas", 20));
                context.fillText(sprite.getName(), 25, 25);
            }
        }
    }
    
    public Group getGroup() {
        return root;
    }
}

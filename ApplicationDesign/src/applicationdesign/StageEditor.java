/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package applicationdesign;

import java.awt.Point;
import java.util.ArrayList;
import javafx.animation.AnimationTimer;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;

/**
 *
 * @author 66945
 */
enum ActionState {
    NONE,
    UNKNOWN,
    MOVE,
    SCALE,
    ROTATE;
}

public class StageEditor {
    //UI variables
    Group root;
    Canvas stageCanvas;
    GraphicsContext context;
    
    //Background variables    
    ArrayList<Sprite> sprites;
    int width;
    int height;
    ActionState currentAction;
    Point freezePoint;
    Point mousePoint;
    
    public StageEditor(int w, int h, ArrayList<Sprite> sprts) {
        width = w;
        height = h;
        
        root = new Group();
        stageCanvas = new Canvas(width, height);
        context = stageCanvas.getGraphicsContext2D();
        
        sprites = sprts;
        
        root.getChildren().add(stageCanvas);
        
        //Events and actions
        
        currentAction = ActionState.NONE;
        freezePoint = new Point(0, 0);
        mousePoint = new Point(0, 0);
        
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
        
        root.setOnMouseMoved(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                mousePoint = new Point((int) event.getX(), (int) event.getY());
                
                if(!currentAction.equals(ActionState.NONE)) {
                    Sprite sprite = getSelectedSprite();
                    Point spritePoint = sprite.getPosition();

                    switch(currentAction){
                        case MOVE:
                            Point movePoint = new Point(mousePoint.x - freezePoint.x, 
                                    mousePoint.y - freezePoint.y);
                            sprite.setPosition(movePoint);
                            break;
                        case SCALE:
                            double scaleFactor = Point.distance(spritePoint.x,
                                    spritePoint.y, mousePoint.x, mousePoint.y) / (100 * 1.4);
                            Point scalePoint = new Point((int) (freezePoint.x * scaleFactor),
                                    (int) (freezePoint.y * scaleFactor));
                            sprite.setScale(scalePoint);
                            break;
                    }
                }
                
                renderStage(); //Allows actions to be rendered in real time but before overlays
                
                for(Sprite sprite : sprites) {
                    if(sprite.pointInBounds(mousePoint) && !sprite.isSelected()) {                            
                        Rectangle rect = sprite.getDetectionBounds();
                        context.setStroke(Color.CADETBLUE);
                        context.strokeRect(rect.getX(), rect.getY(), rect.getWidth(), rect.getHeight());
                    }
                }
            }
        });
        
        root.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if(event.getText() != null && currentAction.equals(ActionState.NONE)) { //Make sure no action is being performed
                    switch(event.getText()) {
                        case "m":
                            currentAction = ActionState.MOVE;
                            Point spritePoint = getSelectedSprite().getPosition();
                            freezePoint = new Point(mousePoint.x - spritePoint.x, 
                                    mousePoint.y - spritePoint.y);
                            break;
                        case "s":
                            currentAction = ActionState.SCALE;
                            freezePoint = getSelectedSprite().getScale();
                            break;
                        default:
                            currentAction = ActionState.UNKNOWN;
                            break;
                    }
                    
                    renderStage();
                }
            }
        });
        
        root.setOnKeyReleased(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                currentAction = ActionState.NONE;
                freezePoint = new Point(0, 0);
                renderStage();
            }
        });
        
        renderStage();
    }
    
    public Sprite getSelectedSprite() {
        for(Sprite sprite : sprites) {
            if(sprite.isSelected()) {
                return sprite;
            }
        }
        
        return null;
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
                
                //Details of selected sprite
                context.setFill(Color.GRAY);
                context.setFont(Font.font("Consolas", 15));
                //Position
                String posString = "Position: (" + sprite.getPosition().x + ", " 
                        + sprite.getPosition().y + ")";
                context.fillText(posString, 40, 45);
                
                //Scale
                String scaleString = "Scale: X=" + sprite.getScale().x + ", Y=" 
                        + sprite.getScale().y;
                context.fillText(scaleString, 40, 65);
            }
        }
        
        switch(currentAction) {
            case NONE:
                break;
            case UNKNOWN:
                context.setFill(Color.FIREBRICK);
                context.setFont(Font.font("Consolas", 40));
                context.fillText("KEY NOT RECOGNIZED", 50, 50);
                break;
            case MOVE:
                context.setFill(Color.CADETBLUE);
                context.setFont(Font.font("Consolas", 40));
                context.fillText("MOVE", 50, 50);
                break;
            case SCALE:
                context.setFill(Color.CADETBLUE);
                context.setFont(Font.font("Consolas", 40));
                context.fillText("SCALE", 50, 50);
                break;
            case ROTATE:
                context.setFill(Color.CADETBLUE);
                context.setFont(Font.font("Consolas", 40));
                context.fillText("ROTATE", 50, 50);
                break;
        }
    }
    
    public Group getGroup() {
        return root;
    }
}

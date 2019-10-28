/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package applicationdesign;

import java.awt.Point;
import java.io.File;
import java.util.ArrayList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

/**
 *
 * @author 66945
 */
public class SpriteInfo {
    //UI elements
    ScrollPane scrollRoot;
    VBox root;
    
    Label spriteName;
    ImageView spriteImage;
    
    Label xPositionLabel;
    TextField xPositionInput;
    
    Label yPositionLabel;
    TextField yPositionInput;
    
    Label xScaleLabel;
    TextField xScaleInput;
    
    Label yScaleLabel;
    TextField yScaleInput;
    
    Label scriptsLabel;
    ArrayList<Button> scriptButtons;
    
    //Background stuff
    ArrayList<Sprite> sprites;
    Sprite activeSprite;
    
    public SpriteInfo(ArrayList<Sprite> sprts) {
        root = new VBox();
        
        sprites = sprts;
        activeSprite = null;
        
        spriteName = new Label();
        spriteName.setFont(Font.font("Consolas", 20));
        spriteImage = new ImageView();
        
        xPositionLabel = new Label("X Pos:");
        xPositionInput = new TextField();
        xPositionInput.setMaxWidth(50);
        
        yPositionLabel = new Label("Y Pos:");
        yPositionInput = new TextField();
        yPositionInput.setMaxWidth(50);
        
        xScaleLabel = new Label("X Scale:");
        xScaleInput = new TextField();
        xScaleInput.setMaxWidth(50);
        
        yScaleLabel = new Label("Y Scale:");
        yScaleInput = new TextField();
        yScaleInput.setMaxWidth(50);
        
        scriptsLabel = new Label("Scripts");
        scriptsLabel.setFont(Font.font("Consolas", 20));
        
        scriptButtons = new ArrayList<>();
        
        scrollRoot = new ScrollPane(root);
        
        scrollRoot.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                activeSprite = Sprite.getSelectedSprite(sprites);
                renderSpriteInfo();
            }
        });
        
        scrollRoot.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if(activeSprite != null) {
                    int xPos = Integer.parseInt(xPositionInput.getText());
                    int yPos = Integer.parseInt(yPositionInput.getText());
                    
                    int xScale = Integer.parseInt(xScaleInput.getText());
                    int yScale = Integer.parseInt(yScaleInput.getText());
                    
                    activeSprite.setPosition(new Point(xPos, yPos));
                    activeSprite.setScale(new Point(xScale, yScale));
                }
            }
        });
        
        renderSpriteInfo();
    }
    
    public void renderSpriteInfo() {        
        if(activeSprite != null) {
            spriteName.setText(activeSprite.getName());
            spriteImage.setImage(activeSprite.getImage());
            
            xPositionInput.setText(String.valueOf(activeSprite.getPosition().x));
            yPositionInput.setText(String.valueOf(activeSprite.getPosition().y));
            
            xScaleInput.setText(String.valueOf(activeSprite.getScale().x));
            yScaleInput.setText(String.valueOf(activeSprite.getScale().y));
            
            reloadScriptButtons();
            addEverything();
            
            for(Button scriptBtn : scriptButtons)
                root.getChildren().add(scriptBtn);
        }else {
            removeEverything();
        }
    }
    
    public void removeEverything() {
        root.getChildren().clear();
    }
    
    public void addEverything() {
        if(root.getChildren().isEmpty()) {
            root.getChildren().add(spriteName);
            root.getChildren().add(spriteImage);
            
            root.getChildren().add(xPositionLabel);
            root.getChildren().add(xPositionInput);
            
            root.getChildren().add(yPositionLabel);
            root.getChildren().add(yPositionInput);
            
            root.getChildren().add(xScaleLabel);
            root.getChildren().add(xScaleInput);
            
            root.getChildren().add(yScaleLabel);
            root.getChildren().add(yScaleInput);
            
            root.getChildren().add(scriptsLabel);
        }
    }
    
    public void reloadScriptButtons() {
        while(!scriptButtons.isEmpty()) {
            root.getChildren().remove(scriptButtons.get(0));
            scriptButtons.remove(scriptButtons.get(0));
        }
        
        for(File script : activeSprite.scripts){
            String path = script.getPath();
            String fileName = path.substring(path.lastIndexOf("\\") + 1);
            
            Button scriptBtn = new Button(fileName);
            scriptBtn.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    System.out.println(fileName);
                }
            });
            
            scriptButtons.add(scriptBtn);
        }
    }
    
    public ScrollPane getPane(){
        return scrollRoot;
    }
}

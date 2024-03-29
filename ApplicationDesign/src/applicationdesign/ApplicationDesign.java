/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package applicationdesign;

import java.awt.Point;
import java.io.File;
import java.util.ArrayList;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

/**
 *
 * @author Daniel McGrath
 */
public class ApplicationDesign extends Application {
    BorderPane root;
    StageEditor stageEdit;
    SpriteInfo spriteInfo;
    TreeStage spriteTree;
    
    @Override
    public void start(Stage primaryStage) {        
        root = new BorderPane();
        
        //Test code
        ArrayList<Sprite> sprts = new ArrayList<>();
        sprts.add(new Sprite("floop", new Point(100, 250), new Point(50, 50), "notepad.png"));
        sprts.get(0).addScript(new File("C:\\users\\66945\\documents\\mud.etext"));
        sprts.add(new Sprite("florp", new Point(450, 100), new Point(100, 50), "notepad.png"));
        sprts.add(new Sprite("money forwards", new Point(250, 275), new Point(100, 100), "moneybag.png"));
        sprts.add(new Sprite("money backwards", new Point(250, 200), new Point(-100, 100), "moneybag.png"));
        
        stageEdit = new StageEditor(600, 500, sprts);
        
        root.setLeft(stageEdit.getGroup());
        stageEdit.getGroup().setFocusTraversable(true);
        
        TabPane tabs = new TabPane();
        
        spriteTree = new TreeStage(sprts);
        Tab stTab = new Tab("Sprite Tree");
        stTab.setContent(spriteTree.getPane());
        tabs.getTabs().add(stTab);
        
        spriteInfo = new SpriteInfo(sprts);
        Tab siTab = new Tab("Sprite Info");
        siTab.setContent(spriteInfo.getPane());
        tabs.getTabs().add(siTab);
        
        root.setCenter(tabs);
        //Delete that code after testing is done
        
        Scene scene = new Scene(root, 800, 500);
        
        primaryStage.setTitle("Application Designer | Daniel McGrath");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}

package main_pod;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URL;

import javax.swing.JButton;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ToolBar;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class Window extends Application {
	
	
	static Pane root = new Pane();
	//static Pane tabInterface = new Pane();
	static Group group = new Group(root);
	Scene scene;
	Button bt  = new Button("");
	
	//paneInterface paneI = new paneInterface();
   
	
	@Override
	public void start(Stage primaryStage) {
		try {
			
			primaryStage.setTitle("Grand Exchange Central");
			root.setPrefSize(1080, 720);
            scene = new Scene(group, 1080, 720,Color.BEIGE);
			root.setId("Bank-Screen");
			Image icon = new Image ("icon.png");
			
			primaryStage.getIcons().add(icon);

			
			DisplayController dc = new DisplayController();
			dc.getTab();
			
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.setAlwaysOnTop(false);
			
			primaryStage.setResizable(true);
			primaryStage.show();
	
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

}

//VBOX to add list of items in ge after search?


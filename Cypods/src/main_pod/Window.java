package main_pod;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URL;

import javax.swing.JButton;

import javafx.application.Application;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ToolBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class Window extends Application implements ActionListener {
	
	static Pane root = new Pane();
	Button bt  = new Button("");
    static Label tab1 = new Label();
    static Label tab2 = new Label();
	/* * bt.
	 * setStyle("-fx-background-image: url('https://secure.runescape.com/m=itemdb_rs/1641812494724_obj_sprite.gif?id=21787')"
	 * ); bt.setPrefSize(30, 30); bt.setLayoutX(132); bt.setLayoutY(130);
	 */

    
	@Override
	public void start(Stage primaryStage) {
		try {
			
			primaryStage.setTitle("Grand Exchange Central");
            Scene scene = new Scene(root, 1080, 720);
			root.setId("Bank-Screen");
			tabs t = new tabs();
			t.getTab1();
			t.getTab2();
			t.getTab3();
			t.getTab4();
			t.getTab5();
			t.getTab6();
			t.getTab7();
			t.getTab8();
			t.getTab9();
			t.getTab10();

			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.setResizable(false);
			primaryStage.show();
			
			//bt.setBackground(Color.BLACK); 
		   	
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		root.setId("RSGIF2");
		
	}

}


//Junk code
/* setting label background without image view
 * tab1.setTranslateX(121); tab1.setTranslateY(30); tab1.setPrefSize(30, 30);
 * root.getChildren().add(tab1); tab1.
 * setStyle("-fx-background-image: url('https://secure.runescape.com/m=itemdb_rs/1641812494724_obj_sprite.gif?id=21787')"
 * );
 */

package main_pod;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

public class paneInterface extends Tab {
	//public Pane tabInterface = new Pane();
	//ScrollPane sp = new ScrollPane();
	ImageView imageView ;
	
	public void activateInterface() {
		
		//tabInterface.autosize();
		
		setItemScrollArea();
		tabInterface.getChildren().add(imageView);
		//root.getChildren().add(sp);

		
	}
	
	private void setItemScrollArea() {
		imageView = new ImageView(new Image("Bank_Interface_Pane_V1.png"));
		//sp.setId("tabInterface");
		
		//sp.setLayoutX(0);
		//sp.setLayoutY(90);
		
		tabInterface.setTranslateX(0);
		tabInterface.setTranslateY(91);
		
		//imageView.setX(0);
		//imageView.setY(95);
		
		tabInterface.setVisible(true);
		
	}
	
	
	

}

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
	ImageView geSearch;
	
	public void activateInterface() {
		
		//tabInterface.autosize();
		
		setItemScrollArea();
		tabInterface.getChildren().add(imageView);
		tabInterface.getChildren().add(geSearch);
		//root.getChildren().add(sp);

		
	}
	
	private void setItemScrollArea() {
		imageView = new ImageView(new Image("GE_TEXT.png"));
		geSearch = new ImageView(new Image("GE_SEARCH_V2.png"));
		//geSearch = new ImageView(new Image("GE_SEARCH_V3.png"));
		//geSearch = new ImageView(new Image("GE_SEARCH_V4.png"));
		imageView.setOpacity(1.0);

		tabInterface.setTranslateX(0);
		tabInterface.setTranslateY(91);
		
		geSearch.setX(0);
		geSearch.setY(405);
		geSearch.setFitWidth(775);
		geSearch.setFitHeight(225);
		geSearch.setRotate(180);
		
		/*geSearch.setX(651);
		geSearch.setY(215);
		geSearch.setFitWidth(580);
		geSearch.setFitHeight(255);
		geSearch.setRotate(90);*/
		
		tabInterface.setVisible(true);
		
	}
	
	
	

}

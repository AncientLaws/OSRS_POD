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

	ImageView imageView ;
	ImageView geSearch;
	ImageView inventory;
	ImageView graphBackground;
	public Pane tabInterface = new Pane();
	
	public void activateInterface() {
		
		setItemScrollArea();
		setInventoryMenu();
		setItemMenu();
		tabInterface.getChildren().add(geSearch);
		tabInterface.getChildren().add(inventory);
		tabInterface.getChildren().add(graphBackground);
		group.getChildren().add(tabInterface);
		tabInterface.setVisible(true);
		
	}
	
	private void setItemScrollArea() {
		geSearch = new ImageView(new Image("GE_SEARCH_V2.png"));

		tabInterface.setTranslateX(0);
		tabInterface.setTranslateY(91);
		
		geSearch.setX(0);
		geSearch.setY(405);
		geSearch.setFitWidth(750);
		geSearch.setFitHeight(225);
		geSearch.setRotate(180);
		
		
		
	}
	
	private void setInventoryMenu() {
		inventory = new ImageView(new Image("Inventory_2.PNG"));
		inventory.setX(746);
		inventory.setY(210);
		inventory.setFitWidth(325);
		inventory.setFitHeight(425);
		
	}

	private void setItemMenu() {
		graphBackground = new ImageView(new Image("Inventory_2.PNG"));
		graphBackground.setX(746);
		graphBackground.setY(0);
		graphBackground.setFitWidth(325);
		graphBackground.setFitHeight(215);
		
	}
	
	

}

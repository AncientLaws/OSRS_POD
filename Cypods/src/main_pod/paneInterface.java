package main_pod;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.nio.channels.NonWritableChannelException;

import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

public class paneInterface extends Window {

	ImageView imageView ;
	ImageView geSearch;
	ImageView inventory;
	ImageView graphBackground;
	Label itemTopMenuLabel;	
	ImageView itemIconPaneImage;
	
	public Pane tabInterface = new Pane();
	
	public void activateInterface() {
		
		drawItemScrollArea();
		drawInventoryMenu();
		drawItemTopMenu();
		tabInterface.getChildren().add(geSearch);
		tabInterface.getChildren().add(inventory);
		tabInterface.getChildren().add(graphBackground);
		group.getChildren().add(tabInterface);
		tabInterface.setVisible(true);
		
	}
	
	private void drawItemScrollArea() {
		geSearch = new ImageView(new Image("GE_SEARCH_V2.png"));

		tabInterface.setTranslateX(0);
		tabInterface.setTranslateY(91);
		
		geSearch.setX(0);
		geSearch.setY(405);
		geSearch.setFitWidth(750);
		geSearch.setFitHeight(225);
		geSearch.setRotate(180);
		
		
		
	}
	
	private void drawInventoryMenu() {
		inventory = new ImageView(new Image("Inventory_2.PNG"));
		inventory.setX(746);
		inventory.setY(210);
		inventory.setFitWidth(325);
		inventory.setFitHeight(425);
		
	}

	private void drawItemTopMenu() {
		//Item Menu Border
		graphBackground = new ImageView(new Image("Inventory_2.PNG"));
		graphBackground.setX(746);
		graphBackground.setY(0);
		graphBackground.setFitWidth(325);
		graphBackground.setFitHeight(215);

	
	}
	
	protected void setItemTopMenu(InputStream input) {

		//Item Menu Icon
		System.out.println("setItemTopMenu InputStream: " + input);
		 Image image = new Image(input);
		 itemIconPaneImage = new ImageView(image);
		 itemIconPaneImage.setPreserveRatio(true);
		 itemIconPaneImage.setFitHeight(200);
		 itemIconPaneImage.setFitWidth(200);
		 itemIconPaneImage.setStyle("-fx-background-color: BLACK");
		 itemTopMenuLabel = new Label("",itemIconPaneImage);
		 itemTopMenuLabel.setTranslateX(200);
		 itemTopMenuLabel.setTranslateY(0);
		 tabInterface.getChildren().add(itemTopMenuLabel);
	}
	
	protected void setItemTopMenuError() {

		//Item Menu Icon
		 Image image = new Image("Item_UnAvailable.png");
		 itemIconPaneImage = new ImageView(image);
		 itemIconPaneImage.setPreserveRatio(true);
		 itemIconPaneImage.setFitHeight(75);
		 itemIconPaneImage.setFitWidth(75);
		 itemIconPaneImage.setStyle("-fx-background-color: BLACK");
		 //itemIconPaneImage.setCache(true);
		 itemTopMenuLabel = new Label("",itemIconPaneImage);
		 itemTopMenuLabel.setTranslateX(980);
		 itemTopMenuLabel.setTranslateY(8);
		 tabInterface.getChildren().add(itemTopMenuLabel);
	}


}

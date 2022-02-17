package main_pod;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.nio.channels.NonWritableChannelException;

import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.util.Duration;

public class paneInterface extends DisplayController {

	ImageView imageView ;
	ImageView geSearch;
	ImageView inventory;
	ImageView graphBackground;
	//Label itemTopMenuLabel;	
	ImageView itemIconPaneImage;
	Image image;
	protected Tooltip pane_Tooltip;

	private String name;
	private String id;
	private String type;
	private String typeIcon;
	private String description;
	private String members;
	private String currentTrend;
	private String currentPrice;
	private String todayTrend;
	private String todayPrice;
	private String day30;
	private String day30_trend;
	private String day30_change;
	private String day90;
	private String day90_trend;
	private String day90_change;
	private String day180;
	private String day180_trend;
	private String day180_change;
	

	private Pane tabInterface = new Pane();
	
	paneInterface()
	
	{
		pane_activateInterface();
	}
	
	protected void pane_setVisibleInterface(boolean bool)
	{
		tabInterface.setVisible(bool);
	}
	
	protected void pane_activateInterface() {
		
		System.out.println("activateInterface");
		pane_drawItemScrollArea();
		pane_drawInventoryMenu();
		pane_drawItemTopMenu();
		tabInterface.getChildren().add(geSearch);
		tabInterface.getChildren().add(inventory);
		tabInterface.getChildren().add(graphBackground);
		group.getChildren().add(tabInterface);
		tabInterface.setVisible(true);
		
	}
	
	private void pane_drawItemScrollArea() {
		geSearch = new ImageView(new Image("GE_SEARCH_V6.png"));

		tabInterface.setTranslateX(0);
		tabInterface.setTranslateY(91);
		
		geSearch.setX(0);
		geSearch.setY(405);
		geSearch.setFitWidth(750);
		geSearch.setFitHeight(225);
		geSearch.setRotate(180);
		
		
		
	}
	
	private void pane_drawInventoryMenu() {
		inventory = new ImageView(new Image("Inventory_2.PNG"));
		inventory.setX(746);
		inventory.setY(210);
		inventory.setFitWidth(325);
		inventory.setFitHeight(425);
		
	}

	private void pane_drawItemTopMenu() {
		//Item Menu Border
		graphBackground = new ImageView(new Image("Inventory_2.PNG"));
		graphBackground.setX(746);
		graphBackground.setY(0);
		graphBackground.setFitWidth(325);
		graphBackground.setFitHeight(215);

	
	}
	
	protected void pane_setItemTopMenu(Image input) {
		//Item Menu Icon
		 System.out.println("setItemTopMenu InputStream: " + input);
		 itemIconPaneImage = new ImageView(input);
		 itemIconPaneImage.setPreserveRatio(true);
		 itemIconPaneImage.setFitHeight(100);
		 itemIconPaneImage.setFitWidth(100);
		 itemIconPaneImage.setX(960);
		 itemIconPaneImage.setY(-5);
		 tabInterface.getChildren().add(itemIconPaneImage);
		 itemIconPaneImage.setOnMousePressed((mouseEvent) -> System.out.println("Teeehee clicked me"));
		 pane_iconTooltip("Icon!");
	}
	
	private void pane_setItemTopMenuError() {

		//Item Menu Icon
		 image = new Image("Item_UnAvailable.png");
		 itemIconPaneImage = new ImageView(image);
		 itemIconPaneImage.setPreserveRatio(true);
		 itemIconPaneImage.setFitHeight(75);
		 itemIconPaneImage.setFitWidth(75);
		 itemIconPaneImage.setStyle("-fx-background-color: BLACK");
		 itemIconPaneImage.setX(960);
		 itemIconPaneImage.setY(-5);
		 //itemIconPaneImage.setCache(true);
		 tabInterface.getChildren().add(itemIconPaneImage);
		 pane_iconTooltip("Item unavailable or unable to retrieve item");
	}
	
	protected void pane_setItemTopMenuVisible(boolean b){
		
		itemIconPaneImage.setVisible(b);
	}

	protected void pane_iconTooltip (String s)
	{
		pane_Tooltip = new Tooltip(s);
		pane_Tooltip.setShowDelay(Duration.millis(100));
		//pane_Tooltip.setId("tooltip");
		pane_Tooltip.install(itemIconPaneImage, pane_Tooltip);
		
	}
	

}

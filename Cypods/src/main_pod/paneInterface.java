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

	Label name;
	Label id;
	Label type;
	Label typeIcon;
	Label description;
	Label members;
	Label currentTrend;
	Label currentPrice;
	Label todayTrend;
	Label todayPrice;
	Label day30;
	Label day30_trend;
	Label day30_change;
	Label day90;
	Label day90_trend;
	Label day90_change;
	Label day180;
	Label day180_trend;
	Label day180_change;
	

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
		initLabels();
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
	
	private void initLabels() {
		Label name  = new Label("-");
		 name.setTranslateX(900);
		 name.setTranslateY(10);
		 name.setStyle("#label");
		 
		Label id  = new Label("-");
		 id.setTranslateX(900);
		 id.setTranslateY(15);
		 
		 
		Label description  = new Label("-");
		 description.setTranslateX(900);
		 description.setTranslateY(20);
		 
		Label members = new Label("");
		 members.setTranslateX(900);
		 members.setTranslateY(25);
		 
		Label currentTrend = new Label("-");
		 currentTrend.setTranslateX(900);
		 currentTrend.setTranslateY(30);
		 
		Label currentPrice = new Label("-");
		 currentPrice.setTranslateX(900);
		 currentPrice.setTranslateY(35);
		 
		Label todayTrend = new Label("-");
		 todayTrend.setTranslateX(900);
		 todayTrend.setTranslateY(40);
		 
		Label todayPrice = new Label("-");
		 todayPrice.setTranslateX(900);
		 todayPrice.setTranslateY(45);
		 
		Label day30 = new Label("-");
		 day30.setTranslateX(900);
		 day30.setTranslateY(50);
		 
		Label day30_trend  = new Label("-");
		 day30_trend.setTranslateX(900);
		 day30_trend.setTranslateY(55);
		 
		Label day30_change  = new Label("-");
		 day30_change.setTranslateX(900);
		 day30_change.setTranslateY(60);   
		 
		Label day90   = new Label("-");
		 day90.setTranslateX(900);
		 day90.setTranslateY(65);
		 
		Label day90_trend  = new Label("-");
		 day90_trend.setTranslateX(900);
		 day90_trend.setTranslateY(70);
		 
		Label day90_change  = new Label("-");
		 day90_change.setTranslateX(900);
		 day90_change.setTranslateY(75);
		 
		Label day180  = new Label("-");
		 day180.setTranslateX(900);
		 day180.setTranslateY(80);
		 
		Label day180_trend  = new Label("-");
		 day180_trend.setTranslateX(900);
		 day180_trend.setTranslateY(85);
		 
		Label day180_change  = new Label("-");
		 day180_change.setTranslateX(900);
		 day180_change.setTranslateY(90);
		 tabInterface.getChildren().addAll(name
				 ,id
				 ,description
				 ,members
				 ,currentTrend
				 ,currentPrice
				 ,todayTrend
				 ,todayPrice
				 ,day30
				 ,day30_trend
				 ,day30_change
				 ,day90
				 ,day90_trend
				 ,day90_change
				 ,day180
				 ,day180_trend
				 ,day180_change
);
		 
	}

}

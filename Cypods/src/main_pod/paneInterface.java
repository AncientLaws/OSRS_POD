package main_pod;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.net.URL;
import java.nio.channels.NonWritableChannelException;

import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
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
	Label currentPrice;
	Label todayPrice;
	Label day30_change;
	Label day90_change;
	Label day180_change;
	
	Label changeToday;
	Label change30Days;
	Label change90Days;
	Label change180Days;
	
	Label xyCoordinates;
	private EventHandler<MouseEvent> mouseMovedHandler ;
	private EventHandler<KeyEvent> textInputListener ;
	
	TextField itemSearchInput;
	String pane_ItemSearchInputText;
	Font f;
	private Pane tabInterface = new Pane();
	
	paneInterface()	{
		pane_activateInterface();
	}
	
	protected void pane_setVisibleInterface(boolean bool){
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
		initTextField();
		group.getChildren().add(tabInterface);
		tabInterface.setVisible(true);
		
	
	}
	
	private void createMonitoredLabel() {
	   
      mouseMovedHandler = event -> {String msg =
	          "(x: "       + event.getX()      + ", y: "       + event.getY()       + ")";

	        xyCoordinates.setText(msg);
	      };
		
	      tabInterface.addEventHandler(MouseEvent.MOUSE_MOVED, mouseMovedHandler);
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
		inventory.setY(110);
		inventory.setFitWidth(325);
		inventory.setFitHeight(525); //425
		
	}

	private void pane_drawItemTopMenu() {
		//Item Menu Border
		graphBackground = new ImageView(new Image("Inventory_2.PNG"));
		graphBackground.setX(746);
		graphBackground.setY(0);
		graphBackground.setFitWidth(325);
		graphBackground.setFitHeight(115); //215

	
	}
	
	protected void pane_setItemTopMenu(Image input) {
		//Item Menu Icon
		System.out.println("START - pane_setItemTopMenu(Image input)");
		tabInterface.getChildren().remove(itemIconPaneImage);
		 try {System.out.println("setItemTopMenu InputStream: " + input);
		 itemIconPaneImage = new ImageView(input);
		 itemIconPaneImage.setPreserveRatio(true);
		 itemIconPaneImage.setFitHeight(100);
		 itemIconPaneImage.setFitWidth(100);
		 itemIconPaneImage.setLayoutX(960);
		 itemIconPaneImage.setLayoutY(-5);
		 tabInterface.getChildren().add(itemIconPaneImage);
		 itemIconPaneImage.setOnMousePressed((mouseEvent) -> System.out.println("Teeehee clicked me"));
		 pane_iconTooltip("Icon!");
		 System.out.println("END - pane_setItemTopMenu(Image input)");
		 }
		 catch(Exception e) {
			 System.out.println("ERROR - pane_setItemTopMenu(Image input)");
			 pane_setItemTopMenuError();
		 }
	}
	
	protected void pane_setItemTopMenuError() {
		
		 System.out.println("pane_setItemTopMenuError()");
		tabInterface.getChildren().remove(itemIconPaneImage);
		//Item Menu Icon
		 image = new Image("Item_UnAvailable.png");
		 itemIconPaneImage = new ImageView(image);
		 itemIconPaneImage.setPreserveRatio(true);
		 itemIconPaneImage.setFitHeight(75);
		 itemIconPaneImage.setFitWidth(75);
		 itemIconPaneImage.setStyle("-fx-background-color: BLACK");
		 itemIconPaneImage.setLayoutX(960);
		 itemIconPaneImage.setLayoutY(5);
		 itemIconPaneImage.setCache(true);
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
	
	private void initTextField() {
		f = new Font("runescape_uf.ttf", 12);
		//f.loadFont(, 50);
		
		itemSearchInput = new TextField("Click me to start searching");
		//itemSearchInput.setStyle("-fx-text-fill: orange; -fx-font-size: 20px; -fx-font-weight: bold");
		itemSearchInput.setOpacity(1);
		itemSearchInput.setBackground(new Background(new BackgroundFill(Color.rgb(201,182,147),null,null))) ;
		itemSearchInput.setLayoutX(8);
		itemSearchInput.setLayoutY(411);
		itemSearchInput.setPrefWidth(734);
		itemSearchInput.setFont(f);
		itemSearchInput.setStyle("-fx-text-fill: blue; -fx-font-size: 13px; -fx-font-weight: bold;-fx-font-family: runescape_uf");
		itemSearchInput.setAlignment(Pos.CENTER);
		//itemSearchInput.setPromptText("Begin typing to search");
		itemSearchInput.setOnMousePressed((mouseEvent) -> itemSearchInput.setText(""));
		itemSearchInput.setFocusTraversable(false);
		tabInterface.getChildren().add(itemSearchInput);
		
			
	}
	
	/*
	 * private void createTextFieldListener() { textInputListener = event ->{
	 * if(event.getCode().equals(KeyCode.ENTER)) { pane_ItemSearchInputText =
	 * itemSearchInput.getText(); } };
	 * tabInterface.addEventHandler(KeyEvent.KEY_PRESSED, textInputListener); }
	 */

	private void initLabels() {
		xyCoordinates = new Label("Coordinates");
		xyCoordinates.setTranslateX(5);
		xyCoordinates.setTranslateY(375);
		xyCoordinates.setStyle("-fx-text-fill: orange; -fx-font-size: 20px; -fx-font-weight: bold");
		
		
		 name  = new Label("-");
		 name.setTranslateX(763);
		 name.setTranslateY(10);
		 name.setStyle("-fx-text-fill: orange; -fx-font-size: 20px; -fx-font-weight: bold");
		 
		 currentPrice = new Label("-");
		 currentPrice.setTranslateX(980);
		 currentPrice.setTranslateY(85);
		 currentPrice.setStyle("-fx-text-fill: white; -fx-font-size: 16px; -fx-font-weight: bold");
		 
		 id  = new Label("");
		 id.setTranslateX(825);
		 id.setTranslateY(190);
		 
		 
		 description  = new Label("-");
		 description.setTranslateX(763);
		 description.setTranslateY(50);
		 description.setWrapText(true);
		 description.setStyle("-fx-text-fill: white; -fx-font-size: 15px;");
		 description.setMaxWidth(205);
		
		 
		 members = new Label("");
		 members.setTranslateX(990);
		 members.setTranslateY(135);
		 
		 todayPrice = new Label("-");
		 todayPrice.setTranslateX(850);
		 todayPrice.setTranslateY(135);
		 
		 changeToday = new Label("Change today:");
		 changeToday.setTranslateX(763);
		 changeToday.setTranslateY(135);
		 
		 day30_change  = new Label("-");
		 day30_change.setTranslateX(820);
		 day30_change.setTranslateY(165);   
		 
		 change30Days = new Label("30 days: ");
		 change30Days.setTranslateX(763);
		 change30Days.setTranslateY(165);   

		 day90_change  = new Label("-");
		 day90_change.setTranslateX(820);
		 day90_change.setTranslateY(195);

		 change90Days = new Label("90 days: ");
		 change90Days.setTranslateX(763);
		 change90Days.setTranslateY(195);
		 
		 day180_change  = new Label("-");
		 day180_change.setTranslateX(820);
		 day180_change.setTranslateY(225);
		 
		 change180Days = new Label("180 days: ");
		 change180Days.setTranslateX(763);
		 change180Days.setTranslateY(225);
		 
		 tabInterface.getChildren().addAll(name
				 ,id
				 ,description
				 ,members
				 ,currentPrice
				 ,todayPrice
				 ,day30_change
				 ,day90_change
				 ,day180_change
				 
				 ,changeToday
				 ,change30Days
				 ,change90Days
				 ,change180Days
				 
				 ,xyCoordinates	);
		 
	}
	
	protected void setLabels(
			 String name1
			,String id1
			,String description1
			,String members1
			,String currentPrice1
			,String currentTrend1
			,String todayPrice1
			,String todayTrend1
			,String day30_trend1
			,String day30_change1
			,String day90_trend1
			,String day90_change1
			,String day180_trend1
			,String day180_change1) 
	{
		createMonitoredLabel();
		name.setText(name1);
		//id.setText(id1);
		description.setText(description1);
		currentPrice.setText(currentPrice1);
		
		if(members1.equals("true")) 
			{members.setText("Members");
		}
		else { 
			members.setText("Free-to-Play");
		}
		
		if (todayTrend1.equals("negative")) {
			
			todayPrice.setText(todayPrice1);
			todayPrice.setStyle("-fx-text-fill: red;");
		}
		else {
			todayPrice.setText(todayPrice1);
			todayPrice.setStyle("-fx-text-fill: green;");
		};
		
		if (day30_trend1.equals("negative")) {
			day30_change.setText(day30_change1);
			day30_change.setStyle("-fx-text-fill: red;");
		}
		else {
			day30_change.setText(day30_change1);
			day30_change.setStyle("-fx-text-fill: green;");
		};
		
		if (day90_trend1.equals("negative")) {
			day90_change.setText(day90_change1);
			day90_change.setStyle("-fx-text-fill: red;");
		}
		else {
			day90_change.setText(day90_change1);
			day90_change.setStyle("-fx-text-fill: green;");
		};

		if (day180_trend1.equals("negative")) {
			day180_change.setText(day180_change1);
			day180_change.setStyle("-fx-text-fill: red;");
		}
		else {
			day180_change.setText(day180_change1);
			day180_change.setStyle("-fx-text-fill: green;");
		};

	}

}

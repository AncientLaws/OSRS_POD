package com.cypods.geBuddy;

import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.control.*;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import org.springframework.stereotype.Component;

import static com.cypods.geBuddy.ApplicationConstant.DEBUG;
import static com.cypods.geBuddy.Window.root;

@Component
public class PaneInterface extends DisplayController implements Runnable {

	/************************** Images **************************/
	Image image;
	ImageView inventory;
	ImageView graphBackground;
	ImageView itemTopMenu;
	ImageView itemIconPaneImage;
	Rectangle clipRect = new Rectangle();

	/************************** Labels **************************/
	Label name;
	Label id;
	//Label type;
	//Label typeIcon;
	Label description;
	Label members;
	Label currentPrice_bigLabel;
	Label currentPrice_priceLabel;
	Label currentPrice_descLabel;
	Label todayPrice;
	Label day30_change;
	Label day90_change;
	Label day180_change;

	Label changeToday;
	Label change30Days;
	Label change90Days;
	Label change180Days;

	Label xyCoordinates;

	GeSearchArea geSearchArea;

//	HashMap<String, GeSearchResultLabel> geSearchResultLabelMap = geSearchArea.getGeSearchResultLabelMap();

	/*************** Buttons ********************/
	ToggleButton day;
	ToggleButton week;
	ToggleButton quarter;
	ToggleButton months3;
	ToggleButton months6;
	ToggleGroup toggleGroup;
	//HBox box;
	ButtonBar buttonBarLeft;
	ButtonBar buttonBarRight;

	/*************** Event handlers ********************/
	private EventHandler<MouseEvent> mouseMovedHandler;

	/*************** Classes /other declarations **********/
	protected Tooltip pane_Tooltip;
	Font f;

	protected AnchorPane tabInterface = new AnchorPane();
	//protected Pane chartPane = new Pane();
	
	int delayW = 25;
	
	Charts cp;


	//StockChart stockChart = new StockChart("Stock chart");
	//ChartViewer chartViewer;

	/*************** End variable declaration **************/

	public PaneInterface() {
		pane_activateInterface();
	}

	protected void pane_setVisibleInterface(boolean bool) {
		tabInterface.setVisible(bool);

	}

	protected void pane_activateInterface() {
		if(DEBUG == true) {System.out.println("activateInterface");}
		tabInterface.setTranslateX(0);
		tabInterface.setTranslateY(91);		
		tabInterface.setPrefWidth(1080);
		tabInterface.setStyle("-fx-border-color: green");

		// Bind the Rectangle's dimensions to the Pane's dimensions
		clipRect.widthProperty().bind(tabInterface.widthProperty());
		clipRect.heightProperty().bind(tabInterface.heightProperty());

		// Set the Rectangle as the clip of the Pane
		tabInterface.setClip(clipRect);
     	 
     	cp = new Charts(tabInterface.getPrefWidth(), tabInterface.getPrefHeight());

		geSearchArea = new GeSearchArea(tabInterface);
		geSearchArea.geSearchArea_initGeSearchLabels(tabInterface);
		geSearchArea.initTextField(tabInterface);


		pane_drawItemScrollArea();
		pane_drawInventoryMenu();

		pane_drawChartArea();
		pane_initLabels();

		pane_createChart();
		
		pane_createButtons();

		root.getChildren().add(tabInterface);
		/**Anchoring tabInterface to root so it's dynamically resized*/
		AnchorPane.setTopAnchor(tabInterface,0.0);
		AnchorPane.setBottomAnchor(tabInterface,0.0);
		AnchorPane.setLeftAnchor(tabInterface,0.0);
		AnchorPane.setRightAnchor(tabInterface,0.0);

		tabInterface.setVisible(true);

	}

	private void createMonitoredLabel() {
		mouseMovedHandler = event -> {
			String msg = "(x: " + event.getX() + ", y: " + event.getY() + ")";
			xyCoordinates.setText(msg);
		};
		tabInterface.addEventHandler(MouseEvent.MOUSE_MOVED, mouseMovedHandler);
	}

	private void pane_drawItemScrollArea() {
		if(DEBUG == true) {System.out.println("geSearchArea_drawItemScrollArea");}
		tabInterface.getChildren().add(geSearchArea.getGeSearchAreaPane());
	}

	private void pane_drawInventoryMenu() {
		if(DEBUG == true) {System.out.println("pane_drawInventoryMenu");}
		inventory = new ImageView(new Image(getClass().getClassLoader().getResource("images/gePriceGuideSidebar3.png").toString(),true));
		inventory.setX(747);
		inventory.setY(404);
		inventory.setFitWidth(321);
		inventory.setFitHeight(223); // 425
		tabInterface.getChildren().add(inventory);
	}

	private void pane_drawItemTopMenuArea() {
		// Item Menu Border
		if(DEBUG == true) {System.out.println("pane_drawItemTopMenuArea");}
		itemTopMenu = new ImageView(new Image(getClass().getClassLoader().getResource("images/Inventory_2.PNG").toString(),true));
		itemTopMenu.setX(746);
		itemTopMenu.setY(0);
		itemTopMenu.setFitWidth(325);
		itemTopMenu.setFitHeight(115); // 215
		tabInterface.getChildren().add(itemTopMenu);
	}

	private void pane_drawChartArea() {
		if(DEBUG == true) {System.out.println("pane_drawChartArea");}
		graphBackground = new ImageView(new Image(getClass().getClassLoader().getResource("images/chartArea3.png").toString(),true));
		graphBackground.setX(4);
		graphBackground.setY(0);
		graphBackground.setFitWidth(1065);
		graphBackground.setFitHeight(404); // 215
		try {tabInterface.getChildren().add(graphBackground);
			
		} catch (Exception e) {
			System.out.println("Error adding background to tabInterface in method pane_drawChartArea");
		}
		if(DEBUG == true) {System.out.println("end pane_drawChartArea");}
	}
	
	
	protected void pane_setItemTopMenu(Image input) {
		// Item Menu Icon 
		if(DEBUG == true) {System.out.println("START - pane_setItemTopMenu(Image input)");}
		tabInterface.getChildren().remove(itemIconPaneImage);
		try {
			if(DEBUG == true) {System.out.println("setItemTopMenu InputStream: " + input);}
			itemIconPaneImage = new ImageView(input);
			itemIconPaneImage.setPreserveRatio(true);
			itemIconPaneImage.setFitHeight(50);
			itemIconPaneImage.setFitWidth(50);
			itemIconPaneImage.setLayoutX(750);
			itemIconPaneImage.setLayoutY(400);
			tabInterface.getChildren().add(itemIconPaneImage);
			itemIconPaneImage.setOnMousePressed((mouseEvent) -> System.out.println("Teeehee clicked me"));
			pane_iconTooltip("Icon!");
			if(DEBUG == true) {System.out.println("END - pane_setItemTopMenu(Image input)");}
		} catch (Exception e) {
			System.out.println("ERROR - pane_setItemTopMenu(Image input)");
			pane_setItemTopMenuError();
		}
	}

	protected void pane_setItemTopMenuError() {

		if(DEBUG == true) {System.out.println("pane_setItemTopMenuError()");}
		tabInterface.getChildren().remove(itemIconPaneImage);
		image = new Image(getClass().getClassLoader().getResource("images/Item_UnAvailable.png").toString(),true);
		itemIconPaneImage = new ImageView(image);
		itemIconPaneImage.setPreserveRatio(true);
		itemIconPaneImage.setFitHeight(75);
		itemIconPaneImage.setFitWidth(75);
		itemIconPaneImage.setStyle("-fx-background-color: BLACK");
		itemIconPaneImage.setLayoutX(960);
		itemIconPaneImage.setLayoutY(5);
		itemIconPaneImage.setCache(true);
		tabInterface.getChildren().add(itemIconPaneImage);
		if(DEBUG == true) {pane_iconTooltip("Item unavailable or unable to retrieve item");}
	}

	protected void pane_setItemTopMenuVisible(boolean b) {

		itemIconPaneImage.setVisible(b);
	}

	protected void pane_iconTooltip(String s) {
		pane_Tooltip = new Tooltip(s);
		// pane_Tooltip.setShowDelay(Duration.millis(100));
		pane_Tooltip.install(itemIconPaneImage, pane_Tooltip);

	}
	
	/**
	 * Method to used to create charts with default settings.
	 * Method created to handle dynamic chart size in cases where the user is resizing the window. This method is handled
	 * by a new thread to increase performance
	 * */
	protected void pane_createChart() {
		if(DEBUG == true) {System.out.println("pane_createChart()");}
		Platform.runLater(()->{
				tabInterface.getChildren().removeAll(cp.charts_chartViewerPrice(), cp.charts_chartViewerVolume());
				tabInterface.getChildren().addAll(cp.charts_chartViewerPrice(), cp.charts_chartViewerVolume());
		});
	}
	
	public void pane_updateChart(int itemID, String timePeriod) {
		Platform.runLater(()->{
			tabInterface.getChildren().removeAll(cp.charts_chartViewerPrice(), cp.charts_chartViewerVolume());
			cp.runChart(itemID, timePeriod);
			tabInterface.getChildren().addAll(cp.charts_chartViewerPrice(), cp.charts_chartViewerVolume());
		});
	}

	/**
	 * A method that initializes all the labels in the main pane interface
	 * (Location, style, etc)
	 **/
	private void pane_initLabels() {
		if(DEBUG == true) {System.out.println("pane_initLabels");}
		xyCoordinates = new Label("Coordinates");
		xyCoordinates.setTranslateX(10);
		//xyCoordinates.setTranslateY(375);
		xyCoordinates.setTranslateY(585);
		xyCoordinates.setStyle("-fx-text-fill: orange; -fx-font-size: 20px; -fx-font-weight: bold");

		name = new Label("Item");
		name.setTranslateX(500);
		name.setTranslateY(4);
		name.setStyle("-fx-text-fill: orange; -fx-font-size: 30px; -fx-font-weight: bold");
		name.getStyleClass().add("labelAll");

		currentPrice_bigLabel = new Label(null);
		currentPrice_bigLabel.setTranslateX(980);
		currentPrice_bigLabel.setTranslateY(85);
		currentPrice_bigLabel.setStyle("-fx-text-fill: white; -fx-font-size: 16px; -fx-font-weight: bold");
		currentPrice_bigLabel.getStyleClass().add("labelAll");

		id = new Label("");
		id.setTranslateX(825);
		id.setTranslateY(190);

		description = new Label(null);
		//description.setTranslateX(763);
		//description.setTranslateY(50);
		description.setTranslateX(800);
		description.setTranslateY(405);
		description.setWrapText(true);
		description.setStyle("-fx-text-fill: white; -fx-font-size: 13px;");
		description.setMaxWidth(260);
		description.getStyleClass().add("labelAll");

		members = new Label("");
		members.setTranslateX(990);
		members.setTranslateY(135);
		
		currentPrice_descLabel = new Label("Current Price: ");
		currentPrice_descLabel.setTranslateX(755);
		currentPrice_descLabel.setTranslateY(447);
		currentPrice_descLabel.setStyle("-fx-text-fill: orange;");
		currentPrice_descLabel.getStyleClass().add("labelAll");
		
		currentPrice_priceLabel = new Label(null);
		currentPrice_priceLabel.setTranslateX(842);
		currentPrice_priceLabel.setTranslateY(447);
		currentPrice_priceLabel.getStyleClass().add("labelAll");
		
		todayPrice = new Label(null);
		todayPrice.setTranslateX(842);
		todayPrice.setTranslateY(473);
		todayPrice.getStyleClass().add("labelAll");

		changeToday = new Label("Change today:");
		changeToday.setTranslateX(755);
		changeToday.setTranslateY(472);
		changeToday.setStyle("-fx-text-fill: orange;");
		changeToday.getStyleClass().add("labelAll");

		day30_change = new Label(null);
		day30_change.setTranslateX(812);
		day30_change.setTranslateY(500);
		day30_change.getStyleClass().add("labelAll");

		change30Days = new Label("30 days: ");
		change30Days.setTranslateX(755);
		change30Days.setTranslateY(500);
		change30Days.setStyle("-fx-text-fill: orange;");
		change30Days.getStyleClass().add("labelAll");

		day90_change = new Label(null);
		day90_change.setTranslateX(812);
		day90_change.setTranslateY(528);
		day90_change.getStyleClass().add("labelAll");

		change90Days = new Label("90 days: ");
		change90Days.setTranslateX(755);
		change90Days.setTranslateY(528);
		change90Days.setStyle("-fx-text-fill: orange;");
		change90Days.getStyleClass().add("labelAll");

		day180_change = new Label(null);
		day180_change.setTranslateX(812);
		day180_change.setTranslateY(556);
		day180_change.getStyleClass().add("labelAll");

		change180Days = new Label("180 days: ");
		change180Days.setTranslateX(755);
		change180Days.setTranslateY(556);
		change180Days.setStyle("-fx-text-fill: orange;");
		change180Days.getStyleClass().add("labelAll");

		tabInterface.getChildren().addAll(name, id, description, members, currentPrice_bigLabel,currentPrice_descLabel,currentPrice_priceLabel ,todayPrice, day30_change,
				day90_change, day180_change

				, changeToday, change30Days, change90Days, change180Days

				//, xyCoordinates
		);

	}

	/**
	 * A method that sets the labels in the main pane interface.
	 **/

	protected void setLabels(String name1, String id1, String description1, String members1, String currentPrice1,
			String currentTrend1, String todayPrice1, String todayTrend1, String day30_trend1, String day30_change1,
			String day90_trend1, String day90_change1, String day180_trend1, String day180_change1) {

		createMonitoredLabel();
		name.setText(name1);
		name.setLayoutX(-((name.getText().length()*10)/2)); //Attempt at centering title
		description.setText(description1);
		currentPrice_bigLabel.setText(currentPrice1);
		currentPrice_priceLabel.setText(currentPrice1);

		if (members1.equals("true")) {
			members.setText("Members");
		} else {
			members.setText("Free-to-Play");
		}

		if (todayTrend1.equals("negative")) {
			todayPrice.setText(todayPrice1);
			todayPrice.setStyle("-fx-text-fill: red;");
		} else {
			todayPrice.setText(todayPrice1);
			todayPrice.setStyle("-fx-text-fill: rgb(0,255,0);");
		}
		;

		if (day30_trend1.equals("negative")) {
			day30_change.setText(day30_change1);
			day30_change.setStyle("-fx-text-fill: red;");
		} else {
			day30_change.setText(day30_change1);
			day30_change.setStyle("-fx-text-fill: rgb(0,255,0);");
		}
		;

		if (day90_trend1.equals("negative")) {
			day90_change.setText(day90_change1);
			day90_change.setStyle("-fx-text-fill: red;");
		} else {
			day90_change.setText(day90_change1);
			day90_change.setStyle("-fx-text-fill: rgb(0,255,0);");
		}
		;

		if (day180_trend1.equals("negative")) {
			day180_change.setText(day180_change1);
			day180_change.setStyle("-fx-text-fill: red;");
		} else {
			day180_change.setText(day180_change1);
			day180_change.setStyle("-fx-text-fill: rgb(0,255,0);");
		}
		;
		
		if (currentPrice1.equals("negative")) {
			currentPrice_priceLabel.setText(currentPrice1);
			currentPrice_priceLabel.setStyle("-fx-text-fill: white;");
		} else {
			currentPrice_priceLabel.setText(currentPrice1);
			currentPrice_priceLabel.setStyle("-fx-text-fill: white;");
		}
		;

	}

	private void pane_createButtons() {
		day 		= new ToggleButton("1 Day");
		week		= new ToggleButton("2 Week");
		quarter 	= new ToggleButton("Quarter");
		months3		= new ToggleButton("6 Months");
		months6		= new ToggleButton("1 Year");
			
		day.setPrefSize(70, 10);
		week.setPrefSize(70, 10);
		quarter.setPrefSize(70, 10);
		months3.setPrefSize(80, 10);
		months6.setPrefSize(70, 10);
		
		day.getStyleClass().add("button");
		week.getStyleClass().add("button");
		quarter.getStyleClass().add("button");
		months3.getStyleClass().add("button");
		months6.getStyleClass().add("button");
		
		day.setToggleGroup(toggleGroup);
		week.setToggleGroup(toggleGroup);
		quarter.setToggleGroup(toggleGroup);
		months3.setToggleGroup(toggleGroup);
		months6.setToggleGroup(toggleGroup);
		
		buttonBarLeft = new ButtonBar();
		buttonBarLeft.setButtonData(quarter, ButtonData.APPLY);
		buttonBarLeft.setButtonData(months3, ButtonData.APPLY);
		buttonBarLeft.setButtonData(months6, ButtonData.APPLY);
		
		buttonBarRight = new ButtonBar();
		buttonBarRight.setButtonData(day, ButtonData.APPLY);
		buttonBarRight.setButtonData(week, ButtonData.APPLY);

		buttonBarLeft.getButtons().addAll(day,week, quarter);
		buttonBarRight.getButtons().addAll(months3,months6);
		

		
		buttonBarRight.setTranslateX(870);
		buttonBarRight.setTranslateY(13);
		
		buttonBarLeft.setTranslateX(-5);
		buttonBarLeft.setTranslateY(13);
		
		tabInterface.getChildren().addAll(buttonBarLeft,buttonBarRight);
		
	}
	
	/**
	 * Purpose: Update Chart size based on the size of the application (re-sizable mode)
	 * Note: Platform.runLater is used so the listener is added after all dependencies are initialized
	 * otherwise you get null pointer exception
	 * */
	public void syncChartSize() {
	
		
//	Platform.runLater(() -> {
//		 	w.primaryStage.widthProperty().addListener((obs, oldVal, newVal) -> {
//		 		//new Thread(() -> {
//		 		delayW++;
//		 		if((delayW % 3) == 0) {
//		 			//System.out.println("Starting WidthProperty thread in synchCHartSize....");
//		 		cp.resizeChartW(cp.charts_chartViewerPrice(), (double)newVal - 8);
//		 		cp.resizeChartW(cp.charts_chartViewerVolume(), (double)newVal - 8);
//		 		//}).start();
//		 		}
//		 });
////		 	w.primaryStage.heightProperty().addListener((obs, oldVal, newVal) -> {
////		 		//new Thread(() -> {
////		 		cp.resizeChartH(cp.charts_chartViewerPrice(), (double)newVal);
////		 		cp.resizeChartH(cp.charts_chartViewerVolume(), (double)newVal);
////		 		//});
////	 	 });
//
//	});
	
	}

	@Override
	public void run() {
		syncChartSize();
		
	}

	public Rectangle getClipRect() {
		return clipRect;
	}

	public void setClipRect(Rectangle clipRect) {
		this.clipRect = clipRect;
	}
}
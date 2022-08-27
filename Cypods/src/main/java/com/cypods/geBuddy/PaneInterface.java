package com.cypods.geBuddy;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.net.URL;
import java.nio.channels.NonWritableChannelException;

import org.jfree.chart.ChartPanel;
import org.jfree.chart.fx.ChartViewer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javafx.application.Platform;
import javafx.embed.swing.SwingNode;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Tooltip;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.effect.Effect;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.util.Duration;

@Component
public class PaneInterface extends DisplayController {

	/************************** Images **************************/
	Image image;
	ImageView imageView;
	ImageView geSearch;
	ImageView inventory;
	ImageView graphBackground;
	ImageView itemTopMenu;
	ImageView itemIconPaneImage;
	ImageView img1 = new ImageView(image);
	ImageView img2 = new ImageView(image);
	ImageView img3 = new ImageView(image);
	ImageView img4 = new ImageView(image);
	ImageView img5 = new ImageView(image);
	ImageView img6 = new ImageView(image);
	ImageView img7 = new ImageView(image);
	ImageView img8 = new ImageView(image);
	ImageView img9 = new ImageView(image);
	ImageView img10 = new ImageView(image);
	ImageView img11 = new ImageView(image);
	ImageView img12 = new ImageView(image);

	/************************** Labels **************************/
	Label name;
	Label id;
	Label type;
	Label typeIcon;
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

	Label geSearchResult1;
	Label geSearchResult2;
	Label geSearchResult3;
	Label geSearchResult4;
	Label geSearchResult5;
	Label geSearchResult6;
	Label geSearchResult7;
	Label geSearchResult8;
	Label geSearchResult9;
	Label geSearchResult10;
	Label geSearchResult11;
	Label geSearchResult12;

	String pane_ItemSearchInputText;
	/*************** Buttons ********************/
	ToggleButton day;
	ToggleButton week;
	ToggleButton month;
	ToggleButton months3;
	ToggleButton months6;
	ToggleGroup toggleGroup;
	HBox box;
	ButtonBar buttonBarLeft;
	ButtonBar buttonBarRight;
	
	/*************** Ge search area locations ********************/
	int row1X;
	int row2X;
	int row3X;
	int row4X;

	int row1Y;
	int row2Y;
	int row3Y;

	/*************** Event handlers ********************/
	private EventHandler<MouseEvent> mouseMovedHandler;
	private EventHandler<KeyEvent> textInputListener;

	/*************** Classes /other declarations **********/
	protected Tooltip pane_Tooltip;
	TextField itemSearchInput;
	Font f;

	protected Pane tabInterface = new Pane();
	protected Pane chartPane = new Pane();
	
	@Autowired
	Charts cp = new Charts();
	//ChartViewer chartViewer;

	/*************** End variable declaration **************/

	public PaneInterface() {
		System.out.println("Attempting to add application context........");
		 //cp = applicationContext.getBean(Charts.class);
		 System.out.println("added application context........");
		pane_activateInterface();
	}

	protected void pane_setVisibleInterface(boolean bool) {
		tabInterface.setVisible(bool);
	}

	protected void pane_activateInterface() {
		if(DEBUG == true) {System.out.println("activateInterface");}
		tabInterface.setTranslateX(0);
		tabInterface.setTranslateY(91);
		// tabInterface.setPrefSize(2000, 2000);

		pane_drawItemScrollArea();
		pane_drawInventoryMenu();

		pane_drawChartArea();
		// pane_drawItemTopMenuArea();
		pane_initLabels();
		initTextField();
		pane_initGeSearchLabels();
		pane_createChart();
		
		pane_createButtons();

		group.getChildren().add(tabInterface);
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
		if(DEBUG == true) {System.out.println("pane_drawItemScrollArea");}
		geSearch = new ImageView(new Image("/images/GE_SEARCH_V6.png"));
		geSearch.setX(0);
		geSearch.setY(405);
		geSearch.setFitWidth(750);
		geSearch.setFitHeight(225);
		geSearch.setRotate(180);
		tabInterface.getChildren().add(geSearch);
	}

	private void pane_drawInventoryMenu() {
		if(DEBUG == true) {System.out.println("pane_drawInventoryMenu");}
		inventory = new ImageView(new Image("/images/gePriceGuideSidebar3.png"));
		inventory.setX(747);
		inventory.setY(404);
		inventory.setFitWidth(321);
		inventory.setFitHeight(223); // 425
		tabInterface.getChildren().add(inventory);
	}

	private void pane_drawItemTopMenuArea() {
		// Item Menu Border
		if(DEBUG == true) {System.out.println("pane_drawItemTopMenuArea");}
		itemTopMenu = new ImageView(new Image("/images/Inventory_2.PNG"));
		itemTopMenu.setX(746);
		itemTopMenu.setY(0);
		itemTopMenu.setFitWidth(325);
		itemTopMenu.setFitHeight(115); // 215
		tabInterface.getChildren().add(itemTopMenu);
	}

	private void pane_drawChartArea() {
		if(DEBUG == true) {System.out.println("pane_drawChartArea");}
		graphBackground = new ImageView(new Image("/images/chartArea3.png"));
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
		image = new Image("/images/Item_UnAvailable.png");
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

	protected void pane_createChart() {
		if(DEBUG == true) {System.out.println("pane_createChart()");}
		
		tabInterface.getChildren().addAll(cp.charts_chartViewer());
	}
	
	public void pane_updateChart(int itemID, String timePeriod) {
		tabInterface.getChildren().remove(cp.charts_chartViewer());
		cp.runchart(itemID, timePeriod);
		tabInterface.getChildren().addAll(cp.charts_chartViewer());
	}

	/**
	 * Initializes input text field in the Ge search bar and adds mouse listener
	 * that will remove the initial prompt text
	 */
	private void initTextField() {
		if(DEBUG == true) {System.out.println("initTextField()");}
		f = new Font("runescape_uf.ttf", 12);
		itemSearchInput = new TextField("What would you like to buy?");
		itemSearchInput.end();
		itemSearchInput.setOpacity(1);
		itemSearchInput.setBackground(new Background(new BackgroundFill(Color.rgb(201, 182, 147), null, null)));
		itemSearchInput.setLayoutX(8);
		itemSearchInput.setLayoutY(411);
		itemSearchInput.setPrefWidth(734);
		itemSearchInput.setFont(f);
		itemSearchInput.setAlignment(Pos.CENTER);
		itemSearchInput.setPromptText("What would you like to buy?");
		if(DEBUG == true) {System.out.println("Caret Position: " + itemSearchInput.getCaretPosition());}
		itemSearchInput.setStyle(
				"-fx-text-fill: black; -fx-font-size: 13px; -fx-font-weight: bold;-fx-font-family: runescape_uf");
		// itemSearchInput.setOnMousePressed((mouseEvent) -> {
		// itemSearchInput.setText("");

		// });
		itemSearchInput.setFocusTraversable(false);
		tabInterface.getChildren().add(itemSearchInput);

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

				, xyCoordinates);

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

	protected void pane_initGeSearchLabels() {
		if(DEBUG == true) {System.out.println("pane_initGeSearchLabels()");}
		int iconWidth = 65;
		int iconHeight = 65;
		int sizeX = 175;
		int sizeY = 50;

		row1X = 14;
		row2X = 190;
		row3X = 366;
		row4X = 542;
		row1Y = 435;
		row2Y = 495;
		row3Y = 555;

		img1.setFitWidth(iconWidth);
		img1.setFitHeight(iconHeight);
		img2.setFitWidth(iconWidth);
		img2.setFitHeight(iconHeight);
		img3.setFitWidth(iconWidth);
		img3.setFitHeight(iconHeight);
		img4.setFitWidth(iconWidth);
		img4.setFitHeight(iconHeight);
		img5.setFitWidth(iconWidth);
		img5.setFitHeight(iconHeight);
		img6.setFitWidth(iconWidth);
		img6.setFitHeight(iconHeight);
		img7.setFitWidth(iconWidth);
		img7.setFitHeight(iconHeight);
		img8.setFitWidth(iconWidth);
		img8.setFitHeight(iconHeight);
		img9.setFitWidth(iconWidth);
		img9.setFitHeight(iconHeight);
		img10.setFitWidth(iconWidth);
		img10.setFitHeight(iconHeight);
		img11.setFitWidth(iconWidth);
		img11.setFitHeight(iconHeight);
		img12.setFitWidth(iconWidth);
		img12.setFitHeight(iconHeight);

		geSearchResult1 = new Label("");
		geSearchResult2 = new Label("");
		geSearchResult3 = new Label("");
		geSearchResult4 = new Label("");
		geSearchResult1.setTranslateX(row1X);
		geSearchResult2.setTranslateX(row2X);
		geSearchResult3.setTranslateX(row3X);
		geSearchResult4.setTranslateX(row4X);
		geSearchResult1.setTranslateY(row1Y);
		geSearchResult2.setTranslateY(row1Y);
		geSearchResult3.setTranslateY(row1Y);
		geSearchResult4.setTranslateY(row1Y);
		geSearchResult1.setPrefSize(sizeX, sizeY);
		geSearchResult2.setPrefSize(sizeX, sizeY);
		geSearchResult3.setPrefSize(sizeX, sizeY);
		geSearchResult4.setPrefSize(sizeX, sizeY);
		geSearchResult1.setWrapText(true);
		geSearchResult2.setWrapText(true);
		geSearchResult3.setWrapText(true);
		geSearchResult4.setWrapText(true);
		geSearchResult1.setGraphic(img1);
		geSearchResult2.setGraphic(img2);
		geSearchResult3.setGraphic(img3);
		geSearchResult4.setGraphic(img4);

		geSearchResult5 = new Label("");
		geSearchResult6 = new Label("");
		geSearchResult7 = new Label("");
		geSearchResult8 = new Label("");
		geSearchResult5.setTranslateX(row1X);
		geSearchResult6.setTranslateX(row2X);
		geSearchResult7.setTranslateX(row3X);
		geSearchResult8.setTranslateX(row4X);
		geSearchResult5.setTranslateY(row2Y);
		geSearchResult6.setTranslateY(row2Y);
		geSearchResult7.setTranslateY(row2Y);
		geSearchResult8.setTranslateY(row2Y);
		geSearchResult5.setPrefSize(sizeX, sizeY);
		geSearchResult6.setPrefSize(sizeX, sizeY);
		geSearchResult7.setPrefSize(sizeX, sizeY);
		geSearchResult8.setPrefSize(sizeX, sizeY);
		geSearchResult5.setWrapText(true);
		geSearchResult6.setWrapText(true);
		geSearchResult7.setWrapText(true);
		geSearchResult8.setWrapText(true);
		geSearchResult5.setGraphic(img5);
		geSearchResult6.setGraphic(img6);
		geSearchResult7.setGraphic(img7);
		geSearchResult8.setGraphic(img8);

		geSearchResult9 = new Label("");
		geSearchResult10 = new Label("");
		geSearchResult11 = new Label("");
		geSearchResult12 = new Label("");
		geSearchResult9.setTranslateX(row1X);
		geSearchResult10.setTranslateX(row2X);
		geSearchResult11.setTranslateX(row3X);
		geSearchResult12.setTranslateX(row4X);
		geSearchResult9.setTranslateY(row3Y);
		geSearchResult10.setTranslateY(row3Y);
		geSearchResult11.setTranslateY(row3Y);
		geSearchResult12.setTranslateY(row3Y);
		geSearchResult9.setPrefSize(sizeX, sizeY);
		geSearchResult10.setPrefSize(sizeX, sizeY);
		geSearchResult11.setPrefSize(sizeX, sizeY);
		geSearchResult12.setPrefSize(155, sizeY);
		geSearchResult9.setWrapText(true);
		geSearchResult10.setWrapText(true);
		geSearchResult11.setWrapText(true);
		geSearchResult12.setWrapText(true);
		geSearchResult9.setGraphic(img9);
		geSearchResult10.setGraphic(img10);
		geSearchResult11.setGraphic(img11);
		geSearchResult12.setGraphic(img12);

		tabInterface.getChildren().addAll(geSearchResult1, geSearchResult2, geSearchResult3, geSearchResult4,
				geSearchResult5, geSearchResult6, geSearchResult7, geSearchResult8, geSearchResult9, geSearchResult10,
				geSearchResult11, geSearchResult12);

	}
	
	private void pane_createButtons() {
		day 		= new ToggleButton("1 Day");
		week		= new ToggleButton("2 Week");
		month		= new ToggleButton("Quarter");
		months3		= new ToggleButton("6 Months");
		months6		= new ToggleButton("1 Year");
			
		day.setPrefSize(70, 10);
		week.setPrefSize(70, 10);
		month.setPrefSize(70, 10);
		months3.setPrefSize(80, 10);
		months6.setPrefSize(70, 10);
		
		day.getStyleClass().add("button");
		week.getStyleClass().add("button");
		month.getStyleClass().add("button");
		months3.getStyleClass().add("button");
		months6.getStyleClass().add("button");
		
		day.setToggleGroup(toggleGroup);
		week.setToggleGroup(toggleGroup);
		month.setToggleGroup(toggleGroup);
		months3.setToggleGroup(toggleGroup);
		months6.setToggleGroup(toggleGroup);
		
		buttonBarLeft = new ButtonBar();
		buttonBarLeft.setButtonData(month, ButtonData.APPLY);
		buttonBarLeft.setButtonData(months3, ButtonData.APPLY);
		buttonBarLeft.setButtonData(months6, ButtonData.APPLY);
		
		buttonBarRight = new ButtonBar();
		buttonBarRight.setButtonData(day, ButtonData.APPLY);
		buttonBarRight.setButtonData(week, ButtonData.APPLY);

		buttonBarLeft.getButtons().addAll(day,week,month);
		buttonBarRight.getButtons().addAll(months3,months6);
		

		
		buttonBarRight.setTranslateX(870);
		buttonBarRight.setTranslateY(13);
		
		buttonBarLeft.setTranslateX(-5);
		buttonBarLeft.setTranslateY(13);
		
		tabInterface.getChildren().addAll(buttonBarLeft,buttonBarRight);
		
	}
	



}
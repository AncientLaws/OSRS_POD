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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static com.cypods.geBuddy.ApplicationConstant.BORDERS;
import static com.cypods.geBuddy.ApplicationConstant.DEBUG;
import static com.cypods.geBuddy.Window.root;

@Component
public class PaneInterface extends DisplayController implements Runnable {

	/************************** Images **************************/
	ImageView inventory;
	ImageView itemTopMenu;
	ImageView itemIconPaneImage;
	Rectangle clipRect = new Rectangle();

	/************************** Labels **************************/
	Label selectedItemNameLabel;

	Label xyCoordinates;

	GeSearchArea geSearchArea;
	@Autowired
	ItemInfoArea itemInformation;

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

	protected AnchorPane tabInterface = new AnchorPane();

	Charts cp;

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
		tabInterface.setPrefWidth(1060);

		if(BORDERS){
			tabInterface.setStyle("-fx-border-color: green");
		}

		// Bind the Rectangle's dimensions to the Pane's dimensions
		clipRect.widthProperty().bind(tabInterface.widthProperty());
		clipRect.heightProperty().bind(tabInterface.heightProperty());

		// Set the Rectangle as the clip of the Pane
		tabInterface.setClip(clipRect);
     	 
     	cp = new Charts(tabInterface.getPrefWidth(), tabInterface.getPrefHeight());

		itemInformation = new ItemInfoArea();
		tabInterface.getChildren().addAll(itemInformation.getvBox());
		itemIconPaneImage = itemInformation.getItemIconPaneImage();

		geSearchArea = new GeSearchArea(tabInterface);
		geSearchArea.geSearchArea_initGeSearchLabels(tabInterface);
		geSearchArea.initTextField(tabInterface);


		pane_drawItemScrollArea();
//		pane_drawInventoryMenu();

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

		AnchorPane.setTopAnchor(cp.chartsPane,45.0);
		AnchorPane.setBottomAnchor(cp.chartsPane, 310.0);

		AnchorPane.setRightAnchor(cp.chartsPane,2.0);
		AnchorPane.setLeftAnchor(cp.chartsPane,2.0);

		tabInterface.setBottomAnchor(itemInformation.getvBox(),85.0);
		tabInterface.setRightAnchor(itemInformation.getvBox(),2.0);

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

	/**
	 * Draws the background color of the tab interface, where the top is a static color, and the bottom is fully
	 * transparent (via gradient)
	 * */
	private void pane_drawChartArea() {
		tabInterface.setStyle("-fx-background-color: linear-gradient(to bottom, rgba(95, 73, 43,1) 20%, rgba(95, 73, 43,0) 45%);");
	}

	protected void pane_setItemTopMenuVisible(boolean b) {

		itemIconPaneImage.setVisible(b);
	}

	protected void pane_iconTooltip(String s) {
		pane_Tooltip = new Tooltip(s);
		pane_Tooltip.install(itemInformation.getItemIconPaneImage(), pane_Tooltip);
	}
	
	/**
	 * Method to used to create charts with default settings.
	 * Method created to handle dynamic chart size in cases where the user is resizing the window. This method is handled
	 * by a new thread to increase performance
	 * */
	protected void pane_createChart() {
		if(DEBUG == true) {System.out.println("pane_createChart()");}
		Platform.runLater(()->{
				tabInterface.getChildren().removeAll(cp.chartsPane);
				tabInterface.getChildren().addAll(cp.chartsPane);
		});
	}
	
	public void pane_updateChart(int itemID, String timePeriod) {
		Platform.runLater(()->{
			tabInterface.getChildren().removeAll(cp.chartsPane);
			cp.runChart(itemID, timePeriod);
			tabInterface.getChildren().addAll(cp.chartsPane);
		});
	}

	/**
	 * Initializes all the labels in the main pane interface
	 * (Location, style, etc)
	 **/
	private void pane_initLabels() {
		if(DEBUG == true) {System.out.println("pane_initLabels");}
		xyCoordinates = new Label("Coordinates");
		xyCoordinates.setTranslateX(10);
		xyCoordinates.setTranslateY(585);
		xyCoordinates.setStyle("-fx-text-fill: orange; -fx-font-size: 20px; -fx-font-weight: bold");

		selectedItemNameLabel = new Label("item");
		tabInterface.setTopAnchor(selectedItemNameLabel, 0.0);
		selectedItemNameLabel.setStyle("-fx-text-fill: orange; -fx-font-size: 30px; -fx-font-weight: bold");
		selectedItemNameLabel.getStyleClass().add("labelAll");

		tabInterface.getChildren().addAll(selectedItemNameLabel);
	}

	/**
	 * A method that sets the labels in the main pane interface.
	 **/

	protected void updateLabels(String name1, String id1, String description1, String members1, String currentPrice1,
								String currentTrend1, String todayPrice1, String todayTrend1, String day30_trend1, String day30_change1,
								String day90_trend1, String day90_change1, String day180_trend1, String day180_change1) {

//		createMonitoredLabel();
		selectedItemNameLabel.setText(name1);
		selectedItemNameLabel.setLayoutX((tabInterface.widthProperty().doubleValue() /2) - 110); //Attempt at centering title

		itemInformation.updateLabels(
				name1,  id1,  description1,  members1,  currentPrice1,
				currentTrend1,  todayPrice1,  todayTrend1,  day30_trend1,  day30_change1,
				day90_trend1,  day90_change1,  day180_trend1,  day180_change1
		);
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

		tabInterface.setLeftAnchor(buttonBarLeft,0.0);
		tabInterface.setRightAnchor(buttonBarRight,20.0);
		tabInterface.setTopAnchor(buttonBarLeft,8.0);
		tabInterface.setTopAnchor(buttonBarRight,8.0);
		
		tabInterface.getChildren().addAll(buttonBarLeft,buttonBarRight);
		
	}

	@Override
	public void run() {
		
	}

	public Rectangle getClipRect() {
		return clipRect;
	}

	public void setClipRect(Rectangle clipRect) {
		this.clipRect = clipRect;
	}
}
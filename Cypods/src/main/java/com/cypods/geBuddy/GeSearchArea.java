package com.cypods.geBuddy;

import javafx.geometry.Pos;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.HashMap;

import static com.cypods.geBuddy.ApplicationConstant.DEBUG;
//import static com.cypods.geBuddy.Window.root;

//@Component
public class GeSearchArea{

	protected AnchorPane geSearchAreaPane = new AnchorPane();

	HashMap<String, GeSearchResultLabel> geSearchResultLabelMap = new HashMap<>(12);

	Rectangle clipRect = new Rectangle();

	/*************** Ge search area locations ********************/
	double row1X = 14;
	double row2X = 190;
	double row3X = 366;
	double row4X = 542;

	double row1Y = 30;
	double row2Y = 90;
	double row3Y = 150;

	int iconWidth = 65;
	int iconHeight = 65;
	int sizeX = 175;
	int sizeY = 50;
/*********************************************/

	ImageView geSearchAreaBackground;

	TextField itemSearchInput;

//	GeSearchArea(){
//		geSearchArea_drawItemScrollArea();
//		geSearchArea_initGeSearchLabels();
//		initTextField();
//	}

	GeSearchArea(AnchorPane anchorPane){
		geSearchArea_drawItemScrollArea(anchorPane);
		geSearchArea_initGeSearchLabels(anchorPane);
		initTextField(anchorPane);
	}


	protected void geSearchArea_drawItemScrollArea(AnchorPane anchorPane) {
		geSearchAreaBackground = new ImageView(new Image("/images/GE_SEARCH_V6.png"));
		geSearchAreaBackground.setFitWidth(750);
		geSearchAreaBackground.setFitHeight(225);
		geSearchAreaBackground.setRotate(180);
		geSearchAreaPane.getChildren().add(geSearchAreaBackground);

		geSearchAreaPane.setPrefWidth(750);
		geSearchAreaPane.setPrefHeight(225);
		geSearchAreaPane.setBottomAnchor(geSearchAreaBackground,0.0);
		geSearchAreaPane.setLeftAnchor(geSearchAreaBackground,0.0);
		geSearchAreaPane.setStyle("-fx-border-color: red");

		// Bind the Rectangle's dimensions to the Pane's dimensions and set the Rectangle as the clip of the Pane
		clipRect.widthProperty().bind(geSearchAreaPane.widthProperty());
		clipRect.heightProperty().bind(geSearchAreaPane.heightProperty());
		geSearchAreaPane.setClip(clipRect);

		//Anchor the geSearchArea pane relative to the tabInterface
		anchorPane.setBottomAnchor(geSearchAreaPane,85.0);
		anchorPane.setLeftAnchor(geSearchAreaPane,0.0);
	}

	protected void geSearchArea_initGeSearchLabels(AnchorPane anchorPane) {
		if(DEBUG == true) {System.out.println("geSearchArea_initGeSearchLabels()");}

		for(int i = 0; i < 12 ; i++){
			String instanceKeyGen = "geSearchResult"+(i+1);
			geSearchResultLabelMap.put(instanceKeyGen, new GeSearchResultLabel(instanceKeyGen));
		}

		for(String key : geSearchResultLabelMap.keySet()){
			GeSearchResultLabel geSearchResultLabel = geSearchResultLabelMap.get(key);
			geSearchResultLabel.getLabelImage().setFitWidth(iconWidth);
			geSearchResultLabel.getLabelImage().setFitHeight(iconHeight);

			geSearchResultLabel.getLabel().setPrefSize(sizeX, sizeY);
			geSearchResultLabel.getLabel().setGraphic(geSearchResultLabel.getLabelImage());
			geSearchResultLabel.getLabel().setWrapText(true);

			switch(key){
				case "geSearchResult1":
					geSearchResultLabel.setLabelLocation(row1X,row1Y, geSearchAreaPane);
					break;
				case "geSearchResult2":
					geSearchResultLabel.setLabelLocation(row2X,row1Y, geSearchAreaPane);
					break;
				case "geSearchResult3":
					geSearchResultLabel.setLabelLocation(row3X,row1Y, geSearchAreaPane);
					break;
				case "geSearchResult4":
					geSearchResultLabel.setLabelLocation(row4X,row1Y, geSearchAreaPane);
					break;
				case "geSearchResult5":
					geSearchResultLabel.setLabelLocation(row1X,row2Y, geSearchAreaPane);
					break;
				case "geSearchResult6":
					geSearchResultLabel.setLabelLocation(row2X,row2Y, geSearchAreaPane);
					break;
				case "geSearchResult7":
					geSearchResultLabel.setLabelLocation(row3X,row2Y, geSearchAreaPane);
					break;
				case "geSearchResult8":
					geSearchResultLabel.setLabelLocation(row4X,row2Y, geSearchAreaPane);
					break;
				case "geSearchResult9":
					geSearchResultLabel.setLabelLocation(row1X,row3Y, geSearchAreaPane);
					break;
				case "geSearchResult10":
					geSearchResultLabel.setLabelLocation(row2X,row3Y, geSearchAreaPane);
					break;
				case "geSearchResult11":
					geSearchResultLabel.setLabelLocation(row3X,row3Y, geSearchAreaPane);
					break;
				case "geSearchResult12":
					geSearchResultLabel.setLabelLocation(row4X,row3Y, geSearchAreaPane);
					break;
			}

			geSearchAreaPane.getChildren().add(geSearchResultLabel.getLabel());
		}
	}

	/**
	 * @Purpose
	 * Clears Ge search results and images from ge search area and remove action listeners
	 * */
	protected void clearGeSearchResults() {

		for(String current : geSearchResultLabelMap.keySet()){
			GeSearchResultLabel geSearchResultLabel = geSearchResultLabelMap.get(current);
			geSearchResultLabel.getLabel().setText(null);
			geSearchResultLabel.getLabelImage().setImage(null);
			geSearchResultLabel.getLabel().setOnMouseEntered((mouseEvent)-> {});
			geSearchResultLabel.getLabel().setOnMouseExited((mouseEvent)-> {});
			geSearchResultLabel.getLabel().setOnMousePressed((mouseEvent)-> {});
			geSearchResultLabel.getLabel().setOnMouseClicked((mouseEvent -> {}));
		}
	}

	/**
	 * Initializes input text field in the Ge search bar and adds mouse listener
	 * that will remove the initial prompt text
	 */
	protected void initTextField(AnchorPane anchorPane) {
		if(DEBUG == true) {System.out.println("initTextField()");}
		//f = new Font("runescape_uf.ttf", 12);
		itemSearchInput = new TextField("");
		itemSearchInput.end();
		itemSearchInput.setOpacity(1);
		itemSearchInput.setBackground(new Background(new BackgroundFill(Color.rgb(201, 182, 147), null, null)));
		anchorPane.setTopAnchor(itemSearchInput,5.0);
		anchorPane.setLeftAnchor(itemSearchInput,8.0);
//		itemSearchInput.layoutXProperty().bind(geSearchAreaPane.layoutXProperty().add(8));
//		itemSearchInput.layoutYProperty().bind(geSearchAreaPane.layoutYProperty().add(5));
		itemSearchInput.setPrefWidth(734);
		itemSearchInput.setAlignment(Pos.CENTER);
//		itemSearchInput.setPromptText("What would you like to buy?");
		if(DEBUG == true) {System.out.println("Caret Position: " + itemSearchInput.getCaretPosition());}
		itemSearchInput.setStyle(
				"-fx-text-fill: black; -fx-font-size: 13px; -fx-font-weight: bold;-fx-font-family: 'runescape_uf.ttf'");
		itemSearchInput.setFocusTraversable(false);
		geSearchAreaPane.getChildren().add(itemSearchInput);
	}

/**~~~~~~~~~~~~~~~Getters and setters~~~~~~~~~~~~~~~~~~~~~*/
	public AnchorPane getGeSearchAreaPane() {
		return geSearchAreaPane;
	}

	public void setGeSearchAreaPane(AnchorPane geSearchAreaPane) {
		this.geSearchAreaPane = geSearchAreaPane;
	}

	public HashMap<String, GeSearchResultLabel> getGeSearchResultLabelMap() {
		return geSearchResultLabelMap;
	}

	public void setGeSearchResultLabelMap(HashMap<String, GeSearchResultLabel> geSearchResultLabelMap) {
		this.geSearchResultLabelMap = geSearchResultLabelMap;
	}

	public TextField getItemSearchInput() {
		return itemSearchInput;
	}

	public void setItemSearchInput(TextField itemSearchInput) {
		this.itemSearchInput = itemSearchInput;
	}

	public Rectangle getClipRect() {
		return clipRect;
	}

	public void setClipRect(Rectangle clipRect) {
		this.clipRect = clipRect;
	}
}

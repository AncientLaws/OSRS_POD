package com.osrs.pod.application.controllers;


import com.osrs.pod.application.ApplicationConstant;
import com.osrs.pod.application.models.GeSearchResultLabel;
import com.osrs.pod.application.services.DataModeler;
import com.osrs.pod.database.configuration.ApplicationContextProvider;
import com.osrs.pod.database.domain.entities.ItemsDb;
import com.osrs.pod.database.model.ItemMaplet;
import com.osrs.pod.database.service.ItemsDao;
import javafx.animation.PauseTransition;
import javafx.application.Platform;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.paint.Color;
import javafx.util.Duration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.osrs.pod.application.ApplicationConstant.*;
import static com.osrs.pod.application.Window.itemMapletList;
import static com.osrs.pod.database.service.DatabaseUpdater.latestPriceNodes;
import static com.osrs.pod.application.Window.root;
public class TabController extends PaneInterfaceController {


	/***************Interface variables********************/
	private Label tabSelectionLabel;
	private String instanceTabName;
	private String tabActive;
	private Image image;
	private String itemToolTip = "Item Tool Tip";
	protected int tab_itemID;
	protected String selectedSearchItem = "";
	private double newSceneWidth;

	/***************Connect and get data********************/
	RequestController requestController =  new RequestController();
	private String ItemSpriteUrl = "";
	protected InputStream error;
	public InputStream input;
	private String[] itemInfoArr = new String [18];
	private String[][] tc_itemListArray = new String[100][6];

	/***************Tab icon settings********************/
	int X;
	int Y;

	/***************End variable declaration**************/

	/*********************Other*************************/
	private PauseTransition pauseTransition;
	private ItemsDao itemsDao;
	private PauseTransition pause;
	private boolean searchHasRun = false; // Flag to ensure it only runs once

	DataModeler dataModeler = new DataModeler();


	/********************End Other*************************/
	TabController() {
	}

	TabController (String instanceTabName){
		super(true);
		this.instanceTabName = instanceTabName;
		imageView = new ImageView();
		tabSettings(this.instanceTabName);
		itemsDao = ApplicationContextProvider.getApplicationContext().getBean(ItemsDao.class);
		// Set up the delay for after the user stops typing
		pause = new PauseTransition(Duration.millis(200)); //ms delay
//		pause.setOnFinished(event -> geSearchResultsDb(geSearchArea.getItemSearchInput().getText()));

	}

	public int tabSettings(String instanceTabName) {
		if(DEBUG == true) {System.out.println("tabSettings");}
		switch (instanceTabName){
			case "Tab10":{ X = 100; Y = 12; setTabActive(instanceTabName); break;}
			case "Tab9": { X = 189; Y = 12; setTabActive(instanceTabName); break;}
			case "Tab8": { X = 280; Y = 12; setTabActive(instanceTabName); break;}
			case "Tab7": { X = 370; Y = 12; setTabActive(instanceTabName); break;}
			case "Tab6": { X = 460; Y = 12; setTabActive(instanceTabName); break;}
			case "Tab5": { X = 550; Y = 12; setTabActive(instanceTabName); break;}
			case "Tab4": { X = 640; Y = 12; setTabActive(instanceTabName); break;}
			case "Tab3": { X = 728; Y = 12; setTabActive(instanceTabName); break;}
			case "Tab2": { X = 818; Y = 12; setTabActive(instanceTabName); break;}
			case "Tab1": { X = 909; Y = 12; setTabActive(instanceTabName); break;}
		}
		initLabel();
		initImage();
		try {
			itemSearchListener();
			initInterfaceSizeListeners();
		}
		catch(Exception e) {catchError();
		}
		return 1;
	}



	/**An attempt to dynamically anchor all the tob icons together*/
	protected void setTabIconRelationships(double sceneWidth){
				double offSetXValue = ((sceneWidth - (tabSelectionLabel.getWidth()*10))/2)+(X-167); //TODO tuned the hell out of this
				tabInterface.setTopAnchor(imageView,(double)Y);
				tabInterface.setTopAnchor(tabSelectionLabel,(double)Y);
				tabInterface.setRightAnchor(imageView,offSetXValue);
				tabInterface.setRightAnchor(tabSelectionLabel,offSetXValue);
	}

	protected void setInterfaceVisible(boolean b){
		pane_setVisibleInterface(b);
	}

	private void setIcon() {
		ItemSpriteUrl = itemInfoArr[ITEM_SPRITE_URL];
		getIcon();
	}

	private void getIcon() {
		try {

			input = new URL (ItemSpriteUrl).openStream();
			image = new Image(input);
			imageView.setImage(image);
		}
		catch(Exception e) {
			catchError();
			System.out.println("Error in getting Icon for:" + getTabActive());

		}
		//return input;
	}

	public void setActive() {
		tabNo = getTabActive();
		setInterfaceVisible(true); 				//Setting current Objects paneInterface to be visible
		root.setId(getTabActive());				//changing background to simulate tab change
		imageView.setOpacity(1);            	//returns item to full opacity
		/*Sets the item search input field to always be active and caret position to be at the end*/
		geSearchArea.getItemSearchInput().requestFocus();
		geSearchArea.getItemSearchInput().positionCaret(geSearchArea.getItemSearchInput().getText().length());
		if(DEBUG == true) {System.out.println("setActive: " + getTabActive());}
	}
	/**
	 * Handles user search input
	 * @Note Thread is created whenever a user attempts to search for an Item. This is to enhance the application performance.
	 * Platform.runLater must be used when updating javafx components
	 * */
	private void itemSearchListener() {
		geSearchArea.getItemSearchInput().setOnKeyPressed(KeyEvent ->
		{
//			if(KeyEvent.getCode().equals(KeyCode.ENTER)) {
//				Thread thread = new Thread(() -> {
//					requestController.osrsSearchItemsParseJsonList(ApplicationConstant.osrsItemSearch, geSearchArea.getItemSearchInput().getText());
//					Platform.runLater(() ->{
//						geSearchResultsDb(geSearchArea.getItemSearchInput().getText());
//					});
//				});
//				thread.start();
//			}
			onKeyReleased(KeyEvent);
//			if(KeyEvent.getCode().equals(KeyCode.ENTER)) {
			pause.setOnFinished(event -> {
				Thread thread = new Thread(() -> {
//					requestController.osrsSearchItemsParseJsonList(ApplicationConstant.osrsItemSearch, geSearchArea.getItemSearchInput().getText());
					Platform.runLater(() ->{
						if(!geSearchArea.getItemSearchInput().getText().isBlank()) {
							geSearchResultsDb(geSearchArea.getItemSearchInput().getText());
						}
						else{
							geSearchArea.clearGeSearchResults();
						}
					});
				});
				thread.start();
			});
		});
		geSearchArea.getItemSearchInput().setOnMousePressed((mouseEvent) -> {
			geSearchArea.getItemSearchInput().setText("");
			geSearchArea.clearGeSearchResults();
		});
	}

	public void onKeyReleased(KeyEvent event) {
		if (event.getCode().equals(KeyCode.ENTER)) {
			geSearchResultsDb(geSearchArea.getItemSearchInput().getText()); // Run immediately on ENTER
		} else {
			if(geSearchArea.getItemSearchInput().getText().isBlank()) {
				geSearchArea.clearGeSearchResults();
			}
			// Reset the pause transition on any other key
			pause.stop();
			searchHasRun = false; // Reset the flag when user types
			pause.play(); // Start or restart the delay
		}
	}

	/**
	 * Update tab icon label locations based on windows size. Added delay to improve performance
	 * */
	private void initInterfaceSizeListeners(){
		pauseTransition = new PauseTransition(Duration.millis(50));

		pauseTransition.setOnFinished(event -> {
			setTabIconRelationships(newSceneWidth);
			selectedItemNameLabel.setLayoutX((newSceneWidth - selectedItemNameLabel.getWidth()) / 2);
		});

		tabInterface.widthProperty().addListener((observableValue, oldSceneWidth, newSceneWidth) -> {
			this.newSceneWidth = newSceneWidth.doubleValue();
			pauseTransition.playFromStart();
		});

		cp.chartsPane.widthProperty().addListener((observable, oldValue ,newValue) -> {
			pauseTransition.playFromStart();
		});
	}

	/**
	 * Retrieve the details of the selected item from search result, and update the chart
	 * @Note A normal thread can be used for plain java, but Platform.runlater() must be used as javafx
	 * components can only be updated by the javafx application thread
	 * */
	private void itemSearchSelectionListener(int itemID) {
		Thread thread = new Thread(() -> {
			itemInfoArr = requestController.get_osrs_api_parseItemJson(ApplicationConstant.osrsGetItemDetails + itemID);
			tab_itemID = itemID;
			Platform.runLater(() -> {
				setIcon();
				updateInterfaceLabels();					//Drawing everything
				pane_updateChart(itemID, "1h");
				addButtonListeners();
				resetButtonClickedStyle();
				setButtonClickedStyleWeek();
			});
		});
		thread.start();
	}

	private void catchError(){
		//root.getChildren().remove(lTab);
		image = new Image(getClass().getClassLoader().getResource("/images/Item_UnAvailable.png").toString(),true);
		imageView.setImage(image);
		imageView.setPreserveRatio(true);
		imageView.setFitHeight(75);
		imageView.setFitWidth(75);
		imageView.setLayoutX(X);
		imageView.setLayoutY(Y);
		imageView.setStyle("-fx-background-color: BLACK");
		imageView.setCache(true);
		tab_IconTooltip("Item unavailable or unable to retrieve item");
		//root.getChildren().add(imageView);


	}

	private void iconImageSettings() {
		if(DEBUG == true) {System.out.println("iconImageSettings InputStream: ");}
		imageView.setPreserveRatio(true);
		imageView.setFitHeight(75);
		imageView.setFitWidth(75);
		imageView.setStyle("-fx-background-color: BLACK");
		imageView.setCache(true);
		imageView.setVisible(true);
		tab_IconTooltip(itemInfoArr[ITEM_NAME]);
		imageView.setOnMouseEntered((mouseEvent) -> MakeItemPop());
		imageView.setOnMouseExited((mouseEvent) -> MakeItemPopBack());


	}

	private void initImage(){
		root.getChildren().add(imageView); //adds all imageViews to the top left corner
		imageView.setVisible(false);       //hides all the added images, only sets visible once clicked
	}

	/**
	 * Initialize label and set default location based on tab no
	 **/
	private void initLabel(){
		Double x = (double) X;
		Double y = (double) Y;
		tabSelectionLabel = new Label();
		if(BORDERS){
			tabSelectionLabel.setText(getInstanceTabName());
		}
		tabSelectionLabel.setPrefSize(75, 75);
		if(BORDERS) {
			tabSelectionLabel.setStyle("-fx-border-color: green");
		}
		root.getChildren().add(tabSelectionLabel);

		/*Platform.run later is neccessary to get the correct widthProperty after application size has been initialized*/
			Platform.runLater(() -> {
			double offSetXValue = ((tabInterface.widthProperty().doubleValue() - (tabSelectionLabel.getWidth()*10))/2)+(X-167); //TODO tuned the hell out of this
			tabInterface.setRightAnchor(imageView,offSetXValue);
			tabInterface.setRightAnchor(tabSelectionLabel,offSetXValue);
		});
	}

	private void updateInterfaceLabels() {

		try {
			try {
				iconImageSettings();  //must have called getIcon() for it not to be null
				itemInformation.setSelectedItemIcon(image);
				pane_iconTooltip(itemInfoArr[ITEM_NAME]);
				updateLabels(
						itemInfoArr[ITEM_NAME]								//name
						,itemInfoArr[ITEM_ID] 								//Item ID
						,itemInfoArr[ITEM_DESC]								//Description
						,itemInfoArr[ITEM_IS_MEMBER]						//Member
						,itemInfoArr[ITEM_CURRENT_PRICE]					//Current price
						,itemInfoArr[ITEM_CURRENT_TREND]					//Current trend
						,itemInfoArr[ITEM_TODAY_PRICE]						//Todays price
						,itemInfoArr[ITEM_TODAY_TREND]						//Todays trend
						,itemInfoArr[ITEM_TREND_30]							//30 day trend
						,itemInfoArr[ITEM_CHANGE_30]						//30 day change
						,itemInfoArr[ITEM_TREND_90]							//90 day trend
						,itemInfoArr[ITEM_CHANGE_90]						//90 day change
						,itemInfoArr[ITEM_TREND_180]						//180 day trend
						,itemInfoArr[ITEM_CHANGE_180]						//180 day change
				);
				geSearchArea.getItemSearchInput().positionCaret(geSearchArea.getItemSearchInput().getText().length());  //
				//pane_createChart();
			}
			catch(Exception e) {
				System.out.println("Error in setInterfaceLabels()");
//				pane_setItemTopMenuError();
				catchError();
			}
		} catch (Exception e) {
			System.out.println("Error in setInterfaceLabels: " + e);
		}
	}

	/**
	 * @Purpose
	 * Dynamically add Ge search result item name and image to the ge search area
	 *
	 * @Steps
	 * - Clear previous ge search
	 * - Re-add label action listeners @Refactor
	 * - Get returned search item
	 * - Add result to all labels
	 * */
	private void geSearchResults() {
		geSearchArea.clearGeSearchResults();
//		addLabelActionListeners();

		tc_itemListArray = requestController.returnItemListArray() ;

		try { //Open stream to grab the image for each of the returned items

			for(int i = 0; i < requestController.get_getSearchResultSize() ; i++)
			{
				String keyGen = "geSearchResult" + (i+1);
				GeSearchResultLabel geSearchResultLabel;

				if(geSearchArea.getGeSearchResultLabelMap().containsKey(keyGen)){
					geSearchResultLabel = geSearchArea.getGeSearchResultLabelMap().get(keyGen);
					geSearchResultLabel.getLabel().setText(tc_itemListArray[i][GE_SEARCH_NAME].concat("  (").concat(tc_itemListArray[i][GE_SEARCH_CURRENT_PRICE]).concat(")"));
					input = new URL (tc_itemListArray[i][GE_SEARCH_ICON_URL]).openStream();
					geSearchResultLabel.getLabelImage().setImage(new Image(input));
				}
			}
		}
		catch(Exception e) {
			/**
			 * When an Item is searched, if the resulting response is less than 12 items, the url list will not
			 * be filled, hence, a null will be present in the url field. This exception 'handles' it by doing nothing
			 */
			System.out.println("Error grabbing Icon Images in TabController>geSearchResults");
			//System.out.println(e);

		}


	}

	private void geSearchResultsDb(String searchItem) {
		geSearchArea.clearGeSearchResults();
//		tc_itemListArray = requestController.returnItemListArray() ;
		List<ItemsDb> itemsDbListArray = itemsDao.findItemBySearch(searchItem);
		addLabelActionListeners(itemsDbListArray.size());
		try { //Open stream to grab the image for each of the returned items
			for(int i = 0; i < itemsDbListArray.size() ; i++)
			{
				String keyGen = "geSearchResult" + (i+1);
				GeSearchResultLabel geSearchResultLabel;
				ItemsDb current = itemsDbListArray.get(i);

				if(geSearchArea.getGeSearchResultLabelMap().containsKey(keyGen)){
					geSearchResultLabel = geSearchArea.getGeSearchResultLabelMap().get(keyGen);
					geSearchResultLabel.setId(current.getItemId());
					geSearchResultLabel.getLabelImage().setImage(new Image(new ByteArrayInputStream(current.getData())));
					geSearchResultLabel.getLabel().setText(current.getItem_name());
					try{
						geSearchResultLabel.getLabel().setText(geSearchResultLabel.getLabel().getText().concat("\n" ).concat("  (").concat(dataModeler.formatNumber(latestPriceNodes.getData().get(current.getItemId().toString()).getHigh().longValue()).toString().concat(")")));
					}
					catch (Exception e){
						//Fail quietly
					}
				}
			}
		}
		catch(Exception e) {
			/**
			 * When an Item is searched, if the resulting response is less than 12 items, the url list will not
			 * be filled, hence, a null will be present in the url field. This exception 'handles' it by doing nothing
			 */
			System.out.println("Error grabbing Icon Images in TabController>geSearchResults");
			//System.out.println(e);

		}


	}


	/**
	 * @Purpose
	 * - Dynamically add action listeners to labels based on search result size
	 * - Enable mouse Enter/Exit animation
	 * - Allow items to be selected and displayed
	 * */
	protected void addLabelActionListeners(int size) {
//		int arrLength = requestController.get_getSearchResultSize();

		int arrLength = size;

		for(int i = 0; i < arrLength ; i++)
		{
			String keyGen = "geSearchResult" + (i+1);
			GeSearchResultLabel geSearchResultLabel = geSearchArea.getGeSearchResultLabelMap().get(keyGen);

			if (geSearchResultLabel != null) {
				Label label = geSearchResultLabel.getLabel();
				int j = i;
				label.setOnMouseEntered((mouseEvent) -> {
					label.setBackground(new Background(new BackgroundFill(Color.rgb(168, 145, 103, .5), null, null)));
					label.getGraphic().setScaleX(1.1);
					label.getGraphic().setScaleY(1.1);
				});
				label.setOnMouseExited((mouseEvent) -> {
					//Only clear background highlight when the item is not selected
					if (!selectedSearchItem.equals(geSearchResultLabel.getLabelInstanceName())) {
						label.setBackground(new Background(new BackgroundFill(null, null, null)));
					}
					label.getGraphic().setScaleX(1);
					label.getGraphic().setScaleY(1);
				});
				label.setOnMouseClicked((mouseEvent) -> {
					//Clear previous selected highlight
					if(geSearchArea.getGeSearchResultLabelMap().containsKey(selectedSearchItem)){
						geSearchArea.getGeSearchResultLabelMap().get(selectedSearchItem).getLabel().setBackground(new Background(new BackgroundFill(null, null, null)));
					}
//					itemSearchSelectionListener(Integer.valueOf(tc_itemListArray[j][1]));
					itemSearchSelectionListener(geSearchResultLabel.getId());
					selectedSearchItem = geSearchResultLabel.getLabelInstanceName();
				});
				label.setOnMousePressed((mouseEvent) -> {
					label.setBackground(new Background(new BackgroundFill(Color.rgb(168, 145, 103, .5), null, null)));
					label.getGraphic().setScaleX(1);
					label.getGraphic().setScaleY(1);
				});
			}
		}
	}

	private void addButtonListeners() {

		day.setOnMouseClicked((mouseEvent) -> {
			resetButtonClickedStyle();
			pane_updateChart(tab_itemID, "5m");
			day.setStyle("-fx-background-color: grey");
		});
		week.setOnMouseClicked((mouseEvent) -> {
			resetButtonClickedStyle();
			pane_updateChart(tab_itemID, "1h");
			week.setStyle("-fx-background-color: grey");
		});
		quarter.setOnMouseClicked((mouseEvent) -> {
			resetButtonClickedStyle();
			pane_updateChart(tab_itemID, "6h");
			quarter.setStyle("-fx-background-color: grey");
		});
		months3.setOnMouseClicked((mouseEvent) -> {
			resetButtonClickedStyle();
			pane_updateChart(tab_itemID, "6Month");
			months3.setStyle("-fx-background-color: grey");
		});
		months6.setOnMouseClicked((mouseEvent) -> {
			resetButtonClickedStyle();
			pane_updateChart(tab_itemID, "24h");
			months6.setStyle("-fx-background-color: grey");
		});
	}
	private void resetButtonClickedStyle() {
		day.setStyle("-fx-background-color: black");
		week.setStyle("-fx-background-color: black");
		quarter.setStyle("-fx-background-color: black");
		months3.setStyle("-fx-background-color: black");
		months6.setStyle("-fx-background-color: black");

	}

	private void setButtonClickedStyleWeek() {
		week.setStyle("-fx-background-color: grey");
	}

	public String getInstanceTabName() {
		return instanceTabName;
	}

	public void setInstanceTabName(String instanceTabName) {
		this.instanceTabName = instanceTabName;
	}

	public Label getTabSelectionLabel() {
		return tabSelectionLabel;
	}

	public void setTabSelectionLabel(Label tabSelectionLabel) {
		this.tabSelectionLabel = tabSelectionLabel;
	}

	public String getTabActive() {
		return tabActive;
	}

	public void setTabActive(String tabActive) {
		this.tabActive = tabActive;
	}

	public ImageView getImageView() {
		return imageView;
	}

	public void setImageView(ImageView imageView) {
		this.imageView = imageView;
	}

	public String getSelectedSearchItem() {
		return selectedSearchItem;
	}

	public void setSelectedSearchItem(String selectedSearchItem) {
		this.selectedSearchItem = selectedSearchItem;
	}
}
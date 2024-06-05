package com.cypods.geBuddy;


import javafx.application.Platform;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.paint.Color;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.net.URL;

import static com.cypods.geBuddy.ApplicationConstant.*;

//import static com.cypods.geBuddy.ApplicationConstant.*;

@Component
//@Scope ("Prototype")
public class TabController extends PaneInterface {


	/***************Interface variables********************/
	private Label tabSelectionLabel;
	private String instanceTabName;
	private String tabActive;
	private Image image;
	protected ImageView imageView;
	Tooltip tooltip;
	private String itemToolTip = "Item Tool Tip";
	protected int tab_itemID;

	/***************Connect and get data********************/
	RequestController requestController =  new RequestController();
	private String ItemSpriteUrl = "";
	protected InputStream error;
	public InputStream input;
	private String[] itemInfoArr = new String [18];
	private String[][] tc_itemListArray = new String[100][6];
	private String osrsItemSearch = "https://services.runescape.com/m=itemdb_oldschool/api/catalogue/items.json?category=1&alpha=";

	/***************Tab icon settings********************/
	int X;
	int Y;

	/***************End variable declaration**************/
//@Autowired
	TabController() {
		imageView = new ImageView();
		initLabel();
		initImage();
	}

	//@Autowired
	TabController (String s){
		instanceTabName = s;
		imageView = new ImageView();
		tabSettings(instanceTabName);

	}
	//@Autowired
	public int tabSettings(String tab) {
		if(DEBUG == true) {System.out.println("tabSettings");}
		switch (tab){
			case "Tab1":{ X = 100; Y = 12; setTabActive(tab); break;}
			case "Tab2":{ X = 189; Y = 12; setTabActive(tab); break;}
			case "Tab3":{ X = 278; Y = 12; setTabActive(tab); break;}
			case "Tab4":{ X = 367; Y = 12; setTabActive(tab); break;}
			case "Tab5":{ X = 456; Y = 12; setTabActive(tab); break;}
			case "Tab6":{ X = 545; Y = 12; setTabActive(tab); break;}
			case "Tab7":{ X = 636; Y = 12; setTabActive(tab); break;}
			case "Tab8":{ X = 728; Y = 12; setTabActive(tab); break;}
			case "Tab9":{ X = 818; Y = 12; setTabActive(tab); break;}
			case "Tab10":{X = 909; Y = 12; setTabActive(tab); break;}
		}
		initLabel();
		initImage();
		try { itemSearchListener();
		}
		catch(Exception e) {catchError();
		}
		return 1;
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
		root.setId(getTabActive());					//changing background to simulate tab change
		imageView.setOpacity(1);            	//returns item to full opacity
		if(DEBUG == true) {System.out.println("setActive: " + getTabActive());}
	}
	/**
	 * Handles user search input
	 * Thread is created whenever a user attempts to search for an Item. This is to enhance the application performance.
	 * */
	private void itemSearchListener() {
		itemSearchInput.setOnKeyPressed(KeyEvent ->
		{
			if(KeyEvent.getCode().equals(KeyCode.ENTER)) {
				//Thread thread = new Thread(() -> {
				pane_ItemSearchInputText = itemSearchInput.getText();
				requestController.set_osrs_api_parseItemJsonList(osrsItemSearch ,pane_ItemSearchInputText);
				geSearchResults(); //clear search results and adds resulting item search images/labels
			}
		});
		itemSearchInput.setOnMousePressed((mouseEvent) -> {
			itemSearchInput.setText("");
			clearGeSearchResults();
		});




	}

	private void itemSearchSelectionListener(int itemID) {
		//new Thread(() -> {
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				//new Thread(() -> {
				itemInfoArr = requestController.get_osrs_api_parseItemJson("https://services.runescape.com/m=itemdb_oldschool/api/catalogue/detail.json?item=" + itemID);
				tab_itemID = itemID;
				setIcon();
				setInterfaceLabels();					//Drawing everything
				pane_updateChart(itemID, "1h");
				addButtonListeners();
				resetButtonClickedStyle();
				setButtonClickedStyleWeek();
				//System.out.println("=========================================Epoch Key values===================================");
				//Get.get_osrs_api_parseItemGraph("https://services.runescape.com/m=itemdb_oldschool/api/graph/26374.json");
				//}).start();
			}

		});
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
		imageView.setLayoutX(X);
		imageView.setLayoutY(Y);
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
	private void MakeItemPop() {
		imageView.setScaleX(1.2);
		imageView.setScaleY(1.2);
	}
	private void MakeItemPopBack() {
		imageView.setScaleX(1);
		imageView.setScaleY(1);
	}

	private void initImage(){
		root.getChildren().add(imageView); //adds all imageViews to the top left corner
		imageView.setVisible(false);       //hides all the added images, only sets visible once clicked
	}

	private void initLabel(){
		tabSelectionLabel = new Label("");
		tabSelectionLabel.setTranslateX(X);
		tabSelectionLabel.setTranslateY(Y);
		tabSelectionLabel.setPrefSize(75, 75);
		root.getChildren().add(tabSelectionLabel);
	}

	private void setInterfaceLabels() {

		try {
			try {
				iconImageSettings();  //must have called getIcon() for it not to be null
				pane_setItemTopMenu(image);
				pane_iconTooltip(itemInfoArr[ITEM_NAME]);
				setLabels(
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
				itemSearchInput.positionCaret(itemSearchInput.getText().length());  //
				//pane_createChart();
			}
			catch(Exception e) {
				System.out.println("Error in setInterfaceLabels()");
				pane_setItemTopMenuError();
				catchError();
			}
		} catch (Exception e) {
			System.out.println("Error in setInterfaceLabels: " + e);
		}
	}

	private void tab_IconTooltip (String s) {
		tooltip = new Tooltip(s);
		//tooltip.setShowDelay(Duration.millis(100));
		tooltip.setId("tooltip");
		tooltip.install(imageView, tooltip);
	}

	/**
	 * @Purpose
	 * Clears Ge search results and images from ge search area
	 * */
	private void clearGeSearchResults() {
		removeLabelActionListeners();
		addGeSearchResultDefaultItemImageViewsToArray();
		addGeSearchResultLabelsToArray();

		for(Label label : geSearchResultLabels)
		{
			label.setText(null);
		}
		for(ImageView view : geSearchResultImages){
			view.setImage(null);
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
		clearGeSearchResults();
		addLabelActionListeners();
		addGeSearchResultDefaultItemImageViewsToArray();
		addGeSearchResultLabelsToArray();

		tc_itemListArray = requestController.returnItemListArray() ;

		try { //Open stream to grab the image for each of the returned items

			for(int i = 0; i < requestController.get_getSearchResultSize() ; i++)
			{
				geSearchResultLabels[i].setText(tc_itemListArray[i][GE_SEARCH_NAME].concat("  (").concat(tc_itemListArray[i][GE_SEARCH_CURRENT_PRICE]).concat(")"));
				input = new URL (tc_itemListArray[i][GE_SEARCH_ICON_URL]).openStream();
				//image = ;
				geSearchResultImages[i].setImage(new Image(input));

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


	protected void addLabelActionListeners() {
		int arrLength = requestController.get_getSearchResultSize();
		addGeSearchResultLabelsToArray();

		for(int i = 0; i < arrLength ; i++)
		{
			Label label = geSearchResultLabels[i];
			int j = i;
			geSearchResultLabels[i].setOnMouseEntered((mouseEvent)-> {label.setBackground(new Background(new BackgroundFill(Color.rgb(168, 145, 103,.5), null, null)));});
			geSearchResultLabels[i].setOnMouseExited ((mouseEvent)-> {label.setBackground(new Background(new BackgroundFill(null, null, null)));});
			geSearchResultLabels[i].setOnMouseClicked((mouseEvent)->{itemSearchSelectionListener(Integer.valueOf(tc_itemListArray[j][1]));});

		}


	}


	private void removeLabelActionListeners() {
		int arrLength = requestController.get_getSearchResultSize();
		addGeSearchResultLabelsToArray();


		for(int i = 0; i < arrLength ; i++)
		{
			Label l = geSearchResultLabels[i];
			l.setOnMouseEntered((mouseEvent)-> {});
			l.setOnMouseClicked((mouseEvent)->{});
		}

	}

	private void addGeSearchResultLabelsToArray() {

		geSearchResultLabels[0] = searchGeResultLabelInstances("geSearchResult1").getLabel();
		geSearchResultLabels[1] = searchGeResultLabelInstances("geSearchResult2").getLabel();
		geSearchResultLabels[2] = searchGeResultLabelInstances("geSearchResult3").getLabel();
		geSearchResultLabels[3] = searchGeResultLabelInstances("geSearchResult4").getLabel();
		geSearchResultLabels[4] = searchGeResultLabelInstances("geSearchResult5").getLabel();
		geSearchResultLabels[5] = searchGeResultLabelInstances("geSearchResult6").getLabel();
		geSearchResultLabels[6] = searchGeResultLabelInstances("geSearchResult7").getLabel();
		geSearchResultLabels[7] = searchGeResultLabelInstances("geSearchResult8").getLabel();
		geSearchResultLabels[8] = searchGeResultLabelInstances("geSearchResult9").getLabel();
		geSearchResultLabels[9] = searchGeResultLabelInstances("geSearchResult10").getLabel();
		geSearchResultLabels[10] = searchGeResultLabelInstances("geSearchResult11").getLabel();
		geSearchResultLabels[11] = searchGeResultLabelInstances("geSearchResult12").getLabel();
	}

	private void addGeSearchResultDefaultItemImageViewsToArray() {

		geSearchResultImages[0]  = searchGeResultLabelInstances("geSearchResult1").getLabelImage();
		geSearchResultImages[1] = searchGeResultLabelInstances("geSearchResult2").getLabelImage();
		geSearchResultImages[2] = searchGeResultLabelInstances("geSearchResult3").getLabelImage();
		geSearchResultImages[3] = searchGeResultLabelInstances("geSearchResult4").getLabelImage();
		geSearchResultImages[4] = searchGeResultLabelInstances("geSearchResult5").getLabelImage();
		geSearchResultImages[5] = searchGeResultLabelInstances("geSearchResult6").getLabelImage();
		geSearchResultImages[6] = searchGeResultLabelInstances("geSearchResult7").getLabelImage();
		geSearchResultImages[7] = searchGeResultLabelInstances("geSearchResult8").getLabelImage();
		geSearchResultImages[8] = searchGeResultLabelInstances("geSearchResult9").getLabelImage();
		geSearchResultImages[9] = searchGeResultLabelInstances("geSearchResult10").getLabelImage();
		geSearchResultImages[10] = searchGeResultLabelInstances("geSearchResult11").getLabelImage();
		geSearchResultImages[11] = searchGeResultLabelInstances("geSearchResult12").getLabelImage();
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
}
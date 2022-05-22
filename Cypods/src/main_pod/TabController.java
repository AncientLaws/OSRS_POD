package main_pod;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

import javafx.embed.swing.SwingNode;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.util.Duration;

public class TabController extends paneInterface {
	
	
/***************Interface variables********************/
	protected Label lTab;
	static String instanceActiveTab; 
	private String tabActive;
	private Image image;
	protected ImageView imageView;
	Tooltip tooltip;
	private String itemToolTip = "Item Tool Tip";
	
/***************Connect and get data********************/
	GET Get =  new GET(); 
	private String ItemSpriteUrl = "";
	protected InputStream error;
	public InputStream input;	
	private String[] itemInfoArr = new String [18];
	private String[][] tc_itemListArray = new String[100][6];

/***************Tab icon settings********************/
	int X;
	int Y;

/***************End variable declaration**************/			
	
TabController (String s){
		imageView = new ImageView();
		tabSettings(s);
		try { itemSearchListener();
		}
		catch(Exception e) {catchError();
		}
	}
	
	public void tabSettings(String tab) {
		System.out.println("tabSettings");
		switch (tab){
			case "Tab1":{ X = 100; Y = 12; tabActive = tab; break; }
			case "Tab2":{ X = 189; Y = 12; tabActive = tab; break;}
			case "Tab3":{ X = 278; Y = 12; tabActive = tab; break;}
			case "Tab4":{ X = 367; Y = 12; tabActive = tab; break;}
			case "Tab5":{ X = 456; Y = 12; tabActive = tab; break;}
			case "Tab6":{ X = 545; Y = 12; tabActive = tab; break;}
			case "Tab7":{ X = 636; Y = 12; tabActive = tab; break;}
			case "Tab8":{ X = 728; Y = 12; tabActive = tab; break;}
			case "Tab9":{ X = 818; Y = 12; tabActive = tab; break;}
			case "Tab10":{X = 909; Y = 12; tabActive = tab; break;}			
		}
		initLabel();
		initImage();
	}
	
	protected void setInterfaceVisible(boolean b){
		pane_setVisibleInterface(b);
	}

	private void setIcon() {
			ItemSpriteUrl = itemInfoArr[3];
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
			System.out.println("Error in getting Icon for:" + tabActive);
			
		}
		//return input;
	}
	
	public void setActive() {
		tabNo = tabActive;
		setInterfaceVisible(true); 				//Setting current Objects paneInterface to be visible
		root.setId(tabActive);					//changing background to simulate tab change
		imageView.setOpacity(1);            	//returns item to full opacity
		System.out.println("setActive: " + tabActive);
		}
	
	private void itemSearchListener() {
		itemSearchInput.setOnKeyPressed(KeyEvent ->
			{
				if(KeyEvent.getCode().equals(KeyCode.ENTER)) {
				pane_ItemSearchInputText = itemSearchInput.getText();
				
				Get.getItemJsonList("https://services.runescape.com/m=itemdb_oldschool/api/catalogue/items.json?category=1&alpha=" + pane_ItemSearchInputText);
				geSearchResults();
			}
				
		itemSearchInput.setOnMousePressed((mouseEvent) -> {
			itemSearchInput.setText("");
			clearGeSearchResults();
		});
				
				

		});
	}
	
	private void itemSearchSelectionListener(int itemID) {
		Get.getItemJson("https://services.runescape.com/m=itemdb_oldschool/api/catalogue/detail.json?item=" + itemID);
		itemInfoArr = Get.getItemInfo();		//Getting chosen Item Json Data
		setIcon();
		setInterfaceLabels();					//Drawing everything
		pane_updateChart("https://prices.runescape.wiki/api/v1/osrs/timeseries?timestep=6h&id=" + itemID);
		
	}
	
	private void catchError(){
		 //root.getChildren().remove(lTab);
		 image = new Image("Item_UnAvailable.png"); 
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
		 System.out.println("iconImageSettings InputStream: ");
		 imageView.setLayoutX(X);
		 imageView.setLayoutY(Y);
		 imageView.setPreserveRatio(true);
		 imageView.setFitHeight(75);
		 imageView.setFitWidth(75);
		 imageView.setStyle("-fx-background-color: BLACK");
         imageView.setCache(true);
         imageView.setVisible(true);
         tab_IconTooltip(itemInfoArr[0]);
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
		  lTab = new Label("");
		  lTab.setTranslateX(X);
		  lTab.setTranslateY(Y);
		  lTab.setPrefSize(75, 75);
		  root.getChildren().add(lTab);
	}
	
	private void setInterfaceLabels() {

	         try {
	        	 try {
		        		 iconImageSettings();  //must have called getIcon() for it not to be null
		        		 pane_setItemTopMenu(image);
		        		 pane_iconTooltip(itemInfoArr[0]);
		        		 setLabels(  
		        				 	 		 itemInfoArr[0]							//name
		        				 			,itemInfoArr[1] 						//Item ID
		        				 			,itemInfoArr[6]							//Description 
		        				 			,itemInfoArr[7]							//Member
		        				 			,itemInfoArr[8]							//Current price
		        				 			,itemInfoArr[9]							//Current trend
		        				 			,itemInfoArr[10]						//Todays price
		        				 			,itemInfoArr[11]						//Todays trend
		        				 			,itemInfoArr[12]						//30 day trend
		        				 			,itemInfoArr[13]						//30 day change
		        				 			,itemInfoArr[14]						//90 day trend
		        				 			,itemInfoArr[15]						//90 day change
		        				 			,itemInfoArr[16]						//180 day trend
		        				 			,itemInfoArr[17]						//180 day change
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
	
	private void clearGeSearchResults() {
		removeLabelActionListeners();
		geSearchResult1.setText("");
		geSearchResult2.setText("");
		geSearchResult3.setText("");
		geSearchResult4.setText("");
		geSearchResult5.setText("");
		geSearchResult6.setText("");
		geSearchResult7.setText("");
		geSearchResult8.setText("");
		geSearchResult9.setText("");
		geSearchResult10.setText("");
		geSearchResult11.setText("");
		geSearchResult12.setText("");
		
		
		img1.setImage(null);
		img2.setImage(null);
		img3.setImage(null);
		img4.setImage(null);
		img5.setImage(null);
		img6.setImage(null);
		img7.setImage(null);
		img8.setImage(null);
		img9.setImage(null);
		img10.setImage(null);
		img11.setImage(null);
		img12.setImage(null);
	}
	
	private void geSearchResults() {
		clearGeSearchResults();		
		addLabelActionListeners();
		
		tc_itemListArray = Get.returnItemListArray() ;
		
		try { //Open stream to grab the image for each of the returned items
			geSearchResult1.setText(tc_itemListArray[0][2]);
			input = new URL (tc_itemListArray[0][0]).openStream();
	     	image = new Image(input); 
	     	img1.setImage(image);

	     	
			geSearchResult2.setText(tc_itemListArray[1][2]);			
			input = new URL (tc_itemListArray[1][0]).openStream();
	     	image = new Image(input); 
	     	img2.setImage(image);

	     	
			geSearchResult3.setText(tc_itemListArray[2][2]);			
			input = new URL (tc_itemListArray[2][0]).openStream();
	     	image = new Image(input); 
	     	img3.setImage(image);

	     	
			geSearchResult4.setText(tc_itemListArray[3][2]);			
			input = new URL (tc_itemListArray[3][0]).openStream();
	     	image = new Image(input); 
	     	img4.setImage(image);

			geSearchResult5.setText(tc_itemListArray[4][2]);			
			input = new URL (tc_itemListArray[4][0]).openStream();
	     	image = new Image(input); 
	     	img5.setImage(image);
	     	
			geSearchResult6.setText(tc_itemListArray[5][2]);			
			input = new URL (tc_itemListArray[5][0]).openStream();
	     	image = new Image(input); 
	     	img6.setImage(image);

			geSearchResult7.setText(tc_itemListArray[6][2]);			
			input = new URL (tc_itemListArray[6][0]).openStream();
	     	image = new Image(input); 
	     	img7.setImage(image);
   	
			geSearchResult8.setText(tc_itemListArray[7][2]);			
			input = new URL (tc_itemListArray[7][0]).openStream();
	     	image = new Image(input); 
	     	img8.setImage(image);

			geSearchResult9.setText(tc_itemListArray[8][2]);			
			input = new URL (tc_itemListArray[8][0]).openStream();
	     	image = new Image(input); 
	     	img9.setImage(image);
	     	
			geSearchResult10.setText(tc_itemListArray[9][2]);	   	     	
			input = new URL (tc_itemListArray[9][0]).openStream();
	     	image = new Image(input); 
	     	img10.setImage(image);	
	     	
			geSearchResult11.setText(tc_itemListArray[10][2]);	     	
			input = new URL (tc_itemListArray[10][0]).openStream();
	     	image = new Image(input); 
	     	img11.setImage(image);	
 	     	
			geSearchResult12.setText(tc_itemListArray[11][2]);	     	
			input = new URL (tc_itemListArray[11][0]).openStream();
	     	image = new Image(input); 
	     	img12.setImage(image);	
	     	
			
		}
		catch(Exception e) {
			
			System.out.println("Error grabbing Icon Images in TabController>geSearchResults");
			System.out.println(e);
			
		}
		
		
	}
	


	private void addLabelActionListeners() {
		geSearchResult1.setOnMouseEntered((mouseEvent)-> {geSearchResult1.setBackground(new Background(new BackgroundFill(Color.rgb(168, 145, 103,.5), null, null)));});
		geSearchResult1.setOnMouseExited ((mouseEvent)-> {geSearchResult1.setBackground(new Background(new BackgroundFill(null, null, null)));});
		geSearchResult1.setOnMouseClicked((mouseEvent)->{itemSearchSelectionListener(Integer.valueOf(tc_itemListArray[0][1]));});
		
		geSearchResult2.setOnMouseEntered((mouseEvent)-> {geSearchResult2.setBackground(new Background(new BackgroundFill(Color.rgb(168, 145, 103,.5), null, null)));});
		geSearchResult2.setOnMouseExited ((mouseEvent)-> {geSearchResult2.setBackground(new Background(new BackgroundFill(null, null, null)));});
		geSearchResult2.setOnMouseClicked((mouseEvent)->{itemSearchSelectionListener(Integer.valueOf(tc_itemListArray[1][1]));});
		
		geSearchResult3.setOnMouseEntered((mouseEvent)-> {geSearchResult3.setBackground(new Background(new BackgroundFill(Color.rgb(168, 145, 103,.5), null, null)));});
		geSearchResult3.setOnMouseExited ((mouseEvent)-> {geSearchResult3.setBackground(new Background(new BackgroundFill(null, null, null)));});
		geSearchResult3.setOnMouseClicked((mouseEvent)->{itemSearchSelectionListener(Integer.valueOf(tc_itemListArray[2][1]));});
		
		geSearchResult4.setOnMouseEntered((mouseEvent)-> {geSearchResult4.setBackground(new Background(new BackgroundFill(Color.rgb(168, 145, 103,.5), null, null)));});
		geSearchResult4.setOnMouseExited ((mouseEvent)-> {geSearchResult4.setBackground(new Background(new BackgroundFill(null, null, null)));});
		geSearchResult4.setOnMouseClicked((mouseEvent)->{itemSearchSelectionListener(Integer.valueOf(tc_itemListArray[3][1]));});
		
		geSearchResult5.setOnMouseEntered((mouseEvent)-> {geSearchResult5.setBackground(new Background(new BackgroundFill(Color.rgb(168, 145, 103,.5), null, null)));});
		geSearchResult5.setOnMouseExited ((mouseEvent)-> {geSearchResult5.setBackground(new Background(new BackgroundFill(null, null, null)));});
		geSearchResult5.setOnMouseClicked((mouseEvent)->{itemSearchSelectionListener(Integer.valueOf(tc_itemListArray[4][1]));});
		
		geSearchResult6.setOnMouseEntered((mouseEvent)-> {geSearchResult6.setBackground(new Background(new BackgroundFill(Color.rgb(168, 145, 103,.5), null, null)));});
		geSearchResult6.setOnMouseExited ((mouseEvent)-> {geSearchResult6.setBackground(new Background(new BackgroundFill(null, null, null)));});
		geSearchResult6.setOnMouseClicked((mouseEvent)->{itemSearchSelectionListener(Integer.valueOf(tc_itemListArray[5][1]));});
		
		geSearchResult7.setOnMouseEntered((mouseEvent)-> {geSearchResult7.setBackground(new Background(new BackgroundFill(Color.rgb(168, 145, 103,.5), null, null)));});
		geSearchResult7.setOnMouseExited ((mouseEvent)-> {geSearchResult7.setBackground(new Background(new BackgroundFill(null, null, null)));});
		geSearchResult7.setOnMouseClicked((mouseEvent)->{itemSearchSelectionListener(Integer.valueOf(tc_itemListArray[6][1]));});
		
		geSearchResult8.setOnMouseEntered((mouseEvent)-> {geSearchResult8.setBackground(new Background(new BackgroundFill(Color.rgb(168, 145, 103,.5), null, null)));});
		geSearchResult8.setOnMouseExited ((mouseEvent)-> {geSearchResult8.setBackground(new Background(new BackgroundFill(null, null, null)));});
		geSearchResult8.setOnMouseClicked((mouseEvent)->{itemSearchSelectionListener(Integer.valueOf(tc_itemListArray[7][1]));});
		
		geSearchResult9.setOnMouseEntered((mouseEvent)-> {geSearchResult9.setBackground(new Background(new BackgroundFill(Color.rgb(168, 145, 103,.5), null, null)));});
		geSearchResult9.setOnMouseExited ((mouseEvent)-> {geSearchResult9.setBackground(new Background(new BackgroundFill(null, null, null)));});
		geSearchResult9.setOnMouseClicked((mouseEvent)->{itemSearchSelectionListener(Integer.valueOf(tc_itemListArray[8][1]));});
		
		geSearchResult10.setOnMouseEntered((mouseEvent)-> {geSearchResult10.setBackground(new Background(new BackgroundFill(Color.rgb(168, 145, 103,.5), null, null)));});
		geSearchResult10.setOnMouseExited ((mouseEvent)-> {geSearchResult10.setBackground(new Background(new BackgroundFill(null, null, null)));});
		geSearchResult10.setOnMouseClicked((mouseEvent)->{itemSearchSelectionListener(Integer.valueOf(tc_itemListArray[9][1]));});
		
		geSearchResult11.setOnMouseEntered((mouseEvent)-> {geSearchResult11.setBackground(new Background(new BackgroundFill(Color.rgb(168, 145, 103,.5), null, null)));});
		geSearchResult11.setOnMouseExited ((mouseEvent)-> {geSearchResult11.setBackground(new Background(new BackgroundFill(null, null, null)));});
		geSearchResult11.setOnMouseClicked((mouseEvent)->{itemSearchSelectionListener(Integer.valueOf(tc_itemListArray[10][1]));});
		
		geSearchResult12.setOnMouseEntered((mouseEvent)-> {geSearchResult12.setBackground(new Background(new BackgroundFill(Color.rgb(168, 145, 103,.5), null, null)));});
		geSearchResult12.setOnMouseExited ((mouseEvent)-> {geSearchResult12.setBackground(new Background(new BackgroundFill(null, null, null)));});
		geSearchResult12.setOnMouseClicked((mouseEvent)->{itemSearchSelectionListener(Integer.valueOf(tc_itemListArray[11][1]));});
	}
	
	
	private void removeLabelActionListeners() {
		geSearchResult1.setOnMouseEntered((mouseEvent)-> {});
		geSearchResult2.setOnMouseEntered((mouseEvent)-> {});
		geSearchResult3.setOnMouseEntered((mouseEvent)-> {});
		geSearchResult4.setOnMouseEntered((mouseEvent)-> {});
		geSearchResult5.setOnMouseEntered((mouseEvent)-> {});
		geSearchResult6.setOnMouseEntered((mouseEvent)-> {});
		geSearchResult7.setOnMouseEntered((mouseEvent)-> {});
		geSearchResult8.setOnMouseEntered((mouseEvent)-> {});
		geSearchResult9.setOnMouseEntered((mouseEvent)-> {});
		geSearchResult10.setOnMouseEntered((mouseEvent)-> {});
		geSearchResult11.setOnMouseEntered((mouseEvent)-> {});
		geSearchResult12.setOnMouseEntered((mouseEvent)-> {});
		
		geSearchResult1.setOnMouseClicked((mouseEvent)->{});
		geSearchResult2.setOnMouseClicked((mouseEvent)->{});
		geSearchResult3.setOnMouseClicked((mouseEvent)->{});
		geSearchResult4.setOnMouseClicked((mouseEvent)->{});
		geSearchResult5.setOnMouseClicked((mouseEvent)->{});
		geSearchResult6.setOnMouseClicked((mouseEvent)->{});
		geSearchResult7.setOnMouseClicked((mouseEvent)->{});
		geSearchResult8.setOnMouseClicked((mouseEvent)->{});
		geSearchResult9.setOnMouseClicked((mouseEvent)->{});
		geSearchResult10.setOnMouseClicked((mouseEvent)->{});
		geSearchResult11.setOnMouseClicked((mouseEvent)->{});
		geSearchResult12.setOnMouseClicked((mouseEvent)->{});
	}
	
	


}

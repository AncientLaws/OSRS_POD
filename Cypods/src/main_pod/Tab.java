package main_pod;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.util.Duration;

public class Tab extends paneInterface {
	
	
	private String ItemSpriteUrl = "";
	private String itemToolTip = "Item Tool Tip";
	private String tabActive;
	protected ImageView imageView;
	private Image image;
	protected InputStream error;
	public InputStream input;
	protected Label lTab;
	static String instanceActiveTab; 
	Tooltip tooltip;
	GET Get =  new GET(); 
	private String[] itemInfoArr = new String [18];
	/** name  				//0		
	,id    				//1	
	,icon 				//2	
	,icon_large  		//3	
	,type  				//4	
	,typeIcon  			//5	
	,description 		//6	
	,members 			//7	

	,currentPrice		//8	
	,currentTrend		//9	

	,todayPrice			//10	
	,todayTrend			//11	

	,day30_trend 		//12	
	,day30_change 		//13	
	,day90_trend 		//14	
	,day90_change 		//15
	,day180_trend 		//16	
	,day180_change};	//17	
	*/

	/**Tab icon settings*/
	int X;
	int Y;
	
/**End Tab icon Settings*/
	
	Tab (String s){
		 imageView = new ImageView();
		tabSettings(s);
		try {
			
			itemSearchListener();
			
		}
		catch(Exception e)
		{
			catchError();
		}

	}
	
	
	//paneInterface pi  = new paneInterface();
	    
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
			Get.getItemJson("https://services.runescape.com/m=itemdb_oldschool/api/catalogue/detail.json?item=" + pane_ItemSearchInputText);
			itemInfoArr = Get.getItemInfo();		//Getting chosen Item Json Data
			setIcon();
			setInterfaceLabels();					//Drawing everything
			
			
			
			}
		});
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
	
	private void tab_IconTooltip (String s)
	{
		tooltip = new Tooltip(s);
		tooltip.setShowDelay(Duration.millis(100));
		tooltip.setId("tooltip");
		tooltip.install(imageView, tooltip);
		
		
	}


}

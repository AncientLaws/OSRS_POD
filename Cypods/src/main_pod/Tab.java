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
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

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
	/**Tab icon settings*/
	int X;
	int Y;
	
/**End Tab icon Settings*/
	
	Tab (String s){
		 imageView = new ImageView();
		tabSettings(s);
		try {
			Get.getItemJson("https://services.runescape.com/m=itemdb_oldschool/api/catalogue/detail.json?item=26382");
			setIcon();
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
			ItemSpriteUrl = Get.getIconLargeSprite();
			getIcon();
		}

	private void /*InputStream*/ getIcon() {
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
		setInterfaceVisible(true); //testing out control using DC
		root.setId(tabActive);
		setInterfaceLabels();
		System.out.println("setActive: " + tabActive);
		}
	

	
	private void catchError(){
		 //root.getChildren().remove(lTab);
		 image = new Image("Item_UnAvailable.png"); 
		 imageView.setImage(image);
		 imageView.setPreserveRatio(true);
		 imageView.setFitHeight(75);
		 imageView.setFitWidth(75);
		 imageView.setX(X);
		 imageView.setY(Y);
		 imageView.setStyle("-fx-background-color: BLACK");
		 imageView.setCache(true);
		 root.getChildren().add(imageView);
		 //imageView.setOnMousePressed((mouseEvent) -> setActive());  Mouse Event 3
		
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
         tab_IconTooltip(Get.getItemName());
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
		        		 pane_iconTooltip(Get.getItemName());
	        	 	 }
	        	 catch(Exception e) {
		        		 System.out.println("Error in setInterfaceLabels()");
		        		 catchError();
	        	 	 }
			} catch (Exception e) {
					System.out.println("Error in setInterfaceLabels: " + e);
			}
	}
	
	private void tab_IconTooltip (String s)
	{
		tooltip = new Tooltip(s);
		tooltip.setId("tooltip");
		tooltip.install(imageView, tooltip);
		
		
	}


}

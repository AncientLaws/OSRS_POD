package main_pod;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;

public class Tab extends DisplayController {
	private String ItemSpriteUrl = "";
	private String ItemError = "https://upload.wikimedia.org/wikipedia/commons/thumb/3/31/ProhibitionSign2.svg/1200px-ProhibitionSign2.svg.png";
	ImageView imageView;
	Image image;
	InputStream error;
	InputStream input;
	Label lTab;
	
	
/**Tab icon settings*/
	int X;
	int Y;
	
/**End Tab icon Settings*/
	
	String tabActive;
	
	paneInterface pi ;
	
	public void tabSettings(String tab) {
		switch (tab)
		{
			case "Tab1":{
				X = 100;
				Y = 12;
				tabActive = tab;
				pi = new paneInterface();
				pi.activateInterface();
				break;
			}
			case "Tab2":{
				X = 189;
				Y = 12;
				tabActive = tab;
				break;
			}
			case "Tab3":{
				X = 278;
				Y = 12;
				tabActive = tab;
				break;
			}
			case "Tab4":{
				X = 367;
				Y = 12;
				tabActive = tab;
				break;
			}
			case "Tab5":{
				X = 456;
				Y = 12;
				tabActive = tab;
				break;
			}
			case "Tab6":{
				X = 545;
				Y = 12;
				tabActive = tab;
				break;
			}
			case "Tab7":{
				X = 636;
				Y = 12;
				tabActive = tab;
				break;
			}
			case "Tab8":{
				X = 728;
				Y = 12;
				tabActive = tab;
				break;
			}
			case "Tab9":{
				X = 818;
				Y = 12;
				tabActive = tab;
				break;
			}
			case "Tab10":{
				X = 909;
				Y = 12;
				tabActive = tab;
				break;
			}
			
		}
		initLabel();
		//setIcon(ICON);
		
	}
	
	
	public void setIcon(String s) {
		
			ItemSpriteUrl = s;
			getIcon();
		}

	public void getIcon() {
		try {

            input = new
      		 URL (ItemSpriteUrl).openStream();
            iconImageSettings(input);
      		 
		
		}
		catch(Exception e) {
			System.out.println("Error in getting Icon for Tab1");
			catchError();
		}
	}
	
	public void setActive() {
		root.setId(tabActive);
		
	}
	
	private void catchError()
	{
		 root.getChildren().remove(lTab);
		 image = new Image("Item_UnAvailable.png"); 
		 imageView = new ImageView(image);
		 imageView.setPreserveRatio(true);
		 imageView.setFitHeight(75);
		 imageView.setFitWidth(75);
		 imageView.setStyle("-fx-background-color: BLACK");
		 imageView.setCache(true);
		 lTab = new Label("",imageView);
		 lTab.setTranslateX(X);
		 lTab.setTranslateY(Y);
		 root.getChildren().add(lTab);
		 lTab.setOnMousePressed((mouseEvent) -> setActive());
		
	}
	
	private void iconImageSettings(InputStream i) {
		 image = new Image(i); 
 		 imageView = new ImageView(image);
 		 imageView.setPreserveRatio(true);
 		 imageView.setFitHeight(75);
 		 imageView.setFitWidth(75);
 		 imageView.setStyle("-fx-background-color: BLACK");
         imageView.setCache(true);
         lTab = new Label("",imageView);
         lTab.setTranslateX(X);
         lTab.setTranslateY(Y);
         root.getChildren().add(lTab);
         lTab.setOnMousePressed((mouseEvent) -> setActive());
		
	}
	
	private void initLabel() {
		
		imageView = new ImageView();
        imageView.setFitHeight(75);
		imageView.setFitWidth(75);
		lTab = new Label("",imageView);
        lTab.setTranslateX(X);
        lTab.setTranslateY(Y);
        root.getChildren().add(lTab);
        lTab.setOnMousePressed((mouseEvent) -> setActive());
        //lTab.setOnMouseEntered((mouseEvent) -> imageView.setEffect(ds));
		
	}
	
	private void removeLabel() {
		root.getChildren().remove(lTab);
	}
	


}

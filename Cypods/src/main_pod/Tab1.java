package main_pod;

import java.io.InputStream;
import java.net.URL;

import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Tab1 extends tabs {
	private String ItemSpriteUrl = "";

	public void setIcon(String s) {
		try {
			ItemSpriteUrl = s;
		}
		catch(Exception e) {
			System.out.println("Error in setting Icon for Tab1");
		}
	
	}	
	public void getIcon() {
		try {
            InputStream input = new
      		 URL (ItemSpriteUrl).openStream();
      		 Image image = new Image(input); 
      		 ImageView imageView = new ImageView(image);
      		 imageView.setPreserveRatio(true);
      		 imageView.setFitHeight(75);
      		 imageView.setFitWidth(75);
      		 imageView.setStyle("-fx-background-color: BLACK");
             imageView.setCache(true);
      		 tab2 = new Label("",imageView);
      		 tab2.setTranslateX(100);
             tab2.setTranslateY(12);
             root.getChildren().add(tab2);
		
		}
		catch(Exception e) {
			System.out.println("Error in getting Icon for Tab1");
		}
	}


}

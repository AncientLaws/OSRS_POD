package main_pod;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Tab1 extends tabs {
	private String ItemSpriteUrl = "";
	private String ItemError = "https://upload.wikimedia.org/wikipedia/commons/thumb/3/31/ProhibitionSign2.svg/1200px-ProhibitionSign2.svg.png";
	ImageView imageView;
	Image image;
	InputStream error;
	InputStream input;
	Label lTab1;

	public void setIcon(String s) {
		
			ItemSpriteUrl = s;
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
	private void catchError()
	{
		try {
			error = new
		      		 URL (ItemError).openStream();
			iconImageSettings(error);
		}
		catch(IOException e) {
			System.out.println("Item \"error\" Icon is null (failed to load)");
		}
	}
	private void iconImageSettings(InputStream i) {
		 image = new Image(i); 
 		 imageView = new ImageView(image);
 		 imageView.setPreserveRatio(true);
 		 imageView.setFitHeight(75);
 		 imageView.setFitWidth(75);
 		 imageView.setStyle("-fx-background-color: BLACK");
         imageView.setCache(true);
         lTab1 = new Label("",imageView);
         lTab1.setTranslateX(100);
         lTab1.setTranslateY(12);
         root.getChildren().add(lTab1);
		
	}


}

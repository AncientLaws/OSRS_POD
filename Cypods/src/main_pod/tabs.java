package main_pod;

import java.io.InputStream;
import java.net.URL;

import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class tabs extends Window{
	
	 GET getItemSprite =  new GET(); 
	public void getTab1() {
		try {
		   Tab1 tab1 = new Tab1(); 
		   tab1.setIcon(getItemSprite.getIconLargeSprite("https://services.runescape.com/m=itemdb_oldschool/api/catalogue/detail.json?item=26382"));
           tab1.getIcon();
		
           
		}
		catch(Exception e) {
			System.out.println("Error in calling tab1");
		}
		
	}
	public void getTab2() {
		try {
            InputStream input = new
      		 URL (getItemSprite.getIconLargeSprite("https://services.runescape.com/m=itemdb_oldschool/api/catalogue/detail.json?item=26384")).openStream();
      		 Image image = new Image(input); 
      		 ImageView imageView = new ImageView(image);
      		 imageView.setPreserveRatio(true);
      		 imageView.setFitHeight(75);
      		 imageView.setFitWidth(75);
      		 tab2 = new Label("",imageView);
      		 tab2.setTranslateX(189);
             tab2.setTranslateY(12);
             root.getChildren().add(tab2);
			
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
	}
	public void getTab3() {
		try {
            InputStream input = new
      		 URL (getItemSprite.getIconLargeSprite("https://services.runescape.com/m=itemdb_oldschool/api/catalogue/detail.json?item=26386")).openStream();
      		 Image image = new Image(input); 
      		 ImageView imageView = new ImageView(image);
      		 imageView.setPreserveRatio(true);
      		 imageView.setFitHeight(75);
      		 imageView.setFitWidth(75);
      		 tab2 = new Label("",imageView);
      		 tab2.setTranslateX(278);
             tab2.setTranslateY(12);
             root.getChildren().add(tab2);
			
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
	}
	public void getTab4() {
		try {
            InputStream input = new
      		 URL (getItemSprite.getIconLargeSprite("https://services.runescape.com/m=itemdb_oldschool/api/catalogue/detail.json?item=26233")).openStream();
      		 Image image = new Image(input); 
      		 ImageView imageView = new ImageView(image);
      		 imageView.setPreserveRatio(true);
      		 imageView.setFitHeight(75);
      		 imageView.setFitWidth(75);
      		 tab2 = new Label("",imageView);
      		 tab2.setTranslateX(367);
             tab2.setTranslateY(12);
             root.getChildren().add(tab2);
             
			
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
	}
	public void getTab5() {
		try {
            InputStream input = new
      		 URL (getItemSprite.getIconLargeSprite("https://services.runescape.com/m=itemdb_oldschool/api/catalogue/detail.json?item=26374")).openStream();
      		 Image image = new Image(input); 
      		 ImageView imageView = new ImageView(image);
      		 imageView.setPreserveRatio(true);
      		 imageView.setFitHeight(75);
      		 imageView.setFitWidth(75);
      		 tab2 = new Label("",imageView);
      		 tab2.setTranslateX(456);
             tab2.setTranslateY(12);
             root.getChildren().add(tab2);
			
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
		
	}
	public void getTab6() {
		try {
            InputStream input = new
      		 URL (getItemSprite.getIconLargeSprite("https://services.runescape.com/m=itemdb_oldschool/api/catalogue/detail.json?item=26235")).openStream();
      		 Image image = new Image(input); 
      		 ImageView imageView = new ImageView(image);
      		 imageView.setPreserveRatio(true);
      		 imageView.setFitHeight(75);
      		 imageView.setFitWidth(75);
      		 tab2 = new Label("",imageView);
      		 tab2.setTranslateX(545);
             tab2.setTranslateY(12);
             root.getChildren().add(tab2);
			
		}
		catch(Exception e) {
			e.printStackTrace();
		}		
	}
	public void getTab7() {
		try {
            InputStream input = new
      		 URL (getItemSprite.getIconLargeSprite("https://services.runescape.com/m=itemdb_oldschool/api/catalogue/detail.json?item=11785")).openStream();
      		 Image image = new Image(input); 
      		 ImageView imageView = new ImageView(image);
      		 imageView.setPreserveRatio(true);
      		 imageView.setFitHeight(75);
      		 imageView.setFitWidth(75);
      		 tab2 = new Label("",imageView);
      		 tab2.setTranslateX(636);
             tab2.setTranslateY(12);
             root.getChildren().add(tab2);
			
		}
		catch(Exception e) {
			e.printStackTrace();
		}		
	}
	public void getTab8() {
		try {
            InputStream input = new
      		 URL (getItemSprite.getIconLargeSprite("https://services.runescape.com/m=itemdb_oldschool/api/catalogue/detail.json?item=11830")).openStream();
      		 Image image = new Image(input); 
      		 ImageView imageView = new ImageView(image);
      		 imageView.setPreserveRatio(true);
      		 imageView.setFitHeight(75);
      		 imageView.setFitWidth(75);
      		 tab2 = new Label("",imageView);
      		 tab2.setTranslateX(728);
             tab2.setTranslateY(12);
             root.getChildren().add(tab2);
			
		}
		catch(Exception e) {
			e.printStackTrace();
		}		
	}
	public void getTab9() {
		try {
            InputStream input = new
      		 URL (getItemSprite.getIconLargeSprite("https://services.runescape.com/m=itemdb_oldschool/api/catalogue/detail.json?item=11828")).openStream();
      		 Image image = new Image(input); 
      		 ImageView imageView = new ImageView(image);
      		 imageView.setPreserveRatio(true);
      		 imageView.setFitHeight(75);
      		 imageView.setFitWidth(75);
      		 tab2 = new Label("",imageView);
      		 tab2.setTranslateX(818);
             tab2.setTranslateY(12);
             root.getChildren().add(tab2);
			
		}
		catch(Exception e) {
			e.printStackTrace();
		}		
	}
	public void getTab10() {
		try {
            InputStream input = new
      		 URL (getItemSprite.getIconLargeSprite("https://services.runescape.com/m=itemdb_oldschool/api/catalogue/detail.json?item=11826")).openStream();
      		 Image image = new Image(input); 
      		 ImageView imageView = new ImageView(image);
      		 imageView.setPreserveRatio(true);
      		 imageView.setFitHeight(75);
      		 imageView.setFitWidth(75);
      		 tab2 = new Label("",imageView);
      		 tab2.setTranslateX(909);
             tab2.setTranslateY(12);
             root.getChildren().add(tab2);
			
		}
		catch(Exception e) {
			e.printStackTrace();
		}		
	}
}

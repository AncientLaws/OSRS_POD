package main_pod;

import java.io.InputStream;
import java.net.URL;

import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class tabs extends Window{
	public void getTab1() {
		try {
            InputStream input = new
      		 URL ("https://secure.runescape.com/m=itemdb_rs/1641812494724_obj_sprite.gif?id=21787").openStream();
      		 Image image = new Image(input); 
      		 ImageView imageView = new ImageView(image);
      		 imageView.setPreserveRatio(true);
      		 imageView.setFitHeight(50);
      		 imageView.setFitWidth(50);
      		 imageView.setStyle("-fx-background-color: BLACK");
             imageView.setCache(true);
      		 tab2 = new Label("",imageView);
      		 tab2.setTranslateX(111);
             tab2.setTranslateY(25);
             root.getChildren().add(tab2);
			
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
	}
	public void getTab2() {
		try {
            InputStream input = new
      		 URL ("https://secure.runescape.com/m=itemdb_rs/1641812494724_obj_sprite.gif?id=21790").openStream();
      		 Image image = new Image(input); 
      		 ImageView imageView = new ImageView(image);
      		 imageView.setPreserveRatio(true);
      		 imageView.setFitHeight(50);
      		 imageView.setFitWidth(50);
      		 tab2 = new Label("",imageView);
      		 tab2.setTranslateX(200);
             tab2.setTranslateY(25);
             root.getChildren().add(tab2);
			
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
	}
	public void getTab3() {
		try {
            InputStream input = new
      		 URL ("https://secure.runescape.com/m=itemdb_rs/1641812494724_obj_sprite.gif?id=21793").openStream();
      		 Image image = new Image(input); 
      		 ImageView imageView = new ImageView(image);
      		 imageView.setPreserveRatio(true);
      		 imageView.setFitHeight(50);
      		 imageView.setFitWidth(50);
      		 tab2 = new Label("",imageView);
      		 tab2.setTranslateX(289);
             tab2.setTranslateY(25);
             root.getChildren().add(tab2);
			
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
	}
	public void getTab4() {
		try {
            InputStream input = new
      		 URL ("https://secure.runescape.com/m=itemdb_rs/1641812494724_obj_sprite.gif?id=21760").openStream();
      		 Image image = new Image(input); 
      		 ImageView imageView = new ImageView(image);
      		 imageView.setPreserveRatio(true);
      		 imageView.setFitHeight(50);
      		 imageView.setFitWidth(50);
      		 tab2 = new Label("",imageView);
      		 tab2.setTranslateX(378);
             tab2.setTranslateY(25);
             root.getChildren().add(tab2);
			
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
	}
	public void getTab5() {
		try {
            InputStream input = new
      		 URL ("https://secure.runescape.com/m=itemdb_rs/1641812494724_obj_sprite.gif?id=21761").openStream();
      		 Image image = new Image(input); 
      		 ImageView imageView = new ImageView(image);
      		 imageView.setPreserveRatio(true);
      		 imageView.setFitHeight(50);
      		 imageView.setFitWidth(50);
      		 tab2 = new Label("",imageView);
      		 tab2.setTranslateX(467);
             tab2.setTranslateY(25);
             root.getChildren().add(tab2);
			
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
		
	}
	public void getTab6() {
		try {
            InputStream input = new
      		 URL ("https://secure.runescape.com/m=itemdb_rs/1641812494724_obj_sprite.gif?id=21762").openStream();
      		 Image image = new Image(input); 
      		 ImageView imageView = new ImageView(image);
      		 imageView.setPreserveRatio(true);
      		 imageView.setFitHeight(50);
      		 imageView.setFitWidth(50);
      		 tab2 = new Label("",imageView);
      		 tab2.setTranslateX(557);
             tab2.setTranslateY(25);
             root.getChildren().add(tab2);
			
		}
		catch(Exception e) {
			e.printStackTrace();
		}		
	}
	public void getTab7() {
		try {
            InputStream input = new
      		 URL ("https://secure.runescape.com/m=itemdb_rs/1641812494724_obj_sprite.gif?id=21763").openStream();
      		 Image image = new Image(input); 
      		 ImageView imageView = new ImageView(image);
      		 imageView.setPreserveRatio(true);
      		 imageView.setFitHeight(50);
      		 imageView.setFitWidth(50);
      		 tab2 = new Label("",imageView);
      		 tab2.setTranslateX(648);
             tab2.setTranslateY(25);
             root.getChildren().add(tab2);
			
		}
		catch(Exception e) {
			e.printStackTrace();
		}		
	}
	public void getTab8() {
		try {
            InputStream input = new
      		 URL ("https://secure.runescape.com/m=itemdb_rs/1641812494724_obj_sprite.gif?id=21694").openStream();
      		 Image image = new Image(input); 
      		 ImageView imageView = new ImageView(image);
      		 imageView.setPreserveRatio(true);
      		 imageView.setFitHeight(50);
      		 imageView.setFitWidth(50);
      		 tab2 = new Label("",imageView);
      		 tab2.setTranslateX(738);
             tab2.setTranslateY(25);
             root.getChildren().add(tab2);
			
		}
		catch(Exception e) {
			e.printStackTrace();
		}		
	}
	public void getTab9() {
		try {
            InputStream input = new
      		 URL ("https://secure.runescape.com/m=itemdb_rs/1641812494724_obj_sprite.gif?id=21695").openStream();
      		 Image image = new Image(input); 
      		 ImageView imageView = new ImageView(image);
      		 imageView.setPreserveRatio(true);
      		 imageView.setFitHeight(50);
      		 imageView.setFitWidth(50);
      		 tab2 = new Label("",imageView);
      		 tab2.setTranslateX(828);
             tab2.setTranslateY(25);
             root.getChildren().add(tab2);
			
		}
		catch(Exception e) {
			e.printStackTrace();
		}		
	}
	public void getTab10() {
		try {
            InputStream input = new
      		 URL ("https://secure.runescape.com/m=itemdb_rs/1641812494724_obj_sprite.gif?id=21696").openStream();
      		 Image image = new Image(input); 
      		 ImageView imageView = new ImageView(image);
      		 imageView.setPreserveRatio(true);
      		 imageView.setFitHeight(50);
      		 imageView.setFitWidth(50);
      		 tab2 = new Label("",imageView);
      		 tab2.setTranslateX(920);
             tab2.setTranslateY(25);
             root.getChildren().add(tab2);
			
		}
		catch(Exception e) {
			e.printStackTrace();
		}		
	}
}
